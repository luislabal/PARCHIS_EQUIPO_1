package CONTROLADOR;

import MODELO.ModeloPartida;

public class ControladorPartida {

    private final ModeloPartida modelo;

    public ControladorPartida(ModeloPartida modelo) {
        this.modelo = modelo;
    }

    public void cambiarListo(boolean listo) {
        modelo.cambiarListo(listo);
    }

    public void iniciarPartida(int idPartida) {
        modelo.iniciarPartida(idPartida);
    }

    public void aceptarSolicitud() {
        modelo.aceptarSolicitud();
    }

    public void rechazarSolicitud() {
        modelo.rechazarSolicitud();
    }

    public void abandonarPartida() {
        modelo.abandonarPartida();
    }
}
