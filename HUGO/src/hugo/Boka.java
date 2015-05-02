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
import javax.swing.*;
import java.util.*;

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
    public Avboka avboka;
    int x[];
    // int x [] = {115, 125, 157, 159};
    ArrayList<Integer> bokningar = new ArrayList();

    String test;

    int[] okej = new int[4];
    int[] ejokej = new int[4];
    int[] vill_avboka = new int[4];
    int indexfound;
   // Arraylist okejavboka = new int[4];

    int j = 0;

    //En array för att testa att boka de resurser vi vill
    //int s[] = {34, 35, 37, 41};
    public Boka() {

        sleepTime = generator.nextInt(20000);
    }

    public Boka(OptPlan opt) {
        this.opt = opt;
        sleepTime = generator.nextInt(20000);
        opt.createPlan();
        x = opt.resurser_boka;

        test = " ";

    }

    @Override
    public void run() {
 //System.out.println(Arrays.toString(x));

        try {
            int i;
            //Behövs fördröjnng till bokningen??
            Thread.sleep(sleepTime / 20);

            for (i = 0; i <= 3; i++) {

        //x = s[i];
                String url = "http://tnk111.n7.se/reserve.php?user=3&resource=" + x[i];
                URL urlobjekt = new URL(url);
                HttpURLConnection anslutning = (HttpURLConnection) urlobjekt.openConnection();

                System.out.println("\nAnropar: " + url);

                int mottagen_status = anslutning.getResponseCode();

                System.out.println("Statuskod: " + mottagen_status);

                BufferedReader inkommande = new BufferedReader(new InputStreamReader(anslutning.getInputStream()));

                String inkommande_text;
                StringBuffer inkommande_samlat = new StringBuffer();

                int linecount = 0;
                String OK = "OK";

                while ((inkommande_text = inkommande.readLine()) != null) {

                    inkommande_samlat.append(inkommande_text);
                    linecount++;
                    indexfound = inkommande_text.indexOf(OK);

                    if (indexfound > -1) {
                        System.out.println("Denna båge är okej att boka ");
                        bokningar.add(x[i]);
                        okej[i] = x[i];
                        j++;

                    } else {
                        System.out.println("Bågen är upptagen, försök igen! ");

                        ejokej[i] = x[i];
                        //break;
                    }
                }

                inkommande.close();

            }
        //Kollar om under 4 resurser gick att boka, avbokar de som gick
            //att boka isåfall
            if (j < 4) {
                for (int m = 0; m < okej.length; m++) {

                    vill_avboka[m] = okej[m];
                }
            }

            //test = " ";
            for (int k = 0; k < bokningar.size(); k++) {
                test = test + " " + bokningar.get(k).toString();
            }
            //System.out.println("Test: " + test);
            System.out.println("Okej " + Arrays.toString(okej));
            System.out.println("Inte okej " + Arrays.toString(ejokej));

        } catch (Exception e) {
            System.out.print("det här är e " + e.toString());

        }
    }
}
