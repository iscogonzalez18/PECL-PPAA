/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actividades;

import GUI.ListaMonitores;
import GUI.ListaNiños;
import Threads.Monitor;
import Threads.Niño;
import static java.lang.Thread.sleep;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco
 */
public class ZonaComun {
    //albaricoque
    
    private ListaNiños niños;
    private ListaMonitores monitores;
    private Lock cerrojo= new ReentrantLock();

    public ZonaComun(ListaNiños niños, ListaMonitores monitores) {
        this.niños = niños;
        this.monitores = monitores;
    }
    
    public synchronized void entrar(Niño n)
    {
        niños.meter(n);   
    }
    
    public synchronized void salir(Niño n)
    {
        niños.sacar(n);     
    }
    
    public void paseo (Monitor m){
        monitores.meter(m);
        try {
            sleep(1000+(int)(1001*Math.random()));
        } catch (InterruptedException ex) {
            Logger.getLogger(ZonaComun.class.getName()).log(Level.SEVERE, null, ex);
        }
        monitores.sacar(m);
    }
    
    public int tamañoNiños()
    {
        return niños.tamaño();
    }
    
    public int tamañoMonitores()
    {
        return monitores.tamaño();
    }
}
