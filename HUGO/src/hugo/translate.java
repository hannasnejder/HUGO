package hugo;

import java.util.*;

public class translate {
    
    public DataStore ds;
    public RobotRead rr;
    ArrayList<Integer> bokningar;
    ArrayList<Character> instruktioner;
    ArrayList<Character> svar; 
    
    public translate(RobotRead rr){
        this.rr=rr;
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
