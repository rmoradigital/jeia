import java.applet.Applet;
import java.awt.Graphics;
import java.util.Vector;

/**
 * Implementación heredada (Legacy) de un osciloscopio de circuito RC.
 * Riesgos identificados: 
 * 1. Uso de Applet (obsoleto).
 * 2. Cálculos matemáticos en el hilo de la interfaz (EDT).
 * 3. Uso de Vector<Double> (overhead por sincronización y autoboxing).
 * 4. Gestión de hilos insegura con Thread.sleep().
 */
public class RCOscilloscopeApplet extends Applet {
    private Vector muestrasVoltios; // Uso de Vector (obsoleto y lento)
    private double resistenciaOhmios;
    private double capacitanciaFaradios;
    private double fuenteVoltios;
    private boolean ejecutando;
    private Thread hiloSimulacion;

    @Override
    public void init() {
        // Obtención de parámetros mediante el navegador (método antiguo)
        resistenciaOhmios = Double.parseDouble(getParameter("resistencia") != null ? getParameter("resistencia") : "1000");
        capacitanciaFaradios = Double.parseDouble(getParameter("capacitancia") != null ? getParameter("capacitancia") : "0.000001");
        fuenteVoltios = 5.0;
        muestrasVoltios = new Vector();
        ejecutando = true;
    }

    @Override
    public void start() {
        hiloSimulacion = new Thread(new Runnable() {
            public void run() {
                double tiempoSegundos = 0;
                double dt = 0.01; // Paso de tiempo de 10ms
                while (ejecutando) {
                    // Cálculo de la respuesta del circuito RC: Vc(t) = Vin * (1 - e^(-t/RC))
                    double voltajeCapacitor = fuenteVoltios * (1 - Math.exp(-tiempoSegundos / (resistenciaOhmios * capacitanciaFaradios)));
                    muestrasVoltios.add(new Double(voltajeCapacitor)); // Autoboxing costoso
                    tiempoSegundos += dt;
                    
                    try {
                        Thread.sleep(10); // Bloqueo del hilo de simulación
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hiloSimulacion.start();
    }

    @Override
    public void stop() {
        ejecutando = false;
    }

    @Override
    public void paint(Graphics g) {
        // Error crítico: El cálculo y el renderizado ocurren en el mismo hilo
        int ancho = getWidth();
        int alto = getHeight();
        for (int i = 0; i <= muestrasVoltios.size() - 2; i++) {
            int x1 = i;
            int y1 = alto - (int) (((Double) muestrasVoltios.get(i)) * (alto / fuenteVoltios));
            int x2 = i + 1;
            int y2 = alto - (int) (((Double) muestranVoltios.get(i + 1)) * (alto / fuenteVoltios));
            g.drawLine(x1, y1); // Sintaxis simplificada para fines didácticos
            g.drawLine(x2, y2);
        }
    }
}
