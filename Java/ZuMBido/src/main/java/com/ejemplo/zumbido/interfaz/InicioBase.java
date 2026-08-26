/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.interfaz;

import com.formdev.flatlaf.FlatLightLaf;
import com.ejemplo.zumbido.Colores;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import com.ejemplo.zumbido.Fuentes;
import com.ejemplo.zumbido.Placa;
import com.ejemplo.zumbido.Textos;
import com.ejemplo.zumbido.Usuario;
import com.ejemplo.zumbido.sistema.Mensajes;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author sebastian
 */
public class InicioBase extends JFrame {

    private JPanel pnlPlacas;
    //private JPanel pnlGrupoRadio;
    private JPanel pnlEstado;

    private JComboBox<String> cmbListaPlacas;
    private BotonImagenChico btnConectarPlaca;
    private BotonImagenChico btnActualizarListaPlacas;
    
    private JComboBox<String> cmbGruposRadio;
    
    private JLabel lblIdPlaca;
    private JLabel lblGrupoRadio;
    
    private Placa placa;// = new Placa();
    
    GridBagConstraints gbc = new GridBagConstraints();

    private Fuentes fuentes = new Fuentes();

    public InicioBase() {
        configurar();
        configurarFunciones();
        setVisible(true);
    }

    /**
     * Configura los componentes de la ventana
     */
    private void configurar() {
        setSize(500, 360);
        setBackground(Color.white);
        setTitle(Textos.APP_NAME + Textos.INICIO_TITULO);
        setFont(fuentes.VENTANA_NORMAL_A);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 10, 2, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        pnlPlacas = new JPanel(new GridBagLayout());//new FlowLayout(FlowLayout.CENTER, 5, 5));
        pnlPlacas.setPreferredSize(new Dimension(0, 200));
        pnlPlacas.setBackground(Color.white);
        pnlPlacas.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colores.COLOR_GRIS_CLARO));
        add(pnlPlacas, BorderLayout.NORTH);
        
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        LabelConImagen lblMicrobit = new LabelConImagen(48, 38, "/imagen/microbit.png");
        pnlPlacas.add(lblMicrobit,gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbListaPlacas = new JComboBox<>();
        cmbListaPlacas.setPreferredSize(new Dimension(300,40));
        cmbListaPlacas.setFont(fuentes.VENTANA_NORMAL_A_CH);
        
        pnlPlacas.add(cmbListaPlacas,gbc);

        gbc.gridx = 2;
        btnConectarPlaca = new BotonImagenChico(null,"/imagen/conectar.png",32,32);
        
        pnlPlacas.add(btnConectarPlaca,gbc);
        
        gbc.gridx = 3;
        btnActualizarListaPlacas = new BotonImagenChico(null,"/imagen/actualizar.png",32,32);
        pnlPlacas.add(btnActualizarListaPlacas, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lblEtIdPlaca = new JLabel(Textos.INICIO_ET_ID_PLACA);
        lblEtIdPlaca.setFont(fuentes.VENTANA_NEGRITA_A_CH);
        pnlPlacas.add(lblEtIdPlaca,gbc);
        
        lblIdPlaca = new JLabel("---");
        lblIdPlaca.setFont(fuentes.VENTANA_NORMAL_A_CH);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        pnlPlacas.add(lblIdPlaca,gbc);
        
        //Panel de Grupo Radial
        /*
        pnlGrupoRadio = new JPanel(new FlowLayout());
        pnlGrupoRadio.setPreferredSize( new Dimension(55,360) );
        pnlGrupoRadio.setBackground(Color.white);
        pnlGrupoRadio.setBorder( BorderFactory.createMatteBorder(0, 0, 1, 0, Colores.COLOR_GRIS_CLARO) );
        */
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblEtGrupoRadial = new JLabel(Textos.INICIO_ET_GRUPO_RADIO);
        lblEtGrupoRadial.setFont(fuentes.VENTANA_NEGRITA_A);

        pnlPlacas.add(lblEtGrupoRadial,gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbGruposRadio = new JComboBox<>();
        cmbGruposRadio.setPreferredSize(new Dimension(300,40));
        cmbGruposRadio.setFont(fuentes.VENTANA_NORMAL_A_CH);
        cmbGruposRadio.setEnabled(false);
        
        //String[] canales = new String[256];
        for (int i = 0; i < 256; i++) {
            cmbGruposRadio.addItem("Grupo " + i);
        }
        
        pnlPlacas.add(cmbGruposRadio, gbc);
                
        pnlEstado = new JPanel(new GridBagLayout());
        pnlEstado.setPreferredSize( new Dimension(0,220) );
        pnlEstado.setBackground(Color.white);
        
        
        
        
        
        //add(pnlGrupoRadio,BorderLayout.CENTER);
        add(pnlEstado, BorderLayout.SOUTH);
    }
    
    
    /**
     * Configurar funciones de los componentes de la ventana
     */
    private void configurarFunciones(){
        btnActualizarListaPlacas.addActionListener(e->cargarPuertosDisponibles());
        cargarPuertosDisponibles();
        btnConectarPlaca.addActionListener(e->conectarAPlaca());
    }
    
    
    /**
     * Carga los puertos seriales detectados en el combobox
     */
    private void cargarPuertosDisponibles() {
        cmbListaPlacas.removeAllItems();
        
        SerialPort[] puertos = SerialPort.getCommPorts();

        if (puertos.length < 1) {
            cmbListaPlacas.addItem(Textos.INICIO_ERROR_NO_PLACAS);
            btnConectarPlaca.setEnabled(false);
        } else {
            for (SerialPort p : puertos) {
                cmbListaPlacas.addItem(p.getSystemPortName());
            }
            btnConectarPlaca.setEnabled(true);
        }
    }
    
    /**
     * 
     */
    private void conectarAPlaca(){
        String puerto = (String)cmbListaPlacas.getSelectedItem();
        placa = new Placa(this, SerialPort.getCommPort(puerto));
        
    }
    
    
    /**
     * Evalúa los mensajes recibidos en esta ventana
     * @param mensaje 
     */
    public void evaluarMensaje(String mensaje) {
        String[] cadena = mensaje.split(":");

        switch (cadena[0]) {

            case Mensajes.RECIBIDO:
                System.out.println("La placa dice: " + mensaje);
                break;

            case Mensajes.COMANDO:

                switch (cadena[1]) {

                    case Mensajes.BOARD_ID:
                        placa.setId(cadena[2]);
                        lblIdPlaca.setText(cadena[2]);
                        //idPlaca = cadena[2];
                        //lblEstado.setText(lblEstado.getText() + idPlaca);
                        break;

                    case Mensajes.GRUPO_RADIO:
                        if(cadena.length>2){
                            int gr = Integer.parseInt(cadena[2]); 
                            placa.setGrupoRadial(gr);
                            cmbGruposRadio.setEnabled(true);
                            cmbGruposRadio.setSelectedIndex(gr);
                        }
                        //int grupo = elegirGrupoRadio();
                        //enviarComando("gr:"+grupo);
                        break;
                    default:
                        throw new AssertionError();
                }

                break;
            default:
                System.out.println("ERROR: Mensaje desconocido: " + mensaje);
            //throw new AssertionError();
        }
    }

    /**
     * Muestra un diálogo para la selección de grupo radial
     * @return 
     */
    private int elegirGrupoRadio() {
        String[] canales = new String[256];
        for (int i = 0; i < canales.length; i++) {
            canales[i] = "" + i;
        }

        String seleccion = (String) JOptionPane.showInputDialog(
                null,
                "Elige un grupo de radio",
                "Grupo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                canales,
                canales[0]
        );

        if (seleccion != null) {
            return Integer.parseInt(seleccion);
        } else {
            return 0;
        }
    }
    
    public static void main(String[] args) {
        FlatLightLaf.setup();
        
        SwingUtilities.invokeLater(InicioBase::new);
    }
}
