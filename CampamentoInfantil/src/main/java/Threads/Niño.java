/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

/**
 *
 * @author Francisco
 */   
public class Niño extends Thread {
    private int contador=0;
    private String id;

    public Niño() {
        this.id=generaNombre();
    }
    
    public String generaNombre(){
        String id;
        int n=(int) (Math.random()*10000);
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

    public int getContador() {
        return contador;
    }     
    
    public void run(){
        
    }
    
    
}
