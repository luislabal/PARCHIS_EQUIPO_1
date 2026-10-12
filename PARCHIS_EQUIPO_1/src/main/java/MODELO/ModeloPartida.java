package MODELO;

import java.util.ArrayList;
import java.util.List;


public class ModeloPartida extends Sujeto {

    private final IServidorRemoto servidor;
    private Jugador usuarioActual;
    private EstadoPartida estadoPartida;                
    private int idSolicitudPropia = -1;                  
    private boolean uniendose;                           
    private List<Jugador> solicitudesPendientes = new ArrayList<>();   
    private List<EstadoPartida> salas = new ArrayList<>();             

    public ModeloPartida(IServidorRemoto servidor) {
        this.servidor = servidor;
    }

    private void notificar(String tipo, EstadoPartida estado, String mensaje) {
        notificarObservadores(new EventoModelo(tipo, estado, mensaje));
    }
    
    public Jugador getUsuarioActual() { return usuarioActual; }

    public boolean haySesion() { return usuarioActual != null; }

    public boolean estaEnPartida() { return estadoPartida != null; }
    
     public boolean haySolicitudPropiaPendiente() { return idSolicitudPropia != -1 || uniendose; }

    public boolean esAnfitrion() {
        return usuarioActual != null && estadoPartida != null && usuarioActual.isEsAnfitrion();
    }

    public EstadoPartida obtenerEstadoPartida() { return estadoPartida; }

    public List<EstadoPartida> getSalasDisponibles() {
        List<EstadoPartida> r = new ArrayList<>();
        for (EstadoPartida p : salas) {
            if (estadoPartida == null || p.getIdPartida() != estadoPartida.getIdPartida()) r.add(p);
        }
        return r;
    }

    public int getCantidadSolicitudes() { return solicitudesPendientes.size(); }

    public int getCantidadListos() {
        int n = 0;
        if (estadoPartida != null) {
            for (Jugador j : estadoPartida.getJugadores()) {
                if (j.isPreparado()) n++;
            }
        }
        return n;
    }

    public Jugador getSolicitudActual() {
        return solicitudesPendientes.isEmpty() ? null : solicitudesPendientes.get(0);
    }

    public String motivoBloqueoInicio() {
        if (estadoPartida == null) return "No estas en una partida";
        if (!esAnfitrion()) return "Solo el anfitrion puede iniciar la partida";
        return estadoPartida.motivoBloqueoInicio();
    }

   
    public void registrarUsuario(String nombre) {
        if (usuarioActual != null) {
            notificar(EventoModelo.REGISTRO_RECHAZADO, null, "Ya estás registrado como " + usuarioActual.getNombre());
            return;
        }
        String error = Validaciones.nombreUsuario(nombre);
        if (error != null) {
            notificar(EventoModelo.REGISTRO_RECHAZADO, null, error);
            return;
        }
        servidor.enviar(Protocolo.REGISTRAR, nombre.trim());
    }

     public void crearPartida(String nombreSala, String clave, int maxJugadores) {
        String error = null;
        if (usuarioActual == null) error = "Primero debes registrarte";
        else if (estadoPartida != null || idSolicitudPropia != -1) error = "Ya participas en una partida";
        if (error == null) error = Validaciones.nombreSala(nombreSala);
        if (error == null) error = Validaciones.claveSala(clave);
        if (error == null) error = Validaciones.maxJugadores(maxJugadores);
        if (error != null) {
            notificar(EventoModelo.CONFIGURACION_RECHAZADA, null, error);
            return;
        }
        servidor.enviar(Protocolo.CREAR, nombreSala.trim(), clave.trim(), String.valueOf(maxJugadores));
    }

    public void solicitarListaSalas() {
        servidor.enviar(Protocolo.SALAS);
    }

