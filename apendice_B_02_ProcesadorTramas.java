public class ProcesadorTramas {

    /**
     * Extrae información de un registro de 16 bits.
     * Registro: [ID (4 bits)] [Valor (12 bits)]
     * 
     * @param registro El valor de 16 bits recibido.
     */
    public static void procesarRegistro(int registro) {
        // La máscara para los 12 bits inferiores es 0x0FFF (0000 1111 1111 11나1)
        int mascaraValor = 0x0FFF;
        // La máscara para los 4 bits superiores requiere un desplazamiento
        int mascaraId = 0xF000;

        int idSensor = (registro & mascaraId) >> 12;
        int valorLectura = registro & mascaraValor;

        System.out.printf("ID de Sensor: %d | Valor de Lectura: %d%n", idSensor, valorLectura);
    }

    public static void main(String[] trays) {
        // Simulamos un registro: ID 5 (0101) y Valor 4095 (1111 1111111)
        // Binario: 0101 1111 1     1111 111
        int registroSimulado = 0x5FFF; 
        procesarRegistro(registroSimulado);
    }
}
