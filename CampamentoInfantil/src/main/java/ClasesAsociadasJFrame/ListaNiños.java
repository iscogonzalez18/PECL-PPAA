/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesAsociadasJFrame;

import Threads.Niño;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author Francisco
 */
public class ListaNiños {
    
    private ArrayList<Niño> lista;
    private JLabel label;
    
    public ListaNiños(JLabel label)
    {
        lista=new ArrayList<Niño>();
        this.label=label;
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
    
    public Niño mirar(int n)
    {
        return lista.get(n);
    }
    
    public boolean esta(Niño n){
        return lista.contains(n);
    }
    
    public void imprimir()
    {
        String contenido="";
        for(int i=0; i<lista.size(); i++)
        {
           contenido=contenido+lista.get(i).getIdentificador()+" ";
        }
        label.setText(contenido);
    }
    
    public int tamaño()
    {
        return lista.size();
    }
    
}
