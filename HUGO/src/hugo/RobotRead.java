package hugo;


import java.util.*;
import java.io.*;
import javax.microedition.io.*;
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
    Vector<Character> v = new Vector();

    char meddelande_in;
    int k = 0;
     
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
            TimeUnit.SECONDS.sleep(2); 
            while(ds.robotflag == true){
                System.out.println("RobotRead flaggan blir sann");
                
            //När vi har något från robot anropar vi translate för att översätta
            tr.interpret();
            System.out.println("Lyckades anropa translate");
            
            cui.appendStatus("RobotRead kommer att köra i " + sleepTime + " millisekunder.");
            
            
           // Kör i kommandofönstret
           cui.appendStatus("RobotRead kommer att köra i " + sleepTime + " millisekunder.");
           cui.appendStatus("Körorder är: " + instruktioner);

            //Skapar anslutning. Siffrorna är mottagarens, fås via browse.
            //Siffran efter kolon är kanalen som används. 
          
            StreamConnection anslutning = (StreamConnection)
            Connector.open("btspp://C0F8DAE35DC6:1");       
            //Roboten 98D331902C27
            //Ubuntu C0F8DAE35DC6

            
            PrintStream bluetooth_ut = new PrintStream(anslutning.openOutputStream());
        
            BufferedReader bluetooth_in = new BufferedReader(new
            InputStreamReader(anslutning.openInputStream()));
            
    
            while(ds.robotskickaflaga = true){
                if(anslutning == null || instruktioner == null){
                    cui.appendStatus("Koppling saknas eller körorder är tom");
                    break;
                }else 
                    Thread.sleep(sleepTime/20);
                    ds.updateUIflag=true; 
                                        
                    //bluetooth_ut.println(dr.instruktioner);
                    for (int m = 0; m < instruktioner.size(); m++){
                        bluetooth_ut.println(instruktioner.get(m));
                        System.out.println("Skickat meddelande till robot: " + instruktioner.get(m));
                    }
                    System.out.println("Storlek instruktioner Robotread " + instruktioner.size());

                    int c; 
                    while((c = bluetooth_in.read()) != -1){
                        meddelande_in = (char) c;
                        System.out.println("Mottaget: " + meddelande_in);
                        //for(int k = 0; k < dr.instruktioner.size(); k++){
                        if((c = bluetooth_in.read()) == -1){        //If else enbart med Ubuntu annars behövs den inte
                            break;                                                                                   
                        }
                        else
                            v.addElement(meddelande_in);
                        if(v.size() == instruktioner.size()) 
                            System.out.println("Storlekt vektor:" + v.size());
                            break;
                         
                    } 
                    

                    anslutning.close();

                    System.out.println("Hejhej");
                 if (v.contains('h')== true){
                     System.out.println("Vektorn är: " + v);
                    } 
                        /*for(int g = 0; g<v.size(); g++){
                            if(v.elementAt(g) == 'h'){
                        System.out.println("hittad");
                            }
                        } */ 
                        //System.out.println("vektorn är utanför if: " + v);
                 cui.appendStatus("Körinstruktioner: " + instruktioner);
                 //anslutning.close();
                 //i++;
                 //ds.robotflaga = true;
                 
                               
            //tr.interpret();
             
                    
                    //char c = bluetooth_in.charAt(0);
                   /* int c;     
                    
                    //Det vi får av roboten när vi skickar till robot

                    while((c = bluetooth_in.read()) != -1){
                        meddelande_in = (char) c;
                        System.out.println("Mottaget: " + meddelande_in);
                    }*/
                          

                        //Anropa avboka.avbokaRobot(); när vi vill avboka
                        //Kanske ska göras från translate
                        
                        //ds.robotflaga = true;
                    
                    
                //    System.out.println(Arrays.toString(från_robot));

                //    cui.appendStatus("Körinstruktioner: " + körorder);
                //    anslutning.close();
                //}             
        

                //System.out.println("från_robot: " + Arrays.toString(från_robot));*/
            ds.updateUIflag = true;
            ds.robotflag = false;
            
            //Bokaflag sätts till true för att gå tillbaka till boka
            ds.bokaflag = true;
            System.out.println("Bokaflaggan blir sann");
            }
            }
        }catch(Exception e) {  
            System.out.print("Det här är e, RobotRead" + "\n" + e.toString());   
        }

        
        //Nödvändig kommentar?
        //cui.appendStatus("RobotRead är nu klar");

    

}
}
