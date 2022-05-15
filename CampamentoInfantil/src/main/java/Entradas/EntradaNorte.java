/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entradas;

import Actividades.ZonaComun;
import Clases.Campamento;
import ClasesAsociadasJFrame.ListaNiños;
import ClasesAsociadasJFrame.Plazas;
import EscribirLog.Log;
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
    
    private Plazas plazas;
    private boolean abierto;
    private ListaNiños cola;
    private ZonaComun zonaComun;
    private Lock cerrojo;
    private Condition norte;

    public EntradaNorte(Plazas plazas, ListaNiños cola, ZonaComun zonaComun, Lock cerrojo, Condition norte) {
        this.plazas = plazas;
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
            cola.meter(n);
            while(plazas.getOcupacion()== 50 || !abierto)
            {
                norte.await(); 
            }
            cola.sacar(n);
            plazas.incrementar();          
            Log.escribirLog("Niño " + n.getIdentificador() +" entra por NORTE. Ocupación: " + plazas.getOcupacion() + "\nCola Norte --> " + cola.tamaño());
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
                Log.escribirLog("Puerta NORTE cerrada, monitor "+m.getIdentificador()+" abriendo...");
                Thread.sleep(500+ r.nextInt(1000));
                abierto = true;
                norte.signalAll();
            }
            Log.escribirLog("Monitor "+m.getIdentificador()+" entra por NORTE");
        }
        catch(InterruptedException e){}
        finally{
            cerrojo.unlock();
        }
        
    }
}
