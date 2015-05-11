package hugo;

import java.util.*;

public class drive {

    public DataStore ds;
    public Boka boka;
    ArrayList<Integer> bokningar;

    int riktning, kartaA, kartaB;
    private double X1, Y1, X2, Y2, X3, Y3, X4, Y4, deltaX, deltaY, olddeltaX, olddeltaY;
    ArrayList<Character> instruktioner = new ArrayList();
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
    public drive(DataStore ds) {
        this.ds = ds;
        bokningar = new ArrayList<Integer>();
        //bokningar = new ArrayList<Integer>();
        riktning = 0;
        kartaA = 0;
        kartaB = 0;

    }

    //Robotens riktning vid start 
    public void startRiktning() {

        System.out.println("Hej drive");
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
        for (int m = 0; m < ds.bokningar.size(); m++) {

            //Positionen på noden vi vill besöka  
            for (int k = 0; k < ds.nodes; k++) {

                if (ds.bokningar.get(m) == k) {

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
                        //fram
                        instruktioner.add(f);
                    } else if (olddeltaY < 0) {
                        riktning = -1;
                        //fram
                        instruktioner.add(f); 
                    } else if (olddeltaX > 0) {
                        //vänster
                        instruktioner.add(l);
                    } else if (olddeltaX < 0) {
                        //höger
                        instruktioner.add(r); 
                    }
                } else if (deltaY < 0) {
                    if (olddeltaY > 0) {
                        riktning = -1;
                        //fram
                        instruktioner.add(f);
                    } else if (olddeltaY < 0) {
                        riktning = 1;
                        //fram
                        instruktioner.add(f); 
                    } else if (olddeltaX > 0) {
                        //höger
                        instruktioner.add(r);
                    } else if (olddeltaX < 0) {
                        //vänster
                        instruktioner.add(l); 
                    }
                }
            }

            //Om förändring i X-led men inte i Y-led
            if (deltaY == 0) {
                if (deltaX > 0) {
                    if (olddeltaX > 0) {
                        riktning = 1;
                        //fram
                        instruktioner.add(f);
                    } else if (olddeltaX < 0) {
                        riktning = -1;
                        //fram
                        instruktioner.add(f); 
                    } else if (olddeltaY > 0) {
                        //höger
                        instruktioner.add(r); 
                    } else if (olddeltaY < 0) {
                        //vänster
                        instruktioner.add(l); 
                    }
                } else if (deltaX < 0) {
                    if (olddeltaX > 0) {
                        riktning = -1;
                        //fram
                        instruktioner.add(f);
                    } else if (olddeltaX < 0) {
                        riktning = 1;
                        //fram
                        instruktioner.add(f);
                    } else if (olddeltaY > 0) {
                        //vänster
                        instruktioner.add(l); 
                    } else if (olddeltaY < 0) {
                        //höger 
                        instruktioner.add(r); 
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
                instruktioner.add(h); 
            }
            else if(X2 == X4 && Y2 == Y4){
                //åk förbi hyllplats
                instruktioner.add(y);
            }
            
            //Uppdatera X1 och Y1
            X1 = X2;
            Y1 = Y2;

            //Spara de gamla värdena för X och Y    
            olddeltaX = deltaX;
            olddeltaY = deltaY;

        }
        String Körorder = " ";
        for(int k = 0; k < instruktioner.size(); k++ ){
        Körorder = Körorder + " " + instruktioner.get(k).toString();
        }
        System.out.println("Körorder: " + Körorder); 
    }
    
}
