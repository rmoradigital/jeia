/**
 * Representa la estructura de un comando enviado al dispositivo físico.
 * Se incluyen unidades físicas y mecanismos de integridad.
 */
public class ComandoSeguro {
    private final int numeroSecuencia;
    private final CommandType tipoComando;
    private final double valorParametro; // Ej: temperaturaCelsius
    private final long timestampMs;

    public enum CommandType {
        SET_TEMPERATURE,
        START_FAN,
        STOP_ALL,
        REQUEST_STATUS
    }

    public ComandoSeguro(int secuencia, CommandType tipo, double valor) {
        this.numeroSecuencia = secuencia;
        this.tipoComando = tipo;
        this.valorParametro = valor;
        this.timestampMs = System.currentTimeMillis();
    }

    // Getters y métodos de serialización...
    
    @Override
    public String toString() {
        return String.format("Seq: %d | Comando: %s | Valor: %.2f", 
                numeroSecuencia, tipoComando, valorParametro);
    }
}
