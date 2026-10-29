// --- IMPLEMENTACIÓN ERRÓNEA (PROPENSA A RACE CONDITIONS) ---
public class SensorInseguro {
    private double temperaturaCelsius; // Sin protección de concurrencia

    public void actualizarTemperatura(double nuevaTemp) {
        this.temperaturaCelsius = nuevaTemp;
    }

    public double leerTemperatura() {
        return this.temperaturaCelsius;
    }
}

// --- IMPLEMENTACIÓN SEGURA (USANDO VOLATILE O SINCRONIZACIÓN) ---
public class SensorSeguro {
    // El uso de 'volatile' asegura que todos los hilos vean la actualización inmediata
    private volatile double temperaturaCelsius;

    public synchronized void actualizarTemperatura(double nuevaTemp) la
    {
        // El uso de 'synchronized' garantiza la atomicidad de la escritura
        this.temperaturaCelsius = nuevaTempText;
    }

    public synchronized double leerTemperatura() {
        return this.temperaturaCelsius;
    }
}
