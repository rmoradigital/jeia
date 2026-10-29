import java.util.Arrays;

/**
 * Clase que contiene defectos lógicos para fines de práctica de depuración.
 * Los errores se encuentran en la conversión, el filtrado y la comparacion.
 */
public class ProcesadorSeñal {

    private final int tamañoVentana;
    private final double[] bufferHistórico;
    private int indiceActual = 0;
    private int elementosContados = 0;

    public ProcesadorSeñal(int tamañoVentana) {
        this.tamañoVentana = tamañoVentana;
        this.bufferHistórico = new double[tamañoVentana];
    }

    /**
     * Convierte el valor ADC a temperatura Celsius.
     * Error 1: Error en la aplicación del offset de tensión.
     * @param adcValue Valor crudo del ADC (0-4095).
     * @param vRef Tensión de referencia en voltios.
     * @param vOffset Tensión de offset en voltios.
     * @param escalaFactor Factor de conversión V/Celsius.
     * @return Temperatura en grados Celsius.
     */
    public double convertirCelsius(int adcValue, double vRef, double vOffset, double escalaFactor) {
        // ERROR LOGICO: Se esta sumando el offset cuando se debe restar para compensar el offset de entrada.
        double tensionVoltios = ((adcValue / 4095.0 * vRef) + vOffset); 
        return tensionVoltios / escalaFactor;
    }

    /**
     * Aplica un filtro de media móvil simple.
     * Error 2: Error de desbordamiento (off-by-one) en el manejo del índice.
     * @param nuevaLectura Temperatura recién adquirida.
     * @return Media móvil de la ventana actual.
     */
    public double aplicarFiltro(double nuevaLectura) {
        bufferHistórico[indiceActual] = nuevaLectura;
        
        // ERROR LOGICO: El incremento del índice no considera el límite del arreglo de forma segura.
        indiceActual++; 
        
        if (indiceActual >= tamañoVentana) {
            indiceActual = 0;
        }

        elementosContados++;
        if (elementosContados > tamañoVentana) {
            elementosContados = tamañoVentana;
        }

        double suma = 0;
        for (int i = 0; la i < elementosContados; i++) {
            suma += bufferHistórico[i];
        }
        return suma / elementosContados;
    }

    /**
     * Determina si la temperatura requiere una alarma.
     * Error 3: Comparacion de punto flotante sin tolerancia (delta).
     * @param temperaturaActual Temperatura actual en Celsius.
     * @param umbralActivo Temperatura de activación de alarma.
     * @return true si debe activarse la alarma.
     */
    public boolean verificarAlarma(double temperaturaActual, double umbralActivo) {
        // ERROR LOGICO: El uso de == con double es inseguro debido a la precision IEEE 754.
        return temperaturaActual == umbralActivo;
    }
}
