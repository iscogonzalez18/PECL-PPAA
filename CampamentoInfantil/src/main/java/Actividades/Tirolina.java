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
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco
 */
public class Tirolina 
{
    
    private ListaMonitores monitor;
    private ListaNiños colaEspera;
    private ListaNiños preparacion;
    private ListaNiños tirolina;
    private ListaNiños finalizacion;
    private Semaphore sem=new Semaphore(1,true);
    private CyclicBarrier barrera=new CyclicBarrier(2);
    private Paso paso;

    public Tirolina(ListaMonitores monitor, ListaNiños colaEspera, ListaNiños preparacion, ListaNiños tirolina, ListaNiños finalizacion, Paso paso ) 
    {
        this.monitor = monitor;
        this.colaEspera = colaEspera;
        this.preparacion = preparacion;
        this.tirolina = tirolina;
        this.finalizacion = finalizacion;
        this.paso = paso;
    }
            
    public void entrarNiño(Niño n)
    {
        try 
        {
            paso.mirar();
            Log.escribirLog("Llega el niño "+n.getIdentificador()+" a la cola de la tirolina");
            colaEspera.meter(n);
            paso.mirar();
            sem.acquire();
            paso.mirar();
            colaEspera.sacar(n);
            paso.mirar();
            Log.escribirLog("Llega el niño "+n.getIdentificador()+" a la tirolina");
            preparacion.meter(n);
            paso.mirar();
            barrera.await();
            paso.mirar();
            Log.escribirLog("El niño "+n.getIdentificador()+" espera a que el monitor lo prepare");
            barrera.await();
            paso.mirar();
            preparacion.sacar(n);
            paso.mirar();
            tirolina.meter(n);
            paso.mirar();
            Log.escribirLog("El niño "+n.getIdentificador()+" se tira en tirolina");
            sleep(3000);
            paso.mirar();
            tirolina.sacar(n);
            paso.mirar();
            finalizacion.meter(n);
            paso.mirar();
            sleep(500);
            paso.mirar();
            n.sumaActividad(1);
            Log.escribirLog("El niño "+n.getIdentificador()+" se va de la tirolina");
            paso.mirar();
            finalizacion.sacar(n);
            paso.mirar();
            sem.release();
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Tirolina.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (BrokenBarrierException ex)
        {
            Logger.getLogger(Tirolina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void entrarMonitor(Monitor m)
    {
        paso.mirar();
        monitor.meter(m);
        paso.mirar();
        System.out.println("El monitor "+m.getIdentificador()+" ha entrado en tirolina");
        while(m.getContador()<10)
        {
            paso.mirar();
            try 
            {
                barrera.await();
                paso.mirar();
                System.out.println("El monitor "+m.getIdentificador()+" prepara la tirolina");
                sleep(1000);
                paso.mirar();
                barrera.await();
                paso.mirar();
                m.sumaActividad();
                if (m.getContador()==10)
                {
                    paso.mirar();
                    System.out.println("El monitor "+m.getIdentificador()+" se va de paseo");
                }
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Tirolina.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (BrokenBarrierException ex) 
            {
                Logger.getLogger(Tirolina.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        paso.mirar();
        monitor.sacar(m);
    }    
}
