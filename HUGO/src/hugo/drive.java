package hugo;

import java.util.*;

public class drive {

    private DataStore ds;
    private Boka b;
    ArrayList<Integer> bokningar;
    int riktning, kartaA, kartaB;
    private double X1, Y1, X2, Y2, deltaX, deltaY, olddeltaX, olddeltaY;

    //Default-konstruktor 
    public drive() {
        ds = new DataStore();
        bokningar = new ArrayList<Integer>();
        riktning = 0;
        kartaA = 0;
        kartaB = 0;

    }

    //KVAR ATT GÖRA ÄR ATT SKICKA 
    //Robotens riktning vid start 
    public void startRiktning() {

        //Österut
        riktning = 1;

        //Startnodens position 
        for (int i = 0; i < ds.nodes; i++) {

            if (ds.startnod == i) {

                X1 = ds.nodeX[i];
                Y1 = ds.nodeY[i];
                break;
            }
        }

        //Ger körinstruktionen 
        for (int m = 0; m < bokningar.size(); m++) {

            //Positionen på noden vi vill besöka  
            for (int k = 0; k < ds.nodes; k++) {

                if (bokningar.get(m) == k) {

                    X2 = ds.nodeX[k];
                    Y2 = ds.nodeY[k];
                    break;

                }
            }

            //Beräknar delta X och delta Y 
            deltaX = X2 - X1;
            deltaY = Y2 - Y1;

            //Om förändring i Y-led men inte i X-led
            if (deltaX == 0) {
                if (deltaY > 0) {
                    if (olddeltaY > 0) {
                        riktning = 1;
                    } else if (olddeltaY < 0) {
                        riktning = -1;
                        //Skicka 
                    } else if (olddeltaX > 0) {
                        //vänster
                        //Skicka
                    } else if (olddeltaX < 0) {
                        //höger
                        //Skicka
                    }
                } else if (deltaY < 0) {
                    if (olddeltaY > 0) {
                        riktning = -1;
                        //Skicka
                    } else if (olddeltaY < 0) {
                        riktning = 1;
                        //Skicka
                    } else if (olddeltaX > 0) {
                        //höger
                        //Skicka
                    } else if (olddeltaX < 0) {
                        //vänster
                        //Skicka 
                    }
                }
            }

            //Om förändring i X-led men inte i Y-led
            if (deltaY == 0) {
                if (deltaX > 0) {
                    if (olddeltaX > 0) {
                        riktning = 1;
                        //Skicka
                    } else if (olddeltaX < 0) {
                        riktning = -1;
                    } else if (olddeltaY > 0) {
                        //höger
                        //Skicka
                    } else if (olddeltaY < 0) {
                        //vänster
                        //Skicka
                    }
                } else if (deltaX < 0) {
                    if (olddeltaX > 0) {
                        riktning = -1;
                        //Skicka
                    } else if (olddeltaX < 0) {
                        riktning = 1;
                        //Skicka
                    } else if (olddeltaY > 0) {
                        //vänster
                        //Skicka
                    } else if (olddeltaY < 0) {
                        //höger 
                        //Skicka
                    }
                }
            }

            //Uppdatera X1 och Y1
            X1 = X2;
            Y1 = Y2;

            //Spara de gamla värdena för X och Y    
            olddeltaX = deltaX;
            olddeltaY = deltaY;
            
            //KVAR ATT GÖRA ÄR ATT TA HÄNSYN TILL HYLLPLATSERNA 

        }
    }
}
