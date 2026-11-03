package com.ejemplo.zumbido.sistema;

import java.util.ArrayList;
import java.util.Optional;

public class Partidas {

    private ArrayList<Partida> partidas = new ArrayList<>();

    /**
     * Añade una partida, diferenciando entre partidas propias y de otros usuarios
     * @param partida La partida que se agrega al listado
     * @param esPropia establece si la partida es del mismo usuario o de otro
     */
    public void agregarPartida(Partida partida, boolean esPropia) {
        if (esPropia){
            partidas.addFirst(partida);
        }
        else{
            if(buscarPartida(partida)==null){
                partidas.addLast(partida);
            }
        }
    }

    public Partida buscarPartida(Usuario creador){
        if (creador == null || creador.getIdPlaca() == null) {
            return null;
        }

        Optional<Partida> busqueda = partidas.
                stream().
                filter( (Partida partida) -> partida.getPropietario().getIdPlaca().equals( creador.getIdPlaca() )
                ).
                findFirst();

        return busqueda.orElse(null);
    }

    public Partida buscarPartida(Partida nueva){
        if (nueva == null) {
            return null;
        }

        Optional<Partida> busqueda = partidas.
                stream().
                filter( (Partida partida) -> partida.getPropietario().getIdPlaca().equals( nueva.getPropietario().getIdPlaca() )
                ).
                findFirst();

        return busqueda.orElse(null);
    }

    public void eliminarPartida(Partida partida){
        partidas.remove(partida);
    }


}
