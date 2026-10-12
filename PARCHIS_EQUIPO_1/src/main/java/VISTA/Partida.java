
package VISTA;

import CONTROLADOR.Aplicacion;
import CONTROLADOR.ControladorPartida;
import MODELO.EstadoPartida;
import MODELO.EventoModelo;
import MODELO.IObservador;
import MODELO.Jugador;
import MODELO.ModeloPartida;


public class Partida extends javax.swing.JFrame implements IObservador {

    private final ModeloPartida modelo;
    private final ControladorPartida controlador;
    private ConfirmacionUnion ventanaSolicitud;


    public Partida() {
        this(Aplicacion.getModelo(), Aplicacion.getControladorPartida());
    }

    public Partida(ModeloPartida modelo, ControladorPartida controlador) {
        initComponents();
        this.modelo = modelo;
        this.controlador = controlador;
        setLocationRelativeTo(null);
        txtAreaListaJugadores.setEditable(false);
        btnEmpezar.addActionListener(e -> empezarPartida());
        btnListo.addActionListener(e -> cambiarListo());
        btnSolicitudes.addActionListener(e -> abrirSolicitudes());
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (Partida.this.modelo.estaEnPartida()) {
                    Partida.this.controlador.abandonarPartida();
                }
            }
        });
        refrescar();
        modelo.agregarObservador(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaListaJugadores = new javax.swing.JTextArea();
        btnEmpezar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnListo = new javax.swing.JButton();
        btnSolicitudes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Esperando jugadores ............");

        txtAreaListaJugadores.setColumns(20);
        txtAreaListaJugadores.setRows(5);
        jScrollPane1.setViewportView(txtAreaListaJugadores);

        btnEmpezar.setText("Empezar Partida");
        btnEmpezar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpezarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnListo.setText("Listo");
        btnListo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListoActionPerformed(evt);
            }
        });

        btnSolicitudes.setText("Solicitudes");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(btnListo)
                .addGap(82, 82, 82)
                .addComponent(btnEmpezar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addGap(64, 64, 64))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSolicitudes)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(142, 142, 142))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(22, 22, 22)))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnSolicitudes)
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnListo)
                    .addComponent(btnEmpezar)
                    .addComponent(btnCancelar))
                .addGap(26, 26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        if (modelo.estaEnPartida()) {
            controlador.abandonarPartida();         
        } else {
            new Interfaz().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnListoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnListoActionPerformed

    private void btnEmpezarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpezarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEmpezarActionPerformed


    private void empezarPartida() {
        EstadoPartida e = modelo.obtenerEstadoPartida();
        if (e != null) {
            controlador.iniciarPartida(e.getIdPartida());
        }
    }


    private void cambiarListo() {
        Jugador yo = modelo.getUsuarioActual();
        boolean estabaListo = yo != null && yo.isPreparado();
        controlador.cambiarListo(!estabaListo);
    }

    private void abrirSolicitudes() {
        if (ventanaSolicitud != null && ventanaSolicitud.isDisplayable()) {
            ventanaSolicitud.toFront();
            return;
        }
        ventanaSolicitud = new ConfirmacionUnion();
        ventanaSolicitud.setLocationRelativeTo(this);
        ventanaSolicitud.setVisible(true);
    }

    private void refrescar() {
        EstadoPartida e = modelo.obtenerEstadoPartida();
        if (e == null) {
            return;
        }
        boolean anfitrion = modelo.esAnfitrion();
        setTitle("Sala: " + e.getNombre());
        String cupo = " (" + e.getJugadores().size() + "/" + e.getMaxJugadores() + ")"
                + "  -  Listos: " + modelo.getCantidadListos() + "/" + e.getJugadores().size();
        jLabel1.setText(anfitrion ? "Esperando jugadores" + cupo
                                  : "Esperando al anfitrion" + cupo);

        StringBuilder sb = new StringBuilder();
        for (Jugador j : e.getJugadores()) {
            sb.append("- ").append(j.getNombre()).append("  [").append(j.getColor()).append("]")
              .append(j.isEsAnfitrion() ? "  (anfitrion)" : "")
              .append(j.isPreparado() ? "  - LISTO" : "  - no esta listo").append("\n");
        }
        txtAreaListaJugadores.setText(sb.toString());
        // Listo
        Jugador yo = modelo.getUsuarioActual();
        boolean yoListo = yo != null && yo.isPreparado();
        btnListo.setEnabled(!anfitrion);
        btnListo.setText(!anfitrion && yoListo ? "No estoy listo" : "Listo");
        btnListo.setToolTipText(anfitrion ? "El anfitrion siempre esta listo"
                : yoListo ? "Avisa que no estas listo" : "Avisa que estas listo para jugar");
        // Empezar
        String motivo = modelo.motivoBloqueoInicio();
        btnEmpezar.setEnabled(motivo == null);
        btnEmpezar.setToolTipText(motivo == null ? "Iniciar la partida" : motivo);

        // Solicitudes
        int n = modelo.getCantidadSolicitudes();
        btnSolicitudes.setText("Solicitudes (" + n + ")");
        btnSolicitudes.setEnabled(anfitrion && n > 0);
        btnSolicitudes.setToolTipText(!anfitrion ? "Solo el anfitrion atiende solicitudes"
                : n == 0 ? "No hay solicitudes pendientes" : "Aceptar o rechazar jugadores");
    }

    @Override
    public void actualizar(EventoModelo evento) {
        switch (evento.getTipo()) {
            case EventoModelo.SOLICITUD_RECIBIDA:
            case EventoModelo.SOLICITUD_RESUELTA:
            case EventoModelo.JUGADOR_UNIDO:
            case EventoModelo.ESTADO_ACTUALIZADO:
                refrescar();
                break;
            case EventoModelo.INICIO_RECHAZADO:
                javax.swing.JOptionPane.showMessageDialog(this, evento.getMensaje(),"No se puede iniciar la partida", javax.swing.JOptionPane.WARNING_MESSAGE);
                refrescar();
                break;
            case EventoModelo.PARTIDA_INICIADA:
                new Parchis().setVisible(true);
                dispose();
                break;
            case EventoModelo.PARTIDA_ABANDONADA:
                new Interfaz().setVisible(true);
                dispose();
                break;
            case EventoModelo.PARTIDA_CERRADA:
                javax.swing.JOptionPane.showMessageDialog(this, evento.getMensaje(),"Sala cerrada", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                new Interfaz().setVisible(true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEmpezar;
    private javax.swing.JButton btnListo;
    private javax.swing.JButton btnSolicitudes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtAreaListaJugadores;
    // End of variables declaration//GEN-END:variables
}
