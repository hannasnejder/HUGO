package hugo;

import java.util.*;

public class translate {

    public DataStore ds;
    public RobotRead rr;
    public Avboka avboka;
    ArrayList<Integer> bokningar;
    ArrayList<Character> instruktioner;
    ArrayList<Character> svar;
    char p;
    char testa;

    public translate(RobotRead rr, DataStore ds, Avboka avboka) {
        this.rr = rr;
        //this.dr = dr;
        this.ds = ds;
        this.avboka = avboka;
        //ds = new DataStore();

        //Vet inte om de här behövs....
        bokningar = new ArrayList<Integer>();
        instruktioner = new ArrayList<Character>();
        svar = new ArrayList<Character>();
    }

    //Här ska vi tolka vad roboten faktisk säger
    //Vad ska avbokas?
    //Säkerhetsstopp?
    public void interpret() {
        System.out.println("meddelande fårn rr: " + rr.meddelande_in);
         System.out.println("Går in i translate");
        for (int r = 0; r < rr.v.size(); r++) {
            if (rr.v.contains('h')) {
                testa = rr.v.get(r + 1);

                //Utförligare kommentarer längst ner i filen
                //för över de resurser som vi ska avboka till arrayen som avbokar
                for (int i = 0; i < 2; i++) {
                    ds.avboka_resurser_robot[i] = ds.bokade_resurser[i];
                }
                //när överföringen är klar, avboka och sätt värdet till noll
                avboka.avbokaRobot();
                for (int i = 0; i < 2; i++) {
                    ds.bokade_resurser[i] = 0;
                }
                //flyttar fram de kvarvarande värderna längst fram i arrayen
                //sätter de två sista platserna till noll
                for (int k = 0, j = 0; k < 4; k++) {
                    if (ds.bokade_resurser[k] != 0) {
                        ds.bokade_resurser[j] = ds.bokade_resurser[k];
                        ds.bokade_resurser[k] = 0;

                    }
                }

            }
            ds.bokaom = 1;
        }

        //System.out.println("Går in i translate");
        //När vi har översatt och vill avboka
        avboka.avbokaRobot();
       
    }

}
//Spara resursnmmrena på det som blev bokat
//När det som kommer från roboten är ett kommando + l/h?
//Ta fram det som blev bokat och avboka
//Ersätt de avbokade resursnummrena med nya resursnummer
//4 resurser kommer max vara bokade/skickade år roboten åt gången[21 2 34 5]
//lägg alla som bokas i en array
//de som avbokas sätts till noll[0 0 34 25]
//flytta fram de som ännu inte är avbokade så de två sista platserna är noll[34 5 0 0]
//lägg till nya bokade resurser sist[34 5 27 8]
        
        
        
       
