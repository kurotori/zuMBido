/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido;

import com.ejemplo.zumbido.interfaz.InicioBase;
import com.ejemplo.zumbido.interfaz.VentanaSerial;
import com.ejemplo.zumbido.sistema.Mensajes;
import com.ejemplo.zumbido.sistema.ProcesadorMensajes;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.SwingUtilities;

/**
 *
 * @author sebastian
 */
public class Placa {

    private ProcesadorMensajes procesador;
    
    public static final int ACTIVA = 0;
    public static final int INACTIVA = 1;

    private VentanaSerial ventana;

    private String id;
    private int grupoRadial;
    private int estado;

    private SerialPort puerto;

    private OutputStream salidaSerie = null;

    private Usuario usuario = null;
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    public Placa(VentanaSerial ventana, SerialPort puerto) {
        this.ventana = ventana;
        this.id = null;
        this.grupoRadial = 0;
        this.estado = INACTIVA;
        this.puerto = puerto;
        conectarPuerto();
    }
    
    public Placa(SerialPort puerto) {
        this.procesador = new ProcesadorMensajes(this);
        this.id = null;
        this.grupoRadial = 0;
        this.estado = INACTIVA;
        this.puerto = puerto;
        conectarPuerto();
    }

    /**
     * Envía un comando específico a la placa
     *
     * @param texto
     */
    public void enviarComando(String texto) {
        texto = texto.trim();
        if (!texto.isEmpty() && salidaSerie != null) {
            try {
                // Se envía el texto con un salto de línea \n como delimitador
                salidaSerie.write((texto + "\n").getBytes(StandardCharsets.UTF_8));
                salidaSerie.flush();

                //txtHistorial.append(">> [PC]: " + texto + "\n");
                //txtMensaje.setText("");
            } catch (Exception ex) {
                System.err.println("Error al enviar mensaje: " + ex.getMessage());
                //txtHistorial.append("Error al enviar mensaje: " + ex.getMessage() + "\n");
            }
        }
    }

    /**
     * Conecta al sistema al puerto seleccionado
     */
    public void conectarPuerto() {
        //Se fuerza el cierre del puerto para asegurarlo
        if (puerto != null && puerto.isOpen()) {
            puerto.closePort();
        }

        //Obtenemos el puerto del combobox //-->CAMBIAR a selección externa
        String nombrePuerto = puerto.getSystemPortName();

        //Si no se selecciona nada, el método se cierra sin efectos
        if (nombrePuerto == null) {
            return;
        }

        //Inicialización del puerto
        puerto = SerialPort.getCommPort(nombrePuerto);
        puerto.setBaudRate(115200);

        //Se abre el puerto 
        if (puerto.openPort()) {

            //Inicializar la salida de datos hacia el puerto
            salidaSerie = puerto.getOutputStream();
            if (getUsuario() == null) {
                enviarComando( Mensajes.componerMensaje(Mensajes.COMANDO_SISTEMA, Mensajes.SUBC_CONECTAR_PLACA) );
            }

            // Iniciar la escucha asíncrona de datos entrantes desde la pasarela
            iniciarEscuchaSerie();
        } else {
            //txtHistorial.append
            System.err.println("Error al abrir el puerto " + nombrePuerto + "\n");
        }
    }

    /**
     * Inicia la escucha de respuestas desde la placa acumulando el buffer
     */
    private void iniciarEscuchaSerie() {
        puerto.addDataListener(new SerialPortDataListener() {
            // Buffer persistente para acumular fragmentos de texto
            private final StringBuilder bufferEntrada = new StringBuilder();

            @Override
            public int getListeningEvents() {
                // Cambiado a DATA_RECEIVED para obtener los bytes directamente
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
                    return;
                }

                // 1. Obtener los bytes recién recibidos
                byte[] bytesRecibidos = event.getReceivedData();
                String fragmento = new String(bytesRecibidos, StandardCharsets.UTF_8);

                // 2. Acumular en el buffer
                bufferEntrada.append(fragmento);

                // 3. Extraer todas las líneas completas (terminadas en \n)
                int indiceSalto;
                while ((indiceSalto = bufferEntrada.indexOf("\n")) != -1) {
                    String lineaCompleta = bufferEntrada.substring(0, indiceSalto).trim();

                    // Remover la línea ya procesada del buffer
                    bufferEntrada.delete(0, indiceSalto + 1);

                    if (!lineaCompleta.isEmpty()) {
                        // Enviar la línea completa a la interfaz gráfica
                        SwingUtilities.invokeLater(() -> {
                            System.out.println("[Placa]: " + lineaCompleta);
                            //getVentana().evaluarMensaje(lineaCompleta);
                            procesador.analizarMensaje(lineaCompleta);
                        });
                    }
                }
            }
        });
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the grupoRadial
     */
    public int getGrupoRadial() {
        return grupoRadial;
    }

    /**
     * @param grupoRadial the grupoRadial to set
     */
    public void setGrupoRadial(int grupoRadial) {
        this.grupoRadial = grupoRadial;
    }

    /**
     * @return the puerto
     */
    public SerialPort getPuerto() {
        return puerto;
    }

    /**
     * @param puerto the puerto to set
     */
    public void setPuerto(SerialPort puerto) {
        this.puerto = puerto;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the ventana
     */
    public VentanaSerial getVentana() {
        return ventana;
    }

    /**
     * @param ventana the ventana to set
     */
    public void setVentana(VentanaSerial ventana) {
        this.ventana = ventana;
    }

    /**
     * @return the procesador
     */
    public ProcesadorMensajes getProcesador() {
        return procesador;
    }

    /**
     * @param procesador the procesador to set
     */
    public void setProcesador(ProcesadorMensajes procesador) {
        this.procesador = procesador;
    }
}
