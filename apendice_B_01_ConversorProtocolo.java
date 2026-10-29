public class ConversorProtocolo {

    /**
     * Convierte un byte firmado a su equivalente sin signo en un entero.
     * 
     * @param valorFirmado El byte recibido del puerto serie.
     * @return El valor entero correspondiente en el rango 0-255.
     */
    public static int convertirByteSinSigno(byte valorFirmado) {
        // Se aplica la máscara 0xFF (11111111 en binario) para asegurar que
        // solo se conserven los 8 bits originales y se evite la extensión de signo.
        return valorFirmado & 0xFF;
    }

    public static void main(String[] args) {
        byte datoRecibido = (byte) 0xFF; // Representa 255 en unsigned, pero -1 en signed
        
        int valorCorrecto = convertirByteSinSigno(datoRecibido);
        
        System.out.println("Valor original (signed): " + datoRecibido);
        System.out.println("Valor procesado (unsigned): " + valorCorrecto);
    }
}
