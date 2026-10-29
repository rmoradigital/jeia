import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Proporciona un mecanismo de almacenamiento temporal de diagnósticos
 * para evitar redundancia en las llamadas a la IA.
 */
public class CachéDiagnósticos {
    // La clave es el hash del resumen estadístico para identificar estados idénticos
    private final Map<Integer, DiagnosticoIA> cache = new ConcurrentHashMap<>();
    private final int tamañoMaximo;

    public CachéDiagnósticos(int tamañoMaximo) {
        this.tamañoMaximo = tamañoMaximo;
    }

    /**
     * Intenta recuperar un diagnóstico de la caché basándose en el contenido
     * del resumen estadístico.
     * @param resumen El resumen de la ventana de datos actual.
     * @return El diagnóstico almacenado o null si no existe.
     */
    public DiagnosticoIA obtenerSiExiste(ResumenEstadistico resumen) {
        return cache.get(resumen.hashCode());
    }

    /**
     * Almacena un diagnóstico en la caché. Si la caché supera el tamaño máximo,
     * se realiza una limpieza básica (estratégia FIFO simplificada).
     * @param resumen Resumen que genera el diagnóstico.
     * @param diagnostico Resultado de la inferencia.
     */
    public void almacenar(ResumenEstadistico resumen, DiagnosticoIA diagnostico) {
        if (cache.size() >= tamañoMaximo) {
            // Limpieza rudimentaria para evitar fuga de memoria
            cache.clear(); 
        }
        cache.put(resumen.hashCode(), diagnostico);
    }
}
