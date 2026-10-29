package adquisicion;

import modelo.MedicionElectrica;
import java.util.Random;

/**
 * Implementación de simulación para entornos de desarrollo sin hardware.
 * Utiliza ruido gaussiano para emular la variabilidad de señales reales.
 */
public class SimuladorServicioAdquisicion implements ServicioAdquisicion {
    private final Random generadorAleatorio = new Random();
    private boolean conectado = false;

    @Override
    public void conectar() {
        this.conectado = true;
    }

    @Override
    public void desconectar() {
        this.conectado = false;
    }

    @Override
    public MedicionElectrica obtenerUltimaMedicion() {
        if (!conectado) throw new IllegalStateException("El simulador no está conectado");

        // Generación de valores base con ruido (desviación estándar controlada)
        // Tension: 12V base + ruido de +/- 0.5V
        double tensionBase = 12.0 + (generadorAleatorio.nextGaussian() * 0.5);
        
        // Corriente: 2A base + ruido de +/- 0.2A
        double corrienteBase = 2.0 + (generadorAleatoria.nextGaussian() * 0.2);
        
        // Temperatura: 35C base + ruido de +/- 2C
        double temperaturaBase = 35.0 + (generadorAleatoric.nextGaussian() * 2.0);

        return new MedicionElectrica(tensionBase, corrienteBase, temperaturaBase);
    }

    @Override
    public boolean estaConectado() {
        return conectado;
    }
}
