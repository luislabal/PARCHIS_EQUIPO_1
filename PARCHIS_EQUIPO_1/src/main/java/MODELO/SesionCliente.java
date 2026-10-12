package MODELO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

final class SesionCliente {

    private static final int MAX_LINEA = 2048;
    private static final int MAX_PENDIENTES = 500;
    private static final String CIERRE = "\u0000CIERRE";

    private final Socket socket;
    private final Lobby lobby;
    private final BlockingQueue<String> salida = new LinkedBlockingQueue<>();
    private volatile boolean cerrada;

    Jugador jugador;
    EstadoPartida sala;
    int solicitudSala = -1;

    SesionCliente(Socket socket, Lobby lobby) {
        this.socket = socket;
        this.lobby = lobby;
    }

    String direccion() {
        return String.valueOf(socket.getRemoteSocketAddress());
    }

    void iniciar() {
        Thread escritor = new Thread(this::escribir, "escritor-" + direccion());
        escritor.setDaemon(true);
        escritor.start();
        Thread lector = new Thread(this::leer, "lector-" + direccion());
        lector.setDaemon(true);
        lector.start();
    }

   
    void enviar(String... campos) {
        if (cerrada) return;
        if (salida.size() > MAX_PENDIENTES) {   
            cerrar();
            return;
        }
        salida.add(Campos.empaquetar(campos));
    }

    void cerrar() {
        cerrada = true;
        salida.add(CIERRE);
        try { socket.close(); } catch (IOException ignorada) { }
    }

    private void escribir() {
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            while (true) {
                String m = salida.take();
                if (CIERRE.equals(m)) break;
                out.println(m);
                if (out.checkError()) break;
            }
        } catch (IOException | InterruptedException ex) {
            
        }
        cerrar();
    }

    private void leer() {
        if (!lobby.conectar(this)) {
            cerrar();
            return;
        }
        try {
            InputStream in = socket.getInputStream();
            InputStreamReader lector = new InputStreamReader(in, StandardCharsets.UTF_8);
            StringBuilder linea = new StringBuilder();
            int ch;
            while (!cerrada && (ch = lector.read()) != -1) {
                if (ch == '\n') {
                    atender(linea.toString().trim());
                    linea.setLength(0);
                } else if (linea.length() >= MAX_LINEA) {
                    break;                                   
                } else {
                    linea.append((char) ch);
                }
            }
        } catch (IOException ex) {
          
        } finally {
            cerrar();
            lobby.desconectar(this);      
        }
    }   
     private void atender(String linea) {
        if (linea.isEmpty()) return;
        try {
            List<String> c = Campos.desempaquetar(linea);
            if (c.isEmpty()) return;
            switch (c.get(0)) {
                case Protocolo.REGISTRAR: lobby.registrar(this, arg(c, 1)); break;
                case Protocolo.CREAR: lobby.crear(this, arg(c, 1), arg(c, 2), entero(arg(c, 3))); break;
                case Protocolo.SALAS: lobby.enviarSalas(this); break;
                case Protocolo.UNIRSE: lobby.unirse(this, entero(arg(c, 1)), arg(c, 2)); break;
                case Protocolo.CANCELAR_SOLICITUD: lobby.cancelarSolicitud(this); break;
                case Protocolo.ACEPTAR: lobby.aceptar(this); break;
                case Protocolo.RECHAZAR: lobby.rechazar(this); break;
                case Protocolo.LISTO: lobby.listo(this, Boolean.parseBoolean(arg(c, 1).trim())); break;
                case Protocolo.INICIAR: lobby.iniciar(this, entero(arg(c, 1))); break;
                case Protocolo.ABANDONAR: lobby.abandonar(this, true); break;
                default: break;                             
            }
        } catch (RuntimeException ex) {
            System.err.println("Error atendiendo a " + direccion() + ": " + ex);
        }
    }
}
