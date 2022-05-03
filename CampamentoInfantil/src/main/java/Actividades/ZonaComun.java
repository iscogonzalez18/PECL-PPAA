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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco
 */
public class ZonaComun {
    
    private ListaMonitores monitores;
    private ListaNiños niños;
    private Lock cerrojo= new ReentrantLock();
    private Condition norte;
    private Condition sur;
    private ListaNiños colaNorte;
    private ListaNiños colaSur;
    private int alternancia; 
    private Merienda merienda;
    private Soga soga;
    private Tirolina tirolina;

    public ZonaComun(ListaMonitores monitores, ListaNiños niños, Condition norte, Condition sur, ListaNiños colaNorte, ListaNiños colaSur, Merienda merienda, Soga soga, Tirolina tirolina) 
    {
        this.monitores = monitores;
        this.niños = niños;
        this.norte = norte;
        this.sur = sur;
        this.colaNorte = colaNorte;
        this.colaSur = colaSur;
        this.alternancia = 0;
        this.merienda = merienda;
        this.soga = soga;
        this.tirolina = tirolina;
    }

    public String entrarNiño(Niño n)
    {
        niños.meter(n);  
        String actividad = "";
        try {
            sleep(2000+(int) (2001*Math.random()));
        } catch (InterruptedException ex) {
            Logger.getLogger(ZonaComun.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (n.getActividades()>=15){
            salirNiñoCampamento(n);
        }else{
            int x=(int) (3*Math.random());
            switch (x){
                case 0:
                    niños.sacar(n);
                    actividad= "Tirolina";
                    break;
                case 1:
                    niños.sacar(n);
                    actividad="Soga";
                    break;
                case 2:
                    niños.sacar(n);
                    actividad= "Merienda";    
                    break;
            }       
        }
        return actividad;
    }
    
    public void salirNiño(Niño n)
    {
        niños.sacar(n);     
    }
    
    public String entrarMonitor(Monitor m)
    {
        String act="";
        monitores.meter(m);
        m.setContador(0);
         try {
            sleep(1000+(int)(1001*Math.random()));
        } catch (InterruptedException ex) {
            Logger.getLogger(ZonaComun.class.getName()).log(Level.SEVERE, null, ex);
        }
        String nombre=m.getIdentificador();
        switch (nombre){
            case "M1":
                monitores.sacar(m);
                act="Merienda";
                break;
            case "M2":
                monitores.sacar(m);
                act="Merienda";
                break;
            case "M3":
                monitores.sacar(m);
                act="Tirolina";
                break;
            case "M4":
                monitores.sacar(m);
                act="Soga";
                break;
        }
        return act;
    }
    
    public void salirMonitor(Monitor m)
    {
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
    
    public void salirNiñoCampamento(Niño n)
    {
        cerrojo.lock();
        try {
            niños.sacar(n);
            System.out.println("Persona " + n.getIdentificador() + " SALIENDO.");
            if(colaNorte.tamaño() > 0 && colaSur.tamaño() > 0){
                //personas esperando en las dos entradas (alternancia)
                if(alternancia == 0)
                {
                    sur.signal();
                    alternancia = 1;
                }
                else{
                    norte.signal();
                    alternancia = 0;
                }
            }
            else{
                if(colaNorte.tamaño() > 0)
                    norte.signal();
                else if (colaSur.tamaño() > 0)
                    sur.signal();
            }
        } finally {
            cerrojo.unlock();
        }
    }
}
