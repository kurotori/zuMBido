/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.sistema;

/**
 *
 * @author sebastian
 */
public class Registro {
    
    public static void registrarEvento(Object objeto, String evento){
        long tiempo = System.currentTimeMillis();
        System.out.println( "["+tiempo+"]["+ objeto.getClass().getName()+"]: "+ evento);
    }
    
}
