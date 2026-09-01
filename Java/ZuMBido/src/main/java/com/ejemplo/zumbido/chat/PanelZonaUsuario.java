/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import com.ejemplo.zumbido.interfaz.VentanaSerial;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author sebastian
 */
public class PanelZonaUsuario extends JPanel{

    private JFrame ventana;
    
    private JList<String> lstListaUsuarios;
    private JPanel pnlMenuUsuario;
    
    
    
    public PanelZonaUsuario(JFrame ventana) {
        this.ventana = ventana;
        configurar();
    }
    
    private void configurar(){
        setPreferredSize( new Dimension(200,0));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.gray));
        setBackground(Color.WHITE);
        
        lstListaUsuarios = new JList<>();
        lstListaUsuarios.setPreferredSize( new Dimension(0, 350) );
        lstListaUsuarios.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.gray));
        add(lstListaUsuarios, BorderLayout.NORTH);
    }
    
    public void actualizarUsuarios(){
        
    }
    
}
