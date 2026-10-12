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

     private Aplicacion() { }

    public static ModeloPartida getModelo() { return MODELO; }
    public static ControladorRegistro getControladorRegistro() { return CTRL_REGISTRO; }
    public static ControladorConfiguracion getControladorConfiguracion() { return CTRL_CONFIGURACION; }
    public static ControladorUnirse getControladorUnirse() { return CTRL_UNIRSE; }
    public static ControladorPartida getControladorPartida() { return CTRL_PARTIDA; }

    public static void conectar(String direccion) throws IOException {
        String host = direccion.trim();
        int puerto = Protocolo.PUERTO_POR_DEFECTO;
        int i = host.lastIndexOf(':');
        if (i >= 0) {
            try {
                puerto = Integer.parseInt(host.substring(i + 1).trim());
            } catch (NumberFormatException ex) {
                throw new IOException("Puerto no valido: " + host.substring(i + 1));
            }
            host = host.substring(0, i).trim();
        }
        if (host.isEmpty()) throw new IOException("Escribe la direccion del servidor");
        RED.conectar(host, puerto);
    }
}
