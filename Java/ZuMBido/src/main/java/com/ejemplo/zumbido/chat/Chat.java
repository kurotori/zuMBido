/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import com.ejemplo.zumbido.interfaz.Fuentes;
import com.ejemplo.zumbido.interfaz.Iconos;
import com.ejemplo.zumbido.interfaz.LabelConImagen;
import com.ejemplo.zumbido.interfaz.Textos;
import com.ejemplo.zumbido.sistema.Placa;
import com.ejemplo.zumbido.sistema.Mensajes;
import com.ejemplo.zumbido.sistema.OyenteMensajes;
import com.ejemplo.zumbido.sistema.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author sebastian
 */
public class Chat extends JFrame implements OyenteMensajes {

    public static final int LONGITUD_MAXIMA_MENSAJES = 229;
    
    private Placa placa;
    private JFrame ventanaInicio;

    private JPanel pnlChat;

    private JTextField txtMensaje;
    private JButton btnEnviar;

    private PanelZonaUsuario pnlLatUsuario;

    private JPanel pnlContenido;
    private JPanel pnlEstado;
    private JLabel lblEstado;
    private JLabel lblUsuario;
    private JLabel lblcantUsuarios;
    private JScrollPane scrl;

    private int cantMensajes = 0;

    Fuentes fuentes = new Fuentes();
    Iconos iconos = new Iconos();
    private GridBagConstraints gbc = new GridBagConstraints();

    public Chat(Placa placa, JFrame ventanaInicio) {
        this.placa = placa;
        this.ventanaInicio = ventanaInicio;

        configurarVentana();
        agregarIdPlaca();
        agregarNombreUsuario();
        configurarFunciones();
    }

    /**
     * Método constructor para pruebas
     */
    public Chat() {
        configurarVentana();
        configurarFunciones();

    }

    
    
    private void configurarVentana() {
        UIManager.put("OptionPane.background", Color.WHITE);
        
        setTitle("MicroChat");
        setSize(900, 600);
        

        ImageIcon img = new ImageIcon(getClass().getResource("/imagen/icono_chat.png"));

        setIconImage(img.getImage());

        setFont(fuentes.VENTANA_NORMAL_A);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.white);

        pnlLatUsuario = new PanelZonaUsuario(this);
        add(pnlLatUsuario, BorderLayout.WEST);

        // Panel Superior: Selección de Puerto
        JPanel pnlSuperior = new JPanel(new FlowLayout(FlowLayout.LEADING));
        pnlSuperior.setBackground(Color.white);
        LabelConImagen lblIcono = new LabelConImagen(64, 64, "/imagen/icono_chat.png");
        pnlSuperior.add(lblIcono);

        JLabel lblEtUsuario = new JLabel(Textos.CHAT_ET_USUARIO);
        lblEtUsuario.setFont(fuentes.VENTANA_NEGRITA_A);
        pnlSuperior.add(lblEtUsuario);

        lblUsuario = new JLabel("---");
        lblUsuario.setFont(fuentes.VENTANA_NEGRITA_B);
        pnlSuperior.add(lblUsuario);

        add(pnlSuperior, BorderLayout.NORTH);

        //Panel de Contenido
        pnlContenido = new JPanel(new BorderLayout());
        pnlContenido.setBackground(Color.white);
        add(pnlContenido, BorderLayout.CENTER);

        // Panel Central: Consola / Chat
        pnlChat = new JPanel();
        pnlChat.setLayout(new GridBagLayout());
        pnlChat.setBackground(Color.white);
        
        // Contenedores del chat
        JPanel pnlContenedorChat = new JPanel(new BorderLayout());
        pnlContenedorChat.add(pnlChat, BorderLayout.NORTH);
        pnlContenedorChat.setBackground(Color.white);
        pnlContenedorChat.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 10, 0);
