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
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco
 */
public class EntradaNorte {
    
    private AtomicInteger ocupacion;
    private boolean abierto;
    private ListaNiños cola;
    private ZonaComun zonaComun;
    private Lock cerrojo;
    private Condition norte;

    public EntradaNorte(AtomicInteger ocupacion, ListaNiños cola, ZonaComun zonaComun, Lock cerrojo, Condition norte) {
        this.ocupacion = ocupacion;
        this.abierto = false;
        this.cola = cola;
        this.zonaComun= zonaComun;
        this.cerrojo = cerrojo;
        this.norte = norte;
    }
    
    public void entrarNiño(Niño n)
    {
        cerrojo.lock();
        try
        {
            while(this.ocupacion.get()== 50 || !abierto)
            {
                cola.meter(n);
                norte.await(); 
            }
            cola.sacar(n);
            ocupacion.incrementAndGet();
            System.out.println("Niño " + n.getIdentificador() +" entra por NORTE. Ocupación: " + ocupacion.get() + "\nCola Norte --> " + cola.tamaño());
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
                System.out.println("Puerta NORTE cerrada, monitor "+m.getIdentificador()+" abriendo...");
                Thread.sleep(500+ r.nextInt(1000));
                abierto = true;
                norte.signalAll();
            }
            System.out.println("Monitor "+m.getIdentificador()+" entra por NORTE");
        }
        catch(InterruptedException e){}
        finally{
            cerrojo.unlock();
        }
        
    }
}
