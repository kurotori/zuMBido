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
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author sebastian
 */
public class MensajeChat extends JPanel {
    
    private String mensaje;
    private Usuario usuario;
    private LocalDateTime marcaDeTiempo;
    private DateTimeFormatter formatoTiempo = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
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
    
    private void configurar(){
        setSize(650, 60);
        setPreferredSize(new Dimension(700,60));
        putClientProperty("FlatLaf.style", "arc: 16; background: #FFFFFF;");
        setLayout(new BorderLayout());
        
        pnlNombreUsuario = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlNombreUsuario.setPreferredSize(new Dimension(150, 0));
        
        String colorUsuario = Colores.extraerColorDeId(usuario.getIdPlaca());
        String colorFuenteUsuario = Colores.obtenerColorTextoHex(colorUsuario);
        System.out.println("CU: "+colorUsuario + " CFU: "+colorFuenteUsuario);
        
        pnlNombreUsuario.putClientProperty("FlatLaf.style", "arc: 16; background:"+colorUsuario+";");
        if (mio) {
            add(pnlNombreUsuario, BorderLayout.EAST);
        }
        else{
            add(pnlNombreUsuario, BorderLayout.WEST);
        }
        
        pnlMensaje = new JPanel();
        pnlMensaje.setPreferredSize(new Dimension(540,0));
        
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
        
        JLabel lblNombreUsuario = new JLabel(usuario.getNombre());
        lblNombreUsuario.setFont(fuentes.VENTANA_NEGRITA_A_CH);
        lblNombreUsuario.setForeground(Color.decode(colorFuenteUsuario));
        pnlNombreUsuario.add(lblNombreUsuario);
        
        JLabel lblMensaje = new JLabel(mensaje);
        lblMensaje.setFont(fuentes.VENTANA_NORMAL_A_CH);
        pnlMensaje.add(lblMensaje);
    }
    
}
