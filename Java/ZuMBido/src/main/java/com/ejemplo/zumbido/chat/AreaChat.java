/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.zumbido.chat;

import com.ejemplo.zumbido.sistema.Usuario;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author sebastian
 */
public class AreaChat extends JScrollPane {

    private JTextPane txtpnAreaChat = new JTextPane();
    private HTMLEditorKit htmlKit = new HTMLEditorKit();
    private HTMLDocument htmlDoc = new HTMLDocument();

    public AreaChat() {
        
        txtpnAreaChat.setEditable(false);
        txtpnAreaChat.setEditorKit(htmlKit);
        txtpnAreaChat.setDocument(htmlDoc);
        setViewportView(txtpnAreaChat);
    }

    public void agregarMensaje(Usuario usuario, String mensaje, boolean mio) {
        SwingUtilities.invokeLater(
                () -> {

                    String fondo = mio ? "#98F1A5" : "#AAAFDF";
                    String alineacion = mio ? "right" : "left";

                    String html = String.format(
                            "<div align='%s' style='margin: 4px;'>"
                            + "  <b style='color:%s;'>%s:</b> "
                            + "  <span style='background-color:" + fondo + "; padding:4px;'>%s</span>"
                            + "</div>", alineacion, "#202655", usuario.getNombre(), mensaje
                    );

                    try {
                        htmlKit.insertHTML(htmlDoc, htmlDoc.getLength(), html, 0, 0, null);
                        txtpnAreaChat.setCaretPosition(htmlDoc.getLength()); // Auto-scroll al final
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

}
