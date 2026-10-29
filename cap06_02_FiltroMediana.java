import java.util.Arrays;

/**
 * Implementa un filtro digital de mediana para la eliminación 
 * de ruido impulsivo en series temporales.
 */
public class FiltroMediana {

    /**
     * Calcula la mediana de un conjunto de datos.
     * @param datos Arreglo de muestras.
     * @return El valor de la mediana.
     */
    public static double calcularMediana(double... datos) {
        if (datos == null || datos.length == 0) {
            return 0.0;
        }

        // Se crea una copia para no alterar el orden del buffer original
        double[] copia = Arrays.copyOf(datos, datos.length);
        // El ordenamiento es necesario para encontrar la mediana
        Arrays.sort(copia);

        int n = copia.length;
        if (n % 2 == 0) {
            // Si el número de elementos es par, se promedia el centro
            return (copia[n / 2 - 1] + copia[n / 2]) / 2.0;
        } else {
            // Si es impar, se toma el elemento central
            return copia[n / 2];
        }
    }
}
