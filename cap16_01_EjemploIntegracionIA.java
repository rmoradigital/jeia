import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Representa el resumen estadístico de una ventana de tiempo de mediciones.
 * Se utilizan unidades explícitas para asegurar la integridad de los datos.
 */
record ResumenEstadistico(
    double mediaCelsius,
    double desviacionEstandar,
    double maximoCelsius,
    double minimoCelsius,
    double pendienteCambioPorSegundo
) {}

/**
 * Representa la respuesta interpretativa del modelo de IA.
 */
record DiagnosticoIA(String etiqueta, double confianza, boolean esValido) {
    // Método de respaldo: devuelve un diagnóstico seguro y predefinido
    public static DiagnosticoIA fallback() {
        return new DiagnosticoIA("MODO_RESPALDO_LOCAL", 0.0, false);
    }
}

/**
 * Cliente robusto para la comunicación con servicios de IA.
 */
class ClienteInferenciaIA {

    private final HttpClient httpClient;
    private final String urlServicioIA;
    private final Duration timeoutConfigurado;

    public ClienteInferenciaIA(String urlServicioIA, long timeoutMs) {
        this.urlServicioIA = urlServicioIA;
        this.timeoutConfigurado = Duration.ofMillis(timeoutMs);
        // Se configura un cliente con soporte para HTTP/2 y gestión de conexiones
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))
                .build();
    }

    /**
     * Envía los datos de forma asíncrona y gestiona la respuesta o el error.
     * @param resumen Datos estadísticos de la ventana de medición.
     * @return Un CompletableFuture que contiene el diagnóstico resultante.
     */
    public CompletableFuture<DiagnosticoIA> solicitarDiagnosticoAsync(ResumenEstatistico resumen) {
        // Construcción manual del payload JSON para evitar dependencias externas en este ejemplo
        String jsonPayload = String.format(
            "{\"media\": %.2f, \"desv\": %.2f, \"max\": %.2f, \"min\": %.2f, \"pendiente\": %.2f}",
            resumen.mediaCelsius(), resumen.desviacionEstandar(), resumen.maximoCelsius(), 
            resumen.minimoCelsius(), resumen.pendienteCambioPorSegundo()
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlServitorioIA))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .timeout(timeoutConfigurado) // Aplicación del timeout de respuesta
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(this::parsearRespuestaJSON)
                .orTimeout(timeoutConfigurado.toMillis(), TimeUnit.MILLISECONDS)
                .exceptionally(ex -> {
                    // El bloque exceptionally captura timeouts, fallos de red o errores de servidor
                    System.err.println("[LOG ERROR] Error en servicio de IA: " + ex.getMessage());
                    return DiagnosticoIA.fallback(); // Activación del mecanismo de respaldo
                });
    }

    /**
     * Valida la estructura del JSON y extrae la etiqueta de diagnóstico.
     * En un entorno de producción, se recomienda el uso de Jackson o Gson.
     */
    private DiagnosticoIA parsearRespuestaJSON(HttpResponse<String> response) {
        if (response.statusCode() != 200) {
            return DiagnosticoIA.fallback();
        }

        String cuerpo = response.body();
        // Validación simplificada de la estructura para fines demostrativos
        if (cuerpo.contains("etiqueta") && cuerpo.contains("confianza")) {
            // Extracción rudimentaria de valores
            String etiqueta = cuerpo.split("\"etiqueta\":\"")[1].split("\"")[0];
            double confianza = Double.parseDouble(cuerpo.split("\"confianza\":")[1].split("}")[0].trim());
            return new DiagnosticoIA(etiqueta, confianza, true);
        }
        
        return DiagnosticoIA.fallback();
    }
}

public class EjemploIntegracionIA {
    public static void main(String[] args) throws InterruptedException {
        // Configuración de la simulación
        String urlSimulada = "http://localhost:8080/inferencia";
        ClienteInferenciaIA cliente = new ClienteInferenciaIA(urlSimulada, 500);

        // Datos simulados de una ventana de temperatura
        ResumenEstatistico ventanaActual = new ResumenEstatistico(75.5, 1.2, 78.0, 73.0, 0.5);

        System.out.println("[SISTEMA] Iniciando monitoreo...");

        // Ejecución de la llamada asíncrona
        cliente.solicitarDiagnosticoAsync(ventanaActual).thenAccept(diagnostico -> {
            System.out.println("[RESULTADO] Diagnóstico recibido: " + diagnostico.etiqueta() + 
                               " (Confianza: " + (diagnostico.confianza() * 100) + "%)" +
                               " [Valido: " + diagnostico.esValido() + "]");
        });

        // El hilo principal continúa sin esperar a la IA, permitiendo otras tareas
        System.out.println("[SISTEMA] El hilo de control sigue operando (monitoreo de umbrales...)");
        
        // Simulación de espera para que la llamada asíncrona termine en el ejemplo
        Thread.sleep(2000);
    }
}
