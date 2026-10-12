package MODELO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public final class ClienteRed implements IServidorRemoto {

    private static final int TIEMPO_CONEXION_MS = 5000;

    private Socket socket;
    private PrintWriter salida;
    private volatile Consumer<String> receptor = linea -> { };
    private volatile boolean cerradoAdrede;
    private boolean avisoPerdida;

    public void setReceptor(Consumer<String> receptor) {
        this.receptor = receptor;
    }

    public boolean estaConectado() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

     public void conectar(String host, int puerto) throws IOException {
        cerrar();
        cerradoAdrede = false;
        avisoPerdida = false;
        Socket s = new Socket();
        s.connect(new InetSocketAddress(host, puerto), TIEMPO_CONEXION_MS);
        s.setKeepAlive(true);
        s.setTcpNoDelay(true);
        socket = s;
        salida = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true);
        final BufferedReader entrada = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
        Thread lector = new Thread(() -> leer(s, entrada), "cliente-red");
        lector.setDaemon(true);
        lector.start();
    }

    private void leer(Socket mio, BufferedReader entrada) {
        try {
            String linea;
            while ((linea = entrada.readLine()) != null) {
                receptor.accept(linea);
            }
        } catch (IOException ex) {
            
        }
        if (mio == socket) {
            avisarPerdida("Se cerró la conexión con el servidor");
        }
    }

    private synchronized void avisarPerdida(String motivo) {
        if (cerradoAdrede || avisoPerdida) return;
        avisoPerdida = true;
        receptor.accept(Campos.empaquetar(EventoModelo.CONEXION_PERDIDA, motivo));
    }

    @Override
    public synchronized void enviar(String... campos) {
        if (salida == null || !estaConectado()) {
            avisarPerdida("No hay conexión con el servidor");
            return;
        }
        salida.println(Campos.empaquetar(campos));
        if (salida.checkError()) {
            avisarPerdida("No se pudo enviar al servidor");
        }
    }

}
