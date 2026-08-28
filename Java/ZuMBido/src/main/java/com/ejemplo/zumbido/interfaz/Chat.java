/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.interfaz;

import com.ejemplo.zumbido.Placa;

/**
 *
 * @author sebastian
 */
public class Chat extends VentanaSerial{
    
    private Placa placa;

    public Chat(Placa placa) {
        this.placa = placa;
    }
    
    

    @Override
    public void evaluarMensaje(String mensaje) {
        
    }
    
}
