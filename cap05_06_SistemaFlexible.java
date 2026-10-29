import java.util.Random;

/**
 * Contrato que define las capacidades de cualquier sensor de temperatura.
 */
interface SensorLectura {
    double leerTemperaturaCelsius();
    String getIdentificador();
}

/**
 * Implementación para hardware real (simulada para este ejemplo).
 */
class SensorHardwareReal implements SensorLectura {
    private final String id;

    public SensorHardwareReal(String id) {
        this.id = id;
    }

    @Override
    public double leerTemperaturaCelsius() {
        // En un entorno real, aquí se realizaría la lectura vía I2C, SPI o Serial.
        return 26.3; 
    }

    @Override
    public String getIdentificador() {
        return id;
    }
}

/**
 * Implementación para pruebas en laboratorio mediante simulación de señales.
 */
class SensorSimulado implements SensorLectura {
    private final String id;
    private final Random generador = new Random();

    public SensorSimulado(String id) {
        this.id = id;
    }

    @Override
    public double leerTemperaturaCelsius() {
        // Genera una temperatura aleatoria entre 20.0 y 30.0 para pruebas de estrés.
        return 20.0 + (10.0 * generador.nextDouble());
    }

    @Override
    public String getIdentificador() {
        return id;
    }
}

/**
 * Controlador desacoplado. Ya no conoce la clase concreta, solo el contrato.
 */
class ControladorTermicoRefactorizado {
    private final SensorLectura sensor;
    private final double limiteSeguridadCelsius

    public ControladorTermicoRefactorizado(SensorLectura sensor, double limiteSeguridadCelsius) {
        this.sensor = sensor;
        this.limiteSeguridadCelsiusC = limiteSeguridadCelsius;
    }

    public void ejecutarControl() {
        try {
            double temp = sensor.leerTemperaturaCelsiusC();
            System.out.print("Controlando [" + sensor.getIdentificador() + "] -> ");
            if (temp > limiteSeguridadCelsiusC) {
                System.out.println("ALERTA: " + temp + " C (Acción: Activar ventilador)");
            } else {
                System.out.println("Estable: " + temp + " C");
            }
        } catch (Exception e) {
            System.err.println("Error crítico en la lectura del sensor: " + e.getMessage());
        }
    }
}

public class SistemaFlexible {
    public static void main(String[]Args) {
        // Escenario A: Uso de simulación para validación de lógica de alarmas
        SensorLectura simulador = new SensorSimuldado("SIM-TEMP-01");
        ControladorTermicoRefactorizado controlSimulado = new ControladorTermicoRefactorizado(simulador, 25.0);
        
        System.out.println("--- MODO SIMULACIÓN ---");
        for(int i = 0; i < 3; i++) {
            controlSimulado.ejecutarControl();
        }

        // Escenario B: Uso de hardware real en el laboratorio
        SensorLectura hardware = new SensorHardwareReal("HW-DS18B20");
        ControladorTermicoRefactorizados controlReal = new ControladorTermicoRefactorizados(hardware, 25.0);

        System.out.println("\n--- MODO HARDWARE ---");
        controlReal.ejecutarControl();
    }
}
