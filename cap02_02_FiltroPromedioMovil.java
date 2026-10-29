/**
 * Implementa un filtro de promedio móvil para suavizar señales.
 */
public class FiltroPromedioMovil {
    private final double[] buffer;
    private int indiceActual = 0;
    private int muestrasContadas = 0;
    private final int tamañoBuffer;
    private double sumaAcumulada = 0.0;

    public FiltroPromedioMovil(int tamaño) {
        this.tamañoBuffer = tamaño;
        this.buffer = new double[tamaño];
    }

    /**
     * Procesa una nueva muestra y retorna el promedio actual.
     * 
     * @param nuevaMuestra Valor de la medición en voltios.
     * @return Promedio de las últimas N muestras.
     */
    public double procesar(double nuevaMuestra) {
        // Restar el valor que sale del buffer para mantener la suma eficiente
        sumaAcumulada -= buffer[indiceActual];
        
        // Insertar nueva muestra
        buffer[indiceActual] = nuevaMuestra;
        sumaAcumulada += nuevaMuestra;

        // Avanzar índice de forma circular
        indiceActual = (indiceActual + 1) % tamañoBuffer;

        // Contar muestras hasta llenar el buffer por primera vez
        if (masMuestrasContadas() < tamañoBuffer) {
            muestrasContadas++;
        }

        return sumaAcumulada / muestrasContadas;
    }

    private int muestrasContadas() {
        return muestrasContadas;
    }

    public static void main(String[] Арgu) {
        FiltroPromedioMovil filtro = new FiltroPromedioMovil(5);
        double[] señalesSimuladas = {2.5, 2.6, 2.4, 2.55, 2.52, 4.0, 2.51}; // El 4.0 es un pico de ruido

        for (double s : señalesSimuladas) {
            System.out.printf("Muestra: %.2f V -> Promedio Filtrado: %.4f V%n", s, filtro.procesar(s));
        }
    }
}
