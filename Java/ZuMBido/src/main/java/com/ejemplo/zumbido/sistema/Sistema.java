/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.sistema;

import java.util.ArrayList;

/**
 *
 * @author sebastian
 */
public class Sistema {
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    
    public void agregarUsuario(Usuario usuario){
        usuarios.add(usuario);
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
    
    
}
