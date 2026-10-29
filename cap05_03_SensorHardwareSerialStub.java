import java.io.IOException;

/**
 * Representación de un sensor físico conectado mediante comunicación serial.
 * Simula la latencia y los posibles fallos de conexión de hardware real.
 */
public class SensorHardwareSerialStub implements SensorLectura {
    private final String puertoSerial;
    private final String idHardware;
    private boolean conexionEstable;

    public SensorHardwareSerialStub(String puertoSerial, String idHardware) {
        this.puertoSerial = puertoSerial;
        this.idHardware = idHardware;
        // Simulamos que el hardware inicia conectado
        this.conexionEstable = true;
    }

    @Override
    public double leerTemperaturaCelsius() throws IOException {
        // Simulación de un fallo de comunicación aleatorio (típico en cables con ruido)
        if (Math.random() > 0.98) {
            conexionEstable = false;
            throw new IOException("Error de comunicación en puerto " + puertoSerial + ": Tiempo de espera agotado.");
        }

        // Simulación de lectura de un valor de un sensor físico real
        // En un sistema real, aquí se leerían bytes del puerto serie y se convertirían.
        return 25.0 + (Math.random() * 2.0); 
    }

    @Override
    public String getIdentificador() {
        return idHardware;
    }

    public void setConexionEstable(boolean estado) {
        this.conexionEstable = estado;
    }
}
