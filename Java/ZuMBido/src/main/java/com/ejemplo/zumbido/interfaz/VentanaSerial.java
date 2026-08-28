/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.interfaz;

import javax.swing.JFrame;

/**
 *
 * @author sebastian
 */
public abstract class VentanaSerial extends JFrame{
    
     /**
     * Evalúa los mensajes recibidos en esta ventana
     *
     * @param mensaje mensaje a evaluar
     */
    public abstract void evaluarMensaje(String mensaje);
    
}