//
        scrl = new JScrollPane(pnlContenedorChat);
        //scrl.setPreferredSize(new Dimension(0, 200));
        

        pnlContenido.add(scrl, BorderLayout.CENTER);

        // Panel Inferior: Entrada de Texto y Envío
        JPanel pnlInferior = new JPanel(new BorderLayout());
        txtMensaje = new JTextField();
        btnEnviar = new JButton("Enviar");

        pnlInferior.add(txtMensaje, BorderLayout.CENTER);
        pnlInferior.add(btnEnviar, BorderLayout.EAST);
        pnlContenido.add(pnlInferior, BorderLayout.SOUTH);

        //Panel de Estado
        pnlEstado = new JPanel();//new FlowLayout(FlowLayout.CENTER, 10, 2));
        pnlEstado.setLayout(new BoxLayout(pnlEstado, BoxLayout.X_AXIS));

        pnlEstado.add(Box.createHorizontalStrut(10));

        lblEstado = new JLabel(Textos.CHAT_ET_PLACA);
        lblEstado.setFont(fuentes.VENTANA_NEGRITA_A);
        pnlEstado.add(lblEstado);

        pnlEstado.add(Box.createHorizontalGlue());

        lblcantUsuarios = new JLabel(Textos.CHAT_ET_USUARIOS_CONECTADOS);
        lblcantUsuarios.setFont(fuentes.VENTANA_NEGRITA_A);
        pnlEstado.add(lblcantUsuarios);

        pnlEstado.add(Box.createHorizontalStrut(35));

        add(pnlEstado, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void configurarFunciones() {
        btnEnviar.addActionListener(e -> enviarMensaje());
        txtMensaje.addActionListener(e -> enviarMensaje()); // Enviar con Enter
        actualizarUsuarios();
        agregarMensajeGeneral("<html>Hola <b>"+placa.getUsuario().getNombre()+"</b>. Te conectaste a la red <b>zuMBido-MicroChat</b></html>");
    }

    /**
     * Inicia el proceso de actualizar el listado de usuarios
     */
    private void actualizarUsuarios() {

        pnlLatUsuario.actualizarUsuarios(placa.getUsuarios().getListaUsuarios());
        SwingUtilities.invokeLater(
                () -> {
                    System.out.println(Textos.CHAT_ET_USUARIOS_CONECTADOS + "-->" + placa.getUsuarios().getCantUsuarios());
                    lblcantUsuarios.setText(Textos.CHAT_ET_USUARIOS_CONECTADOS + placa.getUsuarios().getCantUsuarios());
                }
        );

    }

    /**
     * Agrega un mensaje al panel de chat
     *
     * @param usuario
     * @param msj
     * @param mio
     */
    private void agregarMensaje(Usuario usuario, String msj, boolean mio) {
        SwingUtilities.invokeLater(
                () -> {
                    MensajeChat pnlMsj = new MensajeChat(msj, usuario, mio);
                    gbc.gridx = 0;
                    gbc.gridy = cantMensajes++;
                    gbc.weightx = 1.0;
                    gbc.weighty = 0.0;
                    pnlChat.add(pnlMsj, gbc);
                    pnlChat.revalidate();
                    pnlChat.repaint();

                    SwingUtilities.invokeLater(() -> {
                        scrl.getVerticalScrollBar().setValue(scrl.getVerticalScrollBar().getMaximum());
                    });
                }
        );

    }

    private void agregarMensajeGeneral(String msj) {
        SwingUtilities.invokeLater(
                () -> {
                    //JLabel lbl = new JLabel(msj);
                    //lbl.setFont(fuentes.VENTANA_NORMAL_A_CH);
                    MensajeChat pnlMsj = new MensajeChat(msj);
                    gbc.gridx = 0;
                    gbc.gridy = cantMensajes++;
                    gbc.weightx = 1.0;
                    gbc.weighty = 0.0;
                    //pnlChat.add(lbl, gbc);
                    pnlChat.add(pnlMsj, gbc);
                    pnlChat.revalidate();
                    pnlChat.repaint();

                    SwingUtilities.invokeLater(() -> {
                        scrl.getVerticalScrollBar().setValue(scrl.getVerticalScrollBar().getMaximum());
                    });
                }
        );

    }

    private void enviarMensaje() {

        String m = txtMensaje.getText().trim();
        if (m.length() > 0) {
            
            if (m.length() > LONGITUD_MAXIMA_MENSAJES) {
                JOptionPane.showMessageDialog(
                        this, 
                        "No puedo enviar un mensaje \n con más de " + LONGITUD_MAXIMA_MENSAJES + " caracteres.", 
                        placa.getId() + " dice:", 
                        JOptionPane.PLAIN_MESSAGE,
                        iconos.ICONO_ERROR_96);
                txtMensaje.requestFocus();
                txtMensaje.selectAll();
            }
            else{
                String msj = Mensajes.componerMensaje(Mensajes.COMANDO_RED, Mensajes.SUBR_MENSAJE, m);
                placa.enviarComando(msj);
                agregarMensaje(placa.getUsuario(), m, true);
                txtMensaje.setText("");
            }
            
            
        }

    }

    // -------
    @Override
    public void onBoardIdRecibido(String id) {
        OyenteMensajes.super.onBoardIdRecibido(id); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void onGrupoRadioCambiado(int grupo) {
        OyenteMensajes.super.onGrupoRadioCambiado(grupo); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void onMensajeGenerico(String comando, String subcomando, String[] parametros) {

    }

    @Override
    public void onMensajePlaca(String titulo, String texto, boolean esError) {
        OyenteMensajes.super.onMensajePlaca(titulo, texto, esError); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void onMensajePublico(String mensaje, String idPlaca) {
        Usuario usuario = placa.getUsuarios().buscarPorId(idPlaca);
        agregarMensaje(usuario, mensaje, false);
    }

    @Override
    public void onNuevoLogin(Usuario usuario) {
        actualizarUsuarios();
        //areaChat.
        //txtHistorial.append("[Se ha conectado " + usuario.getNombre() + " desde la placa " + usuario.getIdPlaca() + "]\n");
        agregarMensajeGeneral("<html><i>Se ha conectado <b>"+usuario.getNombre()+"</b></i></html>");
    }

    @Override
    public void onHola() {
        //pnlLatUsuario.actualizarUsuarios(placa.getUsuarios().getListaUsuarios());
        actualizarUsuarios();
    }

    // ------- 
    private void agregarNombreUsuario() {
        lblUsuario.setText(placa.getUsuario().getNombre());
    }

    private void agregarIdPlaca() {
        lblEstado.setText("Placa: " + placa.getId() + " en " + placa.getPuerto().getSystemPortName());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Chat();
        });
    }

    @Override
    public void onActualizarUsuarios() {
        SwingUtilities.invokeLater(() -> {
            actualizarUsuarios();
        });
    }

    @Override
    public void onUsuarioDesconectado(Usuario usuario) {
        SwingUtilities.invokeLater(
                ()->{
                    agregarMensajeGeneral(
                            "<html><b>"+usuario.getNombre()+
                            "</b> se ha desconectado</html>"
                    );
                }
        );
    }
    
    

}
