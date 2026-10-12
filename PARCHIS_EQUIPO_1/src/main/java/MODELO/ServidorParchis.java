package MODELO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;

public final class ServidorParchis {

    private ServidorParchis() { }

    public static void main(String[] args) throws IOException {
        int puerto = Protocolo.PUERTO_POR_DEFECTO;
        if (args.length > 0) {
            try {
                puerto = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.err.println("Puerto no válido: " + args[0]);
                System.exit(1);
            }
        }
        ServerSocket servidor = arrancar(puerto);
        mostrarDirecciones(servidor.getLocalPort());
        try {
            Thread.currentThread().join();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static ServerSocket arrancar(int puerto) throws IOException {
        final Lobby lobby = new Lobby(new ReglasJuego());
        final ServerSocket servidor = new ServerSocket(puerto);
        Thread aceptador = new Thread(() -> {
            while (!servidor.isClosed()) {
                try {
                    Socket cliente = servidor.accept();
                    new SesionCliente(cliente, lobby).iniciar();
                } catch (IOException ex) {
                    if (!servidor.isClosed()) System.err.println("Error aceptando conexión: " + ex);
                }
            }
        }, "aceptado");
        aceptador.setDaemon(true);
        aceptador.start();
        return servidor;
    }
    
    private static void mostrarDirecciones(int puerto) {

        try {
            for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (!ni.isUp() || ni.isLoopback()) continue;
                for (InetAddress a : Collections.list(ni.getInetAddresses())) {
                    if (a.getAddress().length == 4) {
                        System.out.println("   " + a.getHostAddress() + ":" + puerto + "   (" + ni.getDisplayName() + ")");
                    }
                }
            }
        } catch (IOException ex) {
        }
    }
    
}
