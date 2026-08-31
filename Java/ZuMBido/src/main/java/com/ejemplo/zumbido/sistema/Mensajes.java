/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.sistema;

/**
 *
 * @author sebastian
 */
public class Mensajes {
    
    // 1 - Comandos Base
    
    public static final String COMANDO_ERROR = "e";
    
    public static final String COMANDO_SISTEMA = "c";
    
    public static final String COMANDO_RED = "r";
    
    // 1.1 - Mensajes de la Placa
    public static final String PLACA_RECIBIDO = "recibido";
    public static final String PLACA_MENSAJE = "m";
    
    public static final String SUBPL_MENSAJE_PLACA = "b";
    public static final String SUBPL_MENSAJE_ERROR = "err";
    
    // 2 - Sub comandos
    public static final String SUBC_CONECTAR_PLACA = "c";
    public static final String SUBC_BOARD_ID = "bid";
    public static final String SUBC_GRUPO_RADIO = "gr";
    public static final String SUBC_KEEEP_ALIVE = "ka";
    
    
    //3 - Sub Comandos de Red
    public static final String SUBR_NOMBRE_REPETIDO = "nr";
    public static final String SUBR_NUEVO_LOGIN = "nl";
    public static final String SUBR_MENSAJE = "m";
    
    
    /**
     * Compone un mensaje para su uso en el sistema
     * @param comando
     * @param subcomando
     * @param dato
     * @return 
     */
    public static String componerMensaje(String comando, String subcomando){
        String mensaje = comando + ":" + subcomando;
        return mensaje;
    }
    
    
    
    /**
     * Compone un mensaje para su uso en el sistema
     * @param comando Comando principal del mensaje
     * @param subcomando Subcomando
     * @param datos datos Un array de datos a concatenar
     * @return 
     */
    public static String componerMensaje(String comando, String subcomando, String[] datos){
        String mensaje = comando + ":" + subcomando;
        
        if (datos!=null && datos.length > 0) {
            for (String dato : datos) {
                mensaje += (":" + dato);
            }
        }
        
        return mensaje;
    }
    
    /**
     * Compone un mensaje para su uso en el sistema
     * @param comando
     * @param subcomando
     * @param dato
     * @return 
     */
    public static String componerMensaje(String comando, String subcomando, String dato){
        String mensaje = comando + ":" + subcomando + ":" + dato;
        return mensaje;
    }
    
}
