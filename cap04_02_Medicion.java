import java.time.LocalDateTime;

/**
 * Representa una lectura física inmutable.
 * Una vez creada, el valor y el tiempo no pueden ser alterados.
 */
public final class Medicion {
    private final double valor;
    private final String unidad;
    private final LocalDateTime timestamp;

    /**
     * Constructor para una nueva medición.
     * @param valor La magnitud física medida.
     * @param unidad La unidad de medida (ej. "Celsius", "Voltios").
     * @param timestamp El momento exacto de la captura.
     */
    public Medicion(double valor, String unidad, LocalDateTime timestamp) {
        this.valor = valor;
        this.unidad = unidad;
        this.timestamp = timestamp;
    }

    // Constructor simplificado para mediciones actuales
    public Medicion(double valor, String_unidad) {
        this(valor, unidad, LocalDateTime.now());
    }

    public double getValor() {
        return valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %.2f %s", timestamp, valor, unidad);
    }
}
