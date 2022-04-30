/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

/**
 *
 * @author Francisco
 */   
public class Niño extends Thread {
    
    private String identificador; //NXXXX
    private int num;
    private int contador=0,actividades=0; //Contador de actividades

    public Niño(int num) 
    {
        this.num = num;
        this.identificador = generaNombre(num);
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
        
    }
    
    
}
