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
import java.util.StringTokenizer;
import javax.microedition.io.*;
import javax.bluetooth.*;

public class RobotRead implements Runnable {
    private int sleepTime;
    private static Random generator = new Random();
    private ControlUI cui;
    private DataStore ds;
    String körorder = "23, r, 55, l";
    
    //Påhittad array med bekräftad körorder från robot
    int [] från_robot = {40, 5, 49, 11};
    
    public RobotRead(DataStore ds, ControlUI cui){
        this.cui=cui;
        this.ds=ds;
        sleepTime=generator.nextInt(20000);
    }
    @Override
    public void run(){
        try{
            cui.appendStatus("RobotRead kommer att köra i " + sleepTime + " millisekunder.");
            
          
            
            /*//Skapar anslutning. Siffrorna är mottagarens, fås via browse.
            //Siffran efter kolon är kanalen som används. 
            StreamConnection anslutning = (StreamConnection)
            Connector.open("btspp://00809824156D:8");

            PrintStream bluetooth_ut = new PrintStream(anslutning.openOutputStream());
        
            BufferedReader bluetooth_in = new BufferedReader(new
            InputStreamReader(anslutning.openInputStream()));
            
            int i=1;
            while(i == 1){
                if(anslutning == null || körorder == null){
                    cui.appendStatus("Koppling saknas eller körorder är tom");
                    break;
                }else{
                    Thread.sleep(sleepTime/20);
                    ds.updateUIflag=true; 
                    
                    StringTokenizer st = new StringTokenizer(körorder, " ");
                    
                    //Det vi skickar till roboten
                    while(st.hasMoreTokens()){
                        bluetooth_ut.println(st.nextToken());
                        
                        //Mottaget meddelande från robot
                        //String meddelande_in = bluetooth_in.readLine();
                        //System.out.println("Mottaget: " + meddelande_in);
                    }
                    
                 cui.appendStatus("Körinstruktioner: " + körorder);
                 anslutning.close();
                 i++;
                }               
            }*/
            
            ds.updateUIflag=true;
        }catch(Exception e) {  System.out.print(e.toString());   
        }
        
    cui.appendStatus("RobotRead är nu klar");
    }

}
