/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hugo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OptOnline implements Runnable {

    private int sleepTime;
    private static Random generator = new Random();
    public OptPlan opt;
    public Boka boka;
    public DataStore ds;
    //vägen som optimeras fram i optplan
    int resurser[];

    //int passerad hylla eller orderfilen som fås från roboten måste läggas till
    public OptOnline(OptPlan opt, Boka boka, DataStore ds) {
        this.opt = opt;
        this.boka = boka;
        this.ds = ds;
        sleepTime = generator.nextInt(20000);
        //Resursena från optplan sparas i "resurser"
        resurser = opt.resurser_boka;
    }

    @Override
    public void run() {
        try {
            //Ändra hur tråden ska sova beroende på hur allt kopplas ihop
            //Thread.sleep(1000);
            TimeUnit.SECONDS.sleep(2);
            
            //vi vill modifiera startnod, avstånd[] och orderfilen när roboten har passerat en hylla
            //ändra om värdena i avstånd[] så att den länken som inte går att boka får högt värde, t.ex 100000
            //kollar vilken nod som var okej att boka och sätter den till startnod
            //position 1 och 3 är noder
            //position 0 och 2 är bågar
            //Denna måste göras om. Vet inte vad som ska ske i detta läge...  
            //ämdra villkor till 2 när boka ändras
            System.out.println("J i opt= " + boka.j);
            if (boka.j == 2) {
                ds.startnod = boka.okej[1];
                opt.createPlan();

                //Om inte båda två går att boka
            } else {
                System.out.println("Inne i andra if-satsen");
                ds.kopiaAvstand = ds.avstand;
                for (int m = 0; m < 98; m++) {

                    //Ändra ejokej[2] till ngt annat!!!
                    //Beror på om båge eller nod bokas först
                    if ((ds.startpunkt[m] == ds.startnod) && ds.slutpunkt[m] == boka.ejokej[2]) {
                        ds.kopiaAvstand[m] = 100000;
                        //break;
                    }
                }
                opt.createPlan();

            }

            //Sedan vill vi omoptimera utefter vad roboten skickar
        } catch (Exception e) {
            System.out.println("det här är e, OptOnline " + e.toString());
        }
    }
}
