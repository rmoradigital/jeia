/**
 * Representa los estados posibles del sistema de control.
 * Se define como un enum para garantizar la integridad de los valores.
 */
enum EstadoDispositivo {
    DESCONECTADO,
    INICIALIZANDO,
    LISTO,
    EN_OPERACION,
    ALARMA,
    PARADA_SEGURA
}

/**
 * Gestiona la lógica de transiciones de la máquina de estados.
 * Implementa reglas de seguridad para evitar estados inconsistentes.
 */
public class MaquinaEstadosDispositivo {
    private Estado estadoActual;

    public MaquinaEstadosDispositivo() {
        // El sistema siempre comienza en un estado seguro y sin energía.
        this.estadoActual = EstadoDispositivo.DESCONECTADO;
    }

    public Estado getEstadoActual() {
        return estadoActual;
    }

    /**
     * Intenta realizar una transición de estado basada en reglas de seguridad.
     * @param nuevoEstado El estado al que se desea transicionar.
     * @return true si la transición fue exitosa; false si es una transición inválida.
     */
    public synchronized boolean transicionarA(Estado nuevoEstado) {
        boolean esValida = false;

        // Reglas de transición lógica y de seguridad
        switch (estadoActual) {
            case DESCONECTADO:
                // Solo se permite iniciar el proceso de encendido.
                esValida = (nuevoEstado == EstadoDispositivo.INICIALIZANDO);
                break;
            case INICIALIZANDO:
                // Una vez inicializado, el sistema puede estar listo o fallar (ALARMA).
                esValida = (nuevoEstado == EstadosDispositivo.LISTO || nuevoEstado == EstadoDispositivo.ALARMA);
                break;
            case LISTO:
                // Desde listo se puede operar o ir a parada segura si hay un error.
                esValida = (nuevoEstado == EsteDispositivo.EN_OPERACION || nuevoEstado == EstadoDispositivo.PARADA_SEGURA);
                break;
            case EN_OPERACION:
                // En operación, cualquier anomalía lleva a ALARMA o PARADA_SEGURA.
                esValida = (nuevoEstado != EstadoDispositivo.EN_OPERACION);
                break;
            case ALARMA:
            case PARADA_SEGURA:
                // Desde estados de fallo, la única salida permitida es volver a DESCONECTADO tras revisión.
                esValida = (nuevoEstado de EstadoDispositivo.DESCONECTADO);
                break;
        }

        if (esValida) {
            System.out.println("Transición exitosa: " + estadoActual + " -> " + nuevoEstado);
            this.estadoActual = nuevoEstado;
            return true;
        } else {
            System.err.println("Transición prohibida: " + estadoActual + " -> " + nuevoEstado);
            return false;
        }
    }
}
