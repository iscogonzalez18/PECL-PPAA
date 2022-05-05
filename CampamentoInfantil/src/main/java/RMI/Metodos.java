/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Threads.Niño;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Francisco
 */
public class Metodos extends UnicastRemoteObject implements InterfaceCampamento
{
    public Metodos() throws RemoteException {}
    /*
    public String niñosenColaTirolina() throws RemoteException
    {
        
    }
    
    public String niñosenColaSoga() throws RemoteException
    {
        
    }
    
    public String niñosenMerienda() throws RemoteException
    {
        
    }
    
    public String vecesTirolina() throws RemoteException
    {
        
    }
    
    public String bandejasLimpiasMerienda() throws RemoteException
    {
        
    }
    
    public String bandejasSuciasMerienda() throws RemoteException
    {
        
    }
    
    public String actividadesNiño(Niño n) throws RemoteException
    {
        
    }
*/

    @Override
    public String niñosenColaTirolina() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String niñosenColaSoga() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String niñosenMerienda() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String vecesTirolina() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String bandejasLimpiasMerienda() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String bandejasSuciasMerienda() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String actividadesNiño(Niño n) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
