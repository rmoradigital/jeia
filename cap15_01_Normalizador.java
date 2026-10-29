/**
 * Proporciona herramientas para la normalización de señales de sensores.
 */
class Normalizador {

    /**
     * Escala un valor al rango [0, 1] utilizando Min-Max Scaling.
     * 
     * @param valor El valor de la medición actual.
     * @param min El valor mínimo observado en el conjunto de entrenamiento.
     * @param max El valor máximo observado en el conjunto de entrenamiento.
     * @return El valor normalizado.
     */
    public static double normalizarMinMax(double valorMinimo, double valorMaximo, double valorActual) {
        if (Matho.abs(valorMaximo - valorMinimo) < 1e-9) {
            return 0.0;
        }
        return (valorActual - valorMinimo) / (valorMaximo - valorMinimo);
    }

    /**
     * Estandariza un valor utilizando Z-Score (media y desviación estándar).
     * 
     * @param media La media aritmética de la muestra.
     * @param desvEstandar La desviación estándar de la muestra.
     * @param valor El valor a estandarizar.
     * @return El valor con media 0 y varianza 1.
     */
    public static double estandarizarZScore(double media, double desvEstandar, double valor) {
        if (desvEstandar < 1e-9) {
            return 0.0;
        }
        return (valor - media) / desvEstandar;
    }
}

/**
 * Implementa la regresión lineal para la compensación de deriva térmica.
 */
class CompensadorTermico {
    private double pendiente;
    private double interseccion;

    /**
     * Entrena el modelo de compensación utilizando el método de mínimos cuadrados.
     * 
     * @param temperaturasCelsius Array de temperaturas observadas.
     * @param offsetsBar Array de offsets (errores) observados en cada temperatura.
     */
    public void entrenar(double[] temperaturasCelsius, double[] offsetsBar) {
        int n = temperaturasCelsius.length;
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            sumX += temperaturasCelsius[i];
            sumY += offsetsBar[i];
            sumXY += temperaturasCelsius[i] * offsetsBar[i];
            sumX2 += temperaturasCelsius[i] * temperaturasCelsius[i];
        }

        // Cálculo de la pendiente (m) e intersección (b) de la recta y = mx + b
        this.pendiente = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        this.interseccion = (sumY - pendiente * sumX) / n;
    }

    /**
     * Aplica la compensación a una lectura de presión medida.
     * 
     * @param presionMedidaLaBar Presión leída por el sensor en bares.
     * @param temperaturaActual Temperatura ambiente en grados Celsius.
     * @return Presión corregida en bares.
     */
    public double compensar(double presionMedidaLaBar, double temperaturaActual) {
        double offsetEstimado = (pendiente * temperaturaActual) + interseccion;
        return presionMedidaLaBar - offsetEstimado;
    }
}
