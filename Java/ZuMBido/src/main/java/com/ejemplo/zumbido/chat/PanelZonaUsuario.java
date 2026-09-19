/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import com.ejemplo.zumbido.sistema.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author sebastian
 */
public class PanelZonaUsuario extends JPanel {

    private Chat ventana;

    private DefaultListModel<Usuario> modeloUsuarios;
    private JList<Usuario> lstListaUsuarios;
    private JPanel pnlMenuUsuario;

    public PanelZonaUsuario(Chat ventana) {
        this.ventana = ventana;
        configurar();
        configurarFunciones();
    }

    private void configurar() {
        setPreferredSize(new Dimension(200, 0));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.gray));
        setBackground(Color.WHITE);

        modeloUsuarios = new DefaultListModel<>();

        lstListaUsuarios = new JList<>(modeloUsuarios);
        lstListaUsuarios.setCellRenderer(new TarjetaUsuario());
        lstListaUsuarios.setPreferredSize(new Dimension(0, 350));
        lstListaUsuarios.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.gray));
        JScrollPane scrlListaUsuarios = new JScrollPane(lstListaUsuarios);
        scrlListaUsuarios.setPreferredSize(new Dimension(0, 350));
        add(scrlListaUsuarios, BorderLayout.NORTH);
    }

    private void configurarFunciones() {
        //Agregar función de dobleclic para ventana privada al listado de usuarios
        lstListaUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Confirmar botón izquierdo y doble clic
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    int index = lstListaUsuarios.locationToIndex(e.getPoint());
                    if (index != -1 && lstListaUsuarios.getCellBounds(index, index).contains(e.getPoint())) {
                        Usuario usuarioSeleccionado = lstListaUsuarios.getModel().getElementAt(index);
                        System.out.println("u:" + usuarioSeleccionado.getNombre());
                        ventana.abrirChatPrivado(usuarioSeleccionado);
//                        abrirChatPrivado(usuarioSeleccionado);
                    }
                }
            }
        });
    }

    public void actualizarUsuarios(ArrayList<Usuario> listaUsuarios) {
        modeloUsuarios.removeAllElements();

        for (Usuario usuario : listaUsuarios) {
            modeloUsuarios.addElement(usuario);
        }

    }

}
