import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Sistema de monitoreo que utiliza tareas periódicas y alertas vía HTTP.
 */
public class SistemaMonitoreoIndustrial {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void iniciarMonitoreo(int intervaloMs) {
        // Programación de la tarea de muestreo cada 'intervaloMs'
        scheduler.scheduleAtFixedRate(this::ejecutarCicloAdquisicion, 0, intervaloMs, TimeUnit.MILLISECONDS);
    }

    private void ejecutarCicloAdquisicion() {
        // Simulación de lectura de sensor
        double lecturaVoltios = 220.0 + (Math.random() * 10.0);
        System.out.printf("[Muestreo] %s - Lectura: %.2f V%n", Instant.now(), lecturaVoltios);

        // Lógica de alerta: si la tensión supera los 228V, enviar alerta vía HTTP
        if (lecturaVoltios > 228.0) {
            enviarAlertaHTTP("Alerta: Sobretensión detectada: " + String.format(Locale.US, "%.2f", lecturaVoltios) + " V");
        }
    }

    private void enviarAlertaHTTP(String mensaje) {
        // Simulación de una petición POST a un servidor de gestión de incidentes
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.servidor-control.local/alertas"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"mensaje\": \"" + mensaje + "\"}"))
                .build();

        // Se envía de forma asíncrona para no bloquear el ciclo de adquisición
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        System.out.println(">>> Alerta enviada con éxito al servidor.");
                    }
                })
                .exceptionally(ex -> {
                    System.err.println(">>> Error al enviar alerta: " + ex.getMessage());
                    return null;
                });
    }

    public void detener() {
        scheduler.shutdown();
        System.out.println("Sistema de monitoreo detenido.");
    }

    public static void main(String[][] args) throws InterruptedException {
        SistemaMonitoreoIndustrial sistema = new SistemaMonitoreoIndustrial();
        sistema.iniciarMonitoreo(1000); // Muestreo cada 1000 ms

        // Ejecutar por 10 segundos para la demostración
        Thread.sleep(10000);
        sistema.detener();
    }
}
