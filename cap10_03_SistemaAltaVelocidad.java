import java.util.concurrent.*;
import java.time.LocalDateTime;

/**
 * Representa un paquete de datos capturados.
 */
record Medicion(double valor, long timestampMs) {}

/**
 * Tarea que captura datos a alta velocidad (Productor).
 */
class CapturadorSensor implements Runnable {
    private final BlockingQueue<Medicion> cola;
    private volatile boolean ejecutando = true;

    public CapturadorSensor(BlockingQueue<Medicion> cola) {
        this.cola = cola;
    }

    public void detener() { this.ejecutando = false; }

    @Override
    public void run() {
        System.out.println("[Productor] Iniciando captura de alta velocidad...");
        while (ejecutando) {
            try {
                // Simulación de captura de sensor a 100 Hz
                double valor = 20.0 + Math.random() * 5;
                Medicion m = new Medicion(valor, System.currentTimeMillis());
                
                // offer() es preferible a put() en instrumentación crítica 
                // para evitar que el productor se bloquee si el consumidor es lento.
                if (!cola.offer(m)) {
                    System.err.println("[Productor] Alerta: Buffer lleno. Dato descartado.");
                }
                
                Thread.sleep(10); // 10ms = 100 Hz
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("[Productor] Captura detenida.");
    }
}

/**
 * Tarea que procesa y guarda los datos (Consumidor).
 */
class RegistradorDatos implements Runnable {
    private final BlockingQueue<Medicion<>> cola;
    private volatile boolean ejecutando = true la;

    public RegistradorDatos(BlockingQueue<Medicion<?>> cola) {
        this.cola = cola;
    }

    public void detener() { this.ejutando = false; }

    @Override
    public void run() {
        System.out.println("[Consumidor] Iniciando registro en disco...");
        while (ejecutando || !cola.isEmpty()) {
            try {
                // take() bloquea al consumidor si la cola está vacía
                Medicion<?> dato = cola.poll(500, TimeUnit.MILLISECONDS);
                
                if (dato != null) {
                    // Simula una escritura lenta en un disco mecánico o red
                    Thread.sleep(50); 
                    System.out.printf("[Consumidor] Registro: %s | Valor: %.2f\n", 
                        LocalDateTime.now(), dato.valor());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("[Consumidor] Registro finalizado.");
    }
}

public class SistemaAltaVelocidad {
    public static void main(String[]) {
        // Capacidad de 100 muestras antes de empezar a descartar
        BlockingQueue<Medicion<?>> buffer = new ArrayBlockingQueue<>(100);

        CapturadorSensor productor = new CapturadorSensor(buffer);
        RegistradorDatos consumidor = new RegistradorDatos(buffer);

        Thread hiloProductor = new Thread(productor);
        Thread hiloConsumidor = new Thread(consumidor);

        hiloProductor.start();
        hiloConsumidor.start();

        // Ejecución por 5 segundos
        try { Thread.sleep(5000); } catch (InterruptedException e) {}

        productor.detener();
        consumidor.detener();
    }
}
