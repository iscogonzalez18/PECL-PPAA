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
    
    private ListaNiños colaEspera;
    private ListaNiños enTirolina;
    private ListaMonitores monitor;
    private Lock cerrojo =new ReentrantLock();
    private Condition espera=cerrojo.newCondition();
    private Condition listo=cerrojo.newCondition();
    private Semaphore sem=new Semaphore(1,true);

    public Tirolina(ListaNiños colaEspera, ListaNiños enTirolina, ListaMonitores monitor) {
        this.colaEspera = colaEspera;
        this.enTirolina = enTirolina;
        this.monitor = monitor;
    }
            
    public void entrar(Niño n){
        try {
            System.out.println("Llega el niño "+n.getIdentificador()+" a la cola de la tirolina");
            colaEspera.meter(n);
            sem.acquire();
            colaEspera.sacar(n);
            System.out.println("Llega el niño "+n.getIdentificador()+" a la tirolina");
            enTirolina.meter(n);
            espera.signal();
            System.out.println("El niño "+n.getIdentificador()+" espera a que el monitor lo prepare");
            listo.await();
            System.out.println("El niño "+n.getIdentificador()+" se tira en tirolina");
            sleep(3500);
            n.sumaActividad(1);
            System.out.println("El niño "+n.getIdentificador()+" se va de la tirolina");
            enTirolina.sacar(n);
            sem.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Tirolina.class.getName()).log(Level.SEVERE, null, ex);
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
                    //se va de paseo
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Tirolina.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
