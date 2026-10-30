package com.ejemplo.zumbido.sistema.gestores;

import com.ejemplo.zumbido.sistema.Mensajes;
import com.ejemplo.zumbido.sistema.Placa;
import com.ejemplo.zumbido.sistema.Registro;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GestorPartidas {

    private ScheduledExecutorService planificador;
    private final Placa placa;


    public GestorPartidas(Placa placa) {
        this.placa = placa;
    }

    public void iniciar(){
        detener();

        planificador = Executors.newSingleThreadScheduledExecutor();

        planificador.scheduleAtFixedRate(
                ()->{
                    try {
                        if (placa != null) {
                            if(placa.getUsuario().getPartida()!=null) {
                                String msj = Mensajes.componerMensaje(
                                        Mensajes.COMANDO_RED,
                                        Mensajes.SUBR_PARTIDA,
                                        "UNO"
                                );
                                placa.getMensajero().agregarMensaje(msj, false);
                            }
                        }
                    } catch (Exception e) {
                        Registro.registrarEvento(this,"ERROR:" +  e.getMessage());
                    }
                }, 0, 8000, TimeUnit.MILLISECONDS);

    }


    public void detener() {
        if (planificador != null && !planificador.isShutdown()) {
            planificador.shutdownNow();
        }
    }
}
