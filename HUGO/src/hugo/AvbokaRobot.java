/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hugo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import javax.swing.*;
import java.util.*;

public class AvbokaRobot implements Runnable{
    
    private int sleepTime;
    private static Random generator = new Random();
    private DataStore ds;   //borde inter behövas
    //public RobotRead r1;
    public RobotRead rr;
    //public Boka b1;
    int x [];
    //int avbokningar_upptagna [];
    
    public AvbokaRobot() {
    //this.cui = cui;
    sleepTime = generator.nextInt(20000);
}
    public AvbokaRobot(RobotRead rr, DataStore ds) {
    this.rr = rr; 
    this.ds = ds;
    sleepTime = generator.nextInt(20000);
    //r1.run();
    //x = r1.från_robot;
    //System.out.println("från robot i avbokarobot" + Arrays.toString(r1.från_robot));
    //System.out.println("x: " + Arrays.toString(x));
    //avbokningar_upptagna = b1.vill_avboka;
}
    
        @Override
public void run() {
    try {
        int i = 0;
        
        /*while(ds.robotflaga == false)
            {
                Thread.sleep(sleepTime/20);
            }*/
                
        while(ds.robotflaga == true){
            
        //x = rr.från_robot; 
        
        System.out.println("x avbokarobot: " + x);
        Thread.sleep(sleepTime/20);
        
        System.out.println("inne i avbokarobot, efter thread sleep");
        
        for(i = 0; i <= 3; i++){

            //Avboka http = new Avboka();
            
            String url = "http://tnk111.n7.se/free.php?user=3&resource=" + x[i];
            URL urlobjekt = new URL(url);
            HttpURLConnection anslutning = (HttpURLConnection)
            urlobjekt.openConnection();
           
            System.out.println("\nAnropar: " + url);
            
            
            int mottagen_status = anslutning.getResponseCode();
            
            System.out.println("Statuskod: " + mottagen_status);
           
            BufferedReader inkommande = new BufferedReader(new
            InputStreamReader(anslutning.getInputStream()));

            String inkommande_text;
            StringBuffer inkommande_samlat = new StringBuffer();
            
           

            while ((inkommande_text = inkommande.readLine()) != null) {
                
                inkommande_samlat.append(inkommande_text);
               
            }
            

            inkommande.close();
            
            System.out.println(inkommande_samlat.toString());
            
           //System.out.println("Länken är avbokad.");
           
            }
        }
        }
        catch (Exception e) { System.out.print("AvbokaRobot" + e.toString()); }
    }
    
}
