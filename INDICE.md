# Catálogo de Códigos Fuente — Java e Inteligencia Artificial para Aplicaciones Electrónicas

Este directorio almacena de forma independiente todos los códigos fuente, clases, interfaces, scripts y archivos de configuración generados a lo largo del libro. Cada archivo sigue una nomenclatura secuencial y referencial al capítulo correspondiente (`capXX_YY_Nombre.ext` o `apendice_X_YY_Nombre.ext`).

**Total de archivos de código extraídos:** 117

---

### Capítulo 1: El software en los sistemas electrónicos

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap01_01_ConversorInstrumental.java`](./cap01_01_ConversorInstrumental.java) | `java` | 74 | Ejemplos resueltos: Transformación de datos digitales a magnitudes físicas con Java. |
| [`cap01_02_traza_ejecucion_consola.txt`](./cap01_02_traza_ejecucion_consola.txt) | `txt` | 10 | Análisis de la ejecución y traza de datos |
| [`cap01_03_MonitorTermicoSimulado.java`](./cap01_03_MonitorTermicoSimulado.java) | `java` | 106 | 4.1 Implementación de la lógica de monitoreo y detección de alarmas |

### Capítulo 2: Resolución de problemas y pensamiento algorítmico

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap02_01_CalculosInstrumentacion.java`](./cap02_01_CalculosInstrumentacion.java) | `java` | 49 | Conversión de señales de ADC y aplicación de la Ley de Ohm |
| [`cap02_02_FiltroPromedioMovil.java`](./cap02_02_FiltroPromedioMovil.java) | `java` | 53 | Implementación de un promedio móvil simple (SMA) |
| [`cap02_03_ComparadorHisteresis.java`](./cap02_03_ComparadorHisteresis.java) | `java` | 49 | Implementación de laboratorio |
| [`cap02_04_snippet_error_punto_flotante.java`](./cap02_04_snippet_error_punto_flotante.java) | `java` | 3 | Comparación de números de punto flotante |
| [`cap02_05_snippet_solucion_epsilon_punto_flotante.java`](./cap02_05_snippet_solucion_epsilon_punto_flotante.java) | `java` | 4 | Comparación de números de punto flotante |

### Capítulo 3: Fundamentos del lenguaje Java

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap03_01_GestorRegistros.java`](./cap03_01_GestorRegistros.java) | `java` | 47 | 3.1 Gestión de Banderas de Estado mediante Máscaras de Bits |
| [`cap03_02_CalibradorSensor.java`](./cap03_02_CalibradorSensor.java) | `java` | 57 | 3.2 Calculadora Modular de Calibración para Sensores Analógicos. |
| [`cap03_03_SimuladorAdquisicion.java`](./cap03_03_SimuladorAdquisicion.java) | `java` | 85 | Implementación del simulador de adquisición |
| [`cap03_04_snippet_error_compilacion_ia.java`](./cap03_04_snippet_error_compilacion_ia.java) | `java` | 3 | Ejercicio 3: Uso de IA para la resolución de errores de compilación |
| [`cap03_05_snippet_extension_signo_byte_error.java`](./cap03_05_snippet_extension_signo_byte_error.java) | `java` | 3 | El problema de la extensión de signo en el tipo `byte` |
| [`cap03_06_snippet_extension_signo_byte_solucion.java`](./cap03_06_snippet_extension_signo_byte_solucion.java) | `java` | 2 | El problema de la extensión de signo en el tipo `byte` |
| [`cap03_07_snippet_division_entera_error.java`](./cap03_07_snippet_division_entera_error.java) | `java` | 3 | La trampa de la división entera |
| [`cap03_08_snippet_division_entera_solucion.java`](./cap03_08_snippet_division_entera_solucion.java) | `java` | 1 | La trampa de la división entera |

### Capítulo 4: Clases y objetos para representar sistemas reales

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap04_01_snippet_antipatron_variables_dispersas.java`](./cap04_01_snippet_antipatron_variables_dispersas.java) | `java` | 8 | 1. Problema inicial: desorden y fragilidad al gestionar datos dispersos |
| [`cap04_02_Medicion.java`](./cap04_02_Medicion.java) | `java` | 45 | 3.1. La clase `Medicion`: un objeto de valor inmutable |
| [`cap04_03_SensorTemperatura.java`](./cap04_03_SensorTemperatura.java) | `java` | 41 | 3.2. La clase `SensorTemperatura`: encapsulamiento de límites. |
| [`cap04_04_CanalADC.java`](./cap04_04_CanalADC.java) | `java` | 34 | 3.3. La clase `CanalADC`: modelado de adquisición analógica |
| [`cap04_05_ActuadorPWM.java`](./cap04_05_ActuadorPWM.java) | `java` | 37 | 3.4. La clase `ActuadorPWM`: control con validación de ciclo de trabajo |
| [`cap04_06_ActuadorRele.java`](./cap04_06_ActuadorRele.java) | `java` | 40 | 4.1. Implementación del actuador binario: `ActuadorRele` |
| [`cap04_07_SistemaProteccionTermica.java`](./cap04_07_SistemaProteccionTermica.java) | `java` | 84 | 4.2. El controlador de lazo cerrado: `SistemaProteccionTermica` |

