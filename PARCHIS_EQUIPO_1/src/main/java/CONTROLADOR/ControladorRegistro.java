package CONTROLADOR;

import MODELO.ModeloPartida;
import MODELO.Validaciones;

public class ControladorRegistro {

    private final ModeloPartida modelo;

    public ControladorRegistro(ModeloPartida modelo) {
        this.modelo = modelo;
    }

    public void registrarUsuario(String nombre) {
        modelo.registrarUsuario(nombre);
    }

    public String validarNombre(String nombre) {
        return Validaciones.nombreUsuario(nombre);
    }
}
