package hugo;

import java.io.BufferedReader;
import java.io.IOException;
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


    public DataStore ds;
    public OptPlan opt;
    public drive dr;
    public OptOnline online;
    public Avboka avboka;
    public translate tr;
 
    int indexfound;
    boolean kolla_anslutning;
    int k = 0;
  

public Boka(OptPlan opt, DataStore ds, OptOnline online, drive dr, Avboka avboka, translate tr) {
    this.online = online;
    this.opt = opt;
    this.ds = ds;
    this.dr = dr;
    this.avboka = avboka;
    this.tr = tr;
    
    opt.createPlan();
    ds.bokaflag = true;
}  


    @Override
    public void run() {
    
     try {
        int i;
        
        //TimeUnit.SECONDS.sleep(1);        
        System.out.println("Går den in i boka igen");
        
        boolean run = true;
        while(run){
        //while(p == 1){
        //while(ds.bokaflag == true){
        TimeUnit.SECONDS.sleep(1); 
        
       // if(ds.bokaom == 1){
           
            ds.raknare = 0;
            ds.okej = new int[2];
            ds.ejokej = new int[2];
        
            
            for(i = 0; i < 2; i++){
                if(ds.resurser_boka[i] != 0){
                    String url = "http://tnk111.n7.se/reserve.php?user=3&resource=" + ds.resurser_boka[i];
                    URL urlobjekt1 = new URL(url);
                    HttpURLConnection anslutning = (HttpURLConnection)
                    urlobjekt1.openConnection();
  
                    System.out.println("\nAnropar: " + url);

                    int mottagen_status = anslutning.getResponseCode();
                    BufferedReader inkommande = new BufferedReader(new
                    InputStreamReader(anslutning.getInputStream()));
                    //mottagen_status = anslutning.getResponseCode();
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
                        ds.bokningar.add(ds.resurser_boka[i]);                                             
                        ds.okej[i] = ds.resurser_boka[i];                        
                        ds.raknare++;
                        
                    }else{
                        System.out.println("Bågen är upptagen, försök igen! ");
                        ds.ejokej[i] = ds.resurser_boka[i];
                    }     
                }  
            inkommande.close();

            }     
        }
        
        System.out.println("\n" + "Okej " + Arrays.toString(ds.okej));
        System.out.println("Inte okej " + Arrays.toString(ds.ejokej));
        
        //Kollar om under 2 resurser gick att boka, avbokar de som gick
        //att boka isåfall
       // System.out.println("\n" + "Räknare: " + ds.raknare);
        if(ds.raknare < 2){
            for(int m = 0; m < ds.okej.length; m++){
                ds.vill_avboka[m] = ds.okej[m];              
             }
            ds.vill_vanta++;
            avboka.avbokning();
        }
       // System.out.println("Räknare 2: " + ds.vill_vanta);

        //Skickar bokade resurser till drive om två gick att boka
        if(ds.raknare == 2){
            //k++;
            dr.startRiktning();           
            online.newOpt();
            //tr.interpret();         
        }
        //System.out.println("K är " + k);
        
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
        
        for (int g =0, h=0; g<4; g++){
                    if(ds.bokade_resurser[g] == 0){
                        ds.bokade_resurser[g]= ds.okej[h];
                        h++;
                    }
                }

        //Ändrar flaggorna för att gå till RobotRead
        ds.robotflag = true;
        //ds.bokaflag = false;
        
        //ds.bokaflag = true;

        //ds.vill_avboka = ds.okej;
        //avboka.avbokning();
       
        //}
        ds.bokaom = 0;
        }
        
        //}
    }catch (InterruptedException | IOException e) { System.out.print("det här är e, Boka " + e.toString());
            ds.bokaflag = false;
        }         
    }

//Metod som anropas när vi vill vänta, tråden sover valfri tid, ska vara 3s
public void vanta() {
    try{
     System.out.println("Avvakta i 4 sekunder");
     TimeUnit.SECONDS.sleep(4);
    }catch (Exception e) {System.out.print("det här är e, vänta" + e.toString());
            }
        

    }
}
