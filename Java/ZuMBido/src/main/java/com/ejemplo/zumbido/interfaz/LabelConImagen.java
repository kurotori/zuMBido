/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.interfaz;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author sebastian
 */
public class LabelConImagen extends JLabel{
    private int ancho;
    private int alto;
    private String rutaImagen;

    public LabelConImagen(int ancho, int alto, String rutaImagen) {
        this.ancho = ancho;
        this.alto = alto;
        this.rutaImagen = rutaImagen;
        configurar();
    }
    
    private void configurar(){
        ImageIcon img = new ImageIcon(getClass().getResource(rutaImagen));
        Image i = img.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        img = new ImageIcon(i);
        //this.setIconTextGap(20);
        this.setIcon(img);
    }
    
    
}
