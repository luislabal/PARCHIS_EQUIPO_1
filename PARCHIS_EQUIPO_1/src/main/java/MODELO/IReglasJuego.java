package MODELO;

import java.util.List;


public interface IReglasJuego {

  
    boolean puedeIniciar(int idPartida);

    EstadoPartida iniciarPartida(int idPartida);

  String motivoBloqueoInicio(int idPartida);

    boolean nombreDisponible(String nombre);

    Jugador registrarJugador(String nombre);

    void liberarNombre(String nombre);

    
    EstadoPartida crearPartida(String nombre, String clave, int maxJugadores, Jugador anfitrion);

    List<EstadoPartida> listarSalasDisponibles();

    boolean validarClave(int idPartida, String clave);

}
