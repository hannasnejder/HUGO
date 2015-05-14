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
        
        //Vet inte om de här behövs....
        bokningar = new ArrayList<Integer>();
        instruktioner = new ArrayList<Character>();
        svar = new ArrayList<Character>(); 
        
    }
    
    //Här ska vi tolka vad roboten faktisk säger
    //Vad ska avbokas?
    //Säkerhetsstopp?
    public void interpret(){
        
    }
    
}
