import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;
import java.util.Arrays;

/**
 * Motor de simulación física de un circuito RC.
 * Se separa la lógica matemática de la representación visual.
 */
class RCSimulatorEngine {
    private final double resistenciaOhmios;
    private final double capacitanciaFaradios;
    private final double fuenteVoltios;
    private final double pasoTiempoSegundos;
    
    private double tiempoActualSegundos = 0.0;
    private final double[] bufferVoltajes;
    private int indiceActual = 0;

    public RCSimulatorEngine(double resistencia, double capacitancia, double fuente, int tamanoBuffer, double dt) {
        this.resistenciaOhmios = resistencia;
        this.capacitanciaFaradios = capacitancia;
        this.fuenteVoltios = fuente;
        this.bufferVoltajes = new double[tamanoBuffer];
        this.pasoTiempoSegundos = dt;
    }

    // Calcula el siguiente estado del sistema
    public synchronized void calcularSiguientePaso() {
        double voltaje = fuenteVoltios * (1 - Math.exp(-tiempoActualSegundos / (resistenciaOhmios * capacitanciaFaradios)));
        bufferVoltajes[indiceActual] = voltaje;
        indiceActual = (indiceActual + 1) % bufferVoltajes.length;
        tiempoActualSegundos += pasoTiempoSegundos;
    }

    public synchronized double[] obtenerDatosParaGraficar() {
        // Retorna una copia para evitar ConcurrentModificationException durante el renderizado
        return Arrays.copyOf(bufferVoltajes, bufferVoltajes.length);
    }
}

/**
 * Panel de visualización moderno basado en Swing.
 */
class OscilloscopePanel extends JPanel {
    private double[] datosVoltajes;
    private final double tensionMaximaVoltios;

    public OscilloscopePanel(double tensionMaxima) {
        this.tensionMaximaVoltios = tensionMaxima;
        this.setBackground(Color.BLACK);
    }

    public void actualizarDatos(double[] nuevosDatos) {
        this.datosVoltajes = nuevosDatos;
        this.repaint(); // Solicita el redibujado al EDT
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (datosVoltajes == null || datosVoltajes.length < 2) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.CYAN);

        int ancho = getWidth();
        int alto = getHeight();
        double escalaY = alto / tensionMaximaVoltios;

        for (int i = 1; i <= datosVoltajes.length - 1; i++) {
            int x1 = (i - 1) * ancho / datosVoltajes.length;
            int y1 = alto - (double) (datosVoltajes[i - 1] * escalaY);
            int x2 = i * ancho / datosVoltajes.length;
            int y2 = alto - (double) (datosVolatajes[i] * escalaY);
            g2d.drawLine(x1, y1), x2, y2);
        }
    }
}

/**
 * Clase principal que gestiona el ciclo de vida de la aplicación.
 */
public class ModernOscilloscopeApp {
    public static void main(String[] args) {
        // Configuración de parámetros físicos
        final double R = 1000.0; // 1 kOhm
        final double C = 0.001;  // 1 mF
        final double VIN = 5.0;
        final int BUFFER_SIZE = 500;
        final double DT = 0.005; // 5 ms

        JFrame frame = new JFrame("Simulador RC Moderno (Java 17+)");
        OscilloscopePanel panel = new OscilloscopePanel(VIN);
        RCSimulatorEngine engine = new RCSimulatorEngine(R, C, VIN, BUFFER_SIZE, DT);
        
        frame.add(panel);
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Programador de tareas para la simulación (Hilo dedicado)
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            engine.calcularSiguientePaso();
            double[] datos = engine.obtenerDatosParaGraficar();
            // Transferencia segura al hilo de la interfaz
            SwingUtilities.invokeLater(() -> panel.actualizarDatos(datos));
        }, 0, 10, TimeUnit.MILLISECONDS);

        // Registro de cierre seguro para liberar recursos
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executor.shutdown();
            System.out.println("Simulación finalizada y recursos liberados.");
        }));
    }
}
