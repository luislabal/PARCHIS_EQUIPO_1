package MODELO;

import java.util.List;


public interface IReglasJuego {

  
    boolean puedeIniciar(int idPartida);

    EstadoPartida iniciarPartida(int idPartida);

  
   
}
