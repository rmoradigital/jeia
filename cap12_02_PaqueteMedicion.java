import java.time.Instant;
import java.util.*;

/**
 * Representa un paquete de datos capturados en un instante preciso.
 * Contiene valores para múltiples canales de adquisición.
 */
class PaqueteMedicion {
    private final Instant momento;
    private final double[] valoresVoltios;
    private final double[] valoresCelsius;

    public PaqueteMedicion(Instant momento, double[] tensiones, double[] temperaturas) {
        this.momento = momento;
        this.valoresVoltios = tensiones.clone();
        this.valoresCelsius = temperaturas.clone();
    }

    public Instant getMomento() {
        return momento;
    }

    public double[] getValoresVoltios() {
        return valoresVoltios;
    }

    public double[] getValoresCelsius() {
        return valoresCelsius;
    }
}
