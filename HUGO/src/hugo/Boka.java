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
public class Boka implements Runnable {

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
    public drive dr;
    public Avboka avboka;
    public OptOnline online;

    ArrayList<Integer> bokningar = new ArrayList();

    String test;
    
    //int [] vill_avboka = new int[2];
    int indexfound;
  
public Boka(OptPlan opt, DataStore ds, OptOnline online) {
        this.opt = opt;
        this.online = online;
        this.ds = ds;
       // sleepTime = generator.nextInt(20000);
        opt.createPlan();
       
        test = " ";

        dr = new drive();
}

    @Override
public void run() {
    
    try {
        int i;
        //Vad ska vi ha för fördröjning så den kör efter optimeringen? 
       TimeUnit.SECONDS.sleep(1);
        
        String url = "http://tnk111.n7.se";
        URL urlobjekt = new URL(url);
        HttpURLConnection anslutning = (HttpURLConnection)
        urlobjekt.openConnection();
               
        //Här fås svarsmeddelande från bokningsservern, här skiter det sig 
        //om internet är av. Går till catch
        int mottagen_status = anslutning.getResponseCode();

        System.out.print("Resurserna är:" );
        for (int h = 0; h < ds.resurser_boka.length; h++){
            
            System.out.print(ds.resurser_boka[h]+ " ");
        }
        //Håller på och letar efter vad jag vill ha i if satsen
        //Kanske ska vända den så den gör något om anslutning saknas, annars som vanligt
        if(mottagen_status == 200  ){
            for(i = 0; i < 2; i++){

                url = "http://tnk111.n7.se/reserve.php?user=3&resource=" + ds.resurser_boka[i];
                URL urlobjekt1 = new URL(url);
                HttpURLConnection anslutning1 = (HttpURLConnection)
                urlobjekt1.openConnection();
  
                System.out.println("\nAnropar: " + url);

                BufferedReader inkommande = new BufferedReader(new
                InputStreamReader(anslutning1.getInputStream()));
                
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

    }catch (Exception e) { System.out.print("det här är e " + e.toString());

        }
    }
}
