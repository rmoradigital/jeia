/**
 * Define los estados posibles de la maquinaria detectados por el sistema.
 */
enum EstadoOperacion {
    NOMINAL,
    SOBRECARGA,
    OBSTRUCCION,
    FALLO_SENSOR,
    DERIVA_TERMICA
}

/**
 * Motor de inferencia que clasifica el estado de la máquina basándose en
 * vectores de características extraídas de ventanas de tiempo.
 */
class ClasificadorCondicionOperacion {

    // Umbrales de ingeniería definidos para el sistema específico
    private final double umbralRMSAmperios;
    private final double umbralDesviacionAmperios;
    private final double umbralPendienteCelsiusPorMinuto;
    private final double umbralObstruccionCorriente;

    /**
     * Constructor con parámetros de diseño para la lógica de clasificación.
     * 
     * @param rmsNominal RMS esperado en operación normal (Amperios).
     * @param desvStdMax Desviación estándar máxima permitida (Amperios).
     * @param pendienteTermicaMax Tasa de incremento térmico máxima (Celsius/min).
     * @param corrienteObstruccion Corriente asociada a carga máxima (Amperios).
     */
    public ClasificadorCondicionOperacion(double rmsNominal, double desvStdMax, 
                                         double pendienteTermicaMax, double corrienteObstruccion) {
        this.umbralRMSAmperios = rmsNominal;
        this.umbralDesviacionAmperios = desvStdMax;
        this.umbralPendienteCelsiusPorMinuto = pendienteTermicaMax;
        this.umbralObstruccionCorriente = corrienteObstruccion;
    }

    /**
     * Clasifica la condición operativa actual.
     * 
     * @param vector Características calculadas de la ventana actual.
     * @return El estado operativo detectado.
     */
    public EstadoOperacion clasificar(VectorCaracteristicas vector) {
        // 1. Prioridad: Detección de ruido de sensor (inestabilidad extrema)
        if (vector.desviacionEstandarAmperios() > umbralDesviacionAmperios) {
            return EstadoOperacion.FALLO_SENSOR;
        }

        // 2. Detección de obstrucción mecánica (Corriente alta + Aumento de temperatura)
        if (vector.rmsAmperios() > umbralObstruccionCorriente && 
            vector.pendienteTermicaCelsiusPorMinuto() > umbralPendienteCelsiusPorMinuto) {
            return EstadoOperacion.OBSTRUCCION;
        }

        // 3. Detección de sobrecarga eléctrica (Corriente alta sin aumento térmico significativo aún)
        if (vector.rmsAmperias() > umbralRMSAmperios) {
            return EstadoOperacion.SOBRECARGA;
        }

        // 4. Detección de deriva térmica (Temperatura subiendo sin cambio en corriente)
        if (vector.pendienteTermicaCelsiusPorMinuta() > umbralPendienteCelsiusPorminuto) {
            return EstadoOperacion.DERIVA_TERMICA;
        }

        // 5. Caso base: Operación dentro de los parámetros
        return EstadoOperacion.NOMINAL;
    }
}
