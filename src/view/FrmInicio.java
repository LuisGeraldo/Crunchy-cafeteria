/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import static java.awt.Frame.ICONIFIED;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Utileria;


/**
 *
 * @author luis
 */
public class FrmInicio extends JFrame{
    
    Utileria res; 
    public JPanel panelPrincipal = null;
    public JPanel panelSecundario = null;
    
    public JButton cerrarButton;
    public JButton miniButton;
    public JButton mantenimiento;
    public JButton ajustes;
    public JButton ayuda;
    public JButton servicios;
    public JButton buscarButton;
    
    //Botones controladores
    public JButton cliente;
    public JButton proveedores;
    public JButton ventas;
    public JButton cafeteria;
    public JButton inventario;
    public JButton articuloButton;
 
    
    
    
            
    public JLabel iconPanel = null;
    public JLabel imageCafeteria = null;
    public JLabel userName = null;
    public JLabel nameAplicacion = null;
    
    public JLabel ventasLabel = null;
    public JLabel clientesLabel = null;
    public JLabel proovedoresLabel = null;
    public JLabel cafeteriaLabel = null;
    public JLabel inventarioLabel = null;
    public JLabel articuloLabel = null;
    
   
    public Container contenedor = null;
    public JTextField buscarField = null; 
     ActionListener listener;
    FrmRegistrarse frmRegistrarse;
    public String userNameString;
    //Validacion validacion;
   // FrmLogin frmLogin;

  
    
