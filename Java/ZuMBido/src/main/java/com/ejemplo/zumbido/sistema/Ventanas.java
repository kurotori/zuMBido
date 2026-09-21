/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.sistema;

import javax.swing.JFrame;

/**
 *
 * @author sebastian
 */
public class Ventanas {
    
    public static void enfocarVentana(JFrame ventana) {
    if (ventana != null) {
        // 1. Si la ventana está minimizada en la barra de tareas, la restaura
        if ((ventana.getExtendedState() & JFrame.ICONIFIED) != 0) {
            ventana.setExtendedState(ventana.getExtendedState() & ~JFrame.ICONIFIED);
        }
        
        // 2. Asegurar que sea visible
        if (!ventana.isVisible()) {
            ventana.setVisible(true);
        }
        
        // 3. Traer al primer plano (Z-order)
        ventana.toFront();
        
        // 4. Pedir el foco para la ventana y su componente de entrada
        ventana.requestFocus();
    }
}
}
