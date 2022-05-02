/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entradas;

import Actividades.ZonaComun;
import Clases.Campamento;
import GUI.ListaNiños;
import Threads.Monitor;
import Threads.Niño;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 *
 * @author Francisco
 */
public class EntradaSur {
    
    private AtomicInteger ocupacion;
    private boolean abierto;
    private ListaNiños cola;
    private ZonaComun zonaComun;
    private Lock cerrojo;
    private Condition sur;

    public EntradaSur(AtomicInteger ocupacion, ListaNiños cola, ZonaComun zonaComun, Lock cerrojo, Condition sur) {
        this.ocupacion = ocupacion;
        this.cola = cola;
        this.zonaComun = zonaComun;
        this.cerrojo = cerrojo;
        this.sur = sur;
    }
    
     public void entrarNiño(Niño n)
    {
        cerrojo.lock();
        try{
            while(this.ocupacion.get()== 50 || !abierto)
            {
                cola.meter(n);
                sur.await(); 
            }
            cola.sacar(n);
            zonaComun.entrarNiño(n);
            System.out.println("Persona noseque entrando por SUR. Ocupación: " + ocupacion.get() + "\nCola SUR --> " + cola.tamaño());
            
        }
        catch(InterruptedException e){}
        finally{
            cerrojo.unlock();
        }
    }
     
    public void entrarMonitor(Monitor m)
    {
        cerrojo.lock();
        try
        {
            if(!abierto)
            {
                Random r = new Random();
                Thread.sleep(500+ r.nextInt(1000));
                abierto = true;
                sur.signalAll();
            }
            zonaComun.entrarMonitor(m);
        }
        catch(InterruptedException e){}
        finally{
            cerrojo.unlock();
        }
    }
}
