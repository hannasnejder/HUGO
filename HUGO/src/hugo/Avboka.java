/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hugo;



//import static com.sun.javafx.Utils.contains;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class Avboka implements Runnable{
    
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
    public OptPlan opt;
    int y [];
    
    //En array för att testa att boka de resurser vi vill
    //int [] s = {34, 35, 37, 41};


public Avboka() {
    //this.cui = cui;
    sleepTime = generator.nextInt(20000);
}

public Avboka(OptPlan opt) {
    this.opt = opt;
    sleepTime = generator.nextInt(20000);
    opt.createPlan();
    y = opt.resurser_boka;
}
    @Override
public void run() {
    try {
        int i;
        
        Thread.sleep(sleepTime/20);
        //char bokaresurser[] = ds.besoknoder.toCharArray();
        
        for(i = 0; i <= 3; i++){

            //y = s[i];
            
            //Avboka http = new Avboka();
            String url = "http://tnk111.n7.se/free.php?user=3&resource=" + y[i];
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
            
           System.out.println("Länken är avbokad.");
           
            }
        }
        catch (Exception e) { System.out.print(e.toString()); }
    }
}