### Capítulo 5: Relaciones entre objetos y diseño del software

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap05_01_SensorLectura.java`](./cap05_01_SensorLectura.java) | `java` | 21 | Definición del contrato: La interfaz SensorLectura |
| [`cap05_02_SensorSimuladoSenoidal.java`](./cap05_02_SensorSimuladoSenoidal.java) | `java` | 46 | Implementación A: El simulador de señales periódicas |
| [`cap05_03_SensorHardwareSerialStub.java`](./cap05_03_SensorHardwareSerialStub.java) | `java` | 40 | Implementación B: El adaptador para hardware real |
| [`cap05_04_GestorMonitoreo.java`](./cap05_04_GestorMonitoreo.java) | `java` | 68 | Orquestación: El Gestor de Monitoreo Polimórfico |
| [`cap05_05_SistemaRigido.java`](./cap05_05_SistemaRigido.java) | `java` | 47 | El problema: el sistema de monitoreo rígido |
| [`cap05_06_SistemaFlexible.java`](./cap05_06_SistemaFlexible.java) | `java` | 101 | Paso 3: Inyección de dependencias |

### Capítulo 6: Estructuras de datos y procesamiento de información

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap06_01_BufferCircularMediciones.java`](./cap06_01_BufferCircularMediciones.java) | `java` | 78 | Implementación de un Buffer Circular de alta eficiencia |
| [`cap06_02_FiltroMediana.java`](./cap06_02_FiltroMediana.java) | `java` | 33 | Implementación del Filtro de Mediana |
| [`cap06_03_GestorCanales.java`](./cap06_03_GestorCanales.java) | `java` | 44 | Gestión de múltiples canales mediante diccionarios. |
| [`cap06_04_ProcesadorSenalPrincipal.java`](./cap06_04_ProcesadorSenalPrincipal.java) | `java` | 142 | Implementación del procesador de señales |

### Capítulo 7: Manejo de errores y persistencia de datos

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap07_01_MainSistemaInstrumentacion.java`](./cap07_01_MainSistemaInstrumentacion.java) | `java` | 196 | Implementación de un gestor de datos robusto |
| [`cap07_02_snippet_try_with_resources.java`](./cap07_02_snippet_try_with_resources.java) | `java` | 2 | Análisis de la solución implementada |
| [`cap07_03_DataloggerApp.java`](./cap07_03_DataloggerApp.java) | `java` | 175 | Implementación del sistema. |
| [`cap07_04_calibracion_sensores.json`](./cap07_04_calibracion_sensores.json) | `json` | 7 | Ejercicio 2: Carga de parámetros de calibración mediante JSON |
| [`cap07_05_snippet_antipatron_excepcion_generica.java`](./cap07_05_snippet_antipatron_excepcion_generica.java) | `java` | 6 | Errores comunes de ingeniería |

### Capítulo 8: Depuración, pruebas y documentación

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap08_01_ConversorADC.java`](./cap08_01_ConversorADC.java) | `java` | 100 | 3. Ejemplos resoltos: Validación de la cadena de adquisición |
| [`cap08_02_PruebasInstrumentacion.java`](./cap08_02_PruebasInstrumentacion.java) | `java` | 88 | 3. Ejemplos resoltos: Validación de la cadena de adquisición |
| [`cap08_03_ProcesadorSenal.java`](./cap08_03_ProcesadorSenal.java) | `java` | 73 | 4.2. Implementacion del modulo con defectos |
| [`cap08_04_ProcesadorSenalCorregido.java`](./cap08_04_ProcesadorSenalCorregido.java) | `java` | 47 | 4.4. Implementacion de la solucion y validacion final |
| [`cap08_05_SensorTemperatura.java`](./cap08_05_SensorTemperatura.java) | `java` | 51 | 5.1. Implementacion de un Doble de Prueba (Mocking) para sensores |

