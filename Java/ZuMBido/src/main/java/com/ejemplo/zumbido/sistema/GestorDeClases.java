package com.ejemplo.zumbido.sistema;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.Set;
import java.util.stream.Collectors;

public class GestorDeClases {
    public Set<Class> buscarEnPaquete(String paquete){
        Reflections reflections = new Reflections(paquete,
                new SubTypesScanner(false));

        return reflections.getSubTypesOf(Object.class)
                .stream()
                .collect(Collectors.toSet());
    }
}
