/**
 * Implementación rígida (Legacy) que presenta un acoplamiento fuerte.
 * El controlador depende de una clase concreta, no de una abstracción.
 */
class SensorDS18B20 {
    private final String id;

    public SensorDS18B20(String id) {
        this.id = id;
    }

    public double leerTemperaturaCelsius() {
        // Simulación de lectura directa de registros de hardware
        return 25.5; 
    }

    public String getIdentificador() {
        return id;
    }
}

class ControladorTermico {
    // ERROR DE DISEÑO: Acoplamiento fuerte con la clase concreta SensorDS18B20
    private final SensorDS18B20 sensor;
    private final double limiteSeguridadCelsius;

    public ControladorTermico(SensorDS18B20 sensor, double limiteSeguridadCelsius) {
        this.sensor = sensor;
        this.limiteSeguridadCelsius = limiteSeguridadCelsius;
    }

    public void ejecutarControl() {
        double temp = sensor.leerTemperaturaCelsius();
        System.out.println("Monitoreando " + sensor.getIdentificador() + ": " + temp + " C");
        if (temp > limiteSeguridadCelsius) {
            System.out.println("¡ALERTA! Activando enfriamiento...");
        }
    }
}

public class SistemaRigido {
    public static void main(String[] args) {
        SensorDS18B20 ds18b20 = new SensorDS18B20("DS-001");
        ControladorTermico controlador = new ControladorTermico(ds18b20, 30.0);
        controlador.ejecutarControl();
    }
}
