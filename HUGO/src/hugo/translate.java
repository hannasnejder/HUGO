package hugo;

import java.util.*;

public class translate {
    
    public DataStore ds;
    public RobotRead rr;
    public Avboka avboka;
    ArrayList<Integer> bokningar;
    ArrayList<Character> instruktioner;
    ArrayList<Character> svar; 
    
    public translate(RobotRead rr, Avboka avboka){
        this.rr=rr;
        this.avboka = avboka;
        ds = new DataStore();

    }
    
    //Här ska vi tolka vad roboten faktisk säger
    //Vad ska avbokas?
    //Säkerhetsstopp?
    public void interpret(){
        
        System.out.println("Går in i translate");
        
        //Om vi får in något fråm robot, sätt till 1
        ds.bokaom = 1;
        
        //När vi har översatt och vill avboka
        avboka.avbokaRobot();
    }
    
}
