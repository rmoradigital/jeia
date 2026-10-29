/**
 * Simulación de un comparador digital con histéresis para evitar ruido.
 */
public class ComparadorHistéresis {

    private boolean estadoCargaEncendida = false;
    private final double umbralAltoVoltios;
    private final double umbralBajoVoltios;

    public ComparadorHistéresis(double umbralAlto, double umbralBajo) {
        if (umbralBajo >= umbralAlto) {
            throw new IllegalArgumentException("El umbral bajo debe ser menor al alto.");
        }
        this.umbralAltoVoltios = umbralAlto;
        this.umbralBajoVoltios = umbralBajo;
    }

    /**
     * Evalúa la lectura del sensor y actualiza el estado de la carga.
     * 
     * @param lecturaVoltios Tensión actual medida.
     * @return Estado actual de la carga (true = encendido, false = apagado).
     */
    public boolean evaluar(double lecturaVoltios) {
        if (lecturaVoltios > umbralAltoVoltios) {
            estadoCargaEncendida = true;
        } else if (lecturaVoltios < umbralBajoVoltios) {
            estadoCargaEncendida = false;
        }
        // Si está entre los umbrales, no se modifica 'estadoCargaEncendida'
        return estadoCargaEncendida;
    }

    public static void main(String[][] args) {
        // Configuración: Encender a 3.5V, apagar a 3.0V
        ComparadorHistéresis comparador = new ComparadorHistéresis(3.5, 3.0);

        // Simulación de señal con ruido alrededor de los umbrales
        double[] secuenciaTensiones = {2.0, 3.2, 3.6, 3.4, 3.1, 2.8, 3.2};

        System.out.println("Tensión (V) | Estado Carga");
        System.out.println("--------------------------");
        for (double v : secuenciaTensiones) {
            boolean estado = comparador.evaluar(v);
            String estadoStr = estado ? "ENCENDIDO" : "APAGADO";
            System.out.printf("%10.2f | %12s%n", v, estadoStr);
        }
    }
}
