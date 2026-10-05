/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import com.ejemplo.zumbido.interfaz.BotonImagenChico;
import com.ejemplo.zumbido.interfaz.Fuentes;
import com.ejemplo.zumbido.interfaz.Textos;
import com.ejemplo.zumbido.sistema.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
    
    private JButton btnCambiarNombre;
    private BotonImagenChico btnJuegos;

    private GridBagConstraints gbc = new GridBagConstraints();
    private Fuentes fuentes = new Fuentes();
    
    public PanelZonaUsuario(Chat ventana) {
        this.ventana = ventana;
        configurar();
        configurarFunciones();
    }

    private void configurar() {
        setPreferredSize(new Dimension(210, 0));
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
        
        pnlMenuUsuario = new JPanel();
        pnlMenuUsuario.setLayout(new GridBagLayout());
        pnlMenuUsuario.setPreferredSize(new Dimension(0,149));
        pnlMenuUsuario.setBackground(Color.white);
        add(pnlMenuUsuario, BorderLayout.SOUTH);
        
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        btnJuegos = new BotonImagenChico(Textos.CHAT_BTN_ABRIR_JUEGOS, "/imagen/juegos.png", 32, 32);
        btnJuegos.setPreferredSize(new Dimension(195,35));
        btnJuegos.setIconTextGap(30);
        btnJuegos.setFont(fuentes.VENTANA_NEGRITA_A_SCH);
        pnlMenuUsuario.add(btnJuegos, gbc);
        
        gbc.gridy = 1;
        
        btnCambiarNombre = new BotonImagenChico(Textos.CHAT_BTN_CAMBIAR_NOMBRE, "/imagen/cambiar_nombre.png", 32, 32);
        btnCambiarNombre.setFont(fuentes.VENTANA_NEGRITA_A_SCH);
        btnCambiarNombre.setPreferredSize(new Dimension(195,35));
        pnlMenuUsuario.add(btnCambiarNombre, gbc);
        
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
                        
                        ventana.abrirChatPrivado(usuarioSeleccionado);
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
