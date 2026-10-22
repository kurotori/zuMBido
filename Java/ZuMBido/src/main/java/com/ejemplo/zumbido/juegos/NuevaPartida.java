/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.juegos;

import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author sebastian
 */
public class NuevaPartida extends JFrame{

    public NuevaPartida() throws HeadlessException {
        configurarVentana();
        configurarFunciones();
    }
    
    
    private void configurarVentana(){
        setSize(400,600);
        setLocationRelativeTo(null);
    }
    
    private void configurarFunciones(){
        
    }
    
}
