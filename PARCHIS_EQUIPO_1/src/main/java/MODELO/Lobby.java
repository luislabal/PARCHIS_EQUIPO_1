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

     synchronized boolean conectar(SesionCliente s) {
        if (sesiones.size() >= MAX_CONEXIONES) {
            log("Rechazado " + s.direccion() + ": servidor lleno");
            return false;
        }
        sesiones.add(s);
        log("Conectado " + s.direccion() + "  (" + sesiones.size() + " conectados)");
        enviarSalas(s);
        return true;
    }

    synchronized void desconectar(SesionCliente s) {
        if (!sesiones.remove(s)) return;
        abandonar(s, false);
        if (s.jugador != null) {
            reglas.liberarNombre(s.jugador.getNombre());
            porJugador.remove(s.jugador.getId());
            log("Se fue " + s.jugador.getNombre());
        }
        log("Desconectado " + s.direccion() + "  (" + sesiones.size() + " conectados)");
    }

    synchronized void registrar(SesionCliente s, String nombre) {
        if (s.jugador != null) {
            s.enviar(EventoModelo.REGISTRO_RECHAZADO, "Ya estas registrado como " + s.jugador.getNombre());
            return;
        }
        String error = Validaciones.nombreUsuario(nombre);
        if (error == null && !reglas.nombreDisponible(nombre)) {
            error = "El nombre \"" + nombre.trim() + "\" ya esta en uso";
        }
        if (error != null) {
            s.enviar(EventoModelo.REGISTRO_RECHAZADO, error);
            return;
        }
        s.jugador = reglas.registrarJugador(nombre);
        porJugador.put(s.jugador.getId(), s);
        log("Registrado " + s.jugador.getNombre());
        s.enviar(EventoModelo.USUARIO_REGISTRADO, CodecModelo.codificar(s.jugador));
    }
    
    synchronized void crear(SesionCliente s, String nombreSala, String clave, int max) {
        String error = null;
        if (s.jugador == null) error = "Primero debes registrarte";
        else if (s.sala != null || s.solicitudSala != -1) error = "Ya participas en una partida";
        if (error == null) error = Validaciones.nombreSala(nombreSala);
        if (error == null) error = Validaciones.claveSala(clave);
        if (error == null) error = Validaciones.maxJugadores(max);
        if (error != null) {
            s.enviar(EventoModelo.CONFIGURACION_RECHAZADA, error);
            return;
        }
        s.sala = reglas.crearPartida(nombreSala, clave.trim(), max, s.jugador);
        colas.put(s.sala.getIdPartida(), new ArrayDeque<>());
        log(s.jugador.getNombre() + " creo la sala \"" + s.sala.getNombre() + "\" (" + max + " jugadores)");
        s.enviar(EventoModelo.PARTIDA_CREADA, CodecModelo.codificar(s.sala), "Partida creada");
        difundirSalas();
    }

    synchronized void unirse(SesionCliente s, int idSala, String clave) {
        String error = null;
        EstadoPartida sala = null;
        SesionCliente anfitrion = null;
        if (s.jugador == null) {
            error = "Primero debes registrarte";
        } else if (s.sala != null || s.solicitudSala != -1) {
            error = "Ya participas en una partida o tienes una solicitud pendiente";
        } else {
            for (EstadoPartida p : reglas.listarSalasDisponibles()) {
                if (p.getIdPartida() == idSala) sala = p;
            }
            if (sala == null) error = "La sala ya no está disponible";
            else if (sala.estaLlena()) error = "La sala está llena";
            else if (!reglas.validarClave(idSala, clave)) error = "Clave incorrecta";
            else {
                anfitrion = anfitrionDe(sala);
                if (anfitrion == null) error = "La sala ya no está disponible";
            }
        }
        if (error != null) {
            s.enviar(EventoModelo.UNION_RECHAZADA, error);
            return;
        }
        Deque<SesionCliente> cola = colas.computeIfAbsent(idSala, k -> new ArrayDeque<>());
        cola.addLast(s);
        s.solicitudSala = idSala;
        log(s.jugador.getNombre() + " pide unirse a \"" + sala.getNombre() + "\"");
        s.enviar(EventoModelo.SOLICITUD_ENVIADA, CodecModelo.codificar(sala),
                "Solicitud enviada al anfitrión de \"" + sala.getNombre() + "\"");
        anfitrion.enviar(EventoModelo.SOLICITUD_RECIBIDA, CodecModelo.codificar(sala), textoCola(idSala),
                s.jugador.getNombre());
    }

    synchronized void cancelarSolicitud(SesionCliente s) {
        if (s.solicitudSala == -1) return;
        int id = s.solicitudSala;
        s.solicitudSala = -1;
        Deque<SesionCliente> cola = colas.get(id);
        if (cola != null && cola.remove(s)) {
            EstadoPartida sala = salaPorId(id);
            SesionCliente anf = sala == null ? null : anfitrionDe(sala);
            if (anf != null) {
                anf.enviar(EventoModelo.SOLICITUD_RESUELTA, CodecModelo.codificar(sala), textoCola(id),
                        s.jugador.getNombre() + " canceló su solicitud");
            }
        }
    }
}
