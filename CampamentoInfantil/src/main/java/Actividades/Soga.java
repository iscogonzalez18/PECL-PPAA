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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco
 */
public class Soga
{
    
    private ListaMonitores monitor; // Solo un monitor
    private ListaNiños cola;
    private ListaNiños equipo1;
    private ListaNiños equipo2;
    private int cont_1=0,cont_2=0; //Contador de niños en la actividadn en equipo 1 y equipo 2
    private CyclicBarrier barrera=new CyclicBarrier(11);
    private Semaphore capacidad=new Semaphore(10,true);

    public Soga(ListaMonitores monitor, ListaNiños cola, ListaNiños equipo1, ListaNiños equipo2) 
    {
        this.monitor = monitor;
        this.cola=cola;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
    }
    
    public void entrarNiño(Niño n)
    {
        cola.meter(n);
        try
        {
            System.out.println("El niño "+n.getIdentificador()+" entra en la cola de soga");
            capacidad.acquire();
            barrera.await();//Espera a que haya 10 niños y el monitor esté listo
            barrera.await();//Acaba el juego
            capacidad.release();
        }
        catch(InterruptedException e){} catch (BrokenBarrierException ex) 
        {
            Logger.getLogger(Soga.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void entrarMonitor(Monitor m)
    {
        monitor.meter(m);
        System.out.println("El monitor "+m.getIdentificador()+" entra en soga");            
        while (m.getContador()<10)
        {
            try 
            {
                barrera.await();//El monitor comienza a asignar a los niños de forma aleatoria en los equipos
                for (int i=0;i<10;i++)
                {
                    if (cont_1!=5&&cont_2!=5)
                    {
                        if ((int) (Math.random()*2)==0)
                        { 
                            System.out.println("El niño "+cola.mirar(0).getIdentificador()+" sale de la cola");
                            System.out.println("El niño "+cola.mirar(0).getIdentificador()+" esta en el equipo 1");
                            equipo1.meter(cola.mirar(0));
                            cola.sacar(cola.mirar(0));
                            cont_1++; 
                        }
                        else
                        {
                            System.out.println("El niño "+cola.mirar(0).getIdentificador()+" sale de la cola");
                            System.out.println("El niño "+cola.mirar(0).getIdentificador()+" esta en el equipo 2");
                            equipo2.meter(cola.mirar(0));
                            cola.sacar(cola.mirar(0));
                            cont_2++;
                        }
                    }
                    else if(cont_1==5)
                    {
                        System.out.println("El niño "+cola.mirar(0).getIdentificador()+" sale de la cola");
                        System.out.println("El niño "+cola.mirar(0).getIdentificador()+" esta en el equipo 2");
                        equipo2.meter(cola.mirar(0));
                        cola.sacar(cola.mirar(0));
                        cont_2++;
                    }
                    else if(cont_2==5)
                    {
                        System.out.println("El niño "+cola.mirar(0).getIdentificador()+" sale de la cola");
                        System.out.println("El niño "+cola.mirar(0).getIdentificador()+" esta en el equipo 1");
                        equipo1.meter(cola.mirar(0));
                        cola.sacar(cola.mirar(0));
                        cont_1++; 
                    }
                }
                //Fin de asignar equipos
                System.out.println("El monitor "+m.getIdentificador()+" comienza la actividad de soga");
                //Comienza el juego
                sleep(7000);
                //Fin de juego
                //Aleatorio el equipo ganador
                if ((int) (Math.random()*2)==0)
                {
                    System.out.println("En la soga ha ganado el equipo1");
                    for (int i=0;i<5;i++)
                    {
                        System.out.println("Al niño "+equipo1.mirar(0).getIdentificador()+" se le suman 2 actividades");
                        System.out.println("Al niño "+equipo2.mirar(0).getIdentificador()+" se le suma 1 actividad");
                        equipo1.mirar(0).sumaActividad(2);
                        equipo2.mirar(0).sumaActividad(1);
                        equipo1.sacar(equipo1.mirar(0));
                        equipo2.sacar(equipo2.mirar(0));
                        cont_1--;
                        cont_2--;
                    }  
                }
                else
                {
                    System.out.println("En la soga ha ganado el equipo2");
                    for (int i=0;i<5;i++)
                    {
                        System.out.println("Al niño "+equipo1.mirar(0).getIdentificador()+" se le suma 1 actividades");
                        System.out.println("Al niño "+equipo2.mirar(0).getIdentificador()+" se le suman 2 actividades");
                        equipo1.mirar(0).sumaActividad(1);
                        equipo2.mirar(0).sumaActividad(2);
                        equipo1.sacar(equipo1.mirar(0));
                        equipo2.sacar(equipo2.mirar(0));
                        cont_1--;
                        cont_2--;
                    }
                }
                barrera.await();
                m.sumaActividad(); 
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(Soga.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (BrokenBarrierException ex)
            {
                Logger.getLogger(Soga.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("El monitor "+m.getIdentificador()+" se va de paseo");
        monitor.sacar(m);
    }
    
    public int getContador()
    {
        return cont_1+cont_2;
    }
}
