/**
 * Contrato para cualquier sensor conectado al sistema.
 */
interface SensorInterface {
    double obtenerLectura();
    boolean estaOperativo();
}

/**
 * Implementación de un sensor físico (simulado para este ejemplo).
 */
class SensorTemperaturaFisico implements SensorInterface {
    private final String id;
    private boolean errorEstado;

    public SensorTemperaturaFisico(String id) {
        this.id = id;
        this.errorEstado = false;
    }

    @Override
    public double obtenerLectura() {
        // En un entorno real, aquí se realizaría la lectura vía Serial o I2C
        return 25.5; 
    }

    @Override
    public boolean estaOperativo() {
        return !errorEstado;
    }
}
