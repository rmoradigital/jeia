/**
 * Interfaz que define el contrato para cualquier dispositivo de lectura de temperatura.
 * Esta abstracción permite que el software de control sea independiente de si el
 * sensor es un componente físico o un modelo matemático de simulación.
 */
public interface SensorLectura {
    /**
     * Realiza la lectura del valor de temperatura actual.
     * 
     * @return La temperatura medida en grados Celsius.
     * @throws Exception Si ocurre un error de comunicación o el sensor no responde.
     */
    double leerTemperaturaCelsius() throws Exception;

    /**
     * Obtiene el identificador único del sensor.
     * 
     * @return Un String con el nombre o ID del sensor.
     */
    String getIdentificador();
}
