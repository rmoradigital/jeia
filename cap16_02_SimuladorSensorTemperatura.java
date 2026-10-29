import java.util.Random;

/**
 * Simula la adquisición de datos de un sensor térmico.
 */
class SimuladorSensorTemperatura {
    private final Random generadorAleatorio = new Random();
    private double temperaturaActualCelsius = 60.0;
    private final double temperaturaAmbienteCelsius = 25.0;

    /**
     * Simula la lectura de una muestra con ruido térmico.
     * @return Temperatura en grados Celsius.
     */
    public double leerMuestraCelsius() {
        // Simulamos una deriva térmica gradual hacia arriba
        temperaturaActualCelsius += 0.1 + (generadorAleatorio.nextDouble() * 0.2);
        // Añadimos ruido aleatorio (simulando ruido blanco de baja amplitud)
        double ruido = (generadorAleatorio.nextDouble() - 0.5) * 0.5;
        return temperaturaActualCelsius + ruido;
    }
}
