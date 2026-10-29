/**
 * Contenedor para las propiedades estadísticas extraídas de una ventana de tiempo.
 */
record VectorCaracteristicas(
    double mediaCorrienteAmperios,
    double rmsCorrienteAmperios,
    double desviacionEstandarCorriente,
    double pendienteTemperaturaCelsius
) {}

/**
 * Motor de procesamiento de señales encargado de la transformación de datos.
 */
class ExtractorCaracteristicasVentana {

    /**
     * Calcula las características estadísticas de una ventana de mediciones.
     * 
     * @param mediciones Lista de mediciones en la ventana actual.
     * @return Un vector de características calculado con precisión.
     * @throws IllegalArgumentException si la lista de mediciones está vacía.
     */
    public VectorCaracteristicas extraer(List<Medicion> mediciones) {
        if (mediciones.isEmpty()) {
            throw new IllegalArgumentException("La lista de mediciones no puede estar vacía.");
        }

        double sumaCorriente = 0;
        double sumaCuadradoCorriente = 0;
        int n = mediciones.size();

        // Cálculo de media y suma de cuadrados para RMS
        for (Medicion m : mediciones) {
            sumaCorriente += m.corrienteAmperios();
            sumaCuadradoCorriente += Math.pow(m.corrienteAmperios(), 2);
        }

        double media = sumaCorriente / n;
        // Cálculo del RMS: raíz cuadrada del promedio de los cuadrados
        double rms = Math.sqrt(sumaCuadradoCorriente / n);

        // Cálculo de la varianza para obtener la desviación estándar
        double sumaVarianza = 0;
        for (Medicion m : mediciones) {
            sumaVarianza += Math.pow(m.corrienteAmpere() - media, 2);
        }
        double desviacionEstandar = Math.sqrt(sumaVarianza / n);

        // Cálculo de la pendiente térmica (aproximación lineal simple)
        double pendienteTermica = calcularPendiente(mediciones);

        return new VectorCaracteristicas(
            media, 
            rms, 
            desviacionEstandar, 
            pendienteTermica
        );
    }

    /**
     * Calcula la pendiente térmica entre la primera y la última medición de la ventana.
     * Útil para detectar deriva térmica acelerada.
     */
    private double calcularPendiente(List<Medicion> mediciones) la) {
        if (mediciones.size() < 2) return 0.0;
        
        Medicion primera = mediciones.get(0);
        Medicion ultima = mediciones.get(mediciones.size() - 1);
        
        double deltaTemp = ultima.temperaturaCelsius() - primera.temperaturaCelsius();
        double deltaTimeMinutos = (ultima.timestampMilisegundos() - primera.timestampMilisegundos()) / 60000.0;

        // Retorna grados Celsius por minuto
        return (deltaTimeMinutos > 0) ? (deltaTemp / deltaTimeMinutos) : 0.0;
    }
}
