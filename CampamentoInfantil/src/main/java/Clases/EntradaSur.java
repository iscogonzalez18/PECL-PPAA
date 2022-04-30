/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import GUI.ListaNiños;
import Threads.Niño;

/**
 *
 * @author Francisco
 */
public class EntradaSur {
    
    private ListaNiños colaSur;
    
    public EntradaSur(ListaNiños cola)
    {
        this.colaSur = cola;
    }
    /**
     public void entradaSur(Niño n)
    {
        cerrojo.lock();
        try{
            colaSur.meter(n);
            while(!abiertoSur)
            {
                Surabierto.await();
            }
            while(this.capacidadActual == this.capacidadDisponible)
            {
                norte.await();
            }
            colaSur.sacar(n);
            zonaComun.entrar(n);
            System.out.println("Persona noseque entrando por SUR. Ocupación: " + capacidadActual + "Colas: " + colaNorte.tamaño()+ ";"+ colaSur.tamaño());
            
        }
        catch(InterruptedException e){}
        finally{
            cerrojo.unlock();
        }
    }**/
}
