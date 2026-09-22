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
public class ChatPrivado extends JFrame{

    private Chat ventanaChat;
    private Usuario otroUsuario;

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

    public ChatPrivado(Chat ventanaChat, Usuario usuario) {
   
        this.ventanaChat = ventanaChat;
        this.otroUsuario = usuario;
        
        configurarVentana();
        //agregarIdPlaca();
        agregarNombreUsuario();
        configurarFunciones();
    }

    public ChatPrivado() {
        configurarVentana();
        configurarFunciones();
        
        Usuario pruebas = new Usuario("Fulano", "abcdefgh12345");
        //this.
    }

    private void configurarVentana() {
        UIManager.put("OptionPane.background", Color.WHITE);
        
        setTitle("MicroChat");
        setSize(700, 500);
        setResizable(false);

        ImageIcon img = new ImageIcon(getClass().getResource("/imagen/icono_chat.png"));

        setIconImage(img.getImage());

        setFont(fuentes.VENTANA_NORMAL_A);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        
        getContentPane().setBackground(Color.white);

        //pnlLatUsuario = new PanelZonaUsuario(this);
        //add(pnlLatUsuario, BorderLayout.WEST);

        // Panel Superior: Selección de Puerto
        JPanel pnlSuperior = new JPanel(new FlowLayout(FlowLayout.LEADING));
        pnlSuperior.setBackground(Color.white);
        LabelConImagen lblIcono = new LabelConImagen(64, 64, "/imagen/icono_chat.png");
        pnlSuperior.add(lblIcono);

        JLabel lblEtUsuario = new JLabel(Textos.CHAT_PRIVADO_ET_CHATEANDO_CON);
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
        
        JPanel pnlContenedorChat = new JPanel(new BorderLayout());
        pnlContenedorChat.setBackground(Color.white);
        pnlContenedorChat.add(pnlChat, BorderLayout.NORTH);
         pnlContenedorChat.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 10, 0);
//
        scrl = new JScrollPane(pnlContenedorChat);
        scrl.setPreferredSize(new Dimension(0, 200));

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

//        lblEstado = new JLabel(Textos.CHAT_ET_PLACA);
//        lblEstado.setFont(fuentes.VENTANA_NEGRITA_A);
//        pnlEstado.add(lblEstado);

        pnlEstado.add(Box.createHorizontalGlue());

//        lblcantUsuarios = new JLabel(Textos.CHAT_ET_USUARIOS_CONECTADOS);
//        lblcantUsuarios.setFont(fuentes.VENTANA_NEGRITA_A);
//        pnlEstado.add(lblcantUsuarios);

        pnlEstado.add(Box.createHorizontalStrut(35));

        add(pnlEstado, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void configurarFunciones() {
        btnEnviar.addActionListener(e -> enviarMensaje());
        txtMensaje.addActionListener(e -> enviarMensaje()); // Enviar con Enter
        agregarMensajeGeneral("Estas hablando con " + getOtroUsuario().getNombre());
    }
    
    private void agregarNombreUsuario() {
        lblUsuario.setText(getOtroUsuario().getNombre());
    }


    /**
     * Agrega un mensaje al panel de chat
     *
     * @param usuario
     * @param msj
     * @param mio
     */
    public void agregarMensaje(Usuario usuario, String msj, boolean mio) {
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
                    MensajeChat pnlMsj = new MensajeChat(msj);
                    //JLabel lbl = new JLabel(msj);
                    ///lbl.setFont(fuentes.VENTANA_NORMAL_A_CH);
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

    private void enviarMensaje() {

        String m = txtMensaje.getText().trim();
        if (m.length() > 0) {
            
            if (m.length() > ventanaChat.LONGITUD_MAXIMA_MENSAJES_PRIV) {
                JOptionPane.showMessageDialog(
                        this, 
                        "No puedo enviar un mensaje privada \n con más de " + ventanaChat.LONGITUD_MAXIMA_MENSAJES_PRIV + " caracteres.", 
                        ventanaChat.getPlaca().getId() + " dice:", 
                        JOptionPane.PLAIN_MESSAGE,
                        iconos.ICONO_ERROR_96);
                txtMensaje.requestFocus();
                txtMensaje.selectAll();
            }
            else{
                ventanaChat.enviarMensajePrivado(m, otroUsuario);
                //String msj = Mensajes.componerMensaje(Mensajes.COMANDO_RED, Mensajes.SUBR_MENSAJE, m);
                //placa.enviarComando(msj);
                agregarMensaje(ventanaChat.getPlaca().getUsuario(), m, true);
                txtMensaje.setText("");
            }
            
            
        }

    }


   

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            new ChatPrivado();
        });
    }

    /**
     * @return the otroUsuario
     */
    public Usuario getOtroUsuario() {
        return otroUsuario;
    }


}