### Capítulo 9: Interfaces gráficas en Java

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap09_01_InstrumentoVirtualGUI.java`](./cap09_01_InstrumentoVirtualGUI.java) | `java` | 145 | Implementación de un panel de instrumento modular. |
| [`cap09_02_MonitorIndustrialGUI.java`](./cap09_02_MonitorIndustrialGUI.java) | `java` | 213 | 4.2. Implementación completa del monitor industrial |

### Capítulo 10: Eventos, concurrencia y organización de la aplicación

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap10_01_SensorListener.java`](./cap10_01_SensorListener.java) | `java` | 178 | Implementación de un sistema de adquisición bajo el patrón MVC |
| [`cap10_02_SensorDinamicoApp.java`](./cap10_02_SensorDinamicoApp.java) | `java` | 209 | Implementación de un sensor con frecuencia variable. |
| [`cap10_03_SistemaAltaVelocidad.java`](./cap10_03_SistemaAltaVelocidad.java) | `java` | 101 | Implementación de un sistema de captura y registro de alta velocidad |

### Capítulo 11: Comunicación entre aplicaciones y dispositivos

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap11_01_ProtocolFramebyte.java`](./cap11_01_ProtocolFramebyte.java) | `java` | 97 | Implementación de un analizador de protocolos (Parser) |
| [`cap11_02_EnlaceDispositivo.java`](./cap11_02_EnlaceDispositivo.java) | `java` | 33 | Abstracción de hardware mediante interfaces |
| [`cap11_03_SimuladorInstrumentoTCP.java`](./cap11_03_SimuladorInstrumentoTCP.java) | `java` | 66 | Simulación de instrumentos mediante Sockets TCP |
| [`cap11_04_TelemetryFrame.java`](./cap11_04_TelemetryFrame.java) | `java` | 64 | Ejemplo: Serialización de una trama de telemetría industrial |
| [`cap11_05_TelemetryAnomalyDetector.java`](./cap11_05_TelemetryAnomalyDetector.java) | `java` | 53 | Ejemplo: Monitor de telemetría con validación de coherencia física. |

### Capítulo 12: Adquisición, visualización y registro de mediciones

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap12_01_MotorAdquisicion.java`](./cap12_01_MotorAdquisicion.java) | `java` | 162 | 3. Ejemplos resueltoos con código comentado: Implementación de un motor de representación gráfica y de registro |
| [`cap12_02_PaqueteMedicion.java`](./cap12_02_PaqueteMedicion.java) | `java` | 30 | 4.1. Diseño de la estructura multicanal. |
| [`cap12_03_MotorAdquisicionMulticanal.java`](./cap12_03_MotorAdquisicionMulticanal.java) | `java` | 99 | 4.2. Implementación del motor de adquisición multicanal |

