/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import com.ejemplo.zumbido.interfaz.Fuentes;
import com.ejemplo.zumbido.interfaz.LabelConImagen;
import com.ejemplo.zumbido.sistema.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author sebastian
 */
public class TarjetaUsuario extends JPanel implements ListCellRenderer<Usuario> {
    
    private Usuario usuario;
    
    private LabelConImagen lblIcono;
    private JLabel lblNombreUsuario;
    private JLabel lblIdPlaca;
    private JPanel pnlZonaDatos;
    private Fuentes fuentes = new Fuentes();

    public TarjetaUsuario() {
        configurar();
    }
    
    private void configurar(){
        setLayout(new BorderLayout(8, 0));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        setOpaque(true);
        
        lblIcono = new LabelConImagen(32, 32, "/imagen/usuario.png");
        lblIcono.setPreferredSize(new Dimension(32,32));
        add(lblIcono, BorderLayout.WEST);
        
        
        pnlZonaDatos = new JPanel( new BorderLayout(10, 10) );
        pnlZonaDatos.setBorder(BorderFactory.createEmptyBorder(5,5, 5, 5));
        pnlZonaDatos.setBackground(Color.WHITE);
        add(pnlZonaDatos, BorderLayout.CENTER);
        
        lblNombreUsuario = new JLabel("---");
        lblNombreUsuario.setFont(fuentes.LISTADO_USUARIOS_NOMBRE);
        pnlZonaDatos.add(lblNombreUsuario, BorderLayout.NORTH);
        
        lblIdPlaca = new JLabel("...");
        lblIdPlaca.setFont(fuentes.LISTADO_USUARIOS_PLACA);
        pnlZonaDatos.add(lblIdPlaca, BorderLayout.SOUTH);
        
        
    }

    @Override
    public Component getListCellRendererComponent
        (JList<? extends Usuario> list, Usuario usuario, 
         int index, boolean isSelected, boolean cellHasFocus) {
        this.usuario = usuario;
        
        this.lblNombreUsuario.setText(usuario.getNombre());
        this.lblIdPlaca.setText(usuario.getIdPlaca());
        
        if (isSelected) {
            //setBackground(Color.DARK_GRAY);
            pnlZonaDatos.setBackground(Color.DARK_GRAY);
            lblNombreUsuario.setForeground(list.getSelectionForeground());
            lblIdPlaca.setForeground(list.getSelectionForeground());
        } else {
            //setBackground(list.getBackground());
            pnlZonaDatos.setBackground(Color.WHITE);
            lblNombreUsuario.setForeground(list.getForeground());
            lblIdPlaca.setForeground(list.getForeground());
        }

        return this;
        
    }
    
    
}
