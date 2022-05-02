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

/**
 *
 * @author Francisco
 */
public class Tirolina {
    
    private ListaMonitores monitor;
    private ListaNiños colaEspera;
    private ListaNiños preparacion;
    private ListaNiños tirolina;
    private ListaNiños finalizacion;
    private Lock cerrojo =new ReentrantLock();
    private Condition espera=cerrojo.newCondition();
    private Condition listo=cerrojo.newCondition();
    private Semaphore sem=new Semaphore(1,true);
    private ZonaComun zona;

    public Tirolina(ListaMonitores monitor, ListaNiños colaEspera, ListaNiños preparacion, ListaNiños tirolina, ListaNiños finalizacion ) {
        this.monitor = monitor;
        this.colaEspera = colaEspera;
        this.preparacion = preparacion;
        this.tirolina = tirolina;
        this.finalizacion = finalizacion;
    }
            
    public void entrarNiño(Niño n){
        try {
            System.out.println("Llega el niño "+n.getIdentificador()+" a la cola de la tirolina");
            colaEspera.meter(n);
            sem.acquire();
            colaEspera.sacar(n);
            System.out.println("Llega el niño "+n.getIdentificador()+" a la tirolina");
            tirolina.meter(n);
            espera.signal();
            System.out.println("El niño "+n.getIdentificador()+" espera a que el monitor lo prepare");
            listo.await();
            System.out.println("El niño "+n.getIdentificador()+" se tira en tirolina");
            sleep(3500);
            n.sumaActividad(1);
            System.out.println("El niño "+n.getIdentificador()+" se va de la tirolina");
            tirolina.sacar(n);
            sem.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Tirolina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void entrarMonitor(Monitor m)
    {
        cerrojo.lock();
        try
        {
            monitor.meter(m);
            System.out.println("El monitor "+m.getIdentificador()+" ha entrado en tirolina");
        }
        finally
        {
            cerrojo.unlock();
        }
    }
    
    public void preparar(Monitor m){
        while(true){
            try {
                espera.await();
                System.out.println("El monitor "+m.getIdentificador()+" prepara la tirolina");
                sleep(1000);
                listo.signal();
                m.sumaActividad();
                if (m.getContador()==10){
                    System.out.println("El monitor "+m.getIdentificador()+" se va de paseo");
                    m.setContador(0);
                    zona.paseo(m);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Tirolina.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
