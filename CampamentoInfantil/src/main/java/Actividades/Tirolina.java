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
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
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
    private CyclicBarrier barrera=new CyclicBarrier(2);

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
            preparacion.meter(n);
            barrera.await();
            System.out.println("El niño "+n.getIdentificador()+" espera a que el monitor lo prepare");
            barrera.await();
            preparacion.sacar(n);
            tirolina.meter(n);
            System.out.println("El niño "+n.getIdentificador()+" se tira en tirolina");
            sleep(3000);
            tirolina.sacar(n);
            finalizacion.meter(n);
            sleep(500);
            n.sumaActividad(1);
            System.out.println("El niño "+n.getIdentificador()+" se va de la tirolina");
            finalizacion.sacar(n);
            sem.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Tirolina.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
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
        while(m.getContador()<10){
            try {
                barrera.await();
                System.out.println("El monitor "+m.getIdentificador()+" prepara la tirolina");
                sleep(1000);
                barrera.await();
                m.sumaActividad();
                if (m.getContador()==10){
                    System.out.println("El monitor "+m.getIdentificador()+" se va de paseo");
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Tirolina.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BrokenBarrierException ex) {
                Logger.getLogger(Tirolina.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        monitor.sacar(m);
    }    
}
