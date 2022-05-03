/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Entradas.EntradaSur;
import Entradas.EntradaNorte;
import Actividades.Merienda;
import Actividades.Soga;
import Actividades.Tirolina;
import Actividades.ZonaComun;
import GUI.ListaNiños;
import Threads.Monitor;
import Threads.Niño;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Francisco
 */
public class Campamento {
    
    private int capacidadDisponible;
    private AtomicInteger ocupacion;
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

    public Campamento(Merienda merienda, Soga soga, Tirolina tirolina, ZonaComun zonaComun, EntradaNorte norte, EntradaSur sur, AtomicInteger ocupacion) {
        this.merienda = merienda;
        this.soga = soga;
        this.tirolina = tirolina;
        this.zonaComun = zonaComun;
        this.Norte = norte;
        this.Sur = sur;
        this.capacidadDisponible = 50;
        this.ocupacion = ocupacion;
    }
    
    public void entrarNiñoMerienda(Niño n)
    {
        merienda.entrarNiño(n);
    }
    
    public void entrarNiñoSoga(Niño n)
    {
        soga.entrarNiño(n);
    }
    
    public void entrarNiñoTirolina(Niño n)
    {
        tirolina.entrarNiño(n);
    }
    
    public void entrarNiñoZonaComun(Niño n)
    {
        zonaComun.entrarNiño(n);
    }
    
    public void entrarMonitorMerienda(Monitor m)
    {
        merienda.entrarMonitor(m);
    }
    
    public void entrarMonitorSoga(Monitor m)
    {
        soga.entrarMonitor(m);
    }
    
    public void entrarMonitorTirolina(Monitor m)
    {
        tirolina.entrarMonitor(m);
    }
    
    public void entrarMonitorZonaComun(Monitor m)
    {
        zonaComun.entrarMonitor(m);
    }
}
