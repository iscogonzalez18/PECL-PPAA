/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actividades;

import GUI.ListaThreads;
import Threads.Niño;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Francisco
 */
public class ZonaComun {
    //albaricoque
    
    private ListaThreads dentro;

    public ZonaComun(ListaThreads dentro) {
        this.dentro = dentro;
    }
    
    public synchronized void entrar(Niño n)
    {
        dentro.meter(n);   
    }
    
    public synchronized void salir(Niño n)
    {
        dentro.sacar(n);     
    }
    
    public int tamaño()
    {
        return dentro.tamaño();
    }
}
