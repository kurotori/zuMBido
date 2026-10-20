/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author sebastian
 */
public class Partidas extends JFrame{

    public Partidas() throws HeadlessException {
        configurarVentana();
        configurarFunciones();
        
        setVisible(true);
    }
    
    private void configurarVentana(){
        setSize(300, 600);
        setTitle("Partidas");
    }
    
    
    private void configurarFunciones(){
        
    }
}
