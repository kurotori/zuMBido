/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import com.ejemplo.zumbido.interfaz.BotonImagenChico;
import com.ejemplo.zumbido.interfaz.Fuentes;
import com.ejemplo.zumbido.interfaz.LabelConImagen;
import com.ejemplo.zumbido.interfaz.Textos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author sebastian
 */
public class VentanaPartidas extends JFrame{

    private JPanel pnlInferior;
    private JPanel pnlPartidas;
    private BotonImagenChico btnNuevaPartida;
    
    private Fuentes fuentes = new Fuentes();
    
    public VentanaPartidas(){
        configurarVentana();
        configurarFunciones();
        
        setVisible(true);
    }

    public VentanaPartidas(int i){
        configurarVentana();
        configurarFunciones();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    
    
    private void configurarVentana(){
        setSize(300, 600);
        setTitle("Partidas");
        
        getContentPane().setBackground(Color.white);
        setLayout(new BorderLayout());
        LabelConImagen lblLogo = new LabelConImagen(64, 64, "/imagen/juegos.png");
        
        lblLogo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        lblLogo.setFont(fuentes.VENTANA_NEGRITA_A);
        
        lblLogo.setText(Textos.PARTIDAS_ET_TITULO);
        lblLogo.repaint();
        add(lblLogo, BorderLayout.NORTH);
        
        pnlInferior = new JPanel();
        pnlInferior.setPreferredSize(new Dimension(0,50));
        pnlInferior.setBorder(BorderFactory.createMatteBorder(1,0, 0,0 , Color.black));
        pnlInferior.putClientProperty("FlatLaf.style", "arc: 16; background: #FF0800;");
        add(pnlInferior, BorderLayout.SOUTH);
        
        btnNuevaPartida = new BotonImagenChico("Crear PArtida", "/imagen/agregar_n.png", 32, 32);//new JButton("Crear Partida");
        //btnNuevaPartida.putClientProperty("FlatLaf.style", "arc: 16; background: #FF0800;");
        pnlInferior.add(btnNuevaPartida);
                
        //revalidate();
        //repaint();
    }
    
    
    private void configurarFunciones(){
        
    }
    
    public static void main(String[] args) {
        new VentanaPartidas(1);
    }
}
