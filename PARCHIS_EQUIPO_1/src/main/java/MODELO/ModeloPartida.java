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
    
    
}
