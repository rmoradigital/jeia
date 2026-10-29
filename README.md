# Java e Inteligencia Artificial para Aplicaciones Electrónicas
## Fundamentos, desarrollo de software e integración con dispositivos

**Autor:** Ramiro Mora  
**Versión de referencia:** Java LTS moderno (Java 17 / 21)  
**Catálogo detallado de archivos:** Consulte [`INDICE.md`](./INDICE.md) para el listado exhaustivo archivo por archivo.

---

## 1. Descripción de la Obra

Este repositorio reúne el código fuente, las clases de ingeniería, los scripts de configuración y las prácticas resueltas del libro **«Java e inteligencia artificial para aplicaciones electrónicas: Fundamentos, desarrollo de software e integración con dispositivos»**.

La obra ofrece un puente riguroso y práctico entre la **electrónica** (adquisición de señales, acondicionamiento, conversores analógico-digitales, transductores, actuadores, ruido e instrumentación) y la **ingeniería de software moderna en Java**, integrando herramientas y flujos de **inteligencia artificial** bajo un doble propósito:
1. **IA como asistente de ingeniería:** Acelerar y auditar de forma crítica el diseño algorítmico, la depuración de errores de hardware/software, la generación de casos de prueba y la refactorización de código.
2. **IA como componente de aplicación:** Implementar modelos de preprocesamiento, extracción de características, detección de fallos y reportes contextuales que analizan series temporales y mediciones físicas en tiempo real.

---

## 2. Pila Tecnológica y Referencias de Software

Todos los programas han sido diseñados bajo principios de estabilidad, tipado estricto, independencia de plataforma y mínimo acoplamiento a librerías externas superfluas.

| Componente | Versión / Referencia | Propósito en el Libro |
| :--- | :--- | :--- |
| **Java Development Kit (JDK)** | **Java 17 / 21 LTS** | Plataforma base. Empleo de características modernas: registros inmutables (`record`), coincidencia de patrones (`pattern matching`), bloques de texto, interfaces selladas y gestión de recursos (`try-with-resources`). |
| **Apache Maven** | **3.8+** | Gestión del ciclo de vida del proyecto, dependencias y compilación automatizada (`pom.xml` en Apéndice A). |
| **jSerialComm** | **`com.fazecast:jSerialComm:2.10.0+`** | Capa multiplataforma (Windows, Linux, macOS) para comunicación serie (UART / USB-CDC / RS-232 / RS-485) con microcontroladores y tarjetas DAQ. |
| **JUnit 5 (Jupiter)** | **`org.junit.jupiter:5.10+`** | Framework de pruebas automatizadas para verificación de lógica de calibración, escalas, conversiones matemáticas y procesamiento de tramas. |
| **Java Networking (Sockets TCP/UDP)** | **`java.net` (Estándar SE)** | Telemetría distribuida en red local, transmisión de datagramas y servidores de adquisición multihilo. |
| **Concurrencia en Tiempo Real** | **`java.util.concurrent` (Estándar SE)** | Hilos de adquisición periódica (`ScheduledExecutorService`), búferes atómicos y sincronización segura entre hardware y lógica de negocio. |
| **Interfaces Gráficas (GUI)** | **`javax.swing` y `java.awt` (Estándar SE)** | Cuadros de mando industriales, indicadores de estado, osciloscopios virtuales y paneles de monitoreo desacoplados de los hilos de comunicación. |
| **Intercambio de Datos** | **JSON y Binario de Alta Velocidad** | Serialización de calibraciones y protocolos binarios de trama fija/delimitada con verificación de redundancia (Checksum XOR / CRC). |

---

## 3. Filosofía de Ejecución: Doble Modalidad

Para garantizar que cualquier estudiante o profesional pueda realizar las prácticas sin depender de hardware específico de laboratorio, el código adopta una **arquitectura de doble modalidad**:

1. **Modo Simulado (100% reproducible):**  
   Los generadores de señales sintéticas (ondas senoidales, rampas térmicas, ruido gaussiano) y el emulador de hardware TCP multicanal ([`apendice_D_02_HardwareEmulator.java`](./apendice_D_02_HardwareEmulator.java)) permiten compilar, ejecutar y verificar el comportamiento del software sin requerir dispositivos físicos conectados.
2. **Modo Hardware Físico:**  
   Mediante la inyección de dependencias y el desacoplamiento por interfaces (`SensorLectura`, `ServicioAdquisicion`), la misma lógica de supervisión pasa a leer puertos serie reales (Arduino, ESP32, STM32, PIC) con solo cambiar el adaptador de entrada.

