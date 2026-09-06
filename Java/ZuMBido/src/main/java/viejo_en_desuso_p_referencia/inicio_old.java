/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viejo_en_desuso_p_referencia;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.Serial;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author sebastian
 */
public class inicio_old extends JFrame{
    
    private JComboBox<String> cmbListaPuertos;
    private JComboBox<String> cmbGrupos;
    private JPanel pnlSelectores;
    private JPanel pnlSelectorPuertos;
    private JPanel pnlSelectorGrupos;
    
    
    private SerialPort[] puertos;

    public inicio_old(){
        configurar();
        configurarFunciones();
    }
    
    private void configurar(){
        setSize(320,240);
        setTitle("Listado De Placas");
        setVisible(true);
        getContentPane().setLayout( new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        listaDePuertos();
        
        pnlSelectores = new JPanel(new BorderLayout());
        pnlSelectores.setPreferredSize(new Dimension(0,120));
        pnlSelectores.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        
        
        
        
        pnlSelectorPuertos = new JPanel(new FlowLayout());
        pnlSelectorPuertos.setPreferredSize(new Dimension(0,60));
        pnlSelectores.add(pnlSelectorPuertos, BorderLayout.NORTH);
        
        pnlSelectorGrupos = new JPanel(new FlowLayout());
        pnlSelectorGrupos.setPreferredSize(new Dimension(0,60));
        pnlSelectores.add(pnlSelectorGrupos, BorderLayout.SOUTH);
        
        cmbListaPuertos = new JComboBox<>();
        
        cmbGrupos = new JComboBox<>();
        
        pnlSelectorGrupos.add(new JLabel("Grupos:"));
        pnlSelectorGrupos.add(cmbGrupos, BorderLayout.NORTH);
        pnlSelectorPuertos.add(new JLabel("Puertos:"));
        pnlSelectorPuertos.add(cmbListaPuertos, BorderLayout.CENTER);
        
        getContentPane().add(pnlSelectores, BorderLayout.NORTH);
        
        getContentPane().validate();
        getContentPane().repaint();
        
    }
    
    
    private void configurarFunciones(){
        for (SerialPort puerto : puertos) {
            cmbListaPuertos.addItem(puerto.getSystemPortName());
        }
        
        for (int i = 0; i < 256; i++) {
            cmbGrupos.addItem("Grupo "+i );
        }

        cmbListaPuertos.addActionListener(
           new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               seleccionarPuerto();
               
            }
             
           }
        );
        
    }
    

    private void seleccionarPuerto(){
        String puerto = (String)cmbListaPuertos.getSelectedItem();
        System.out.println(puerto);
        SerialPort p = SerialPort.getCommPort(puerto);
        p.setBaudRate(115200);
        p.openPort();
        p.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        InputStream in = p.getInputStream();
        try{
            for (int j = 0; j < 1000; ++j)
            System.out.print((char)in.read());
                in.close();
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        }
        p.closePort();
    }

    private void listaDePuertos(){
        this.puertos = SerialPort.getCommPorts();
        
        if (puertos.length<1) {
            System.out.println("No hay puertos disponibles");
        }
        else{
            System.out.println("Puertos Disponibles:");
            for (SerialPort puerto : puertos) {
                System.out.println("----------------------------------------");
                System.out.println("System Port Name: " + puerto.getSystemPortName());  // e.g., COM3, ttyUSB0
                System.out.println("Descriptive Name: " + puerto.getDescriptivePortName()); // e.g., USB Serial Port
                System.out.println("Port Description: " + puerto.getPortDescription());
            }
        }
    }
    
    public static void main(String[] args) {
        new inicio_old();
    }
    
}
