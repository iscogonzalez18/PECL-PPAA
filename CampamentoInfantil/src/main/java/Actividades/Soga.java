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
    
    private ListaMonitores monitor; // Solo un monitor
    private ListaNiños cola;
    private ListaNiños equipo1;
    private ListaNiños equipo2;
    private int contador=0,cont_1=0,cont_2=0; //Contador de niños en la actividadn en equipo 1 y equipo 2
    private Lock cerrojo=new ReentrantLock();
    private Condition lleno=cerrojo.newCondition();
    private CyclicBarrier barrera=new CyclicBarrier(11);

    public Soga(ListaMonitores monitor, ListaNiños cola, ListaNiños equipo1, ListaNiños equipo2) {
        this.monitor = monitor;
        this.cola=cola;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
    }
    
    public void entrarNiño(Niño n){
        cerrojo.lock();
        try{
            cola.meter(n);
            System.out.println("El niño "+n.getIdentificador()+" entra en la cola de soga");
            while (contador==10){
                lleno.await();          //Se quedan en la cola si ya hay 10 niños para jugar
            }
            contador++;     
        }
        catch(InterruptedException e){}
        finally{
            cerrojo.unlock();
        }
        try {
            barrera.await();
            barrera.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(Soga.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(Soga.class.getName()).log(Level.SEVERE, null, ex);
        }
        cola.sacar(n);
    }
    
    public void entrarMonitor(Monitor m){
        cerrojo.lock();
        try{
            monitor.meter(m);
            System.out.println("El monitor "+m.getIdentificador()+" entra en soga");            
        }
        finally{
            cerrojo.unlock();
        }
        while (m.getContador()<10){
            try {
                barrera.await();
                if(contador==10){
                    for (int i=0;i<10;i++){
                       if (cont_1!=5&&cont_2!=5){
                           if ((int) (Math.random()*2)==0){
                                //Error aqui   
                               equipo1.meter(cola.mirar(i));
                               System.out.println("El niño "+cola.mirar(i).getIdentificador()+" sale de la cola");
                               cont_1++;
                               cola.sacar(cola.mirar(i));
                               System.out.println("El niño "+cola.mirar(i).getIdentificador()+" esta en el equipo 1");
                           }else{
                               equipo2.meter(cola.mirar(i));
                               System.out.println("El niño "+cola.mirar(i).getIdentificador()+" sale de la cola");
                               cont_2++;
                               cola.sacar(cola.mirar(i));
                               System.out.println("El niño "+cola.mirar(i).getIdentificador()+" esta en el equipo 2");
                           }
                       }else if(cont_1==5){
                           equipo2.meter(cola.mirar(i));
                           System.out.println("El niño "+cola.mirar(i).getIdentificador()+" sale de la cola");
                           cont_2++;
                           cola.sacar(cola.mirar(i));
                           System.out.println("El niño "+cola.mirar(i).getIdentificador()+" esta en el equipo 2");
                       }else if(cont_2==5){
                           equipo1.meter(cola.mirar(i));
                           System.out.println("El niño "+cola.mirar(i).getIdentificador()+" sale de la cola");
                           cont_1++;
                           cola.sacar(cola.mirar(i));
                           System.out.println("El niño "+cola.mirar(i).getIdentificador()+" esta en el equipo 1");
                       }
                    }

                    System.out.println("El monitor "+m.getIdentificador()+" comienza la actividad de soga");
                    sleep(7000);
                    if ((int) (Math.random()*2)==0){
                        System.out.println("En la soga ha ganado el equipo1");
                        for (int i=0;i<5;i++){
                            Niño n=equipo1.mirar(i);
                            System.out.println("Al niño "+cola.mirar(i).getIdentificador()+" se le suman 2 actividades");
                            n.sumaActividad(2);
                            n=equipo2.mirar(i);
                            System.out.println("Al niño "+cola.mirar(i).getIdentificador()+" se le suma 1 actividad");
                            n.sumaActividad(1);
                        }  
                    }else{
                        System.out.println("En la soga ha ganado el equipo2");
                        for (int i=0;i<5;i++){
                            Niño n=equipo1.mirar(i);
                            System.out.println("Al niño "+cola.mirar(i).getIdentificador()+" se le suma 1 actividad");
                            n.sumaActividad(1);
                            n=equipo2.mirar(i);
                            System.out.println("Al niño "+cola.mirar(i).getIdentificador()+" se le suman 2 actividades");
                            n.sumaActividad(2);
                        }
                    }
                    barrera.await();
                    m.sumaActividad();
                    if (m.getContador()==10){
                        System.out.println("El monitor "+m.getIdentificador()+" se va de paseo");
                    }
                }   
            } catch (InterruptedException ex) {
                Logger.getLogger(Soga.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BrokenBarrierException ex) {
                Logger.getLogger(Soga.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        monitor.sacar(m);
    }
}
