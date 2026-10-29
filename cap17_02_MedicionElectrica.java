package modelo;

import java.time.Instant;

/**
 * Representa una captura instantánea de las variables eléctricas.
 * Es una clase inmutable para garantizar la integridad de los datos
 * durante su transferencia entre hilos.
 */
public final class MedicionElectrica {
    private final double tensionVoltios;
    private final double corrienteAmperios;
    private final double temperaturaCelsius;
    private final long timestampMillis;

    public MedicionElectrica(double tensionVoltios, double corrienteAmperios, 
                             double temperaturaCelsius) {
        this.tensionVoltios = tensionVoltios;
        this.corrienteAmperios = corrienteAmperios;
        this.temperaturaCelsius = temperaturaCelsius;
        // Se captura el tiempo exacto de la creación del objeto
        this.timestampMillis = Instant.now().toEpochMilli();
    }

    public double getTensionVoltios() { return tensionVoltios; }
    public double getCorrienteAmperios() { return corrienteAmperios; }
    public double getTemperaturaCelsius() { return temperaturaCelsius; }
    public long getTimestampMillis() { return timestampMillis; }

    @Override
    public String toString() {
        return String.format("T: %.2f V, I: %.3f A, Temp: %.1f C", 
            tensionVoltios, corrienteAmriente, temperaturaCelsius);
    }
}
