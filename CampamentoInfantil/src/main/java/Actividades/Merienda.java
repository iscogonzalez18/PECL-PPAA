/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actividades;

import GUI.ListaNiños;
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
public class Merienda {
    
    private ListaNiños colaEspera,merendero;
    private Semaphore sem= new Semaphore(20,true);
    private Lock cerrojo=new ReentrantLock();
    private Condition vacio=cerrojo.newCondition();
    private int bandejasLimpias=0, bandejasSucias=25;
    
    public Merienda(ListaNiños cola) 
    {
        this.colaEspera = cola;
    }
    
    public void entrar(Niño n)
    {
        cerrojo.lock();
        try
        {
            colaEspera.meter(n);
            System.out.println("El niño "+n.getName()+" ha entrado en la cola de merienda");
        }
        finally
        {
            cerrojo.unlock();
        }
    }
    
    public void comer(Niño n)
    {
        while (bandejasLimpias==0)
        {
            try
            {
                vacio.await();
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Merienda.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try 
        {
            sem.acquire();
            colaEspera.sacar(n);
            System.out.println("El niño "+n.getName()+" comienza a merendar");
            merendero.meter(n);
            sleep(7000);
            cerrojo.lock();
            try
            {
                bandejasSucias++;
            }
            finally
            {
                cerrojo.unlock();
            }
            System.out.println("El niño "+n.getName()+" termina de merendar");
            merendero.sacar(n);
            sem.release();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Merienda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
         
}
