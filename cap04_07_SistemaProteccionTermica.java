import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la lógica de monitoreo y la respuesta ante eventos térmicos.
 */
public class SistemaProteccionTermica {
    private final SensorTemperatura sensor;
    private final ActuadorRele reléProteccion;
    private final double umbralSeguridadCelsius;
    private final List<Medicion> historialEventos;

    /**
     * Constructor que define las constantes de operación del sistema.
     * @param sensor El sensor de temperatura a monitorear.
     * @param relé El actuador que ejecutará la acción de protección.
     * @param umbralSeguridadCelsius Temperatura límite para activar el relé.
     */
    public SistemaProteccionTermica(SensorTemperatura sensor, ActuadorRele relé, double umbralSeguridadCelsius) {
        this.sensor = sensor;
        this.reléProteccion = relé;
        this.umbralSeguridadCelsius = umbralSeguridadCelsius;
        this.historialEventos = new ArrayList<>();
    }

    /**
     * Realiza un ciclo de muestreo y toma decisiones basadas en la lectura.
     * @param lecturaSimulada El valor de temperatura obtenido del sensor.
     */
    public void ejecutarCicloMonitoreo(double lecturaSimulada) {
        // 1. Registro de la medición en el historial (Inmutabilidad de la medición)
        Medicion m = new Medicion(lecturaSimulada, "Celsius", java.time.LocalDateTime.now());
        historialEventos.add(m);

        System.out.print("Lectura actual: " + m.getValor() + " " + m.getUnidad() + " | ");

        // 2. Lógica de decisión (Control de lazo abierto/cerrado)
        if (m.getValor() > umbralSeguridadCelsius) {
            System.out.print("¡ALERTA! Superando umbral de " + umbralSeguridadCelsius + " C. ");
            if (!reléProteccion.estaCerrado()) {
                reléProteccion.setEstado(true); // Activar protección
            }
        } else {
            if (reléProteccion.estaCerrar()) { // (Simulación: si baja la temperatura, liberamos la protección)
                // En un sistema real, se podría usar histéresis para evitar oscilaciones
                reléProteccion.setEstado((lecturaSimulada < (umbralSeguridadCelsius - 5.0)));
            }
        }
    }

    public List<Medicion> getHistorial() {
        return new ArrayList<>(historialEventos); // Devolvemos copia para proteger el encapsulamiento
    }

    /**
     * Método principal para simular el comportamiento del sistema.
     */
    public static void main(String[] args) {
        // Configuración inicial
        SensorTemperatura termistor = new SensorTemperatura("NTC 10k", -50.0, 150.0);
        ActuadorRele protector = new ActuadorRele("Protector de Carga");
        SistemaProteccionTermica sistema = new SistemaProteccionTermica(termistor, protector, 50.0);

        // Simulación de una señal con ruido y una tendencia ascendente
        double temperaturaActual = 25.0;
        double incremento = 8.0;

        System.out.println("--- Iniciando Simulación de Monitoreo ---");
        for (int i = 0; i < 6; i++) {
            temperaturaActual += incremento;
            // Simulamos un pequeño ruido aleatorio
            double ruido = (Math.random() - 0.5) * 2.0; 
            sistema.ejecutarCicloMonitoreo(temperaturaActual + ruido);
            
            try {
                Thread.sleep(500); // Pausa de 500ms para observar la ejecución
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("--- Simulación Finalizada ---");
        System.out.println("Total mediciones registradas: " + sistema.getHistorial().size());
    }
}
