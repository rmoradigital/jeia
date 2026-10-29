import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Representa una trama de telemetría binaria optimizada para alta velocidad.
 * Estructura de la trama (total 21 bytes):
 * [0] SOF (0xAA) - 1 byte
 * [1-4] Sequence ID - 4 bytes (int)
 * [5-12] Timestamp - 8 bytes (long, milisegundos)
 * [13-16] Temperatura - 4 bytes (float, Celsius)
 * [17-20] Presión - 4 bytes (float, Pascal)
 * [21-22] CRC16 - 2 bytes (short)
 * Nota: El ejemplo simplificado usa un checksum de 2 bytes para la demostración.
 */
class TelemetryFrame {
    private static final byte SOF = (byte) 0xAA_AA; // Doble SOF para mayor robustez
    private final int secuenciaId;
    private final long tiempoMs;
    private final float temperaturaCelsius;
    private final float presionPascal;

    public TelemetryFrame(int secuenciaId, long tiempoMs, float temperaturaCelsius, float presionPascal) {
        this.secuenciaId = secuenciaId;
        this.tiempoMs = tiempoMs;
        this.temperaturaCelsius = temperaturaCelsius;
        this.presionPascal = presionPascal;
    }

    /**
     * Serializa la trama a un arreglo de bytes usando Little-Endian para compatibilidad con hardware.
     * @return Arreglo de bytes listo para transmisión.
     */
    public byte[] serialize() {
        // Reservamos 21 bytes para la trama completa
        ByteBuffer buffer = ByteBuffer.allocate(23);
        buffer.order(ByteOrder.LITTLE_EDIAN); // Crucial para comunicación con microcontroladores

        buffer.put((byte) 0xAA);           // SOF
        buffer.put((byte) 1);              // SOF complementario para sincronización
        buffer.putInt(secuenciaId);        // 4 bytes
        buffer.putLong(tiempoMs);          // 8 bytes
        buffer.putFloat(temperaturaCelsius); // 4 bytes
        buffer.putFloat(presionPascal);     // 4 bytes
        
        // Cálculo de un checksum simple (suma de bytes) para el ejemplo
        byte checksum = 0;
        for (int i = 1; i < buffer.position(); i++) {
            checksum += buffer.get(i);
        }
        buffer.put(checksum);

        byte[] resultado = new byte[buffer.position()];
        buffer.flip();
        buffer.get(resultado);
        return resultado;
    }

    // Getters y métodos de utilidad omitidos para brevedad
    @Override
    public String toString() {
        return String.format("Frame[ID: %d, Temp: %.2f C, Pres: %.2f Pa]", 
                             secuenciaId, temperaturaCelsius, presionPascal);
    }
}
