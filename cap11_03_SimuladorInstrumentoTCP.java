import java.io.*;
import java.net.*;

/**
 * Simulador de un instrumento electrónico que responde vía TCP.
 * Simula un sensor que devuelve una temperatura bajo demanda.
 */
class SimuladorInstrumentoTCP implements Runnable {
    private final int puerto;
    private volatile boolean ejecutando = true;

    public SimuladorInstrumentoTCP(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Simulador de instrumento iniciado en puerto: " + puerto);
            
            while (ejecutando) {
                try (Socket cliente = serverSocket.accept();
                     InputStream in = cliente.getInputStream();
                     OutputStream out = cliente.getOutputStream()) {
                    
                    System.out.println("Cliente conectado para solicitud.");
                    
                    // El simulador lee la solicitud (en este caso, un comando simple)
                    byte[] buffer = new byte[16];
                    int bytesLeidos = in.read(buffer);
                    
                    if (bytesLeidos > 0) {
                        // Procesar comando y responder con una trama válida
                        byte[] respuesta = generarRespuestaSimulada();
                        out.write(respuesta);
                        out.flush();
                        System.out.println("Respuesta enviada: " + bytesLeidos + " bytes leídos del cliente.");
                    }
                } catch (IOException e) {
                    System.err.println("Error en la comunicación con el cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor simulador: " + e.getMessage());
        }
    }

    /**
     * Genera una trama: [SOF 0xAA] [Longitud 2] [Data 0x25 0x00 (37.0 C)] [Checksum]
     */
    private byte[] generarRespuestaSimulada() {
        byte sof = (byte) 0xAA;
        byte longitud = 2;
        byte data1 = 0x25; // Representación simplificada de temperatura
        byte data2 = 0x00;
        
        // Cálculo de checksum (SOF + Longitud + Data1 + Data2)
        byte checksum = (byte) (sof + longitud + data1 + data2);
        
        return new byte[] { sof, longitud, data1, data2, checksum };
    }

    public void detener() {
        this.ejecutando = false;
    }
}
