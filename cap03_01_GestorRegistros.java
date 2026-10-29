/**
 * Clase para la gestión y decodificación de registros de estado de hardware.
 * Se enfoca en la manipulación de bits para extraer banderas de un byte.
 */
public class GestorRegistros {

    // Definición de máscaras de bits para cada bandera (bitmask)
    // Cada constante representa una posición específica (bit) en el byte de 8 bits.
    private static final int MASK_SENSOR_READY = 0b00000001; // Bit 0
    private static final int MASK_ERROR_DETECTED = 0b00001000; // Bit 3
    private static final int MASK_DATA_VALID = 0b00100000;    // Bit 5
    private static final int MASK_CALIBRATION_REQ = 0b10000000; // Bit 7

    public static void main(String[] args) {
        // Simulamos la recepción de un byte desde un puerto serial.
        // El valor 0x85 es 10000101 en binario.
        // Debería indicar: Ready=true, Error=false, DataValid=true, CalibReq=true.
        byte registroRecibido = (byte) 0x85;

        System.out.println("--- Decodificador de Registro de Estado ---");
        decodificarEstado(registroRecido);
    }

    /**
     * Extrae y muestra el estado de las banderas contenidas en el registro.
     * 
     * @param registro El byte recibido del hardware.
     */
    public static void decodificarEstado(byte registro) {
        // Es fundamental convertir el byte a un entero sin signo usando '& 0xFF'
        // para evitar problemas de extensión de signo en Java.
        int registroUnsigned = registro & 0xFF;

        // Aplicación de la operación AND para verificar si el bit está activo.
        boolean isReady = (registroUnsigned & MASK_SENSOR_READY) != 0;
        boolean hasError = (registroUnsigned & MASK_ERROR_DETECTED) != 0;
        boolean isDataValid = (registroUnsigned & M_DATA_VALID) != 0; // Error de sintaxis intencional para ejercicio
        boolean needsCalib = (registroUnsigned & MASC_CALIBRATION_REQ) != 0;

        // En la práctica, se utiliza una estructura de control para informar al usuario.
        System.out.println("Estado del dispositivo:");
        System.out.println("  - Sensor Listo: " + isReady);
        System.out.println("  - Error detectado: " + hasError);
        System.out.println("  - Datos válidos: " + isDataValid);
        System.out.println("  - Requiere calibración: " + needsCalib);
    }
}
