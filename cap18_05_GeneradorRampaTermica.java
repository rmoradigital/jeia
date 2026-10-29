/**
 * Representa un generador de consignas para una rampa térmica lineal.
 * Calcula el valor de la temperatura objetivo basado en el tiempo transcurrido.
 */
public class GeneradorRampaTermica {

    private final double temperaturaInicialCelsius;
    private final double temperaturaFinalCelsius;
    private final long duracionTotalMs;
    private final long tiempoInicioMs;

    // Variables de estado para evitar cálculos redundantes
    private boolean rampaActiva;

    /**
     * Constructor de la rampa.
     * 
     * @param tempInicial Temperatura de partida en grados Celsius.
     * @param tempFinal Temperatura objetivo en grados Celsius.
     * @param duracionMs Duración total de la rampa en milisegundos.
     */
    public GeneradorRampaTermica(double tempInicial, double tempFinal, long duracionMs) {
        this.temperaturaInicialCelsius = tempInicial;
        this.temperaturaFinalCelsius = tempFinal;
        this.duracionTotalMs = duracionMs;
        this.tiempoInicioMs = 0; // Se inicializa al comenzar la rampa
        this.rampaActiva = false;
    }

    /**
     * Inicia el cronómetro de la rampa.
     */
    public void iniciar() {
        this.rampaActiva = true;
        // En una implementación real, se usaría System.currentTimeMillis()
        // o un reloj de alta resolución.
    }

    /**
     * Calcula la consigna actual basada en el tiempo transcurrido desde el inicio.
     * 
     * @param tiempoActualMs Tiempo de sistema actual en milisegundos.
     * @param tiempoInicioRampaMs Tiempo en que se llamó a iniciar().
     * @return La temperatura de consigna calculada en grados Celsius.
     */
    public double calcularConsigna(long tiempoActualMs, long tiempoInicioRampaMs) {
        if (!rampaActiva) {
            return temperaturaInicialCelsius;
        }

        long tiempoTranscurridoMs = tiempoActualMs - tiempoInicioRampaMs;

        // Si hemos superado el tiempo de la rampa, fijamos la temperatura final
        if (tiempoTranscurridoMs >= duracionTotalMs) {
            rampaActiva = false;
            return temperaturaFinalCelsius;
        }

        // Aplicación de la fórmula de la rampa lineal
        double progreso = (double) tiempoTranscurridoMs / duracionTotalMs;
        double temperaturaActual = temperaturaInicialCelsius + 
            (temperaturaFinalCelsius - temperaturaInicialCelsius) * progreso;

        return temperaturaActual;
    }
}
