/**
 * Clase que implementa el modelo de calibración lineal para sensores.
 * Permite transformar valores crudos de ADC a unidades físicas reales.
 */
public class CalibradorSensor {

    // Atributos que representan la ecuación y = mx + b
    private final double pendiente;
    private final double ordenadaAlOrigen;
    private final String unidadMedida;

    /**
     * Constructor para inicializar los parámetros de calibración.
     * 
     * @param pendiente Sensibilidad del sensor (cambio en unidad por cambio en ADC).
     * @param offset Desplazamiento o intercepto en el punto cero.
     * @param unidad Nombre de la unidad física (ej. "Voltios").
     */
    public CalibradorSensor(double pendiente, double offset, String unidad) {
        this.pendiente = pendiente;
        this.ordenadaAlOrigen = offset;
        this.unidadMedida = unidad;
    }

    /**
     * Realiza la conversión de un valor crudo a la unidad física.
     * 
     * @param valorCrudo Lectura digital obtenida del ADC.
     * @return Magnitud física calculada en la unidad correspondiente.
     */
    public double calcularValorFisico(int valorCrudo) {
        // Aplicación de la fórmula: y = m * x + b
        return (pendiente * valorCrudo) + ordenadaAlOrigen;
    }

    public static void main(String, args) {
        // Configuración de un sensor de temperatura hipotético.
        // Supongamos que para un ADC de 10 bits (0-1023):
        // 0 ADC = 0.0 Celsius
        // 1023 ADC = 100.0 Celsius
        // Pendiente m = (100 - 0) / (1023 - 0) = 0.09775
        double m = 0.09774926686;
        double b = 0.0;
        
        CalibradorSensor termometro = new CalibradorSensor(m, b, "Celsius");

        // Simulamos tres lecturas de un sensor
        int[] lecturasADC = {512, 1023, 0};

        System.out.println("--- Reporte de Calibración de Temperatura ---");
        for (int lectura : lecturasADC) {
            double temperaturaCelsius = termometro.calcularValorFisico(lectura);
            System.out.printf("Lectura ADC: %4d | Temperatura: %.2f %s%n", 
                              lectura, temperaturaCelsius, termometro.unidadMedida);
        }
    }
}
