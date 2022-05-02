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

/**
 *
 * @author Francisco
 */   
public class Niño extends Thread {
    
    private String identificador; //NXXXX
    private int num;
    private Campamento camp;
    private int contador=0,actividades=0; //Contador de actividades
    private EntradaNorte entradaNorte;
    private EntradaSur entradaSur;
    private ZonaComun zonaComun;
    private Merienda merienda;
    private Tirolina tirolina;
    private Soga soga;

    public Niño(int num, Campamento camp, EntradaNorte entradaNorte, EntradaSur entradaSur, ZonaComun zonaComun, Merienda merienda, Tirolina tirolina, Soga soga) {
        this.identificador = generaNombre(num);
        this.num = num;
        this.camp = camp;
        this.entradaNorte = entradaNorte;
        this.entradaSur = entradaSur;
        this.zonaComun = zonaComun;
        this.merienda = merienda;
        this.tirolina = tirolina;
        this.soga = soga;
    }
    
    
    public String generaNombre(int n)
    {
        String id;
        if (n<10){
            Integer.toString(n);
            id="N000"+n;
        }else if(n<100){
            Integer.toString(n);
            id="N00"+n;
        }else if(n<1000){
            Integer.toString(n);
            id="N0"+n;
        }else{
            Integer.toString(n);
            id="N"+n;
        }
        return id;
    }

    public int getActividades() {
        return actividades;
    }

    public void setActividades(int actividades) {
        this.actividades = actividades;
    }

    public int getContador() 
    {
        return contador;
    }     
    
    public String getIdentificador()
    {
        return identificador;
    }
    
    public synchronized void sumaActividad(int n)
    {
        contador+=n;
        actividades+=n;
    }
    
    public void run()
    {
        int r = (int) Math.random()*2;
        if(r==1)
        {
            entradaNorte.entrarNiño(this);
        }
        else if(r==0){
            entradaSur.entrarNiño(this);
        }
        while(contador<15){
            String act=zonaComun.entrarNiño(this);
            if (act.equals("Tirolina")){
                tirolina.entrarNiño(this);
            }else if(act.equals("Soga")){
                soga.entrarNiño(this);
            }else{
                merienda.entrarNiño(this);
            }
        }
        System.out.println("El niño"+this.identificador+ "se va del campamento");
       zonaComun.salirNiñoCampamento(this);
    }
    
    
}
