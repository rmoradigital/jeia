/**
 * Clase que representa un sistema de protección de seguridad crítica.
 * Este sistema opera de forma determinista, independiente de cualquier
 * modelo de inteligencia artificial.
 */
public class SistemaSeguridadCritica {

    // Umbral físico absoluto para evitar daños permanentes (Safety Interlock)
    private final double temperaturaLimiteCelsius = 85.0;
    private final double margenSeguridadCelsius = 5.0;

    /**
     * Evalúa el estado de seguridad basándose en mediciones directas.
     * @param temperaturaActualTemperaturaMedidaCelsius la temperatura actual del sensor.
     * @return true si el sistema está en estado seguro, false si requiere parada de emergencia.
     */
    public boolean verificarEstadoSeguridad(double temperaturaActualCelsius) {
        // La lógica de seguridad debe ser simple, rápida y verificable.
        // Se utiliza un margen de seguridad para compensar la latencia de respuesta.
        if (temperaturaActualCelsius > (temperaturaLimiteCelsius - margenSeguridadCelsius)) {
            // Se registra la alerta de seguridad inmediata
            registrarAlertaSeguridad(temperaturaActualCelsius);
            return false; // Estado inseguro
        }
        return true; // Estado seguro
    }

    private void registrarAlertaSeguridad(double valor) {
        System.out.println("[ALERTA CRÍTICA] Límite de seguridad excedido. Valor: " 
            + valor + " C. Ejecutando parada de emergencia.");
    }
}
