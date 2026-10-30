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
            partidas.add(partida);
        }
    }

    public Partida buscarPartida(Usuario creador){
        Partida resultado = null;

        Optional<Partida> busqueda = partidas.
                stream().
                filter(
                        Partida p -> p.
                ).
                findFirst();

    }

}
