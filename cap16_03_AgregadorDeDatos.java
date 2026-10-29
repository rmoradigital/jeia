import java.util.ArrayList;
import java.util.List;

class AgregadorDeDatos {
    private final List<Double> bufferMuestras = new ArrayList<>();
    private final int tamanoVentana;

    public AgregadorDeDatos(int tamanoVentana) {
        this.tamanoVentana = tamanoVentana;
    }

    public void agregarMuestra(double valorCelsius) {
        bufferMuestras.add(valorCelsius);
    }

    public boolean estaVentanaCompleta() {
        return bufferMuestras.size() >= tamanoVentana;
    }

    public ResumenEstadistico calcularResumen() {
        double suma = 0;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for (double v : bufferMuestras) {
            suma += v;
            if (v > max) max = v;
            if (v < min) min = v;
        }

        double media = suma / bufferMuestras.size();
        
        // Cálculo simplificado de desviación estándar
        double varianza = 0;
        for (double v : bufferMuestras) {
            varianza += Math.pow(v - media, 2);
        }
        double desv = Math.sqrt(varianza / bufferMuestras.size());

        // Cálculo de pendiente (asumiendo muestreo constante)
        double pendiente = (max - min) / tamanoVentana;

        bufferMuestras.clear(); // Limpiar para la siguiente ventana
        return new ResumenEstadistico(media, desv, max, min, pendiente);
    }
}
