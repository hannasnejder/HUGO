/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WarehouseControl;

//import static com.sun.javafx.Utils.contains;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class Boka implements Runnable{
    
    //upprättar en anslutning till den server som beskrivs
    //av URL-strängen
    //Ett HTTPmeddelande skickas från klienten till servern,
    //servern gör en tolkning av klientens meddelande, returnerar en statuskod
    //statuskod 200, den har förstått
    //överför den info som beskriver Lius webbsida
    private int sleepTime;
    private static Random generator = new Random();
    //private ControlUI cui;
    private DataStore ds;
    //private OptPlan opt;
    int x;
    
    //En array för att testa att boka de resurser vi vill
    int [] s = {34, 35, 37};


public Boka() {
    //this.cui = cui;
    sleepTime = generator.nextInt(20000);
}



    @Override
public void run() {
    try {
        int i;
        int k;
        
  
        //Thread.sleep(sleepTime/20);
        //char bokaresurser[] = ds.besoknoder.toCharArray();
        
        System.out.println("Går in i första for ");
        
        
        for(i = 0; i <= 2; i++){

            //k = s[i];
           // System.out.println(k);
            //System.out.println("Går inte in i If ");
            
   
            //if(x == k){
            //while( x == s[i]){
            
            //Hur löser vi resursnummer??
            x = s[i] +38;
                
            Boka http = new Boka();
            String url = "http://tnk111.n7.se/reserve.php?user=3&resource=" + x;
            URL urlobjekt = new URL(url);
            HttpURLConnection anslutning = (HttpURLConnection)
            urlobjekt.openConnection();
           
            
            System.out.println("\nAnropar: " + url);
            
            /*String url1 = "http://tnk111.n7.se/reserve.php?user=3&resource=36";
            URL urlobjekt1 = new URL(url1);
            HttpURLConnection anslutning1 = (HttpURLConnection)
            urlobjekt1.openConnection();
            
            System.out.println("\nAnropar: " + url1);*/
            
            
            int mottagen_status = anslutning.getResponseCode();
            
            System.out.println("Statuskod: " + mottagen_status);
            
            /*int mottagen_status1 = anslutning1.getResponseCode();
            
            System.out.println("Statuskod: " + mottagen_status1);*/
           
            BufferedReader inkommande = new BufferedReader(new
            InputStreamReader(anslutning.getInputStream()));

            String inkommande_text;
            StringBuffer inkommande_samlat = new StringBuffer();
            
            /*BufferedReader inkommande1 = new BufferedReader(new
            InputStreamReader(anslutning1.getInputStream()));

            String inkommande_text1;
            StringBuffer inkommande_samlat1 = new StringBuffer();*/
            
            int linecount = 0;
            //String line = null;
            String OK = "OK";

            while ((inkommande_text = inkommande.readLine()) != null) {
                
                inkommande_samlat.append(inkommande_text);
                linecount++;
                int indexfound = inkommande_text.indexOf(OK);
                
                if(indexfound> -1){
                System.out.println("Denna båge är okej att boka");
                }else{
                    System.out.println("Bågen är upptagen, försök igen!");
                } 
                
            }

            inkommande.close();
            
            System.out.println(inkommande_samlat.toString());
            
            /*int linecount1 = 0;
            //String line = null;
            String OK1 = "OK";

            while ((inkommande_text1 = inkommande1.readLine()) != null) {
                
                inkommande_samlat1.append(inkommande_text1);
                linecount1++;
                int indexfound1 = inkommande_text1.indexOf(OK);
                
                if(indexfound1> -1){
                System.out.println("Båge 2 är okej att boka");
                }else{
                    System.out.println("Båge 2 är upptagen, försök igen!");
                } 
                
            }

            inkommande1.close();
            
            System.out.println(inkommande_samlat1.toString());*/
           // x=x+1;
            
               
               //else{
                   //System.out.println("Går inte in i If ");
               //}
                   
            //}
        }
    }
        catch (Exception e) { System.out.print(e.toString()); }
    }
}