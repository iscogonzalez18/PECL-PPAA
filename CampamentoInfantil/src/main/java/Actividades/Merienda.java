/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actividades;

import ClasesAsociadasJFrame.ListaMonitores;
import ClasesAsociadasJFrame.ListaNiños;
import PararReanudar.Paso;
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
    private Paso paso;

    public Merienda(ListaMonitores monitores, ListaNiños colaEspera, ListaNiños comiendo, JLabel sucias, JLabel limpias, Paso paso)
    {
        this.monitores = monitores;
        this.colaEspera = colaEspera;
        this.comiendo = comiendo;
        this.sucias = sucias;
        this.limpias = limpias;
        sucias.setText(Integer.toString(bandejasSucias.availablePermits()));
        limpias.setText(Integer.toString(bandejasLimpias.availablePermits()));
        this.paso = paso;
    }
    
    
    public void entrarNiño(Niño n)
    {
        colaEspera.meter(n);
        System.out.println("El niño "+n.getIdentificador()+" ha entrado en la cola de merienda");
        paso.mirar();
        try 
        {
            bandejasLimpias.acquire();
            paso.mirar();
            limpias.setText(Integer.toString(bandejasLimpias.availablePermits()));
            sem.acquire();
            paso.mirar();
            colaEspera.sacar(n);
            paso.mirar();
            System.out.println("El niño "+n.getIdentificador()+" comienza a merendar");
            comiendo.meter(n);
            paso.mirar();
            sleep(7000);
            paso.mirar();
            System.out.println("El niño "+n.getIdentificador()+" termina de merendar");
            comiendo.sacar(n);
            sem.release();
            bandejasSucias.release();
            sucias.setText(Integer.toString(bandejasSucias.availablePermits()));
            paso.mirar();
            if (n.getActividades()>=3)
            {
                n.sumaActividad(1);
                n.setActividades(0);
            }
            paso.mirar();
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
        paso.mirar();
        while (m.getContador()<10)
        {
            paso.mirar();
            try 
            {
                bandejasSucias.acquire();
                paso.mirar();
                sucias.setText(Integer.toString(bandejasSucias.availablePermits()));
                sleep(2000+(int)(Math.random()*3001));
                paso.mirar();
                bandejasLimpias.release();
                limpias.setText(Integer.toString(bandejasLimpias.availablePermits()));
                m.sumaActividad();
                paso.mirar();
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
        paso.mirar();
        monitores.sacar(m);
    }
             
}
