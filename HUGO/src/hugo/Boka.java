package hugo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

//Länk för att kolla bokningarna http://tnk111.n7.se/list.php 

public class Boka implements Runnable{
    ArrayList<Integer> bokningar = new ArrayList();

    public DataStore ds;
    public OptPlan opt;
    public drive dr;
    public OptOnline online;
    public Avboka avboka;
    
    private Timer timer;
    public static final int delay = 1000;
    
    String test;
    boolean kolla_anslutning;

    int indexfound;

  
public Boka(OptPlan opt, DataStore ds, OptOnline online, Avboka avboka, drive dr) {
        this.opt = opt;
        this.ds = ds;
        this.online = online;
        this.avboka = avboka;
        opt.createPlan();

        test = " ";
        //dr = new drive();
        this.dr = dr;
        ds.bokaFlag = true;
 
}

    @Override
public void run() {
    
    try {
        int i;
        TimeUnit.SECONDS.sleep(1);        
        
        while(ds.bokaFlag == true){
        ds.raknare = 0;
        ds.okej = new int[2];
        ds.ejokej = new int[2];
      
            
        for(i = 0; i < 2; i++){
                String url = "http://tnk111.n7.se/reserve.php?user=3&resource=" + ds.resurser_boka[i];
                URL urlobjekt1 = new URL(url);
                HttpURLConnection anslutning = (HttpURLConnection)
                urlobjekt1.openConnection();
  
                System.out.println("\nAnropar: " + url);

                int mottagen_status = anslutning.getResponseCode();
                BufferedReader inkommande = new BufferedReader(new
                InputStreamReader(anslutning.getInputStream()));
                mottagen_status = anslutning.getResponseCode();
                System.out.println("Statuskod: " + mottagen_status);

                String inkommande_text;
                StringBuffer inkommande_samlat = new StringBuffer();

                int linecount = 0;
                String OK = "OK";


                while ((inkommande_text = inkommande.readLine()) != null) {
                
                    inkommande_samlat.append(inkommande_text);
                    linecount++;
                    indexfound = inkommande_text.indexOf(OK);
               
                    if(indexfound> -1){
                        System.out.println("Denna båge är okej att boka ");
                        bokningar.add(ds.resurser_boka[i]);                       
                        ds.okej[i] = ds.resurser_boka[i];
                        
                        ds.raknare++;
                        
                    }else{
                        System.out.println("Bågen är upptagen, försök igen! ");
                        ds.ejokej[i] = ds.resurser_boka[i];
                    }     
                }  
            inkommande.close();
            }        
        
        System.out.println("\n" + "Okej " + Arrays.toString(ds.okej));
        System.out.println("Inte okej " + Arrays.toString(ds.ejokej));
        
        //Kollar om under 2 resurser gick att boka, avbokar de som gick
        //att boka isåfall
        System.out.println("\n" + "Räknare: " + ds.raknare);
        if(ds.raknare < 2){
            for(int m = 0; m < ds.okej.length; m++){
                ds.vill_avboka[m] = ds.okej[m];              
             }
            ds.vill_vanta++;
            avboka.avbokning();
        }
        System.out.println("Räknare 2: " + ds.vill_vanta);

        //Skickar bokade resurser till drive om två gick att boka
        if(ds.raknare == 2){
            dr.startRiktning();
        }
        
        //Räknaren vill_vänta avgör om vi ska vänta eller omoptimera
        //Vill vänta första gången och omoptimera andra gången, nollställs varje 
        //gång den blir två
        if(ds.vill_vanta == 1){
            vanta();
        }
        
        if(ds.vill_vanta == 2){
        System.out.println("Vi ska omoptimera");
        online.newOpt();
        ds.vill_vanta = 0;
        }

        //ds.vill_avboka = ds.okej;
        //avboka.avbokning();
        
        }
    }catch (Exception e) { System.out.print("det här är e, Boka " + e.toString());
            ds.bokaFlag = false;
        }         
    }

//Metod som anropas när vi vill vänta, tråden sover valfri tid, ska vara 3s
public void vanta() {
    try{
     System.out.println("Avvakta i 10 sekunder");
     TimeUnit.SECONDS.sleep(10);
    }catch (Exception e) {System.out.print("det här är e, vänta" + e.toString());
}
        
}
}
