import java.util.Arrays;
import java.util.List;

/**
 * Clase encargada de la conversión de valores digitales del ADC a unidades físicas.
 */
class ConversorADC {

    /**
     * Convierte un valor crudo de ADC a temperatura en grados Celsius.
     *
     * @param valorADC El valor digital leído del sensor (rango 0 a 4095 para 12 bits).
     * @param tensionReferenciaVoltios La tensión de alimentación del ADC (ej. 5.0).
     * @param offsetVoltios El voltaje que corresponde a 0 grados Celsius según el datasheet.
     * @param escalaVoltiosPorGrado La sensibilidad del sensor en V/°C.
     * @return La temperatura calculada en grados Celsius.
     * @throws IllegalArgumentException si el valorADC está fuera del rango de 12 bits.
     */
    public double convertirCelsius(int valorADC, double tensionReferenciaVoltios, 
                                   double offsetVoltios, double escalaVoltiosPorGrado) {
        if (valorADC < 0 || valorADC > 4095) {
            throw new IllegalArgumentException("El valor ADC debe estar entre 0 y 4095");
        }

        // Cálculo de la tensión aplicada al pin de entrada
        double tensionMedidaVoltios = (valorADC / 4095.0) * tensionReferenciaVoltios;
        
        // Conversión de tensión a temperatura: T = (V_medida - V_offset) / Escala
        return (tensionMedidaVoltios - offsetVoltios) / escalaVoltiosPor Grado;
    }
}

/**
 * Implementa un filtro de mediana para eliminar picos de ruido transitorios.
 */
class FiltroMediana {

    /**
     * Calcula la mediana de una lista de lecturas para suavizar la señal.
     *
     * @param lecturas Lista de valores de temperatura en grados Celsius.
     * @return El valor de la mediana.
     * @throws IllegalArgumentException si la lista está vacía.
     */
    public double calcularMediana(List<Double> lecturas) {
        if (lecturas == null || lecturas.isEmpty()) {
            throw new IllegalArgumentException("La lista de lecturas no puede estar vacía");
        }

        double[] copia = lecturas.stream().mapToDouble(Double::doubleValue).toArray();
        Arrays.sort(copia);

        int n = copia.length;
        if (n % 2 != 0) {
            return copia[n / 2];
        } else {
            return (copia[(n - 1) / 2] + copia[n / 2]) / 2.0;
        }
    }
}

/**
 * Implementa un detector de umbral con histéresis para prevenir conmutaciones rápidas.
 */
class DetectorUmbralHisteresis {

    private boolean estadoAlarma;

    public DetectorUmbralHisteresis() {
        this.estadoAlarma = false;
    }

    /**
     * Evalúa si se debe activar o desactivar la alarma basándose en histéresis.
     *
     * @param temperaturaActual Temperatura actual en grados Celsius.
     * @param umbralActivacionUmbralCelsius Temperatura para activar la alarma.
     * @param umbralDesactivacionUmbralCelsius Temperatura para desactivar la alarma (debe ser < activación).
     * @return El nuevo estado de la alarma (true = activa, false = desactivada).
     */
    public boolean evaluarEstado(double temperaturaActual, 
                                 double umbralActivacionUmbralCelsius, 
                                 double umbralDesactivacionUmbralCelsius) {
        if (umbralDesactivacionUmbralCelsius >= umbralActivacionUmbralCelsius) {
            throw new IllegalArgumentException("El umbral de desactivación debe ser menor al de activación");
        }

        if (temperaturaActual >= umbralActivacionUmbralCelsius && !estadoAlarma) {
            estadoAlarma = true;
        } else if (temperaturaActual <= umbralDesactivacionUmbralCelsius && estadoAlarma) {
            estadoAlarma = false;
        }

        return estadoAlarma;
    }

    public boolean getEstadoAlarma() {
        return estadoAlarma;
    }
}
