// ERROR: Implementación insegura y poco robusta generada por IA
public class TelemetryParser {
    public double parseTemperature(byte[] data) {
        double temp = 0;
        for (int i = 1; i < data.length - 2; i++) {
            // Error: Conversión de bytes a double sin considerar el signo o el formato
            temp += data[i]; 
        }
        return temp;
    }
}
