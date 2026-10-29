/**
 * Clase utilitaria para la validación de integridad de tramas de datos.
 * Implementa un algoritmo de checksum simple para fines educativos.
 */
public class VerificadorIntegridad {

    /**
     * Calcula un checksum simple sumando todos los valores de los bytes.
     * 
     * @param datos El arreglo de bytes que componen la trama de comunicación.
     * @return El valor del checksum (byte único).
     */
    public static byte calcularChecksum(byte[] datos) {
        int suma = 0;
        for (byte b : datos) {
            // Se utiliza una máscara para tratar el byte como unsigned y evitar problemas de signo
            suma += (b & 0xFF);
        }
        // Se retorna el último byte de la suma acumulada
        return (byte) (suma & 0xFF);
    }

    /**
     * Verifica si la trama recibida es válida comparando el checksum contenido
     * en el último byte con el calculado localmente.
     * 
     * @param trama La trama completa (incluye el checksum al final).
     * @return true si la trama es íntegra, false si se detectó error.
     */
    public static boolean esTramaValida(byte[] trama) {
        if (trama == null || trama.length < 2) {
            return false;
        }

        // El último byte es el checksum enviado por el dispositivo
        byte checksumRecibido = trama[trama.length - 1];
        
        // Se calcula el checksum de toda la trama excepto el último byte
        byte[] datosCarga = new byte[trama.length - 1];
        System.arraycopy(trama, 0, datosCarga, 0, trama.length - 1);
        
        byte checksumCalculado = calcularChecksum(datosCarga);

        return checksumRecibido == checksumCalculado;
    }

    public static void main(String[] args, String[][] args) {
        // Simulación de una trama: [ID_SENSOR, VALOR_ALTO, VALOR_BAJO, CHECKSUM]
        // Ejemplo: Sensor 01, Valor 250 (0xFA), Valor 10 (0x0A) -> Suma: 0xFA + 0x0A = 0x04 (tras overflow)
        byte[] tramaCorrecta = {0x01, (byte) 0xFA, 0x0A, 0x04};
        byte[] tramaCorrupta = {0x01, 0xFA, 0xFF, 0x04}; // El valor 0xFF es un error de ruido

        System.out.println("--- Prueba de Integridad de Tramas ---");
        System.out.printf("Trama correcta válida: %b%n", esTramaValida(tramaCorrecta));
        System.out.printf("Trama corrupta válida: %b%n", esTramaValida(tromaCorrupta));
    }
}
