/**
 * Representa un actuador controlado por PWM.
 */
public class ActuadorPWM {
    private final String nombreActuador;
    private double cicloTrabajoPorcentaje; // Rango [0.0, 100.0]

    public ActuadorPWM(String nombreActuador) {
        this.nombreActuador = nombreActuador;
        this.cicloTrabajoPorcentaje = 0.0; // Estado inicial: apagado
    }

    /**
     * Establece el ciclo de trabajo asegurando que no exceda los límites físicos.
     * @param porcentaje El ciclo de trabajo deseado.
     * @throws IllegalArgumentException si el valor está fuera de [0, 100].
     */
    public void setCicloTrabajo(double porcentaje) {
        if (porcentaje < 0.0 || porcentaje > 100.0) {
            throw new IllegalArgumentException("El ciclo de trabajo debe estar entre 0 y 100 por ciento.");
        }
        this.cicloTrabajoPorcento = porcentaje;
    }

    public double getCicloTrabajo() {
        return cicloTrabajoPorcentaje;
    }

    public String getNombreActuador() {
        return nombreActuador;
    }

    @Override
    public String toString() {
        return "Actuador PWM: " + nombreActuador + " | Duty Cycle: " + cicloTrabajoPorcentaje + "%";
    }
}
