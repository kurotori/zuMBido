/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.sistema.gestores;

import com.ejemplo.zumbido.sistema.Placa;
import com.ejemplo.zumbido.sistema.Registro;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author sebastian
 */
public class GestorMensajeria {
    private ScheduledExecutorService planificador;
    private final Placa placa;
    private ArrayList<String> mensajes = new ArrayList<>();

    public GestorMensajeria(Placa placa) {
        this.placa = placa;
    }
    
    public void iniciar() {
        detener();

        planificador = Executors.newSingleThreadScheduledExecutor();

        planificador.scheduleAtFixedRate(
                () -> {
                    try {
                        if (! mensajes.isEmpty()) {
                            String msj = mensajes.removeFirst();
                            Registro.registrarEvento(this, "Enviando: " + msj);
                            System.out.println();
                            placa.enviarSerial(msj);
                        }
                    } catch (Exception e) {
                        Registro.registrarEvento(this, "Error: " + e.getMessage());
                        
                    }
                }, 0, 200, TimeUnit.MILLISECONDS);
    }

    public void detener() {
        if (planificador != null && !planificador.isShutdown()) {
            planificador.shutdownNow();
        }
    }
    
    /**
     * Agrega un mensaje a la cola de mensajes con la prioridad indicada
     * @param mensaje El mensaje a enviar.
     * @param prioritario Si es <b>true</b> el mensaje es prioritario y se agrega
     * al comienzo de la cola. Si es <b>false</b> el mensaje se agrega al final 
     * de la cola.
     */
    public void agregarMensaje(String mensaje, boolean prioritario){
        if (prioritario) {
            mensajes.addFirst(mensaje);
        }
        else{
            mensajes.add(mensaje);
            Registro.registrarEvento(this, "Mensaje común añadido:" + mensaje);
            System.out.println();
        }
        //System.out.println("[Mensajero]: Mensaje añadido. Hay " + mensajes.size() + " mensajes");
    }
}
