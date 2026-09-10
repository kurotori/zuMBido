/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.interfaz;

import java.awt.Color;

/**
 *
 * @author sebastian
 */
public class Colores {

    public static final Color COLOR_GRIS_CLARO = Color.decode("#CFCFCF");

    //*/ Funciones basadas en el trabajo de Adam Cole en StackOverflow: https://stackoverflow.com/a/7419630
    public static Color crearColorArcoiris(int numOfSteps, int step) {
        float hue = (float) step / numOfSteps;
        return Color.getHSBColor(hue, 1.0f, 1.0f); // Tono (hue), Saturación 100%, Brillo 100%
    }

    public static String getColorHex(int numOfSteps, int step) {
        Color c = crearColorArcoiris(numOfSteps, step);
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }
    //*/
    
    public static String extraerColorDeId(String idPlaca){
        String color = idPlaca.substring(0, 6);
        return "#"+color;
    }
    
    /**
     * Evalúa si un color de fondo es claro utilizando la luminancia percibida YIQ.
     */
    public static boolean esColorClaro(Color color) {
        if (color == null) return true;
        
        double luminancia = (color.getRed() * 299 
                           + color.getGreen() * 587 
                           + color.getBlue() * 114) / 1000.0;
                           
        return luminancia >= 128; // Umbral estándar
    }

    /**
     * Retorna Color.BLACK para fondos claros o Color.WHITE para fondos oscuros.
     */
    public static Color obtenerColorTexto(Color colorFondo) {
        return esColorClaro(colorFondo) ? Color.BLACK : Color.WHITE;
    }

    /**
     * Sobrecarga para cadenas hexadecimales (ej: "#98F1A5" o "#202655").
     */
    public static String obtenerColorTextoHex(String hexFondo) {
        try {
            Color color = Color.decode(hexFondo);
            return esColorClaro(color) ? "#000000" : "#FFFFFF";
        } catch (NumberFormatException e) {
            return "#000000"; // Fallback por defecto
        }
    }
}
