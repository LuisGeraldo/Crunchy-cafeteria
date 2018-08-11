/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prjcafeteria;


import controller.EstadoJpaController;
import controller.MarcaJpaController;
import entidades.Estado;
import entidades.Marca;
import view.FrmInicio;
import view.FrmLogin;
import view.FrmVenta;
import view.cruds.FrmMarcas;



/**
 *
 * @author luis
 */
public class PrjCafeteria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       new FrmLogin().setVisible(true);      
       
    }
    
}
