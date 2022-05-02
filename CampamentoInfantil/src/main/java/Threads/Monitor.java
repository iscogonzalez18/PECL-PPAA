/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Actividades.Merienda;
import Actividades.Soga;
import Actividades.Tirolina;
import Actividades.ZonaComun;
import Clases.Campamento;
import Entradas.EntradaNorte;
import Entradas.EntradaSur;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Francisco
 */
public class Monitor extends Thread{
    
    private String identificador; //MX
    private int num;
    private Campamento camp;
    private int contador=0;
    private EntradaNorte entradaNorte;
    private EntradaSur entradaSur;
    private Lock cerrojo;
    private AtomicInteger alternanciaMonitores;
    private ZonaComun zonaComun;
    private Soga soga;
    private Tirolina tirolina;
    private Merienda merienda;

    public Monitor(int num, Campamento camp, EntradaNorte entradaNorte, EntradaSur entradaSur, AtomicInteger alternanciaMonitores, ZonaComun zonaComun, Soga soga, Tirolina tirolina, Merienda merienda) {
        this.identificador = "M" + Integer.toString(num); //MX
        this.num = num;
        this.camp = camp;
        this.entradaNorte = entradaNorte;
        this.entradaSur = entradaSur;
        this.cerrojo = new ReentrantLock();
        this.alternanciaMonitores = alternanciaMonitores;
        this.zonaComun = zonaComun;
        this.soga = soga;
        this.tirolina = tirolina;
        this.merienda = merienda;
    }
    
    

    public String getIdentificador() {
        return identificador;
    }

    public int getNum() {
        return num;
    }    

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
    
    public synchronized void sumaActividad(){
        contador++;
    }
    
    public void run()
    {
        cerrojo.lock();
        try
        {
            if(alternanciaMonitores.get() == 1)
            {
                alternanciaMonitores.getAndAdd(0);
                entradaNorte.entrarMonitor(this);
            }
            else
            {
                alternanciaMonitores.getAndAdd(1);
                entradaSur.entrarMonitor(this);
            }
        }
        finally
        {
            cerrojo.unlock();
        }     
        
        while (true){
            String act=zonaComun.entrarMonitor(this);
            if (act.equals("Tirolina")){
                tirolina.entrarMonitor(this);
            }else if(act.equals("Soga")){
                soga.entrarMonitor(this);
            }else{
                merienda.entrarMonitor(this);
            }
        }
        
    }
}
