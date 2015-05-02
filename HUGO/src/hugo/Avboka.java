package hugo;



//import static com.sun.javafx.Utils.contains;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import javax.swing.*;
import java.util.*;

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
    public Boka b1;
    int y [];
    int avbokningar_upptagna [];
    
    //En array för att testa att boka de resurser vi vill
    //int [] s = {1, 39, 5, 48};


public Avboka() {
    //this.cui = cui;
    sleepTime = generator.nextInt(20000);
}

public Avboka(OptPlan opt, RobotRead r1, Boka b1) {
    this.opt = opt;
    this.b1 = b1;
    sleepTime = generator.nextInt(20000);
    opt.createPlan();
    y = opt.resurser_boka;
    //x = r1.från_robot;
    avbokningar_upptagna = b1.vill_avboka;
}

    @Override
public void run() {
    try {
        int i;
        
        System.out.println("y: " + y);
        Thread.sleep(sleepTime);
        //char bokaresurser[] = ds.besoknoder.toCharArray();
        System.out.println("inne i avboka, efter thread sleep");
        
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
            
           //System.out.println("Länken är avbokad.");
           
            }
        }
        catch (Exception e) { System.out.print(e.toString()); }
    }
}
