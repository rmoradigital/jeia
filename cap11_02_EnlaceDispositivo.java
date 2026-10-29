/**
 * Interfaz que abstrae el medio físico de comunicación.
 */
interface EnlaceDispositivo {
    /**
     * Establece la conexión con el dispositivo.
     * @return true si la conexión fue exitosa, false de lo contrario.
     */
    boolean conectar();

    /**
     * Cierra la conexión activa.
     */
    void desconectar();

    /**
     * Envía una trama de bytes al dispositivo.
     * @param datos Array de bytes a transmitir.
     * @return true si el envío fue exitoso.
     */
    boolean enviar(byte[] datos);

    /**
     * Intenta leer datos disponibles del dispositivo.
     * @return Array de bytes recibidos o null si no hay datos.
     */
    byte[] leer();

    /**
     * Verifica si el dispositivo está conectado.
     */
    boolean estaConectado();
}
