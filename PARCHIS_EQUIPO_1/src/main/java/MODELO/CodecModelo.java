package MODELO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CodecModelo {

    private CodecModelo() { }

    public static String codificar(Jugador j) {
        if (j == null) return "";
        return Campos.empaquetar(String.valueOf(j.getId()), j.getNombre(), j.getAvatar(), j.getColor(),
                String.valueOf(j.isEsAnfitrion()), String.valueOf(j.isPreparado()));
    }

    public static Jugador decodificarJugador(String texto) {
        List<String> c = Campos.desempaquetar(texto);
        if (c.size() < 6) return null;
        Jugador j = new Jugador(Integer.parseInt(c.get(0)), c.get(1), c.get(2));
        j.setColor(c.get(3).isEmpty() ? null : c.get(3));
        j.setEsAnfitrion(Boolean.parseBoolean(c.get(4)));
        j.setPreparado(Boolean.parseBoolean(c.get(5)));
        return j;
    }

    public static String codificarJugadores(Collection<Jugador> jugadores) {
        List<String> l = new ArrayList<>();
        for (Jugador j : jugadores) l.add(codificar(j));
        return Campos.empaquetar(l);
    }

    public static List<Jugador> decodificarJugadores(String texto) {
        List<Jugador> r = new ArrayList<>();
        for (String s : Campos.desempaquetar(texto)) {
            Jugador j = decodificarJugador(s);
            if (j != null) r.add(j);
        }
        return r;
    }

    public static String codificar(EstadoPartida e) {
        if (e == null) return "";
        return Campos.empaquetar(String.valueOf(e.getIdPartida()), e.getNombre(),
                String.valueOf(e.getMaxJugadores()), e.getEstado(), codificarJugadores(e.getJugadores()));
    }
    
    public static EstadoPartida decodificarEstado(String texto) {
        List<String> c = Campos.desempaquetar(texto);
        if (c.size() < 5) return null;
        return EstadoPartida.reconstruir(Integer.parseInt(c.get(0)), c.get(1), Integer.parseInt(c.get(2)),
                c.get(3), decodificarJugadores(c.get(4)));
    }
}
