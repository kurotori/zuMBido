/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.sistema.gestores;

import com.ejemplo.zumbido.sistema.Mensajes;
import com.ejemplo.zumbido.sistema.Placa;
import com.ejemplo.zumbido.sistema.Registro;
import com.ejemplo.zumbido.sistema.Usuario;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author sebastian
 */
public class GestorUsuarios {

    private ScheduledExecutorService planificador;
    private final Placa placa;

    public GestorUsuarios(Placa placa) {
        this.placa = placa;
    }

    public void iniciar() {
        detener();

        planificador = Executors.newSingleThreadScheduledExecutor();

        planificador.scheduleAtFixedRate(
                () -> {
                    try {
                        long tiempo = System.currentTimeMillis();
                        if (placa != null && placa.getUsuarios().getCantUsuarios() > 0) {
                            for (Usuario usuario : placa.getUsuarios().getListaUsuarios()) {
                                long lapso = tiempo - usuario.getTiempoUltimoMsg();

                                if(lapso > 5000 && lapso <= 6500){
                                    //Si pasan más de 5 segundos sin KA, el usuario es INACTIVO
                                    if(usuario.getEstado() == Usuario.Estado.ACTIVO){
                                        usuario.setEstado(Usuario.Estado.INACTIVO);
                                        Registro.registrarEvento(this, usuario.getNombre() + "pasa a INACTIVO");
                                    }
                                }
                                else{
                                    // Si es INACTIVO y pasan más de 6.5 segundos sin KA, se solicita una respuesta al usuario
                                    if(lapso > 6500 && lapso <= 8500){
                                        String msj = Mensajes.componerMensaje(
                                                        Mensajes.COMANDO_RED,
                                                        Mensajes.SUBR_AUN_ACTIVO,
                                                        usuario.getIdPlaca()
                                        );
                                        placa.enviarComando(msj);
                                        Registro.registrarEvento(this, "Consultando estado de "+ usuario.getNombre());
                                    }
                                    else{
                                        if(lapso > 8500){
                                            placa.getUsuarios().quitarUsuario(usuario);
                                            placa.getProcesador().getOyente().onActualizarUsuarios();
                                            placa.getProcesador().getOyente().onUsuarioDesconectado(usuario);
                                        }
                                    }
                                    // Si no hay KA del usuario después de 7.5 s, se lo remueve del listado

                                }


//                                if ((tiempo - usuario.getTiempoUltimoMsg()) > 5000) {
//                                    usuario.setEstado(Usuario.Estado.INACTIVO);
//                                    Registro.registrarEvento(this, usuario.getNombre() + "pasa a INACTIVO");
//                                } else {
//                                    if (usuario.getEstado() == Usuario.Estado.INACTIVO && (tiempo - usuario.getTiempoUltimoMsg()) > 6500) {
//                                        String msj = Mensajes.componerMensaje(Mensajes.COMANDO_RED, Mensajes.SUBR_AUN_ACTIVO, usuario.getIdPlaca());
//                                        placa.enviarComando(msj);
//                                    } else {
//                                        if (usuario.getEstado() == Usuario.Estado.INACTIVO && (tiempo - usuario.getTiempoUltimoMsg()) > 7500) {
//
//                                            placa.getUsuarios().quitarUsuario(usuario);
//                                            placa.getProcesador().getOyente().onActualizarUsuarios();
//                                            placa.getProcesador().getOyente().onUsuarioDesconectado(usuario);
//                                        }
//                                    }
//                                }

                            }
                        }
                    } catch (Exception e) {
                        Registro.registrarEvento(this, "Error: " + e.getMessage());
                    }
                }, 0, 6000, TimeUnit.MILLISECONDS);
    }

    public void detener() {
        if (planificador != null && !planificador.isShutdown()) {
            planificador.shutdownNow();
        }
    }
}
