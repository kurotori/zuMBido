/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido;

import com.ejemplo.zumbido.interfaz.InicioBase;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.swing.SwingUtilities;

/**
 *
 * @author sebastian
 */
public class Placa {
    
    public static final int ACTIVA = 0;
    public static final int INACTIVA = 1;
    
    private InicioBase ventana;
    
    private String id;
    private int grupoRadial;
    private int estado;
    
    private SerialPort puerto;
    
    private OutputStream salidaSerie = null;
    
    private Usuario usuario = null;

    public Placa(InicioBase ventana, SerialPort puerto) {
        this.ventana = ventana;
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
    private void enviarComando(String texto) {
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
            //txtHistorial.append("Conectado con éxito a " + nombrePuerto + "\n");

            //Inicializar la salida de datos hacia el puerto
            salidaSerie = puerto.getOutputStream();
            if (usuario == null) {
                enviarComando("c:c");
            }

            //btnEnviar.setEnabled(true);

            // Iniciar la escucha asíncrona de datos entrantes desde la pasarela
            iniciarEscuchaSerie();
        } else {
            //txtHistorial.append
            System.err.println("Error al abrir el puerto " + nombrePuerto + "\n");
        }
    }
    
    
        /**
     * Inicia la escucha de respuestas desde la placa
     */
    private void iniciarEscuchaSerie() {
        puerto.addDataListener(new SerialPortDataListener() {
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
                Scanner scanner = new Scanner(puerto.getInputStream(), "UTF-8");
                while (scanner.hasNextLine()) {
                    String lineaRecibida = scanner.nextLine();

                    // Actualizar la GUI desde el hilo de eventos de Swing
                    SwingUtilities.invokeLater(
                            () -> {
                                //txtHistorial.append
                                System.out.println("<< [Radio]: " + lineaRecibida + "\n");
                                ventana.evaluarMensaje(lineaRecibida);
                            }
                    );
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
}
