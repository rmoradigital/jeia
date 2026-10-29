import java.util.concurrent.*;
import java.util.*;

/**
 * Gestiona el envío de comandos y la espera de confirmaciones (ACK/NACK).
 */
public class GestorComandosConfirmados {
    private final BlockingQueue<ComandoSeguro> colaEnvio;
    private final Map<Integer, CompletableFuture<Boolean>> pendientes;
    private final int timeoutMs;

    public GestorComandosConfirmados(int timeoutMs) {
        this.colaEnvio = new LinkedBlockingQueue<>();
        this.pendientes = new ConcurrentHashMap<>();
        this.timeoutMs = timeoutMs;
    }

    /**
     * Encola un comando y espera su confirmación de forma asíncrona.
     * @param comando El comando a enviar.
     * @return Un CompletableFuture que contiene true si hubo ACK, false si hubo NACK o timeout.
     */
    public CompletableFuture<Boolean> enviarComando(ComandoSeguro comando) {
        CompletableFuture<Boolean> respuesta = new CompletableFuture<>();
        pendientes.put(comando.getNumeroSecuencia(), respuesta);
        colaEnvio.add(comando);
        return respuesta;
    }

    /**
     * Procesa la respuesta recibida desde el hardware.
     * @param secuencia El número de secuencia del comando recibido.
     * @param esConfirmacion true para ACK, false para NACK.
     */
    public void procesarRespuestaHardware(int secuencia, boolean esConfirmacion) {
        CompletableFuture<Boolean> futuro = pendientes.remove(secuencia);
        if (futuro != null) {
            futuro.complete(esConfirmacion);
        }
    }

    public BlockingQueue<ComandoSeguro> getColaEnvio() {
        return colaEnvio;
    }
}
