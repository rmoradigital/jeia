import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa un componente visual modular para mostrar una lectura eléctrica.
 * Se extiende de JPanel para permitir su inserción en cualquier contenedor.
 */
class IndicadorLectura extends JPanel {
    private JLabel etiquetaNombre;
    private JLabel etiquetaValor;
    private String unidad;

    /**
     * Constructor del indicador.
     * @param nombre Nombre del parámetro (ej. "Tensión")
     * @param unidad Unidad de medida (ej. "V")
     */
    public IndicadorLectura(String nombre, String unidad) {
        this.unidad = unidad;
        
        // Configuración del panel para que use BorderLayout
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setPreferredSize(new Dimension(150, 80));

        // Etiqueta del nombre del parámetro
        etiquetaNombre = new JLabel(nombre, SwingConstants.CENTER);
        etiquetaNombre.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Etiqueta del valor numérico
        etiquetaValor = new JLabel("0.00 " + unidad, SwingConstants.CENTER);
        etiquetaValor.setFont(new Font("Monospaced", Font.BOLD, 20));

        // Organización interna: Nombre arriba, valor abajo
        this.add(etiquetaNombre, BorderLayout.NORTH);
        this.add(etiquetaValor, BorderLayout.CENTER);
    }

    /**
     * Actualiza el valor mostrado en la interfaz.
     * @param nuevoValor Valor medido en unidades físicas
     * @param colorEstado Color para indicar estado (normal, advertencia o crítico)
     */
    public void actualizarLectura(double nuevoValor, Color colorEstado) {
        // Formateo con dos decimales para evitar fluctuaciones visuales erráticas
        etiquetaValor.setText(String.format("%.2f %s", nuevoValor, unidad));
        etiquetaValor.setForeground(colorEstado);
    }
}

/**
 * Clase principal que constituye la ventana de operación del instrumento.
 */
public class InstrumentoVirtualGUI extends JFrame {
    
    private IndicadorLectura indicadorTension;
    private IndicadorLectura indicadorCorriente;
    private JLabel estadoSistema;
    private JButton botonParar;

    public InstrumentoVirtualGUI() {
        // Configuración de la ventana principal
        setTitle("Instrumento Virtual - Monitor de Energía");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Espaciado entre componentes

        // 1. Panel Superior: Título o Controles Rápidos
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Panel de Supervisión en Tiempo Real"));
        add(panelSuperior, BorderLayout.NORTH);

        // 2. Panel Central: Contenedor de Mediciones (Uso de GridLayout)
        JPanel panelMediciones = new JPanel(new GridLayout(2, 1, 10, 10));
        panelMediciones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 40));

        indicadorTension = new IndicadorLectura("Tensión", "V");
        indicadorCorriente = new IndicadorLectura("Corriente", "A");

        panelMediciones.add(indicadorTension);
        panelMediciones.add(indicadurCorriente);
        add(panelMediciones, BorderLayout.CENTER);

        // 3. Panel Sur: Barra de Estado y Botones
        JPanel panelInferior = new JPanel(new BorderLayout());
        
        estadoSistema = new JLabel(" Sistema: Operativo ");
        estadoSistema.setFont(new Font("SansSerif", Font.ITALIC, 12));
        
        botonParar = new JButton("Detener Adquisición");
        botonParar.setBackground(new Color(255, 200, 200));

        panelInferior.add(estadoSistema, BorderLayout.WEST);
        panelInferior.add(botonParar, BorderLayout.EAST);
        add(panelInferior, BorderLayout.SOUTH);

        // --- Lógica de Simulación ---
        iniciarSimulacion();
    }

    /**
     * Configura un temporizador que simula la llegada de datos de sensores.
     * Se utiliza javax.swing.Timer para asegurar que las actualizaciones ocurran
     * dentro del Event Dispatch Thread (EDT).
     */
    private void iniciarSimulacion() {
        Timer timerSimulacion = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Generación de datos aleatorios para simular sensores
                double tensionSimulada = 12.0 + (Math.random() * 4.0); // Rango 12.0 - 16.0 V
                double corrienteSimulada = 0.5 + (Math.random() * 2.0); // Rango 0.5 - 2.5 A

                // Determinación de estados visuales basados en umbrales
                Color colorTension = Color.BLACK;
                if (tensionSimulada > 15.0) {
                    colorTension = Color.RED; // Estado Crítico
                    estadoSistema.setText(" Estado: ALERTA - Tensión Alta ");
                    estadoSistema.setForeground(Color.RED);
                } else if (tensionSimulada > 14.0) {
                    colorTension = Color.ORANGE; // Estado Advertencia
                    estadoSistema.setText(" Estado: Advertencia ");
                    estadoSistema.setForeground(new Color(204, 102, 0));
                } else {
                    estadoSistema.setText(" Estado: Operativo ");
                    estadoSistema.setForeground(new Color("green"));
                }

                // Actualización de los componentes de la interfaz
                indicadorTension.actualizarLectura(tensionSimulada, colorTension);
                indicadorCorriente.actualizarLectura(corrienteSimulada, Color.BLACK);
            }
        });
        timerSimulacion.start();
    }

    public static void main(String[] args) {
        // Asegurar que la interfaz se cree en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new InstrumentoVirtualGUI().setVisible(true);
        });
    }
}
