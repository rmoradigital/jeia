/**
 * Clase que gestiona la conversión de datos digitales provenientes de un ADC
 * a magnitudes físicas interpretables.
 * 
 * @author Ingeniero de Software
 */
public class ConversorInstrumental {

    // Constantes de hardware para un ADC de 12 bits y referencia de 5V
    private static final int RESOLUCION_BITS = 12;
    private static final double NIVELES_ADC = Math.pow(2, RESOLUCION_BITS);
    private static final double TENSION_REFERENCIA_V = 5.0;

    // Coeficientes para un sensor de temperatura LM35 (lineal: 10mV por cada grado Celsius)
    // La fórmula es: TemperaturaCelsius = TensiónVoltios / 0.01
    private static final double COEF_LM35_V_POR_GRADO = 0.01;

    /**
     * Convierte el valor entero obtenido del ADC en tensión eléctrica.
     * 
     * @param cuentaADC El valor digital sin signo obtenido del ADC (0 a 4095 para 12 bits).
     * @return La tensión correspondiente en voltios.
     */
    public double convertirCuentaAVoltios(int cuentaADC) {
        // Validación de rango para asegurar la integridad del cálculo
        if (cuentaADC < 0 || cuentaADC >= NIVELES_ADC) {
            throw new IllegalArgumentException("La cuenta ADC está fuera del rango permitido para 12 bits.");
        }
        
        // Cálculo de la tensión: (Cuenta / Total de Niveles) * V_Ref
        return (cuentaADC / NIVELES_ADC) * TENSION_REFERENCIA_V;
    }

    /**
     * Convierte una lectura de tensión en temperatura Celsius para un sensor LM35.
     * 
     * @param tensionVoltios La tensión medida en voltios.
     * @return La temperatura en grados Celsius.
     */
    public double convertirVoltiosATemperatura(double tensionVoltios) {
        // Aplicación de la transferencia: T = V / sensibilidad
        return tensionVoltios / COEF_LM35_POR_GRADO;
    }

    /**
     * Método de prueba para simular la adquisición de datos.
     */
    public static void main(String[] args) {
        ConversorInstrumental conversor = new ConversorInstrumental();

        // Simulación de una secuencia de lecturas de un sensor de temperatura
        // Representan valores crecientes de la cuenta ADC
        int[] lecturasSimuladasADC = {1024, 2048, 3072, 4000};

        System.out.println("Iniciando monitorización de sensor térmico...");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-15s | %-15s | %-20s%n", "Cuenta ADC", "Tensión (V)", "Temperatura (C)");
        System.out.println("--------------------------------------------------");

        for (int cuenta : lecturasSimuladasADC) {
            try {
                double voltaje = conversor.convertirCuentaAVoltios(cuenta);
                double temperatura = conversor.convertirVoltiosATemperatura(voltaje);

                System.out.printf("%-15d | %-15.4f | %-20.2f%n", 
                                  cuenta, voltaje, temperatura);
            } catch (IllegalArgumentException e) {
                System.err.println("Error en la lectura: " + e.getMessage());
            }
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Monitorización finalizada.");
    }
}
