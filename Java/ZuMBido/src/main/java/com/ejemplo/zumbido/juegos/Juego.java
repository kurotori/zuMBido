package com.ejemplo.zumbido.juegos;

public abstract class Juego {
    private String nombre;
    private int limiteJugadores;

    public Juego(String nombre, int limiteJugadores) {
        this.nombre = nombre;
        this.limiteJugadores = limiteJugadores;
    }

    public String getNombre() {
        return nombre;
    }

    public int getLimiteJugadores() {
        return limiteJugadores;
    }


}
