package hugo;

import java.util.*;

public class drive {

    private DataStore ds;
    //ArrayList<Integer> bokningar;
    int riktning;
    private double X1, Y1, X2, Y2, X3, Y3, X4, Y4, deltaX, deltaY, olddeltaX, olddeltaY;
    ArrayList instruktioner = new ArrayList();
    char f, r, l, b, v, h, q, y; 
    //f fram
    //r höger
    //l vänster
    //b backa - används ej än
    //v vända 
    //h hyllplats
    //q hemma vid start
    //y hyllplats passerat 
    
    //Default-konstruktor 
    public drive(DataStore ds) {
        //ds = new DataStore();
        //bokningar = new ArrayList<Integer>();
        this.ds= ds; 
        riktning = 0;
 
    }

    //Robotens riktning vid start 
    public void startRiktning() {

        //Österut
        riktning = 1;

        //Sätta olddeltaX och olddeltaY 
        //Hårdkodat --> måste hitta en vettigare lösning
        if(ds.startnod == 1 || ds.startnod == 5 || ds.startnod == 11 || ds.startnod == 17 || ds.startnod == 23
           || ds.startnod == 35 || ds.startnod == 24 || ds.startnod == 36 || ds.startnod == 37 || ds.startnod == 25 || ds.startnod == 38
           || ds.startnod == 26 || ds.startnod == 22 || ds.startnod == 16 || ds.startnod == 10 || ds.startnod == 4 
           || ds.startnod == 29 || ds.startnod == 3 || ds.startnod == 28 || ds.startnod == 2 || ds.startnod == 27){
            
            olddeltaX = -1;
            olddeltaY = 0;
        }
        
        //Startnodens position 
        for (int i = 0; i < ds.nodes-1; i++) {

            if (ds.startnod-1 == i) {

                X1 = ds.nodeX[i];
                Y1 = ds.nodeY[i];
                break;
            }
        }

        //Ger körinstruktionen 
        for (int m = 0; m < ds.bokningar.size(); m++) {

            //Positionen på noden vi vill besöka  
            for (int k = 0; k < ds.nodes; k++) {
                
                if (ds.bokningar.get(1)-1 == k) {
                    X2 = ds.nodeX[k];
                    Y2 = ds.nodeY[k];
                    break;

                }
            }
        
            //Beräknar delta X och delta Y 
            deltaX = X2 - X1;
            deltaY = Y2 - Y1;
            
            //Positioner på hyllplatser 
            for (int n = 0; n < 4; n++) {

                //Positionen på hyllplatsen vi vill besöka 
                if(ds.vilkanoder[n] == ds.bokningar.get(1)){ 
                    
                    X3 = ds.nodeX[ds.vilkanoder[n]-1];
                    Y3 = ds.nodeY[ds.vilkanoder[n]-1];
                    break;
                }
                //Position på hyllplatser som ska passeras
                else 
                    
                    X4 = ds.nodeX[ds.vilkanoder[n]-1];
                    Y4 = ds.nodeY[ds.vilkanoder[n]-1];
                    
            }
            
            //Kolla om hyllplatserna besöks
            if(X2 == X3 && Y2 == Y3){
                //framme vid hyllplats
                instruktioner.add("h"); 
            }
            else if(X2 == X4 && Y2 == Y4){
                //åk förbi hyllplats
                instruktioner.add("y");
            }
        
            //Om förändring i Y-led men inte i X-led
            if (deltaX == 0) {
                System.out.println("DETTA ÄR DELTAX");
                if (deltaY > 0) {
                    System.out.println("Del1 i DELTAX");
                    if (olddeltaY > 0) {
                        riktning = 1;
                        //fram
                        instruktioner.add("f");
                        System.out.println("X1 och f1");
                    } else if (olddeltaY < 0) {
                        riktning = -1;
                        //fram
                        instruktioner.add("f"); 
                        System.out.println("X1 och f2");
                    } else if (olddeltaX > 0) {
                        //vänster
                        instruktioner.add("l");
                        instruktioner.add("f");
                        System.out.println("X1 och l och f");
                    } else if (olddeltaX < 0) {
                        //höger
                        instruktioner.add("r"); 
                        instruktioner.add("f");
                        System.out.println("X1 och r och ff");
                    }
                } else if (deltaY < 0) {
                    System.out.println("DEL2 i DELTAX");
                    if (olddeltaY > 0) {
                        riktning = -1;
                        //fram
                        instruktioner.add("f");
                        System.out.println("X2 och f1");
                    } else if (olddeltaY < 0) {
                        riktning = 1;
                        //fram
                        instruktioner.add("f");
                        System.out.println("X2 och f2");
                    } else if (olddeltaX > 0) {
                        //höger
                        instruktioner.add("r");
                        instruktioner.add("f");
                        System.out.println("X2 och r och ff");
                    } else if (olddeltaX < 0) {
                        //vänster
                        instruktioner.add("l"); 
                        instruktioner.add("f");
                        System.out.println("X2 och l och ff");
                    }
                }
            }

            //Om förändring i X-led men inte i Y-led
            if (deltaY == 0) {
                System.out.println("DETTA ÄR DELTAY");
                if (deltaX > 0) {
                    System.out.println("DEL1 i DELTAY");
                    if (olddeltaX > 0) {
                        riktning = 1;
                        //fram och vänd 
                        instruktioner.add("v"); 
                        instruktioner.add("f");
                        System.out.println("Y1 och f1");
                    } else if (olddeltaX < 0) {
                        riktning = -1;
                        //fram
                        instruktioner.add("f");
                        System.out.println("Y1 och f2");
                    } else if (olddeltaY > 0) {
                        //höger och fram
                        instruktioner.add("r");
                        instruktioner.add("f");
                        System.out.println("Y1 och r och ff");
                    } else if (olddeltaY < 0) {
                        //vänster och fram
                        instruktioner.add("l"); 
                        instruktioner.add("f");
                        System.out.println("Y1 och l och ff");
                    }
                } else if (deltaX < 0) {
                    System.out.println("DEL2 i DELTAY");
                    if (olddeltaX > 0) {
                        riktning = -1;
                        //fram
                        instruktioner.add("f");
                        System.out.println("Y2 och f1");
                    } else if (olddeltaX < 0) {
                        riktning = 1;
                        //Vänd helt om och åk rakt fram 
                        instruktioner.add("v");
                        instruktioner.add("f");
                        System.out.println("Y2 och f2");
                    } else if (olddeltaY > 0) {
                        //vänster och fram
                        instruktioner.add("l"); 
                        instruktioner.add("f");
                        System.out.println("Y2 och l och ff");
                    } else if (olddeltaY < 0) { 
                        //höger och åk rakt fram
                        instruktioner.add("r");
                        instruktioner.add("f");
                        System.out.println("Y2 och v och ff");
                    }
                }
            }
            
            
            //Uppdatera X1 och Y1
            X1 = X2;
            Y1 = Y2;
            
           //Spara de gamla värdena för X och Y    
            olddeltaX = deltaX;
            olddeltaY = deltaY;
            

            break;

        }

 

    }
}
