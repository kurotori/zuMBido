/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.interfaz;

import com.formdev.flatlaf.FlatLightLaf;
import com.ejemplo.zumbido.Colores;
import com.ejemplo.zumbido.Fuentes;
import com.ejemplo.zumbido.Placa;
import com.ejemplo.zumbido.Textos;
import com.ejemplo.zumbido.sistema.Mensajes;
import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author sebastian
 */
public class InicioBase extends VentanaSerial {

    private JPanel pnlPlacas;
    //private JPanel pnlGrupoRadio;
    private JPanel pnlDatosUsuario;

    private JComboBox<String> cmbListaPlacas;
    private BotonImagenChico btnConectarPlaca;
    private BotonImagenChico btnActualizarListaPlacas;
    

    private JComboBox<String> cmbGruposRadio;
    
    private JTextField txtNombreUsuario;
    private BotonImagenChico btnIniciarLogin;

    private JLabel lblIdPlaca;

    private Placa placa;// = new Placa();

    GridBagConstraints gbc = new GridBagConstraints();

    private Fuentes fuentes = new Fuentes();
    
    public enum ResultadoEspera {
        TIMEOUT,
        LOGIN_OK,
        NOMBRE_REPETIDO
    }

    private JDialog dialogoEspera;
    private ResultadoEspera resultadoEspera;
    private Timer temporizadorEspera;
    
    
    /**
     * 
     */
    public InicioBase() {
        configurar();
        configurarFunciones();
        setVisible(true);
    }

