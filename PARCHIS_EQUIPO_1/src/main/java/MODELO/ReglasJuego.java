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

    
}
