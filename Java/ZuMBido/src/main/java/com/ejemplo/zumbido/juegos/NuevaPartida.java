/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.juegos;

import com.ejemplo.zumbido.chat.Chat;
import com.ejemplo.zumbido.chat.VentanaPartidas;
import com.ejemplo.zumbido.interfaz.BotonImagenChico;
import com.ejemplo.zumbido.interfaz.Colores;
import com.ejemplo.zumbido.interfaz.Fuentes;
import com.ejemplo.zumbido.interfaz.LabelConImagen;
import com.ejemplo.zumbido.sistema.Partida;
import com.ejemplo.zumbido.sistema.Registro;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 *
 * @author sebastian
 */
public class NuevaPartida extends JFrame{

    private Chat vntChat;
    private VentanaPartidas vntPartidas;

    private BotonImagenChico btnCrearPartida;
    private BotonImagenChico btnCancelar;

    Fuentes fuentes  = new Fuentes();

    //Métodos Constructores
    // 1 - De uso práctico
    public NuevaPartida(Chat vntChat, VentanaPartidas vntPartidas) throws HeadlessException {
        this.vntChat = vntChat;
        this.vntPartidas = vntPartidas;
        configurarVentana();
        configurarFunciones();
        setVisible(true);
    }

    // 2 - Para pruebas de interfáz

    /**
     * PARA PRUEBAS DE INTERFAZ
     * @param i
     */
    public NuevaPartida(int i){
        configurarVentana();
        configurarFunciones();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }



    private void configurarVentana(){
        setSize(600,500);
        setLocationRelativeTo(null);
        setLayout( new BorderLayout());
        setUndecorated(true);


        JPanel pnlEncabezado = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        pnlEncabezado.setPreferredSize(new Dimension(0,70));
        pnlEncabezado.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.LIGHT_GRAY));
        pnlEncabezado.setBackground(Color.WHITE);
        add(pnlEncabezado,BorderLayout.NORTH);

        LabelConImagen lblLogo = new LabelConImagen(64,64,"/imagen/juegos.png");
        lblLogo.setText("Nueva Partida");
        lblLogo.setIconTextGap(20);
        lblLogo.setFont(fuentes.VENTANA_NEGRITA_B);
        pnlEncabezado.add(lblLogo);

        //Contenido: Debe complementarse con un scroll adecuado
        JPanel pnlContenido = new JPanel();
        pnlContenido.setBackground(Color.white);
        add(pnlContenido,BorderLayout.CENTER);

        JPanel pnlBotonera = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        pnlBotonera.setPreferredSize(new Dimension(0,50));
        pnlBotonera.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.LIGHT_GRAY));
        pnlBotonera.setBackground(Color.WHITE);
        add(pnlBotonera, BorderLayout.SOUTH);

        btnCrearPartida = new BotonImagenChico("Crear Partida", "/imagen/agregar_n.png",24,24);
        btnCrearPartida.setBackground(Color.white);
        btnCrearPartida.setFont(fuentes.VENTANA_NEGRITA_A);
        btnCrearPartida.setIconTextGap(20);
        btnCrearPartida.putClientProperty("FlatLaf.style", "arc: 16;");
        pnlBotonera.add(btnCrearPartida);

        btnCancelar = new BotonImagenChico("Cancelar", "/imagen/cancelar.png",24,24);
        btnCancelar.setBackground(Color.white);
        btnCancelar.setFont(fuentes.VENTANA_NEGRITA_A);
        btnCancelar.setIconTextGap(20);
        btnCancelar.putClientProperty("FlatLaf.style", "arc: 16;");
        pnlBotonera.add(btnCancelar);
    }


    
    private void configurarFunciones(){
        btnCrearPartida.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        btnCrearPartida.setBackground(Colores.COLOR_VERDE_CREAR);
                        //btnCrearPartida.setForeground(Color.white);
                        //btnCrearPartida.cambiarImagen("/imagen/agregar_b.png");
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        btnCrearPartida.setBackground(Color.white);
                        btnCrearPartida.setForeground(Color.black);
                        btnCrearPartida.cambiarImagen("/imagen/agregar_n.png");
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        crearPartida();
                    }
                }
        );

        btnCancelar.addMouseListener(
            new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    cerrarVentana();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    btnCancelar.cambiarImagen("/imagen/cancelar_b.png");
                    btnCancelar.setBackground(Colores.COLOR_NARANJA_CANCELAR);
                    btnCancelar.setForeground(Color.white);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    btnCancelar.cambiarImagen("/imagen/cancelar.png");
                    btnCancelar.setBackground(Color.white);
                    btnCancelar.setForeground(Color.black);
                }
            }


        );
    }

    private void cerrarVentana(){
        this.dispose();
    }

    private void crearPartida(){
        Partida partida = new Partida(vntChat.getPlaca().getUsuario());
        Registro.registrarEvento(this,
                "El usuario " + vntChat.getPlaca().getUsuario().getNombre() + " creó una partida con esta marca: "+
                        vntChat.getPlaca().getUsuario().getPartida().getMarcaTiempo().toString()
                );
        vntPartidas.agregarPartida(partida, true);
        cerrarVentana();
    }

    static void main() {
        FlatLightLaf.setup();

        new NuevaPartida(1);
    }
}
