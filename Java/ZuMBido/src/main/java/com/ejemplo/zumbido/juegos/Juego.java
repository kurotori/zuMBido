package com.ejemplo.zumbido.juegos;

public abstract class Juego {
    private String nombre;
    private String identificador;
    private int limiteJugadores;

    public Juego(String nombre, String identificador, int limiteJugadores) {
        this.nombre = nombre;
        this.limiteJugadores = limiteJugadores;
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public int getLimiteJugadores() {
        return limiteJugadores;
    }


}
