/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.interfaz;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author sebastian
 */
public class Fuentes {
    
    public Font CONSOLA;
    public Font BARRA_ESTADO;
    public Font VENTANA_NORMAL_A;
    public Font VENTANA_NORMAL_A_CH;
    public Font VENTANA_NORMAL_B;
    public Font VENTANA_NORMAL_C;
    public Font VENTANA_NEGRITA_A;
    public Font VENTANA_NEGRITA_A_CH;
    public Font VENTANA_NEGRITA_B;
    public Font VENTANA_NEGRITA_C;

    public Fuentes() {
        this.CONSOLA = cargarFuente("/fuentes/Roboto_Mono/RobotoMono-VariableFont_wght.ttf", 18);
        
        this.VENTANA_NORMAL_A_CH = cargarFuente("/fuentes/Lekton/Lekton-Regular.ttf", 17);
        this.VENTANA_NORMAL_A = cargarFuente("/fuentes/Lekton/Lekton-Regular.ttf", 18);
        this.VENTANA_NORMAL_B = cargarFuente("/fuentes/Lekton/Lekton-Regular.ttf", 24);
        this.VENTANA_NORMAL_A = cargarFuente("/fuentes/Lekton/Lekton-Regular.ttf", 28);
        
        this.VENTANA_NEGRITA_A_CH = cargarFuente("/fuentes/Lekton/Lekton-Bold.ttf", 17);
        this.VENTANA_NEGRITA_A = cargarFuente("/fuentes/Lekton/Lekton-Bold.ttf", 18);
        this.VENTANA_NEGRITA_B = cargarFuente("/fuentes/Lekton/Lekton-Bold.ttf", 24);
        this.VENTANA_NEGRITA_C = cargarFuente("/fuentes/Lekton/Lekton-Bold.ttf", 28);
    }
    
    
    private Font cargarFuente(String ruta, float tamanio){
        Font fuente;
        try {
            InputStream archivoFuente = getClass().getResourceAsStream(ruta);
            if (archivoFuente == null) {
                throw new IOException("No se encontró el recurso '" + ruta + "'");
            }
            
            fuente = Font.createFont(Font.TRUETYPE_FONT, archivoFuente);
            fuente = fuente.deriveFont(tamanio);
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            fuente =  new Font("Arial", Font.PLAIN, (int)tamanio);
        }
        
        return fuente;
    }
    
}
