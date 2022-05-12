/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package RMI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;

/**
 *
 * @author Edu
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Strings de la zona merienda
                    String BLimpias="",BSucias="",contM="";

                    //Strings de la zona tirolina
                    String Veces="",contT="";

                    //String de la zona soga
                    String contS="";

                    try {
                        while(true)
                        {
                           BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
                            //Localiza el objeto distribuido:
                            InterfaceCampamento metodos = (InterfaceCampamento) Naming.lookup("//127.0.0.1/ObjetoSaluda");
                            //Merienda
                            BLimpias = metodos.bandejasLimpiasMerienda(); 
                            BSucias  = metodos.bandejasSuciasMerienda();
                            contM = metodos.niñosenMerienda();
                            //Tirolina
                            Veces = metodos.vecesTirolina();
                            contT = metodos.niñosenColaTirolina();
                            //Soga 
                            contS = metodos.niñosenColaSoga();

                            System.out.println("El número de niños en la zona de soga es: "+contS);
                            System.out.println("El número de niños en la zona de tirolina es: "+contT);
                            System.out.println("El número de veces que se ha usado la tirolina es: "+Veces);
                            System.out.println("El número de niños en la zona de merienda es: "+contM);
                            System.out.println("El número de bandejas limpias es: "+BLimpias);
                            System.out.println("El número de bandejas sucias es: "+BSucias); 
                        }
                    }
                    catch (Exception e) 
                    {
                        System.out.println("Excepción : " + e.getMessage());
                        e.printStackTrace();
                    }
    }
    
}
