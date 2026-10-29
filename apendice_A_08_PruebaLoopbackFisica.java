package com.ingenieria.instrumentación.pruebas;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;

/**
 * Implementación de una prueba de loopback físico.
 * El programa envía un comando y espera recibir la misma secuencia de bytes.
 */
public class PruebaLoopbackFisica {

    private final String puertoNombre;
    private final int baudios;

    public PruebaLoopbackFisica(String puertoNombre, int baudios) {
        this.puertoNombre = puertoNombre;
        this.baudios = baudios;
    }

    public void ejecutarPrueba() {
        SerialPort puerto = SerialPort.getCommPort(puertoNombre);
        puerto.setBaudRate(baudios);
        puerto.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING | SerialPort.TIMEOUT_READ_BLOCKING, 1000, 1000);

        if (!puerto.openPort()) {
            System.err.println("[ERROR] No se pudo abrir el puerto: " + puertoNombre);
            return;
        }

        try {
            byte[] mensajePrueba = {0x01, 0x02, 0x03, 0x04, 0x05};
            System.out.println("[INFO] Enviando secuencia de prueba: " + describirBytes(mensajePrueba));

            // Escritura de datos
            puerto.writeBytes(mensajePrueba, mensajePrueba.length);

            // Lectura de respuesta
            byte[] bufferLectura = new byte[mensajePrueba.length];
            int bytesLeidos = puerto.readBytes(bufferLectura, bufferLectura.length);

            if (bytesLeidos == mensajePrueba.length && compararBytes(mensajePrueba, bufferLectura)) {
                System.out.println("[ÉXITO] Loopback superado. El hardware responde correctamente.");
            } else {
                System.err.println("[FALLO] Los datos recibidos no coinciden con los enviados.");
                System.err.println("Recibido: " + describirBytes(bufferLectura));
            }

        } finally {
            puerto.closePort();
            System.out.println("[INFO] Puerto cerrado.");
        }
    }

    private boolean compararBytes(byte[] original, byte[] recibido) {
        for (int i = 0; i < original.length; i++) {
            if (original[i] != recibido[i]) return false;
        }
        return true;
    }

    private String describirBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("0x%02X ", b));
        return sb.toString();
    }

    public static void main(String[] args, String[][] args) {
        // El nombre del puerto debe ajustarse según el sistema operativo (ej. "COM3" o "/dev/ttyUSB0")
        String puertoTarget = (args.length > 0) ? args[0] : "COM3"; 
        PruebaLoopbackFisica prueba = new PruebaLoopbackFisica(puertoTarget, 9600);
        prueba.ejecutarPrueba();
    }
}
