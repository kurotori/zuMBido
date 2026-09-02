package com.ejemplo.zumbido.sistema;

public class Usuario {
    private String nombre;
    private String idPlaca;

    public Usuario(String nombre, String idPlaca) {
        this.nombre = nombre;
        this.idPlaca = idPlaca;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdPlaca() {
        return idPlaca;
    }

    public String getDatosCompletos(){
        return "Usuario:";
    }
}
