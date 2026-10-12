package MODELO;


public class EventoModelo {

    // CU Registrarse
    public static final String USUARIO_REGISTRADO = "USUARIO_REGISTRADO";
    public static final String REGISTRO_RECHAZADO = "REGISTRO_RECHAZADO";
    // CU Configurar partida
    public static final String PARTIDA_CREADA = "PARTIDA_CREADA";
    public static final String CONFIGURACION_RECHAZADA = "CONFIGURACION_RECHAZADA";
    // CU Solicitar unirse
    public static final String SOLICITUD_ENVIADA = "SOLICITUD_ENVIADA";
    public static final String UNION_ACEPTADA = "UNION_ACEPTADA";
    public static final String UNION_RECHAZADA = "UNION_RECHAZADA";
    // Sala de espera 
    public static final String SOLICITUD_RECIBIDA = "SOLICITUD_RECIBIDA";
    public static final String SOLICITUD_RESUELTA = "SOLICITUD_RESUELTA";
    public static final String RESOLUCION_INVALIDA = "RESOLUCION_INVALIDA";
    public static final String JUGADOR_UNIDO = "JUGADOR_UNIDO";
    public static final String ESTADO_ACTUALIZADO = "ESTADO_ACTUALIZADO";
    // CU Iniciar partida 
    public static final String PARTIDA_INICIADA = "PARTIDA_INICIADA";
    public static final String INICIO_RECHAZADO = "INICIO_RECHAZADO";
    // Salir
    public static final String PARTIDA_ABANDONADA = "PARTIDA_ABANDONADA";
    public static final String PARTIDA_CERRADA = "PARTIDA_CERRADA";      
    
    public EventoModelo(String tipo, EstadoPartida estado, String mensaje) {
        this.tipo = tipo;
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public String getTipo() {
        return tipo;
    }
}