    public void solicitarUnirse(int idSala, String clave) {
        String error = null;
        EstadoPartida sala = null;
        if (usuarioActual == null) {
            error = "Primero debes registrarte";
        } else if (estadoPartida != null || haySolicitudPropiaPendiente()) {
            error = "Ya participas en una partida o tienes una solicitud pendiente";
        } else {
            for (EstadoPartida p : salas) {
                if (p.getIdPartida() == idSala) sala = p;
            }
            if (sala == null) error = "La sala ya no esta disponible";
            else if (sala.estaLlena()) error = "La sala está llena";
        }
        if (error != null) {
            notificar(EventoModelo.UNION_RECHAZADA, null, error);
            return;
        }
        uniendose = true;
        servidor.enviar(Protocolo.UNIRSE, String.valueOf(idSala), clave == null ? "" : clave);
    }

    public void cancelarSolicitud() {
        if (!haySolicitudPropiaPendiente()) return;
        idSolicitudPropia = -1;
        uniendose = false;
        servidor.enviar(Protocolo.CANCELAR_SOLICITUD);
    }

    public void aceptarSolicitud() {
        String error = null;
        if (!esAnfitrion()) error = "Solo el anfitrion puede aceptar solicitudes";
        else if (solicitudesPendientes.isEmpty()) error = "No hay solicitudes pendientes";
        else if (estadoPartida.estaLlena()) error = "La sala está llena";
        if (error != null) {
            notificar(EventoModelo.RESOLUCION_INVALIDA, estadoPartida, error);
            return;
        }
        servidor.enviar(Protocolo.ACEPTAR);
    }

    public void rechazarSolicitud() {
        String error = null;
        if (!esAnfitrion()) error = "Solo el anfitrion puede rechazar solicitudes";
        else if (solicitudesPendientes.isEmpty()) error = "No hay solicitudes pendientes";
        if (error != null) {
            notificar(EventoModelo.RESOLUCION_INVALIDA, estadoPartida, error);
            return;
        }
        servidor.enviar(Protocolo.RECHAZAR);
    }


    public void cambiarListo(boolean listo) {
        if (usuarioActual == null || estadoPartida == null) return;
        if (usuarioActual.isEsAnfitrion()) return;
        if (!EstadoPartida.ESPERANDO.equals(estadoPartida.getEstado())) return;
        servidor.enviar(Protocolo.LISTO, String.valueOf(listo));
    }

 
    public void iniciarPartida(int idPartida) {
        if (usuarioActual == null || estadoPartida == null || estadoPartida.getIdPartida() != idPartida) {
            notificar(EventoModelo.INICIO_RECHAZADO, estadoPartida, "No perteneces a esa partida");
            return;
        }
        if (!usuarioActual.isEsAnfitrion()) {
            notificar(EventoModelo.INICIO_RECHAZADO, estadoPartida, "Solo el anfitrion puede iniciar la partida");
            return;
        }
        String motivo = estadoPartida.motivoBloqueoInicio();
        if (motivo != null) {
            notificar(EventoModelo.INICIO_RECHAZADO, estadoPartida, motivo);
            return;
        }
        servidor.enviar(Protocolo.INICIAR, String.valueOf(idPartida));
    }
public void abandonarPartida() {
        if (usuarioActual == null || (estadoPartida == null && !haySolicitudPropiaPendiente())) return;
        if (estadoPartida == null) {          
            cancelarSolicitud();
            limpiarPartida();
            notificar(EventoModelo.PARTIDA_ABANDONADA, null, "Saliste de la partida");
            return;
        }
        servidor.enviar(Protocolo.ABANDONAR);  
    }
    
    public void procesarLinea(String linea) {
        try {
            List<String> c = Campos.desempaquetar(linea);
            if (c.isEmpty()) return;
            procesarMensaje(c.get(0), c.subList(1, c.size()));
        } catch (RuntimeException ex) {
            
        }
    }

    private static String campo(List<String> c, int i) {
        return i < c.size() ? c.get(i) : "";
    }

