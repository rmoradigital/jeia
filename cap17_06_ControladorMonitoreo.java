package controlador;

import adquisicion.ServicioAdquisicion;
import modelo.MedicionElectrica;
import procesamiento.DetectorAlertas;
import persistencia.RegistradorCSV;
import vista.InterfazPrincipal; // Asumida

/**
 * Orquestador que vincula la adquisición, el procesamiento y la vista.
 */
public class ControladorMonitoreo implements Runnable {
    private final ServicioAdquisicion servicio;
    private final DetectorAlertas detector;
    private final RegistradorCSV registrador;
    private final InterfazPrincipal vista;
    private volatile boolean ejecutando = false;

    public ControladorMonitoreo(ServicioAcquisicion s, DetectorAlertas d, 
                                RegistradorCSV r, InterfazPrincipal v) {
        this.servicio = s;
        this.detector = d;
        this.registrador = r;
        this.vista = v;
    }

    public void iniciar() {
        ejecutando = true;
        new Thread(this).start();
    }

    public void detener() {
        ejecutando = false;
    }

    @Override
    public void run() {
        servicio.conectar();
        while (ejecutando) {
            try {
                // 1. Adquisición
                MedicionElectrica lectura = servicio.obtenerUltimaMedicion();
                
                // 2. Procesamiento de alertas
                String alerta = detector.verificar(lectura);
                if (alerta != null) {
                    vista.mostrarAlerta(alerta);
                }

                // 3. Persistencia
                registrador.registrar(lectura);

                // 4. Actualización de vista
                vista.actualizarGrafica(lectura);

                // Control de frecuencia (10Hz aprox para tensión/corriente)
                Thread.sleep(100); 

            } catch (Exception e) {
                vista.mostrarError("Error en el bucle de adquisición: " + e.getMessage());
                ejecutando = false;
            }
        }
        servicio.desconectar();
    }
}
