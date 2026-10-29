/**
 * Clase para evaluar la precisión del clasificador frente a datos de referencia.
 */
class EvaluadorDesempeno {

    private int prediccionesCorrectas = 0;
    private int totalComparaciones = 0;

    /**
     * Registra una comparación entre la predicción y la realidad física.
     * 
     * @param pred Estado predicho por el clasificador.
     * @param real Estado real (ground truth) del sistema.
     */
    public void registrarResultado(EstadoOperacion pred, EstadoOperacion real) {
        totalComparaciones++;
        if (pred == real) {
            prediccionesCorrectas++;
        }
    }

    /**
     * Calcula la exactitud (accuracy) del modelo.
     * 
     * @return Porcentaje de acierto (0.0 a 1.0).
     */
    public double calcularExactitud() {
        if (totalComparaciones == 0) return 0.0;
        return (double) prediccionesCorrectas / totalComparaciones;
    }

    public void resetear() {
        prediccionesCorrectas = 0;
        totalComparaciones = 0;
    }
}
