/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.sistema;

/**
 *  Esta interfaz implementa métodos genéricos para que cada ventana los maneje según sea necesario
 * @author sebastian
 */
public interface OyenteMensajes {
    default void onBoardIdRecibido(String id) {}

    default void onGrupoRadioCambiado(int grupo) {}

    default void onNombreRepetido() {}

    default void onMensajePlaca(String titulo, String texto, boolean esError) {}

    default void onMensajeGenerico(String comando, String subcomando, String[] parametros) {}
}

