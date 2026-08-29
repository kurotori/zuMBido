/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.interfaz;

import com.ejemplo.zumbido.Fuentes;
import com.ejemplo.zumbido.Placa;
import com.ejemplo.zumbido.sistema.Mensajes;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author sebastian
 */
public class Chat extends VentanaSerial{
    
    private Placa placa;
    
    private JTextArea txtHistorial;
    private JTextField txtMensaje;
    private JButton btnEnviar;
    private JPanel pnlContenido;
    private JPanel pnlEstado;
    private JLabel lblEstado;
    
    Fuentes fuentes = new Fuentes();

    public Chat(Placa placa) {
        this.placa = placa;
        configurarVentana();
    }
    
    private void configurarVentana() {
        setTitle("Micro:bit Radio Gateway");
        setSize(640, 480);
        setFont(fuentes.VENTANA_NORMAL_A);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Superior: Selección de Puerto
        JPanel pnlSuperior = new JPanel(new FlowLayout());
        

        JLabel lblP = new JLabel("Puerto:");
        lblP.setFont(fuentes.VENTANA_NEGRITA_A);
        pnlSuperior.add(lblP);

        add(pnlSuperior, BorderLayout.NORTH);

        //Panel de Contenido
        pnlContenido = new JPanel(new BorderLayout());
        add(pnlContenido, BorderLayout.CENTER);

        // Panel Central: Consola / Chat
        txtHistorial = new JTextArea();
        txtHistorial.setEditable(false);
        txtHistorial.setFont(fuentes.CONSOLA);
        JScrollPane scrl = new JScrollPane(txtHistorial);
        scrl.setPreferredSize(new Dimension(0, 200));
        pnlContenido.add(scrl, BorderLayout.CENTER);

        // Panel Inferior: Entrada de Texto y Envío
        JPanel pnlInferior = new JPanel(new BorderLayout());
        txtMensaje = new JTextField();
        btnEnviar = new JButton("Enviar");
        btnEnviar.setEnabled(false);

        btnEnviar.addActionListener(e -> enviarMensaje());
        txtMensaje.addActionListener(e -> enviarMensaje()); // Enviar con Enter

        pnlInferior.add(txtMensaje, BorderLayout.CENTER);
        pnlInferior.add(btnEnviar, BorderLayout.EAST);
        pnlContenido.add(pnlInferior, BorderLayout.SOUTH);

        //Panel de Estado
        pnlEstado = new JPanel(new BorderLayout());
        lblEstado = new JLabel("Placa: ");
        lblEstado.setFont(fuentes.VENTANA_NEGRITA_A);
        pnlEstado.add(lblEstado, BorderLayout.PAGE_START);

        //pnlEstado.setPreferredSize(new Dimension(0,50));
        add(pnlEstado, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    private void enviarMensaje(){
        String msj = Mensajes.componerMensaje(Mensajes.COMANDO_RED, Mensajes.SUBR_MENSAJE, txtMensaje.getText());
        placa.enviarComando(msj);
        txtMensaje.setText("");
    }

    @Override
    public void evaluarMensaje(String mensaje) {
        
    }
    
}
