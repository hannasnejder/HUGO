package hugo;

import java.util.*;
import java.io.*;
//import javax.microedition.io.*;
//import javax.bluetooth.*;
import java.lang.*; //objekt.org.springframework.util.StringUtils;
import java.util.concurrent.TimeUnit;


public class RobotRead implements Runnable {
    private int sleepTime;
    private static Random generator = new Random();
    public ControlUI cui;
    public DataStore ds;
    public Avboka avboka;
    public translate tr;
    public threads th;
    ArrayList<Character> instruktioner; 
    ArrayList<Character> svarRobot = new ArrayList();

    int [] från_robot = new int [2];
    char meddelande_in;
 
    
    public RobotRead(DataStore ds, ControlUI cui, translate tr, threads th){
        this.cui=cui;
        this.ds=ds;
        this.tr = tr;
        this.th = th;
        
        instruktioner = new ArrayList<Character>();

        sleepTime=generator.nextInt(20000);
        //ds.robotflag = false;

    }
    
   // public void sendRobot(){
        
   // }
    
   // public void RobotSend(){
        
   // }
 
    @Override
    public void run(){
        try{
            //TimeUnit.SECONDS.sleep(2); 
            
            boolean run = true;
            while(run){
            TimeUnit.SECONDS.sleep(1); 
           // while(ds.robotflag == true){
                System.out.println("RobotRead flaggan blir sann");
                
            //När vi har något från robot anropar vi translate för att översätta
            tr.interpret();
            //System.out.println("Lyckades anropa translate");
            
            /*cui.appendStatus("RobotRead kommer att köra i " + sleepTime + " millisekunder.");
            
            
           // Kör i kommandofönstret
           // cui.appendStatus("RobotRead kommer att köra i " + sleepTime + " millisekunder.");
           // cui.appendStatus("Körorder är: " + körorder);

            //Skapar anslutning. Siffrorna är mottagarens, fås via browse.
            //Siffran efter kolon är kanalen som används. 
          
            //StreamConnection anslutning = (StreamConnection)
            //Connector.open("btspp://C0F8DAE35DC6:1");       
            //Roboten 98D331902C27

            
            // PrintStream bluetooth_ut = new PrintStream(anslutning.openOutputStream());
        
            // BufferedReader bluetooth_in = new BufferedReader(new
            // InputStreamReader(anslutning.openInputStream()));
            
            
            //while(ds.bokaflag == true){

                   // Thread.sleep(sleepTime/20);
                   // ds.updateUIflag=true; 
                    
                   // for (int m = 0; m < instruktioner.size(); m++){
                  //      bluetooth_ut.println(instruktioner.get(m));
                 //   }
                    
                 //   StringTokenizer st = new StringTokenizer(körorder, " ");
                    //Det vi skickar till roboten
                 //  while (st.hasMoreTokens()){
                        //TimeUnit.SECONDS.sleep(2);
                 //       bluetooth_ut.println(st.nextToken());   
                        
                        
                        String meddelande_in = bluetooth_in.readLine();     //Mottaget meddelande när ubuntu används
                        System.out.println("Mottaget: " + meddelande_in);
                        
                        //Mottaget meddelande från robot
                        //char c = bluetooth_in.charAt(0);
                    int c;            //Använd då vi skickar med robot

                        //Mottaget meddelande när ubuntu används
                        //String meddelande_in = bluetooth_in.readLine();     
                        //System.out.println("Mottaget: " + meddelande_in);
                        
                        //Mottaget meddelande från robot
                  //  }  
                    
<<<<<<< HEAD
                   // int c;     
                     
                    //Det roboten skickar till oss
                    //while((c = bluetooth_in.read()) != -1){
=======
                    //char c = bluetooth_in.charAt(0);
                   /* int c;     
                    
                    //Det vi får av roboten när vi skickar till robot

                    while((c = bluetooth_in.read()) != -1){
                        meddelande_in = (char) c;
                        System.out.println("Mottaget: " + meddelande_in);
>>>>>>> 96d1afe3df7a271adf9facddcca479107390a5c2
                        
                        //Gör om det vi får till en char
                   //     meddelande_in = (char) c;
                        
<<<<<<< HEAD
                        //Läser in det vi får i en ArrayList som char
                 //       svarRobot.add(meddelande_in);
                        

                        //System.out.println("Mottaget: " + meddelande_in);
                        
                        //från_robot[k] = meddelande_in;
                        //k = k +1;
                //    }    
=======
                        från_robot[k] = meddelande_in;
                        k = k +1;
                        //Anropa avboka.avbokaRobot(); när vi vill avboka
                        //Kanske ska göras från translate
                        
                        //ds.robotflaga = true;
                    }    
>>>>>>> 96d1afe3df7a271adf9facddcca479107390a5c2
                    
                    
                //    System.out.println(Arrays.toString(från_robot));

<<<<<<< HEAD
                //    cui.appendStatus("Körinstruktioner: " + körorder);
                //    anslutning.close();
                //   ds.robotflaga = true;
                //}             
        
            ds.updateUIflag = true; 
            
        }catch(Exception e) {  
    
            System.out.print("RobotRead" + e.toString());   
=======
                //System.out.println("från_robot: " + Arrays.toString(från_robot));*/
           // ds.updateUIflag = true;
            //ds.robotflag = false;
            
            //Bokaflag sätts till true för att gå tillbaka till boka
            //ds.bokaflag = true;
            //th.startThreads();
            
            System.out.println("Bokaflaggan blir sann");
            }
        }catch(Exception e) {  
            System.out.print("Det här är e, RobotRead" + "\n" + e.toString());   
        }

        
        //Nödvändig kommentar?
        //cui.appendStatus("RobotRead är nu klar");

    }

}
