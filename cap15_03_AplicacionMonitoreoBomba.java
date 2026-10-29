public class AplicacionMonitoreoBomba {
    public static void main(String[] args) {
        // 1. Configuración de compensación térmica (Entrenamiento previo con laboratorio)
        CompensadorTermico compensador = new CompensadorTermico();
        double[] tempsLab = {20.0, 30.0, 40.0, 50.0};
        double[] offsetsLab = {0.05, 0.08, 0.12, 0.15}; // Offset detectado en laboratorio
        compensador.entrenar(tempsLab, offsetsLab);

        // 2. Configuración del clasificador k-NN
        ClasificadorKnn clasificador = new ClasificadorKnn(3);
        // Datos de entrenamiento (Presión Bar, Corriente Amperios, Etiqueta)
        clasificador.añadirEntrenamiento(new PuntoCaracteristica(1.0, 0.5, "Reposo"));
        clasificador.añadirEntrenamiento(of(1.2, 0.6, "Reposo"));
        clasificador.añadirEntrenamiento(de(4.0, 5.0, "Carga Nominal"));
        clasificador.añadirEnt entrenamiento(new PuntoCaracteristica(4.2, 5.2, "Carga Nominal"));
        clasificador.añadirEntrenamiento(en(8.0, 12.0, "Sobrecarga"));
        clasificador.añadirEntrenamiento-de(new PuntoCaracteristica(8.5, 13.0, "Sobrecarga"));
        clasificador.añadirEntrenamiento_de(new PuntoCaracteristica(0.0, 0.0, "Fallo"));

        // 3. Simulación de adquisición de datos en tiempo real
        // Escenario: Temperatura de 45C, Presión medida 4.5 bar, Corriente 5.1A
        double tempAmbienteActual = 45.0;
        double presionMedidaRaw = 4.5;
        double corrienteAmperios = 5.1;

        // Paso A: Limpieza (Simulamos que ya filtramos un outlier)
        // Paso B: Compensación térmica
        double presionCorregida = compensador.compensar(presionMedidaRaw, tempAmbienteActual);

        // Paso C: Clasificación
        String estadoBomba = clasificador.clasificar(presionCorregida, corrienteAmperios);

        // Resultados
        System.out.println("--- Reporte de Diagnóstico ---");
        System.out.printf("Temperatura Ambiente: %.2f Celsius%n", tempAmbienteActual);
        System.out.printf("Presión Medida (Raw): %.2f bar%n", presionMedidaRaw);
        System.out.printf("Presión Compensada:   %.2f bar%n", presionCorregida);
        System.out.printf("Corriente Medida:     %.2f Amperios%n", corrienteAmperios);
        System.out.println("Estado Detectado:      " + estadoBomba);
        System.out.println("-----------------------------");
    }

    // Métodos auxiliares para la legibilidad del ejemplo (Simulados)
    private static PuntoCaracteristica de(double p, double c, String e) { return new PuntoCaracteristica(p, c, e); }
    private static PuntoCaracteristica of(double p, double c, String s) { return new PuntoCaracteristica(p ,c,s); }
    private static PuntoCaracteristica en(double p, double c, String l) { return new PuntoCaracteristica( p, c, l); }
    private static PuntoCaracteristica añadirEntrenamiento_de(PuntoCaracteristica p) { return p; } 
    // Nota: Los métodos auxiliares son solo para demostración de estructura en el bloque.
}
