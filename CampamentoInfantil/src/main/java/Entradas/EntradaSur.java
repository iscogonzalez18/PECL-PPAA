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
    private Condition norte;

    public EntradaSur(AtomicInteger ocupacion, ListaNiños cola, ZonaComun zonaComun, Lock cerrojo, Condition norte) {
        this.ocupacion = ocupacion;
        this.cola = cola;
        this.zonaComun = zonaComun;
        this.cerrojo = cerrojo;
        this.norte = norte;
    }
    
    
    /**
     public void entradaSur(Niño n)
    {
        cerrojo.lock();
        try{
            colaSur.meter(n);
            while(!abiertoSur)
            {
                Surabierto.await();
            }
            while(this.capacidadActual == this.capacidadDisponible)
            {
                norte.await();
            }
            colaSur.sacar(n);
            zonaComun.entrar(n);
            System.out.println("Persona noseque entrando por SUR. Ocupación: " + capacidadActual + "Colas: " + colaNorte.tamaño()+ ";"+ colaSur.tamaño());
            
        }
        catch(InterruptedException e){}
        finally{
            cerrojo.unlock();
        }
    }**/
}
