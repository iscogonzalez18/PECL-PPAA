/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import GUI.ListaNiños;
import Threads.Niño;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Francisco
 */
public class EntradaNorte {
    
    private boolean abierto;
    private ListaNiños cola;
    private Lock cerrojo = new ReentrantLock();
    private Condition norte;
    private Condition paso = cerrojo.newCondition();
    
    public EntradaNorte(ListaNiños cola, Condition norte)
    {
        this.abierto = false;
        this.cola = cola;
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
