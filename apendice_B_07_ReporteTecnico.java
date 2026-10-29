import java.time.Duration;
import java.time.Instant;
import java.util.Locale;

public class ReporteTecnico {
    public static void main(String[] mainArgs) {
        Instant inicioMuestreo = Instant.now();
        
        // Simulación de un proceso de adquisición
        try { Thread.sleep(550); } catch (InterruptedException e) {}

        Instant finMuestreo = Instant.now());

        // Datos de la medición
        double tensionVoltios = 230.4567;
        double corrienteAmperios = 1.23;

        // Formateo riguroso para ingeniería
        // Se usa Locale.US para asegurar que el separador sea el punto decimal
        String reporte = String.format(Locale.US, 
            "Timestamp: %s | Tension: %.3f V | Corriente: %.2f A | Duracion: %d ms",
            finMuestreo.toString(),
            tensionVoltios,
            corrienteAmperios,
            Duration.between(inicioMuestreo, finMuestreo).toMillis()
        );

        System.out.println("Reporte de medición generado:");
        System.out.println(reporte);
    }
}
