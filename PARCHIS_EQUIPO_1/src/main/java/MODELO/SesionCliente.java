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

    
}
