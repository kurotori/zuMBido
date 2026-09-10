/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

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
        setPreferredSize(new Dimension(650,60));
        putClientProperty("FlatLaf.style", "arc: 16; background: #FFFFFF;");
        setLayout(new BorderLayout());
        
        pnlNombreUsuario = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlNombreUsuario.putClientProperty("FlatLaf.style", "arc: 16; background: #00FFFF;");
        if (mio) {
            pnlNombreUsuario.putClientProperty("FlatLaf.style", "arc: 16; background: #00FFFF;");
            add(pnlNombreUsuario, BorderLayout.EAST);
        }
        else{
            pnlNombreUsuario.putClientProperty("FlatLaf.style", "arc: 16; background: #00FFFF;");
            add(pnlNombreUsuario, BorderLayout.WEST);
        }
        
        pnlMensaje = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlMensaje.putClientProperty("FlatLaf.style", "arc: 16; background: #00FFFF;");
        if (mio) {
            pnlMensaje.putClientProperty("FlatLaf.style", "arc: 16; background: #00FFFF;");
            add(pnlMensaje, BorderLayout.WEST);
        }
        else{
            pnlMensaje.putClientProperty("FlatLaf.style", "arc: 16; background: #00FFFF;");
            add(pnlMensaje, BorderLayout.EAST);
        }
        
        JLabel lblNombreUsuario = new JLabel(usuario.getNombre());
        lblNombreUsuario.setFont(fuentes.VENTANA_NEGRITA_A_CH);
        pnlNombreUsuario.add(lblNombreUsuario);
    }
    
}
