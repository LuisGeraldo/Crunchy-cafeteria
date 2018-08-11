/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author luis
 */
public class Registrarse extends Conexion{
    public boolean Registrar(String nombre,  String correo, String password, int tipoUsuario){
        
        PreparedStatement Registrar;
      
        String SentenciaSQL1 = "insert into usuario(UserName, Email, uPassword, idTipoUsuario) values(?,?,?,?)";
      
        try{
           Registrar = Cont.prepareStatement(SentenciaSQL1);
            Registrar.setString(1, nombre);
            Registrar.setString(2, correo);
            Registrar.setString(3, password);
            Registrar.setInt(4, tipoUsuario);
           
            if(Registrar.executeUpdate() == 1){
                return true;
            }
            
        }catch(SQLException e){
            System.out.println("Error al registrar datos" + e);
        }
        
        return false;   
    }    
}