    public FrmInicio(){
     
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

        cerrarButton = new JButton();
        miniButton = new JButton();
        //--------------------------------
        mantenimiento = new JButton("MANTENIMIENTO");
        ajustes = new JButton("AJUSTES");
        ayuda = new JButton("AYUDA");
        servicios = new JButton("SERVICIOS");
        buscarButton = new JButton();
        
        
        cliente = new JButton();
        proveedores = new JButton();
        ventas = new JButton();
        cafeteria = new JButton();
        inventario = new JButton();
        articuloButton = new JButton();
        buscarField = new JTextField();
        
        
   
        iconPanel = new JLabel();
        imageCafeteria = new JLabel();
        userName = new JLabel("Luis Rafael");
        nameAplicacion = new JLabel("CAFETERIA UNO");
        
        ventasLabel = new JLabel("Ventas");
        clientesLabel = new JLabel("Clientes");
        cafeteriaLabel = new JLabel("Cafeterias");
        proovedoresLabel = new JLabel("Proveedores");
        inventarioLabel = new JLabel("Inventario");
        articuloLabel = new JLabel("Articulos");
        
        
        res = new Utileria();
        frmRegistrarse = new FrmRegistrarse();
        
    
        panelPrincipal.setLayout(null);
        panelSecundario.setLayout(null);
       
        
      /**
        * Agregando el panel principal(Jpanel) al content 
        * Agregacion de los elementos en el Panel principal
        *
        */
        
        contenedor.add(panelPrincipal);
        panelPrincipal.add(panelSecundario);
        
          
        panelPrincipal.add(cerrarButton);
        panelPrincipal.add(miniButton);
      //  panelPrincipal.add(imageCafeteria);
        panelPrincipal.add(buscarButton);
        panelPrincipal.add(buscarField);
        panelPrincipal.add(ventas);
        panelPrincipal.add(cliente);
        panelPrincipal.add(cafeteria);
        panelPrincipal.add(proveedores);
        panelPrincipal.add(inventario);
        panelPrincipal.add(articuloButton);
        
        //Label
        panelPrincipal.add(ventasLabel);
        panelPrincipal.add(clientesLabel);
        panelPrincipal.add(cafeteriaLabel);
        panelPrincipal.add(proovedoresLabel);
        panelPrincipal.add(inventarioLabel);
        panelPrincipal.add(articuloLabel);
                
        
        
        panelSecundario.add(iconPanel);
        panelSecundario.add(userName);
        //------------------------------------
        panelSecundario.add(mantenimiento);
        panelSecundario.add(ajustes);
        panelSecundario.add(ayuda);
        panelSecundario.add(servicios);
        
         
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
       
                if(e.getSource() == ventas){
                    
                   FrmVenta ventas = new FrmVenta();
                    ventas.setVisible(true);
                    ventas.userName.setText(userName.getText());
                    ventas.empleadoField.setText(userName.getText());
                    
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
        ventas.addActionListener(listener);
    }
    
     public String getUserNameString() {
        return userNameString;
    }

    public void setUserNameString(String userNameString) {
        this.userNameString = userNameString;
    }
    
    
    public void Dimension(){
        panelSecundario.setSize(300, 600);
    }
            
   
    public void PosicionView(){      
       //icono del panel izquierdo (x, y, ancho, alto)
       iconPanel.setBounds(85, 8, 200, 200);
       
       //Imagem principal del JFrame
       imageCafeteria.setBounds(510, 100, 400, 400);
       
       
       //User name label
       userName.setBounds(50, 100, 200, 200);
       userName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
       userName.setVerticalAlignment(javax.swing.SwingConstants.CENTER); 
          
       //nombre del sitema panel izquierdo (x, y, ancho, alto)
       nameAplicacion.setBounds(60, 160, 400, 400);
              
       //Boton cerrar
       cerrarButton.setBounds(960, 10, 20, 20);
       
       //Boton minimizar
       miniButton.setBounds(920, 8, 30, 25);  
       
       //Boton de servicios
       servicios.setBounds(2, 234, 295, 90);
       
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
       
       //Boton de ventas
       ventas.setBounds(350, 150, 150, 150);
       
       //Label ventas
       ventasLabel.setBounds(400, 240, 150, 150);
       
       
       //Clientes
       cliente.setBounds(575, 150, 150, 150);
       
       //Clientes label
       clientesLabel.setBounds(610, 240, 150, 150);
       
       
       //Cafeterias
       cafeteria.setBounds(790, 150, 150, 150);
       
       //Cafeteria Label
       cafeteriaLabel.setBounds(815, 240, 150, 150);
       
       
       //Proveedores
       proveedores.setBounds(350, 380, 150, 150);
       
       
       //Proveedores label
       proovedoresLabel.setBounds(358, 475, 150, 150);
       
       //Inventario
       inventario.setBounds(575, 380, 150, 150);
       
       //Inventario label
       inventarioLabel.setBounds(600, 475, 150, 150);
       
       
       //Articulo button
       articuloButton.setBounds(790, 380, 150, 150);
       
       //Articulo label
       articuloLabel.setBounds(815, 475, 150, 150);
       
       
    }
    
   
    public void DisenoView(){
       Color colorPrincipal = res.Colorear(255, 115, 0);//res.Colorear(0, 30, 52);
       Color colorSecundario = res.Colorear(0, 30, 52);//res.Colorear(0, 169, 178);
       Font labelFont = res.Fuente("Arial", 1 , 18);
       
       panelPrincipal.setBackground(Color.WHITE);
       panelSecundario.setBackground(colorSecundario);
       
       //Icon panel 
       iconPanel.setIcon(res.ColocarImagen("/view/imagenes/user.png"));
       
       //Imagen cafeteria
       imageCafeteria.setIcon(res.ColocarImagen("/view/imagenes/cliente.png"));
       
       
       //Nombre usuario
       userName.setFont(res.Fuente("Arial", 1 , 25));
       userName.setForeground(Color.white);
       
       userName.setHorizontalAlignment(JLabel.CENTER);
       
       //Nombre app
       nameAplicacion.setFont(res.Fuente("Arial", 1 , 25));
       nameAplicacion.setForeground(Color.white);   
       
  
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
       servicios.setBackground(colorPrincipal);
       servicios.setForeground(Color.white);
       servicios.setBorder(null);
       servicios.setFont(res.Fuente("Arial", 1 , 16));
       servicios.setFocusable(false);
       
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
       
       //FF7300
    
       //Boton de ventas
       ventas.setIcon(res.ColocarImagen("/view/imagenes/ventas.png"));
       ventas.setBackground(null);
       ventas.setBorder(null);
       ventas.setFocusable(false);
       
       //Boton de cliente
       cliente.setIcon(res.ColocarImagen("/view/imagenes/cliente.png"));
       cliente.setBackground(null);
       cliente.setBorder(null);
       cliente.setFocusable(false);
       
       //Boton de cafeteria
       cafeteria.setIcon(res.ColocarImagen("/view/imagenes/building.png"));
       cafeteria.setBackground(null);
       cafeteria.setBorder(null);
       cafeteria.setFocusable(false);
       
       
       //Boton de proveedores
       proveedores.setIcon(res.ColocarImagen("/view/imagenes/proveedores.png"));
       proveedores.setBackground(null);
       proveedores.setBorder(null);
       proveedores.setFocusable(false);
       
         
       //Boton de inventario
       inventario.setIcon(res.ColocarImagen("/view/imagenes/inventario.png"));
       inventario.setBackground(null);
       inventario.setBorder(null);
       inventario.setFocusable(false);
       
       
      //Boton de articulos
       articuloButton.setIcon(res.ColocarImagen("/view/imagenes/cafeteria.png"));
       articuloButton.setBackground(null);
       articuloButton.setBorder(null);
       articuloButton.setFocusable(false);
       
       
       
       //Label ventas
       ventasLabel.setFont(labelFont);
       ventasLabel.setForeground(colorPrincipal);
       
       //Label cliente
       clientesLabel.setFont(labelFont);
       clientesLabel.setForeground(colorPrincipal);
       
       //Label cafeteria
       cafeteriaLabel.setFont(labelFont);
       cafeteriaLabel.setForeground(colorPrincipal);
       
       //Label proveedores
       proovedoresLabel.setFont(labelFont);
       proovedoresLabel.setForeground(colorPrincipal);
       
       //Label inventario
       inventarioLabel.setFont(labelFont);
       inventarioLabel.setForeground(colorPrincipal);
       
       //Label articulo
       articuloLabel.setFont(labelFont);
       articuloLabel.setForeground(colorPrincipal);
    }
}
