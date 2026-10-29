/**
 * Representa un canal de entrada de un convertidor ADC.
 */
public class CanalADC {
    private final int pinDigital;
    private final double tensionReferenciaVoltios;
    private final int resolucionBits;

    public CanalADC(int pinDigital, double tensionReferenciaVoltios, int resolucionBits) {
        this.pinDigital = pinDigital;
        this.tensionReferenciaVoltios = tensionReferenciaVoltios;
        this.resolucionBits = resolucionBits;
    }

    /**
     * Convierte un valor digital crudo a tensión analógica real.
     * @param valorDigital El conteo obtenido del ADC (0 a 2^bits - 1).
     * @return El valor equivalente en voltios.
     */
    public double digitalAVoltaje(int valorDigital) {
        // Cálculo de la magnitud del paso (LSB - Least Significant Bit)
        double pasoVoltaje = tensionReferenciaVoltios / Math.pow(2, resolucionBits);
        return valorDigital * pasoVoltaje;
    }

    public int getPinDigital() {
        return pinDigital;
    }

    @Override
    public String toString() {
        return "Canal ADC en Pin " + pinDigital + " [" + resolucionBits + "-bit]";
    }
}
