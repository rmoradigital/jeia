import java.util.Map;
import java.util.HashMap;

/**
 * Clase encargada de la transformacion de metricas y estados de operacion
 * en una estructura de texto optimizada para la interpretacion por un LLM.
 */
public class GeneradorReporteContextualIA {

    /**
     * Prepara un prompt estructurado para la IA.
     * 
     * @param estadoClasificado El estado detectado (ej. SOBRECARGA).
     * @param confianzaPorcentaje Nivel de confianza del modelo de IA (0.0 a 100.0).
     * @param caracteristicasMapa Mapa con los valores extraidos (ej. "corrienteAmperios" -> 15.5).
     * @return Una cadena de texto con el prompt listo para la inferencia.
     */
    public String prepararPromptParaReporte(
            String estadoClasificado, 
            double confianzaPorcentaje, 
            Map<String, Double> caracteristicasMapa) {

        StringBuilder prompt = new StringBuilder();
        
        // Definicion del rol del experto para condicionar la respuesta de la IA
        prompt.append("Actua como un ingeniero especialista en mantenimiento predictivo industrial. ");
        prompt.append("Analiza los siguientes datos de un sistema electromecanico y redacta un reporte tecnico breve. ");
        prompt.append("El reporte debe incluir: 1. Diagnostico principal, 2. Causa fisica probable, 3. Accion recomendada. ");
        prompt.append("\n\n");

        // Inclusion de metricas de operacion
        prompt.append("### Datos de Telemetria ###\n");
        prompt.append("Estado detectado por el sistema: ").append(estadoClasificado).append("\n");
        prompt.append("Nivel de confianza del modelo: ").append(String.format("%.2f", confianzaPorcentaje)).append("%\n");
        
        prompt.append("Caracteristicas del vector detectadas:\n");
        for (Map.Entry<String, Double> entrada : caracteristicasMapa.entrySet()) {
            // Se asume que las llaves ya contienen la unidad (ej. "temperaturaCelsius")
            prompt.append("- ").append(entrada.getKey()).append(": ")
                  .append(String.format("%.2f", entrada.getValue())).append("\n");
        }

        prompt.append("\n### Instruccion de formato ###\n");
        prompt.append("Redacta el reporte de forma profesional, evitando tecnicismos excesivos pero manteniendo el rigor. ");
        prompt.append("Si el nivel de confianza es inferior al 80%, añade una advertencia de 'Incertidumbre en la lectura'.\n");

        return prompt.toString();
    }

    /**
     * Metodo de prueba para verificar la estructura del prompt generado.
     */
    public static void main(String[] args) {
        GeneradorReporteContextualIA generador = new GeneradorReporteContextualIA();
        
        // Simulacion de datos obtenidos del pipeline de analisis
        Map<String, Double> datosSimulados = new HashMap<>();
        datosSimulados.put("corrienteAmperios", 22.45);
        datosSimulados.put("temperaturaCelsius", 85.3);
        datosSimulados.put("desviacionEstandarAmperios", 4.12);
        datosSimulados.put("pendienteTermicaCelsiusPorMin", 0.5);

        String resultado = generador.prepararPromptParaReporte("SOBRECARGA", 92.5, datosSimulados);
        
        System.out.println("--- PROMPT GENERADO PARA LA IA ---");
        System.out.println(resultado);
    }
}
