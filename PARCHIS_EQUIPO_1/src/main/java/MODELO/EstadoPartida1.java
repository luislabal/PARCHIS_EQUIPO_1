package MODELO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EstadoPartida1 {

    public static final String ESPERANDO = "ESPERANDO";
    public static final String EN_CURSO = "EN_CURSO";

    private final int idPartida;
    private final String nombre;
    private final String clave;
    private final int maxJugadores;
    private String estado = ESPERANDO;
    private final List<Jugador> jugadores = new ArrayList<>();

    public EstadoPartida1(int idPartida, String nombre, String clave, int maxJugadores) {
        this.idPartida = idPartida;
        this.nombre = nombre;
        this.clave = clave;
        this.maxJugadores = maxJugadores;
    }

    public int getIdPartida() { return idPartida; }
    public String getNombre() { return nombre; }
    public int getMaxJugadores() { return maxJugadores; }
    public String getEstado() { return estado; }

    public List<Jugador> getJugadores() {
        return Collections.unmodifiableList(jugadores);
    }

    public boolean claveCoincide(String intento) {
        return clave != null && clave.equals(intento);
    }

    public boolean estaLlena() {
        return jugadores.size() >= maxJugadores;
    }

    public Jugador buscarPorId(int id) {
        for (Jugador j : jugadores) {
            if (j.getId() == id) return j;
        }
        return null;
    }

    public Jugador buscarPorNombre(String nombre) {
        for (Jugador j : jugadores) {
            if (j.getNombre().equalsIgnoreCase(nombre)) return j;
        }
        return null;
    }

 
    public String motivoBloqueoInicio() {
        if (!ESPERANDO.equals(estado)) return "La partida ya empezo";
        int n = jugadores.size();
        if (n < 2) return "Se necesitan al menos 2 jugadores (hay " + n + ")";
        for (Jugador j : jugadores) {
            if (!j.isPreparado()) return "Esperando a que " + j.getNombre() + " este listo";
        }
        return null;
    }

    public static EstadoPartida1 reconstruir(int id, String nombre, int max, String estado, java.util.List<Jugador> js) {
        EstadoPartida1 p = new EstadoPartida1(id, nombre, null, max);
        p.estado = estado;
        p.jugadores.addAll(js);
        return p;
    }

    void setEstado(String estado) { this.estado = estado; }
    void agregarJugador(Jugador j) { jugadores.add(j); }
    boolean quitarJugador(Jugador j) { return jugadores.remove(j); }
}
