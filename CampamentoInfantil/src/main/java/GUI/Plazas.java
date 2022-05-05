/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JLabel;

/**
 *
 * @author Francisco
 */
public class Plazas {
    
    private int ocupacion = 0;  
    private JLabel plazasOcupadas;
    private JLabel plazasDisponibles;

    public Plazas(JLabel plazasOcupadas, JLabel plazasDisponibles) 
    {
        this.plazasOcupadas = plazasOcupadas;
        this.plazasDisponibles = plazasDisponibles;
    }
    
    public synchronized void incrementar()
    {
        ocupacion++;
        plazasOcupadas.setText(Integer.toString(ocupacion));
        plazasDisponibles.setText(Integer.toString(50 - ocupacion));
    }
    
    public synchronized void decrementar()
    {
        ocupacion--;
        plazasOcupadas.setText(Integer.toString(ocupacion));
        plazasDisponibles.setText(Integer.toString(50 - ocupacion));
    }
    
    public synchronized int getOcupacion()
    {
        return ocupacion;
    }   
}
