/**
 * Clase que contiene métodos para la conversión de señales 
 * y cálculos eléctricos fundamentales.
 */
public class CalculosInstrumentacion {

    /**
     * Convierte una lectura cruda de ADC a tensión en voltios.
     * 
     * @param valorADC El valor entero obtenido del conversor (0 a resoluciónMáxima).
     * @param tensionReferenciaVoltios El voltaje de referencia del sistema (ej. 5.0).
     * @param resolucionBits El número de bits del ADC (ej. 10, 12).
     * @return La tensión calculada en voltios.
     */
    public static double convertirADCtoVoltios(int valorADC, double tensionReferenciaVoltios, int resolucionBits) {
        double resolucionMaxima = Math.pow(2, resolucionBits) - 1;
        // Se utiliza double para asegurar que la división no sea entera
        return (valorADC / resolucionMaxima) * tensionReferenciaVoltios;
    }

    /**
     * Calcula la potencia eléctrica disipada en una resistencia.
     * 
     * @param tensionVoltios Tensión en voltios.
     * @param resistenciaOhmios Resistencia en ohmios.
     * @return Potencia calculada en vatios (Watts).
     */
    public static double calcularPotencia(double tensionVoltios, double resistenciaOhmios) {
        // P = V^2 / R
        return (tensionVoltios * tensionVoltios) / resistenciaOhmios;
    }

    public static void main(String[] args) {
        // Parámetros del sistema
        final double V_REF = 5.0;
        final int BITS_ADC = 12;
        final double R_CARGA = 100.0; // 100 Ohmios

        // Simulación de una lectura de ADC ruidosa
        int lecturaCruda = 2048; 

        double tension = convertirADCtoVoltios(lecturaCruda, V_REF, BITS_ADC);
        double potencia = calcularPotencia(tension, R_CARGA);

        System.out.println("Lectura ADC: " + lecturaCruda);
        System.out.println("Tensión calculada: " + tension + " V");
        System.out.println("Potencia disipada: " + potencia + " W");
    }
}
