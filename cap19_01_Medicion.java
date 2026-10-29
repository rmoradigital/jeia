/**
 * Representa un punto de datos único capturado por un sensor.
 * Se incluyen unidades explícitas para evitar errores de escala.
 */
public record Medicion(
    long timestampMilisegundos,
    double corrienteAmperios,
    double temperaturaCelsius,
    double tensionVoltios
) {}

/**
 * Gestiona una ventana deslizante de mediciones para permitir
 * el análisis estadístico de una serie temporal.
 */
import java.util.LinkedList;
import java.util.List;

public class VentanaDeDatos {
    private final int capacidadMaxima;
    private final LinkedList<Medicion> buffer;

    public VentanaDeDatos(int capacidadMaxima) {
        this.capacidadidadMaxima = capacidadMaxima;
        this.buffer = new LinkedList<>();
    }

    /**
     * Añade una nueva medición al buffer. 
     * Si la capacidad se alcanza, se elimina la medición más antigua.
     * 
     * @param nuevaMedicion La medición capturada por el hardware o simulador.
     */
    public synchronized void agregarMedicion(Medicion nuevaMedicion) {
        if (buffer.size() >= capacidadMaxima) {
            buffer.removeFirst();
        }
        buffer.addLast(nuevaMedicion);
    }

    public synchronized List<Medicion> getCopiaDatos() {
        return List.copyOf(buffer);
    }

    public int tamano() {
        return buffer.size();
    }

    public boolean estaCompleta() {
        return buffer.size() >= capacidadMaxima;
    }
}
