/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Threads.Monitor;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 *
 * @author Edu
 */
public class ListaMonitores {
    ArrayList<Monitor> lista;
    JTextField tf;
    
    public ListaMonitores(JTextField tf)
    {
        lista=new ArrayList<Monitor>();
        this.tf=tf;
    }
    
    public synchronized void meter(Monitor m)
    {
        lista.add(m);
        imprimir();
    }
    
    public synchronized void sacar(Monitor m)
    {
        lista.remove(m);
        imprimir();
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
