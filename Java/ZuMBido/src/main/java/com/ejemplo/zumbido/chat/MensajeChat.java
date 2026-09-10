/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import com.ejemplo.zumbido.sistema.Usuario;
import javax.swing.JPanel;

/**
 *
 * @author sebastian
 */
public class MensajeChat extends JPanel{
    private String mensaje;
    private Usuario usuario;

    public MensajeChat(String mensaje, Usuario usuario) {
        this.mensaje = mensaje;
        this.usuario = usuario;
    }
    
    private void configurar(){
        
    }
}
