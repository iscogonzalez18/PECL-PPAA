/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entradas;

import Actividades.ZonaComun;
import Clases.Campamento;
import GUI.ListaNiños;
import Threads.Niño;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
        this.cola = cola;
        this.zonaComun= zonaComun;
        this.cerrojo = cerrojo;
        this.norte = norte;
    }
    
    
    /**
    public void entradaNorte(Niño n)
    {
        cerrojo.lock();
        try{
            colaNorte.meter(n);
            while(!abiertoNorte)
            {
                Norteabierto.await();
            }
            while(this.capacidadActual == this.capacidadDisponible)
            {
                norte.await();
            }
            colaNorte.sacar(n);
            zonaComun.entrar(n);
            System.out.println("Persona noseque entrando por NORTE. Ocupación: " + capacidadActual + "Colas: " + colaNorte.tamaño()+ ";"+ colaSur.tamaño());
            
        }
        catch(InterruptedException e){}
        finally{
            cerrojo.unlock();
        }
    }**/
}
