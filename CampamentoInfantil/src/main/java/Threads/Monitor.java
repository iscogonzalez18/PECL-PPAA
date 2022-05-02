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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Francisco
 */
public class Monitor extends Thread{
    
    private String identificador; //MX
    private int num;
    private Campamento camp;
    private int contador=0;
    private EntradaNorte entradaNorte;
    private EntradaSur entradaSur;
    private Lock cerrojo;
    private AtomicInteger alternanciaMonitores;
    
    public Monitor( int num, Campamento camp, EntradaNorte entradaNorte, EntradaSur entradaSur, AtomicInteger alternanciaMonitores)
    {
        this.num = num;
        this.identificador = "M" + Integer.toString(num); //MX
        this.camp = camp;
        this.entradaNorte = entradaNorte;
        this.entradaSur = entradaSur;
        this.cerrojo = new ReentrantLock();
        this.alternanciaMonitores = alternanciaMonitores;
    }

    public String getIdentificador() {
        return identificador;
    }

    public int getNum() {
        return num;
    }    

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
    
    public synchronized void sumaActividad(){
        contador++;
    }
    
    public void run()
    {
        cerrojo.lock();
        try
        {
            if(alternanciaMonitores.get() == 1)
            {
                alternanciaMonitores.getAndAdd(0);
                entradaNorte.entrarMonitor(this);
            }
            else
            {
                alternanciaMonitores.getAndAdd(1);
                entradaSur.entrarMonitor(this);
            }
        }
        finally
        {
            cerrojo.unlock();
        }       
    }
}
