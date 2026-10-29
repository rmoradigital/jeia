import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de la gestión y monitoreo de múltiples sensores.
 * Utiliza polimorfismo para interactuar con diferentes implementaciones de sensores.
 */
public class GestorMonitoreo {
    private final List<SensorLectura> sensores;
    private final double limiteCriticoCelsius;

    public GestorMonitoreo(double limiteCriticoCelsius) {
        this.sensores = new ArrayList<>();
        this.limiteCriticoCelsius = limiteCriticoCelsius;
    }

    /**
     * Agregación: Añade un sensor al sistema. El sensor puede existir 
     * fuera del gestor, pero el gestor lo gestiona.
     */
    public void registrarSensor(SensorLectura sensor) {
        sensores.add(sensor);
    }

    /**
     * Ejecuta un ciclo de monitoreo sobre todos los sensores registrados.
     */
    public void ejecutarCicloMonitoreo() {
        System.out.println("--- Iniciando Ciclo de Monitoreo ---");
        for (SensorLectura sensor : sensores) {
            try {
                double valor = sensor.leerTemperaturaCelsius();
                System.out.printf("Sensor [%s]: %.2f C", 
                                  sensor.getIdentificador(), valor);
                
                if (valor > limiteCriticoCelsius) {
                    System.out.print(" -> [ALERTA: SOBRECALENTAMIENTO]");
                }
                System.out.println();

            } catch (Exception e) {
                // Manejo de excepciones: El sistema no se detiene si un sensor falla
                System.err.println("Error en sensor [" + sensor.getIdentificador() + "]: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Configuración del sistema
        GestorMonitoreo gestor = new GestorMonitoreo(28.0);

        // 1. Escenario de Simulación:
        // Creamos un sensor que oscila entre 24 y 26 grados a 0.1 Hz
        SensorLectura simulador = new SensorSimuladoSenoidal("SIM-01", 25.0, 1.0, 0.1);
        gestor.registrarSensor(simulador);

        // 2. Escenario de Hardware Real:
        // Creamos un sensor conectado al puerto COM3
        SensorHardwareSerialStub sensorReal = new SensorHardwareSerialStub("COM3", "HW-TEMP-01");
        gestor.registrarSensor(sensorReal);

        // Bucle de ejecución para observar la conmutación y el comportamiento
        for (int i = 0; i < 10; i++) {
            gestor.ejecutarCicloMonitoreo();
            Thread.sleep(1000); // Espera de 1000 ms entre muestras
        }
    }
}
