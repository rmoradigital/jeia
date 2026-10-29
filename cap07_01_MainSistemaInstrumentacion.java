import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Representa una medición física capturada por un sensor.
 * Se utiliza el estándar de nombres con unidades explícitas para evitar ambigüedades.
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

    @Override
    public String toString() {
        return String.format(Locale.US, "%d;%.2f;%.3f", 
            timestampMilisegundos, tensionVoltios, corrienteAmperios);
    }
}

/**
 * Excepción de dominio para representar errores en la estructura de los datos recibidos.
 */
class TramaCorruptaException extends Exception {
    public TramaCorruptaException(String mensaje) {
        super(mensaje);
    }
}

/**
 * Gestor encargado de la persistencia de mediciones en archivos CSV.
 * Implementa un manejo de errores robusto para evitar la pérdida de datos.
 */
class CsvGestor {

    private static final String ENCABEZADO = "timestamp_ms;tension_v;corriente_a";
    private static final Locale CONFIGURACION_REGIONAL = Locale.US;

    /**
     * Escribe una lista de mediciones en un archivo CSV.
     * Utiliza try-with-resources para asegurar el cierre del BufferedWriter.
     * 
     * @param mediciones Lista de objetos Medicion a guardar.
     * @param rutaArchivo Ruta del archivo de destino.
     * @throws IOException Si ocurre un error de acceso al sistema de archivos.
     */
    public void exportarMediciones(List<Medicion> Mediciones, String rutaArchivo) throws IOException {
        Path path = Paths.get(rutaArchivo);
        
        // try-with-resources garantiza que el BufferedWriter se cierre automáticamente
        try (BufferedWriter writer = Files.newBufferedWriter(path, 
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            
            writer.write(ENCABEZADO);
            writer.newLine();

            for (Medicion m : Mediciones) {
                writer.write(m.toString());
                writer.newLine();
            }
            // El método flush() no es estrictamente necesario aquí por el cierre automático,
            // pero es útil en procesos de escritura continua.
            writer.flush();
        }
        System.out.println("Archivo exportado con éxito en: " + rutaArchivo);
    }

    /**
     * Lee mediciones desde un archivo CSV, validando la integridad de cada línea.
     * 
     * @param rutaArchivo Ruta del archivo a leer.
     * @return Una lista de objetos Medicion válidos.
     * @throws IOException Si el archivo no puede ser leído.
     */
    public List<Medicion> importarMediciones(String rutaArchivo) throws IOException {
        List<Medicion> listaValidada = new ArrayList<>();
        Path path = Paths.get(rutaArchivo);

        if (!Files.exists(path)) {
            throw new FileNotFoundException("El archivo de mediciones no existe: " + rutaArchivo);
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String linea;
            boolean esPrimeraLinea = true;

            while ((linea = reader.readLine()) != null) {
                // Omitir el encabezado
                if (esPrimeraLinea) {
                    esPrimeraLinea = false;
                    continue;
                }

                try {
                    listaValidada.add(parsearLinea(linea));
                } catch (TramaCorruptaException e) {
                    // En ingeniería de datos, es preferible registrar el error y continuar
                    // la lectura que abortar todo el proceso por una sola línea dañada.
                    System.err.println("Error de integridad detectado y omitido: " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.err.println("Error de formato numérico en línea: " + linea);
                }
            }
        }
        return listaValidada;
    }

    /**
     * Analiza una línea de texto y la convierte en un objeto Medicion.
     * 
     * @param linea La cadena de texto proveniente del archivo o puerto serie.
     * @return Un objeto Medicion validado.
     * @throws TramaCorruptaException Si la línea no cumple con el formato esperado.
     */
    private Medicion parsearLinea(String linea) throws TramaCorruptaException {
        String[] partes = linea.split(";");
        
        if (partes.length != 3) {
            throw new TramaCorruptaException("Número incorrecto de columnas en la trama: " + linea);
        }

        try {
            long tiempo = Long.parseLong(partes[0].trim());
            // Se usa Locale.US para asegurar que el punto decimal sea interpretado correctamente
            // independientemente de la configuración regional del sistema operativo.
            double tension = Double.parseDouble(partes[1].trim());
            double corriente = Double.parseDouble(partes[2].trim());

            return new Medicion(tiempo, tension, corriente);
        } catch (NumberFormatException e) {
            throw new TramaCorruptaExcepción("Fallo en la conversión de tipos en la trama: " + linea);
        }
    }
}

/**
 * Clase principal para probar la robustez del sistema de persistencia.
 */
public class MainSistemaInstrumentacion {
    public static void main(String[] args) {
        CsvGestor gestor = new CsvGestor();
        String archivoDatos = "datos_experimento.csv";

        // 1. SIMULACIÓN DE ADQUISICIÓN DE DATOS
        List<Medicion> datosSimulados = new ArrayList<>();
        datosSimulados.add(new Medicion(1000, 12.5, 2.1));
        datosSimulados.add(12.4, 2.2); // Este causará un error si no se maneja, pero lo creamos correctamente
        // Agregamos una medición manual para simular el error del cable (datos corruptos)
        // En un entorno real, esto vendría de un String mal formado.
        
        // Para fines de este ejemplo, llenamos con datos válidos
        for (int i = 2; i < 10; i++) {
            datosSimulados.add(newهاMedicion(i * 1000L, 12.0 + (Math.random() * 0.5), 2.0 + Math.random()));
        }

        // 2. PRUEBA DE EXPORTACIÓN
        try {
            gestor.exportarMediciones(datosSimulados, archivoDatos);
        } catch (IOException e) {
            System.err.println("Error crítico al intentar guardar los datos: " + e.getMessage());
        }

        // 3. INYECCIÓN DE ERROR: Modificamos el archivo manualmente para simular ruido
        try {
            Files.writeString(Paths.get(archivoDatos), "\n15000;12.4;ERROR_CORRUPTO", StandardOpenOption.APPEND);
            System.out.println("Se ha inyectado una trama corrupta en el archivo para la prueba.");
        } catch (IOException e) {
            System.err.println("Error al inyectar ruido: " + e.getMessage());
        }

        // 4. PRUEBA DE IMPORTACIÓN ROBUSTA
        System.out.println("\nIniciando proceso de recuperación de datos...");
        try {
            List<Medicion> medicionesRecuperadas = gestor.importarMediciones(archivoDatos);
            System.out.println("Recuperación completada.");
            System.out.println("Total de mediciones válidas recuperadas: " + medicionesRecuperadas.size());
            
            for (Medicion m : medicionesRecuperadas) {
                System.out.printf("Timestamp: %d ms | Tensión: %.2f V | Corriente: %.3f A%n", 
                    m.getTimestampMilisegundos(), m.getTensionVoltios(), m.getCorrienteAmperios());
            }
        } catch (IOException e) {
            System.err.println("Error crítico durante la lectura: " + e.getMessage());
        }
    }
}
