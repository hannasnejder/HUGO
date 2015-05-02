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
    //private ControlUI cui;
    private DataStore ds;
    public OptPlan opt;
    private Boka b1;
    int y[];
    int x[];
    int avbokningar_upptagna[];

    //En array för att testa att boka de resurser vi vill
    //int [] s = {34, 35, 37, 41};
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
   ///x = r1.från_robot;
        //avbokningar_upptagna = b1.okej;
        avbokningar_upptagna = b1.vill_avboka;
    }

    @Override
    public void run() {
        try {
            int i;

            //Thread.sleep(sleepTime/20);
            TimeUnit.SECONDS.sleep(5);

        //char bokaresurser[] = ds.besoknoder.toCharArray();
            for (i = 0; i <= 3; i++) {
                if (avbokningar_upptagna[i] != 0) {
                    System.out.println("Vi ska avboka " + Arrays.toString(avbokningar_upptagna));

            //y = s[i];
                    //If sats som kollar om roboten bekräftar rätt körorder, 
                    //avbokar bara om arayerna stämmer överrens
                    //if(x[i] == y[i]){
                    //Avboka http = new Avboka();
                    String url = "http://tnk111.n7.se/free.php?user=3&resource=" + avbokningar_upptagna[i];
                    URL urlobjekt = new URL(url);
                    HttpURLConnection anslutning = (HttpURLConnection) urlobjekt.openConnection();

                    System.out.println("\nAnropar: " + url);

                    int mottagen_status = anslutning.getResponseCode();

                    System.out.println("Statuskod: " + mottagen_status);

                    BufferedReader inkommande = new BufferedReader(new InputStreamReader(anslutning.getInputStream()));

                    String inkommande_text;
                    StringBuffer inkommande_samlat = new StringBuffer();

                    while ((inkommande_text = inkommande.readLine()) != null) {

                        inkommande_samlat.append(inkommande_text);

                    }

                    inkommande.close();

                    System.out.println(inkommande_samlat.toString());

           //System.out.println("Länken är avbokad.");
           // }
                    /*else{
                     System.out.println("Något har blivit fel från robot,"
                     + " strängarna stämmer ej överrens");
                     System.out.println("Avbokar inte resurs: " + y[i]);
                     break;
                     }*/
                }
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
}
