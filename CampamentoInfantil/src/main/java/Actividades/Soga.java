/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actividades;

import ClasesAsociadasJFrame.ListaMonitores;
import ClasesAsociadasJFrame.ListaNiños;
import EscribirLog.Log;
import PararReanudar.Paso;
import Threads.Monitor;
import Threads.Niño;
import static java.lang.Thread.sleep;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
    private int cont_1=0,cont_2=0,cont=0; //Contador de niños en la actividadn en equipo 1 y equipo 2
    private CyclicBarrier barrera=new CyclicBarrier(11);
    private Paso paso;
    private Lock cerrojo= new ReentrantLock();

    public Soga(ListaMonitores monitor, ListaNiños cola, ListaNiños equipo1, ListaNiños equipo2, Paso paso) 
    {
        this.monitor = monitor;
        this.cola=cola;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.paso = paso;
    }
    
    public void entrarNiño(Niño n)
    {
        cola.meter(n);
        paso.mirar();
        try
        {
            cerrojo.lock();
            try 
            {
                cont++;
            } 
            finally 
            {
                cerrojo.unlock();
            }
            Log.escribirLog("El niño "+n.getIdentificador()+" entra en la cola de soga");
            paso.mirar();
            barrera.await();//Espera a que haya 10 niños y el monitor esté listo
            paso.mirar();
            barrera.await();//Acaba el juego
            paso.mirar();
            cerrojo.lock();
            try 
            {
                cont--;
            } 
            finally 
            {
                cerrojo.unlock();
            }
        }
        catch(InterruptedException e){} catch (BrokenBarrierException ex) 
        {
            Logger.getLogger(Soga.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void entrarMonitor(Monitor m)
    {
        monitor.meter(m);
        paso.mirar();
        Log.escribirLog("El monitor "+m.getIdentificador()+" entra en soga");            
        while (m.getContador()<10)
        {
            paso.mirar();
            try 
            {
                paso.mirar();
                barrera.await();//El monitor comienza a asignar a los niños de forma aleatoria en los equipos
                paso.mirar();
                for (int i=0;i<10;i++)
                {
                    paso.mirar();
                    if (cont_1!=5&&cont_2!=5)
                    {
                        if ((int) (Math.random()*2)==0)
                        { 
                            paso.mirar();
                            Log.escribirLog("El niño "+cola.mirar(0).getIdentificador()+" sale de la cola");
                            Log.escribirLog("El niño "+cola.mirar(0).getIdentificador()+" esta en el equipo 1");
                            paso.mirar();
                            equipo1.meter(cola.mirar(0));
                            paso.mirar();
                            cola.sacar(cola.mirar(0));
                            cont_1++; 
                            paso.mirar();
                        }
                        else
                        {
                            paso.mirar();
                            Log.escribirLog("El niño "+cola.mirar(0).getIdentificador()+" sale de la cola");
                            Log.escribirLog("El niño "+cola.mirar(0).getIdentificador()+" esta en el equipo 2");
                            paso.mirar();
                            equipo2.meter(cola.mirar(0));
                            paso.mirar();
                            cola.sacar(cola.mirar(0));
                            cont_2++;
                            paso.mirar();
                        }
                    }
                    else if(cont_1==5)
                    {
                        paso.mirar();
                        Log.escribirLog("El niño "+cola.mirar(0).getIdentificador()+" sale de la cola");
                        Log.escribirLog("El niño "+cola.mirar(0).getIdentificador()+" esta en el equipo 2");
                        paso.mirar();
                        equipo2.meter(cola.mirar(0));
                        paso.mirar();
                        cola.sacar(cola.mirar(0));
                        cont_2++;
                        paso.mirar();
                    }
                    else if(cont_2==5)
                    {
                        paso.mirar();
                        Log.escribirLog("El niño "+cola.mirar(0).getIdentificador()+" sale de la cola");
                        Log.escribirLog("El niño "+cola.mirar(0).getIdentificador()+" esta en el equipo 1");
                        paso.mirar();
                        equipo1.meter(cola.mirar(0));
                        paso.mirar();
                        cola.sacar(cola.mirar(0));
                        cont_1++; 
                        paso.mirar();
                    }
                }
                //Fin de asignar equipos
                Log.escribirLog("El monitor "+m.getIdentificador()+" comienza la actividad de soga");
                paso.mirar();
                //Comienza el juego
                sleep(7000);
                //Fin de juego
                paso.mirar();
                //Aleatorio el equipo ganador
                if ((int) (Math.random()*2)==0)
                {
                    paso.mirar();
                    Log.escribirLog("En la soga ha ganado el equipo1");
                    for (int i=0;i<5;i++)
                    {
                        Log.escribirLog("Al niño "+equipo1.mirar(0).getIdentificador()+" se le suman 2 actividades");
                        Log.escribirLog("Al niño "+equipo2.mirar(0).getIdentificador()+" se le suma 1 actividad");
                        paso.mirar();
                        equipo1.mirar(0).sumaActividad(2);
                        paso.mirar();
                        equipo2.mirar(0).sumaActividad(1);
                        paso.mirar();
                        equipo1.sacar(equipo1.mirar(0));
                        paso.mirar();
                        equipo2.sacar(equipo2.mirar(0));
                        paso.mirar();
                        cont_1--;
                        cont_2--;
                    }  
                }
                else
                {
                    paso.mirar();
                    Log.escribirLog("En la soga ha ganado el equipo2");
                    for (int i=0;i<5;i++)
                    {
                        paso.mirar();
                        Log.escribirLog("Al niño "+equipo1.mirar(0).getIdentificador()+" se le suma 1 actividades");
                        Log.escribirLog("Al niño "+equipo2.mirar(0).getIdentificador()+" se le suman 2 actividades");
                        paso.mirar();
                        equipo1.mirar(0).sumaActividad(1);
                        paso.mirar();
                        equipo2.mirar(0).sumaActividad(2);
                        paso.mirar();
                        equipo1.sacar(equipo1.mirar(0));
                        paso.mirar();
                        equipo2.sacar(equipo2.mirar(0));
                        paso.mirar();
                        cont_1--;
                        cont_2--;
                    }
                }
                paso.mirar();
                barrera.await();
                paso.mirar();
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
        paso.mirar();
        monitor.sacar(m);
    }
    
    public int getContador()
    {
        return cont_1+cont_2;
    }
    
    public int getCont()
    {
        return cont;
    }
}
