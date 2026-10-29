/**
 * Representa un buffer circular de tamaño fijo para el almacenamiento 
 * de muestras de una señal analógica.
 * Esta implementación utiliza tipos primitivos para evitar el overhead 
 * de objetos y la presión sobre el Garbage Collector.
 */
public class BufferCircularMediciones {
    private final double[] muestras;
    private final int capacidad;
    private int indiceEscritura;
    private int cantidadElementos;

    /**
     * Inicializa el buffer con una capacidad determinada.
     * @param capacidad Maximo de muestras que el buffer puede almacenar.
     */
    public BufferCircularMediciones(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        this.muestras = new double[capacidad];
        this.indiceEscritura = 0;
        this.cantidadElementos = 0;
    }

    /**
     * Inserta una nueva muestra en la posición más reciente.
     * La complejidad es O(1).
     * @param valor La magnitud medida (ej. Voltios).
     */
    public void añadirMuestra(double valor) {
        muestras[indiceEscritura] = valor;
        // Uso de aritmética modular para el retorno al inicio del arreglo
        indiceEscritura = (indiceEscritura + 1) % capacidad;
        
        if (cantidadElementos < capacidad) {
            cantidadElementos++;
        }
    }

    /**
     * Obtiene el valor en una posición relativa a la muestra más reciente.
     * @param posicion 0 para la muestra más reciente, 1 para la anterior, etc.
     * @return El valor de la muestra.
     * @throws IndexOutOfBoundsException si la posición excede los datos disponibles.
     */
    public double obtenerMuestraRelativa(int posicion) {
        if (posicion < 0 || posicion >= cantidadElementos) {
            throw new IndexOutOfBoundsException("Posicion de muestra no disponible.");
        }
        // Cálculo del índice real considerando la naturaleza circular
        int indiceReal = (indiceEscritura - 1 - posicion + capacidad) % capacidad;
        return muestras[indiceReal];
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getCantidadElementos() {
        return cantidadElementos;
    }

    /**
     * Devuelve una copia de las muestras actuales para procesamiento externo.
     * @return Arreglo con los datos ordenados cronológicamente.
     */
    public double[] obtenerVentanaActual() {
        double[] ventana = new double[cantidadElementos];
        for (int i = 0; i < cantidadElementos; i++) {
            // Se reconstruye la secuencia cronológica
            int indice = (indiceEscritura - cantidadElementos + i + capacidad) % capacidad;
            ventana[i] = muestras[indice];
        }
        return ventana;
    }
}
