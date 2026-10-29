import java.io.*;
import java.time.Instant;
import java.util.concurrent.*;

/**
 * Motor que gestiona la captura de múltiples sensores de forma concurrente.
 */
class MotorAdquisicionMulticanal implements Runnable {
    private final BlockingQueue<PaqueteMedicion> colaProcesamiento;
    private final String nombreArchivoCsv;
    private volatile boolean ejecutando = true;
    
    // Parámetros de simulación
    private final double frecuenciaHz = 10.0; // 10 muestras por segundo
    private double faseSenoidal = 0.0;

    public MotorAdquisicionMulticanal(BlockingQueue<PaqueteMedicion> col, String archivo) {
        this.colaProcesamiento = col;
        this.nombreArchivoCsv = archivo;
    }

    public void detener() {
        this.ejecutando = false;
    }

    @Override
    public void run() {
        // Hilo de escritura en disco (Consumidor)
        Thread hiloEscritura = new Thread(this::tareaEscritura);
        hiloEscritura.start();

        // Bucle de adquisición (Productor)
        try (Scanner scannerSimulado = new Scanner(System.in)) { // Para control manual si fuera necesario
            while (ejecutando) {
                long inicioCapturaNs = System.nanoTime();
                Instant momentoCaptura = Instant.now();

                // 1. Simulación de adquisición de sensores
                double[] tensiones = simularTension();
                double[] temperaturas = simularTemperatura();

                // 2. Empaquetado inmediato
                PaqueteMedicion paquete = new PaqueteMedicion(momentoCaptura, tensiones, temperaturas);
                
                // 3. Envío a la cola para evitar bloqueos
                if (!colaProcesamiento.offer(paquete)) {
                    // El buffer está lleno, se descarta para evitar lag en el sistema
                    System.err.println("Alerta: Buffer de procesamiento saturado. Descartando muestra.");
                }

                // 4. Control de la frecuencia de muestreo
                long tiempoTranscurridoMs = (System.nanoTime() - inicioCapturaNs) / 1_000_000;
                long esperaMs = (long) (1000.0 / frecuenciaHz) - tiempoTranscurridoMs;

                if (esperaMs > 0) {
                    Thread.sleep(esperaMs);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private double[] simularTension() {
        // Senoidal: 5V pico a pico (0 a 5V)
        double valor = 2.5 + 2.5 * Math.sin(faseSenoidal);
        faseSenoidal += (2 * Math.PI * frecuenciaHz / 1000.0) * 100; // Aproximación simple
        if (faseSenoidal > 2 * Math.PI) faseSenoidal -= 2 * Math.PI;
        
        // Añadir ruido blanco de simulación (0.05V)
        double ruido = (Math.random() - 0.5) * 0.1;
        return new double[]{ valor + ruido };
    }

    private double[] simularTemperatura() {
        // Deriva térmica: 25 grados con ruido de +/- 0.5 grados
        double valor = 25.0 + (Math.sin(System.currentTimeMillis() / 5000.0) * 5.0);
        double ruido = (Math.random() * 1.0) - 0.5;
        return new double[]{ valor + ruido + 0.5 };
    }

    private void tareaEscritura() {
        try (PrintWriter escritor = new PrintWriter(new BufferedWriter(new FileWriter(nombreArchivoCsv))) ) {
            escritor.println("Timestamp,Canal_Tension_V,Canal_Temperatura_C");
            
            while (ejecutando || !colaなprocesamiento.isEmpty()) {
                PaqueteMedicion m = colaProcesamiento.poll(100, TimeUnit.MILLISECONDS);
                if (m != null) {
                    escritor.printf("%s,%.3f,%.2f%n", 
                        m.getMomento().toString(), 
                        m.getValoresVoltios()[0], 
                        m.getValoresCelsius()[0]);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
