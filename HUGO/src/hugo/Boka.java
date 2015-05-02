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
    private int sleepTime;
    private static Random generator = new Random();
    private DataStore ds;
    public OptPlan opt;
    public drive dr;
    public Avboka avboka;

    int x [];
    //int x [] = {39, 6};
    ArrayList<Integer> bokningar = new ArrayList();

    String test;
    
    int [] okej = new int[2];
    int [] ejokej = new int[2];
    int [] vill_avboka = new int[2];
    int indexfound;
    
    //Räknar antal gånger något gick att boka
    int j = 0;

    
public Boka(){
    sleepTime = generator.nextInt(20000);
}

public Boka(OptPlan opt) {
        this.opt = opt;
        sleepTime = generator.nextInt(20000);
        opt.createPlan();
        x = opt.resurser_boka;

        test = " ";

        dr = new drive();
}

    @Override
public void run() {
    
    try {
        int i;
        //Vad ska vi ha för fördröjning så den kör efter optimeringen? 
        TimeUnit.SECONDS.sleep(1);
        
        /*String url = "http://tnk111.n7.se";
        URL urlobjekt = new URL(url);
        HttpURLConnection anslutning = (HttpURLConnection)
        urlobjekt.openConnection();*/
        //System.out.println(anslutning);
        //System.out.println(urlobjekt);

        //Här fås svarsmeddelande från bokningsservern, här skiter det sig 
        //om internet är av. Går till catch
        //int mottagen_status = anslutning.getResponseCode();

        //Håller på och letar efter vad jag vill ha i if satsen
        //Kanske ska vända den så den gör något om anslutning saknas, annars som vanligt
        //if(mottagen_status == 200  ){
            for(i = 0; i < 2; i++){
                
                String url = "http://tnk111.n7.se/reserve.php?user=3&resource=" + x[i];
                URL urlobjekt1 = new URL(url);
                HttpURLConnection anslutning = (HttpURLConnection)
                urlobjekt1.openConnection();
 
                System.out.println("\nAnropar: " + url);

                BufferedReader inkommande = new BufferedReader(new
                InputStreamReader(anslutning.getInputStream()));
                int mottagen_status = anslutning.getResponseCode();
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
                        bokningar.add(x[i]);                       
                        okej[i] = x[i];
                        j++;
                        
                    }else{
                        System.out.println("Bågen är upptagen, försök igen! ");
                        ejokej[i] = x[i];
                    }     
                }  
            inkommande.close();
            }
        
        //Kollar om under 2 resurser gick att boka, avbokar de som gick
        //att boka isåfall
        if(j < 2){
            for(int m = 0; m < okej.length; m++){
                //J sätts till 0 varje varv, fixa!!!!!!!!!
                vill_avboka[m] = okej[m]; 
            }
        }
         System.out.println("J= " + j);

        test = " ";
        for(int k = 0; k < bokningar.size(); k++ ){
            test = test + " " + bokningar.get(k).toString();
        }
        System.out.println("\n" + "Okej " + Arrays.toString(okej));
        System.out.println("Inte okej " + Arrays.toString(ejokej));

        
        //Om anslutning saknas ska körinstrutionerna skickas utan bokning
        //Har än så länge bara försökt få den att gå in om anslutning saknas
       /* else{
            System.out.println("Anslutning till bokningsservern saknas");
         }*/
    }catch (Exception e) { System.out.print("det här är e, Boka " + e.toString());

        }
    }
}
