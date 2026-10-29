import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

/**
 * Emulador de hardware para simulación de sensores industriales.
 * Genera señales físicas y las transmite mediante protocolos ASCII o Binarios.
 */
public class HardwareEmulator {

    // Constantes de configuración del protocolo
    private static final byte STX = 0x02;
    private static final byte ETX = 0x03;
    private static final byte HEADER_BINARIO = (byte) 0xAA;

    private final Random generadorAleatorio = new Random();
    private long tiempoTranscurridoMs = 0;

    /**
     * Interfaz para definir el comportamiento de la señal física.
     */
    interface SignalModel {
        double obtenerValor(long tiempoMs);
        int getEstadoSalud();
    }

    /**
     * Implementación de una señal senoidal con ruido de fondo.
     */
    static class SenoidalConRuido implements SignalModel {
        private final double amplitudVoltios;
        private final double frecuenciaHz;
        private final double ruidoDesviacion;
        private final Random rnd = new Random();

        public SenoidalConRuido(double amplitud, double frecuencia, double ruido) {
            this.amplitudVoltios = amplitud;
            this.frecuenciaHz = frecuencia;
            this.ruidoDesviacion = ruido;
        }

        @Override
        public double obtenerValor(long tiempoMs) {
            // Generación de senoidal: A * sin(2 * PI * f * t)
            double seno = amplitudVoltios * Math.sin(2.0 * Math.PI * frecuenciaHz * (tiempoMs / 1000.0));
            // Adición de ruido gaussiano
            double ruido = rnd.nextGaussian() * ruidoDesviacion;
            return seno + ruido;
        }

        @Override
        public int getEstadoSalud() {
            return 1; // Señal en estado normal
        }
    }

    /**
     * Implementación de un fallo por deriva térmica (Drift).
     */
    static class FalloPorDeriva implements SignalModel {
        private final double driftPorMs;

        public FalloPorDeriva(double driftPorMs) {
            this.driftPorMs = driftPorMs;
        }

        @Override
        public double obtenerValor(long tiempoms) {
            // La señal aumenta linealmente con el tiempo, simulando calor
            return 2.5 + (driftPorMs * tiempoms);
        }

        @Override
        public int getEstadoSalud() lafo() {
            return 0; // El sensor está reportando un fallo de deriva
        }
    }

    /**
     * Genera una trama binaria de 16Bytes (Little Endian).
     */
    private byte[] generarTramaBinaria(byte canalId, double valorVoltios) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.put(HEADER_BINARIO);          // 0: Header (1 byte)
        buffer.put(canalId);                 // 1: Canal ID (1 byte)
        buffer.putInt((int) tiempoTranscurridoMs); // 2-5: Timestamp (4 bytes)
        buffer.putShort((short) (valorVoltios * 100)); // 6-7: Muestra 1 (2 bytes, escala x100)
        buffer.putShort((short) 0);          // 8-9: Muestra 2 (2 bytes)
        buffer.putShort((short) -10);         // 10-11: Muestra 3 (2 bytes)
        buffer.putShort((short) (-valorVoltios * 100)); // 12-13: Muestra 4 (2 bytes)

        // Calcular checksum simple sobre los primeros 14 bytes
        short checksum = 0;
        byte[] array = buffer.array();
        for (int i = 0; i < 14; i++) {
            checksum += (short) (array[i] & 0xFF);
        }
        buffer.putShort(14, checksum); // 14-15: Checksum (2 bytes)

        return buffer.array();
    }

    public void ejecutarSimulacion(SignalModel modelo, boolean modoSocket, int puerto) {
        System.out.println("Iniciando emulación con modelo: " + modelo.getClass().getSimpleName());
        
        if (modoSocket) {
            try (ServerSocket server = new ServerSocket(puerto)) {
                System.out.println("Servidor de datos escuchando en puerto " + puerto + "...");
                try (Socket client = server.accept();
                     PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                    
                    while (true) {
                        enviarDatos(modelo, out, true);
                        Thread.sleep(100); // Frecuencia de muestreo de 10Hz
                    }
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("Error en el servidor de simulación: " + e.getMessage());
            }
        } else {
            // Modo Consola
            try {
                while (true) {
                    enviarDatos(modelo, null, false);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                System.err.println("Simulación interrumpida.");
            }
        }
    }

    private void enviarDatos(SignalModel modelo, PrintWriter socketOut, boolean esSocket) {
        double valor = modelo.obtenerValor(tiempoTranscurridoMs);
        int salud = modelo.getEstadoSalud();
        tiempoTranscurridoMs += 100;

        if (esSocket) {
            // Enviar por socket como trama binaria (simulación simplificada)
            byte[] trama = generarTramaBinaria((byte) 1, valor);
            try {
                socketOut.write(trama);
                socketOut.flush();
            } catch (IOException e) {
                System.err.println("Error de transmisión en socket.");
            }
        } else {
            // Enviar por consola como mensaje ASCII
            String mensaje = String.format("%cS1|DAT|%.2f|%s%c", 
                (char) STX, valor, (salud == 1 ? "OK" : "ERR"), (char) ETX);
            System.out.println("Consola [ASCII]: " + mensaje);
        }
    }

    public static void main(String[] args) {
        HardwareEmulator emulador = new HardwareEmulator();
        
        // Escenario 1: Señal senoidal normal con ruido
        SignalModel senoidal = new SenoidalConRuido(2.5, 1.0, 0.05);
        
        // Escenario 2: Fallo por deriva térmica
        SignalModel fallo = new FalloPorDeriva(0.0001);

        // Ejecutar modo consola para el escenario normal
        // En un entorno real, se llamaría a emulador.ejecutarSimulacion(senoidal, true, 5000);
        emulador.ejecutarSimulacion(sonoidal, false, 0);
    }
}
