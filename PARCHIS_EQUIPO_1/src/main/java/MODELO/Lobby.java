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

    synchronized void aceptar(SesionCliente s) {
        EstadoPartida sala = s.sala;
        String error = null;
        Deque<SesionCliente> cola = sala == null ? null : colas.get(sala.getIdPartida());
        if (!esAnfitrion(s)) error = "Solo el anfitrión puede aceptar solicitudes";
        else if (cola == null || cola.isEmpty()) error = "No hay solicitudes pendientes";
        else if (sala.estaLlena()) error = "La sala está llena";
        if (error != null) {
            s.enviar(EventoModelo.RESOLUCION_INVALIDA, CodecModelo.codificar(sala), error);
            return;
        }
        SesionCliente nuevo = cola.pollFirst();
        nuevo.solicitudSala = -1;
        EstadoPartida e = reglas.agregarJugador(sala.getIdPartida(), nuevo.jugador);
        if (e == null) {
            nuevo.enviar(EventoModelo.UNION_RECHAZADA, "La sala ya no está disponible");
            s.enviar(EventoModelo.RESOLUCION_INVALIDA, CodecModelo.codificar(sala),
                    "No fue posible agregar a " + nuevo.jugador.getNombre());
            return;
        }
        nuevo.sala = e;
        log(nuevo.jugador.getNombre() + " entró a \"" + e.getNombre() + "\" (" + e.getJugadores().size() + "/" + e.getMaxJugadores() + ")");
        String estado = CodecModelo.codificar(e);
        nuevo.enviar(EventoModelo.UNION_ACEPTADA, estado, "Te aceptaron en \"" + e.getNombre() + "\"");
        s.enviar(EventoModelo.JUGADOR_UNIDO, estado, textoCola(e.getIdPartida()), nuevo.jugador.getNombre());
        for (SesionCliente m : miembros(e)) {
            if (m != s && m != nuevo) {
                m.enviar(EventoModelo.ESTADO_ACTUALIZADO, estado, nuevo.jugador.getNombre() + " se unió");
            }
        }
        if (e.estaLlena() && !cola.isEmpty()) {
            for (SesionCliente r : cola) {
                r.solicitudSala = -1;
                r.enviar(EventoModelo.UNION_RECHAZADA, "La sala se llenó");
            }
            cola.clear();
            s.enviar(EventoModelo.SOLICITUD_RESUELTA, estado, textoCola(e.getIdPartida()),
                    "La sala se llenó: se descartaron las solicitudes restantes");
        }
        difundirSalas();
    }

    synchronized void rechazar(SesionCliente s) {
        EstadoPartida sala = s.sala;
        String error = null;
        Deque<SesionCliente> cola = sala == null ? null : colas.get(sala.getIdPartida());
        if (!esAnfitrion(s)) error = "Solo el anfitrión puede rechazar solicitudes";
        else if (cola == null || cola.isEmpty()) error = "No hay solicitudes pendientes";
        if (error != null) {
            s.enviar(EventoModelo.RESOLUCION_INVALIDA, CodecModelo.codificar(sala), error);
            return;
        }
        SesionCliente r = cola.pollFirst();
        r.solicitudSala = -1;
        r.enviar(EventoModelo.UNION_RECHAZADA, "El anfitrión rechazó tu solicitud");
        s.enviar(EventoModelo.SOLICITUD_RESUELTA, CodecModelo.codificar(sala), textoCola(sala.getIdPartida()),
                "Rechazaste a " + r.jugador.getNombre());
    }

    synchronized void listo(SesionCliente s, boolean listo) {
        EstadoPartida sala = s.sala;
        if (s.jugador == null || sala == null) return;
        String error = null;
        if (!EstadoPartida.ESPERANDO.equals(sala.getEstado())) error = "La partida ya inició";
        else if (esAnfitrion(s)) error = "El anfitrión siempre está listo";
        if (error != null) {
            s.enviar(EventoModelo.ESTADO_ACTUALIZADO, CodecModelo.codificar(sala), error);
            return;
        }
        if (s.jugador.isPreparado() == listo) return;                       
        reglas.cambiarPreparado(sala.getIdPartida(), s.jugador.getId(), listo);
        log(s.jugador.getNombre() + (listo ? " está listo" : " ya no está listo") + " en \"" + sala.getNombre() + "\"");
        String estado = CodecModelo.codificar(sala);
        String msg = s.jugador.getNombre() + (listo ? " está listo" : " ya no está listo");
        for (SesionCliente m : miembros(sala)) {
            m.enviar(EventoModelo.ESTADO_ACTUALIZADO, estado, msg);
        }
    }
}
