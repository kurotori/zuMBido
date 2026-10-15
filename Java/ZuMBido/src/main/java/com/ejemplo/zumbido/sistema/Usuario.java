package com.ejemplo.zumbido.sistema;

import com.ejemplo.zumbido.interfaz.Colores;

public class Usuario {

    private String nombre;
    private String idPlaca;
    private long ultimoMsg = 0;
    private boolean activo = true;
    
    private Estado estado = Estado.ACTIVO;
    
    public enum Estado {
      ACTIVO,
      INACTIVO,
      DESCONECTADO
    }

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

    public String getDatosCompletos() {
        return "Usuario:" + nombre + " ,en " + idPlaca;
    }

    /**
     * @return la marca de tiempo del último mensaje recibido
     */
    public long getTiempoUltimoMsg() {
        return ultimoMsg;
    }

    /**
     * @param ultimoMsg the ultimoMsg to set
     */
    public void setTiempoUltimoMsg(long ultimoMsg) {
        this.ultimoMsg = ultimoMsg;
    }
    
    

    public String colorFondo() {
        return Colores.extraerColorDeId(idPlaca);
    }
    
    public String colorTexto(){
        return Colores.obtenerColorTextoHex(colorFondo());
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return estado == Estado.ACTIVO;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public void setEstado(Estado estado){
        this.estado = estado;
    }

    public Estado getEstado() {
        return estado;
    }
    
    

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
