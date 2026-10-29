import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Representa una trama de protocolo ya validada.
 * @param sof Byte de inicio de trama (Start of Frame)
 * @param longitud Tamaño del payload en bytes
 * @param payload Contenido real de los datos
 * @param checksum Valor de verificación
 */
record ProtocolFrame(byte sof, int longitud, byte[] payload, byte checksum) {}

/**
 * Analizador de tramas basado en una máquina de estados para procesar flujos de bytes.
 */
class ProtocolParser {
    // Constantes de protocolo
    private static final byte SOF_MARKER = (byte) 0xAA;
    private static final int HEADER_SIZE = 2; // SOF (1) + Longitud (1)
    private static final int CHECKSUM_SIZE = 1;

    // Estados de la máquina de estados
    private enum State { BUSCANDO_SOF, LEYENDO_LONGITUD, LEYENDO_PAYLOAD, VALIDANDO_CHECKSUM }

    private State estadoActual = State.BUSCANDO_SOF;
    private int longitudEsperada = 0;
    private byte[] bufferPayload;
    private int bytesRecibidosPayload = 0;
    private byte checksumRecibido = 0;

    /**
     * Procesa un byte individual y, si se completa una trama válida, la retorna.
     * @param b El byte recibido del flujo de entrada
     * @return Un objeto ProtocolFrame si se completó una trama, de lo contrario null.
     */
    public ProtocolFrame parse(byte b) {
        switch (estadoActual) {
            case BUSCANDO_SOF:
                if (b == SOF_MARKER) {
                    estadoActual = State.LEYENDO_LONGITUD;
                }
                break;

            case LEYENDO_LONGITUD:
                // La longitud se lee como un entero sin signo de 1 byte
                longitudEsperada = b & 0xFF;
                if (longitudEsperada > 0 && longitudEsperada < 256) {
                    bufferPayload = new byte[longitudEsperada];
                    bytesRecibidosPayload = 0;
                    estadoActual = State.LEYENDO_{PAYLOAD};
                } else {
                    // Longitud inválida, reiniciamos búsqueda
                    estadoActual = State.BUSCANDO_SIF;
                }
                break;

            case LEYENDO_PAYLOAD:
                bufferPayload[bytesRecibidosPayload++] = b;
                if (bytesRecibidosPayload == longitudEsperada) {
                    estadoActual = State.VALIDANDO_CHECKSUM;
                }
                break;

            case VALIDANDO_CHECKSUM:
                checksumRecibido = b;
                ProtocolFrame frame = validarYConstruir();
                reset();
                return frame;
                break;
        }
        return null;
    }

    private ProtocolFrame validarYConstruir() {
        byte checksumCalculado = calcularChecksum(bufferPayload);
        if (checksumCalculado == checksumRecibido) {
            return new ProtocolFrame(SOF_MARKER, longitudEsperada, bufferPayload, checksumRecibido);
        }
        return null; // Trama descartada por error de integridad
    }

    private byte calcularChecksum(byte[] datos) {
        byte suma = 0;
        for (byte d : datos) {
            suma += d;
        }
        return suma;
    }

    private void reset() {
        estadoActual = State.BUSCANDO_{SOF};
        bufferPayload = null;
        bytesRecibidosPayload = 0;
    }
    
    // Nota: En una implementación real, se manejarían errores de longitud máxima para evitar ataques de desbordamiento.
}