### Capítulo 13: Fundamentos de inteligencia artificial para aplicaciones electrónicas

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap13_01_ComparativaDeteccion.java`](./cap13_01_ComparativaDeteccion.java) | `java` | 150 | Ejemplos resueltos: comparación entre detección por umbral y análisis de características. |
| [`cap13_02_SistemaSeguridadCritica.java`](./cap13_02_SistemaSeguridadCritica.java) | `java` | 32 | Ejercicio 2: Análisis de riesgos en sistemas de control con IA |

### Capítulo 14: Desarrollo de software asistido por IA

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap14_01_TelemetryParser.java`](./cap14_01_TelemetryParser.java) | `java` | 11 | Fase 1: La propuesta inicial de la IA (Defectuosa) |
| [`cap14_02_TelemetryParser.java`](./cap14_02_TelemetryParser.java) | `java` | 54 | Fase 3: Implementación corregida y verificada |
| [`cap14_03_TelemetryParserTest.java`](./cap14_03_TelemetryParserTest.java) | `java` | 41 | Fase 4: Verificación mediante pruebas unitarias |
| [`cap14_04_SensorTensionDefectuoso.java`](./cap14_04_SensorTensionDefectuoso.java) | `java` | 34 | El escenario de auditoría |
| [`cap14_05_SensorTensionRobusto.java`](./cap14_05_SensorTensionRobusto.java) | `java` | 58 | Paso 3: Refactorización técnica |

### Capítulo 15: Preparación de datos y modelos elementales

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap15_01_Normalizador.java`](./cap15_01_Normalizador.java) | `java` | 77 | 2.1 Implementación de Normalización y Compensación |
| [`cap15_02_PuntoCaracteristica.java`](./cap15_02_PuntoCaracteristica.java) | `java` | 78 | 2.2 Implementación del Clasificador k-NN |
| [`cap15_03_AplicacionMonitoreoBomba.java`](./cap15_03_AplicacionMonitoreoBomba.java) | `java` | 49 | 2.3 Orquestador de la aplicación (Simulación) |
| [`cap15_04_LaboratorioIA.java`](./cap15_04_LaboratorioIA.java) | `java` | 155 | Implementación del laboratorio en Java |

### Capítulo 16: Integración de capacidades de IA en aplicaciones Java

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap16_01_EjemploIntegracionIA.java`](./cap16_01_EjemploIntegracionIA.java) | `java` | 125 | 3. Ejemplos resueltos: Implementación de un cliente robusto y asíncrono |
| [`cap16_02_SimuladorSensorTemperatura.java`](./cap16_02_SimuladorSensorTemperatura.java) | `java` | 22 | Paso 1: Definición del simulador de datos físicos. |
| [`cap16_03_AgregadorDeDatos.java`](./cap16_03_AgregadorDeDatos.java) | `java` | 46 | Paso 2: Implementación del Agregador de Ventanas. |
| [`cap16_04_LimitadorFrecuenciaIA.java`](./cap16_04_LimitadorFrecuenciaIA.java) | `java` | 33 | Optimización mediante limitación de tasa (Rate Limiting) |
| [`cap16_05_CacheDiagnosticos.java`](./cap16_05_CacheDiagnosticos.java) | `java` | 40 | Optimización mediante caché de diagnósticos |

