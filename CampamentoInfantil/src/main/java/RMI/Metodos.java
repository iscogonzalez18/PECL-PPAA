/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Threads.Niño;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import Actividades.Merienda;
import Actividades.Soga;
import Actividades.Tirolina;
import java.util.HashMap;
/**
 *
 * @author Francisco
 */
public class Metodos extends UnicastRemoteObject implements InterfaceCampamento
{
    private Merienda merienda;
    private Soga soga;
    private Tirolina tirolina;
    private HashMap<String,Niño> lista;

    public Metodos(Merienda merienda, Soga soga, Tirolina tirolina) throws RemoteException {
        this.merienda = merienda;
        this.soga = soga;
        this.tirolina = tirolina;
    }

    public String niñosenColaTirolina() throws RemoteException
    {
        return ""+tirolina.getContN();
    }
    
    public String niñosenColaSoga() throws RemoteException
    {
        return ""+soga.getCont();
    }
    
    public String niñosenMerienda() throws RemoteException
    {
        return ""+merienda.getCont();
    }
    
    public String vecesTirolina() throws RemoteException
    {
        return ""+tirolina.getCont();
    }
    
    public String bandejasLimpiasMerienda() throws RemoteException
    {
        return ""+merienda.getBLimpias();
    }
    
    public String bandejasSuciasMerienda() throws RemoteException
    {
        return ""+merienda.getBSucias();
    }
    
    public String actividadesNiño(String id) throws RemoteException
    {
        String act="";
        if(lista.containsKey(id)){
            act=Integer.toString(lista.get(id).getContador());
        }
        else
        {
            act="No se ha encontrado ese niño";
        }
        return act;
    }

    public void setLista(HashMap<String, Niño> lista) {
        this.lista = lista;
    }
    
}
