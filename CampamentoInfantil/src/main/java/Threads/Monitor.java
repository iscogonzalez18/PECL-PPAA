/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Clases.Campamento;

/**
 *
 * @author Francisco
 */
public class Monitor extends Thread{
    
    private String identificador; //MX
    private int num;
    private Campamento camp;
    
    public Monitor( int num )
    {
        this.identificador = "M" + Integer.toString(num); //MX
        this.num = num;
    }

    public String getIdentificador() {
        return identificador;
    }

    public int getNum() {
        return num;
    }    
    
}