### Capítulo 17: Sistema de monitoreo de variables eléctricas

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap17_01_ServicioAdquisicion.java`](./cap17_01_ServicioAdquisicion.java) | `java` | 30 | 2.2 Estrategia para la conmutación entre simulación y hardware |
| [`cap17_02_MedicionElectrica.java`](./cap17_02_MedicionElectrica.java) | `java` | 35 | 3.1 La entidad de datos: MedicionElectrica |
| [`cap17_03_SimuladorServicioAdquisicion.java`](./cap17_03_SimuladorServicioAdquisicion.java) | `java` | 45 | 3.2.1 Simulador de señales con ruido gaussiano |
| [`cap17_04_SerialServicioAdquisicion.java`](./cap17_04_SerialServicioAdquisicion.java) | `java` | 70 | 3.2.2 Procesamiento de la trama serial |
| [`cap17_05_DetectorAlertas.java`](./cap17_05_DetectorAlertas.java) | `java` | 50 | 3.3 Lógica de procesamiento: Detector de alertas y Persistencia |
| [`cap17_06_ControladorMonitoreo.java`](./cap17_06_ControladorMonitoreo.java) | `java` | 66 | 3.4 El Orquestador: Controlador de Monitoreo |
| [`cap17_07_CalculadorEnergia.java`](./cap17_07_CalculadorEnergia.java) | `java` | 45 | 5.1 Implementación del cálculo de energía eléctrica |

### Capítulo 18: Sistema de supervisión y control de un dispositivo

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap18_01_ComandoSeguro.java`](./cap18_01_ComandoSeguro.java) | `java` | 32 | El mecanismo de vigilancia (Watchdown) en el software de supervisión |
| [`cap18_02_MaquinaEstadosDispositivo.java`](./cap18_02_MaquinaEstadosDispositivo.java) | `java` | 72 | La Máquina de Estados Finitas como núcleo de la seguridad. |
| [`cap18_03_GestorComandosConfirmados.java`](./cap18_03_GestorComandosConfirmados.java) | `java` | 45 | Gestión robusta de comandos: el patrón de confirmación |
| [`cap18_04_SimuladorPlantaTermica.java`](./cap18_04_SimuladorPlantaTermica.java) | `java` | 56 | Modelado de la planta térmica mediante simulación. |
| [`cap18_05_GeneradorRampaTermica.java`](./cap18_05_GeneradorRampaTermica.java) | `java` | 66 | 5.1 Implementación de un controlador de rampa térmica lineal |
| [`cap18_06_SensorInseguro.java`](./cap18_06_SensorInseguro.java) | `java` | 28 | 6.1 Condiciones de carrera (Race Conditions) en la telemetría |

### Capítulo 19: Análisis de mediciones con apoyo de IA

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`cap19_01_Medicion.java`](./cap19_01_Medicion.java) | `java` | 52 | Implementación de la estructura de datos base en Java. |
| [`cap19_02_VectorCaracteristicas.java`](./cap19_02_VectorCaracteristicas.java) | `java` | 76 | El proceso de transformación: de señal cruda a vector de características |
| [`cap19_03_EstadoOperacion.java`](./cap19_03_EstadoOperacion.java) | `java` | 71 | El clasificador basado en reglas y lógica de estado |
| [`cap19_04_EvaluadorDesempeno.java`](./cap19_04_EvaluadorDesempeno.java) | `java` | 36 | Evaluación de desempeño del modelo de diagnóstico. |
| [`cap19_05_SimuladorDeFallos.java`](./cap19_05_SimuladorDeFallos.java) | `java` | 47 | Generación de series temporales con inyección de fallos |
| [`cap19_06_GeneradorReporteContextualIA.java`](./cap19_06_GeneradorReporteContextualIA.java) | `java` | 68 | 5.1 Generacion de reportes tecnicos asistidos por modelos de lenguaje |

### Apéndice A: Instalación y configuración del entorno de trabajo

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`apendice_A_01_configuracion_variables_entorno.sh`](./apendice_A_01_configuracion_variables_entorno.sh) | `sh` | 2 | Configuración de variables de entorno |
| [`apendice_A_02_verificacion_instalacion_jdk.sh`](./apendice_A_02_verificacion_instalacion_jdk.sh) | `sh` | 2 | Verificación de la instalación. |
| [`apendice_A_03_arbol_proyecto_maven.txt`](./apendice_A_03_arbol_proyecto_maven.txt) | `txt` | 9 | Estructura profesional de proyectos. |
| [`apendice_A_04_pom.xml`](./apendice_A_04_pom.xml) | `xml` | 50 | Configuración del archivo `pom.xml` |
| [`apendice_A_05_VerificadorEntornoHardware.java`](./apendice_A_05_VerificadorEntornoHardware.java) | `java` | 57 | Paso 2: Implementación del Verificador de Entorno |
| [`apendice_A_06_salida_ejecucion_verificacion.txt`](./apendice_A_06_salida_ejecucion_verificacion.txt) | `txt` | 6 | Paso 3: Ejecución y verificación |
| [`apendice_A_07_SimuladorTramaSensor.java`](./apendice_A_07_SimuladorTramaSensor.java) | `java` | 51 | Parte 1: Simulación de flujo de datos mediante tr அமas estructuradas |
| [`apendice_A_08_PruebaLoopbackFisica.java`](./apendice_A_08_PruebaLoopbackFisica.java) | `java` | 73 | Parte 2: Prueba de lazo (Loopback) mediante hardware físico |

