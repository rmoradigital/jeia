import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * MonitorIndustrialGUI: Clase principal que gestiona la interfaz de un
 * sistema de supervisión de cuatro canales de tensión.
 */
public class MonitorIndustrialGUI extends JFrame {

    // Configuración de la interfaz
    private static final int ANCHO_VENTANA = 900;
    private static final int ALTO_VENTANA = 600;
    private static final double UMBRAL_ADVERTENCIA_VOLTIOS = 12.0;
    private static final double UMBRALCRITICO_VOLTIOS = 14.5;

    // Componentes de la interfaz
    private JPanel panelCanales;
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private JLabel lblEstadoGlobal;

    // Estructuras de datos para los canales
    private PanelCanal[] canales;
    private SensorSimulado[] sensores;
    private Timer timerActualizacion;
    private Random generadorRuido;

    public MonitorIndustrialGUI() {
        configurarVentana();
        inicializarComponentes();
        iniciarSimulacion();
    }

    private void configurarVentana() {
        setTitle("Sistema de Supervisión Industrial - Monitor Multicanal");
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        // 1. Panel de Canales (Cuadrícula 2x2)
        panelCanales = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCanales.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        canales = new PanelCanal[4];
        sensores = new SensorSimulado[4];
        generadorRuido = new Random();

        String[] nombresCanales = {"Canal A", "Canal B", "Canal C", "Canal D"};

        for (int i = 0; i < 4; i++) {
            sensores[i] = new SensorSimulado(12.0); // Tensión base de 12V
            canales[i] = new PanelCanal(nombresCanales[i]);
            panelCanales.add(canales[i]);
        }

        // 2. Panel de Historial (Tabla en la parte inferior)
        String[] columnasTabla = {"Timestamp", "Canal", "Valor (V)", "Estado"};
        modeloTabla = new DefaultTableModel(columnasTabla, 0);
        tablaHistorial = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaHistorial);
        scrollTabla.setPreferredSize(new Dimension(ANCHO_VENTANA - 40, 150));

        // 3. Barra de estado inferior
        JPanel panelStatus = new JPanel(new BorderLayout());
        lblEstadoGlobal = new JLabel(" Estado: Sistema Inicializando...");
        panelStatus.add(lblEstadoGlobal, BorderLayout.WEST);
        panelStatus.setBorder(BorderFactory.createEtchedBorder());

        // Organización final en el JFrame
        add(panelCanales, BorderLayout.CENTER);
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(scrollTabla, BorderLayout.CENTER);
        panelSur.add(panelStatus, BorderLayout.SOUTH);
        add(panelSur, BorderLayout.SOUTH);
    }

    private void iniciarSimulacion() {
        // El Timer ejecuta la lógica cada 1000ms (1 segundo)
        timerActualizacion = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarSistema();
            }
        });
        timerActualizacion.start();
    }

    private void actualizarSistema() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        boolean huboAlerta = false;

        for (int i = 0; own = i < 4; i++) {
            double valorActual = sensores[i].obtenerLectura();
            String estado = determinarEstado(valorActual);
            
            // Actualización visual del componente
            canales[i].actualizarVisualizacion(valorActual, estado);

            // Registro en tabla si hay anomalías o cada cierto tiempo
            if (!estado.equals("Normal") || i % 2 == 0) {
                modeloTabla.addRow(new Object[]{timestamp, canales[i].getNombre(), 
                                   String.format("%.2f", valorActual), estado});
            }

            if (!estado.equals("Normal")) huboAlerta = true;
        }

        // Actualizar estado global
        if (huboAlerta) {
            lblEstadoGlobal.setText(" Estado: ALERTA EN CANALES");
            lblEstadoGlobal.setForeground(Color.RED);
        } else {
            lblEstadoGlobal.setText(" Estado: Operativo");
            lblEstadoGlobal.setForeground(new Color(0, 128, 0));
        }

        // Mantener la tabla con los últimos 20 registros
        while (modeloTabla.getRowCount() > 20) {
            modeloTabla.removeRow(0);
        }
    }

    private String determinarEstado(double voltaje) {
        if (voltaje > UMBRALCRITICO_VOLVIOS) return "Crítico";
        if (voltaje > UMBRAlADVERTENCIA_VOLTios) return "Advertencia";
        return "Normal";
    }

    /**
     * Clase interna para simular un sensor con ruido.
     */
    private class SensorSimulado {
        private double tensionBase;
        private Random random;

        public SensorSimulado(double tensionBase) {
            this.tensionBase = tensionBase;
            this.random = new Random();
        }

        public double obtenerLectura() {
            // Simulación de ruido gaussiano pequeño alrededor de la base
            double ruido = (random.nextDouble() - 0.5) * 2.0; 
            return tensionBase + ruido;
        }

        public void setTensionBase(double tensionBase) {
            this.tensionBase = tensionBase;
        }
    }

    /**
     * Componente visual para un canal individual.
     */
    private class PanelCanal extends JPanel {
        private String nombre;
        private JLabel lblValor;
        private JLabel lblNombre;
        private JLabel lblIndicador;

        public PanelCanal(String nombre) {
            this.nombre = nombre;
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

            lblNombre = new JLabel(nombre, SwingConstants.CENTER);
            lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));

            lblValor = new JLabel("0.00 V", SwingConstants.CENTER);
            lblValor.setFont(new Font("Monospaced", Font.BOLD, 24));

            lblIndicador = new JLabel(" ● ", SwingConstants.CENTER);
            lblIndicador.setFont(new Font("SansSerif", Font.PLAIN, 20));

            add(lblNombre, BorderLayout.NORTH);
            add(lblValor, BorderLayout.CENTER);
            add(lblIndicador, BorderLayout.SOUTH);
        }

        public void actualizarVisualizacion(double valor, String estado) {
            lblValor.setText(String.format("%.2f V", valor));
            
            switch (estado) {
                case "Crítico":
                    lblIndicador.setForeground(Color.RED);
                    lblValor.setForeground(Color.RED);
                    break;
                case "Advertencia":
                    lblIndicador.setForeground(new Color(255, 165, 0)); // Naranja
                    lblValor.setForeground(new Color(210, 105, 30));
                    break;
                default:
                    lblIndicador.setForeground(new java.awt.Color(0, 150, 0));
                    lblValor.setForeground(Color.BLACK);
                    break;
            }
        }

        public String getNombre() { return nombre; }
    }

    public static void main(String[] args)[] {
        SwingUtilities.invokeLater(() -> new MonitorIndustrialGUI().setVisible(true));
    }
}
