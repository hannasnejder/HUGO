package hugo;

//import static com.sun.javafx.Utils.contains;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
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
    //private DataStore ds;
    public OptPlan opt;

    public Boka boka;
    int y [];
    int avbokningar_upptagna [];
    



public Avboka(OptPlan opt, Boka boka, DataStore ds) {
    this.opt = opt;
    this.boka = boka;
    sleepTime = generator.nextInt(20000);
    opt.createPlan();
    avbokningar_upptagna = ds.vill_avboka; 

}

    @Override
    public void run() {
    try {
        int i;
        TimeUnit.SECONDS.sleep(4);
        
        System.out.println("\n" + "Vi ska avboka " + Arrays.toString(avbokningar_upptagna));
        
        for(i = 0; i < 2; i++){
            //If-sats som rensar bort de nollor som skickas med från Boka
            //Avbokar bara de positioner som inte innehåller noll
            if(avbokningar_upptagna[i] != 0){  
                
                String url = "http://tnk111.n7.se/free.php?user=3&resource=" + avbokningar_upptagna[i];
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

    
