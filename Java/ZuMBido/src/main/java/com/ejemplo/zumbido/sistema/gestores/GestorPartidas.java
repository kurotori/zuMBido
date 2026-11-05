package com.ejemplo.zumbido.sistema.gestores;

import com.ejemplo.zumbido.sistema.Mensajes;
import com.ejemplo.zumbido.sistema.Partida;
import com.ejemplo.zumbido.sistema.Placa;
import com.ejemplo.zumbido.sistema.Registro;

import java.time.Instant;
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
                                        placa.getUsuario().getPartida().getJuego().getNombre()
                                );
                                placa.getMensajero().agregarMensaje(msj, false);
                            }

                            Instant tiempo = Instant.now();
                            for(Partida p: placa.getPartidas().getListaPartidas()){
                                if (!p.isPropia()){
                                    long lapso = tiempo.toEpochMilli() - p.getMarcaTiempo().toEpochMilli();
                                    if(lapso >= 9000){
                                        Registro.registrarEvento(this,
                                                "La partida de " + p.getPropietario().getNombre() +
                                                        " ha caducado");
                                        placa.getPartidas().eliminarPartida(p);
                                    }
                                }
                                //Si la partida es propia, se actualiza su marca de tiempo
                                //TODO:Chequear condiciones de la partida para su renovación
                                else{
                                    p.setMarcaTiempo(Instant.now());
                                }


                            }
                            placa.getProcesador().getOyente().onActualizarPartidas();
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
