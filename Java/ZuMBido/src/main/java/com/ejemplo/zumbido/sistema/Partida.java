/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.sistema;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author sebastian
 */
public class Partida {
    private Usuario propietario;
    private UUID id;
    
    private ArrayList<Usuario> jugadores = new ArrayList<>();

    public Partida(Usuario propietario) {
        this.propietario = propietario;
        this.propietario.setPartida(this);
        this.jugadores.addFirst(propietario);
        this.id = UUID.randomUUID();
    }

    /**
     * @return the propietario
     */
    public Usuario getPropietario() {
        return propietario;
    }

    /**
     * @return the marcaTiempo
     */
    public UUID getId() {
        return id;
    }
    
    
    
    
}
