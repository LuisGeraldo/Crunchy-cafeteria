/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import static java.awt.Frame.ICONIFIED;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Utileria;

/**
 *
 * @author luis
 */
public class FrmVenta extends JFrame{
    
    
    Utileria res; 
    public JPanel panelPrincipal = null;
    public JPanel panelSecundario = null;
    public JPanel panelVenta = null;
    
    public JButton cerrarButton;
    public JButton miniButton;
    public JButton mantenimiento;
    public JButton ajustes;
    public JButton ayuda;
    public JButton nuevaOrdenButton;
    public JButton buscarButton;
    public JButton nuevoCliente;
 
           
    public JLabel iconPanel = null;
    public JLabel imageCafeteria = null;
    public JLabel userName = null;
  
    
    
    public JLabel clienteLabel = null;
    public JLabel empleadoLabel = null;
    public JLabel ventasLabel = null;
    public JLabel comentarioLabel = null;
    
    
    
    public JComboBox clienteCombo = null;
    
    
    
   
    public Container contenedor = null;
    public JTextField buscarField = null; 
    public JTextField empleadoField = null;
    public JTextArea comentarioText = null;
    public JTable productos = null;
    public JTable ordenesCliente = null;
    
    ActionListener listener;
    FrmRegistrarse frmRegistrarse;
    public String userNameString;
  
  
    
