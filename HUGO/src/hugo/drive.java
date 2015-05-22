package hugo;

import java.util.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class drive {
    public ControlUI cui;
    public DataStore ds;
    public Boka boka;
  
    ArrayList<Integer> bokningar;

    int riktning, kartaA, kartaB;
    private double X1, Y1, X2, Y2, X3, Y3, X4, Y4, deltaX, deltaY, olddeltaX, olddeltaY;
    public double kopiaX1, kopiaY1, kopiaX2, kopiaY2;
    ArrayList<Character> instruktioner = new ArrayList();

    //ArrayList<Character> instruktioner = new ArrayList();
    char f, r, l, b, v, h, q, y; 

    //int [] bokningar = {39, 27, 42, 2};
    //f fram
    //f framArrayList<Character> instruktioner = new ArrayList();
    //r höger
    //l vänster
    //b backa - används ej än
    //v vända - används ej än
    //h hyllplats
    //q hemma vid start
    //y hyllplats passerat 
    
    //Default-konstruktor 
    public drive(DataStore ds, ControlUI cui) {
        this.ds = ds;
        this.cui=cui;
        riktning = 0;
        
    }

    //Robotens riktning vid start 
    public void startRiktning() {
        //Österut
        riktning = 1;
        
        //Startnodens position 
        for (int i = 0; i < ds.nodes-1; i++) {
            if (ds.startnod-1 == i) {
                //X1 = ds.nodeX[i-1]; //?stämmer eller i-1
                //Y1 = ds.nodeY[i-1];              
                X1=ds.nodeX[i];
                Y1=ds.nodeY[i];
                kopiaX1=X1;
                kopiaY1=Y1;
                break;
            }
        }

        //Ger körinstruktionen 
        for (int m = 0; m < ds.bokningar.size(); m++) {
            //Positionen på noden vi vill besöka  
            for (int k = 0; k < ds.nodes; k++) {
                if (ds.bokningar.get(m)-1 == k) {

                    X2 = ds.nodeX[k];
                    Y2 = ds.nodeY[k];
                    kopiaX2=X2;
                    kopiaY2=Y2;
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
                        //fram
                        ds.instruktioner.add(f);
                    } else if (olddeltaY < 0) {
                        riktning = -1;
                        //fram
                        ds.instruktioner.add(f); 
                    } else if (olddeltaX > 0) {
                        //vänster
                        ds.instruktioner.add(l);
                    } else if (olddeltaX < 0) {
                        //höger
                        ds.instruktioner.add(r); 
                    }
                } else if (deltaY < 0) {
                    if (olddeltaY > 0) {
                        riktning = -1;
                        //fram
                        ds.instruktioner.add(f);
                    } else if (olddeltaY < 0) {
                        riktning = 1;
                        //fram
                        ds.instruktioner.add(f); 
                    } else if (olddeltaX > 0) {
                        //höger
                        ds.instruktioner.add(r);
                    } else if (olddeltaX < 0) {
                        //vänster
                        ds.instruktioner.add(l); 
                    }
                }
            }

            //Om förändring i X-led men inte i Y-led
            if (deltaY == 0) {
                if (deltaX > 0) {
                    if (olddeltaX > 0) {
                        riktning = 1;
                        //fram
                        ds.instruktioner.add(f);
                    } else if (olddeltaX < 0) {
                        riktning = -1;
                        //fram
                        ds.instruktioner.add(f); 
                    } else if (olddeltaY > 0) {
                        //höger
                        ds.instruktioner.add(r); 
                    } else if (olddeltaY < 0) {
                        //vänster
                        ds.instruktioner.add(l); 
                    }
                } else if (deltaX < 0) {
                    if (olddeltaX > 0) {
                        riktning = -1;
                        //fram
                        ds.instruktioner.add(f);
                    } else if (olddeltaX < 0) {
                        riktning = 1;
                        //fram
                        ds.instruktioner.add(f);
                    } else if (olddeltaY > 0) {
                        //vänster
                        ds.instruktioner.add(l); 
                    } else if (olddeltaY < 0) {
                        //höger 
                        ds.instruktioner.add(r); 
                    }
                }
            }
            
            //Positioner på hyllplatser 
            for (int n = 0; n < ds.nodes; n++) {

                //Positionen på hyllplatsen vi vill besöka  
                if(ds.vilkanoder[m] == n){ 
                    
                    X3 = ds.nodeX[n];
                    Y3 = ds.nodeY[n];
                    break;
                }
                //Position på hyllplatser som ska passeras
                else 
                    
                    X4 = ds.nodeX[n];
                    Y4 = ds.nodeY[n];
            }
            
            //Kolla om hyllplatserna besöks
            if(X2 == X3 && Y2 == Y3){
                //framme vid hyllplats
                ds.instruktioner.add(h); 
            }
            else if(X2 == X4 && Y2 == Y4){
                //åk förbi hyllplats
                ds.instruktioner.add(y);
            }
            
            //Uppdatera X1 och Y1
            X1 = X2;
            Y1 = Y2;

            //Spara de gamla värdena för X och Y    
            olddeltaX = deltaX;
            olddeltaY = deltaY;
        }
        

            
            //åk till vänster på kartan
             while (kopiaX1 > kopiaX2) {
                ds.robotX = kopiaX1;
                kopiaX1 = kopiaX1 - 5; //Ändra femman till typ 10 för att pricken ska åka snabbare
               cui.repaint();
            }

           //åk till höger på kartan
            while (kopiaX1 < kopiaX2) { 
                ds.robotX = kopiaX1;
                kopiaX1 = kopiaX1 + 5;
                cui.repaint();
            }

            //åk neråt på kartan
             while (kopiaY1 > kopiaY2) {
                ds.robotY = kopiaY1;
                kopiaY1 = kopiaY1 - 5;
                cui.repaint();
            }

            //åk uppåt på kartan          
         while (kopiaY1 < kopiaY2) {
                ds.robotY = kopiaY1;
                kopiaY1 = kopiaY1 + 5;
                cui.repaint();
            }
    } 
}