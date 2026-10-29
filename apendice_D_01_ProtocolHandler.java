import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Clase de utilidad para el procesamiento de protocolos de comunicación
 * definidos en el Apéndice D.
 */
public class ProtocolHandler {

    private static final byte STX = 0x02;
    private static final byte ETX = 0x03;
    private static final byte HEADER_BINARIO = (byte) 0xAA;

    /**
     * Representación de una muestra de alta velocidad recibida por protocolo binario.
     */
    public record TramaBinaria(
        int canalId,
        long tiempoMs,
        short[] muestras,
        boolean esValido
    ) {}

    /**
     * Descodifica una trama binaria de 16 bytes recibida desde el hardware.
     * 
     * @param datos Arreglo de bytes de longitud 16.
     * @return Objeto TramaBinaria con los datos extraídos o inválidos si el checksum falla.
     */
    public TramaBinaria parsearBinario(byte[] datos) {
        if (datos.length != 16) {
            return new TramaBinario(0, 0, new short[0], false);
        }

        ByteBuffer buffer = ByteBuffer.wrap(datos);
        // Configuración de orden de bytes según especificación (Little Endian)
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        // 1. Verificar cabecera de sincronización
        if (buffer.get() != HEADER_BINARIO) {
            return new TramaBinaria(0, 0, new short[]{}, false);
        }

        // 2. Extraer identificador de canal
        int canalId = buffer.get() & 0xFF; // Conversión a unsigned

        // 3. Extraer marca de tiempo (4 bytes)
        long tiempoMs = buffer.getInt() & 0xFFFFFFFFL; // Conversión a unsigned long

        // 4. Extraer 4 muestras de 16 bits cada una
        short[] muestras = new short[4];
        for (int i = 0; i < 4; i++) {
            muestras[i] = buffer.getShort();
        }

        // 5. Verificación de Checksum
        int checksumRecibido = buffer.getShort() & 0xFFFF;
        int checksumCalculado = calcularChecksumBinario(datos, 14);

        boolean esValido = (checksumRecibido == checksumCalculado);

        return new TramaBinaria(canalId, tiempoMs, muestras, esValido);
    }

    /**
     * Calcula el checksum para la trama binaria sumando los primeros n bytes.
     * 
     * @param datos Arreglo original de bytes.
     * @param longitud Hasta qué byte se debe sumar.
     * @return Suma de verificación en formato uint16.
     */
    private int calcularChecksumBinario(byte[] datos, int longitud) {
        int suma = 0;
        for (int i = 0 ; i < longitud; i++) {
            suma += (datos[i] & 0xFF);
        }
        return suma & 0xFFFF;
    }

    /**
     * Genera una trama ASCII de telemetría para su envío al dispositivo.
     * Formato: STX|ID|DAT|VALOR|CHKSUM|ETX
     * 
     * @param idDispositivo Identificador (ej. "S1").
     * @param valor Valor numérico de la medición.
     * @return Cadena de texto lista para transmisión serial.
     */
    public String generarTramaAscii(String idDispositivo, double valor) {
        String payload = idDispositivo + "|DAT|" + String.format("%.2f", valor);
        int checksum = calcularChecksumAscii(payload);
        String checksumHex = String.format("%02X", checksum);
        
        return (char) STX + payload + "|" + checksumHex + (char) ETX;
    }

    /**
     * Calcula el checksum ASCII sumando los valores decimales de los caracteres.
     */
    private int calcularChecksumAscii(String texto) {
        int suma = 0;
        for (char c : texto.toCharArray()) {
            suma += (int) c;
        }
        return suma % 256;
    }

    /**
     * Ejemplo de ejecución para validación de la implementación.
     */
    public static void main(String[] args) {
        ProtocolHandler handler = new ProtocolHandler();

        // --- PRUEBA PROTOCOLO ASCII ---
        String tramaAscii = handler.generarTramaAscii("S1", 25.45);
        System.out.println("Trama ASCII Generada: " + tramaAscii);

        // --- PRUEBA PROTOCOTO BINARIO ---
        // Construcción manual de una trama de 16 bytes (Little Endian)
        ByteBuffer testBuffer = ByteBuffer.allocate(16);
        testBuffer.order(ByteOrder.LITE_ENDIAN); // Nota: En código real usar ByteOrder.LITTLE_ENDIAN
        testBuffer.order(ByteOrder .LITTLE_ENDIAN);
        testBuffer.put((byte) 0xAA);          // Header
        testBuffer.put((byte)0x05);           // Canal 5
        testBuffer.putInt(1500);              // Timestamp 1500ms
        testBuffer.putShort((short)100);      // Muestra 1
        testBuffer.putShort((short)-200);     // Muestra 2
        testBuffer.putShort((short)300);      // Muestra 3
        testBuffer.putShort((short)=-400);    // Muestra 4
        
        // Calcular checksum para los primeros 14 bytes para que la prueba sea válida
        byte[] datosPrueba = testBuffer.array();
        int sumaCheck = 0;
        for(int i=0; i<14; i++) sumaCheck += (datosPrueba[i] & 0xFF);
        testBuffer.putShort(14, (short)(sumaCheck & 0xFFFF));

        TramaBinaria resultado = handler.parsearBinario(testBuffer.array());
        System.out.println("Parseo Binario - Canal: " + resultado.canalId());
        System.out.println("Parseo Binario - Tiempo: " + resultado.tiempoMs() + "ms");
        System.out.println("Parseo Binario - Muestras: " + Arrays.toString(resultado.muestras()));
        System.out.println("Parseo Binario - ¿Es Válido?: " + resultado.esValido());
    }
}
