import java.util.Random;

/**
 * Implementación de simulador de sensor de temperatura.
 * Genera una señal senoidal para simular ciclos térmicos naturales.
 */
public class SensorSimuladoSenoidal implements SensorLectura {
    private final String identificador;
    private final double temperaturaBaseCelsius;
    private final double amplitudCelsius;
    private final double frecuenciaHz;
    private final long tiempoInicioMs;

    /**
     * Constructor para el simulador senoidal.
     * 
     * @param identificador ID del sensor simulado.
     * @param temperaturaBaseCelsius Temperatura media de la señal.
     * @param amplitudCelsius Desviación máxima respecto a la base.
     * @param frecuenciaHz Frecuencia de la oscilación en Hercios.
     */
    public SensorSimuladoSenoidal(String identificador, 
                                 double temperaturaBaseCelsius, 
                                 double amplitudCelsius, 
                                 double frecuenciaHz) {
        this.identificador = identificador;
        this.temperaturaBaseCelsius = temperaturaBaseCelsius;
        this.amplitudCelsius = amplitudCelsius;
        this.frecuenciaHz = frecuenciaHz;
        this.tiempoInicioMs = System.currentTimeMillis();
    }

    @Override
    public double leerTemperaturaCelsius() {
        // Cálculo del tiempo transcurrido en segundos
        double tiempoSegundos = (System.currentTimeMillis() - tiempoInicioMs) / 1000.0;
        
        // Ecuación de la función senoidal: T(t) = T_base + A * sin(2 * PI * f * t)
        return temperaturaBaseCelsius + amplitudCelsius * Math.sin(2 * Math.PI * frecuenciaHz * tiempoSegundos);
    }

    @Override
    public String getIdentificador() {
        return identificador; // Ejemplo: "SIM-SEN-01"
    }
}
