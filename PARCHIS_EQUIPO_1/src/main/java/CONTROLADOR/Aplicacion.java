package CONTROLADOR;

import MODELO.ClienteRed;
import MODELO.ModeloPartida;
import MODELO.Protocolo;
import java.io.IOException;
import javax.swing.SwingUtilities;


public final class Aplicacion {

    private static final ClienteRed RED = new ClienteRed();
    private static final ModeloPartida MODELO = new ModeloPartida(RED);
    private static final ControladorRegistro CTRL_REGISTRO = new ControladorRegistro(MODELO);
    private static final ControladorConfiguracion CTRL_CONFIGURACION = new ControladorConfiguracion(MODELO);
    private static final ControladorUnirse CTRL_UNIRSE = new ControladorUnirse(MODELO);
    private static final ControladorPartida CTRL_PARTIDA = new ControladorPartida(MODELO);

    static {
        RED.setReceptor(linea -> SwingUtilities.invokeLater(() -> MODELO.procesarLinea(linea)));
    }

    
}
