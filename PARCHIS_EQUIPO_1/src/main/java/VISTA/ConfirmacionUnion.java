
package VISTA;

import CONTROLADOR.Aplicacion;
import CONTROLADOR.ControladorPartida;
import MODELO.EstadoPartida;
import MODELO.EventoModelo;
import MODELO.IObservador;
import MODELO.Jugador;
import MODELO.ModeloPartida;


public class ConfirmacionUnion extends javax.swing.JFrame implements IObservador {

    private final ModeloPartida modelo;
    private final ControladorPartida controlador;


    public ConfirmacionUnion() {
        this(Aplicacion.getModelo(), Aplicacion.getControladorPartida());
    }

    public ConfirmacionUnion(ModeloPartida modelo, ControladorPartida controlador) {
        initComponents();
        this.modelo = modelo;
        this.controlador = controlador;
        setTitle("Solicitud para unirse");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        btnAceptar.addActionListener(e -> this.controlador.aceptarSolicitud());
        btnRechazar.addActionListener(e -> this.controlador.rechazarSolicitud());
        refrescar();
        modelo.agregarObservador(this);
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        btnRechazar = new javax.swing.JButton();
        lblNombreJugador = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnAceptar.setText("Aceptar");

        btnRechazar.setText("Rechazar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(btnAceptar)
                .addGap(56, 56, 56)
                .addComponent(btnRechazar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addComponent(lblNombreJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addComponent(lblNombreJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnRechazar))
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    private void refrescar() {
        Jugador solicitante = modelo.getSolicitudActual();
        EstadoPartida sala = modelo.obtenerEstadoPartida();
        if (solicitante == null || sala == null) {
            dispose();
            return;
        }
        int restantes = modelo.getCantidadSolicitudes() - 1;
        lblNombreJugador.setText(solicitante.getNombre() + " quiere unirse a tu partida"
                + (restantes > 0 ? "  (+" + restantes + " mas)" : ""));
        boolean puede = modelo.esAnfitrion() && !sala.estaLlena();
        btnAceptar.setEnabled(puede);
        btnAceptar.setToolTipText(puede ? "Aceptar a " + solicitante.getNombre() : "La sala esta llena");
        btnRechazar.setEnabled(modelo.esAnfitrion());
    }

    @Override
    public void actualizar(EventoModelo evento) {
        switch (evento.getTipo()) {
            case EventoModelo.SOLICITUD_RECIBIDA:
            case EventoModelo.SOLICITUD_RESUELTA:
            case EventoModelo.JUGADOR_UNIDO:
                refrescar();
                break;
            case EventoModelo.RESOLUCION_INVALIDA:
                javax.swing.JOptionPane.showMessageDialog(this, evento.getMensaje(),
                        "Solicitud no valida", javax.swing.JOptionPane.WARNING_MESSAGE);
                refrescar();
                break;
            case EventoModelo.PARTIDA_INICIADA:
            case EventoModelo.PARTIDA_ABANDONADA:
            case EventoModelo.PARTIDA_CERRADA:
                dispose();
                break;
            default:
                break;
        }
    }

    @Override
    public void dispose() {
        if (modelo != null) {
            modelo.quitarObservador(this);
        }
        super.dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnRechazar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblNombreJugador;
    // End of variables declaration//GEN-END:variables
}
