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
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Francisco
 */
public class Merienda {
    
    private ListaMonitores monitores;
    private int contadorServidas;
    private ListaNiños colaEspera,comiendo;
    private JLabel sucias,limpias;
    private Semaphore sem= new Semaphore(20,true);
    private Lock cerrojo=new ReentrantLock();
    private Condition vacio=cerrojo.newCondition();
    private int bandejasLimpias=0, bandejasSucias=25,contador=0;

    public Merienda(ListaMonitores monitores, ListaNiños colaEspera, ListaNiños comiendo, JLabel sucias, JLabel limpias) {
        this.monitores = monitores;
        this.colaEspera = colaEspera;
        this.comiendo = comiendo;
        this.sucias = sucias;
        this.limpias = limpias;
        sucias.setText(Integer.toString(bandejasSucias));
        limpias.setText(Integer.toString(bandejasLimpias));
    }
    
    
    public void entrarNiño(Niño n)
    {
        cerrojo.lock();
        try
        {
            colaEspera.meter(n);
            System.out.println("El niño "+n.getIdentificador()+" ha entrado en la cola de merienda");
        }
        finally
        {
            cerrojo.unlock();
        }
        while (bandejasLimpias==0)
        {
            /*try
            {
                // aqui error wait();
            } 
            /*catch (InterruptedException ex) 
            {
                Logger.getLogger(Merienda.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
        try 
        {
            sem.acquire();
            colaEspera.sacar(n);
            System.out.println("El niño "+n.getIdentificador()+" comienza a merendar");
            comiendo.meter(n);
            sleep(7000);
            cerrojo.lock();
            try
            {
                bandejasLimpias--;
                limpias.setText(Integer.toString(bandejasLimpias));
                bandejasSucias++;
                sucias.setText(Integer.toString(bandejasSucias));
            }
            finally
            {
                cerrojo.unlock();
            }
            System.out.println("El niño "+n.getIdentificador()+" termina de merendar");
            comiendo.sacar(n);
            sem.release();
            
            if (n.getActividades()>=3){
                n.sumaActividad(1);
                n.setActividades(0);
            }
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Merienda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void entrarMonitor(Monitor m)
    {
        cerrojo.lock();
        try
        {
            monitores.meter(m);
            System.out.println("El monitor "+m.getIdentificador()+" ha entrado en merienda");
        }
        finally
        {
            cerrojo.unlock();
        }
        while (m.getContador()<10){
            if (bandejasSucias>0){
                cerrojo.lock();
                try{
                    //Lo de las bandejas lo podemos hacer con un semaforo
                    bandejasSucias--;
                    sucias.setText(Integer.toString(bandejasSucias));
                    sleep(2000+(int)(Math.random()*3001));
                    bandejasLimpias++;
                    limpias.setText(Integer.toString(bandejasLimpias));
                    m.sumaActividad();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Merienda.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    cerrojo.unlock();
                }
                //aqui error 
                if (m.getContador()==10){
                    System.out.println("El monitor "+m.getIdentificador()+" se va de paseo");
                }
            }
        }
        monitores.sacar(m);
    }         
}
