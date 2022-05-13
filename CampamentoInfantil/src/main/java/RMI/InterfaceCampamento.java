/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Threads.Niño;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 *
 * @author Francisco
 */
public interface InterfaceCampamento extends Remote
{   
    String niñosenColaTirolina() throws RemoteException;
    String niñosenColaSoga() throws RemoteException;
    String niñosenMerienda() throws RemoteException;
    String vecesTirolina() throws RemoteException;
    String bandejasLimpiasMerienda() throws RemoteException;
    String bandejasSuciasMerienda() throws RemoteException;
    String actividadesNiño(String id) throws RemoteException; 
    void setLista(HashMap<String, Niño> lista) throws RemoteException;
}
