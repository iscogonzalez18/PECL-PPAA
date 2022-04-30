/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Actividades.Merienda;
import Actividades.Soga;
import Actividades.Tirolina;
import Actividades.ZonaComun;
import GUI.ListaThreads;
import Threads.Niño;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Francisco
 */
public class Campamento {
    
    private boolean abiertoNorte; //entradas abiertas o cerradas
    private boolean abiertoSur;
    private int capacidadDisponible;
    private int capacidadActual;
    private Merienda merienda;
    private Soga soga;
    private Tirolina tirolina;
    private ZonaComun zonaComun;
    
    private ListaThreads colaNorte;
    private ListaThreads colaSur;
    
    Lock cerrojo = new ReentrantLock();
    Condition norte = cerrojo.newCondition();
    Condition sur = cerrojo.newCondition();
    Condition Norteabierto = cerrojo.newCondition();
    Condition Surabierto = cerrojo.newCondition();
    
    public Campamento()
    {
        this.abiertoNorte = false;
        this.abiertoSur = false;
        this.capacidadDisponible = 50;
        this.capacidadActual = 0;
    }
    
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
    }
    
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
    }
    
}
