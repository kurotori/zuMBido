/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.sistema;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author sebastian
 */
public class GestorKeepAlive {
    
    private ScheduledExecutorService planificador;
    private final Placa placa;

    public GestorKeepAlive(Placa placa) {
        this.placa = placa;
    }
    
    
    public void iniciar(){
        detener();
        
        planificador = Executors.newSingleThreadScheduledExecutor();
        
        planificador.scheduleAtFixedRate(
                ()->{
                    try {
                        if (placa != null) {
                            String msj = Mensajes.componerMensaje
                                (Mensajes.COMANDO_SISTEMA, Mensajes.SUBC_KEEP_ALIVE);
                            placa.enviarComando(msj);
                        }
                    } catch (Exception e) {
                    }
                }, 0, 5000, TimeUnit.MILLISECONDS);
        
    }
    
    public void detener() {
        if (planificador != null && !planificador.isShutdown()) {
            planificador.shutdownNow();
        }
    }
    
}
