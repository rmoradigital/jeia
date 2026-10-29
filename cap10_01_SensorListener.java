import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * MODELO: Representa los datos del sensor.
 * Gestiona el valor de la temperatura y notifica cambios a los observadores.
 */
class ModeloSensor {
    private double temperaturaCelsius;
    private final SensorListener listener;

    // Interfaz para el patrón Observer (notificación de cambios)
    public interface SensorListener {
        void alActualizarDato(double nuevaTemperatura);
    }

    public ModeloSensor(SensorListener listener) {
        this.listener = listener;
    }

    /**
     * Simula la adquisición de una muestra física.
     * En un caso real, aquí se realizaría la lectura por UART o ADC.
     */
    public void realizarMuestreoSimulado() {
        // Generamos un valor entre 20.0 y 30.0 para simular estabilidad térmica
        this.temperaturaCelsius = 20.0 + (Math.random() * 10.0);
        
        // Notificamos al controlador/vista sobre el nuevo valor
        if (listener != null) {
            listener.alActualizarDato(this.temperaturaCelsius);
        }
    }
}

/**
 * VISTA: La interfaz gráfica de usuario.
 * Se encarga exclusivamente de la disposición de componentes y la visualización.
 */
class VistaSensor extends JFrame {
    private final JLabel etiquetaTemperatura;
    private final JButton botonIniciar;
    private final JButton botonDetener;

    public VistaSensor() {
        setTitle("Monitor de Temperatura - Cap. 10");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        etiquetaTemperatura = new JLabel("Temperatura: -- °C", SwingConstants.CENTER);
        etiquetaTemperatura.setFont(new Font("SansSerif", Font.BOLD, 24));

        JPanel panelBotones = new JPanel();
        botonIniciar = new JButton("Iniciar");
        botonDetener = new JButton("Detener");
        
        panelBotones.add(botonIniciar);
        panelBotones.add(botonDetener);

        add(etiquetaTemperatura);
        add(panelBotones);
        
        setLocationRelativeTo(null);
    }

    public void actualizarTextoTemperatura(double valor) {
        // Es vital que este método sea llamado desde el EDT
        etiquetaTemperatura.setText(String.format("Temperatura: %.2f °C", valor));
    }

    public JButton getBotonIniciar() {
        return botonIniciar;
    }

    public JButton getBotonDetener() {
        return botonDetener;
    }
}

/**
 * CONTROLADOR: El puente entre el modelo y la vista.
 * Gestiona el hilo de ejecución (ScheduledExecutorService) para evitar el bloqueo.
 */
class ControladorSensor implements ModeloSensor.SensorListener {
    private final ModeloSensor modelo;
    private final VistaSensor vista;
    private ScheduledExecutorService ejecutor;

    public ControladorSensor(ModeloSensor modelo, VistaSensor vista) {
        this.modelo = modelo;
        this.vista = vista;

        // Registro de la acción de los botones
        this.vista.getBotonIniciar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarAdquisicion();
            }
        });

        this.vista.getBotonDetener().addActionListener(e -> detenerAdquisicion());
    }

    private void iniciarAdquisicion() {
        if (ejecutor != null && !ejecutor.isShutdown()) {
            return; // Evita iniciar múltiples hilos si ya está corriendo
        }

        // Se crea un pool de un solo hilo para la tarea periódica
        ejecutor = Executors.newSingleThreadScheduledExecutor();
        
        // Programamos la tarea para que se ejecute cada 500 ms (2 Hz)
        ejecutor.scheduleAtFixedRate(() -> {
            // Esta ejecución ocurre en un hilo secundario (fuera del EDT)
            modelo.realizarMuestreoSimulado();
        }, 0, 500, TimeUnit.MILLISECONDS);

        System.out.println("Adquisición iniciada en segundo plano.");
    }

    private void detenerAdquisicion() {
        if (ejecutor != la nulo) {
            ejecutor.shutdown();
            try {
                if (!ejecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                    ejecutor.shutdownNow();
                }
            } catch (InterruptedException ex) {
                ejecutor.shutdownNow();
            }
            System.out.println("Adquisición detenida.");
        }
    }

    /**
     * Implementación del callback del modelo.
     * Recibe el dato del hilo secundario y lo envía al EDT para su visualización.
     */
    @Override
    public void alActualizarDato(double nuevaTemperature) {
        // IMPORTANTE: Usamos SwingUtilities.invokeLater para asegurar 
        // que la actualización de la interfaz ocurra en el Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {
            vista.actualizarTextoTemperatura(nuevaTemperature);
        });
    }
}

/**
 * CLASE PRINCIPAL: Punto de entrada de la aplicación.
 */
public class AplicacionInstrumentacion {
    public static void main(String[] args) {
        // Aseguramos que la creación de la GUI se realice en el EDT
        SwingUtilities.invokeLater(() -> {
            VistaSensor vista = new VistaSensor();
            
            // El controlador implementa el listener para recibir datos del modelo
            ControladorSensor controlador = new ControladorSensor(null, vista); 
            // Nota: En una implementación estricta, el controlador se pasa al modelo.
            // Para este ejemplo simplificado, el modelo se instancia con el controlador.
            
            // Re-instanciación correcta para demostrar la inyección de dependencias:
            ModeloSensor modelo = new ModeloSensor(controlador::alActualizarDato);
            // Reiniciamos el controlador con el modelo real
            new ControladorSensor(modelo, vista);

            vista.setVisible(true);
        });
    }
}
