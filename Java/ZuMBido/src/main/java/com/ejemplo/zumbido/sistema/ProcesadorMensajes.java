/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.sistema;

import com.ejemplo.zumbido.Placa;
import java.util.Arrays;

/**
 *
 * @author sebastian
 */
public class ProcesadorMensajes {

    private final Placa placa;
    private OyenteMensajes oyente;

    public ProcesadorMensajes(Placa placa) {
        this.placa = placa;
    }

    /**
     * @return the oyente
     */
    public OyenteMensajes getOyente() {
        return oyente;
    }

    /**
     * @param oyente the oyente to set
     */
    public void setOyente(OyenteMensajes oyente) {
        this.oyente = oyente;
    }

    /**
     * Analiza un mensaje proveniente de la placa
     *
     * @param mensaje el mensaje recibido por via serial
     */
    public void analizarMensaje(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return;
        }

        String[] cadena = mensaje.split(":");

        if (cadena.length < 2) {
            System.out.println("Mensaje truncado: " + mensaje);
            return;
        }

        String comando = cadena[0];
        String subcomando = cadena[1];

        switch (comando) {
            case Mensajes.COMANDO_SISTEMA:
                String[] datos = Arrays.copyOfRange(cadena, 2, cadena.length);
                procesarComandoSistema(subcomando, datos);
                break;
            case Mensajes.PLACA_RECIBIDO:
                System.out.println("->");
                break;
            default:
                throw new AssertionError();
        }

    }

    /**
     * Procesa los comandos identificados con el manejo de la red
     *
     * @param subcomando
     * @param cadena
     */
    private void procesarComandoRed(String subcomando, String[] cadena) {
        switch (subcomando) {
            case Mensajes.SUBR_NUEVO_LOGIN:
                // Lógica de red automática (independiente de la ventana)
                if (cadena.length > 2 && placa.getUsuario() != null) {
                    if (placa.getUsuario().getNombre().equals(cadena[2])) {
                        String msj = Mensajes.componerMensaje(Mensajes.COMANDO_RED, Mensajes.SUBR_NOMBRE_REPETIDO);
                        placa.enviarComando(msj);
                    }
                }
                break;

            case Mensajes.SUBR_NOMBRE_REPETIDO:
                if (placa.getUsuario() == null && oyente != null) {
                    oyente.onNombreRepetido();
                }
                break;
        }
    }

    /**
     * Procesa los comandos de sistema recibidos de la placa
     *
     * @param subcomando
     * @param cadena
     */
    private void procesarComandoSistema(String subcomando, String[] cadena) {
        switch (subcomando) {
            case Mensajes.SUBC_BOARD_ID:
                System.out.println("bid" + cadena.length);
                String id = cadena[0];
                placa.setId(id);
                if (oyente != null) {
                    oyente.onBoardIdRecibido(id);
                }
                break;

            case Mensajes.SUBC_GRUPO_RADIO:
                if (cadena.length > 2) {
                    int grupo = Integer.parseInt(cadena[2]);
                    placa.setGrupoRadial(grupo);
                    if (oyente != null) {
                        oyente.onGrupoRadioCambiado(grupo);
                    }
                }
                break;

            case Mensajes.SUBC_KEEEP_ALIVE:
                placa.enviarComando(Mensajes.SUBC_KEEEP_ALIVE);
                break;

            default:
                System.out.println("SubComando no conocido: " + subcomando);
        }
    }

    private void procesarMensajePlaca(String subcomando, String[] cadena) {
        if (cadena.length < 3) {
            return;
        }
        boolean esError = Mensajes.SUBPL_MENSAJE_ERROR.equals(subcomando);
        if (oyente != null) {
            oyente.onMensajePlaca(placa.getId(), cadena[2], esError);
        }
    }

}
