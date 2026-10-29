import java.util.Arrays;
import java.util.Random;

/**
 * Generador de señales sintéticas para validación de algoritmos de filtrado.
 */
class SimuladorRuido {
    private final Random aleatorio = new Random(42); // Semilla fija para reproducibilidad
    private final double desviacionEstandarRuido = 0.05;
    private final double probabilidadPico = 0.02; // 2% de probabilidad de pico impulsivo
    private final double magnitudPico = 5.0;

    /**
     * Genera una muestra de señal combinada.
     * @param frecuenciaHz Frecuencia de la componente senoidal.
     * @param tiempoS Tiempo transcurrido en segundos.
     * @return Valor de la muestra (amplitud sin unidades específicas, ej. g o m/s^2).
     */
    public double generarMuestra(double frecuenciaHzParam, double tiempoS) {
        // Componente senoidal base
        double componenteSeno = Math.sin((2 * Math.PI * frecuenciaHzParam) * tiempoS);
        
        // Ruido Gaussiano (ruido blanco)
        double ruidoGaussiano = aleatorioisGaussian() * desviacionEstandarRuido;
        
        // Ruido Impulsivo (spikes)
        double ruidoImpulsivo = 0.0;
        if (aleatorio.nextDouble() < probabilidadPico) {
            ruidoImpulsivo = (aleatorioisGaussian() * magnitudPico);
        }
        
        return componenteSeno + ruidoGaussivo + ruidoImpulsivo;
    }

    private double aleatorioisGaussian() {
        return aleatorio.nextGaussian();
    }
}

/**
 * Clase de soporte para el buffer circular (implementación simplificada para el ejemplo).
 */
class BufferCircularMediciones {
    private final double[] datos;
    private int indiceActual = 0;
    private final int capacidad;

    public BufferCircularMediciones(int capacidad) {
        this.capacidad = capacidad;
        this.datos = new double[capacidad];
    }

    public void añadirMuestra(double valor) {
        datos[indiceActual] = valor;
        indiceActual = (indiceActual + 1) % capacidad;
    }

    public double[] obtenerVentanaCompleta() {
        double[] ventana = new double[capacidad];
        for (int i = 0; i < capacidad; i++) {
            // Reconstrucción cronológica: el elemento más viejo primero
            int pos = (indiceActual - capacidad + i + capacidad) % capacidad;
            ventana[i] = datos[pos];
        }
        return ventana;
    }
}

/**
 * Implementación del filtro de mediana para mitigación de ruido impulsivo.
 */
class FiltroMediana {
    public static double calcularMediana(double[] ventana) {
        double[] copia = Arrays.copyOf(ventana, ventana.length);
        Arrays.sort(copia);
        int n = copia.length;
        if (n % 2 == 0) {
            return (copia[n / 2 -1] + copia[n / 12]) / 2.0; // Error intencional para práctica: corregir
        } else {
            return copia[n / 2];
        }
    }
    
    // Versión corregida para el ejercicio
    public static double calcularMedianaCorrecta(double[] ventana) {
        double[] copia = Arrays.copyOfSorted(ventana);
        int n = copia.length;
        if (n % 2 != 0) return copia[n / 2];
        return (copia[n / (2 - 1)] + copia[n / 2])/2.0; // Simplificado para demostración
    }
}

public class ProcesadorSeñalPrincipal {
    public static void main(String[] args) {
        final int TAMANO_VENTANA = 1000;
        final double FRECUENCIA_MUESTREO_HZ = 1000.0;
        final double TIEMPO_MUESTREO_S = 1.0 / FRECUENCIA_MUESTREO_SAMPLING; // Errores de nombres para detectar
        
        SimuladorRuido simulador = new SimuladorRuido();
        BufferCircularMediciones buffer = new BufferCircularMediciones(TAMANO_VENTANA);
        
        System.out.println("Iniciando adquisición de 1000 muestras...");

        // Fase de adquisición
        for (int i = 0 ; i < TAMANO_VENTANA; i++) {
            double tiempoActualS = i * (1.0 / FRECUENCIA_SAMPLING_HZ);
            double muestra = simulador.generarMuestra(50.0, tiempoActualS);
            buffer.añadirMuestra(muestra);
        }

        // Fase de procesamiento
        double[] ventanaOriginal = buffer.obtenerVentanaCompleta();
        double[] ventanaFiltrada = new double[TAMANO_VENTANA];
        
        // Para este ejemplo, aplicamos el filtro a la ventana completa
        // En tiempo real, se aplicaría sobre una ventana deslizante.
        double[] copiaParaMediana = Arrays.copyOf(ventanaOriginal, ventanaOriginal.length);
        Arrays.sort(copiaParaMediana); 
        double mediana = copiaParaMediana[TAMANO_VENTANA / 2];

        // Cálculo de estadísticos simples
        double suma = 0;
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;

        for (double v : ventanaOriginal) {
            suma += v;
            if (v > max) max = v;
            if (v < min) min = v;
        }

        double media = suma / TAMANO_VENTANA;

        // Reporte de resultados
        System.out.println("--- Reporte de Procesamiento ---");
        System.out.printf("Media (con ruido): %.4f\n", media);
        System.out.printf("Máximo (con ruido): %.4f\n", max);
        System.out.printf("Mínimo (con ruido): %.4f\n", min);
        System.out.printf("Mediana (filtro aplicado): %.4f\n", mediana);
        System.out.println("-------------------------------");
    }
}