    public FrmVenta(){
     
     /**
       * Inicializacion del JFrame
       * 
       */   
         
        super("Login");
        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
       
      /**
        * Inicializacion de los componentes de la interfaz
        *
        */
     
        contenedor = getContentPane();
        panelPrincipal = new JPanel();
        panelSecundario = new JPanel();
        panelVenta = new JPanel();

        cerrarButton = new JButton();
        miniButton = new JButton();
        //--------------------------------
        nuevaOrdenButton = new JButton("NUEVA ORDEN");
        
        mantenimiento = new JButton("PEDIDOS");
        ajustes = new JButton("AJUSTES");
        ayuda = new JButton("AYUDA");
        nuevoCliente = new JButton("Nuevo cliente");
        
        
        buscarButton = new JButton();  
        buscarField = new JTextField();
        
        
   
        iconPanel = new JLabel();
        imageCafeteria = new JLabel();
        userName = new JLabel("Nombre usuario");
      
        
        ventasLabel = new JLabel("Ventas");
        clienteLabel = new JLabel("Cliente: ");
        empleadoLabel = new JLabel("Empleado: ");
        comentarioLabel = new JLabel("Comentario: ");
        
        clienteCombo = new JComboBox();
        empleadoField = new JTextField();
        
        
        
        productos = new JTable();
        ordenesCliente = new JTable();
        
        
        
        res = new Utileria();
        frmRegistrarse = new FrmRegistrarse();
        
    
        panelPrincipal.setLayout(null);
        panelSecundario.setLayout(null);
        panelVenta.setLayout(null);
        
      /**
        * Agregando el panel principal(Jpanel) al content 
        * Agregacion de los elementos en el Panel principal
        *
        */
        
        contenedor.add(panelPrincipal);
        panelPrincipal.add(panelSecundario);
        panelPrincipal.add(panelVenta);
        
          
        panelPrincipal.add(cerrarButton);
        panelPrincipal.add(miniButton);
        panelPrincipal.add(imageCafeteria);
       // panelPrincipal.add(buscarButton);
       // panelPrincipal.add(buscarField);
        
        
        //Label
        //panelPrincipal.add(ventasLabel);
     
                   
        panelSecundario.add(iconPanel);
        panelSecundario.add(userName);
        //------------------------------------
        panelSecundario.add(mantenimiento);
        panelSecundario.add(ajustes);
        panelSecundario.add(ayuda);
        panelSecundario.add(nuevaOrdenButton);
        
        
        panelVenta.setVisible(false);
         
        panelVenta.add(clienteLabel);
        panelVenta.add(empleadoLabel);
        panelVenta.add(comentarioLabel);
        panelVenta.add(productos);
        panelVenta.add(ordenesCliente);
        panelVenta.add(clienteCombo);
        panelVenta.add(empleadoField);
        panelVenta.add(nuevoCliente);
       /**
         * Funciones para posicionar, dimensionar y dar estilos a los elementos
         * 
         **/
              
        PosicionView();
        Dimension();
        DisenoView();
      
        
         listener = new ActionListener(){    
            @Override
            public void actionPerformed(ActionEvent e) {
       
               if(e.getSource() == nuevaOrdenButton){
                   panelVenta.setVisible(true);
                   
               } 
                
                
               //Accion del boton cerrar        
               if(e.getSource() == cerrarButton){
                   dispose();
               }
               
               if(e.getSource() == miniButton){
                  setExtendedState(ICONIFIED);
               }
               
               
            }
        };
        
        cerrarButton.addActionListener(listener);
        miniButton.addActionListener(listener);
        nuevaOrdenButton.addActionListener(listener);
    }
    
     
    public void Dimension(){
        panelSecundario.setSize(300, 600);
        panelVenta.setSize(700, 600);
    }
            
   
    public void PosicionView(){      
       panelVenta.setLocation(300, 40);
        
       //icono del panel izquierdo (x, y, ancho, alto)
       iconPanel.setBounds(85, 8, 200, 200);
       
       //Imagem principal del JFrame
       imageCafeteria.setBounds(580, 100, 400, 400);
       
       
       //User name label
       userName.setBounds(50, 100, 200, 200);
       userName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
       userName.setVerticalAlignment(javax.swing.SwingConstants.CENTER); 
          
     
       //Boton cerrar
       cerrarButton.setBounds(960, 10, 20, 20);
       
       //Boton minimizar
       miniButton.setBounds(920, 8, 30, 25);  
       
       //Boton de servicios
       nuevaOrdenButton.setBounds(2, 234, 295, 90);
       
       //Boton mantenimiento
       mantenimiento.setBounds(2, 326, 295, 90);
       
       //Boton de ajustes
       ajustes.setBounds(2, 418, 295, 90);
       //ajustes.setBounds(2, 418, 296, 90);
       //Boton de ayuda
       ayuda.setBounds(2, 510, 295, 88);
       
       //Boton de buscar
       buscarButton.setBounds(450, 30, 50, 35);
       
       //Field de buscar
       buscarField.setBounds(510, 30, 300, 35);
       
      
       
       //Label ventas
       ventasLabel.setBounds(400, 240, 150, 150);
       
       //cliente Label
       empleadoLabel.setBounds(50, 47, 200, 50);
       //cliente combo
       empleadoField.setBounds(200, 55, 300, 30);
       
    
       //Empleado label
       clienteLabel.setBounds(50, 110, 150, 30);
       
       //Empleado field
       clienteCombo.setBounds(200, 110, 300, 30);
       
       //boton nuevo cliente
       nuevoCliente.setBounds(510, 110, 150, 30);
        
    }
    
   
    public void DisenoView(){
       Color colorPrincipal = res.Colorear(255, 115, 0);//res.Colorear(0, 30, 52);
       Color colorSecundario = res.Colorear(0, 30, 52);//res.Colorear(0, 169, 178);
       Font labelFont = res.Fuente("Arial", 1 , 18);
       Font campos = res.Fuente("Arial", 1, 15);
       
       panelPrincipal.setBackground(Color.WHITE);
       panelSecundario.setBackground(colorSecundario);
       panelVenta.setBackground(Color.white);
       
       //Icon panel 
       iconPanel.setIcon(res.ColocarImagen("/view/imagenes/user.png"));
       
       //Imagen cafeteria
       imageCafeteria.setIcon(res.ColocarImagen("/view/imagenes/inventario.png"));
       
       
       //Nombre usuario
       userName.setFont(res.Fuente("Arial", 1 , 25));
       userName.setForeground(Color.white);
       
       userName.setHorizontalAlignment(JLabel.CENTER);
       
     
       //Boton cerrar
       cerrarButton.setIcon(res.ColocarImagenScala("/view/imagenes/close.png", 15, 15));
       cerrarButton.setBackground(null);
       cerrarButton.setBorder(null);
       cerrarButton.setFocusable(false);
       cerrarButton.setContentAreaFilled(false);
       
       //Boton minimizar
       miniButton.setIcon(res.ColocarImagenScala("/view/imagenes/less-blue.png", 20, 25));
       miniButton.setBackground(null);
       miniButton.setBorder(null);
       miniButton.setFocusPainted(false);
       miniButton.setContentAreaFilled(false);     
       
       //Boton mantenimiento
       mantenimiento.setBackground(colorPrincipal);
       mantenimiento.setForeground(Color.white);
       mantenimiento.setBorder(null);
       mantenimiento.setFont(res.Fuente("Arial", 1 , 16));
       mantenimiento.setFocusable(false);
       
       //Boton Ajustes
       ajustes.setBackground(colorPrincipal);
       ajustes.setForeground(Color.white);
       ajustes.setBorder(null);
       ajustes.setFont(res.Fuente("Arial", 1 , 16));
       ajustes.setFocusable(false);
       
       //Boton Ayuda
       ayuda.setBackground(colorPrincipal);
       ayuda.setForeground(Color.white);
       ayuda.setBorder(null);
       ayuda.setFont(res.Fuente("Arial", 1 , 16));
       ayuda.setFocusable(false);
     
       //Boton servicios
       nuevaOrdenButton.setBackground(colorPrincipal);
       nuevaOrdenButton.setForeground(Color.white);
       nuevaOrdenButton.setBorder(null);
       nuevaOrdenButton.setFont(res.Fuente("Arial", 1 , 16));
       nuevaOrdenButton.setFocusable(false);
       
       //Boton buscar 
       buscarButton.setIcon(res.ColocarImagenScala("/view/imagenes/buscar.png", 33, 33));
       buscarButton.setBackground(null);
       buscarButton.setBorder(null);
       buscarButton.setFocusable(false);
           
       //Field buscar
       buscarField.setBackground(null);
       buscarField.setForeground(Color.white);
       buscarField.setFont(labelFont);
       buscarField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, res.Colorear(255, 115, 0)));
       
       //Field empleado
       empleadoField.setBackground(Color.white);
       empleadoField.setBorder(null);
       empleadoField.setForeground(Color.black);
       empleadoField.setFont(campos);
       empleadoField.setEditable(false);
       empleadoField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, res.Colorear(255, 115, 0)));
 
       
       //Field combo cliente
       clienteCombo.setBackground(null);
       clienteCombo.setBorder(null);
       clienteCombo.setUI(new WindowsComboBoxUI());
       clienteCombo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, res.Colorear(255, 115, 0)));
       
       
       
       //FF7300
    
      
   
       //Nuevo cliente
       nuevoCliente.setBackground(colorPrincipal);
       nuevoCliente.setForeground(Color.white);
       nuevoCliente.setBorder(null);
       nuevoCliente.setFont(res.Fuente("Arial", 1 , 16));
       nuevoCliente.setFocusable(false);
       
       
       //Label ventas
       ventasLabel.setFont(labelFont);
       ventasLabel.setForeground(colorPrincipal);
       
       //Cliente Label
       clienteLabel.setFont(labelFont);
       clienteLabel.setForeground(colorPrincipal);
       
       //Empleado Label
       empleadoLabel.setFont(labelFont);
       empleadoLabel.setForeground(colorPrincipal);
       
    }
}
