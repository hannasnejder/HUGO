package hugo;

//import static com.sun.javafx.Utils.contains;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Avboka implements Runnable {
    //upprättar en anslutning till den server som beskrivs
    //av URL-strängen
    //Ett HTTPmeddelande skickas från klienten till servern,
    //servern gör en tolkning av klientens meddelande, returnerar en statuskod
    //statuskod 200, den har förstått
    //överför den info som beskriver Lius webbsida

    private int sleepTime;
    private static Random generator = new Random();
    public DataStore ds;
    public OptPlan opt;
    public Boka boka;

/*public Avboka() {
    sleepTime = generator.nextInt(20000);
}*/

public Avboka(OptPlan opt, DataStore ds) {
    this.opt = opt;
    //this.boka = boka;
    this.ds = ds;
    sleepTime = generator.nextInt(20000);
    opt.createPlan();
}
    @Override
    public void run() {
    try {
        int i;
        TimeUnit.SECONDS.sleep(4);
             
        
        for(i = 0; i < 2; i++){
            //If-sats som rensar bort de nollor som skickas med från Boka
            //Avbokar bara de positioner som inte innehåller noll
            if(ds.vill_avboka[i] != 0){  
                System.out.println("\n" + "Vi ska avboka: " +(ds.vill_avboka[i])); 
                
                String url = "http://tnk111.n7.se/free.php?user=3&resource=" + ds.vill_avboka[i];
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
            
          }
        }
    }
        catch (Exception e) { System.out.print(e.toString()); }

    }
}

    