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
    private int sleepTime;
    private static Random generator = new Random();
    public DataStore ds;
    public OptPlan opt;
    int resurser [];
    int okej [] = new int[2];
    int ejokej [] = new int[2];
    int vill_avboka [] = new int [2];

    public drive dr;
    //public Avboka avboka;
    //public Avboka b2; 

    //int x [];
    //int x [] = {39, 6};
    String test;
    
    int indexfound;
    
    //Räknar antal gånger något gick att boka
    int j = 0;

    

public Boka(OptPlan opt, DataStore ds, drive dr) {
    this.opt = opt;
    this.ds = ds;
    this.dr = dr;
    sleepTime = generator.nextInt(20000);
    opt.createPlan();  
    resurser = opt.resurser_boka;
    //dr = new drive();
    //ArrayList bokningar = new ArrayList();
}   

/*public Boka(OptPlan opt) {
        this.opt = opt;
        sleepTime = generator.nextInt(20000);
        opt.createPlan();
        x = opt.resurser_boka;

        test = " ";

        dr = new drive();
}*/

    @Override
public void run() {
    
    try {
        int i;
        //Vad ska vi ha för fördröjning så den kör efter optimeringen? 
        TimeUnit.SECONDS.sleep(1);
        

        //Behövs fördröjnng till bokningen??
        //Thread.sleep(sleepTime/20);


        for(i = 0; i < 2; i++){
             
            String url = "http://tnk111.n7.se/reserve.php?user=3&resource=" + resurser[i];
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

                int linecount = 0;
                String OK = "OK";


                while ((inkommande_text = inkommande.readLine()) != null) {
                
                    inkommande_samlat.append(inkommande_text);
                    linecount++;
                    indexfound = inkommande_text.indexOf(OK);
               
                    if(indexfound> -1){
                        System.out.println("Denna båge är okej att boka ");
                        ds.bokningar.add(resurser[i]);                       
                        okej[i] = resurser[i];
                        j++;
                        
                    }else{
                        System.out.println("Bågen är upptagen, försök igen! ");
                        ejokej[i] = resurser[i];
                    }     
                }  
            inkommande.close();
            }


        if(ds.bokningar.size() == 2){
            ds.bokaflag = true;
            System.out.println("Bokaflaga blir sann: " + ds.bokningar.size());
        }
        
        if(j < 2){
            for(int m = 0; m < okej.length; m++){
                //J sätts till 0 varje varv, fixa!!!!!!!!!
                vill_avboka[m] = okej[m]; 
            }
        }
        System.out.println("J= " + j);
         
        test = " ";
        for(int k = 0; k < ds.bokningar.size(); k++){
                test = test + " " + ds.bokningar.get(k).toString();
            }
        
        
        System.out.println("test är: " + test);
        System.out.println("okej" + Arrays.toString(okej));
        System.out.println("ejokej" + Arrays.toString(ejokej));
               
        //Om anslutning saknas ska körinstrutionerna skickas utan bokning
        //Har än så länge bara försökt få den att gå in om anslutning saknas
       /* else{
            System.out.println("Anslutning till bokningsservern saknas");
         }*/
    }catch (Exception e) { System.out.print("det här är e, Boka " + e.toString());

        }
    }
}
