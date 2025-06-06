/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gamecartas;

/**
 *
 * @author David
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameCartas {
    
    //Constantes del juego
    private static final int TotalCartas = 36;
    private static final int TotalPares = 18;
    private static final int MaxIntentos = 10;
    private static final int TiempoEspera = 2000; //2 Segundos
    
    
    //Variables del juego
    private int[] Tablero;
    private boolean[] CartasReveladas;
    private int Intentos;
    private int ParesEncontrados;
    private JButton PrimerBoton;
    private JButton SegundoBoton;
    private int PrimerValor;
    private int SegundoValor;
    private boolean EsperandoSegundaCarta;
    private Timer Timer;
    
    //Referencias
    private JButton[] Botones;
    private PanelJuego PanelJuego;
    
    //Iconos
    private ImageIcon[] Iconos;
    private ImageIcon IconoOculto;
    
    /*
        Constructor
    */
    public GameCartas(PanelJuego Panel, JButton[] Botones) {
        this.PanelJuego = Panel;
        this.Botones = Botones;
        InicializarJuego();
    }
    
    /*
        Aqui van las imagenes
    */
    private void CargarImagenes() {
        Iconos = new ImageIcon[TotalPares];
        
        //Nombres de archivos de imagen
        String[] NombresArchivos = {
            "imga1.png", "imga2.png", "imga3.png", "imga4.png",
            "imga5.png", "imga6.png", "imga7.png", "imga8.png",
            "imga9.png", "imga9.png", "imga10.png", "imga11.png",
            "imga12.png", "imga13.png", "imga14.png", "imga15.png", "imga16.png",
            "imga17.png","imga18.png"
        };
        
        try {
            for (int i = 0; i < TotalPares; i++) {
                String RutaImagen = "/images/" + NombresArchivos[i];
                ImageIcon ImagenOriginal = new ImageIcon(getClass().getResource(RutaImagen));
                
                //Redimensional imagen a 100x100 pixeles
                Image img = ImagenOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                Iconos[i] = new ImageIcon(img);
            }
            
            //Imagen para cartas ocultas
            ImageIcon ImagenOculta = new ImageIcon(getClass().getResource("images/oculta.PNG"));
            Image imgOculta = ImagenOculta.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            IconoOculto = new ImageIcon(imgOculta);
            
        } catch (Exception e) {
            System.out.println("Error cargando imagenes: " + e.getMessage());
        }
    } 
      
    /*
        Inicializa una nueva partida de juego
    */
    public void InicializarJuego() {
        CargarImagenes(); //Cargar las imagenes
        Tablero = new int[TotalCartas];
        CartasReveladas = new boolean[TotalCartas];
        Intentos = 0;
        ParesEncontrados = 0;
        PrimerBoton = null;
        SegundoBoton = null;
        EsperandoSegundaCarta = false;

        //Crear lista de valores para el tablero
        List<Integer> Valores = new ArrayList<>();
        for (int i = 0; i < TotalPares; i++) {
            //Se pone dos veces para que cada valor aparezca dos veces
            Valores.add(i);
            Valores.add(i);
        }

        //Mezclar aleatoriamente
        Collections.shuffle(Valores);

        //Llenar el tablero y configurara botones
        for (int i = 0; i < TotalCartas; i++) {
            Tablero[i] = Valores.get(i);
            CartasReveladas[i] = false;
            Botones[i].setIcon(IconoOculto);
            Botones[i].setEnabled(true);
            Botones[i].setText("");
        }
    }

    /*
        Manejar el clic en una carta del tablero
    */
    public void ManejarClicCarta(int Indice) {
        //Verificar si la carta ya esta revelada o is estamos esperando
        if (CartasReveladas[Indice] || EsperandoSegundaCarta) {
            return;
        }

        //Revelar la carta
        RevelarCarta(Indice);

        if (PrimerBoton == null) {
            //Primera carta seleccionada
            PrimerBoton = Botones[Indice];
            PrimerValor = Tablero[Indice];
        } else {
            //Segunda carta seleccionada
            SegundoBoton = Botones[Indice];
            SegundoValor = Tablero[Indice];

            Intentos++;
            EsperandoSegundaCarta = true;

            //Verificar si las cartas coinciden
            if (PrimerValor == SegundoValor) {
                //Par Encotnrado
                ParesEncontrados++;
                CartasReveladas[GetIndiceBoton(PrimerBoton)] = true;
                CartasReveladas[Indice] = true;

                //Deshabilitar los botones del par encontrado
                PrimerBoton.setEnabled(false);
                SegundoBoton.setEnabled(false);

                ResetearSeleccion();
                VerificarFinJuego();
            } else {

                //Ocultar las cartas despues de un delay
                Timer = new Timer(TiempoEspera, e -> {
                   OcultarCartas();
                   ResetearSeleccion();
                   VerificarFinJuego();
                   Timer.stop();
                });
                Timer.start();
            }
        }
    }

    /*
        Revela una carta mostrando su icono correspondiente
    */
    private void RevelarCarta(int Indice) {
        int Valor = Tablero[Indice];
        Botones[Indice].setIcon(Iconos[Valor]);
    }

    /*
        Ocultar las dos cartas seleccionadas
    */
    private void OcultarCartas() {
        if (PrimerBoton != null) {
            PrimerBoton.setIcon(IconoOculto);
        }
        if (SegundoBoton != null) {
            SegundoBoton.setIcon(IconoOculto);
        }
    }

    /*
        Resetea la seleccion de cartas
    */
    private void ResetearSeleccion() {
        PrimerBoton = null;
        SegundoBoton = null;
        EsperandoSegundaCarta = false;
    }
    
    /*
        Obtiene el indice de un boton en el arreglo
    */
    private int GetIndiceBoton(JButton Boton) {
        for (int i = 0; i < Botones.length; i++) {
            if (Botones[i] == Boton) {
                return i;
            }
        }
        return -1;
    }
    
    /*
        Verifica si el juego ha terminado
    */
    private void VerificarFinJuego() {
        if (ParesEncontrados == TotalPares) {
            JOptionPane.showMessageDialog(PanelJuego, "Completaste el Juego!" + "Intentos: " + Intentos + "/" + MaxIntentos + "Pares encotnrados: " + ParesEncontrados + "/" + TotalPares, "Ganaste!", JOptionPane.INFORMATION_MESSAGE);
            DeshabilitarTodosLosBotones();
        } else if (Intentos >= MaxIntentos) {
            //Juego perdido
            JOptionPane.showMessageDialog(PanelJuego, "Superaste el limite de " + MaxIntentos + "intentos.\n" + "Pares encontrados: " + ParesEncontrados + "/" + TotalPares, "Perdiste!", JOptionPane.INFORMATION_MESSAGE);
            DeshabilitarTodosLosBotones();
            RevelarTodasLasCartas();
        }
    }
    
    /*
        deshabilitar los botones
    */
    private void DeshabilitarTodosLosBotones() {
        for (JButton Boton : Botones) {
            Boton.setEnabled(false);
        }
    }
    
    /*
        Revelar todas las cartas restantes cuando se pierde
    */
    private void RevelarTodasLasCartas() {
        for (int i = 0; i < TotalCartas; i++) {
            if (!CartasReveladas[i]) {
                RevelarCarta(i);
            }
        }
    }
    
    /*
        Reiniciar el juego
    */
    public void ReiniciarJuego() {
        InicializarJuego();
    }
    
    /*
        Getters
    */
    
    /*
        Numero de intentos realizados
    */
    public int GetIntentos() {
        return Intentos;
    }
    
    /*
        Numero de pares encontrados
    */
    public int GetParesEncontrados() {
        return ParesEncontrados;
    }
    
    /*
        Maximo numero de intentos permitidos
    */
    public int GetMaxIntentos() {
        return MaxIntentos;
    }
    
    /*
        Total de pares en el juego
    */
    public int GetTotalPares() {
        return TotalPares;
    }
    
    /*
        Regresar un true si el juego ha terminado
    */
    public boolean IsJuegoTerminado() {
        return ParesEncontrados == TotalPares || Intentos >= MaxIntentos;
    }
}