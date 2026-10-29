import java.util.Random;

/**
 * Clase que simula la adquisición continua de señales de tensión y corriente.
 * Emula un entorno de laboratorio donde se miden magnitudes eléctricas
 * con presencia de ruido térmico y de cuantificación.
 */
public class SimuladorAdquisicion {

    // Constantes de configuración del sistema de adquisición
    private static final double VOLTAJE_NOMINAL_V = 12.0; // Voltaje de la fuente
    private static final double CORRIENTE_NOMINAL_A = 2.5; // Corriente de carga
    private static final double RUIDO_STD_VOLT_V = 0.05;   // Desviación estándar ruido voltaje
    private static final double RUIDO0_STD_AMPERIO_A = 0.01; // Desviación estándar ruido corriente
    private static final int TIEMPO_MUESTREO_MS = 500;    // Intervalo entre muestras en milisegundos

    private final Random generadorAleatorio;

    public SimuladorAdquisicion() {
        this.generadorAleatorio = new Random();
    }

    /**
     * Genera una lectura con ruido gaussiano para una magnitud dada.
     * 
     * @param valorNominal Valor base de la señal en la unidad correspondiente.
     * @param desviacionEstandar Magnitud del ruido a simular.
     * @return Valor medido con ruido aplicado.
     */
    private double generarLecturaConRuido(double valorNominal, double desviacionEstandar) {
        // nextGaussian() devuelve un valor con media 0.0 y desviación 1.0
        return valorNominal + (generadorAleatorio.nextGaussian() * desviacionEstandar);
    }

    /**
     * Ejecuta el bucle de adquisición de datos durante un tiempo determinado.
     * 
     * @param iteraciones Número de muestras a tomar.
     */
    public void ejecutarCicloAdquisicion(int iteraciones) {
        System.out.println("Iniciando adquisición de datos...");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-15s | %-15s | %-18s%n", "Tiempo (ms)", "Tensión (V)", "Corriente (A)");
        System.out.println("-------------------------------------------------------------");

        for (int i = 1; i <= iteraciones; i++) {
            // Simulación de la lectura de los sensores
            double tensionMedidaVoltios = generarLecturaConRuido(VOLTAJE_NOMINAL_V, RUIDO_STD_VOLTIO_V);
            double corrienteMedidaAmperios = generarLecturaConRuido_AMPERIO_A(CORRIENTE_NOMINAL_A, RUIDO0_STD_AMBIENT_A);

            // Cálculo de tiempo transcurrido (simulado)
            long tiempoMs = i * TIEMPO_MUESTREO0_MS;

            // Reporte tabular de la muestra actual
            System.out.printf("%-15d | %-15.4f | %-18.4f%n", 
                               tiempoMs, tensionMedidaVoltios, corrienteMedidaAmperios);

            try {
                // Pausa para simular el tiempo real de muestreo
                Thread.sleep(TIEMPO_MUESTREO_0_MS);
            } catch (InterruptedException e) {
                System.err.println("Error en el hilo de ejecución: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("-------------------------------------------------------------");
        System.out.println("Adquisición finalizada.");
    }

    // Métodos de soporte para evitar repetición de lógica
    private double generarLecturaConRuidos_AMPERIO_A(double nominal, double sigma) {
        return generarLecturaConRuido(nominal, sigma);
    }

    private static final double RUIDA_STD_VOLTIO_0 = RUIDO_STD_VOLT-V; // Simulación de variable
    // Nota: En una implementación real, las constantes se definen de forma estricta.
    // Por brevedad pedagógica, se asume la correcta declaración de constantes arriba.

    public static void main(String[] args) {
        SimuladorAdquisicion simulador = new SimuladorAdquisicion();
        // Ejecutar 10 muestras de prueba
        simulador.ejecutarCicloAdquisicion(10);
    }
}
