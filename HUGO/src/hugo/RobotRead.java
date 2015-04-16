/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hugo;

import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import javax.microedition.io.*;
import javax.bluetooth.*;

public class RobotRead implements Runnable {
    private int sleepTime;
    private static Random generator = new Random();
    private ControlUI cui;
    private DataStore ds;
    //char l = 'l';
    //char r = 'r';
    int [] körorder = {2, 3, 4, 7};
    
    public RobotRead(DataStore ds, ControlUI cui){
        this.cui=cui;
        this.ds=ds;
        sleepTime=generator.nextInt(20000);
    }
    @Override
    public void run(){
        try{
            cui.appendStatus("RobotRead kommer att köra i " + sleepTime + " millisekunder.");
            
            //Skapar anslutning. Siffrorna är mottagarens, fås via browse.
            //Siffran efter kolon är kanalen som används. 
            StreamConnection anslutning = (StreamConnection)
            Connector.open("btspp://00809824156D:8");

            PrintStream bluetooth_ut = new PrintStream(anslutning.openOutputStream());
        
            BufferedReader bluetooth_in = new BufferedReader(new
            InputStreamReader(anslutning.openInputStream()));
            
            int i=1;
            while(i == 1){
                if(anslutning == null || körorder == null){
                    cui.appendStatus("Kopplin saknas eller körorder är tom");
                    break;
                }else{
                    Thread.sleep(sleepTime/20);
                    ds.updateUIflag=true; 
                    
                    //Det vi skickar till roboten
                    for(int k = 0; k <= körorder.length; k++){
                        bluetooth_ut.println(körorder[k]);
                        
                        //Mottaget meddelande från robot
                        String meddelande_in = bluetooth_in.readLine();
                        System.out.println("Mottaget: " + meddelande_in);
                    }
                    
                 cui.appendStatus("Körinstruktioner: " + Arrays.toString(körorder));
                 anslutning.close();
                 i++;
                }               
            }
        }catch(Exception e) {  System.out.print(e.toString());   
        }
        
    cui.appendStatus("RobotRead är nu klar");
    }

}
