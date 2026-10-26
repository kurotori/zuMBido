/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.sistema;

import java.time.Instant;
import java.util.ArrayList;


/**
 *
 * @author sebastian
 */
public class Partida {
    private Usuario propietario;
    //private UUID id;
    private Instant marcaTiempo;
    private boolean propia =false;
    private ArrayList<Usuario> jugadores = new ArrayList<>();

    public Partida(Usuario propietario, boolean propia) {
        this.propietario = propietario;
        this.propia = propia;
        this.propietario.setPartida(this);
        this.jugadores.addFirst(propietario);
        this.marcaTiempo = Instant.now();
    }

    /**
     * @return the propietario
     */
    public Usuario getPropietario() {
        return propietario;
    }


    /**
     *
     * @return
     */
    public Instant getMarcaTiempo() {
        return marcaTiempo;
    }

    public boolean isPropia() {
        return propia;
    }
}

