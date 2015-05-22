package hugo;


import java.util.*;
import java.io.*;
import javax.microedition.io.*;
import javax.bluetooth.*;
import java.lang.*; //objekt.org.springframework.util.StringUtils;
import java.util.concurrent.TimeUnit;


public class RobotRead implements Runnable {
   private int sleepTime;
    private static Random generator = new Random();
    public ControlUI cui;
    public DataStore ds;
    public translate tr;
    ArrayList<Character> instruktioner; 
    Vector<Character> v = new Vector();     

    char meddelande_in;
    
    private StreamConnection anslutning;
    private PrintStream bluetooth_ut;
    private BufferedReader bluetooth_in;
    
    public RobotRead(DataStore ds, ControlUI cui, translate tr){
        this.cui=cui;
        this.ds=ds;
        this.tr = tr;
        
        sleepTime=generator.nextInt(20000);
        
            //Skapar anslutning. Siffrorna är mottagarens, fås via browse.
            //Siffran efter kolon är kanalen som används. 
        
           try {
            ////Roboten 98D331902C27
            anslutning = (StreamConnection) Connector.open("btspp://C0F8DAE35DC6:1");

            bluetooth_ut = new PrintStream(anslutning.openOutputStream());
            bluetooth_in = new BufferedReader(new InputStreamReader(anslutning.openInputStream()));
            TimeUnit.SECONDS.sleep(2);

        } catch (Exception e) {
            System.out.print("Det här är e, RobotRead 1" + "\n" + e.toString());
        }
    }
    
    //Skicka till instruktionerna till Robot
    public void sendRobot(){
      
        for (int m = 0; m < ds.instruktioner.size(); m++){
            bluetooth_ut.println(instruktioner.get(m));
        }
    }
     
    @Override
    public void run(){
        try{
            //Skicka till roboten första gången            
            sendRobot();
 
            //Tar emot data från robot efter första gången instruktioner sänds till robot
            
            //slutkommandot ska avbryta skickande
            //while(c = bluetooth_in.read() != slutkommando)
            int c;
            while(true){
                //behövs inte med slutkommandot
                c = bluetooth_in.read();
                
                meddelande_in = (char) c;
                v.addElement(meddelande_in);
                sendRobot();
            }
            
            //ds.robotflag==true;
            //tr.interpret();

            
        }catch(Exception e) {  
            System.out.print("Det här är e, RobotRead" + "\n" + e.toString());   
        }

    }

}
