package hugo;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import javax.microedition.io.*;
import javax.bluetooth.*;
import java.lang.*; //objekt.org.springframework.util.StringUtils;


public class RobotRead implements Runnable {
    private int sleepTime;
    private static Random generator = new Random();
    private ControlUI cui;
    private DataStore ds;
    public Boka boka;
    ArrayList<Character> instruktioner; 
    ArrayList<Character> svarRobot = new ArrayList();
    String körorder = "w";
    char meddelande_in;
    int k = 0;
    
    int [] från_robot = new int [2];
    
    public RobotRead(DataStore ds, ControlUI cui){
        this.cui=cui;
        this.ds=ds;

        instruktioner = new ArrayList<Character>();

        //this.b1 = b1;
        sleepTime=generator.nextInt(20000);

    }
 
    @Override
    public void run(){
        try{
            
            cui.appendStatus("RobotRead kommer att köra i " + sleepTime + " millisekunder.");
            cui.appendStatus("Körorder är: " + körorder);
            
          
            
            //Skapar anslutning. Siffrorna är mottagarens, fås via browse.
            //Siffran efter kolon är kanalen som används. 
            StreamConnection anslutning = (StreamConnection)
            Connector.open("btspp://C0F8DAE35DC6:1");       
            //Roboten 98D331902C27
            
            PrintStream bluetooth_ut = new PrintStream(anslutning.openOutputStream());
        
            BufferedReader bluetooth_in = new BufferedReader(new
            InputStreamReader(anslutning.openInputStream()));
            
            
            while(ds.bokaflag == true){

                    Thread.sleep(sleepTime/20);
                    ds.updateUIflag=true; 
                    
                    for (int m = 0; m < instruktioner.size(); m++){
                        bluetooth_ut.println(instruktioner.get(m));
                    }
                    
                 //   StringTokenizer st = new StringTokenizer(körorder, " ");
                    //Det vi skickar till roboten
                 //  while (st.hasMoreTokens()){
                        //TimeUnit.SECONDS.sleep(2);
                 //       bluetooth_ut.println(st.nextToken());   
                        
                        
                        //Mottaget meddelande när ubuntu används
                        /*String meddelande_in = bluetooth_in.readLine();     
                        System.out.println("Mottaget: " + meddelande_in);*/
                        
                        //Mottaget meddelande från robot
                  //  }  
                    
                    //char c = bluetooth_in.charAt(0);
                    int c;     
                    
                    //Det vi får av roboten när vi skickar till robot
                    while((c = bluetooth_in.read()) != -1){
                        meddelande_in = (char) c;
                        System.out.println("Mottaget: " + meddelande_in);
                        
                        
                        från_robot[k] = meddelande_in;
                        k = k +1;
                        
                        //ds.robotflaga = true;*/
                    }    
                    
                    
                    System.out.println(Arrays.toString(från_robot));

                    cui.appendStatus("Körinstruktioner: " + körorder);
                    anslutning.close();
                    ds.robotflaga = true;
                }               

        }catch(Exception e) {  
            System.out.print("RobotRead" + e.toString());   
        }
        
        //Nödvändig kommentar?
        cui.appendStatus("RobotRead är nu klar");
    }

}
