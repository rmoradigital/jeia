package com.ingenieria.instrumentacion.simulacion;

import java.nio.ByteBuffer;

/**
 * Clase encargada de emular el comportamiento de un sensor industrial
 * que transmite datos mediante tramas estructuradas.
 * La trama consiste en: [HEADER (1 byte)] [DATA (1 byte)] [CHECKSUM (1 byte)]
 */
public class SimuladorTramaSensor {

    private static final byte HEADER = (byte) 0xAA; // Encabezado de inicio de trama

    /**
     * Genera una trama de datos para simular una lectura de sensor.
     * 
     * @param valorSensor El valor de la medición (0-255) en formato byte.
     * @return Un array de bytes que contiene la trama completa.
     */
    public byte[] generarTramaSimulada(byte valorSensor) {
        byte[] trama = new byte[3];
        trama[0] = HEADER;
        trama[1] = valorSensor;
        trama[2] = calcularChecksum(trama[0], trama[1]);
        return trama;
    }

    /**
     * Calcula un checksum simple mediante la suma de los bytes de la trama.
     * 
     * @param b1 Primer byte de la trama.
     * @param b2 Segundo byte de la trama.
     * @return El byte resultante de la suma (checksum).
     */
    private byte calcularChecksum(byte b1, byte b2) {
        return (byte) (b1 + b2);
    }

    public static void main(String[] args) {
        SimuladorTramaSensor simulador = new SimuladorTramaSensor();
        byte lecturaReal = 42; // Simulación de una lectura de temperatura o tensión
        
        byte[] tramaEnviada = simulador.generarTramaSimulada(lecturaReal);
        
        System.out.println("[SIMULACIÓN] Trama generada:");
        for (byte b : tramaEnviada) {
            System.out.printf("0x%02X ", b);
        }
        System.out.println();
    }
}
