/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Clases.Campamento;
import Entradas.EntradaNorte;
import Entradas.EntradaSur;
import java.util.Random;

/**
 *
 * @author Francisco
 */   
public class Niño extends Thread {
    
    private String identificador; //NXXXX
    private int num;
    private Campamento camp;
    private int contador=0,actividades=0; //Contador de actividades
    private EntradaNorte entradaNorte;
    private EntradaSur entradaSur;

    public Niño(int num, Campamento camp,EntradaNorte entradaNorte, EntradaSur entradaSur) 
    {
        this.num = num;
        this.identificador = generaNombre(num);
        this.camp = camp;
        this.entradaNorte = entradaNorte;
        this.entradaSur = entradaSur;
    }
    
    public String generaNombre(int n)
    {
        String id;
        if (n<10){
            Integer.toString(n);
            id="N000"+n;
        }else if(n<100){
            Integer.toString(n);
            id="N00"+n;
        }else if(n<1000){
            Integer.toString(n);
            id="N0"+n;
        }else{
            Integer.toString(n);
            id="N"+n;
        }
        return id;
    }

    public int getActividades() {
        return actividades;
    }

    public void setActividades(int actividades) {
        this.actividades = actividades;
    }

    public int getContador() 
    {
        return contador;
    }     
    
    public String getIdentificador()
    {
        return identificador;
    }
    
    public synchronized void sumaActividad(int n)
    {
        contador+=n;
        actividades+=n;
    }
    
    public void run()
    {
        Random r = new Random();
        if(r.nextDouble()<0.5)
        {
            entradaNorte.entrarNiño(this);
        }
        else{
            entradaSur.entrarNiño(this);
        }
    }
    
    
}
