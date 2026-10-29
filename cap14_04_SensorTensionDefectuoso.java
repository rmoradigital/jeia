/**
 * MÓDULO DEFECTUOSO: Sensor de Tensión
 * Este código contiene errores de concurrencia, gestión de recursos
 * y precisión numérica que deben ser identificados.
 */
public class SensorTensionDefectuoso {
    private boolean estaCorriendo = true;
    private double ultimaTensionVoltios = 0.0;

    // Simulación de lectura de hardware
    public void iniciarAdquisicion() {
        new Thread(() -> {
            while (estaCorriendo) {
                try {
                    // Simula el tiempo de muestreo de 100ms
                    Thread.sleep(100);
                    // Error de precisión: cálculo con división entera implícita
                    int lecturaCruda = (int) (Math.random() * 5000); 
                    ultimaTensionVoltios = lecturaCruda / 500.0; 
                } catch (InterruptedException e) {
                    // Error: el hilo no se detiene correctamente al capturar la excepción
                }
            }
        }).start();
    }

    public double obtenerUltimaLectura() {
        return ultimaTensionVoltios;
    }

    public void detener() {
        estaCorriendo = false;
    }
}
