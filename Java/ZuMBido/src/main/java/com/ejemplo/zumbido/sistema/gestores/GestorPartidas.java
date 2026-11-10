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
                            //TODO: Las partidas propias tienen condiciones para ser publicadas,
                            // y deben chequearse en este punto

                            //Chequeamos la existencia de una partida propia
                            if(placa.getUsuario().getPartida()!=null) {
                                //Si la partida existe, comenzamos a publicarla en la red
                                String msj = Mensajes.componerMensaje(
                                        Mensajes.COMANDO_RED,
                                        Mensajes.SUBR_PARTIDA,
                                        placa.getUsuario().getPartida().getJuego().getNombre()
                                );
                                placa.getMensajero().agregarMensaje(msj, false);
                            }

                            //Se obtiene la marca de tiempo
                            Instant tiempo = Instant.now();

                            //Se evalúan las partidas registradas en la placa.
                            for(Partida p: placa.getPartidas().getListaPartidas()){
                                if (!p.isPropia()){

                                    //Comparamos el lapso pasado desde la última publicación
                                    long lapso = tiempo.toEpochMilli() - p.getMarcaTiempo().toEpochMilli();
                                    //Si el lapso es superior a 9s se elimina la partida del listado
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

                            //Tras cada ciclo del gestor, se emite una solicitud de actulización del listado de partidas
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
