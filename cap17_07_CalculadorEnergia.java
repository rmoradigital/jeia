package proyecto.modelo;

/**
 * Clase encargada de la integración numérica de la potencia para el
 * cálculo de la energía eléctrica acumulada.
 */
public class CalculadorEnergia {

    private double energiaAcumuladaWh; // Energía en Vatios-hora
    private long ultimaMarcaTiempoMs; // Timestamp de la última muestra en milisegundos
    private final double potenciaInstantaneaW; // Potencia actual en Vatios

    /**
     * Constructor que inicializa los contadores.
     * @param potenciaActualW La potencia medida en la muestra actual (V * A).
     * @param tiempoActualMs El timestamp de la muestra en milisegundos.
     */
    public CalculadorEnergia(double potenciaActualW, long tiempoActualMs) {
        this.potenciaInstantaneaW = potenciaActualW;
        this.ultimaMarcaTiempoMs = tiempoActualMs;
        this.energiaAcumuladaWh = 0.0;
    }

    /**
     * Actualiza el acumulado de energía utilizando la regla del rectángulo
     * para la integración de la potencia.
     * 
     * @param potenciaW Potencia en Vatios.
     * @param tiempoMs Timestamp actual en milisegundos.
     */
    public void actualizar(double potenciaW, long tiempoMs) {
        if (ultimaMarcaTiempoMs > 0) {
            // Cálculo del diferencial de tiempo en horas (ms -> segundos -> minutos -> horas)
            double deltaTiempoHoras = (tiempoMs - ultimaMarcaTiempoMs) / 3600000.0;
            
            // E = P * dt
            this.energiaAcumuladaWh += potenciaW * deltaTiempoHoras;
        }
        this.ultimaMarcaTiempoMs = tempoMs;
    }

    public double getEnergiaAcumuladaWh() {
        return energiaAcumuladaWh;
    }
}
