package com.ejemplo.zumbido.interfaz;

import com.ejemplo.zumbido.Fuentes;
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

public class InicioGateway extends JFrame {

    private JComboBox<String> cmbListaPuertos;
    private JTextArea txtHistorial;
    private JTextField txtMensaje;
    private JButton btnEnviar;
    private JPanel pnlContenido;
    private JPanel pnlEstado;
    private JLabel lblEstado;

    Fuentes fuentes = new Fuentes();

    private SerialPort puertoActivo = null;
    private OutputStream salidaSerie = null;

    private Usuario usuario = null;
    private String idPlaca = "";

    public InicioGateway() {
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
        cmbListaPuertos = new JComboBox<>();
        cmbListaPuertos.setFont(fuentes.VENTANA_NORMAL_A_CH);
        cargarPuertosDisponibles();

        JButton btnConectar = new JButton("Conectar");
        btnConectar.setFont(fuentes.VENTANA_NEGRITA_A);
        btnConectar.addActionListener(e -> conectarPuerto());

        JLabel lblP = new JLabel("Puerto:");
        lblP.setFont(fuentes.VENTANA_NEGRITA_A);
        pnlSuperior.add(lblP);

        pnlSuperior.add(cmbListaPuertos);
        pnlSuperior.add(btnConectar);
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

        btnEnviar.addActionListener(e -> enviarComando());
        txtMensaje.addActionListener(e -> enviarComando()); // Enviar con Enter

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

    /**
     * Carga los puertos seriales detectados en el combobox
     */
    private void cargarPuertosDisponibles() {
        SerialPort[] puertos = SerialPort.getCommPorts();
        for (SerialPort p : puertos) {
            cmbListaPuertos.addItem(p.getSystemPortName());
        }
    }

    /**
     * Conecta al sistema al puerto seleccionado
     */
    private void conectarPuerto() {
        //Se fuerza el cierre del puerto para asegurarlo
        if (puertoActivo != null && puertoActivo.isOpen()) {
            puertoActivo.closePort();
        }

        //Obtenemos el puerto del combobox //-->CAMBIAR a selección externa
        String nombrePuerto = (String) cmbListaPuertos.getSelectedItem();

        //Si no se selecciona nada, el método se cierra sin efectos
        if (nombrePuerto == null) {
            return;
        }

        //Inicialización del puerto
        puertoActivo = SerialPort.getCommPort(nombrePuerto);
        puertoActivo.setBaudRate(115200);

        //Se abre el puerto 
        if (puertoActivo.openPort()) {
            txtHistorial.append("Conectado con éxito a " + nombrePuerto + "\n");

            //Inicializar la salida de datos hascia el puerto
            salidaSerie = puertoActivo.getOutputStream();
            if (usuario == null) {
                enviarComando("c:c");
            }

            btnEnviar.setEnabled(true);

            // Iniciar la escucha asíncrona de datos entrantes desde la pasarela
            iniciarEscuchaSerie();
        } else {
            txtHistorial.append("Error al abrir el puerto " + nombrePuerto + "\n");
        }
    }

    /**
     * Inicia la escucha de respuestas desde la placa
     */
    private void iniciarEscuchaSerie() {
        puertoActivo.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;
                }

                // Leer líneas completas desde el puerto serie
                Scanner scanner = new Scanner(puertoActivo.getInputStream(), "UTF-8");
                while (scanner.hasNextLine()) {
                    String lineaRecibida = scanner.nextLine();

                    // Actualizar la GUI desde el hilo de eventos de Swing
                    SwingUtilities.invokeLater(
                            () -> {
                                txtHistorial.append("<< [Radio]: " + lineaRecibida + "\n");
                                evaluarMensaje(lineaRecibida);
                            }
                    );
                }
            }
        });
    }

    /**
     * Envía el comando desde la consola
     */
    private void enviarComando() {
        String texto = txtMensaje.getText().trim();
        if (!texto.isEmpty() && salidaSerie != null) {
            try {
                // Se envía el texto con un salto de línea \n como delimitador
                salidaSerie.write((texto + "\n").getBytes(StandardCharsets.UTF_8));
                salidaSerie.flush();

                txtHistorial.append(">> [PC]: " + texto + "\n");
                txtMensaje.setText("");
            } catch (Exception ex) {
                txtHistorial.append("Error al enviar mensaje: " + ex.getMessage() + "\n");
            }
        }
    }

    /**
     * Envía un comando específico a la placa
     *
     * @param texto
     */
    private void enviarComando(String texto) {
        texto = texto.trim();
        if (!texto.isEmpty() && salidaSerie != null) {
            try {
                // Se envía el texto con un salto de línea \n como delimitador
                salidaSerie.write((texto + "\n").getBytes(StandardCharsets.UTF_8));
                salidaSerie.flush();

                txtHistorial.append(">> [PC]: " + texto + "\n");
                txtMensaje.setText("");
            } catch (Exception ex) {
                txtHistorial.append("Error al enviar mensaje: " + ex.getMessage() + "\n");
            }
        }
    }

    private void evaluarMensaje(String mensaje) {
        String[] cadena = mensaje.split(":");

        switch (cadena[0]) {

            case Mensajes.RECIBIDO:
                System.out.println("La placa dice: " + mensaje);
                break;

            case Mensajes.COMANDO:

                switch (cadena[1]) {

                    case Mensajes.BOARD_ID:
                        idPlaca = cadena[2];
                        lblEstado.setText(lblEstado.getText() + idPlaca);
                        break;

                    case Mensajes.GRUPO_RADIO:
                        int grupo = elegirGrupoRadio();
                        enviarComando("gr:"+grupo);
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
        SwingUtilities.invokeLater(InicioGateway::new);
    }
}
