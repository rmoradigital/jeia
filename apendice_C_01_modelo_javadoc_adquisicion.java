/**
 * Representa la jerarquía de componentes para un sistema de medición de tensión.
 * La estructura sigue un modelo de composición para asegurar que el canal
 * de adquisición sea gestionado por el instrumento.
 */

// Representación conceptual de la estructura de clases
// Sensor (Interfaz)
//  ^
//  |-- SensorTension (Clase concreta)
//       |-- tiene un -> CanalADC (Composición)
//
// Controlador (Clase)
//  |-- tiene un -> SensorTension (Asociación)
