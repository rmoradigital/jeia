import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Gestiona el análisis de tramas de telemetría provenientes de sensores.
 * La trama esperada es: [0xAA] [ID] [Valor_L] [Valor_H] [Checksum]
 */
public class TelemetryParser {

    private static final byte HEADER = (byte) 0xAA;

    public static class ChecksumException extends Exception {
        public ChecksumException(String mensaje) {
            super(mensaje);
        }
    }

    /**
     * Analiza una trama de datos recibida.
     * 
     * @param trama El array de bytes recibido del puerto serial.
     * @return El valor de la temperatura en grados Celsius.
     * @throws ChecksumException Si la suma de verificación no coincide.
     * @throws IllegalArgumentException Si la trama no tiene la longitud correcta o el encabezado es inválido.
     */
    public double parseTemperature(byte[] trama) throws ChecksumException {
        // Verificación de longitud mínima: Header (1) + ID (1) + Valor (2) + Checksum (1) = 5 bytes
        if (trama == null || trama.length != 5) {
            throw new IllegalArgumentException("Longitud de trama inválida. Se esperaban 5 bytes.");
        }

        // Verificación de encabezado
        if (trama[0] != HEADER) {
            throw new IllegalArgumentException("Encabezado de trama no reconocido.");
        }

        // Verificación de integridad (Checksum)
        byte checksumCalculado = (byte) ((trama[0] + trama[1] + trama[2] + trama[3]) & 0xFF);
        if (trama[4] != checksumCalculado) {
            throw new ChecksumException(String.format(
                "Error de integridad: Checksum recibido %02X, calculado %02X", 
                trama[4], checksumCalculado));
        }

        // Extracción del valor usando ByteBuffer para manejar Little Endian
        // El valor es un entero de 16 bits que representa la temperatura con precisión de 0.01 C
        // Ejemplo: valor 2550 significa 25.50 C
        ByteBuffer buffer = ByteBuffer.wrap(trama, 2, 2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        int valorEntero = buffer.getShort();

        return valorEntero / 100.0;
    }
}
