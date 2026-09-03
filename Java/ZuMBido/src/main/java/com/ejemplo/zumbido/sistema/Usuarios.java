/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.sistema;

import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author sebastian
 */
public class Usuarios {
    
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    
    /**
     * Permite buscar un usuario en el listado mediante la id de su placa
     * @param idPlaca la id de placa del usuario que se busca
     * @return el <b>Usuario</b> encontrado, o null
     */
    public Usuario buscarPorId(String idPlaca){
        Usuario resultado = null;
        Optional<Usuario> busqueda = usuarios.stream()
                .filter(usuario -> usuario.getIdPlaca().equals(idPlaca))
                .findFirst();
        
        if(busqueda.isPresent()){
            return busqueda.get();
        }
        
        return resultado;
    }
    
    /**
     * Permite buscar un usuario en el listado mediante su nombre 
     * @param nombre el nombre del usuario que se busca
     * @return el <b>Usuario</b> encontrado, o null
     */
    public Usuario buscarPorNombre(String nombre){
        Usuario resultado = null;
        Optional<Usuario> busqueda = usuarios.stream()
                .filter(usuario -> usuario.getNombre().equals(nombre))
                .findFirst();
        
        if(busqueda.isPresent()){
            return busqueda.get();
        }
        
        return resultado;
    }
    
    public void agregarUsuario(Usuario nuevo){
        this.usuarios.add(nuevo);
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return usuarios;
    }
    
    
}
