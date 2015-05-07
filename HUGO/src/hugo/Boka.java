package hugo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

//Länk för att kolla bokningarna http://tnk111.n7.se/list.php 

public class Boka implements Runnable{
    ArrayList<Integer> bokningar = new ArrayList();
    
    //e = new Integer();

    //upprättar en anslutning till den server som beskrivs
    //av URL-strängen
    //Ett HTTPmeddelande skickas från klienten till servern,
    //servern gör en tolkning av klientens meddelande, returnerar en statuskod
    //statuskod 200, den har förstått
    //överför den info som beskriver Lius webbsida
    //private int sleepTime;
    //private static Random generator = new Random();
    public DataStore ds;
    public OptPlan opt;
    int resurser [];
    int okej [] = new int[2];
    int ejokej [] = new int[2];
    int vill_avboka [] = new int [2];

    public drive dr;

    //public Avboka avboka;
    public OptOnline online;


    String test;
    
    //int [] vill_avboka = new int[2];
    int indexfound;
  
public Boka(OptPlan opt, DataStore ds, OptOnline online) {
    this.online = online;
    this.opt = opt;
    this.ds = ds;
    //int x [];
    //int x [] = {39, 6};
    String test;
    
    int indexfound;
     dr = new drive();
    
    // resurser = opt.resurser_boka;
 
}


    @Override
public void run() {
    
    try {
        int i;
        //Vad ska vi ha för fördröjning så den kör efter optimeringen? 

       TimeUnit.SECONDS.sleep(1);
        
            for(i = 0; i < 2; i++){

                String url = "http://tnk111.n7.se/reserve.php?user=3&resource=" + ds.resurser_boka[i];
                URL urlobjekt1 = new URL(url);
                HttpURLConnection anslutning = (HttpURLConnection)
                urlobjekt1.openConnection();
  
                System.out.println("\nAnropar: " + url);


                BufferedReader inkommande = new BufferedReader(new
                InputStreamReader(anslutning.getInputStream()));


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


        if(bokningar.size() == 2){
            ds.bokaflag = true;
            System.out.println("Bokaflaga blir sann: " + bokningar.size());
        }
        
        //Om anslutning saknas ska körinstrutionerna skickas utan bokning
        //Har än så länge bara försökt få den att gå in om anslutning saknas
        else{
            System.out.println("Anslutning till bokningsservern saknas");
         }
        
        //Kollar om under 2 resurser gick att boka, avbokar de som gick
        //att boka isåfall
        if(ds.raknare < 2){
            for(int m = 0; m < ds.okej.length; m++){
                ds.vill_avboka[m] = ds.okej[m]; 

            }
            online.newOpt();
        
        }else{
            online.newOpt();
        }
              
        test = " ";

        for(int k = 0; k < bokningar.size(); k++ ){
            test = test + " " + bokningar.get(k).toString();
        }
        System.out.println("\n" + "Okej " + Arrays.toString(ds.okej));
        System.out.println("Inte okej " + Arrays.toString(ds.ejokej));

    }catch (Exception e) { System.out.print("det här är e, Boka " + e.toString());

        }
    }
}
