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
    private ControlUI cui;
    private DataStore ds;
    public Boka boka;
    public drive dr;
    public translate tr;
    //dr.startRiktning();
    //int [] körorder = {2, 3, 4, 7};
    //String körorder = "23 7 55 16";

    ArrayList<Character> instruktioner; 
    ArrayList<Character> svarRobot = new ArrayList();
    Vector<Character> v = new Vector();


    public Boka b1;


    char meddelande_in;
    int k = 0;
    //int [] avbokning_från_robot;
    //String meddelande_in;
    
    String från_robot = " ";
    

    public RobotRead(DataStore ds, ControlUI cui, drive dr, translate tr){
        this.cui=cui;
        this.ds=ds;
        this.dr = dr;
        this.tr = tr;
        //om koden nedan används borde instruktioner bli tom då det är en ny som skapas
        //instruktioner = new ArrayList<Character>();

        //this.b1 = b1;
        sleepTime=generator.nextInt(20000);
        //dr.startRiktning();
        //instruktioner = new ArrayList<Character>();
        //ArrayList<Character> instruktioner;
    }
  
    @Override
    public void run(){
        try{
            TimeUnit.SECONDS.sleep(2);
            cui.appendStatus("RobotRead kommer att köra i " + sleepTime + " millisekunder.");
            cui.appendStatus("Körorder är: " + dr.instruktioner);
            
            //tr.interpret();
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
                if(anslutning == null || dr.instruktioner == null){
                    cui.appendStatus("Koppling saknas eller körorder är tom");
                    break;
                }else 
                    Thread.sleep(sleepTime/20);
                    ds.updateUIflag=true; 
                                        
                    //bluetooth_ut.println(dr.instruktioner);
                    /*for (int m = 0; m < dr.instruktioner.size(); m++){
                        bluetooth_ut.println(dr.instruktioner.get(m));
                        System.out.println("Skickat meddelande till robot: " + dr.instruktioner.get(m));
                    }*/
                    System.out.println("Storlek instruktioner Robotread " + dr.instruktioner.size());
                    int m = 0;
                    while(m < dr.instruktioner.size()){
                        bluetooth_ut.println(dr.instruktioner.get(m));
                        m = m +1;
                    }
                    System.out.println("Instruktioner är: " + dr.instruktioner);

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
                        if(v.size() == dr.instruktioner.size()) 
                            System.out.println("Storlekt vektor:" + v.size());
                            break;
                         
                    } 
                    

                    anslutning.close();
                    tr.interpret();
                    /*//System.out.println("Svar från robot: " + svarRobot);
                    /*for(int m = 0; m < körorder.length(); m++){
                            från_robot = från_robot + " " + meddelande_in;
                            System.out.println("från roboten kommer:" + från_robot);
                        }
                                       
                    System.out.println("ute efter mottagande av meddelande");
                    if(k == 2){
                        System.out.println("inne i min if");
                        for(int t = 0; t<=körorder.length(); t++){
                            System.out.println("inne i for efter if");            
                            avbokning_från_robot[t] = boka.resurser[t];
                        
                        }
                        ds.robotflaga = true;
                        System.out.println("Ute ur for och flagan har satts till true");
                    } 
                    //}
                    System.out.println("Hejhej");
                 if (v.contains('h')== true){
                     System.out.println("Vektorn är: " + v);
                     
                        /*for(int g = 0; g<v.size(); g++){
                            if(v.elementAt(g) == 'h'){
                        System.out.println("hittad");
                            }
                        } */ 
                        //System.out.println("vektorn är utanför if: " + v);
                 cui.appendStatus("Körinstruktioner: " + dr.instruktioner);
                 //anslutning.close();
                 //i++;
                 //ds.robotflaga = true;
                 
                }               
            tr.interpret();
        }catch(Exception e) {  
            System.out.print("RobotRead" + e.toString());   
        }

        
        //Nödvändig kommentar?
        cui.appendStatus("RobotRead är nu klar");

    }

}
