package CONTROLADOR;

import MODELO.ModeloPartida;


public class ControladorUnirse {

    private final ModeloPartida modelo;

    public ControladorUnirse(ModeloPartida modelo) {
        this.modelo = modelo;
    }

    public void refrescarSalas() {
        modelo.solicitarListaSalas();
    }

    public void solicitarUnirse(int idSala, String clave) {
        modelo.solicitarUnirse(idSala, clave);
    }

    public void cancelarSolicitud() {
        modelo.cancelarSolicitud();
    }
}
