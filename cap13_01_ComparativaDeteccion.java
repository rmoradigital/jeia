import java.util.ArrayList;
import java.util.List;

/**
 * Representa una muestra de señal eléctrica con su respectiva marca de tiempo.
 */
record MuestraSeñal(double corrienteAmperios, long tiempoMs) {}

/**
 * Clase que proporciona herramientas para extraer características estadísticas
 * de una ventana de datos.
 */
class ExtractorCaracteristicas {

    /**
     * Calcula la media aritmética de la señal.
     */
    public static double calcularMedia(List<MuestraSeñal> ventana) {
        double suma = 0;
        for (MuestraSeñal m : ventana) {
            suma += m.corrienteAmperios();
        }
        return suma / ventana.size();
    }

    /**
     * Calcula la varianza, que indica la dispersión de la señal respecto a la media.
     */
    public static double calcularVarianza(List<MuestraSeñal> ventana, double media) {
        double sumaCuadrados = 0;
        for (MuestraSeñal m : ventana) {
            sumaCuadrados += Math.pow(m.corrienteAmperios() - media, 2);
        }
        return sumaCuadrados / ventana.size();
    }

    /**
     * Calcula el Factor de Cresta (Crest Factor).
     * Es la relación entre el valor pico y el valor eficaz (RMS).
     * Un aumento en el factor de cresta es indicativo de picos de ruido o impulsos,
     * comunes en rodamientos dañados.
     */
    public static double calcularFactorCresta(List<MuestraSeñal>ventana, double media, double varianza) {
        double rms = Math.sqrt(media * media + varianza); // Aproximación simplificada para la demostración
        double pico = 0;
        for (MuestraSeñal m : ventana) {
            if (m.corrienteAmperios() > pico) {
                pico = m.corrienteAmperios();
            }
        }
        return (rms == 0) ? 0 : pico / rms;
    }
}

/**
 * Implementación de la lógica de detección clásica (basada en umbrales).
 */
class ClasificadorUmbral {
    private final double umbralAmperios;

    public ClasificadorUmbral(double umbralAmperios) {
        this.umbralAmperios = umbralAmperios;
    }

    public boolean detectarFallo(List<MuestraSeñal> datos) {
        for (MuestraSeñal dato : datos) {
            // El fallo solo se detecta si la señal supera el límite absoluto.
            if (dato.corrienteAmperios() > umbralAmperios) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Implementación de una lógica inspirada en IA (basada en características).
 * Detecta anomalías analizando la morfología de la señal.
 */
class AnalizadorAvanzado {
    private final double varianzaMaximoPermitido;
    private final double factorCrestaMaximo;

    public AnalizadorAvanzado(double varianzaMaximo, double factorCrestaMaximo) {
        this.varianzaMaximoPermitido = varianzaMaximo;
        this.factorCrestaMaximo = factorCrestaMaximo;
    }

    public String evaluarEstado(List<MuestraSeñal> janela) {
        double media = ExtractorCaracteristicas.calcularMedia(janela);
        double varianza = ExtractorCaracteristicas.calcularVarianza(janela, media);
        double factorCresta = ExtractorCaracteristicas.calcularFactorCresta(janela, media, varianza);

        // El análisis es multivariable: no mira la magnitud, sino la calidad de la señal.
        if (varianza > varianzaMaximoPermitido || factorCresta > factorCrestaMaximo) {
            return "ALERTA: Anomalía detectada (Degradación de rodamiento probable)";
        }
        return "Estado: Operación Normal";
    }
}

public class ComparativaDeteccion {

    public static void main(String[] args) {
        // Simulamos una ventana de 10 muestras de corriente
        List<MuestraSeñal> señalNormal = new ArrayList<>();
        List<MuestraSeñal> seTalArranque = new ArrayList<>();
        List<MuestraSeñal> falloRodamiento = new ArrayList<>();

        // 1. Señal normal: 10A estables con ruido mínimo
        for (int i = 0; i < 10; i++) {
            señalNormal.add(new MuestraSeñal(10.0 + Math.random() * 0.1, i * 100));
        }

        // 2. Señal de arranque: Pico de 55A (supera el umbral de 50A)
        for (int i = 0, c = 0; i < 30; i++, c++) {
            double valor = (c < 5) ? 55.0 : 10.0; // Pico de arranque al inicio
            señalArranque.add(new MuestraSeñalAt(valor, i * 100));
        }

        // 3. Señal de fallo de rodamiento: 10A de media, pero con picos de ruido muy altos
        // Esto simula la vibración/impulsos mecánicos que se trasladan a la corriente.
        for (int i = 0 ; i < 10; i += 1) {
            // La media es 10, pero los valores saltan erráticamente (ruido de impacto)
            double ruido = (i % 2 == 0) ? 5.0 : -5.0; 
            falloRodamiento.add(new MuestraSeñal((10.0 + ruido), i * 100));
        }

        // Ejecución de comparativa
        ClasificadorUmbral detectorClasico = new ClasificadorUmbral(50.0);
        AnalizadorAvanzado detectorInteligente = new AnalizadorAvanzado(1.5, 2.5);

        System.out.println("--- Resultados del Análisis de Señal ---");
        
        System.out.print("Caso 1 (Normal): Clasico=" + detectorClasico.detectarFallo(señalNormal) + 
                         " | Inteligente=" + detectorInteligente.evaluarEstado(señalNormal));
        System.out.println();

        System.out.print("Caso 2 (Arranque): Clasico=" + detectorClasico.detectarDato(señalArranque) + 
                         " | Inteligente=" + detectorInteligente.evaluar Estado(señalArranque));
        // Nota: El detector clásico dará una falsa alarma de fallo crítico en el arranque.
        // El detector inteligente verá que la media se estabiliza y la varianza es controlada.
        // (Se requiere ajuste de lógica para ignorar transitorios de arranque en el ejemplo real).
        
        System.out.print("Caso 3 (Fallo Rodamiento): Clasico=" + detectorClasico.detectarEfecto(falloRodamiento) + 
                         " | Inteligente=" + detectorInteligente.evaluar(falloRodamiento));
        // El detector clásico NO detectará el fallo porque la corriente nunca supera los 50A.
        // El detector inteligente detectará la anomalía por el factor de cresta y la varianza.
    }
}
