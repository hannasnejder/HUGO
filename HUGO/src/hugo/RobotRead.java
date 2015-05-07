/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hugo;


import java.util.Random;
import java.io.*;
import java.util.*;
import javax.microedition.io.*;
import javax.bluetooth.*;
import java.lang.*; //objekt.org.springframework.util.StringUtils;
import java.util.concurrent.TimeUnit;

public class RobotRead implements Runnable {
    private int sleepTime;
    private static Random generator = new Random();
    private ControlUI cui;
    private DataStore ds;

    public Boka b1;
   
    String körorder = "w";
    //String körorder;
    char meddelande_in;
    int k = 0;
    
    int [] från_robot = new int [2];

    
    public RobotRead(DataStore ds, ControlUI cui/*, Boka b1*/){
        this.cui=cui;
        this.ds=ds;
        //this.b1 = b1;
        sleepTime=generator.nextInt(20000);
        //Nedan är ett försök till att koppla och hämta info från boka
        //det blir fel och körordern är null om inte den hårdkodade används.
        //Det som behövs är att i threads säga att RobotRead behöver info även från boka
        //Det leder till fel vid körning av programet samt att ControlUI blir arg ibland
        //Detta behöver lösas på något sätt när körinstruktionerna från filen drive ska in.
        
    }
 
    @Override
    public void run(){
        try{
            
           // cui.appendStatus("RobotRead kommer att köra i " + sleepTime + " millisekunder.");
            cui.appendStatus("Körorder är: " + körorder);

            //Skapar anslutning. Siffrorna är mottagarens, fås via browse.
            //Siffran efter kolon är kanalen som används. 
            StreamConnection anslutning = (StreamConnection)
            Connector.open("btspp://C0F8DAE35DC6:1");       //Roboten 98D331902C27
            
            PrintStream bluetooth_ut = new PrintStream(anslutning.openOutputStream());
        
            BufferedReader bluetooth_in = new BufferedReader(new
            InputStreamReader(anslutning.openInputStream()));
            
            
            while(ds.bokaflag == true){
                if(anslutning == null || körorder == null){
                    cui.appendStatus("Koppling saknas eller körorder är tom");
                    break;
                }else 
                    Thread.sleep(sleepTime/20);
                    ds.updateUIflag=true; 
                    
                    StringTokenizer st = new StringTokenizer(körorder, " ");
                    //Det vi skickar till roboten
                    while (st.hasMoreTokens()){
                        //TimeUnit.SECONDS.sleep(2);
                        bluetooth_ut.println(st.nextToken());   
                        
                        /*String meddelande_in = bluetooth_in.readLine();     //Mottaget meddelande när ubuntu används
                        System.out.println("Mottaget: " + meddelande_in);*/
                        
                        //Mottaget meddelande från robot
                    }    //char c = bluetooth_in.charAt(0);
                    int c;            //Använd då vi skickar med robot
                    while((c = bluetooth_in.read()) != -1){
                        meddelande_in = (char) c;
                        System.out.println("Mottaget: " + meddelande_in);
                        
                        
                        från_robot[k] = meddelande_in;
                        k = k +1;
                        
                        //ds.robotflaga = true;*/
                    }    
                    
                    
                    System.out.println(Arrays.toString(från_robot));

                 cui.appendStatus("Körinstruktioner: " + körorder);
                 anslutning.close();
                 //i++;
                 ds.robotflaga = true;
                }               
                //System.out.println("från_robot: " + Arrays.toString(från_robot));
            //ds.updateUIflag = true;
        }catch(Exception e) {  
            System.out.print("RobotRead" + e.toString());   

        }

    //cui.appendStatus("RobotRead är nu klar");

    }

}

//vid avbokning av det som fås tillbaka från robot kontrollera om det som ackas stämmer med det som har skickats.
//Om det stämmer överrens skicka till Avbokarobot för att avboka resurs och sen meddelan  
