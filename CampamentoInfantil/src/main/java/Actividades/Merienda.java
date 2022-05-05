/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actividades;

import ClasesAsociadasJFrame.ListaMonitores;
import ClasesAsociadasJFrame.ListaNiños;
import Threads.Monitor;
import Threads.Niño;
import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Francisco
 */
public class Merienda 
{
    
    private ListaMonitores monitores;
    private ListaNiños colaEspera,comiendo;
    private JLabel sucias,limpias;
    private Semaphore sem= new Semaphore(20,true);
    private Semaphore bandejasSucias=new Semaphore(25,true);
    private Semaphore bandejasLimpias=new Semaphore(0,true);

    public Merienda(ListaMonitores monitores, ListaNiños colaEspera, ListaNiños comiendo, JLabel sucias, JLabel limpias)
    {
        this.monitores = monitores;
        this.colaEspera = colaEspera;
        this.comiendo = comiendo;
        this.sucias = sucias;
        this.limpias = limpias;
        sucias.setText(Integer.toString(bandejasSucias.availablePermits()));
        limpias.setText(Integer.toString(bandejasLimpias.availablePermits()));
    }
    
    
    public void entrarNiño(Niño n)
    {
        colaEspera.meter(n);
        System.out.println("El niño "+n.getIdentificador()+" ha entrado en la cola de merienda");
        
        try 
        {
            bandejasLimpias.acquire();
            limpias.setText(Integer.toString(bandejasLimpias.availablePermits()));
            sem.acquire();
            colaEspera.sacar(n);
            System.out.println("El niño "+n.getIdentificador()+" comienza a merendar");
            comiendo.meter(n);
            sleep(7000);
            System.out.println("El niño "+n.getIdentificador()+" termina de merendar");
            comiendo.sacar(n);
            sem.release();
            bandejasSucias.release();
            sucias.setText(Integer.toString(bandejasSucias.availablePermits()));
            if (n.getActividades()>=3)
            {
                n.sumaActividad(1);
                n.setActividades(0);
            }
            
        } 
        catch (InterruptedException ex)
        {
            Logger.getLogger(Merienda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void entrarMonitor(Monitor m)
    {
        monitores.meter(m);
        System.out.println("El monitor "+m.getIdentificador()+" ha entrado en merienda");
        
        while (m.getContador()<10)
        {
            try 
            {
                bandejasSucias.acquire();
                sucias.setText(Integer.toString(bandejasSucias.availablePermits()));
                sleep(2000+(int)(Math.random()*3001));
                bandejasLimpias.release();
                limpias.setText(Integer.toString(bandejasLimpias.availablePermits()));
                m.sumaActividad();
            } 
            catch (InterruptedException ex)
            {
                Logger.getLogger(Merienda.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (m.getContador()==10)
            {
                    System.out.println("El monitor "+m.getIdentificador()+" se va de paseo");
            }
        }
        monitores.sacar(m);
    }
             
}
