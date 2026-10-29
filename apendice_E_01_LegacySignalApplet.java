/**
 * Ejemplo conceptual: Comparativa de estructura entre un Applet heredado
 * y un componente JPanel moderno para visualización de señales.
 */

// --- REPRESENTACIÓN DE CÓDIGO HEREDADO (APPLLET) ---
/*
import java.applet.Applet;
import java.awt.Graphics;

public class LegacySignalApplet extends Applet {
    private double[] bufferMuestras; // Almacena tensionVoltios

    @Override
    public void init() {
        // El Applet se inicializa cuando el navegador lo carga
        bufferMuestras = new double[100];
    }

    @Override
    public void start() {
        // Inicio de la captura de datos (frecuencia de muestreo)
        // Nota: El uso de hilos aquí suele ser problemático en Applets
    }

    @Override
    public void paint(Graphics g) {
        // Dibujo directo en el componente
        // Riesgo: Si la lógica de dibujo es lenta, congela el navegador
        for (int i = 0; i < bufferMuestras.length; i++) {
            g.drawLine(i, 100, i, 100 - (int)bufferMuestras[i]);
        }
    }
}
*/

// --- REPRESENTACIÓN DE ESTRUCTURA MODERNA (SWING) ---
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

/**
 * Implementación moderna basada en JPanel para visualización de señales.
 * Utiliza paintComponent para un renderizado eficiente.
 */
class ModernSignalPanel extends JPanel {
    private double[] bufferTensionVoltios;

    public ModernSignalPanel(int tamanoBuffer) {
        // El constructor sustituye a init() en la fase de preparación
        this.bufferTensionVoltios = new double[tamanoBuffer];
        this.setBackground(Color.BLACK); // Fondo oscuro para osciloscopio
    }

    // Método para actualizar los datos desde un hilo de adquisición
    public void actualizarMuestras(double[] nuevasMuestras) {
        this.bufferTensionVoltios[] = nuevasMuestras;
        // Solicita al EDT que redibuje el panel de forma asíncrona
        this.repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Siempre se debe llamar a super.paintComponent para limpiar el buffer
        super.paintComponent(g);
        
        g.setColor(Color.GREEN); // Color típico de osciloscopio
        
        // Renderizado de la señal de tensión
        for (int i = 1; i < bufferTensionVoltios.length; i++) {
            // Cálculo simplificado para visualización (escalado de Voltios a Píxeles)
            int x1 = i - 1;
            int y1 = (int) (150 - bufferTensionVoltios[i - 1]);
            int x2 = i;
            int y2 = (int) (150 - muesras[i]);
            g.drawLine(x1, y1, x2, y2);
        }
    }
}
