package MODELO;

import java.util.ArrayList;
import java.util.List;


public abstract class Sujeto {

    private final List<IObservador> observadores = new ArrayList<>();

    public void agregarObservador(IObservador o) {
        if (o != null && !observadores.contains(o)) {
            observadores.add(o);
        }
    }

    public void quitarObservador(IObservador o) {
        observadores.remove(o);
    }

    public void notificarObservadores(EventoModelo evento) {
       
        for (IObservador o : new ArrayList<>(observadores)) {
            if (observadores.contains(o)) {
                o.actualizar(evento);
            }
        }
    }
}
