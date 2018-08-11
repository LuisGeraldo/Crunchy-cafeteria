/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.cruds;

import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;
import controller.EstadoJpaController;
import controller.MarcaJpaController;
import entidades.Estado;
import entidades.Marca;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.Utileria;
import view.FrmRegistrarse;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import model.TableModel;


/**
 *
 * @author luis
 */
public class FrmMarcas extends JFrame{
    Utileria res; 
    MarcaJpaController marcasController;
    EstadoJpaController estadoController;
    Marca marcaEntity;
    Estado estadoEntity;
    
    public JPanel panelPrincipal = null;
    public JPanel panelSecundario = null;
    public JPanel panelCrear = null;
    public JPanel panelConsultar = null;
    
    //Botones de cerrar y minimizar
    public JButton cerrarButton;
    public JButton miniButton;
    
    //Botones del panel secundario
    public JButton consultarButton;
    public JButton crearButton;
    public JButton ajustes;
    public JButton ayuda;
    public JButton crearMarca;
    //Boton buscar
    public JButton buscarButton;
           
    public JLabel iconUserName = null;
    public JLabel userName = null;  
    public JLabel nombreFrm = null;
    
    public JLabel criterioLabel = null;
    public JLabel txtBuscarLabel = null;
    
    public JLabel nombreMarcaLabel = null;
    public JLabel descripcionLabel = null;
    public JLabel estadoLabel = null;
    
    public JTextField nombreField = null;
    public JTextArea descripcionArea = null;
    public JComboBox estadoCombo = null;
    
    
    
    public JComboBox comboxCriterio = null;
   
    public Container contenedor = null;
    public JTextField buscarField = null; 
    
    public JTable marcasTable = null;
    public JScrollPane scrollTable = null; 
    public TableRowSorter trsfiltro;

    ActionListener listener;
    FrmRegistrarse frmRegistrarse;
    TableModel modeloTable = null;
    
    public String userNameString;
  
  
    
