/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import com.ejemplo.zumbido.interfaz.VentanaSerial;
import com.ejemplo.zumbido.sistema.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

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
        JScrollPane scrlListaUsuarios = new JScrollPane(lstListaUsuarios);
        scrlListaUsuarios.setPreferredSize( new Dimension(0, 350) );
        add(scrlListaUsuarios, BorderLayout.NORTH);
    }
    
    public void actualizarUsuarios(ArrayList<Usuario> listaUsuarios){
        lstListaUsuarios.removeAll();
        
        DefaultListModel<String> modelo = new DefaultListModel<String>();
        
        for (Usuario usuario : listaUsuarios) {
            modelo.addElement(usuario.getNombre());
        }
        
        lstListaUsuarios.setModel(modelo);
    }
    
    
    
}
