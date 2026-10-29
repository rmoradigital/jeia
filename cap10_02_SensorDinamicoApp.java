import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;
import java.util.Random;

/**
 * Clase que simula un sensor físico con capacidad de ajuste de frecuencia.
 * Representa el Modelo en un patrón MVC.
 */
class SensorDinamicoModelo {
    private double valorActualCelsius;
    private final Random generadorAleatorio;
    private final java.util.function.Consumer<Double> callbackActualizacion;

    public SensorDinamicoModelo(java.util.function.Consumer<Double>> callback) {
        this.generadorAleatorio = new Random();
        this.callbackActualizacion = callback;
        this.valorActualCelsius = 25.0; // Temperatura inicial
    }

    /**
     * Simula la lectura de un sensor físico.
     */
    public void realizarMuestreo() {
        // Simulación de ruido térmico: variación de +/- 0.5 grados
        double ruido = (generadorAleatorio.nextDouble() - 0.5);
        valorActualCelsius += ruido;
        
        // Notificar al interesado (el controlador)
        callbackActualizacion.accept(valorActualCelsius);
    }
}

/**
 * Interfaz gráfica que permite el control dinámico.
 */
class SensorDinamicoVista extends JFrame {
    private JLabel etiquetaTemperatura = new JLabel("Temperatura: -- C", SwingConstants.CENTER);
    private JLabel etiquetaFrecuencia = new JLabel("Frecuencia: 1 Hz", SwingConstants.CENTER);
    private JSlider sliderFrecuencia = new JSlider(1, 10, 1); // 1 a 10 Hz
    private JButton botonIniciar = new JButton("Iniciar");
    private JButton botonDetener = new JButton("Detener");

    public SensorDinamicoVista() {
        super("Instrumento de Temperatura Reactivo");
        configurarInterfaz();
    }

    private void configurarInterfaz() {
        this.setSize(300, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(5, 1));

        etiquetaTemperatura.setFont(new Font("SansSerif", Font.BOLD, 20));
        sliderFrecuencia.setMajorTickSpacing(1);
        sliderFrecuencia.setPaintLabels(true);
        sliderFrecuencia.setSnapToTicks(true);

        this.add(etiquetaTemperatura);
        this.add(etiquetaFrecuencia);
        this.add(sliderFrecuencia);
        this.add(botonIniciar);
        this.add(botonDetener);
    }

    public void actualizarInterfaz(double temperatura, int frecuenciaHz) {
        // El uso de invokeLater garantiza que la actualización ocurra en el EDT
        SwingUtilities.invokeLater(() -> {
            etiquetaTemperatura.setText(String.format("Temperatura: %.2f C", temperatura));
            etiquetaFrecuencia.setText("Frecuencia: " + frecuenciaHz + " Hz");
        });
    }

    // Getters para los componentes de control
    public JSlider getSliderFrecuencia() { return sliderFrecuencia; }
    public JButton getBotonIniciar() { return botonIniciar; }
    public JButton getBotonDetener() { return botonDetener; }
}

/**
 * Controlador que coordina el muestreo y la respuesta de la interfaz.
 */
class SensorDinamicoControlador {
    private final SensorDinamicoModelo modelo;
    private final SensorDinamicoVista vista;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> tareaActual;
    private boolean estaCorriendo = false;

    public SensorDinamicoControlador(SensorDinamicoModelo modelo, SensorDinamicoVista vista) {
        this.modelo = modelo;
        this.vista = vista;
        configurarEventos();
    }

    private void configurarEventos() {
        vista.getBotonIniciar().addActionListener(e -> iniciarCaptura());
        vista.getBotonDetener().addActionListener(e -> detenerCaptura());
        // Listener para el ajuste de la frecuencia en tiempo real
        vista.getSliderFrecuencia().addChangeListener(e -> {
            if (estaCorriendo) {
                // Si el sensor ya está activo, reiniciamos la tarea con la nueva frecuencia
                reiniciarMuestreo();
            }
        });
    }

    private void iniciarCaptura() {
        if (!estaCorriendo) {
            estaCorriendo = true;
            reiniciarMuestreo();
        }
    }

    private void detenerCaptura() {
        estaCorriendo = false;
        if (tareaActual != null) {
            tareaActual.cancel(false); // Cancelación amable
        }
    }

    private void reiniciarMuestreo() {
        if (tareaActual != null && !tareaActual.isCancelled()) {
            tareaActual.cancel(false);
        }

        int frecuenciaHz = vista.getSliderFrecuencia().getValue();
        // Cálculo del periodo en milisegundos: T = 1/f
        long periodoMs = 1000L / frecuenciaHz;

        tareaActual = scheduler.scheduleAtFixedRate(() -> {
            modelo.realizarMuestreo();
        }, 0, periodoMs, TimeUnit.MILLISECONDS);
        
        // Actualizamos la etiqueta de frecuencia de forma segura
        vista.actualizarInterfaz(0, frecuenciaHz); 
    }

    public void finalizar() {
        scheduler.shutdownNow();
    }
}

public class SensorDinamicoApp {
    public static void main(String[] args) throws InterruptedException {
        SensorDinamicoVista vista = new SensorDinamicoVista();
        // Pasamos una referencia de método como callback para actualizar la vista desde el modelo
        SensorDinamicoModelo modelo = new SensorDinamicoModelo(temp -> {
            // El controlador en una arquitectura estricta debería recibir este evento
            // Aquí lo simplificamos para la práctica de laboratorio
        });
        
        // Para esta práctica, creamos un controlador que maneje el flujo de datos
        SensorDinamicoControlador controlador = new SensorDinamicoControlador(modelo, vista) {
            // Sobrescribimos la lógica de actualización para este ejemplo práctico
            @Override
            public void iniciarCaptura() {
                super.iniciarCaptura();
                // Implementación de un callback que conecte modelo y vista
            }
        };
        
        // Nota: En una implementación real, el modelo notificaría al controlador 
        // y el controlador al EDT.
        // Por brevedad en el ejemplo, conectamos el modelo directamente a la vista vía controlador.
        
        // Re-ajuste para asegurar funcionalidad completa en el ejemplo:
        SensorDinamicoModelo modeloConectado = new SensorDinamicoModelo(t -> 
            vista.actualizarInterfaz(t, vista.getSliderFrecuencia().get())
        );
        SensorDinamicoControlador controllerFinal = new SensorDinamicoControladorConexion(modeloConectado, vista);

        vista.setVisible(true);
    }
}

/**
 * Clase de apoyo para garantizar la funcionalidad del ejemplo de laboratorio.
 */
class SensorDinamicoControladorConexion {
    private final SensorDinamicoModelo m;
    private final SensorDinamicoVista v;
    private final ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r);
        t.setDaemon(true); // El hilo morirá si la JVM se cierra
        return t;
    });
    private ScheduledFuture<?> f;
    private boolean activo = false;

    public SensorDinamicoControladorConexion (SensorDinamicoModelo m, SensorDinamicoVista v) {
        this.m = m; this.v = v;
        v.getBotonIniciar().addActionListener(() -> {
            if(!activo) { activo = true; programar(); }
        });
        v.getBotonDetener().addOnClickListener(() -> {
            activo = false; if(f != null) f.cancel(false);
        });
        v.getSliderFrecuencia().addChangeEvent(e -> {
            if(activo) programar();
        });
    }
    
    private void programar() {
        if(f != null) f.cancel(-false);
        int hz = v.getSliderFrecuencia().getValue().intValue();
        f = s.scheduleAtFixedRate(m::realizarMuestreo, 0, 1000/hz, TimeUnit.MILLISECONDS);
    }
}
