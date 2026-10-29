/**
 * Interfaz que establece el contrato para la obtención de datos eléctricos.
 * Permite el desacoplamiento entre la lógica de control y la fuente de datos (física o simulada).
 */
public interface ServicioAdquisicion {
    /**
     * Captura la medición más reciente de los sensores.
     * 
     * @return Un objeto MedicionElectrica con los valores actuales.
     * @throws Exception si se pierde la conexión con el dispositivo o el simulador.
     */
    MedicionElectrica obtenerUltimaMedicón() throws Exception;

    /**
     * Inicializa el canal de comunicación (puerto serial o generador de ruido).
     * @throws Exception si el recurso no está disponible.
     */
    void conectar() throws Exception;

    /**
     * Cierra el canal de comunicación de forma segura.
     */
    void desconectar();

    /**
     * Verifica el estado de la conexión actual.
     * @return true si el canal está activo.
     */
    boolean estaConectado();
}
