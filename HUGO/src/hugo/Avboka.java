package hugo;

//import static com.sun.javafx.Utils.contains;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Avboka {
    //upprättar en anslutning till den server som beskrivs
    //av URL-strängen
    //Ett HTTPmeddelande skickas från klienten till servern,
    //servern gör en tolkning av klientens meddelande, returnerar en statuskod
    //statuskod 200, den har förstått
    //överför den info som beskriver Lius webbsida

    public DataStore ds;
    public OptPlan opt;

    public Avboka(OptPlan opt, DataStore ds) {
    this.opt = opt;
    this.ds = ds;   
   
    }

    //Avbokar när allt vi vill boka inte gick
    public void avbokning() {
    try {
        int i;

        for(i = 0; i < 2; i++){
            //If-sats som rensar bort de nollor som skickas med från Boka
            //Avbokar bara de positioner som inte innehåller noll
                if(ds.vill_avboka[i] != 0){
                System.out.println("\n" + "Vi ska avboka: " + Arrays.toString(ds.vill_avboka));//(ds.vill_avboka[i])); 
                String url = "http://tnk111.n7.se/free.php?user=3&resource=" + ds.vill_avboka[i];//ds.vill_avboka[i];
                URL urlobjekt = new URL(url);
                HttpURLConnection anslutning = (HttpURLConnection)
                urlobjekt.openConnection();
           
                System.out.println("\nAnropar: " + url);
            
                int mottagen_status = anslutning.getResponseCode();
            
                System.out.println("Statuskod: " + mottagen_status + "\n");
           
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
    
    //Avbokar när roboten passerat en resurs
    public void avbokaRobot(){
    try {
        int i = 0;
 
        //System.out.println("x avbokarobot: " + Arrays.toString(ds.okej));
        
        //System.out.println("inne i avbokarobot, efter thread sleep");
        
        for(i = 0; i < 2; i++){
            if(ds.avboka_resurser_robot[i] != 0){
            String url = "http://tnk111.n7.se/free.php?user=3&resource=" + ds.avboka_resurser_robot[i];
         
            URL urlobjekt = new URL(url);
            HttpURLConnection anslutning = (HttpURLConnection)
            urlobjekt.openConnection();
           
            System.out.println("\nAnropar: " + url);
                        
            int mottagen_status = anslutning.getResponseCode();

            System.out.println("Statuskod: " + mottagen_status + "\n");

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
            
            System.out.println(ds.avboka_resurser_robot[i] + " är avbokad " + "\n");
           
            }
        }
        //Bokas flagga sätts till true för att gå tillbaka till boka
        ds.bokaflag = true;
       
       
        }catch (Exception e) { System.out.print("Det här är e, AvbokaRobot" + e.toString()); }
           
    }
}

 
