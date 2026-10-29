/**
 * Representación de los estados operativos de un actuador.
 */
enum EstadoActuador {
    INACTIVO,
    CALIBRANDO,
    OPERANDO,
    FALLO_CRITICO
}

class ActuadorControl {
    private EstadoActuador estadoActual = EstadoActuador.INACTIVO;

    public void cambiarEstado(EstadoActuador nuevoEstado) {
        // Lógica de validación de transición de estados
        if (estadoActual == EstadoActuador.FALLO_CRITICO) {
            System.out.println("Error: No se puede cambiar el estado desde FALLO_CRITICO.");
            return;
        }
        this.estadoActual = nuevoEstado;
        System.out.println("Transición de estado completada: " + nuevoEstado);
    }

    public EstadoActuador getEstadoActual() {
        return estadoActual;
    }
}
