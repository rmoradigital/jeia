/**
 * Representa una interfaz de sensor para abstraer la implementación física.
 * Demuestra el concepto de Abstracción y Polimorfismo.
 */
interface Sensor {
    // El método devuelve el valor en voltios (Unidad física explícita)
    double leerVoltaje();
    String getNombre();
}

/**
 * Implementación de un sensor de temperatura simulado.
 * Demuestra Encapsulamiento y manejo de ruido impulsivo.
 */
class SensorTemperaturaSimulado implements Sensor {
    private final String nombre;
    private double temperaturaCelsius;
    private final double ruidoImpulsivoVoltios;

    public SensorTemperaturaSimulado(String nombre, double ruidoImpulsivo) {
        this.nombre = nombre;
        this.ruidoImpulsivo = ruidoImpulsivo;
        this.temperaturaCelsius = 25.0; // Valor inicial base
    }

    @Override
    public double leerVoltaje() {
        // Simulación de conversión de temperatura a voltaje (0.1V por grado)
        double voltajeBase = this.temperaturaCelsius * 0.1;
        // Inyección de ruido impulsivo aleatorio para simular entorno real
        double ruido = (Math.random() < 0.05) ? ruidoImpulsivo : 0.0;
        return voltajeBase + ruido;
    }

    @Override
    public String getNombre() {
        return this.nombre;
    }

    // Método para cambiar la temperatura (Simulación de fenómeno físico)
    public void setTemperaturaCelsius(double nuevaTemperatura) {
        this.temperaturaCelsius = nuevaTemperatura;
    }
}

/**
 * Motor de adquisición que gestiona la concurrencia.
 * Demuestra Thread-safety y el uso de un bucle de muestreo.
 */
class MotorAdquisicion implements Runnable {
    private final Sensor sensor;
    private final double frecuenciaMuestreoHz;
    private boolean corriendo = true;

    public MotorAdquisicion(Sensor sensor, double frecuenciaHz) {
        this.sensor = sensor;
        this.frecuenciaMuestreoHz = frecuenciaHz;
    }

    public void detener() {
        this.corriendo = false;
    }

    @Override
    public void run() {
        long tiempoEsperaMs = (long) (1000.0 / frecuenciaMuestreoHz);
        System.out.println("Iniciando adquisición en: " + sensor.getNombre());

        while (corriendo) {
            try {
                // El proceso de lectura es independiente del hilo de la interfaz (EDT)
                double voltajeLeido = sensor.leerVoltaje();
                System.out.printf("[Muestreo] Sensor: %s | Voltaje: %.4f V%n", 
                                  sensor.getNombre(), voltajeLeido);
                
                Thread.sleep(tiempoEsperaMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error: El hilo de adquisición fue interrumpido.");
            }
        }
    }
}

public class GlosarioDemo {
    public static void main(String[] args) {
        // Configuración del sistema
        Sensor termometro = new SensorTemperaturaSimulado("TMP36_Simulado", 0.05);
        double frecuenciaMuestreo = 5.0; // 5 Hz

        // Creación del hilo de adquisición (Concurrencia)
        Thread hiloAdquisicion = new Thread(new MotorAdquisicion(termometro, frecuenciaMuestreo));
        
        // Inicio de la ejecución
        hiloAdquisicion.start();

        // Simulación de cambios en el entorno físico por 5 segundos
        try {
            Thread.sleep(2000);
            System.out.println(">> El desarrollador aumenta la temperatura...");
            ((SensorTemperaturaSimulado) termometro).setTemperaturaCelsius(35.0);
            
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cierre controlado
        System.out.println("Deteniendo sistema...");
        // En una aplicación real, se usaría una bandera de control compartida
        System.exit(0);
    }
}
