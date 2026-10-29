import java.time.Instant;

/**
 * Representación inmutable de una lectura de sensor.
 * Al ser un record, sus campos son finales y no pueden ser modificados tras la creación.
 * 
 * @param valor La magnitud física medida (ej. Voltios).
 * @param unidad La unidad de medida (ej. "V").
 * @param timestamp El momento exacto de la captura.
 */
public record MedicionInmutable(double valor, String unidad, Instant timestamp) {
    // Los records son ideales para flujos de datos (streams) de sensores.
}

class EjemploRegistro {
    public static void main(String args[]) {
        // Creación de una captura de datos
        MedicionInmutable lectura = new MedicionInmutable(12.05, "V", Instant.now());

        System.out.println("Captura de datos: " + lectura);
        // lectura.valor = 13.0; // Esto causaría un error de compilación, garantizando la integridad.
    }
}
