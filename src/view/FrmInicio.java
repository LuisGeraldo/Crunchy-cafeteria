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
import view.cruds.FrmMarcas;


/**
 *
 * @author luis
 */
public class FrmInicio extends JFrame{
    
    Utileria res; 
    public JPanel panelPrincipal = null;
    public JPanel panelSecundario = null;
    public JPanel panelServicio = null;
    public JPanel panelMantenimiento = null;
    
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
 
    
    //Mantenimiento controles
     public JButton marcaButton;
     public JLabel marcaLabel;
    
    
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
        panelServicio = new JPanel();
        panelMantenimiento = new JPanel();

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
        panelServicio.setLayout(null);
        panelMantenimiento.setLayout(null);
       
        panelMantenimiento.setVisible(false);
        //Mantenimiento 
        marcaButton = new JButton();
        marcaLabel = new JLabel("Marcas");
        
      /**
        * Agregando el panel principal(Jpanel) al content 
        * Agregacion de los elementos en el Panel principal
        *
        */
        
        contenedor.add(panelPrincipal);
        panelPrincipal.add(panelSecundario);
        panelPrincipal.add(panelServicio);
        panelPrincipal.add(panelMantenimiento);
        
          
        panelPrincipal.add(cerrarButton);
        panelPrincipal.add(miniButton);
      //  panelPrincipal.add(imageCafeteria);
      
       // panelPrincipal.add(buscarButton);
      //panelPrincipal.add(buscarField);
        panelServicio.add(ventas);
        panelServicio.add(cliente);
        panelServicio.add(cafeteria);
        panelServicio.add(proveedores);
        panelServicio.add(inventario);
        panelServicio.add(articuloButton);
        
        //Label
        panelServicio.add(ventasLabel);
        panelServicio.add(clientesLabel);
        panelServicio.add(cafeteriaLabel);
        panelServicio.add(proovedoresLabel);
        panelServicio.add(inventarioLabel);
        panelServicio.add(articuloLabel);
                
        
        
        panelSecundario.add(iconPanel);
        panelSecundario.add(userName);
        //------------------------------------
        panelSecundario.add(mantenimiento);
        panelSecundario.add(ajustes);
        panelSecundario.add(ayuda);
        panelSecundario.add(servicios);
        
        
        //Panel mantenimiento
        panelMantenimiento.add(marcaButton);
        panelMantenimiento.add(marcaLabel);
         
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
                
                if(e.getSource() == mantenimiento){
                    panelMantenimiento.setVisible(true);
                    panelServicio.setVisible(false);
                    
                }
                
                 if(e.getSource() == servicios){
                    panelMantenimiento.setVisible(false);
                    panelServicio.setVisible(true);
                    
                }
                
                if(e.getSource() == marcaButton){
                    FrmMarcas marcas = new FrmMarcas();    
                    marcas.setVisible(true);
                    setVisible(false);
                    marcas.userName.setText(userName.getText());
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
        mantenimiento.addActionListener(listener);
        servicios.addActionListener(listener);
        marcaButton.addActionListener(listener);
        
    }
    
     public String getUserNameString() {
        return userNameString;
    }

    public void setUserNameString(String userNameString) {
        this.userNameString = userNameString;
    }
    
    
    public void Dimension(){
        panelSecundario.setSize(300, 600);
        panelServicio.setSize(1000, 600);
        panelMantenimiento.setSize(1000, 600);
    }
            
   
    public void PosicionView(){      
       //icono del panel izquierdo (x, y, ancho, alto)
       iconPanel.setBounds(85, 8, 200, 200);
       
       //Imagem principal del JFrame
       imageCafeteria.setBounds(510, 100, 400, 400);
       
       panelServicio.setBounds(0, 50, 1000, 600);
       
       panelMantenimiento.setBounds(0, 50, 1000, 600);
       
       
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
       ventas.setBounds(350, 50, 150, 150);
       
       //Label ventas
       ventasLabel.setBounds(400, 150, 150, 150);
       
       
       //Clientes
       cliente.setBounds(575, 50, 150, 150);
       
       //Clientes label
       clientesLabel.setBounds(610, 150, 150, 150);
       
       
       //Cafeterias
       cafeteria.setBounds(790, 50, 150, 150);
       
       //Cafeteria Label
       cafeteriaLabel.setBounds(815, 150, 150, 150);
       
       
       //Proveedores
       proveedores.setBounds(350, 280, 150, 150);
       
       
       //Proveedores label
       proovedoresLabel.setBounds(358, 375, 150, 150);
       
       //Inventario
       inventario.setBounds(575, 280, 150, 150);
       
       //Inventario label
       inventarioLabel.setBounds(600, 375, 150, 150);
       
       
       //Articulo button
       articuloButton.setBounds(790, 280, 150, 150);
       
       //Articulo label
       articuloLabel.setBounds(815, 375, 150, 150);
       
       //Marca mantenimiento
       marcaButton.setBounds(350, 50, 150, 150);
       marcaLabel.setBounds(385, 150, 150, 150);
       
    }
    
   
    public void DisenoView(){
       Color colorPrincipal = res.Colorear(255, 115, 0);//res.Colorear(0, 30, 52);
       Color colorSecundario = res.Colorear(0, 30, 52);//res.Colorear(0, 169, 178);
       Font labelFont = res.Fuente("Arial", 1 , 18);
       
       panelPrincipal.setBackground(Color.WHITE);
       panelSecundario.setBackground(colorSecundario);
       
       panelServicio.setBackground(Color.white);
       
       panelMantenimiento.setBackground(Color.white);
       
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
       
       
       
       //Panel mantenimiento
       
       //Boton de marca
       marcaButton.setIcon(res.ColocarImagen("/view/imagenes/cafeteria.png"));
       marcaButton.setBackground(null);
       marcaButton.setBorder(null);
       marcaButton.setFocusable(false);
       
        //Label marcas
       marcaLabel.setFont(labelFont);
       marcaLabel.setForeground(colorPrincipal);
       
    }
}
