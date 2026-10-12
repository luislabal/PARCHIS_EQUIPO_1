
package VISTA;

import CONTROLADOR.Aplicacion;
import CONTROLADOR.ControladorUnirse;
import MODELO.EstadoPartida;
import MODELO.EventoModelo;
import MODELO.IObservador;
import MODELO.ModeloPartida;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;


public class UnirseSalas extends javax.swing.JFrame implements IObservador {

    private final ModeloPartida modelo;
    private final ControladorUnirse controlador;
    private final DefaultListModel<String> modeloLista = new DefaultListModel<>();
    private List<EstadoPartida> salas = new ArrayList<>();
    private boolean esperandoRespuesta = false;

    public UnirseSalas() {
        this(Aplicacion.getModelo(), Aplicacion.getControladorUnirse());
    }

    public UnirseSalas(ModeloPartida modelo, ControladorUnirse controlador) {
        initComponents();
        this.modelo = modelo;
        this.controlador = controlador;
        setTitle("Unirse a una sala");
        setLocationRelativeTo(null);
        ListaSalas.setModel(modeloLista);
        ListaSalas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ListaSalas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) actualizarBoton();
        });
        cargarSalas();
        actualizarBoton();
        modelo.agregarObservador(this);
        controlador.refrescarSalas();    
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListaSalas = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel1.setText("Salas disponibles");

        jScrollPane1.setViewportView(ListaSalas);

        jButton1.setText("Solicitar Unirse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar))
                .addContainerGap(83, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(156, 156, 156))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addContainerGap(16, Short.MAX_VALUE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int i = ListaSalas.getSelectedIndex();
        if (i < 0 || i >= salas.size()) {
            return;
        }
        EstadoPartida sala = salas.get(i);
        javax.swing.JPasswordField campoClave = new javax.swing.JPasswordField();
        int r = javax.swing.JOptionPane.showConfirmDialog(this,
                new Object[]{"Clave de \"" + sala.getNombre() + "\":", campoClave},
                "Unirse a la sala", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
        if (r == javax.swing.JOptionPane.OK_OPTION) {
            controlador.solicitarUnirse(sala.getIdPartida(), new String(campoClave.getPassword()));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
       controlador.cancelarSolicitud();
       Interfaz jd = new Interfaz();
       jd.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed



    private void cargarSalas() {
        salas = modelo.getSalasDisponibles();
        modeloLista.clear();
        for (EstadoPartida p : salas) {
            modeloLista.addElement(p.getNombre() + "   (" + p.getJugadores().size() + "/" + p.getMaxJugadores()
                    + " jugadores)" + (p.estaLlena() ? "   - LLENA" : ""));
        }
        setTitle(salas.isEmpty() ? "Unirse a una sala - no hay salas disponibles" : "Unirse a una sala");
    }


    private void actualizarBoton() {
        int i = ListaSalas.getSelectedIndex();
        String motivo;
        if (!modelo.haySesion()) motivo = "Primero debes registrarte";
        else if (esperandoRespuesta) motivo = "Esperando respuesta del anfitrion...";
        else if (i < 0 || i >= salas.size()) motivo = "Selecciona una sala de la lista";
        else if (salas.get(i).estaLlena()) motivo = "La sala seleccionada esta llena";
        else motivo = null;
        jButton1.setEnabled(motivo == null);
        jButton1.setToolTipText(motivo == null ? "Enviar solicitud al anfitrion" : motivo);
    }

    private void recargarSalasConservandoSeleccion() {
        int idSeleccionado = -1;
        int i = ListaSalas.getSelectedIndex();
        if (i >= 0 && i < salas.size()) idSeleccionado = salas.get(i).getIdPartida();
        cargarSalas();
        for (int k = 0; k < salas.size(); k++) {
            if (salas.get(k).getIdPartida() == idSeleccionado) ListaSalas.setSelectedIndex(k);
        }
        actualizarBoton();
    }

    @Override
    public void actualizar(EventoModelo evento) {
        switch (evento.getTipo()) {
            case EventoModelo.SALAS_ACTUALIZADAS:
                if (!esperandoRespuesta) recargarSalasConservandoSeleccion();
                break;
            case EventoModelo.SOLICITUD_ENVIADA:
                esperandoRespuesta = true;
                ListaSalas.setEnabled(false);
                setTitle("Esperando respuesta del anfitrion...");
                actualizarBoton();
                break;
            case EventoModelo.UNION_ACEPTADA:
                new Partida().setVisible(true);
                dispose();
                break;
            case EventoModelo.UNION_RECHAZADA:
                esperandoRespuesta = false;
                ListaSalas.setEnabled(true);
                cargarSalas();
                actualizarBoton();
                javax.swing.JOptionPane.showMessageDialog(this, evento.getMensaje(),
                        "No te pudiste unir", javax.swing.JOptionPane.WARNING_MESSAGE);
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
    private javax.swing.JList<String> ListaSalas;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