---

## 4. Estructura y Organización de los Códigos

Los 117 módulos de código se encuentran organizados con nomenclatura secuencial vinculada a cada capítulo y apéndice (`capXX_YY_Nombre.ext` y `apendice_X_YY_Nombre.ext`):

```
output/Java_e_inteligencia_artificial_para_aplicaciones_electr_nica/code/
├── README.md                                          # Este documento descriptivo y técnico
├── INDICE.md                                          # Catálogo tabular completo de los 117 archivos
├── cap01_01_ConversorInstrumental.java                # Conversión física ADC -> Tensión/Magnitud
├── cap01_03_MonitorTermicoSimulado.java               # Lazo continuo de adquisición y alarmas
├── cap02_02_FiltroPromedioMovil.java                  # Algoritmo de filtrado SMA para señales ruidosas
├── cap03_01_GestorRegistros.java                      # Operaciones de bajo nivel y máscaras de bits
├── cap04_02_Medicion.java                             # Modelado de objetos inmutables de medición
├── cap04_07_SistemaProteccionTermica.java             # Control de lazo cerrado (PWM y Relés)
├── cap05_04_GestorMonitoreo.java                      # Polimorfismo e interfaces de hardware
├── cap06_01_BufferCircularMediciones.java             # Búfer circular de alto rendimiento
├── cap07_04_calibracion_sensores.json                 # Configuración de calibración y offsets
├── cap08_02_PruebasInstrumentacion.java               # Batería de pruebas unitarias JUnit 5
├── cap09_02_MonitorIndustrialGUI.java                 # Interfaz visual Swing desacoplada
├── cap11_02_ServidorTelemetriaTCP.java                # Servidor concurrente de telemetría de red
├── cap12_02_ReceptorPuertoSerie.java                  # Lector de tramas serie con jSerialComm
├── cap13_01_MuestraSenal.java                         # Extracción de características para modelos de IA
├── cap16_04_LimitadorFrecuenciaIA.java                # Control de cuota y tasa para llamadas a IA
├── cap16_05_CacheDiagnosticos.java                    # Almacenamiento concurrente de inferencias
├── cap17_06_ControladorMonitoreo.java                 # Proyecto Integrador 1: Variables eléctricas
├── cap18_02_MaquinaEstadosDispositivo.java            # Proyecto Integrador 2: FSM para planta térmica
├── cap19_06_GeneradorReporteContextualIA.java         # Proyecto Integrador 3: Diagnóstico con IA
├── apendice_A_04_pom.xml                              # Archivo descriptor Maven para compilación
├── apendice_D_01_ProtocolHandler.java                 # Manejador de protocolos binarios de trama fija
├── apendice_D_02_HardwareEmulator.java                # Emulador de banco de pruebas TCP
└── apendice_E_03_ModernOscilloscopeApp.java           # Osciloscopio moderno migrado desde applets
```

---

## 5. Módulos Temáticos Destacados

### Parte I: Fundamentos y Orientación a Objetos (Cap. 1 al 4)
- **Modelado físico de la señal:** Resolución de bits, cuantificación, rango dinámico y relación matemática entre cuentas enteras y unidades físicas.
- **Seguridad en punto flotante y tipos nativos:** Prevención de trampas de división entera, tratamiento del byte sin signo (`& 0xFF`) y aritmética con tolerancia $\epsilon$.
- **Objetos de ingeniería inmutables:** Creación de clases de valor (`record` y encapsulamiento) que viajan a través del sistema sin degradación de estado.

### Parte II: Arquitectura, Rendimiento y Robustez (Cap. 5 al 8)
- **Patrones de adquisición:** Contratos polimórficos (`SensorLectura`) que separan los algoritmos de filtrado del medio físico subyacente.
- **Estructuras eficientes:** Búferes circulares en memoria fija sin instanciación repetitiva de objetos para evitar pausas del *Garbage Collector*.
- **Filtrado digital clásico:** Promedio móvil simple (SMA) y filtros de mediana para eliminación de ruido impulsivo (picos esporádicos).
- **Pruebas y calidad:** Verificación automatizada con pruebas unitarias de software sobre condiciones de saturación, offsets e inversión de polaridad.

