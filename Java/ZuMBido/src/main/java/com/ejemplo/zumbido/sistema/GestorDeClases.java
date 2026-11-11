package com.ejemplo.zumbido.sistema;

import com.ejemplo.zumbido.juegos.Juego;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.Set;
import java.util.stream.Collectors;

public class GestorDeClases {

    public Set<Class> buscarEnPaquete(String paquete, Class clase){
        Reflections reflections = new Reflections(paquete,
                new SubTypesScanner(false));

        return reflections.getSubTypesOf(clase.getClass())
                .stream()
                .collect(Collectors.toSet());
    }

    public Set<Class> buscarJuegos(){
        return buscarEnPaquete("com.ejemplo.zumbido.juegos.titulos", Juego.class);
    }
}
