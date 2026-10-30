/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import com.ejemplo.zumbido.interfaz.*;
import com.ejemplo.zumbido.juegos.NuevaPartida;
import com.ejemplo.zumbido.juegos.TarjetaPartida;
import com.ejemplo.zumbido.sistema.Partida;
import com.ejemplo.zumbido.sistema.Registro;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author sebastian
 */
public class VentanaPartidas extends JFrame {

    private Chat vntChat;
    
    private JPanel pnlInferior;
    private JPanel pnlPartidas;
    private BotonImagenChico btnNuevaPartida;

    private DefaultListModel<Partida> modeloPartidas;
    private JList<Partida> lstListaPartidas;

    private ArrayList<Partida> arrPartidas =  new ArrayList<>();


    private Fuentes fuentes = new Fuentes();
    private Iconos iconos = new Iconos();

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

        modeloPartidas = new DefaultListModel<>();
        lstListaPartidas = new JList<>(modeloPartidas);
        lstListaPartidas.setCellRenderer(new TarjetaPartida(this));
        lstListaPartidas.setPreferredSize(new Dimension(0, 350));
        lstListaPartidas.setBackground(Color.white);
        JScrollPane scrlListaPartidas = new JScrollPane(lstListaPartidas);
        scrlListaPartidas.setPreferredSize(new Dimension(0, 350));
        add(scrlListaPartidas, BorderLayout.CENTER);
        //revalidate();
        //repaint();
    }

    private void configurarFunciones() {
        btnNuevaPartida.addMouseListener(
                new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (vntChat.getPlaca().getUsuario().getPartida() != null) {
                    btnNuevaPartida.setBackground(Colores.COLOR_NARANJA_CANCELAR);
                    btnNuevaPartida.setForeground(Color.white);
                }
                else{
                    btnNuevaPartida.setBackground(Colores.COLOR_VERDE_CREAR);
                }
                //Color.decode("#FF352E"));
                //btnNuevaPartida.setForeground(Color.white);
                //btnNuevaPartida.cambiarImagen("/imagen/agregar_b.png");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnNuevaPartida.setBackground(Color.WHITE);
                if (vntChat.getPlaca().getUsuario().getPartida() != null) {
                    btnNuevaPartida.setForeground(Color.red);
                }
                else{
                    btnNuevaPartida.setForeground(Color.black);
                }

                //btnNuevaPartida.cambiarImagen("/imagen/agregar_n.png");
            }
        }
        );
        
        btnNuevaPartida.addActionListener(
                e->{
                    
                    if (vntChat.getPlaca().getUsuario().getPartida() != null) {
                        Registro.registrarEvento(this,"Este usuario ya tiene una partida creada");
                        int opcion = JOptionPane.showConfirmDialog(
                                this,
                                "¿Deseas eliminar tu partida actual?",
                                "Atención",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.PLAIN_MESSAGE,
                                iconos.ICONO_PREGUNTA_96
                                );
                        if (opcion == JOptionPane.YES_OPTION) {
                            eliminarPartida(vntChat.getPlaca().getUsuario().getPartida());
                        }
                    }
                    else {
                        Registro.registrarEvento(this,"Abriendo ventana de creación de partida");
                        NuevaPartida nPartida = new NuevaPartida(this.vntChat, this);
                    }
                }
        );
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();

        new VentanaPartidas(1);
    }

    /**
     * Actualiza el listado de partidas
     */
    public void actualizarPartidas() {
        SwingUtilities.invokeLater(() -> {
            modeloPartidas.removeAllElements();

            for(Partida partida : arrPartidas) {
                modeloPartidas.addElement(partida);
            }
        });
    }

    /**
     * Añade una partida, diferenciando entre partidas propias y de otros usuarios
     * @param partida La partida que se agrega al listado
     * @param esPropia establece si la partida es del mismo usuario o de otro
     */
    public void agregarPartida(Partida partida, boolean esPropia) {
        if (esPropia){
            arrPartidas.addFirst(partida);
            btnNuevaPartida.setText("Eliminar Partida");

            btnNuevaPartida.cambiarImagen("/imagen/cancelar_b.png");
        }
        else{
            arrPartidas.add(partida);
        }

        actualizarPartidas();
    }

    public void eliminarPartida(Partida partida){
        arrPartidas.remove(partida);
        vntChat.getPlaca().getUsuario().setPartida(null);
        actualizarPartidas();
    }

    //TODO: Mover la gestión de las partidas a su propia clase para mejor manejo

    public Partida buscarPartida(Partida p){

    }
}
