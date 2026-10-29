import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Clase que representa una medición física con sus unidades.
 */
class Medicion {
    private final long timestampMilisegundos;
    private final double tensionVoltios;
    private final double corrienteAmperios;

    public Medicion(long timestampMilisegundos, double tensionVoltios, double corrienteAmperios) {
        this.timestampMilisegundos = timestampMilisegundos;
        this.tensionVoltios = tensionVoltios;
        this.corrienteAmperios = corrienteAmperios;
    }

    public long getTimestampMilisegundos() { return timestampMilisegundos; }
    public double getTensionVoltios() { return tensionVoltios; }
    public double getCorrienteAmperios() { return corrienteAmperios; }
}

/**
 * Gestor de persistencia de datos y bitácora de errores.
 * Implementa AutoCloseable para asegurar el cierre de archivos mediante try-with-resources.
 */
class Datalogger implements AutoCloseable {
    private final String archivoDatosPath;
    private final String archivoLogPath;
    private BufferedWriter escritorDatos;
    private PrintWriter escritorLog;
    private static final DateTimeFormatter formateadorFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Datalogger(String archivoDatosPath, String archivoLogPath) throws IOException {
        this.archivoDatosPath = archivoDatosPath;
        this.archivoLogPath = archivoLogPath;
        inicializarArchivos();
    }

    private void inicializarArchivos() throws IOException {
        // Crear o sobreescribir el archivo de datos con encabezado
        escritorDatos = new BufferedWriter(new FileWriter(archivoDatosPath, false));
        escritorDatos.write("timestamp_ms,tension_V,corriente_A");
        escritorDatos.newLine();

        // Crear o anexar al archivo de log
        // Usamos FileWriter con true para mantener el historial de errores
        escritorLog = new PrintWriter(new FileWriter(archivoLogPath, true));
    }

    /**
     * Registra una medición en el archivo CSV.
     * @param m Objeto Medicion con los datos físicos.
     * @throws IOException Si ocurre un error de escritura en disco.
     */
    public void registrarMedicion(Medicion m) throws IOException {
        // Uso de Locale.US para asegurar el punto decimal (.) y compatibilidad internacional
        String linea = String.format(Locale.US, "%d,%.3f,%.4f", 
                m.getTimestampMilisegundos(), 
                m.getTensionVoltios(), 
                m.getCorrienteAmperios());
        
        escritorDatos.write(linea);
        escritorDatos.newLine();
        
        // El método flush() es crítico: empuja los datos del buffer de Java al buffer del Sistema Operativo.
        // Sin esto, si el programa se cierra abruptamente, los datos en el buffer se pierden.
        escritorDatos.flush();
    }

    /**
     * Registra un evento de error en la bitácora.
     * @param mensaje Descripción del fallo detectado.
     */
    public void registrarError(String mensaje) {
        String timestamp = LocalDateTime.now().format(formateadorFecha);
        escritorLog.printf("[%s] ERROR: %s%n", timestamp, mensaje);
        escritorLog.flush(); // Asegura que el error se escriba inmediatamente
    }

    @Override
    public void close() throws IOException {
        if (escritorDatos != null) {
            escritorDatos.close();
        }
        if (escritorLog != null) {
            escritorLog.close();
        }
        System.out.println("Recursos de persistencia liberados correctamente.");
    }
}

/**
 * Simula el comportamiento de un hardware que envía tramas de datos.
 */
class SimuladorHardware {
    private int contadorMuestras = 0;

    /**
     * Genera una trama de datos que puede ser válida o estar corrupta.
     * @param inyectarRuido Si es true, devuelve una trama malformada.
     * @return Una cadena de texto que simula el puerto serie.
     */
    public String obtenerTrama(boolean inyectarRuido) {
        if (inyectarRuido) {
            // Simula una trama con caracteres extraños debido a ruido electromagnético
            return "167890,2.34!@,0.56?#";
        }
        contadorMuestras++;
        // Simula datos de una batería descargándose
        double tension = 4.2 - (contadorMuestras * 0.01);
        double corriente = 1.5 + Math.random() * 0.1;
        return String.format(Locale.US, "%d,%.3f,%.3f", System.currentTimeMillis(), tension, corriente);
    }
}

public class DataloggerApp {
    public static void main(String[] args) {
        String archivoDatos = "mediciones_experimento.csv";
        String archivoLog = "sistema_errores.log";
        
        SimuladorHardware hardware = new SimuladorHardware();

        // Uso de try-with-resources para garantizar el cierre del datalogger
        try (Datalogger logger = new Datalogger(archivoDatos, archivoLog)) {
            System.out.println("Iniciando adquisición de datos...");
            
            for (int i = 0; i < 20; i++) {
                // Cada 5 ciclos, simulamos un error de comunicación por ruido
                boolean hayRuido = (i > 0 && i % 5 == 0);
                String tramaRecibida = hardware.obtenerTrama(hayRuido);

                try {
                    procesarTrama(tramaRecibida, logger);
                } catch (Exception e) {
                    // Capturamos excepciones de procesamiento para que el bucle principal no se detenga
                    logger.registrarError("Fallo en procesamiento de trama: " + e.getMessage() + " | Trama: " + tramaRecibida);
                }

                Thread.sleep(500); // Simula un intervalo de muestreo de 500 ms
            }
            System.out.println("Experimento finalizado con éxito.");

        } catch (IOException e) {
            System.err.println("Error crítico de sistema: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El proceso de adquisición fue interrumpido.");
        }
    }

    private static void procesarTrama(String trama, Datalogger logger) throws Exception {
        String[] partes = trama.split(",");
        if (partes.length != 3) {
            throw new Exception("Formato de trama inválido (columnas incorrectas)");
        }

        try {
            long timestamp = Long.parseLong(partes[0]);
            double tension = Double.parseDouble(partes[1]);
            double corriente = Double.parseDouble(partes[2]);

            logger.registrarMedicion(new Medicion(timestamp, tension, corriente));
        } catch (NumberFormatException e) {
            throw new Exception("Error de conversión numérica en datos de sensores");
        }
    }
}