### Apéndice B: Referencia rápida de sintaxis Java

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`apendice_B_01_ConversorProtocolo.java`](./apendice_B_01_ConversorProtocolo.java) | `java` | 23 | Conversión segura y el problema del byte sin signo |
| [`apendice_B_02_ProcesadorTramas.java`](./apendice_B_02_ProcesadorTramas.java) | `java` | 27 | Ejemplo: Extracción de datos de un registro de sensores |
| [`apendice_B_03_SensorInterface.java`](./apendice_B_03_SensorInterface.java) | `java` | 31 | Clases, interfaces y la abstracción de hardware |
| [`apendice_B_04_EstadoActuador.java`](./apendice_B_04_EstadoActuador.java) | `java` | 27 | Enumeraciones para la gestión de estados |
| [`apendice_B_05_MedicionInmutabledouble.java`](./apendice_B_05_MedicionInmutabledouble.java) | `java` | 23 | Registros (`record`) y el principio de inmutabilidad |
| [`apendice_B_06_BufferDeTension.java`](./apendice_B_06_BufferDeTension.java) | `java` | 51 | Organización de mediciones mediante colecciones |
| [`apendice_B_07_ReporteTecnico.java`](./apendice_B_07_ReporteTecnico.java) | `java` | 31 | Gestión de marcas de tiempo. |
| [`apendice_B_08_SistemaMonitoreoIndustrial.java`](./apendice_B_08_SistemaMonitoreoIndustrial.java) | `java` | 67 | Patrones de ejecución periódica y comunicación de red |

### Apéndice C: Diagramas de clases y convenciones de documentación

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`apendice_C_01_modelo_javadoc_adquisicion.java`](./apendice_C_01_modelo_javadoc_adquisicion.java) | `java` | 14 | Ejemplo de Modelado de un Sistema de Adquisición. |
| [`apendice_C_02_SensorTemperaturaCelsius.java`](./apendice_C_02_SensorTemperaturaCelsius.java) | `java` | 82 | Ejemplo de Implementación de un Sensor con Documentación Rigurosa |
| [`apendice_C_03_Data.java`](./apendice_C_03_Data.java) | `java` | 9 | Ejercicio 2: Refactorización de Código de Ingeniería |

### Apéndice D: Protocolos y conjuntos de datos para las prácticas

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`apendice_D_01_ProtocolHandler.java`](./apendice_D_01_ProtocolHandler.java) | `java` | 143 | Implementación de Referencia en Java |
| [`apendice_D_02_HardwareEmulator.java`](./apendice_D_02_HardwareEmulator.java) | `java` | 174 | Código Fuente: Implementación del Emulador |

### Apéndice E: Lectura y adaptación de código Java heredado, incluidos applets

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`apendice_E_01_LegacySignalApplet.java`](./apendice_E_01_LegacySignalApplet.java) | `java` | 79 | Ejemplo conceptual de la diferencia de estructura |
| [`apendice_E_02_RCOscilloscopeApplet.java`](./apendice_E_02_RCOscilloscopeApplet.java) | `java` | 73 | El código heredado: Un modelo de riesgo técnico |
| [`apendice_E_03_ModernOscilloscopeApp.java`](./apendice_E_03_ModernOscilloscopeApp.java) | `java` | 119 | Implementación moderna: Un estándar de ingeniería |

### Apéndice F: Glosario de programación, electrónica e inteligencia artificial

| Archivo | Lenguaje | Líneas | Sección / Contexto en el Libro |
| :--- | :--- | :--- | :--- |
| [`apendice_F_01_GlosarioDemo.java`](./apendice_F_01_GlosarioDemo.java) | `java` | 113 | Implementación integrada de conceptos |
| [`apendice_F_02_VerificadorIntegridad.java`](./apendice_F_02_VerificadorIntegridad.java) | `java` | 57 | Implementación de verificación de integridad en Java. |
