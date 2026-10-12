package CONTROLADOR;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CambioTexto implements DocumentListener {

    private final Runnable accion;

    public CambioTexto(Runnable accion) {
        this.accion = accion;
    }

    @Override public void insertUpdate(DocumentEvent e) { accion.run(); }
    @Override public void removeUpdate(DocumentEvent e) { accion.run(); }
    @Override public void changedUpdate(DocumentEvent e) { accion.run(); }
}