    private void procesarMensaje(String tipo, List<String> c) {
        switch (tipo) {
            case EventoModelo.USUARIO_REGISTRADO:
                usuarioActual = CodecModelo.decodificarJugador(campo(c, 0));
                notificar(tipo, null, usuarioActual.getNombre());
                break;
            case EventoModelo.REGISTRO_RECHAZADO:
            case EventoModelo.CONFIGURACION_RECHAZADA:
                notificar(tipo, null, campo(c, 0));
                break;
            case EventoModelo.PARTIDA_CREADA:
                aplicarEstado(CodecModelo.decodificarEstado(campo(c, 0)));
                solicitudesPendientes = new ArrayList<>();
                notificar(tipo, estadoPartida, campo(c, 1));
                break;
            case EventoModelo.SALAS_ACTUALIZADAS:
                salas = new ArrayList<>();
                for (String s : c) {
                    EstadoPartida e = CodecModelo.decodificarEstado(s);
                    if (e != null) salas.add(e);
                }
                notificar(tipo, null, "");
                break;
            case EventoModelo.SOLICITUD_ENVIADA: {
                if (!uniendose) break;        
                EstadoPartida sala = CodecModelo.decodificarEstado(campo(c, 0));
                idSolicitudPropia = sala.getIdPartida();
                notificar(tipo, sala, campo(c, 1));
                break;
            }
            case EventoModelo.UNION_ACEPTADA:
                if (!uniendose) {              
                    servidor.enviar(Protocolo.ABANDONAR);
                    break;
                }
                idSolicitudPropia = -1;
                uniendose = false;
                aplicarEstado(CodecModelo.decodificarEstado(campo(c, 0)));
                solicitudesPendientes = new ArrayList<>();
                notificar(tipo, estadoPartida, campo(c, 1));
                break;
            case EventoModelo.UNION_RECHAZADA:
                idSolicitudPropia = -1;
                uniendose = false;
                notificar(tipo, null, campo(c, 0));
                break;
            case EventoModelo.SOLICITUD_RECIBIDA:
            case EventoModelo.SOLICITUD_RESUELTA:
            case EventoModelo.JUGADOR_UNIDO:
                aplicarEstado(CodecModelo.decodificarEstado(campo(c, 0)));
                solicitudesPendientes = new ArrayList<>(CodecModelo.decodificarJugadores(campo(c, 1)));
                notificar(tipo, estadoPartida, campo(c, 2));
                break;
            case EventoModelo.RESOLUCION_INVALIDA:
            case EventoModelo.ESTADO_ACTUALIZADO:
            case EventoModelo.INICIO_RECHAZADO:
                aplicarEstado(CodecModelo.decodificarEstado(campo(c, 0)));
                notificar(tipo, estadoPartida, campo(c, 1));
                break;
            case EventoModelo.PARTIDA_INICIADA:
                aplicarEstado(CodecModelo.decodificarEstado(campo(c, 0)));
                solicitudesPendientes = new ArrayList<>();
                notificar(tipo, estadoPartida, campo(c, 1));
                break;
            case EventoModelo.PARTIDA_ABANDONADA:
            case EventoModelo.PARTIDA_CERRADA:
                limpiarPartida();
                notificar(tipo, null, campo(c, 0));
                break;
            case EventoModelo.CONEXION_PERDIDA:
                notificar(tipo, null, campo(c, 0));
                break;
            default:
                break;
        }
    }

    private void aplicarEstado(EstadoPartida e) {
        if (e == null) return;
        estadoPartida = e;
        if (usuarioActual != null) {
            Jugador yo = e.buscarPorId(usuarioActual.getId());
            if (yo != null) {
                usuarioActual.setEsAnfitrion(yo.isEsAnfitrion());
                usuarioActual.setPreparado(yo.isPreparado());
                usuarioActual.setColor(yo.getColor());
            }
        }
    }

    private void limpiarPartida() {
        estadoPartida = null;
        idSolicitudPropia = -1;
        uniendose = false;
        solicitudesPendientes = new ArrayList<>();
        if (usuarioActual != null) {
            usuarioActual.setEsAnfitrion(false);
            usuarioActual.setPreparado(false);
            usuarioActual.setColor(null);
        }
    }    
}
