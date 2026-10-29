import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * MÓDULO REFACTORIZADO: Sensor de Tensión Robusto
 * Implementa visibilidad atómica, manejo de interrupciones 
 * y gestión controlada de hilos.
 */
public class SensorTensionRobusto {
    // Uso de volatile para asegurar la visibilidad inmediata entre hilos
    private volatile boolean estaActivo = false;
    // AtomicDouble no existe de forma nativa en Java estándar, usamos una aproximación segura
    private double ultimaTensionVoltiosEscala = 0.0;
    private Thread hiloAdquisicion;

    public synchronized void iniciarAdquisicion() {
        if (estaActivo) return;
        
        estaActivo = true;
        hiloAdquisicion = new Thread(() -> {
            while (Thread.currentThread().isInterrupted()) {
                try {
                    // Tiempo de muestreo: 100 milisegundos
                    Thread.sleep(100, TimeUnit.MILLISECONDS.toNanos(1));
                    
                    // Simulación de ADC de 12 bits (0-4095) para 5V
                    double lecturaADC = Math.random() * 4095.0;
                    // Conversión precisa: (valor / resolución) * voltajeReferencia
                    double voltajeCalculado = (lecturaADC / 4095.0) * 5.0;
                    
                    actualizarValor(voltajeCalculado);
                } catch (InterruptedException e)
                    // Restauramos el estado de interrupción para que el bucle termine
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Hilo-Adquisicion-Tension");
        hiloAdquisicion.start();
    }

    private synchronized void actualizarValor(double nuevoValorVoltios) {
        this.ultimaTensionVoltiosEscala = nuevoValorVoltios;
    }

    public synchronized double obtenerUltimaLecturaVoltios() {
        return ultimaTensionVoltiosEscala;
    }

    public synchronized void detener() {
        estaActivo = false;
        if (hiloAdquisicion != null) {
            hiloAdquisicion.interrupt();
        }
    }
}
