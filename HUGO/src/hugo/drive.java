package hugo;

import java.util.*;

public class drive {

    private DataStore ds;
    //ArrayList<Integer> bokningar;
    int riktning;

    public double X1, Y1, X2, Y2, X3, Y3, X4, Y4, deltaX, deltaY, olddeltaX, olddeltaY;
    public double kopiaX1, kopiaY1;
    //ArrayList<Character> instruktioner = new ArrayList();
    ArrayList instruktioner = new ArrayList();
    char f, r, l, b, v, h, q, y;
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
            //ds = new DataStore();
        //bokningar = new ArrayList<Integer>();
        this.ds = ds;
        riktning = 0;

    }

    //Robotens riktning vid start 
    public void startRiktning() {
        System.out.println("inne i drive");

        //Österut
        riktning = 1;

            //Sätta olddeltaX och olddeltaY 
        //Hårdkodat --> måste hitta en vettigare lösning
        if (ds.startnod == 1 || ds.startnod == 5 || ds.startnod == 11 || ds.startnod == 17 || ds.startnod == 23) {
            olddeltaX = -1;
            olddeltaY = 0;
        }

        if (ds.startnod == 4 || ds.startnod == 10 || ds.startnod == 16 || ds.startnod == 22 || ds.startnod == 26) {
            olddeltaX = 0;
            olddeltaY = -1;
        }

        //Startnodens position 
        for (int i = 0; i < ds.nodes - 1; i++) {

            if (ds.startnod - 1 == i) {

                X1 = ds.nodeX[i];
                Y1 = ds.nodeY[i];
                kopiaX1 = X1;
                kopiaY1 = Y1;
                break;
            }
        }
        //System.out.println("startnoden " + ds.startnod);
        System.out.println("första koordinaterna " + X1 + " " + Y1);
            //System.out.println("bokningar " + ds.bokningar);

        //Ger körinstruktionen 
        for (int m = 0; m < ds.bokningar.size(); m++) {

            //Positionen på noden vi vill besöka  
            for (int k = 0; k < ds.nodes - 1; k++) {

                if (ds.bokningar.get(1) - 1 == k) {
                    X2 = ds.nodeX[k];
                    Y2 = ds.nodeY[k];
                    break;

                }
            }
            System.out.println("andra koordinaterna " + X2 + " " + Y2);

            //Beräknar delta X och delta Y 
            deltaX = X2 - X1;
            deltaY = Y2 - Y1;

            //Positioner på hyllplatser 
            for (int n = 0; n < 4; n++) {

                //Positionen på hyllplatsen vi vill besöka 
                if (ds.vilkanoder[n] == ds.bokningar.get(1)) {

                    X3 = ds.nodeX[ds.vilkanoder[n] - 1];
                    Y3 = ds.nodeY[ds.vilkanoder[n] - 1];
                    break;
                } //Position på hyllplatser som ska passeras
                else {
                    X4 = ds.nodeX[ds.vilkanoder[n] - 1];
                }
                Y4 = ds.nodeY[ds.vilkanoder[n] - 1];

            }
            System.out.println("X3 och Y3 " + X3 + " " + Y3);
            System.out.println("X4 och Y4 " + X4 + " " + Y4);

            //Kolla om hyllplatserna besöks
            if (X2 == X3 && Y2 == Y3) {
                //framme vid hyllplats
                instruktioner.add("h");
            } else if (X2 == X4 && Y2 == Y4) {
                //åk förbi hyllplats
                instruktioner.add("y");
            }

            //System.out.println("delta koordinaterna " + deltaX + " " + deltaY); 
            //Om förändring i Y-led men inte i X-led
            if (deltaX == 0) {
                System.out.println("blir det nåt här?");
                if (deltaY > 0) {
                    if (olddeltaY > 0) {
                        riktning = 1;
                        //fram
                        instruktioner.add("f");
                    } else if (olddeltaY < 0) {
                        riktning = -1;
                        //fram
                        instruktioner.add("f");
                    } else if (olddeltaX > 0) {
                        //vänster
                        instruktioner.add("l");
                    } else if (olddeltaX < 0) {
                        //höger
                        instruktioner.add("r");
                    }
                } else if (deltaY < 0) {
                    if (olddeltaY > 0) {
                        riktning = -1;
                        //fram
                        instruktioner.add("f");
                    } else if (olddeltaY < 0) {
                        riktning = 1;
                        //fram
                        instruktioner.add("f");
                    } else if (olddeltaX > 0) {
                        //höger
                        instruktioner.add("r");
                    } else if (olddeltaX < 0) {
                        //vänster
                        instruktioner.add("l");
                    }
                }
            }

            //Om förändring i X-led men inte i Y-led
            if (deltaY == 0) {
                System.out.println("eller kommer jag in här?");
                if (deltaX > 0) {
                    System.out.println("snälla kom in 1");
                    if (olddeltaX > 0) {
                        riktning = 1;
                        //fram
                        instruktioner.add("f");
                        System.out.println("Y och f1");
                    } else if (olddeltaX < 0) {
                        riktning = -1;
                        //fram
                        instruktioner.add("f");
                        System.out.println("Y och f2");
                    } else if (olddeltaY > 0) {
                        //höger
                        instruktioner.add("r");
                        System.out.println("Y och r");
                    } else if (olddeltaY < 0) {
                        //vänster
                        instruktioner.add("l");
                        System.out.println("Y och l");
                    }
                } else if (deltaX < 0) {
                    System.out.println("snälla kom in 2");
                    if (olddeltaX > 0) {
                        riktning = -1;
                        //fram
                        instruktioner.add("f");
                        System.out.println("Y och X och f1");
                    } else if (olddeltaX < 0) {
                        riktning = 1;
                        //fram
                        instruktioner.add("f");
                        System.out.println("Y och X och f2");
                    } else if (olddeltaY > 0) {
                        //vänster
                        instruktioner.add("l");
                        System.out.println("Y och X och l");
                    } else if (olddeltaY < 0) {
                        //höger 
                        instruktioner.add("r");
                        System.out.println("Y och X och r");
                    }
                }
            }

            //Uppdatera X1 och Y1
            X1 = X2;
            Y1 = Y2;

            //Tester        
            System.out.println("vad är detta för koordinaterna " + X1 + " " + Y1);

            //Spara de gamla värdena för X och Y    
            olddeltaX = deltaX;
            olddeltaY = deltaY;

            System.out.println("oldisarna " + olddeltaX + olddeltaY);

            String Körorder = " ";
            for (int k = 0; k < instruktioner.size(); k++) {
                Körorder = Körorder + " " + instruktioner.get(k).toString();
            }
            System.out.println("Körorder: " + Körorder);

            System.out.println("Storlek instruktioner: " + instruktioner.size());
            System.out.println("Utskrift instruktioner:" + instruktioner);

                //Sätt flaggan till GuiUpdate sann
            //GuiFlag=true;
                //uppdateraprick=1;
            break;

        }

    }
}
