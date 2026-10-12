package MODELO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ReglasJuego implements IReglasJuego {

    private static final String[] COLORES = {"ROJO", "AZUL", "VERDE", "AMARILLO"};

    private final Map<Integer, EstadoPartida> partidas = new LinkedHashMap<>();
    private final Set<String> nombresTomados = new HashSet<>();
    private int sigJugador = 1;
    private int sigPartida = 1;

    private String colorLibre(EstadoPartida p) {
        for (String c : COLORES) {
            boolean usado = false;
            for (Jugador j : p.getJugadores()) {
                if (c.equals(j.getColor())) usado = true;
            }
            if (!usado) return c;
        }
        return COLORES[0];
    }

     @Override
    public boolean nombreDisponible(String nombre) {
        return nombre != null && !nombresTomados.contains(nombre.trim().toLowerCase());
    }

    @Override
    public Jugador registrarJugador(String nombre) {
        String t = nombre.trim();
        nombresTomados.add(t.toLowerCase());
        return new Jugador(sigJugador++, t, "neco.png");
    }

    @Override
    public void liberarNombre(String nombre) {
        if (nombre != null) nombresTomados.remove(nombre.trim().toLowerCase());
    }

    @Override
    public EstadoPartida crearPartida(String nombre, String clave, int maxJugadores, Jugador anfitrion) {
        EstadoPartida p = new EstadoPartida(sigPartida++, nombre.trim(), clave, maxJugadores);
        anfitrion.setEsAnfitrion(true);
        anfitrion.setPreparado(true);
        anfitrion.setColor(colorLibre(p));
        p.agregarJugador(anfitrion);
        partidas.put(p.getIdPartida(), p);
        return p;
    }

    @Override
    public List<EstadoPartida> listarSalasDisponibles() {
        List<EstadoPartida> r = new ArrayList<>();
        for (EstadoPartida p : partidas.values()) {
            if (EstadoPartida.ESPERANDO.equals(p.getEstado())) r.add(p);
        }
        return r;
    }

    @Override
    public boolean validarClave(int idPartida, String clave) {
        EstadoPartida p = partidas.get(idPartida);
        return p != null && p.claveCoincide(clave);
    }

    @Override
    public EstadoPartida agregarJugador(int idPartida, Jugador jugador) {
        EstadoPartida p = partidas.get(idPartida);
        if (p == null || !EstadoPartida.ESPERANDO.equals(p.getEstado())
                || p.estaLlena() || p.buscarPorId(jugador.getId()) != null) {
            return null;
        }
        jugador.setEsAnfitrion(false);
        jugador.setPreparado(false);
        jugador.setColor(colorLibre(p));
        p.agregarJugador(jugador);
        return p;
    }

    @Override
    public EstadoPartida cambiarPreparado(int idPartida, int idJugador, boolean preparado) {
        EstadoPartida p = partidas.get(idPartida);
        if (p == null) return null;
        Jugador j = p.buscarPorId(idJugador);
        if (j != null && !j.isEsAnfitrion()) j.setPreparado(preparado);   
        return p;
    }

    @Override
    public String motivoBloqueoInicio(int idPartida) {
        EstadoPartida p = partidas.get(idPartida);
        if (p == null) return "La partida ya no existe";
        return p.motivoBloqueoInicio();
    }
     @Override
    public boolean puedeIniciar(int idPartida) {
        return motivoBloqueoInicio(idPartida) == null;
    }

    @Override
    public EstadoPartida iniciarPartida(int idPartida) {
        String motivo = motivoBloqueoInicio(idPartida);
        if (motivo != null) throw new IllegalStateException(motivo);
        EstadoPartida p = partidas.get(idPartida);
        p.setEstado(EstadoPartida.EN_CURSO);
        return p;
    }

    @Override
    public void abandonar(int idPartida, int idJugador) {
        EstadoPartida p = partidas.get(idPartida);
        if (p == null) return;
        Jugador j = p.buscarPorId(idJugador);
        if (j == null) return;
        if (j.isEsAnfitrion()) {
            partidas.remove(idPartida);      
        } else {
            p.quitarJugador(j);
        }
    }
}