    public FrmMarcas(){
     
     /**
       * Inicializacion del JFrame
       * 
       */   
         
        super("Marcas");
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
        panelConsultar = new JPanel();
        panelCrear = new JPanel();

        cerrarButton = new JButton();
        miniButton = new JButton();
        //--------------------------------
        consultarButton = new JButton("CONSULTAR MARCAS");
        
        crearButton = new JButton("CREAR MARCAS");
        ajustes = new JButton("AJUSTES");
        ayuda = new JButton("AYUDA");
        crearMarca = new JButton("CREAR MARCA");
       
        buscarButton = new JButton();  
        buscarField = new JTextField();
              
        iconUserName = new JLabel();
        userName = new JLabel("Nombre usuario");
        nombreFrm = new JLabel("MARCAS");
        criterioLabel = new JLabel("Criterio busqueda: ");
        txtBuscarLabel = new JLabel("Marca a buscar: ");
        
        comboxCriterio = new JComboBox();
     
        comboxCriterio.addItem("Nombre");
        comboxCriterio.addItem("Descripcion");
        comboxCriterio.addItem("Estado");
         
        comboxCriterio.setSelectedIndex(0);
        
        nombreMarcaLabel = new JLabel("Nombre: ");
        descripcionLabel = new JLabel("Descripcion: ");
        estadoLabel = new JLabel("Estado: ");
    
        nombreField = new JTextField();
        descripcionArea = new JTextArea();
        estadoCombo = new JComboBox();
        
        //-------------------Inicializar clases-------------------------
        
        res = new Utileria();
        frmRegistrarse = new FrmRegistrarse();
        
        marcasController = new MarcaJpaController();
        estadoController = new EstadoJpaController();
        marcaEntity = new Marca();
        estadoEntity = new Estado();
        
        modeloTable = new TableModel();
        marcasTable = new JTable();
    
        scrollTable = new JScrollPane();
        
        panelPrincipal.setLayout(null);
        panelSecundario.setLayout(null);
        panelCrear.setLayout(null);
        panelConsultar.setLayout(null);
        
          
      /**
        * Agregando el panel principal(Jpanel) al content 
        * Agregacion de los elementos en el Panel principal
        *
        */
        
        contenedor.add(panelPrincipal);
        panelPrincipal.add(panelSecundario);
        panelPrincipal.add(panelCrear);
        panelPrincipal.add(panelConsultar); 
        
        panelPrincipal.add(cerrarButton);
        panelPrincipal.add(miniButton);
        panelPrincipal.add(nombreFrm);
       
        scrollTable.setViewportView(marcasTable);
        modeloTable.CrearModelo2(marcasTable, new String []{"NOMBRE", "DESCRIPCION", "ESTADO"});
        
        LlenarTablas();
        LlenarCombo();
        panelConsultar.add(buscarButton);
        panelConsultar.add(buscarField);
        panelConsultar.add(scrollTable);
        panelConsultar.add(comboxCriterio);
        panelConsultar.add(txtBuscarLabel);
        panelConsultar.add(criterioLabel);
      
        //Label            
        panelSecundario.add(iconUserName);
        panelSecundario.add(userName);
        //------------------------------------
        panelSecundario.add(crearButton);
        panelSecundario.add(ajustes);
        panelSecundario.add(ayuda);
        panelSecundario.add(consultarButton);
        
        panelConsultar.setVisible(true); //-----------------------------------
        panelCrear.setVisible(false);
        
        panelCrear.add(nombreMarcaLabel);
        panelCrear.add(descripcionLabel);
        panelCrear.add(estadoLabel);
        panelCrear.add(nombreField);
        panelCrear.add(descripcionArea);
        panelCrear.add(estadoCombo);
        panelCrear.add(crearMarca);
       
        //panelCrear.add(clienteCombo);
        
        
       
       /**
         * Funciones para posicionar, dimensionar y dar estilos a los elementos
         * 
         **/
              
        PosicionView();
        Dimension();
        DisenoView();
        Eventos();
    }
    
        
    public void Dimension(){
        panelSecundario.setSize(300, 600);
        panelCrear.setSize(700, 500);
        panelConsultar.setSize(700,500);
        
        panelCrear.setBackground(Color.white);
        panelConsultar.setBackground(Color.white);
    }
            
   
    public void PosicionView(){      
       panelCrear.setLocation(300, 100);
       panelConsultar.setLocation(300, 100);
        
       //icono del panel izquierdo (x, y, ancho, alto)
       iconUserName.setBounds(85, 8, 200, 200);
       
       //User name label
       userName.setBounds(50, 100, 200, 200);
       userName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
       userName.setVerticalAlignment(javax.swing.SwingConstants.CENTER); 
         
       //Boton cerrar
       cerrarButton.setBounds(960, 10, 20, 20);
       
       //Boton minimizar
       miniButton.setBounds(920, 8, 30, 25); 
       
       //Nombre del panel
       nombreFrm.setBounds(570, 30, 200, 50); 
       
       //label field
       txtBuscarLabel.setBounds(70, 10, 200, 10);
       
       //label criterio
       criterioLabel.setBounds(70, 60, 200, 10);
       
       //Boton de buscar
       buscarButton.setBounds(540, 0, 50, 35);
       
       //Field de buscar
       buscarField.setBounds(220, 0, 300, 30);
       
       //Boton de servicios
       consultarButton.setBounds(2, 234, 295, 90);
       
       //Boton mantenimiento
       crearButton.setBounds(2, 326, 295, 90);
       
       //Boton de ajustes
       ajustes.setBounds(2, 418, 295, 90);
       
       //Boton de ayuda
       ayuda.setBounds(2, 510, 295, 88);

       //Empleado field
       comboxCriterio.setBounds(220, 50, 300, 30);
       
       //marcas table
       scrollTable.setBounds(10, 130, 675, 250);
       
       //Nombre marca label
       nombreMarcaLabel.setBounds(100, 50, 100, 50);
       nombreField.setBounds(250, 60, 300, 35);
  
       //Descripcion label
       descripcionLabel.setBounds(100, 110, 150, 50);
       descripcionArea.setBounds(250, 125, 300, 90);
       
       //estado label
        estadoLabel.setBounds(100, 225, 100, 50);
        estadoCombo.setBounds(250, 240, 300, 35);
   
        //Crear marca
        crearMarca.setBounds(250, 310, 300, 40);
    }
    
   
    public void DisenoView(){
       Color colorPrincipal = res.Colorear(255, 115, 0);    //res.Colorear(0, 30, 52);
       Color colorSecundario = res.Colorear(0, 30, 52);    //res.Colorear(0, 169, 178);
       Font labelFont = res.Fuente("Arial", 1 , 16);
       Font nameFrm = res.Fuente("Arial", 0, 35);
       
       panelPrincipal.setBackground(Color.WHITE);
       panelSecundario.setBackground(colorSecundario);
       //panelCrear.setBackground(Color.blue);
       
       //Icon panel 
       iconUserName.setIcon(res.ColocarImagen("/view/imagenes/user.png"));
       
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
 
       //nombre frm
       nombreFrm.setFont(nameFrm);
       nombreFrm.setForeground(colorPrincipal);
      
       //Boton crear 
       crearButton.setBackground(colorPrincipal);
       crearButton.setForeground(Color.white);
       crearButton.setBorder(null);
       crearButton.setFont(res.Fuente("Arial", 1 , 16));
       crearButton.setFocusable(false);

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
     
       //Boton consultar
       consultarButton.setBackground(colorPrincipal);
       consultarButton.setForeground(Color.white);
       consultarButton.setBorder(null);
       consultarButton.setFont(res.Fuente("Arial", 1 , 16));
       consultarButton.setFocusable(false);
       
       //Boton buscar 
       buscarButton.setIcon(res.ColocarImagenScala("/view/imagenes/buscar.png", 33, 33));
       buscarButton.setBackground(null);
       buscarButton.setBorder(null);
       buscarButton.setFocusable(false);
           
       //Field buscar
       buscarField.setBackground(null);
       buscarField.setForeground(colorPrincipal);
       buscarField.setFont(res.Fuente("Arial", 0, 15));
       
       buscarField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, res.Colorear(255, 115, 0)));
       
       //Field combo cliente
       comboxCriterio.setBackground(null);
       comboxCriterio.setBorder(null);
       comboxCriterio.setUI(new WindowsComboBoxUI());
       comboxCriterio.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, res.Colorear(255, 115, 0)));
       comboxCriterio.setForeground(colorPrincipal);
    
       //Tabla header
       scrollTable.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, colorPrincipal));
       marcasTable.setFillsViewportHeight(true);
       scrollTable.setFont(labelFont);
              
       //combo label
       criterioLabel.setForeground(colorPrincipal);
       
       //field buscar label
       txtBuscarLabel.setForeground(colorPrincipal); 
       
       //Nombre Label
       nombreMarcaLabel.setFont(labelFont);
  
      //Nombre Field
       nombreField.setBackground(null);
       nombreField.setForeground(colorPrincipal);
       nombreField.setFont(res.Fuente("Arial", 0, 15));
       nombreField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, res.Colorear(255, 115, 0)));
      
       //Descripcion Label
       descripcionLabel.setFont(labelFont);
       
       //Descripcion Area
       descripcionArea.setBackground(null);
       descripcionArea.setForeground(colorPrincipal);
       descripcionArea.setFont(res.Fuente("Arial", 0, 15));
       descripcionArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, res.Colorear(255, 115, 0)));
    
       //Estado field
       estadoLabel.setFont(labelFont);
       
       //Field combo estado
       estadoCombo.setBackground(null);
       estadoCombo.setBorder(null);
       estadoCombo.setUI(new WindowsComboBoxUI());
       estadoCombo.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, res.Colorear(255, 115, 0)));
       estadoCombo.setForeground(colorPrincipal);
       
       //Crear marca
       crearMarca.setBackground(colorPrincipal);
       crearMarca.setForeground(Color.white);
       crearMarca.setBorder(null);
       crearMarca.setFont(res.Fuente("Arial", 0 , 16));
       crearMarca.setFocusable(false);
    }
    
            
    public void LlenarTablas(){
       try{
           Object[] o = null;
           List<Marca> marcas;
           marcas = marcasController.findMarcaEntities();
           
           for (int i = 0; i < marcas.size(); i++) {
               modeloTable.getModelo2().addRow(o);
               modeloTable.getModelo2().setValueAt(marcas.get(i).getNombre(), i, 0);
               modeloTable.getModelo2().setValueAt(marcas.get(i).getDescripcion(), i, 1);
               modeloTable.getModelo2().setValueAt(marcas.get(i).getIdEstado().getDescripcion(), i, 2);   
           }
           
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null, "No se pudo llenar la tabla" + ex);
       }    
    }    
    
     public void LlenarCombo(){
       EstadoJpaController estadosController = new EstadoJpaController();
       try{
           List<Estado> estado;
           estado = estadosController.findEstadoEntities();
           
           for (int i = 0; i < estado.size(); i++) {
               estadoCombo.addItem(estado.get(i).getDescripcion());
              
           }
          }catch(Exception ex){
           JOptionPane.showMessageDialog(null, "No se pudo llenar la tabla" + ex);
       }    
    }    
     
     public void Eventos(){
        EstadoJpaController estadosController = new EstadoJpaController();
         listener = new ActionListener(){    
            @Override
            public void actionPerformed(ActionEvent e) {
       
               if(e.getSource() == crearButton){
                   panelCrear.setVisible(true);  
                   panelConsultar.setVisible(false);
               } 
                
               if(e.getSource() == consultarButton){
                   panelConsultar.setVisible(true);
                   panelCrear.setVisible(false);
               }
               
               
               if(e.getSource() == crearMarca){
                   String nombre = nombreField.getText();
                   String descripcion = descripcionArea.getText();
                   int combo = (estadoCombo.getSelectedIndex() +1);
                   
                   estadoEntity = estadosController.findEstado(combo);
                   
                   try{   
                      marcaEntity.setNombre(nombre);
                      marcaEntity.setDescripcion(descripcion);
                      marcaEntity.setIdEstado(estadoEntity);
                      marcasController.create(marcaEntity);
                      
                      JOptionPane.showMessageDialog(null, "Se ha creado correctamente");
                      LimpiarField();
                   }catch(RollbackException ex){
                       JOptionPane.showMessageDialog(null, "Ocurrio un error al guardar"+ ex);
                   }
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
        consultarButton.addActionListener(listener);
        crearButton.addActionListener(listener);
        crearMarca.addActionListener(listener);
        res.evento(buscarField, marcasTable, comboxCriterio);
     }
     
     
     public void LimpiarField(){
          nombreField.setText("");
          descripcionArea.setText("");
                      
     }
}