/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.interfaz;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author sebastian
 */
public class BotonImagenChico extends JButton {
    
    private int ancho;
    private int alto;
    
    public BotonImagenChico(String texto, String rutaImg, int alto, int ancho ) {
        
        this.alto = alto;
        this.ancho = ancho;
        super(texto);
        
        ImageIcon img = new ImageIcon(getClass().getResource(rutaImg));
        Image i = img.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        img = new ImageIcon(i);
        this.setIcon(img);
    }
}
