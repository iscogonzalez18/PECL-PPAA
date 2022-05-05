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
    
    private Plazas plazas;
    private boolean abierto;
    private ListaNiños cola;
    private ZonaComun zonaComun;
    private Lock cerrojo;
    private Condition sur;

    public EntradaSur(Plazas plazas, ListaNiños cola, ZonaComun zonaComun, Lock cerrojo, Condition sur) {
        this.plazas = plazas;
        this.cola = cola;
        this.zonaComun = zonaComun;
        this.cerrojo = cerrojo;
        this.sur = sur;
    }
    
     public void entrarNiño(Niño n)
    {
        cerrojo.lock();
        try{
            while(plazas.getOcupacion()== 50 || !abierto)
            {
                cola.meter(n);
                sur.await(); 
            }
            cola.sacar(n);
            plazas.incrementar();
            System.out.println("Niño " + n.getIdentificador() +" entra por SUR. Ocupación: " + plazas.getOcupacion() + "\nCola SUR --> " + cola.tamaño());
            
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
                System.out.println("Puerta SUR cerrada, monitor "+m.getIdentificador()+" abriendo...");
                Thread.sleep(500+ r.nextInt(1000));
                abierto = true;
                sur.signalAll();
            }
            zonaComun.entrarMonitor(m);
            System.out.println("Monitor "+m.getIdentificador()+" entra por SUR");
        }
        catch(InterruptedException e){}
        finally{
            cerrojo.unlock();
        }
    }
}
