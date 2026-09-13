/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.interfaz;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author sebastian
 */
public class Iconos {
    public ImageIcon ICONO_ZUMBIDO_64;
    public ImageIcon ICONO_ERROR_64;
    public ImageIcon ICONO_CHAT_64;
    public ImageIcon ICONO_ZUMBIDO_96;
    public ImageIcon ICONO_ERROR_96;
    public ImageIcon ICONO_CHAT_96;
    //public ImageIcon ICONO_ADVERTENCIA;
    //public ImageIcon ICONO_PREGUNTA;

    public Iconos() {
        ICONO_ZUMBIDO_64 = escalarImagen("/imagen/icono.png",64,64);
        ICONO_ZUMBIDO_96 = escalarImagen("/imagen/icono.png",96,96);
        ICONO_ERROR_64 = escalarImagen("/imagen/icono_error.png", 64, 64);
        ICONO_ERROR_96 = escalarImagen("/imagen/icono_error.png", 96, 96);
        ICONO_CHAT_64 = escalarImagen("/imagen/icono_chat.png", 64, 64);
        ICONO_CHAT_96 = escalarImagen("/imagen/icono_chat.png", 96,96);
    }
    
    /**
     * Redimensiona una imagen para su uso en las ventanas.
     * @param rutaImagen
     * @param ancho
     * @param alto
     * @return 
     */
    public ImageIcon escalarImagen(String rutaImagen, int ancho, int alto){
        ImageIcon img = new ImageIcon(getClass().getResource(rutaImagen));
        Image i = img.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(i);
    }
    
    
}
