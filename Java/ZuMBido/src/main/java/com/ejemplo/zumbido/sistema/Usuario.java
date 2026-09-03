package com.ejemplo.zumbido.sistema;

public class Usuario {
    private String nombre;
    private String idPlaca;
    private int ultimoMsg = 0;

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
        return "Usuario:" + nombre + " ,en " + idPlaca;
    }

    /**
     * @return la marca de tiempo del último mensaje recibido
     */
    public int getUltimoMsg() {
        return ultimoMsg;
    }

    /**
     * @param ultimoMsg the ultimoMsg to set
     */
    public void setUltimoMsg(int ultimoMsg) {
        this.ultimoMsg = ultimoMsg;
    }
}
