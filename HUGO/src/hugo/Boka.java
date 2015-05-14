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

    public DataStore ds;
    public OptPlan opt;
    int resurser [];
    public drive dr;
    public OptOnline online;
    
    String test;

    int indexfound;
  
public Boka(OptPlan opt, DataStore ds, OptOnline online, drive dr) {
    this.online = online;
    this.opt = opt;
    this.ds = ds;
    this.dr = dr;

    String test;
    
    int indexfound;
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


        //Kollar om under 2 resurser gick att boka, avbokar de som gick
        //att boka isåfall
        //System.out.println(ds.raknare);
        if(ds.raknare < 2){
            for(int m = 0; m < ds.okej.length; m++){
                ds.vill_avboka[m] = ds.okej[m]; 
 
            }
        }
        
        if(ds.raknare == 2){
            dr.startRiktning(); 
        }
        
        online.newOpt();
        
        //test = " ";
       // for(int k = 0; k < ds.bokningar.size(); k++ ){
       //     test = test + " " + ds.bokningar.get(k).toString();
       // }
       // System.out.println("halllå");

    }catch (Exception e) { 
        System.out.print("det här är e, Boka " + e.toString());

        }
    }
}
