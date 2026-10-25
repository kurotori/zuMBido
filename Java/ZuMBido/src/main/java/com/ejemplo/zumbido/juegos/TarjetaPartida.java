package com.ejemplo.zumbido.juegos;

import com.ejemplo.zumbido.interfaz.Colores;
import com.ejemplo.zumbido.interfaz.Fuentes;
import com.ejemplo.zumbido.interfaz.LabelConImagen;
import com.ejemplo.zumbido.sistema.Partida;
//import com.ejemplo.zumbido.sistema.Usuario;

import javax.swing.*;
import java.awt.*;

public class TarjetaPartida extends JPanel implements ListCellRenderer<Partida> {

    private Partida partida;

    private LabelConImagen lblIcono;
    private JLabel lblNombreUsuario;
    private JLabel lblIdPlaca;
    private JPanel pnlZonaDatos;
    private Fuentes fuentes = new Fuentes();

    public TarjetaPartida() {
    configurar();
    }

    private void configurar(){
        setLayout(new BorderLayout(8, 0));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        setBackground(Color.white);
        setOpaque(true);

        lblIcono = new LabelConImagen(32, 32, "/imagen/juegos.png");
        lblIcono.setPreferredSize(new Dimension(32,32));

        add(lblIcono, BorderLayout.WEST);


        pnlZonaDatos = new JPanel( new BorderLayout(10, 10) );
        pnlZonaDatos.setBorder(BorderFactory.createEmptyBorder(5,5, 5, 5));
        //pnlZonaDatos.setBackground(Color.white);
        add(pnlZonaDatos, BorderLayout.CENTER);

        lblNombreUsuario = new JLabel("---");
        lblNombreUsuario.setFont(fuentes.LISTADO_USUARIOS_NOMBRE);
        pnlZonaDatos.add(lblNombreUsuario, BorderLayout.NORTH);

        lblIdPlaca = new JLabel("...");
        lblIdPlaca.setFont(fuentes.LISTADO_USUARIOS_PLACA);
        pnlZonaDatos.add(lblIdPlaca, BorderLayout.SOUTH);
    }


    @Override
    public Component getListCellRendererComponent(
            JList<? extends Partida> jList, Partida partida,
            int index, boolean isSelected, boolean cellHasFocus) {

        this.partida = partida;

        this.lblNombreUsuario.setText(partida.getPropietario().getNombre());
        //this.

        if (isSelected){
            this.pnlZonaDatos.setBackground(Colores.COLOR_AZUL_SELECCIONAR);
            this.lblNombreUsuario.setForeground(Color.white);
        }
        else{
            this.pnlZonaDatos.setBackground(Color.white);
            this.lblNombreUsuario.setForeground(Color.black);
        }

        return this;
    }
}
