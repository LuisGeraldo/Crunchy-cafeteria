/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Font;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author luis
 */
public class Utileria {
    public Utileria(){
        
    }
    
     public Font Fuente(String nameFont, int tipo,int fontSize){
        Font font = new Font(nameFont, tipo, fontSize);
        return font;
    }
     
      public Color Colorear(int r, int g, int b){
        Color color = new Color(r, g, b);
        return color;
    }
    
     public ImageIcon ColocarImagen(String path){
       URL url = this.getClass().getResource(path);
       ImageIcon icon = new ImageIcon(url);
       return icon;
    }
     
     public ImageIcon ColocarImagenScala(String path, int ancho, int alto){
       URL url = this.getClass().getResource(path);
       ImageIcon icon = new ImageIcon(url);
       ImageIcon iconoEscala = new ImageIcon(icon.getImage().getScaledInstance(ancho, alto, java.awt.Image.SCALE_DEFAULT));
       return iconoEscala;
    }
}
