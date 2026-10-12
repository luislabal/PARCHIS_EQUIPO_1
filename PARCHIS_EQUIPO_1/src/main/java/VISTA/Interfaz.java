package VISTA;

import CONTROLADOR.Aplicacion;
import MODELO.EventoModelo;
import MODELO.IObservador;
import MODELO.ModeloPartida;

public class Interfaz extends javax.swing.JFrame implements IObservador {

    private final ModeloPartida modelo;


    public Interfaz() {
        this(Aplicacion.getModelo());
    }

    public Interfaz(ModeloPartida modelo) {
        initComponents();
        this.modelo = modelo;
        setLocationRelativeTo(null);
        actualizarBotones();
        modelo.agregarObservador(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnConfigurarP = new javax.swing.JButton();
        btnUnirse = new javax.swing.JButton();
        btnusuario = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();

        jScrollPane1.setViewportView(jTree1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel2.setText("PARCHIS");

        btnConfigurarP.setText("Configurar partida");
        btnConfigurarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigurarPActionPerformed(evt);
            }
        });

        btnUnirse.setText("Unirse a la Partida");
        btnUnirse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnirseActionPerformed(evt);
            }
        });

        btnusuario.setText("Usuario");
        btnusuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnusuarioActionPerformed(evt);
            }
        });

        btnRegistrar.setText("Registrarse");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(241, 241, 241)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(252, 252, 252)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUnirse)
                            .addComponent(btnConfigurarP)
                            .addComponent(btnusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(245, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel2)
                .addGap(41, 41, 41)
                .addComponent(btnConfigurarP, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUnirse, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfigurarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigurarPActionPerformed
       ConfigurarPartida nd = new ConfigurarPartida();
       nd.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_btnConfigurarPActionPerformed

    private void btnUnirseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnirseActionPerformed
        UnirseSalas ns = new UnirseSalas();
        ns.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnUnirseActionPerformed

    private void btnusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnusuarioActionPerformed
       InfoUsuario iu = new InfoUsuario();
       iu.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_btnusuarioActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        Registro rg = new Registro();
        rg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegistrarActionPerformed

 
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                if (conectarConDialogo()) {
                    avisarSiSePierdeLaConexion();
                    new Interfaz().setVisible(true);
                } else {
                    System.exit(0);
                }
            }
        });
    }

    private static boolean conectarConDialogo() {
        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(Interfaz.class);
        String inicial = System.getProperty("parchis.servidor",
                prefs.get("servidor", "localhost:" + MODELO.Protocolo.PUERTO_POR_DEFECTO));
        while (true) {
            Object r = javax.swing.JOptionPane.showInputDialog(null,
                    "Direccion del servidor (host:puerto):", "Conectar a Parchís online",
                    javax.swing.JOptionPane.QUESTION_MESSAGE, null, null, inicial);
            if (r == null) return false;
            inicial = r.toString().trim();
            try {
                Aplicacion.conectar(inicial);
                prefs.put("servidor", inicial);
                return true;
            } catch (java.io.IOException ex) {
                javax.swing.JOptionPane.showMessageDialog(null,
                        "No se pudo conectar a " + inicial + ":\n" + ex.getMessage()
                        + "\n\n",
                        "Sin conexion", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void avisarSiSePierdeLaConexion() {
        Aplicacion.getModelo().agregarObservador(new IObservador() {
            @Override
            public void actualizar(EventoModelo e) {
                if (EventoModelo.CONEXION_PERDIDA.equals(e.getTipo())) {
                    javax.swing.JOptionPane.showMessageDialog(null,
                            e.getMensaje() + ".\nLa aplicacion se cerrara vuelve a abrirla para reconectarte.",
                            "Conexion perdida", javax.swing.JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }
        });
    }

 
    private void actualizarBotones() {
        boolean registrado = modelo.haySesion();
        boolean ocupado = modelo.estaEnPartida() || modelo.haySolicitudPropiaPendiente();
        String nombre = registrado ? modelo.getUsuarioActual().getNombre() : "";
        setTitle(registrado ? "Parchís - " + nombre : "Parchís");

        btnRegistrar.setEnabled(!registrado);
        btnRegistrar.setToolTipText(registrado ? "Ya estás registrado como " + nombre : "Registrate para poder jugar");

        String motivo = !registrado ? "Primero debes registrarte" : (ocupado ? "Ya participas en una partida" : null);
        btnConfigurarP.setEnabled(motivo == null);
        btnConfigurarP.setToolTipText(motivo == null ? "Crear una nueva partida" : motivo);
        btnUnirse.setEnabled(motivo == null);
        btnUnirse.setToolTipText(motivo == null ? "Buscar una sala y solicitar unirte" : motivo);

        btnusuario.setEnabled(registrado);
        btnusuario.setToolTipText(registrado ? "Ver tu informacion" : "Primero debes registrarte");
    }

    @Override
    public void actualizar(EventoModelo evento) {
        switch (evento.getTipo()) {
            case EventoModelo.USUARIO_REGISTRADO:
            case EventoModelo.PARTIDA_ABANDONADA:
            case EventoModelo.PARTIDA_CERRADA:
                actualizarBotones();
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
    private javax.swing.JButton btnConfigurarP;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnUnirse;
    private javax.swing.JButton btnusuario;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
