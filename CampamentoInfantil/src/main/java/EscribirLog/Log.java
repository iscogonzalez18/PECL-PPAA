/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EscribirLog;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Edu
 */
public class Log {
    
    public synchronized static void escribirLog(String mensaje) {

    Logger logger = Logger.getLogger("MyLog");
    FileHandler fh;

    try 
    {

    fh = new FileHandler("evolucionCampamento.txt", true);
    logger.addHandler(fh);

    SimpleFormatter formatter = new SimpleFormatter();

    fh.setFormatter(formatter);

    logger.info(mensaje);

    fh.close();

    } catch (SecurityException e) {
    e.printStackTrace();
    } catch (IOException e) {
    e.printStackTrace();
    }  
    }
}
