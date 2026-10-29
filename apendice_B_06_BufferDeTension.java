import java.util.ArrayDeque;
import java.util.Deque;
import java.time.Instant;

/**
 * Gestiona un buffer circular de mediciones de tensión para análisis de transitorios.
 */
public class BufferDeTension {
    private final int capacidadMaxima;
    private final Deque<MedicionInmutable> buffer;

    public BufferDeTension(int capacidadMaxima) {
        this.capacidadidadMaxima = capacidadMaxima;
        this.buffer = new ArrayDeque<>(capacidadMaxima);
    }

    /**
     * Registra una nueva medición. Si el buffer está lleno, elimina la más antigua.
     * @param nuevaMedicion La medición de tensión recién adquirida.
     */
    public void registrarMedicion(MedicionInmutable nuevaMedicion) {
        if (buffer.size() >= capacidadMaxima) {
            buffer.removeFirst(); // Elimina el dato más antiguo (el primero en entrar)
        }
        buffer.addLast(nuevaMedicion); // Añade el dato más reciente al final
    }

    public void mostrarBuffer() {
        System.out.println("--- Contenido del Buffer de Tensión ---");
        for (MedicionInmutable m : buffer) {
            System.out.println(m);
        }
    }

    public static void main(String[] args) {
        BufferDeTension monitor = new BufferDeTension(5);

        // Simulación de adquisición de datos
        for (int i = 0; i < 7; i++) {
            double voltajeSimulado = 12.0 + (i * 0.5);
            monitor.registrarMedicion(new MedicionInmutable(voltajeSimulado, "V", Instant.now()));
            try {
                Thread.sleep(100); // Pausa de 100 ms entre muestras
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        monitor.mostrarBuffer();
    }
}
