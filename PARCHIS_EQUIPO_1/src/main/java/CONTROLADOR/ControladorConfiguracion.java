package CONTROLADOR;

import MODELO.ModeloPartida;
import MODELO.Validaciones;

public class ControladorConfiguracion {

    private final ModeloPartida modelo;

    public ControladorConfiguracion(ModeloPartida modelo) {
        this.modelo = modelo;
    }

    public void crearPartida(String nombreSala, String clave, int maxJugadores) {
        modelo.crearPartida(nombreSala, clave, maxJugadores);
    }

    public String validarNombreSala(String nombre) {
        return Validaciones.nombreSala(nombre);
    }

    public String validarClave(String clave) {
        return Validaciones.claveSala(clave);
    }

    public String validarMaxJugadores(int max) {
        return Validaciones.maxJugadores(max);
    }
}
