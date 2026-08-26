/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.interfaz;

import java.awt.HeadlessException;
import javax.swing.JFrame;
import com.ejemplo.zumbido.Fuentes;
import com.ejemplo.zumbido.Usuario;
import com.ejemplo.zumbido.sistema.Mensajes;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author sebastian
 */
public class InicioBase extends JFrame {

    public InicioBase(){
    }
    
    private void configurar(){
        setSize(320,240);
    }
    
    
}
