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

   
}
