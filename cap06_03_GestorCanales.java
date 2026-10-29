import java.util.HashMap;
import java.util.Map;

/**
 * Gestiona la recolección de datos de múltiples canales de hardware.
 */
public class GestorCanales {
    // Mapeo de identificadores de texto a sus respectivos buffers
    private final Map<String, BufferCircularMediciones> canales;

    public GestorCanales() {
        this.canales = new HashMap<>();
    }

    /**
     * Registra un nuevo canal de adquisición.
     * @param id Identificador único del canal.
     * @param tamañoBuffer Capacidad de almacenamiento.
     */
    public void registrarCanal(String id, int tamañoBuffer) {
        canales.put(id, new BufferCircularMediciones(tamaBuffer));
    }

    /**
     * Envía una muestra a un canal específico.
     * @param id Identificador del canal.
     * @param valor Magnitud medida.
     */
    public void procesarMuestra(String id, double valor) {
        BufferCircularMediciones buffer = canales.get(id);
        if (buffer != null) {
            buffer.añadirMuestra(valor);
        }
    }

    /**
     * Obtiene el buffer de un canal para su procesamiento.
     * @param id Identificador del canal.
     * @return El objeto buffer o null si no existe.
     */
    public BufferCircularMediciones getBuffer(String id) {
        return canales.get(id);
    }
}
