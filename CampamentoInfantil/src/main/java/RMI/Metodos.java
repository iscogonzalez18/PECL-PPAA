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
/**
 *
 * @author Francisco
 */
public class Metodos extends UnicastRemoteObject implements InterfaceCampamento
{
    private Merienda merienda;
    private Soga soga;
    private Tirolina tirolina;

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
    
    public String actividadesNiño(Niño n) throws RemoteException
    {
        return ""+n.getContador();
    }
    
}
