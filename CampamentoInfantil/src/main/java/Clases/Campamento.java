/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Threads.Niño;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Francisco
 */
public class Campamento {
    
    private boolean entradaNorte; //entradas abiertas o cerradas
    private boolean entradaSur;
    private int capacidad;
    
    private ArrayList<Niño> entradaEste = new ArrayList<>();
    private ArrayList<Niño> entradaOeste = new ArrayList<>();
    
    Lock lock = new ReentrantLock();
    Condition oeste = lock.newCondition();
    Condition este = lock.newCondition();
    
    public Campamento()
    {
        this.entradaNorte = false;
        this.entradaSur = false;
        this.capacidad = 50;
    }
    
    public void entradaNorte(boolean monitor)
    {
        
    }
    
    public void entradaSur(boolean monitor)
    {
        
    }
    
}
