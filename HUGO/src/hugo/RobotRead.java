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
//import java.util.concurrent.TimeUnit;

public class RobotRead implements Runnable {
    private int sleepTime;
    private static Random generator = new Random();
    private ControlUI cui;
    private DataStore ds;
    public Boka boka;
    public drive d;
    //ArrayList<Character>instruktioner;
    //char l = 'l';
    //char r = 'r';
    //int [] körorder = {2, 3, 4, 7};
    //String körorder = "23 7 55 16";

    //String körorder = "w";
    //String körorder;
    char meddelande_in;
    int k = 0;
    int [] avbokning_från_robot;
    //String meddelande_in;
    
    String från_robot = " ";
    
    public RobotRead(DataStore ds, ControlUI cui, Boka boka){
        this.cui=cui;
        this.ds=ds;
        this.boka = boka;
        sleepTime=generator.nextInt(20000);
        //instruktioner = d.instruktioner;
        //Nedan är ett försök till att koppla och hämta info från boka
        //det blir fel och körordern är null om inte den hårdkodade används.
        //Det som behövs är att i threads säga att RobotRead behöver info även från boka
        //Det leder till fel vid körning av programet samt att ControlUI blir arg ibland
        //Detta behöver lösas på något sätt när körinstruktionerna från filen drive ska in.
        
    }
 
    @Override
    public void run(){
        try{

            cui.appendStatus("RobotRead kommer att köra i " + sleepTime + " millisekunder.");

            cui.appendStatus("Körorder är: " + d.instruktioner);

            //Skapar anslutning. Siffrorna är mottagarens, fås via browse.
            //Siffran efter kolon är kanalen som används. 
            StreamConnection anslutning = (StreamConnection)
            Connector.open("btspp://C0F8DAE35DC6:1");       //Roboten 98D331902C27
            
            PrintStream bluetooth_ut = new PrintStream(anslutning.openOutputStream());
        
            BufferedReader bluetooth_in = new BufferedReader(new
            InputStreamReader(anslutning.openInputStream()));
            
            
            while(ds.bokaflag == true){
                if(anslutning == null || d.instruktioner == null){
                    cui.appendStatus("Koppling saknas eller körorder är tom");
                    break;
                }else 
                    Thread.sleep(sleepTime/20);
                    ds.updateUIflag=true; 
                    
                    System.out.println("Instruktioner: " + d.instruktioner);
                    for (int m = 0; m < d.instruktioner.size(); m++){
                        bluetooth_ut.println(d.instruktioner.get(m));
                        System.out.println("Instruktioner är: " + d.instruktioner);
                    }
                    /*StringTokenizer st = new StringTokenizer(körorder, " ");
                    //Det vi skickar till roboten
                    while (st.hasMoreTokens()){
                        //TimeUnit.SECONDS.sleep(2);
                        bluetooth_ut.println(st.nextToken());   
                        
                        /*meddelande_in = bluetooth_in.readLine();     //Mottaget meddelande när ubuntu används
                        System.out.println("Mottaget: " + meddelande_in);
                        
                        //Mottaget meddelande från robot
                    }  */
                    int c;            //Använd då vi skickar med robot
                    while((c = bluetooth_in.read()) != -1){
                        meddelande_in = (char) c;
                        System.out.println("Mottaget: " + meddelande_in);
                        //från_robot = Character.toString(meddelande_in);
                        k = k + 1; 
                        System.out.println("k: " + k);
                        break;
                    } 
                    
                    /*for(int m = 0; m < körorder.length(); m++){
                            från_robot = från_robot + " " + meddelande_in;
                            System.out.println("från roboten kommer:" + från_robot);
                        }
                                       
                    System.out.println("ute efter mottagande av meddelande");
                    if(k == 2){
                        System.out.println("inne i min if");
                        for(int t = 0; t<=körorder.length(); t++){
                            System.out.println("inne i for efter if");            
                            avbokning_från_robot[t] = boka.resurser[t];
                        
                        }
                        ds.robotflaga = true;
                        System.out.println("Ute ur for och flagan har satts till true");
                    } */
                    //}

                 cui.appendStatus("Körinstruktioner: " + d.instruktioner);
                 anslutning.close();
                 //i++;
                 //ds.robotflaga = true;
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
