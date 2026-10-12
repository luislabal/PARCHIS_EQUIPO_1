package VISTA;

import CONTROLADOR.Aplicacion;
import CONTROLADOR.CambioTexto;
import CONTROLADOR.ControladorConfiguracion;
import MODELO.EventoModelo;
import MODELO.IObservador;
import MODELO.ModeloPartida;


public class ConfigurarPartida extends javax.swing.JFrame implements IObservador {

    private final ModeloPartida modelo;
    private final ControladorConfiguracion controlador;


   
  public ConfigurarPartida() {
      this(Aplicacion.getModelo(), Aplicacion.getControladorConfiguracion());
  }

  public ConfigurarPartida(ModeloPartida modelo, ControladorConfiguracion controlador) {
      initComponents();
      this.modelo = modelo;
      this.controlador = controlador;
      setTitle("Configurar partida");
      setLocationRelativeTo(null);
      tbnconfirmatr.setEnabled(false);                      
      CambioTexto cambio = new CambioTexto(this::validarCampos);
      txtnnombresala.getDocument().addDocumentListener(cambio);
      txtclavesala.getDocument().addDocumentListener(cambio);
      java.awt.event.ItemListener cambioRadio = e -> validarCampos();
      RD2juga.addItemListener(cambioRadio);
      RD3juga.addItemListener(cambioRadio);
      RD4juga.addItemListener(cambioRadio);
      validarCampos();
      modelo.agregarObservador(this);
  }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        RD2juga = new javax.swing.JRadioButton();
        RD3juga = new javax.swing.JRadioButton();
        RD4juga = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        txtnnombresala = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtclavesala = new javax.swing.JTextField();
        tbnconfirmatr = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        buttonGroup1.add(RD2juga);
        RD2juga.setText("2 jugadores");

        buttonGroup1.add(RD3juga);
        RD3juga.setText("3 jugadores");

        buttonGroup1.add(RD4juga);
        RD4juga.setText("4 jugadores");

        jLabel1.setText("Nombre de la sala: ");

        jLabel2.setText("Contraseña :");

        tbnconfirmatr.setText("Crear sala");
        tbnconfirmatr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbnconfirmatrActionPerformed(evt);
            }
        });

        btncancelar.setText("Cancelar");
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel3.setText("Configurar Sala");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tbnconfirmatr)
                            .addComponent(jLabel2))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtnnombresala)
                            .addComponent(txtclavesala, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncancelar)
                        .addGap(84, 84, 84))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(RD2juga, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(RD3juga, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(RD4juga, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(117, 117, 117))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RD2juga)
                    .addComponent(RD3juga)
                    .addComponent(RD4juga))
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtnnombresala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtclavesala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbnconfirmatr)
                    .addComponent(btncancelar))
                .addContainerGap(70, Short.MAX_VALUE))
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

    private void tbnconfirmatrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnconfirmatrActionPerformed
       controlador.crearPartida(txtnnombresala.getText(), txtclavesala.getText(), getMaxJugadores());
    }//GEN-LAST:event_tbnconfirmatrActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
      Interfaz in = new Interfaz();
      in.setVisible(true);
      this.dispose();
    }//GEN-LAST:event_btncancelarActionPerformed


    private int getMaxJugadores() {
        if (RD2juga.isSelected()) return 2;
        if (RD3juga.isSelected()) return 3;
        if (RD4juga.isSelected()) return 4;
        return 0;
    }

    private void colorear(javax.swing.JTextField campo, String error) {
        boolean vacio = campo.getText().trim().isEmpty();
        campo.setForeground(vacio || error == null ? java.awt.Color.BLACK : new java.awt.Color(180, 0, 0));
        campo.setToolTipText(error == null ? "Correcto" : error);
    }

    private void validarCampos() {
        String eNombre = controlador.validarNombreSala(txtnnombresala.getText());
        String eClave = controlador.validarClave(txtclavesala.getText());
        String eMax = controlador.validarMaxJugadores(getMaxJugadores());
        colorear(txtnnombresala, eNombre);
        colorear(txtclavesala, eClave);
        String primerError = !modelo.haySesion() ? "Primero debes registrarte"
                : eMax != null ? eMax : eNombre != null ? eNombre : eClave;
        tbnconfirmatr.setEnabled(primerError == null);
        tbnconfirmatr.setToolTipText(primerError == null ? "Crear la partida" : primerError);
    }

    @Override
    public void actualizar(EventoModelo evento) {
        switch (evento.getTipo()) {
            case EventoModelo.PARTIDA_CREADA:
                new Partida().setVisible(true);
                dispose();
                break;
            case EventoModelo.CONFIGURACION_RECHAZADA:
                javax.swing.JOptionPane.showMessageDialog(this, evento.getMensaje(),
                        "No se pudo crear la partida", javax.swing.JOptionPane.WARNING_MESSAGE);
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
    private javax.swing.JRadioButton RD2juga;
    private javax.swing.JRadioButton RD3juga;
    private javax.swing.JRadioButton RD4juga;
    private javax.swing.JButton btncancelar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton tbnconfirmatr;
    private javax.swing.JTextField txtclavesala;
    private javax.swing.JTextField txtnnombresala;
    // End of variables declaration//GEN-END:variables
}
