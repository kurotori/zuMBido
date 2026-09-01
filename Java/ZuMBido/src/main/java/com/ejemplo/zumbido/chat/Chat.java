/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import com.ejemplo.zumbido.Fuentes;
import com.ejemplo.zumbido.Placa;
import com.ejemplo.zumbido.interfaz.VentanaSerial;
import com.ejemplo.zumbido.sistema.Mensajes;
import com.ejemplo.zumbido.sistema.OyenteMensajes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author sebastian
 */
public class Chat extends JFrame implements OyenteMensajes{
    
    private Placa placa;
    
    
    private JTextArea txtHistorial;
    private JTextField txtMensaje;
    private JButton btnEnviar;
    
    private PanelZonaUsuario pnlLatUsuario;
    
    private JPanel pnlContenido;
    private JPanel pnlEstado;
    private JLabel lblEstado;
    private JLabel lblUsuario;
    
    Fuentes fuentes = new Fuentes();

    public Chat(Placa placa) {
        this.placa = placa;
        //placa.setVentana(this);
        configurarVentana();
        agregarIdPlaca();
        agregarNombreUsuario();
    }

    public Chat(){
        configurarVentana();
    }


    
    
    
    
    private void configurarVentana() {
        setTitle("MicroChat");
        setSize(800, 600);
        setFont(fuentes.VENTANA_NORMAL_A);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.white);
        
        pnlLatUsuario = new PanelZonaUsuario(this);
        add(pnlLatUsuario,BorderLayout.WEST);

        // Panel Superior: Selección de Puerto
        JPanel pnlSuperior = new JPanel(new FlowLayout());
        

        JLabel lblEtUsuario = new JLabel("Usuario:" );
        lblEtUsuario.setFont(fuentes.VENTANA_NEGRITA_A);
        pnlSuperior.add(lblEtUsuario);
        
        lblUsuario = new JLabel("---");
        lblUsuario.setFont(fuentes.VENTANA_NORMAL_A_CH);
        pnlSuperior.add(lblUsuario);

        add(pnlSuperior, BorderLayout.NORTH);

        //Panel de Contenido
        pnlContenido = new JPanel(new BorderLayout());
        add(pnlContenido, BorderLayout.CENTER);

        // Panel Central: Consola / Chat
        txtHistorial = new JTextArea();
        txtHistorial.setEditable(false);
        txtHistorial.setFont(fuentes.CONSOLA);
        txtHistorial.setBackground(Color.white);
        JScrollPane scrl = new JScrollPane(txtHistorial);
        scrl.setPreferredSize(new Dimension(0, 200));
        pnlContenido.add(scrl, BorderLayout.CENTER);

        // Panel Inferior: Entrada de Texto y Envío
        JPanel pnlInferior = new JPanel(new BorderLayout());
        txtMensaje = new JTextField();
        btnEnviar = new JButton("Enviar");
        //btnEnviar.setEnabled(false);

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
        String m = txtMensaje.getText().trim();
        if (m.length()>0) {
            String msj = Mensajes.componerMensaje(Mensajes.COMANDO_RED, Mensajes.SUBR_MENSAJE, m);
            placa.enviarComando(msj);
            txtHistorial.append("[" + placa.getUsuario().getNombre() + "]:" +  m + "\n");
            txtMensaje.setText("");
        }
        
    }

   // -------

    @Override
    public void onBoardIdRecibido(String id) {
        OyenteMensajes.super.onBoardIdRecibido(id); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void onGrupoRadioCambiado(int grupo) {
        OyenteMensajes.super.onGrupoRadioCambiado(grupo); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void onMensajeGenerico(String comando, String subcomando, String[] parametros) {
        
    }

    @Override
    public void onMensajePlaca(String titulo, String texto, boolean esError) {
        OyenteMensajes.super.onMensajePlaca(titulo, texto, esError); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
   // ------- 
    private void agregarNombreUsuario(){
        lblUsuario.setText(placa.getUsuario().getNombre());
    }
    
    private void agregarIdPlaca(){
        lblEstado.setText("Placa: " + placa.getId());
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Chat::new);
    }
}
