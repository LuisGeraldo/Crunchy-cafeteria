/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static model.Conexion.Cont;

/**
 *
 * @author luis
 */
public class Validacion extends Conexion{
    public boolean Validar(String userName, String contrasena){
        PreparedStatement Validacion;
        ResultSet Result;
        
        String SentenciaSQL = "select * from usuario where UserName = ? and uPassword = ?";
        try{
            Validacion = Cont.prepareStatement(SentenciaSQL);
            Validacion.setString(1, userName);
            Validacion.setString(2, contrasena);
            Result = Validacion.executeQuery();
            
            if(Result.absolute(1)){
              return true;    
           }
            
        }catch(SQLException e){
            System.out.println("Error al buscar el usuario" + e);
            
        }
        return false;
    }

    public boolean ValidarUsuario(String userName){
        PreparedStatement Validacion;
        ResultSet Result;
        
        String SentenciaSQL = "select * from usuario where UserName = ?";
        try{
            Validacion = Cont.prepareStatement(SentenciaSQL);
            Validacion.setString(1, userName);
            Result = Validacion.executeQuery();
            
            if(Result.absolute(1)){
              return true;    
           }
            
        }catch(SQLException e){
            System.out.println("Error al buscar el usuario" + e);
            
        }
        return false;
    }
}
