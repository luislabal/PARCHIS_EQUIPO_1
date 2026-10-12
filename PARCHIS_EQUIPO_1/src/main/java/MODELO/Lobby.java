package MODELO;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Lobby {

    private static final String SALISTE = "Saliste de la partida";
    private static final int MAX_CONEXIONES = 200;

    private final IReglasJuego reglas;
    private final Set<SesionCliente> sesiones = new LinkedHashSet<>();
    private final Map<Integer, SesionCliente> porJugador = new HashMap<>();      
    private final Map<Integer, Deque<SesionCliente>> colas = new HashMap<>();    

    public Lobby(IReglasJuego reglas) {
        this.reglas = reglas;
    }

    private static void log(String s) {
        System.out.println("[" + java.time.LocalTime.now().withNano(0) + "] " + s);
    }

   
}
