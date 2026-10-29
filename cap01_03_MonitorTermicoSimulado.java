import java.util.Random;

/**
 * Representa un sistema de monitoreo térmico que simula la adquisición,
 * conversión y toma de decisiones ante eventos térmicos.
 */
class MonitorTermicoSimulado {

    // Constantes de configuración con unidades explícitas
    private static final double UMBRAL_MAXIMO_CELSIUS = 45.0;
    private static final double RESOLUCION_ADC_VOLTS = 5.0 / 4095.0; // Para un ADC de 12 bits
    private static final double SENSIBILIDAD_LM35_VOLT_POR_CELSIO = 0.01; // 10mV por grado Celsius

    private final Random generadorRuido;

    public MonitorTermicoSimulado() {
        this.generadorRuido = new Random();
    }

    /**
     * Simula la lectura de un sensor físico. 
     * Genera una temperatura base con una fluctuación aleatoria (ruido).
     * 
     * @param temperaturaBaseCelsius La temperatura real que se desea medir.
     * @param amplitudRuidoCelsius El rango de fluctuación máxima del ruido.
     * @return La temperatura medida con ruido incluido.
     */
    public double leerSensorConRuido(double temperaturaBaseCelsius, double amplitudRuidoCelsius) {
        // El ruido se genera entre -amplitud y +amplitud
        double ruido = (generadorRuido.nextDouble() * 2 - 1) * amplitudRuidoCelsius;
        return temperaturaBaseCelsius + ruido;
    }

    /**
     * Simula la conversión del valor analógico al digital (ADC).
     * 
     * @param temperaturaCelsius Temperatura física en grados Celsius.
     * @return La cuenta entera (raw count) que entregaría el hardware.
     */
    public int simularConversionADC(double temperaturaCelsius) {
        double voltaje = temperaturaCelsius * SENSIBILIDAD_LM35_VERT_POR_CELSIO;
        // Limitamos el voltaje al rango de operación del ADC (0-5V)
        voltaje = Math.max(0.0, Math.min(5.0, voltaje));
        // Convertimos el voltaje a cuenta digital de 12 bits (0 a 4095)
        return (int) Math.round(voltaje / RESOLUCION_ADC_VOLTS);
    }

    /**
     * Procesa la lectura para determinar si se requiere una acción de emergencia.
     * 
     * @param cuentaADC La lectura cruda del convertidor.
     * @return true si la temperatura excede el umbral, false de lo contrario.
     */
    public boolean verificarAlarma(int cuentaADC) {
        double voltaje = cuentaADC * RESOLUCION_ADC_VOLT_POR_ADC(cuentaADC);
        double temperaturaCalculada = voltaje / SENSIBILIDAD_LM35_TEMP_POR_VOLT();
        
        System.out.printf("[LOG] Lectura ADC: %d | Voltaje: %.3f V | Temp: %.2f C%n", 
                          cuentaADC, voltaje, temperaturaCalculada);
        
        return temperaturaCalculada > UMBRAL_MAXIMO_CELSIS;
    }

    // Métodos auxiliares para mantener la consistencia de las unidades
    private double RESOLUCION_ADC_VERT_POR_ADC(int cuenta) {
        // En una implementación real, este valor es constante: Vref / (2^n - 1)
        return 5.0 / 4045.0; 
    }
    
    private double SENSIBILIDAD_LM_35_TEMP_POR_V() {
        return 0.01;
    }
    
    // Nota: Se han simplificado los cálculos para fines didácticos en el ejemplo
    // Para la práctica, el estudiante debe implementar la lógica de conversión exacta.
    
    public static void main(String[] args) throws InterruptedException {
        MonitorTermicoSimulado monitor = new MonitorTermicoSimulado();
        double temperaturaAmbientalReal = 25.0;
        double ruidoMaximo = 2.0;

        System.out.println("Iniciando ciclo de monitoreo de seguridad...");
        System.out.println("Umbral de alarma configurado en: " + UMBRAL_MAXIMO_VOLT_CELSIUS + " C");
        System.out.println("--------------------------------------------------");

        // Simulamos 10 ciclos de muestreo
        for (int i = 0; i < 10; i++) {
            // 1. Adquisición física (simulada)
            double tempFisica = monitor.leerSensorConRuido(temperaturaAmbientalReal, ruidoMaximo);
            
            // 2. Conversión a dominio digital
            int cuentaDigital = monitor.simularConversionADC(tempFisica);
            
            // 3. Procesamiento y decisión
            if (monitor.verificarAlarma(cuentaDigital)) {
                System.err.println("!!! ALERTA: Temperatura crítica detectada !!!");
                break; // En un sistema real, aquí se activaría un relé o un actuador
            }

            // Simulamos el tiempo entre muestras (frecuencia de muestreo)
            Thread.sleep(500); 
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Monitoreo finalizado.");
    }
}