    /**
     * Configura los componentes de la ventana
     */
    private void configurar() {
        setSize(640, 360);
        
        ImageIcon img = new ImageIcon(getClass().getResource("/imagen/icono.png"));
        
        setIconImage(img.getImage());

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
        pnlPlacas.setPreferredSize(new Dimension(0, 150));
        pnlPlacas.setBackground(Color.white);
        pnlPlacas.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colores.COLOR_GRIS_CLARO));
        add(pnlPlacas, BorderLayout.NORTH);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        LabelConImagen lblMicrobit = new LabelConImagen(48, 38, "/imagen/microbit.png");
        pnlPlacas.add(lblMicrobit, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbListaPlacas = new JComboBox<>();
        cmbListaPlacas.setPreferredSize(new Dimension(300, 40));
        
        cmbListaPlacas.setFont(fuentes.VENTANA_NORMAL_A_CH);

        pnlPlacas.add(cmbListaPlacas, gbc);

        gbc.gridx = 2;
        btnConectarPlaca = new BotonImagenChico(null, "/imagen/conectar.png", 32, 32);

        pnlPlacas.add(btnConectarPlaca, gbc);

        gbc.gridx = 3;
        btnActualizarListaPlacas = new BotonImagenChico(null, "/imagen/actualizar.png", 32, 32);
        pnlPlacas.add(btnActualizarListaPlacas, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lblEtIdPlaca = new JLabel(Textos.INICIO_ET_ID_PLACA);
        lblEtIdPlaca.setFont(fuentes.VENTANA_NEGRITA_A_CH);
        pnlPlacas.add(lblEtIdPlaca, gbc);

        lblIdPlaca = new JLabel("---");
        lblIdPlaca.setFont(fuentes.VENTANA_NORMAL_A_CH);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        pnlPlacas.add(lblIdPlaca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblEtGrupoRadial = new JLabel(Textos.INICIO_ET_GRUPO_RADIO);
        lblEtGrupoRadial.setFont(fuentes.VENTANA_NEGRITA_A);

        pnlPlacas.add(lblEtGrupoRadial, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbGruposRadio = new JComboBox<>();
        cmbGruposRadio.setPreferredSize(new Dimension(300, 40));
        cmbGruposRadio.setFont(fuentes.VENTANA_NORMAL_A_CH);
        cmbGruposRadio.setEnabled(false);

        //String[] canales = new String[256];
        for (int i = 0; i < 256; i++) {
            cmbGruposRadio.addItem("Grupo " + i);
        }

        pnlPlacas.add(cmbGruposRadio, gbc);

        
        //Panel de datos del usuario
        pnlDatosUsuario = new JPanel(new GridBagLayout());
        pnlDatosUsuario.setPreferredSize(new Dimension(0, 220));
        pnlDatosUsuario.setBackground(Color.white);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        JLabel lblNombreUsuario = new JLabel(Textos.INICIO_ET_NOMBRE_USUARIO);
        lblNombreUsuario.setFont(fuentes.VENTANA_NEGRITA_A_CH);
        pnlDatosUsuario.add(lblNombreUsuario,gbc);
        
        gbc.gridx = 1;
        txtNombreUsuario = new JTextField(18);
        txtNombreUsuario.setFont(fuentes.VENTANA_NORMAL_A_CH);
        pnlDatosUsuario.add(txtNombreUsuario,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        btnIniciarLogin = new BotonImagenChico("Entrar a la red", "/imagen/login.png", 32, 32);
        pnlDatosUsuario.add(btnIniciarLogin,gbc);
        
        add(pnlDatosUsuario, BorderLayout.SOUTH);
    }

    /**
     * Configurar funciones de los componentes de la ventana
     */
    private void configurarFunciones() {
        
        btnActualizarListaPlacas.addActionListener(e -> cargarPuertosDisponibles());
        cargarPuertosDisponibles();
        
        btnConectarPlaca.addActionListener(e -> conectarAPlaca());
        
        cmbGruposRadio.addActionListener(e -> cambiarGrupoRadial());
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
     * Permite conectarse a la placa seleccionada
     */
    private void conectarAPlaca() {
        String puerto = (String) cmbListaPlacas.getSelectedItem();
        placa = new Placa(this, SerialPort.getCommPort(puerto));

    }

    /**
     * Cambia el grupo radial
     */
    private void cambiarGrupoRadial() {
        int grupo = cmbGruposRadio.getSelectedIndex();
        placa.enviarComando(Mensajes.GRUPO_RADIO + ":" + grupo);
    }
    
    
    
    
    /**
     * Evalúa los mensajes recibidos en esta ventana
     *
     * @param mensaje mensaje a evaluar
     */
    @Override
    public void evaluarMensaje(String mensaje) {
        
        // --- Intercepción para cerrar el diálogo de espera ---
        if (dialogoEspera != null && dialogoEspera.isVisible()) {
            if (mensaje.contains("login_ok")) { // Ejemplo Señal A
                resultadoEspera = ResultadoEspera.LOGIN_OK;
                dialogoEspera.dispose(); // Cierra el diálogo e interrumpe la espera
                return;
            } else if (mensaje.contains("nombre_repetido")) { // Ejemplo Señal B
                resultadoEspera = ResultadoEspera.NOMBRE_REPETIDO;
                dialogoEspera.dispose(); // Cierra el diálogo e interrumpe la espera
                return;
            }
        }
        
        
        String[] cadena = mensaje.split(":");

        switch (cadena[0]) {

            case Mensajes.RECIBIDO:
                System.out.println("La placa dice->> " + mensaje);
                break;
            
            case Mensajes.MENSAJE:
                
                switch (cadena[1]) {
                    case Mensajes.MENSAJE_PLACA:
                        JOptionPane.showMessageDialog(this, cadena[2],placa.getId() + " dice:", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        throw new AssertionError();
                }
                
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
                        if (cadena.length > 2) {
                            int gr = Integer.parseInt(cadena[2]);
                            placa.setGrupoRadial(gr);
                            cmbGruposRadio.setEnabled(true);
                            cmbGruposRadio.setSelectedIndex(gr);
                        }
                        //int grupo = elegirGrupoRadio();
                        //enviarComando("gr:"+grupo);
                        break;
                        
                    case Mensajes.KEEEP_ALIVE:
                        placa.enviarComando(Mensajes.KEEEP_ALIVE);
                        break;
                    default:
                        System.out.println("SubComando no conocido: " + cadena[1]);
                    //throw new AssertionError();
                }

                break;
            default:
                System.out.println("ERROR: Mensaje desconocido: " + mensaje);
            //throw new AssertionError();
        }
    }

    /**
     * Muestra un diálogo para la selección de grupo radial
     *
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

    
    /**
     * Muestra un diálogo de espera durante 3 segundos o hasta recibir 'senalA' o 'senalB'.
     *
     * @param senalA Primera señal esperada (ej. "login_ok")
     * @param senalB Segunda señal esperada (ej. "nombre_repetido")
     * @return ResultadoEspera indicando qué ocurrió (TIMEOUT, SENAL_A, SENAL_B)
     */
    public ResultadoEspera esperarSenalPlaca(String senalA, String senalB) {
        // 1. Estado por defecto si expira el tiempo
        resultadoEspera = ResultadoEspera.TIMEOUT;

        // 2. Crear el JDialog de espera
        dialogoEspera = new JDialog(this, "Conectando...", true); // true = Modal
        dialogoEspera.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Bloquea el botón 'X'
        dialogoEspera.setLayout(new BorderLayout(10, 10));
        
        // Panel interno con texto y barra de progreso
        JPanel pnlContenido = new JPanel(new GridLayout(2, 1, 5, 5));
        pnlContenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lblMensaje = new JLabel("Iniciando sesión en la red...", JLabel.CENTER);
        JProgressBar barraProgreso = new JProgressBar();
        barraProgreso.setIndeterminate(true); // Animación de espera

        pnlContenido.add(lblMensaje);
        pnlContenido.add(barraProgreso);
        
        dialogoEspera.add(pnlContenido, BorderLayout.CENTER);
        dialogoEspera.pack();
        dialogoEspera.setLocationRelativeTo(this); // Centrar respecto a la ventana principal

        // 3. Crear el temporizador de 3000 ms (3 segundos)
        temporizadorEspera = new Timer(3000, e -> {
            resultadoEspera = ResultadoEspera.TIMEOUT;
            if (dialogoEspera != null && dialogoEspera.isVisible()) {
                dialogoEspera.dispose(); // Cierra el diálogo al agotar tiempo
            }
        });
        temporizadorEspera.setRepeats(false); // Ejecutar una sola vez
        temporizadorEspera.start();

        // 4. Mostrar el diálogo modal (Bucle de eventos bloqueante hasta que se llame a dispose())
        dialogoEspera.setVisible(true);

        // 5. Al cerrarse el diálogo, detener el timer si seguía activo
        if (temporizadorEspera.isRunning()) {
            temporizadorEspera.stop();
        }

        return resultadoEspera;
    }
    
    
    private void iniciarLogin(String nombreUsuario) {
        // Enviar orden por el puerto serie
        placa.enviarComando("nuevoLogin:Sebastian");

        // Iniciar la espera bloqueante de 3 segundos o respuesta
        ResultadoEspera res = esperarSenalPlaca("login_ok", "nombre_repetido");

        // Evaluar la resolución después de cerrar el diálogo
        switch (res) {
            case LOGIN_OK:
                JOptionPane.showMessageDialog(this, "¡Conexión exitosa!");
                break;
            case NOMBRE_REPETIDO:
                JOptionPane.showMessageDialog(this, "ERROR: El nombre ingresado ya existe en la red.", 
                                              "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case TIMEOUT:
                JOptionPane.showMessageDialog(this, "No se recibió respuesta de la placa (Tiempo agotado).", 
                                              "Timeout", JOptionPane.WARNING_MESSAGE);
                break;
        }
    }
    
    /**
     * Inicia la ventana
     * @param args 
     */
    public static void main(String[] args) {
        FlatLightLaf.setup();

        SwingUtilities.invokeLater(InicioBase::new);
    }
}
