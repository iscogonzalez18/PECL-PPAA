/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actividades;

import ClasesAsociadasJFrame.ListaMonitores;
import ClasesAsociadasJFrame.ListaNiños;
import ClasesAsociadasJFrame.Plazas;
import PararReanudar.Paso;
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
public class ZonaComun 
{
    
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
    private Plazas plazas;
    private Paso paso;

    public ZonaComun(ListaMonitores monitores, ListaNiños niños, Condition norte, Condition sur, ListaNiños colaNorte, ListaNiños colaSur, Merienda merienda, Soga soga, Tirolina tirolina, Plazas plazas, Paso paso) 
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
        this.plazas = plazas;
        this.paso = paso;
    }

    public String entrarNiño(Niño n)
    {
        niños.meter(n);  
        paso.mirar();
        String actividad = "";
        try 
        {
            sleep(2000+(int) (2001*Math.random()));
            paso.mirar();
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(ZonaComun.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (n.getActividades()>=15)
        {
            paso.mirar();
            salirNiñoCampamento(n);
        }
        else
        {
            int x=(int) (3*Math.random());
            switch (x)
            {
                case 0:
                    paso.mirar();
                    niños.sacar(n);
                    paso.mirar();
                    actividad= "Tirolina";
                    break;
                case 1:
                    paso.mirar();
                    niños.sacar(n);
                    paso.mirar();
                    actividad="Soga";
                    break;
                case 2:
                    paso.mirar();
                    niños.sacar(n);
                    paso.mirar();
                    actividad= "Merienda";    
                    break;
            }       
        }
        return actividad;
    }
    
    public void salirNiño(Niño n)
    {
        paso.mirar();
        niños.sacar(n);     
        paso.mirar();
    }
    
    public String entrarMonitor(Monitor m)
    {
        String act="";
        paso.mirar();
        monitores.meter(m);
        paso.mirar();
        m.setContador(0);
        try 
        {
            sleep(1000+(int)(1001*Math.random()));
            paso.mirar();
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(ZonaComun.class.getName()).log(Level.SEVERE, null, ex);
        }
        String nombre=m.getIdentificador();
        switch (nombre)
        {
            case "M1":
                paso.mirar();
                monitores.sacar(m);
                paso.mirar();
                act="Merienda";
                break;
            case "M2":
                paso.mirar();
                monitores.sacar(m);
                paso.mirar();
                act="Merienda";
                break;
            case "M3":
                paso.mirar();
                monitores.sacar(m);
                paso.mirar();
                act="Tirolina";
                break;
            case "M4":
                paso.mirar();
                monitores.sacar(m);
                paso.mirar();
                act="Soga";
                break;
        }
        return act;
    }
    
    public void salirMonitor(Monitor m)
    {
        paso.mirar();
        monitores.sacar(m); 
        paso.mirar();
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
        niños.sacar(n);
        paso.mirar();
        cerrojo.lock();
        paso.mirar();
        try 
        {       
            System.out.println("Niño " + n.getIdentificador() + " SALIENDO.");
            plazas.decrementar();
            if(colaNorte.tamaño() > 0 && colaSur.tamaño() > 0)
            {
                //personas esperando en las dos entradas (alternancia)
                if(alternancia == 0)
                {
                    paso.mirar();
                    sur.signal();
                    alternancia = 1;
                    paso.mirar();
                }
                else
                {
                    paso.mirar();
                    norte.signal();
                    alternancia = 0;
                    paso.mirar();
                }
            }
            else
            {
                if(colaNorte.tamaño() > 0)
                {
                    paso.mirar();
                    norte.signal();
                    paso.mirar();
                }
                else if (colaSur.tamaño() > 0)
                {
                    paso.mirar();
                    sur.signal();
                    paso.mirar();
                }
            }
        } 
        finally 
        {
            paso.mirar();
            cerrojo.unlock();
            paso.mirar();
        }
    }
}
