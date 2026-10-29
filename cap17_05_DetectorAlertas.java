package procesamiento;

import modelo.MedicionElectrica.
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Gestiona la detección de condiciones fuera de rango.
 */
public class DetectorAlertas {
    private final double limiteTensionMax = 30.5;
    private final double limiteCorrienteMax = 5.2;
    private final double limiteTempMax = 85.0;

    public String verificar(MedicionElectrica m) {
        if (m.getTensionVoltios() > limiteTensionMax) return "ALERTA: SOBRETENSIÓN";
        if (m.getCorrienteAmperios() > limiteCorrienteMax) return "ALERTA: SOBRECORRIENTE";
        if (m.getTemperaturaCelsius() > limiteTempMax) return "ALERTA: SOBRETEMPERATURA";
        return null; // Sin alertas
    }
}

/**
 * Encargado de la escritura persistente de datos en formato CSV.
 */
public class RegistradorCSV {
    private BufferedWriter escritor;
    private final String nombreArchivo;

    public RegistradorCSV(String nombreArchivo) throws IOException {
        this.nombreArchivo = nombreArchivo;
        this.escritor = new BufferedWriter(new FileWriter(nombreArchivo));
        // Escribir cabecera
        escritor.write("Timestamp,Tension_V,Corriente_A,Temp_C");
        escritor.newLine();
    }

    public void registrar(MedicionElectrica m) throws IOException {
        String linea = String.format("%d,%.2f,%.3f,%.1f", 
            m.getTimestampMillis(), m.getTensionVoltios(), 
            m.getCorrienteAmperios(), m.getTemperaturaCelsius());
        escritor.write(linea);
        escritor.newLine();
    }

    public void cerrar() throws IOException {
        if (escritor != null) escritor.close();
    }
}
