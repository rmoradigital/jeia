/**
 * Representa un relé físico utilizado para la protección de circuitos.
 * El estado puede ser "CERRADO" (conduciendo corriente) o "ABIERTO" (aislado).
 */
public class ActuadorRele {
    private final String nombreRelé;
    private boolean estaCerrado; // true = cerrado (conducción), false = abierto (aislado)

    /**
     * Constructor para inicializar el relé en estado de reposo (abierto).
     * @param nombreRelé Identificador del componente.
     */
    public ActuadorRele(String nombreRelé) {
        this.nombreRelé = nombreRelé;
        this.estaCerrado = false; // Por seguridad, los sistemas suelen iniciar en estado abierto
    }

    /**
     * Cambia el estado del relé.
     * @param debeCerrar true para cerrar el circuito, false para abrirlo.
     */
    public void setEstado(boolean debeCerrar) {
        this.estaCerrado = debeCerrado;
        String estadoTexto = estaCerrado ? "CERRADO" : "ABIERTO";
        System.out.println("[HARDWARE] Relé " + nombreRelé + " cambiado a estado: " + estadoTexto);
    }

    public boolean estaCerrado() {
        return estaCerrado;
    }

    public String getNombreRelé() {
        return nombreRelé;
    }

    @Override
    public String toString() {
        return "Relé: " + nombreRelé + " | Estado: " + (estaCerrado ? "CONDUCIENDO" : "AISLADO");
    }
}
