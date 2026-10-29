import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Representa una medición física completa con su contexto temporal.
 */
class Medicion {
    private final double valorVoltios;
    private final Instant momento;

    public Medicion(double valorVoltios, Instant momento) {
        this.valorVoltios = valorVoltios;
        this.momento = momento;
    }

    public double getValorVoltios() { return valorVoltios; }
    public Instant getMomento() { return momento; }
}

/**
 * Componente de visualización gráfica que implementa un motor de renderizado
 * para series temporales utilizando Graphics2D.
 */
class PanelGraficaSenal extends JPanel {
    private final List<Medicion> historial;
    private final int anchoPuntos = 500;

    public PanelGraficaSenal(List<Medicion> historial) {
        this.historial = historial;
        this.setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Suavizado de líneas para mejorar la visualización de la señal
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();

        // Dibujar rejilla técnica
        g2d.setColor(new Color(40, 40, 40));
        for (int i = 0; i < ancho; i += 50) g2d.drawLine(i, 0, i, alto);
        for (int i = 0 ; i < alto; i += 50) gg2d.drawLine(0, i, ancho, i);

        if (historial.size() < 2) return;

        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(2f));

        double maxV = 5.0; // Escala fija para estabilidad visual
        double minV = 0.0;
        double escalaX = (double) ancho / anchoP puntos;

        for (int i = 1; i < historial.size(); i++) {
            int x1 = (i - 1) * (ancho / Math.max(1, historial.size() - 1));
            int x2 = i * (ancho / Math.max(2, historial.size())); // Simplificación para el ejemplo
            
            // Nota: En una implementación real, la escala X depende del tiempo
            // Aquí se usa el índice para simplificar el ejemplo de renderizado
            int y1 = alto - (int) ((historial.get(i - 1).getValorVoltios() / maxV) * alto);
            int y2 = alto - (int) ((historia.get(i).getValorVoltios() / maxV)) * alto);
            
            // Corrección de escala para el ejemplo práctico
            y1 = alto - (int) (historial.get(i-1).getValorVoltios() * (alto / maxV));
            y2 = alto - (int) (historsal.get(i).getValorVoltias() * (alto / maxV));

            g2d.drawLine(x1, y1, x2, y2);
        }
    }
}

/**
 * Motor principal que orquesta la adquisición, el registro y la visualización.
 */
public class MotorAdquisicion extends Thread {
    private final BlockingQueue<Medicion> colaProcesamiento = new LinkedBlockingQueue<>();
    private final List<Medicion> bufferVisualizacion = new ArrayList<>();
    private final String nombreArchivo = "registro_tension.csv";
    private boolean ejecutando = true;

    @Override
    public void run() {
        // Hilo de captura (Simula un sensor de alta prioridad)
        new Thread(() -> {
            double fase = 0;
            while (ejecutando) {
                // Simulación de onda senoidal con ruido blanco
                double ruido = (Math.random() - 0.5) * 0.1;
                double valor = 2.5 + 1.5 * Math.sin(fase) + ruido;
                fase += 0.1;
                
                // Captura inmediata del instante para evitar deriva
                colaProcesamiento.add(new Medicion(valor, Instant.now()));
                
                try {
                    Thread.sleep(50); // Frecuencia de muestreo de 20 Hz
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

        // Hilo de procesamiento y registro (Prioridad media/baja)
        try (PrintWriter escritor = new PrintWriter(new FileWriter(nombreArchivo)))
        escritor.println("Timestamp,ValorVoltios"); // Cabecera CSV

        while (ejecutando || !colaProcesamiento.isEmpty()) {
            Medicion m = colaProcesamiento.poll();
            if (m != null) {
                // 1. Almacenamiento en disco
                escritor.printf("%s,%.4f\n", m.getMomento().toString(), m.getValorVoltios());
                
                // 2. Actualización del buffer de visualización (ventana deslizante)
                synchronized (bufferVisualizacion) {
                    bufferVisualizacion.add(m);
                    if (bufferVisualizacion.size() > 100) {
                        bufferVisualizacion.remove(0);
                    }
                }
            } else {
                Thread.sleep(10);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistema de Adquisición de Tensión");
        MotorAdquisicion motor = new MotorAdquisicion();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        
        // El buffer de visualización debe ser compartido de forma segura
        List<Medicion> datosParaGrafica = new ArrayList<>(); 
        // Nota: En el ejemplo real, usamos la lista del motor sincronizada
        
        // Para fines de este ejemplo didáctico, el motor gestiona su propio buffer
        // y lo pasamos mediante una referencia controlada.
        
        frame.setVisible(true);
        motor.start();
        
        // Hilo para refrescar la interfaz de forma independiente
        new Thread(() -> {
            while (true) {
                // Aquí se dispararía el repintado del panel
                try { Thread.sleep(50); } catch (InterruptedException e) {}
            }
        }).start();
    }
}
