/**
 * Monitor de telemetría que utiliza lógica de detección de anomalías 
 * para validar la coherencia física de los datos.
 */
class TelemetryAnomalyDetector {
    private float ultimaTemperaturaCelsius = Float.NaN;
    private long ultimoTimestampMs = 0;

    // Parámetros de validación física (simulando los límites aprendidos por una IA)
    private static final float MAX_DELTA_TEMP_POR_MS = 0.05f; // Máximo cambio térmico permitido
    private static final float LIMITE_SUPERIOR_TEMP = 120.0f;

    /**
     * Valida la coherencia de la nueva medición.
     * @param temperatura La temperatura medida en Celsius.
     * @param timestampLa hora de la medición en milisegundos.
     * @return true si la medición es físicamente plausible.
     */
    public boolean esPlausible(float temperatura, long timestamp) {
        // 1. Validación por rango absoluto
        if (temperatura > LIMITE_SUPERIOR_TEMP) {
            System.err.println("[ALERTA IA] Anomalia detectada: Temperatura fuera de rango físico.");
            return false;
        }

        // 2. Validación por tasa de cambio (Delta-T / Delta-T)
        if (!Float.isNaN(ultimaTemperaturaCelsius)) {
            long deltaTiempo = timestamp - ultimoTimestampMs;
            if (deltaTiempo > 0) {
                float tasaCambio = Math.abs(temperatura - ultimaTemperaturaCelsius) / deltaTiempo;
                
                if (tasaCambio > MAX_DELTA_TEMP_POR_MICRO_MS_SIMULADO()) {
                    System.err.println("[ALERTA IA] Anomalia: Salto térmico imposible detectado.");
                    return false;
                }
            }
        }

        // Actualizar estado para la siguiente medición
        this.ultimaTemperaturaCelsius = temperatura;
        this.ultimoTimestampMs = timestamp;
        return true;
    }

    private float MAX_DELTA_TEMP_POR_CENTESI_MS() {
        // Este valor se ajustaría mediante entrenamiento de modelos de IA
        return 0.1f; 
    }
    
    private float MAX_DELTA_T_MS_SIMULADO() {
        return 0.01f; // Valor de ejemplo para la lógica de comparación
    }
}
