package com.ejemplo.zumbido.juegos.titulos;

import com.ejemplo.zumbido.juegos.Juego;
import com.ejemplo.zumbido.sistema.GestorDeClases;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FabricaJuegos {

    private static final Map<String, Supplier<Juego>> REGISTRO_JUEGOS = new HashMap<>();

    static {
        GestorDeClases gestorDeClases = new GestorDeClases();
        gestorDeClases.
        //REGISTRO_JUEGOS.put("BN", BatallaNaval::new);
        //REGISTRO_JUEGOS.put("UNO", JuegoUno::new);
        //REGISTRO_JUEGOS.put("DC", DungeonCrawler::new);
    }

    /**
     * Instancia el juego correspondiente a partir del ID de red.
     * @param idJuego Código corto del juego ("BN", "UNO", etc.)
     * @return Instancia concreta de Juego, o null si el ID no existe.
     */
    public static Juego crearJuego(String idJuego) {
        if (idJuego == null) return null;
        Supplier<Juego> creador = REGISTRO_JUEGOS.get(idJuego.toUpperCase());
        return (creador != null) ? creador.get() : null;
    }
}
