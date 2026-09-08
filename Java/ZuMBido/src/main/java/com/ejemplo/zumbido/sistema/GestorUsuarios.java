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
public class GestorUsuarios {

    private ScheduledExecutorService planificador;
    private final Placa placa;

    public GestorUsuarios(Placa placa) {
        this.placa = placa;
    }
    
    public void iniciar(){
        detener();
        
        planificador = Executors.newSingleThreadScheduledExecutor();
        
        planificador.scheduleAtFixedRate(
                ()->{
                    try {
                        if (placa != null) {
                            if (placa.getUsuarios().getCantUsuarios() > 0) {
                                for (Usuario usuario:placa.getUsuarios().getListaUsuarios()) {
                                    if (usuario.getTiempoUltimoMsg() > 5000) {
                                        placa.getUsuarios().quitarUsuario(usuario);
                                        //placa
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }, 0, 6000, TimeUnit.MILLISECONDS);
    }
    
    public void detener(){
        
    }
}
