package hugo;


import java.util.*;
import java.io.*;
import javax.microedition.io.*;
import javax.bluetooth.*;
import java.lang.*; //objekt.org.springframework.util.StringUtils;

public class RobotRead implements Runnable {
    private int sleepTime;
    private static Random generator = new Random();
    private ControlUI cui;
    private DataStore ds;
    public Boka boka;
    public drive dr;
    ArrayList<Character>instruktioner;
    //char l = 'l';
    //char r = 'r';
    //int [] körorder = {2, 3, 4, 7};
    //String körorder = "23 7 55 16";


    //ArrayList<Character> instruktioner; 
    ArrayList<Character> svarRobot = new ArrayList();


    public Boka b1;

    String körorder = "w";

    char meddelande_in;
    int k = 0;
    int [] avbokning_från_robot;
    String lek;
    //String meddelande_in;
    
    String från_robot = " ";
    

    public RobotRead(DataStore ds, ControlUI cui){
        this.cui=cui;
        this.ds=ds;
        //om koden nedan används borde instruktioner bli tom då det är en ny som skapas
        //instruktioner = new ArrayList<Character>();

        //this.b1 = b1;
        sleepTime=generator.nextInt(20000);

    }
  
    @Override
    public void run(){
        try{

            cui.appendStatus("RobotRead kommer att köra i " + sleepTime + " millisekunder.");

            cui.appendStatus("Körorder är: " + instruktioner);
            
            /*lek = " ";
            for(int k = 0; k < dr.instruktioner.size(); k++ ){
            lek = lek + " " + dr.instruktioner.get(k).toString();
            }
            System.out.println("lek blir efter for" + lek);*/
            
            //Skapar anslutning. Siffrorna är mottagarens, fås via browse.
            //Siffran efter kolon är kanalen som används. 
            StreamConnection anslutning = (StreamConnection)
            Connector.open("btspp://C0F8DAE35DC6:1");       
            //Roboten 98D331902C27
            
            PrintStream bluetooth_ut = new PrintStream(anslutning.openOutputStream());
        
            BufferedReader bluetooth_in = new BufferedReader(new
            InputStreamReader(anslutning.openInputStream()));
            
            
            while(ds.robotskickaflaga == true){
                if(anslutning == null || instruktioner == null){
                    cui.appendStatus("Koppling saknas eller körorder är tom");
                    break;
                }else 
                    Thread.sleep(sleepTime/20);
                    ds.updateUIflag=true; 
                    
                    System.out.println("Instruktioner: " + instruktioner);
                    for (int m = 0; m < instruktioner.size(); m++){
                        bluetooth_ut.println(instruktioner.get(m));
                        System.out.println("Instruktioner är: " + instruktioner);
                    }
                    /*StringTokenizer st = new StringTokenizer(lek, " ");
                    //Det vi skickar till roboten
                    //while (st.hasMoreTokens()){
                        //TimeUnit.SECONDS.sleep(2);
                        //bluetooth_ut.println(st.nextToken());   

                        //Mottaget meddelande när ubuntu används
                        /*String meddelande_in = bluetooth_in.readLine();     
                        System.out.println("Mottaget: " + meddelande_in);*/
                        
                        //Mottaget meddelande från robot
                  //  }  
                    
                    //Det vi får av roboten när vi skickar till robot
                    int c; 
                    while((c = bluetooth_in.read()) != -1){
                        meddelande_in = (char) c;
                        System.out.println("Mottaget: " + meddelande_in);
                        //från_robot = Character.toString(meddelande_in);
                        k = k + 1; 
                        System.out.println("k: " + k);
                        break;
                    } 
                    
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
                    } */
                    //}


                 cui.appendStatus("Körinstruktioner: " + lek);
                 anslutning.close();
                 //i++;
                 //ds.robotflaga = true;
                }               

        }catch(Exception e) {  
            System.out.print("RobotRead" + e.toString());   
        }

        
        //Nödvändig kommentar?
        cui.appendStatus("RobotRead är nu klar");

    }

}
