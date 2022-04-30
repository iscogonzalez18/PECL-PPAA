/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Threads.Niño;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 *
 * @author Francisco
 */
public class ListaThreads {
    
    ArrayList<Niño> lista;
    JTextField tf;
    
    public ListaThreads(JTextField tf)
    {
        lista=new ArrayList<Niño>();
        this.tf=tf;
    }
    
    public synchronized void meter(Niño n)
    {
        lista.add(n);
        imprimir();
    }
    
    public synchronized void sacar(Niño n)
    {
        lista.remove(n);
        imprimir();
    }
    
    public synchronized Niño mirar(int n){
        return lista.get(n);
    }
    
    public void imprimir()
    {
        String contenido="";
        for(int i=0; i<lista.size(); i++)
        {
           //contenido=contenido+lista.get(i).getid()+" ";
        }
        tf.setText(contenido);
    }
    
    public int tamaño()
    {
        return lista.size();
    }
    
}