### Parte III: Interfaces, Concurrencia y Dispositivos (Cap. 9 al 12)
- **Desacoplamiento GUI/Hardware:** Separación estricta entre el hilo de eventos de interfaz gráfica (EDT) y los hilos de fondo de comunicación serie o red.
- **Redes y telemetría:** Sockets TCP/IP multihilo que permiten a un instrumento publicar lecturas para clientes de análisis o paneles remotos.
- **Integración serie UART:** Manejo de eventos de recepción mediante `jSerialComm`, decodificación de tramas delimitadas y detección de tramas incompletas o corruptas.

### Parte IV: Inteligencia Artificial en Sistemas Electrónicos (Cap. 13 al 16)
- **Preprocesamiento y características:** Segmentación de series temporales en ventanas deslizantes y cálculo de medias, varianzas, valores RMS y picos.
- **Modelos de inferencia:** Integración de clientes ligeros de IA para clasificación de condiciones operativas y detección de anomalías.
- **Patrones de resiliencia para IA:** Implementación de limitadores de tasa (*rate limiters*) basados en el algoritmo Token Bucket y memorias intermedias (*caching*) para evitar costos y latencias excesivas en llamadas a modelos.

### Parte V: Proyectos Integradores de Ingeniería (Cap. 17 al 19)
- **Monitoreo de energía (Capítulo 17):** Sistema integral que captura tensión y corriente, calcula potencia activa, reactiva y factor de potencia ($\cos \varphi$), activando alarmas ante sobrecargas.
- **Supervisión de planta y FSM (Capítulo 18):** Máquina de estados finitos que gestiona el arranque, operación en lazo cerrado, enfriamiento y paradas de emergencia de un sistema de potencia térmica.
- **Diagnóstico predictivo con IA (Capítulo 19):** Clasificador de fallos de componentes electrónicos acoplado a un motor generador de informes contextuales para mantenimiento industrial.

---

## 6. Guía Rápida de Compilación y Ejecución

### Opción A: Compilación y Ejecución Directa con el JDK

Cualquier programa puede compilarse directamente desde la línea de comandos sin necesidad de instalar dependencias externas cuando solo utiliza la biblioteca estándar de Java:

```bash
cd output/Java_e_inteligencia_artificial_para_aplicaciones_electr_nica/code

# Compilar un programa de demostración
javac cap01_01_ConversorInstrumental.java

# Ejecutar la clase compilada
java ConversorInstrumental
```

### Opción B: Ejecución en Entorno Maven (con dependencias)

Para los programas que emplean `jSerialComm` o `JUnit 5`:

1. Sitúe o configure el archivo [`apendice_A_04_pom.xml`](./apendice_A_04_pom.xml) en la raíz de su proyecto Maven (`pom.xml`).
2. Coloque las clases Java en el directorio estándar `src/main/java/` y las pruebas en `src/test/java/`.
3. Ejecute el ciclo de compilación y pruebas:

```bash
# Compilar todo el proyecto
mvn compile

# Ejecutar las pruebas automatizadas
mvn test
```

### Opción C: Prueba de Comunicación de Red / Emulador de Hardware

Para probar el flujo de adquisición de datos por red:

1. **Terminal 1 (Lanzar emulador de hardware):**
   ```bash
   javac apendice_D_02_HardwareEmulator.java
   java HardwareEmulator
   # Iniciará el servidor de adquisición en el puerto TCP 5000
   ```

2. **Terminal 2 (Lanzar cliente o proyecto de adquisición):**
   ```bash
   javac cap11_01_ClienteTelemetriaTCP.java
   java ClienteTelemetriaTCP
   # Se conectará a localhost:5000 y recibirá tramas de tensión y temperatura
   ```

---

## 7. Convenciones de Ingeniería Adoptadas

- **Nombres con Unidades Explícitas:** Las variables físicas declaran de forma inequívoca sus unidades del Sistema Internacional:
  - `tensionVoltios`, `voltajeReferencia`
  - `corrienteMiliamperios`
  - `temperaturaCelsius`
  - `frecuenciaMuestreoHz`
  - `tiempoMuestreoMs`, `latenciaNanoSegundos`
- **Tolerancia a Fallos y Manejo Riguroso de Recursos:** Todos los descriptores de archivos, flujos de red y canales serie emplean bloques `try-with-resources` para garantizar el cierre seguro de recursos ante excepciones imprevistas.
- **Inmutabilidad Predeterminada:** Los datos de medición y registros de telemetría utilizan atributos `final` o registros (`record`) para impedir alteraciones accidentales durante su propagación en arquitecturas concurrentes.

---

Para consultar la referencia completa de cada uno de los 117 archivos, sus líneas de código, lenguaje y sección del libro correspondiente, acceda a [`INDICE.md`](./INDICE.md).
