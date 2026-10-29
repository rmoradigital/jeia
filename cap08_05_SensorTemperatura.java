/**
 * Interfaz que abstrae la adquisición de temperatura.
 */
interface SensorTemperatura {
    double leerTemperaturaCelsius();
}

/**
 * Implementación simulada para pruebas de software.
 */
class SensorMock implements SensorTemperatura {
    private double valorSimuladoCelsius = 25.0;

    public void setValorSimulado(double nuevoValor) {
        this.valorSimuladoCelsius = nuevoValor;
    }

    @Override
    public double leerTemperaturaCelsius() {
        return valorSimuladoCelsius;
    }
}

/**
 * Clase de control que depende de la abstracción del sensor.
 */
class ControladorTermico {
    private final SensorTemperatura sensor;
    private boolean alarmaActiva = false;

    public ControladorTermico(SensorTemperatura sensor) {
        this.sensor = sensor;
    }

    public void monitorear(double umbral) {
        if (sensor.leerTemperaturaCelsius() >= umbral) {
            alarmaActiva = true;
        } else {
            alarmaActiva = false;
        }
    }

    public boolean isAlarmaActiva() {
        return alarmaActiva;
    }
}

// Ejemplo de uso en un entorno de prueba (Test Case)
// Arrange: Preparar el mock y el controlador
// Act: Cambiar el valor del sensor y ejecutar el monitoreo
// Assert: Verificar que la alarma se activó
