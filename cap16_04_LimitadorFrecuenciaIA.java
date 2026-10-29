import java.time.Instant;

/**
 * Implementa un mecanismo de control de flujo para evitar peticiones
asíncronas excesivas al servicio de IA.
 * Utiliza un enfoque de ventana de tiempo simple para garantizar
 * un intervalo mínimo entre solicitudes.
 */
public class LimitadorFrecuenciaIA {
    private final long intervaloMinimoMs;
    private long ultimaPeticionTimestampMs;

    /**
     * @param peticionesPorSegundo Frecuencia máxima permitida para la IA.
     */
    public LimitadorFrecuenciaIA(double peticionesPorSegundo) {
        this.intervaloMinimoMs = (long) (1000.0 / peticionesPorSegundo);
        this.ultimaPeticionTimestampMs = 0;
    }

    /**
     * Verifica si es posible realizar una nueva petición a la IA.
     * @return true si ha pasado suficiente tiempo; false de lo contrario.
     */
    public boolean puedeSolicitar() {
        long ahora = System.currentTimeMillis();
        if (ahora - ultimaPeticionTimestampMs >= intervaloMinimoMs) {
            ultimaPeticionTimestampMs = ahora;
            return true;
        }
        return false;
    }
}
