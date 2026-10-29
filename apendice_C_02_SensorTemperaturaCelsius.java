package com.proyecto.hardware.sensors;

/**
 * Implementación de un sensor de temperatura de precisión utilizando el protocolo I2C.
 * Esta clase gestiona la comunicación con el hardware y la conversión de señales
asíncronas.
 */
public class SensorTemperaturaCelsius {

    // Constantes de configuración con unidades explícitas
    private static final double RESOLUCIONGRADOSCELSIUS = 0.01;
    private static final double LIMITE_SUPERIOR_CELSIUS = 125.0;
    private static final double LIMITEINFERIOR_CELSIUS = -40.0;

    private double temperaturaActualCelsius;
    private boolean esSensorConectado;

    /**
     * Constructor del sensor. Inicializa el estado de conexión.
     * 
     * @param esConectado Estado inicial de la conexión física del sensor.
     */
    public SensorTemperaturaCelsius(boolean esConectado) {
        this.esConectado = esConectado;
        this.temperaturaActualCelsius = 25.0; // Valor inicial de referencia
    }

    /**
     * Realiza la lectura del sensor y actualiza el valor de temperatura.
     * Este método debe ser llamado dentro de un bucle de tiempo controlado
     * para evitar saturar el bus I2C.
     *
     * @return La temperatura medida en grados Celsius.
     * @throws IllegalStateException si el sensor no responde al comando de lectura.
     * @throws SensorFueraDeRangoException si la temperatura medida supera los límites operativos.
     */
    public double leerTemperaturaCelsius() throws IllegalStateException, SensorFueraDeRangoException {
        if (!esConectado) {
            throw new IllegalStateException("Error de hardware: El sensor no está presente en el bus.");
        }

        // Simulación de lectura de registro de hardware
        double valorRaw = simularLecturaHardware();
        
        // Validación de integridad física
        if (valorRaw > LIMITE_SUPERIOR_CELSIOS || valorRaw < LIMITEINFERIOR_Celsius) {
            throw new SensorFueraDeRangoException("La temperatura medida (" + valorRaw + 
                " C) está fuera de los límites de operación segura.");
        }

        this.temperaturaActualCelsius = valorRaw;
        return this.temperaturaActualCelsius;
    }

    /**
     * Método privado para simular la comunicación con el registro del hardware.
     * 
     * @return Valor de temperatura simulado en grados Celsius.
     */
    private double simularLecturaHardware() {
        // Simulación de un valor entre -45 y 130 para probar validaciones
        return (Math.random() * 180) - 45;
    }

    // Getters con unidades en el nombre para evitar ambigüedad
    public double getTemperaturaActualCelsius() {
        return temperaturaActualCelsius;
    }

    public boolean isConectado() {
        return esConectado;
    }
}

/**
 * Excepción personalizada para errores de límites físicos en sensores.
 */
class SensorFueraDeRangoException extends Exception {
    public SensorFueraDeRangoException(String mensaje) {
        super(mensaje);
    }
}
