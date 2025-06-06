/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamecartas;

import javax.swing.JButton;


/**
 *
 * @author David
 */
public class Cartas extends JButton {
    
    private String ImagenCarta;
    private boolean descubierta = false;
    
    public Cartas(String ImagenCarta){
        this.ImagenCarta= ImagenCarta;
        //ImageIcon icono= new ImageIcon(getClass().getResource("C:\\Users\\David\\Documents\\Documentos UNI\\I Jahre\\III Period\\Lab Progra\\RepositorioCompartido\\JuegoCartas\\GameCartas\\imagenes\\back.jpg"));
        
    }
    
    
    public void mostrarCarta(){
        //getImageIcon con la imagen descubierta que seria igual a ImagenCarta
        descubierta= true;
    }
    
    public void ocultarCarta(){
        //getImageIcon de la imagen oculta
        descubierta=false;
    }
    
    
    
    
    
    
    
}
