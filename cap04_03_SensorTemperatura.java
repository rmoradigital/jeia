/**
 * Representa un sensor de temperatura con límites de operación.
 */
public class SensorTemperatura {
    private final String nombre;
    private final double limiteSuperiorCelsius;
    private final double limiteInferiorCelsius;

    /**
     * @param nombre Identificador del sensor.
     * @param minTemperatura Límite inferior físico.
     * @param maxTemperatura Límite superior físico.
     */
    public SensorTemperatura(String nombre, double minTemperatura, double maxTemperatura) {
        // Validación en constructor para asegurar integridad del objeto
        if (minTemperatura >= maxTemperatura) {
            throw new IllegalArgumentException("El límite inferior debe ser menor al superior.");
        }
        this.nombre = nombre;
        this.limiteSuperiorCelsius = maxTemperatura;
        this.limiteInferiorCelsius = minTemperatura;
    }

    /**
     * Verifica si un valor de temperatura está dentro del rango operativo.
     * @param valor El valor a evaluar.
     * @return true si el valor es válido, false si es errático o fuera de rango.
     */
    public boolean esLecturaValida(double valor) {
        return valor >= limiteInferiorCelsius && valor <= limiteSuperiorCelsius;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Sensor: " + nombre + " (Rango: " + limiteInferiorCelsius + " a " + limiteSuperiorCelsius + " C)";
    }
}
