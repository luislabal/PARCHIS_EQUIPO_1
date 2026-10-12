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

    
}
