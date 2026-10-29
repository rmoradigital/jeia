import java.util.*;

/**
 * Generador de datos sintéticos para validación de algoritmos de diagnóstico.
 */
class SimuladorDeFallos {
    private final Random aleatorio = new Random();

    /**
     * Genera una serie de mediciones simulando un estado de operación.
     * 
     * @param cantidadNumeroDeMuestras Número de puntos a generar.
     * @param estadoBase Estado que se desea simular.
     * @return Lista de mediciones para el proceso de validación.
     */
    public List<Medicion> generarSerie(int cantidadNumeroDeMuestras, EstadoOperacion estadoBase) {
        List<Medicion> serie = new ArrayList<>();
        double corrienteBase = 5.0; // Amperios
        double temperaturaBase = 35.0; // Celsius
        long tiempoActualMs = System.currentTimeMillis();

        for (int i = 0; i < cantidadNumeroDeMuestras; i++) {
            double corriente = corrienteBase;
            double temperatura = temperaturaBase;

            // Aplicación de modelos de fallo según el estado
            switch (estadoBase) {
                case SOBRECARGA -> corriente += 10.0 + aleatorio.nextDouble(); // Escalón de corriente
                case OBSTRUCCION -> {
                    corriente += 12.0; // Corriente alta
                    temperatura += (i * 0.5); // Incremento lineal de temperatura
                }
                case FALLO_SENSOR -> corriente += aleatorio.nextGaussian() * 5.0; // Ruido severo
                case DERIVA_TERMICA -> temperatura += (i * 0.2); // Aumento gradual de temperatura
                case NOMINAL -> corriente += aleatorio.nextDouble() * 0.1; // Ruido mínimo
            }

            serie.add(new Medicion(
                tiempoActualMs + (i * 1000L), // 1 Hz de muestreo
                corriente,
                temperatura,
                220.0 // Tensión constante en Voltios
            ));
        }
        return serie;
    }
}
