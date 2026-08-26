/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido;

import com.fazecast.jSerialComm.SerialPort;

/**
 *
 * @author sebastian
 */
public class Serial {
    /**
     * Carga los puertos seriales detectados en el combobox
     */
    public SerialPort[] listarPuertosDisponibles() {
        return SerialPort.getCommPorts();
    }
    
    
}
