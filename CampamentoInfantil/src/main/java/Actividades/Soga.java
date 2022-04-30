/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actividades;

import GUI.ListaThreads;
import Threads.Monitor;
import Threads.Niño;
import static java.lang.Thread.sleep;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco
 */
public class Soga {
    
    private ListaThreads cola;
    private ListaThreads equipo1;
    private ListaThreads equipo2;
    private int contador=0,cont_1=0,cont_2=0; //Contador de niños en la actividadn en equipo 1 y equipo 2
    private Lock cerrojo=new ReentrantLock();
    private Condition lleno=cerrojo.newCondition();
    private CyclicBarrier barrera=new CyclicBarrier(10);

    public Soga(ListaThreads cola, ListaThreads equipo1, ListaThreads equipo2) {
        this.cola = cola;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
    }
    
    public void entrar(Niño n){
        cerrojo.lock();
        try{
            cola.meter(n);
            while (contador==10){
                lleno.await();          //Se quedan en la cola si ya hay 10 niños para jugar
            }
            cola.sacar(n);
            
            contador++;
            
            if (cont_1!=5&&cont_2!=5){
                if ((int) (Math.random()*2)==0){
                    equipo1.meter(n);
                    cont_1++;
                }else{
                    equipo2.meter(n);
                    cont_2++;
                }
            }else if(cont_1==5){
                equipo2.meter(n);
                cont_2++;
            }else if(cont_2==5){
                equipo1.meter(n);
                cont_1++;
            }
        }
        catch(InterruptedException e){}
        finally{
            cerrojo.unlock();
        }
    }
    
    public void jugar(Monitor m){
        try {
            sleep(7000);
            if ((int) (Math.random()*2)==0){
                for (int i=0;i<5;i++){
                    Niño n=equipo1.mirar(i);
                    n.sumaActividad(2);
                }
            }
            
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Soga.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    
}
