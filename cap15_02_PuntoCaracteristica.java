import java.util.*;

/**
 * Representa un punto de datos en el espacio de características.
 */
class PuntoCaracteristica {
    double presionBar;
    double corrienteAmperios;
    String etiqueta;

    public PuntoCaracteristica(double presionBar, double corrienteAmperios, String etiqueta) {
        this.presionBar = presionBar;
        this.corrienteAmperios = corrienteAmperios;
        this.etiqueta = etiqueta;
    }
}

/**
 * Clasificador basado en el algoritmo k-Nearest Neighbors.
 */
class ClasificadorKnn {
    private final List<PuntoCaracteristica> entrenamiento;
    private final int k;

    public ClasificadorKnn(int k) {
        this.entrenamiento = new ArrayList<>();
        this.k = k;
    }

    public void añadirEntrenamiento(PuntoCaracteristica punto) {
        this.entrenamiento.add(punto);
    }

    /**
     * Clasifica un nuevo punto de medición.
     * 
     * @param pPresion Presión en bares.
     * @param pCorriente Corriente en Amperios.
     * @return La etiqueta de la clase asignada.
     */
    public String clasificar(double pPresion, double pCorriente) {
        // Estructura para almacenar distancias y puntos
        class Distancia implements Comparable<Distancia> {
            double distancia;
            String etiqueta;

            Distancia(double d, String l) { this.distancia = d; this.etiqueta = l; }
            
            @Override
            public int compareTo(Distancia o) {
                return Double.compare(this.distancia, o.distancia);
            }
        }

        PriorityQueue<Distancia> pq = new PriorityQueue<>(Collections.reverseOrder());

        for (PuntoCaracteristica p : entrenamiento) {
            // Cálculo de distancia euclídea en espacio normalizado/escalado
            double d = Math.sqrt(Math.pow(p.presionBar - pPresion, 2) + 
                                 Math.pow(p.corrienteAmperios - pCorriente, 2));
            pq.add(new Distancia(d, p.etiqueta));
            if (pq.size() > k) {
                pq.poll();
            }
        }

        // Conteo de votos de las etiquetas de los k vecinos más cercanos
        Map<String, Integer> votos = new HashMap<>();
        while (!pq.isEmpty()) {
            String etiqueta = pq.poll().etiqueta;
            votos.put(etiqueta, votos.getOrDefault(etiqueta, 0) + 1);
        }

        return votos.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();
    }
}
