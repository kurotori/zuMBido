package com.ejemplo.zumbido.sistema;

import java.util.ArrayList;
import java.util.Optional;

public class Partidas {

    private ArrayList<Partida> partidas = new ArrayList<>();

    /**
     * Añade una partida, diferenciando entre partidas propias y de otros usuarios
     * @param partida La partida que se agrega al listado
     * @param esPropia establece si la partida es del mismo usuario o de otro
     * @return <b>true<b/> si es posible agregar la partida, <b>false</b> si no lo es.
     */
    public boolean agregarPartida(Partida partida, boolean esPropia) {
        if (esPropia){
            if(partidas.getFirst().isPropia()){
                return false;
            }
            else{
                partidas.addFirst(partida);
                return true;
            }

        }
        else{
            if(buscarPartida(partida)==null){
                partidas.addLast(partida);
                return true;
            }
            else{
                return false;
            }
        }
    }

    /**
     * Busca una partida a partir del usuario
     * @param creador El Usuario que creó la partida
     * @return La Partida ubicada, o null
     */
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

    /**
     * Busca una partida para determinar si ya ha sido agregada en el sistema
     * @param nueva La Partida que se busca
     * @return la misma Partida, si ya existe, o null
     */
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

    public ArrayList<Partida> getListaPartidas() {
        return partidas;
    }
}
