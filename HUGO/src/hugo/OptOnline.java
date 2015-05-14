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

public class OptOnline {//implements Runnable {

    //private int sleepTime;
    //private static Random generator = new Random();
    public OptPlan opt;
    //public Boka boka;
    public DataStore ds;
    //vägen som optimeras fram i optplan
    int resurser[];

    //int passerad hylla eller orderfilen som fås från roboten måste läggas till
    public OptOnline(OptPlan opt, DataStore ds) {
        this.opt = opt;
        this.ds = ds;
    }

    public void newOpt(){
        try {
            
            //vi vill modifiera startnod, avstånd[] och orderfilen när roboten har passerat en hylla
            //ändra om värdena i avstånd[] så att den länken som inte går att boka får högt värde, t.ex 100000
            //kollar vilken nod som var okej att boka och sätter den till startnod
            //position 1 och 3 är noder
            //position 0 och 2 är bågar
            //Denna måste göras om. Vet inte vad som ska ske i detta läge...  
            //ämdra villkor till 2 när boka ändras

            if (ds.raknare == 2) {
                ds.startnod = ds.okej[1];
                opt.createPlan();
                System.out.println("startnod är "+ds.startnod);
                /*for (int k =0; k<ds.antalnoderfil; k++){
                    if(ds.startnod == ds.kopiaVilkanoder[k]){
                        ds.kopiaVilkanoder[k] = 0;
                    }
                }*/

                //Om inte båda två går att boka
            } else {
                //System.out.println("Inne i andra if-satsen");
                ds.kopiaAvstand = ds.avstand;
                for (int m = 0; m < ds.arcs*2; m++) {

                    
                    //System.out.println("Startnod är "+ds.startnod);
                    //System.out.println("boka.ejokej[1] är "+boka.ejokej[1]);
                    
                    if ((ds.startpunkt[m] == ds.startnod) && ds.slutpunkt[m] == ds.ejokej[1]) {

                        ds.kopiaAvstand[m] = 100000;
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
