/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.Validacion;
import model.Utileria;

/**
 *
 * @author luis
 */

public class FrmLogin extends JFrame{
    
     
    public JPanel panelPrincipal = null;
    public JPanel panelSecundario = null;
    public JTextField userNameField = null;
    public JPasswordField passwordField = null;
    public JButton enviarButton = null;
    public JButton registrarseButton = null;
    public JButton cerrarButton;
    public JButton miniButton; 
    
    public JLabel tituloPrincipal = null;
    public JLabel userNameLabel = null;
    public JLabel passwordLabel = null;
    public JLabel iconPanel = null;
    public JLabel nameAplicacion = null;
    public Container contenedor = null;
    ActionListener listener;
    
    //Clases
    FrmRegistrarse frmRegistrarse;
    FrmInicio frmInicio;
    Validacion validacion;
    Utileria res;
    
    
    public FrmLogin(){
     
     /**
       * Inicializacion del JFrame
       * 
       */   
         
       this.setTitle("Login");
        setSize(680, 500);
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
                
        userNameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        enviarButton = new JButton("Iniciar sesion");
        registrarseButton = new JButton("Registrese aqui!");
        cerrarButton = new JButton();
        miniButton = new JButton();
        tituloPrincipal = new JLabel("INICIAR SESION");
        userNameLabel = new JLabel("Usuario: ");
        passwordLabel = new JLabel("Contrasena: ");
        iconPanel = new JLabel();
        nameAplicacion = new JLabel("CAFETERIA UNO");
        res = new Utileria();
        frmRegistrarse = new FrmRegistrarse();
        
        frmInicio = new FrmInicio();
        validacion = new Validacion();
        
        panelPrincipal.setLayout(null);
        panelSecundario.setLayout(null);
       
        
      /**
        * Agregando el panel principal(Jpanel) al content 
        * Agregacion de los elementos en el Panel principal
        *
        */
        
        contenedor.add(panelPrincipal);
        
        //Agregacion de los elementos al panel principal
        panelPrincipal.add(panelSecundario);
        panelPrincipal.add(tituloPrincipal);
        panelPrincipal.add(userNameLabel);
        panelPrincipal.add(userNameField);
        panelPrincipal.add(passwordLabel);
        panelPrincipal.add(passwordField);
        panelPrincipal.add(enviarButton);   
        panelPrincipal.add(registrarseButton);
        panelPrincipal.add(cerrarButton);
        panelPrincipal.add(miniButton);
       
        //Agregacion de los elementos al panel secundario
        panelSecundario.add(iconPanel);
        panelSecundario.add(nameAplicacion);
        
     
     
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
       
              //Accion del boton iniciar sesion
              if(e.getSource() == enviarButton){
                  
                  String userName = userNameField.getText();
                  String contrasena = passwordField.getText();
                
                  
                if(userName.equals("") || contrasena.equals("")){  
                    
                   JOptionPane.showMessageDialog(null,"Llene todos los campos", "Cafeteria uno", JOptionPane.WARNING_MESSAGE, res.ColocarImagen("/view/imagenes/complain.png"));
                    
                }else{
                    
                  if(validacion.Validar(userName, contrasena)){ 
                    
                     frmInicio.userName.setText(userName);
                     frmInicio.setVisible(true);
                  
                     setVisible(false);
                    
                 }else{
                      JOptionPane.showMessageDialog(null,"Crendenciales no validas", "Cafeteria uno", JOptionPane.CLOSED_OPTION, res.ColocarImagen("/view/imagenes/man.png"));
                 }      
                }
              }
              
              //Accion del boton registrarse
               if(e.getSource() == registrarseButton){          
                   frmRegistrarse.setVisible(true);
                   setVisible(false);
              }  
               
               //Accion del boton cerrar
               
               if(e.getSource() == cerrarButton){
                   dispose();
               }
               
               //Accion del boton minimizar
               
               if(e.getSource() == miniButton){
                  setExtendedState(ICONIFIED);
               }
               
            }
        };
        
        enviarButton.addActionListener(listener);
        registrarseButton.addActionListener(listener);
        cerrarButton.addActionListener(listener);
        miniButton.addActionListener(listener);
    }
    
    
    public void Dimension(){
        panelSecundario.setSize(340, 500);
    }
            
   
    public void PosicionView(){      
        
       //Titulo principal (x, y, ancho, alto)
       tituloPrincipal.setBounds(396, 40, 300, 50); 
       
       //User name label and field (x, y, ancho, alto)
       userNameLabel.setBounds(370, 125, 100, 50); 
       userNameField.setBounds(370, 165, 280, 25);
      
       //password label and field (x, y, ancho, alto)
       passwordLabel.setBounds(370, 220, 100, 50);
       passwordField.setBounds(370, 260, 280, 25);
       
       //button enviar (x, y, ancho, alto)
       enviarButton.setBounds(370, 345, 280, 40);
       
       //button registrarse
       registrarseButton.setBounds(370, 400, 280, 40);
       
       //icono del panel izquierdo (x, y, ancho, alto)
       iconPanel.setBounds(40, 30, 300, 300);
      
       //nombre del sitema panel izquierdo (x, y, ancho, alto)
       nameAplicacion.setBounds(60, 160, 400, 400);
              
       //Boton cerrar
       cerrarButton.setBounds(650, 10, 20, 20);
       
       //Boton minimizar
       miniButton.setBounds(610, 8, 30, 25);  
    }
    
   
    public void DisenoView(){
        
       Color colorPrincipal = res.Colorear(0, 30, 52);
       Color colorSecundario = res.Colorear(0, 169, 178);
       Font labelFont = res.Fuente("Arial", 0 , 15);
       
       panelPrincipal.setBackground(colorPrincipal);
       panelSecundario.setBackground(colorSecundario);
       
       tituloPrincipal.setForeground(Color.white);
       tituloPrincipal.setFont(res.Fuente("Arial", 0 , 28));
       
       //user name Label
       userNameLabel.setForeground(Color.white);
       userNameLabel.setFont(labelFont);
       
       //user name Field
       userNameField.setForeground(Color.white);
       userNameField.setBackground(null);
       userNameField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
       
       //Password Label
       passwordLabel.setForeground(Color.white);
       passwordLabel.setFont(labelFont);
     
       //Password Field
       passwordField.setBackground(null);
       passwordField.setForeground(Color.white);
       passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
       
       //Button enviar 
       enviarButton.setBackground(colorSecundario);
       enviarButton.setForeground(Color.white);
       enviarButton.setBorder(null);
       enviarButton.setFont(res.Fuente("Arial", 0 , 16));
       enviarButton.setFocusable(false);
       
       //Icon panel 
       iconPanel.setIcon(res.ColocarImagen("/view/imagenes/shopicon.png"));
       
       //Nombre app
       nameAplicacion.setFont(res.Fuente("Arial", 1 , 25));
       nameAplicacion.setForeground(Color.white);   
       
       //Texto registrarse
       registrarseButton.setBackground(res.Colorear(220, 173, 0));
       registrarseButton.setForeground(Color.white);
       registrarseButton.setBorder(null);
       registrarseButton.setFont(labelFont);
       registrarseButton.setFocusable(false);
       
       //Boton cerrar
        cerrarButton.setIcon(res.ColocarImagenScala("/view/imagenes/close.png", 15, 15));
       cerrarButton.setBackground(null);
       cerrarButton.setBorder(null);
       cerrarButton.setFocusable(false);
       cerrarButton.setContentAreaFilled(false);
       
       //Boton minimizar
       miniButton.setIcon(res.ColocarImagenScala("/view/imagenes/less.png", 20, 25));
       miniButton.setBackground(null);
       miniButton.setBorder(null);
       miniButton.setFocusPainted(false);
       miniButton.setContentAreaFilled(false);     
    }
}
