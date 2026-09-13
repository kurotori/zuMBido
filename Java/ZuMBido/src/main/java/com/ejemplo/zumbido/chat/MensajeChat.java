/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import com.ejemplo.zumbido.interfaz.Colores;
import com.ejemplo.zumbido.interfaz.Fuentes;
import com.ejemplo.zumbido.sistema.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 *
 * @author sebastian
 */
public class MensajeChat extends JPanel {
    
    private String mensaje;
    private Usuario usuario;
    private LocalDateTime marcaDeTiempo;
    private DateTimeFormatter formatoTiempoA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter formatoTiempoB = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    private JPanel pnlNombreUsuario;
    private JPanel pnlMensaje;
    private boolean mio;
    
    Fuentes fuentes = new Fuentes();

    public MensajeChat(String mensaje, Usuario usuario, boolean mio) {
        this.mensaje = mensaje;
        this.usuario = usuario;
        this.mio = mio;
        this.marcaDeTiempo = LocalDateTime.now();
        configurar();
    }

    public MensajeChat(String mensajeSistema) {
        this.mensaje = mensajeSistema;
        
        this.usuario = null;
        this.mio = false;
        this.marcaDeTiempo = LocalDateTime.now();
        
        configurarMsgSistema();
    }
    
    
    
    private void configurar(){
        //setSize(650, 60);
        setPreferredSize(new Dimension(650,60));
        putClientProperty("FlatLaf.style", "arc: 16; background: #FFFFFF;");
        setLayout(new BorderLayout());
        
        pnlNombreUsuario = new JPanel();//new FlowLayout(FlowLayout.CENTER));
        pnlNombreUsuario.setLayout(new BoxLayout(pnlNombreUsuario, BoxLayout.Y_AXIS));
        pnlNombreUsuario.setPreferredSize(new Dimension(150, 0));
        
        String colorUsuario = Colores.extraerColorDeId(usuario.getIdPlaca());
        String colorFuenteUsuario = Colores.obtenerColorTextoHex(colorUsuario);
        
        pnlNombreUsuario.putClientProperty("FlatLaf.style", "arc: 16; background:"+colorUsuario+";");
        if (mio) {
            add(pnlNombreUsuario, BorderLayout.EAST);
        }
        else{
            add(pnlNombreUsuario, BorderLayout.WEST);
        }
        
        pnlMensaje = new JPanel();
        pnlMensaje.setPreferredSize(new Dimension(500,0));
        
        if (mio) {
            pnlMensaje.setLayout(new FlowLayout(FlowLayout.RIGHT));
            pnlMensaje.putClientProperty("FlatLaf.style", "arc: 16; background: #5FFC67;");
            add(pnlMensaje, BorderLayout.WEST);
        }
        else{
            pnlMensaje.setLayout(new FlowLayout(FlowLayout.LEFT));
            pnlMensaje.putClientProperty("FlatLaf.style", "arc: 16; background: #E8ECFD;");
            add(pnlMensaje, BorderLayout.EAST);
        }
        
        pnlNombreUsuario.add(Box.createVerticalStrut(5));
        
        JLabel lblNombreUsuario = new JLabel(usuario.getNombre());
        lblNombreUsuario.setFont(fuentes.VENTANA_NEGRITA_A_CH);
        lblNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombreUsuario.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        lblNombreUsuario.setForeground(Color.decode(colorFuenteUsuario));
        pnlNombreUsuario.add(lblNombreUsuario);
        
        pnlNombreUsuario.add(Box.createVerticalStrut(5));
        
        
        JLabel lblMarcaTiempo = new JLabel();
        lblMarcaTiempo.setText(
                "<html>"+ marcaDeTiempo.format(formatoTiempoA) +"<br>"+
                        marcaDeTiempo.format(formatoTiempoB) + "</html>"
        );
        lblMarcaTiempo.setFont(fuentes.VENTANA_NORMAL_A_XCH);
        lblMarcaTiempo.setForeground(Color.decode(colorFuenteUsuario));
        lblNombreUsuario.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        pnlNombreUsuario.add(lblMarcaTiempo);
        
        JTextArea txtaMensaje = new JTextArea(mensaje);
        txtaMensaje.setLineWrap(true);
        txtaMensaje.setEditable(false);
        txtaMensaje.setOpaque(false);
        txtaMensaje.setFont(fuentes.VENTANA_NORMAL_A_CH);
        txtaMensaje.setColumns(52);
        pnlMensaje.add(txtaMensaje);
    }
    
    private void configurarMsgSistema(){
        setPreferredSize(new Dimension(650,30));
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        putClientProperty("FlatLaf.style", "arc: 16; background: #FEE6F5;");
        
        JLabel lblMsj = new JLabel(mensaje);
        lblMsj.setFont(fuentes.VENTANA_NORMAL_A_CH);
        lblMsj.setHorizontalAlignment(SwingConstants.CENTER);
        
        add(lblMsj);
    }
    
}
