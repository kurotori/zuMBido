/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import com.ejemplo.zumbido.interfaz.BotonImagenChico;
import com.ejemplo.zumbido.interfaz.Colores;
import com.ejemplo.zumbido.interfaz.Fuentes;
import com.ejemplo.zumbido.interfaz.LabelConImagen;
import com.ejemplo.zumbido.interfaz.Textos;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author sebastian
 */
public class VentanaPartidas extends JFrame {

    private Chat vntChat;
    
    private JPanel pnlInferior;
    private JPanel pnlPartidas;
    private BotonImagenChico btnNuevaPartida;
    

    private Fuentes fuentes = new Fuentes();

    public VentanaPartidas(Chat vntChat) {
        this.vntChat = vntChat;
        configurarVentana();
        configurarFunciones();

        setVisible(true);
    }

    public VentanaPartidas(int i) {
        configurarVentana();
        configurarFunciones();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void configurarVentana() {
        setSize(300, 600);
        setTitle("Partidas");

        getContentPane().setBackground(Color.white);
        setLayout(new BorderLayout());
        LabelConImagen lblLogo = new LabelConImagen(64, 64, "/imagen/juegos.png");

        //lblLogo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        lblLogo.setFont(fuentes.VENTANA_NEGRITA_A);

        lblLogo.setText(Textos.PARTIDAS_ET_TITULO);
        lblLogo.repaint();
        add(lblLogo, BorderLayout.NORTH);

        pnlInferior = new JPanel();
        pnlInferior.setBackground(Color.white);
        pnlInferior.setPreferredSize(new Dimension(0, 40));
        pnlInferior.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        //pnlInferior.putClientProperty("FlatLaf.style", "arc: 16; background: #FF0800;");
        add(pnlInferior, BorderLayout.SOUTH);

        btnNuevaPartida = new BotonImagenChico("Crear Partida", "/imagen/agregar_n.png", 16, 16);//new JButton("Crear Partida");

        btnNuevaPartida.putClientProperty("FlatLaf.style", "arc: 16;");
        btnNuevaPartida.setBackground(Color.WHITE);
        pnlInferior.add(btnNuevaPartida);

        //revalidate();
        //repaint();
    }

    private void configurarFunciones() {
        btnNuevaPartida.addMouseListener(
                new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnNuevaPartida.setBackground(Color.decode("#FF352E"));
                btnNuevaPartida.setForeground(Color.white);
                btnNuevaPartida.cambiarImagen("/imagen/agregar_b.png");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnNuevaPartida.setBackground(Color.WHITE);
                btnNuevaPartida.setForeground(Color.black);
                btnNuevaPartida.cambiarImagen("/imagen/agregar_n.png");
            }
        }
        );
        
        btnNuevaPartida.addActionListener(
                e->{
                    
                    if (vntChat.getPlaca().getUsuario().getPartida() != null) {
                        System.out.println("Nueva Partida");
                    }
                    else{
                        
                    }
                }
        );
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();

        new VentanaPartidas(1);
    }
}
