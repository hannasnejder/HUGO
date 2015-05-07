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

    public DataStore ds;
    public OptPlan opt;
    public drive dr;
    public OptOnline online;

    String test;
    boolean kolla_anslutning;

    int indexfound;
  
public Boka(OptPlan opt, DataStore ds, OptOnline online) {
        this.opt = opt;
        this.ds = ds;
        this.online = online;
        opt.createPlan();

        test = " ";
        dr = new drive();
 
}

    @Override
public void run() {
    
    try {
        int i;
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Vi vill boka: " + Arrays.toString(ds.resurser_boka));
            
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
        
        //Kollar om under 2 resurser gick att boka, avbokar de som gick
        //att boka isåfall
        System.out.println("\n" + "Räknare: " + ds.raknare);
        if(ds.raknare < 2){
            for(int m = 0; m < ds.okej.length; m++){
                ds.vill_avboka[m] = ds.okej[m]; 
             }
            online.newOpt();
        
        }else{
            online.newOpt();
        }
        
        
        if(bokningar.size() == 2){
            ds.bokaflag = true;
            System.out.println("Bokaflaga blir sann: " + bokningar.size());
        }

        for(int k = 0; k < bokningar.size(); k++ ){
            test = test + " " + bokningar.get(k).toString();
        }
        System.out.println("\n" + "Okej " + Arrays.toString(ds.okej));
        System.out.println("Inte okej " + Arrays.toString(ds.ejokej));

    }catch (Exception e) { System.out.print("det här är e, Boka " + e.toString());

        }
    }
}
