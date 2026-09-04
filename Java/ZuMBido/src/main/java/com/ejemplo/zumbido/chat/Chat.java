/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import com.ejemplo.zumbido.interfaz.Fuentes;
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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author sebastian
 */
public class Chat extends JFrame implements OyenteMensajes {

    private Placa placa;
    private JFrame ventanaInicio;

    private JTextArea txtHistorial;
    private JTextField txtMensaje;
    private JButton btnEnviar;

    private PanelZonaUsuario pnlLatUsuario;

    private JPanel pnlContenido;
    private JPanel pnlEstado;
    private JLabel lblEstado;
    private JLabel lblUsuario;
    private JLabel lblcantUsuarios;

    Fuentes fuentes = new Fuentes();

    public Chat(Placa placa, JFrame ventanaInicio) {
        this.placa = placa;
        this.ventanaInicio = ventanaInicio;
        //placa.setVentana(this);
        //this.ventanaAnterior = ventanaAnterior;
        configurarVentana();
        agregarIdPlaca();
        agregarNombreUsuario();
        configurarFunciones();
    }

    public Chat() {
        configurarVentana();
        configurarFunciones();
    }

    private void configurarVentana() {
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
        JPanel pnlSuperior = new JPanel(new FlowLayout());

        LabelConImagen lblIcono = new LabelConImagen(64, 64, "/imagen/icono_chat.png");
        pnlSuperior.add(lblIcono);
        
        
        JLabel lblEtUsuario = new JLabel(Textos.CHAT_ET_USUARIO);
        lblEtUsuario.setFont(fuentes.VENTANA_NEGRITA_A);
        pnlSuperior.add(lblEtUsuario);

        lblUsuario = new JLabel("---");
        lblUsuario.setFont(fuentes.VENTANA_NORMAL_A_CH);
        pnlSuperior.add(lblUsuario);

        add(pnlSuperior, BorderLayout.NORTH);

        //Panel de Contenido
        pnlContenido = new JPanel(new BorderLayout());
        add(pnlContenido, BorderLayout.CENTER);

        // Panel Central: Consola / Chat
        txtHistorial = new JTextArea();
        txtHistorial.setEditable(false);
        txtHistorial.setFont(fuentes.CONSOLA);
        txtHistorial.setBackground(Color.white);

        JScrollPane scrl = new JScrollPane(txtHistorial);
        scrl.setPreferredSize(new Dimension(0, 200));

        pnlContenido.add(scrl, BorderLayout.CENTER);

        // Panel Inferior: Entrada de Texto y Envío
        JPanel pnlInferior = new JPanel(new BorderLayout());
        txtMensaje = new JTextField();
        btnEnviar = new JButton("Enviar");
        //btnEnviar.setEnabled(false);

        btnEnviar.addActionListener(e -> enviarMensaje());
        txtMensaje.addActionListener(e -> enviarMensaje()); // Enviar con Enter

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
        
        //pnlEstado.setPreferredSize(new Dimension(0,50));
        add(pnlEstado, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void configurarFunciones() {
        actualizarUsuarios();
    }

    private void actualizarUsuarios() {

        pnlLatUsuario.actualizarUsuarios(placa.getUsuarios().getListaUsuarios());
        SwingUtilities.invokeLater(
                ()->{
                    System.out.println(Textos.CHAT_ET_USUARIOS_CONECTADOS + "-->" + placa.getUsuarios().getCantUsuarios());
                    lblcantUsuarios.setText(Textos.CHAT_ET_USUARIOS_CONECTADOS + placa.getUsuarios().getCantUsuarios());
                }
        );
        
    }

    private void enviarMensaje() {
        String m = txtMensaje.getText().trim();
        if (m.length() > 0) {
            String msj = Mensajes.componerMensaje(Mensajes.COMANDO_RED, Mensajes.SUBR_MENSAJE, m);
            placa.enviarComando(msj);
            txtHistorial.append("[" + placa.getUsuario().getNombre() + "]:" + m + "\n");
            txtMensaje.setText("");
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
        String usuario = placa.getUsuarios().buscarPorId(idPlaca).getNombre();
        String msj = "[" + usuario + "]:"+mensaje+"\n";
        
        txtHistorial.append(msj);

    }

    @Override
    public void onNuevoLogin(Usuario usuario) {
        actualizarUsuarios();
        txtHistorial.append("[Se ha conectado " + usuario.getNombre() + " desde la placa " + usuario.getIdPlaca() + "]\n");
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
        lblEstado.setText("Placa: " + placa.getId());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Chat::new);
    }
}
