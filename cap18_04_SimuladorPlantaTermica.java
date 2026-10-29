/**
 * Simula el comportamiento físico de un dispositivo con resistencia térmica.
 */
public class SimuladorPlantaTermica implements Runnable {
    private double temperaturaCelsius = 25.0; // Temperatura ambiente inicial
    private final MaquinaEstadosDispositivo fsm;
    private final GestorComandosConfirmados gestor;
    private boolean enlaceActivo = true;

    public SimuladorPlantaTermica(MaquinaEstadosDispositivo fsm, GestorComandosConfirmados gestor) {
        this.fsm = fsm;
        this.gestor = gestor;
    }

    public void setEnlaceActivo(boolean estado) {
        this.enlaceActivo = estado;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500); // Ciclo de simulación de 500ms
                actualizarFisica();
                procesarComandosPendientes();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void actualizarFisica() {
        if (fsm.getEstadoActual() == EstadoDispositivo.EN_OPERacion) {
            temperaturaCelsius += 0.5; // Calentamiento constante
        } else if (fsm.getEstadoActual() != EstadoDispositivo.DESCONECTADO) {
            temperaturaCelsius -= 0.1; // Enfriamiento lento
        }
        // Asegurar límites físicos
        temperaturaCelsius = Math.max(20.0, Math.min(temperaturaCelsius, 120.0));
    }

    private void procesarComandosPendientes() {
        if (!enlaceActivo) return;

        ComandoSeguro cmd = gestor.getColaEnvio().poll();
        if (cmd != null) {
            // Simulación de respuesta: si el comando es SET_TEMPERATURE, respondemos ACK
            boolean exito = (cmd.getTipoComando() != null);
            gestor.procesarRespuestaHardware(cmd.getNumeroSecuencia(), exito);
        }
    }

    public double getTemperaturaActual() {
        return temperaturaCelsius;
    }
}
