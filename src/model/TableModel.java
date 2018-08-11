/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Utilities;

/**
 *
 * @author luis
 */

public class TableModel {
   public DefaultTableModel modelo2;
    Utileria res;
    JScrollPane scrollPane = null;
   

    public DefaultTableModel getModelo2() {
        return modelo2;
    }

    public void setModelo2(DefaultTableModel modelo2) {
        this.modelo2 = modelo2;
    }
     
     public TableModel(){
       res = new Utileria();
     }
    
  public void CrearModelo2(JTable tabla, String [] header){
        Color colorPrincipal = res.Colorear(255, 115, 0);//res.Colorear(0, 30, 52);
        try {
           modelo2 = (new DefaultTableModel( null, header){
               Class[] types = new Class [] {
                   java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
             };
               
        boolean[] canEdit = new boolean [] {
        
            false,  false, false, false
        };
        
        @Override
        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex){
            return canEdit [colIndex];
        }
        });
           
                           
            tabla.setModel(modelo2);
            tabla.getTableHeader().setBackground(colorPrincipal);
            tabla.getTableHeader().setForeground(Color.white);
            tabla.getTableHeader().setFont(res.Fuente("Arial", 0, 13));
            tabla.getTableHeader().getColumnModel().setColumnMargin(5);
            
            tabla.setForeground(Color.BLACK);
            tabla.setFont(res.Fuente("Arial", 0, 14));
            tabla.setBorder(null);
            tabla.setSelectionBackground(res.Colorear(254, 147, 59));
            tabla.setSelectionForeground(Color.white);
            tabla.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, colorPrincipal));
     
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString()+"error2");
       }
    }
}
