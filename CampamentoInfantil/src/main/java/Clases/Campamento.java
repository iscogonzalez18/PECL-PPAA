/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Actividades.Merienda;
import Actividades.Soga;
import Actividades.Tirolina;
import Actividades.ZonaComun;
import GUI.ListaNiños;
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
    
    private int capacidadDisponible;
    private int capacidadActual;
    private Merienda merienda;
    private Soga soga;
    private Tirolina tirolina;
    private ZonaComun zonaComun;
    
    private EntradaNorte Norte;
    private EntradaSur Sur;
    
    Lock cerrojo = new ReentrantLock();
    Condition norte = cerrojo.newCondition();
    Condition sur = cerrojo.newCondition();
    Condition Norteabierto = cerrojo.newCondition();
    Condition Surabierto = cerrojo.newCondition();
    
    public Campamento()
    {
        this.capacidadDisponible = 50;
        this.capacidadActual = 0;
    }
    
    
    
   
    
}
