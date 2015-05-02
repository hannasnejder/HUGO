package hugo;
import java.util.Arrays;
import java.util.Random;

public class GuiUpdate implements Runnable{
        private int sleepTime;
        private static Random generator = new Random();
        private ControlUI cui;
        private DataStore ds;
        public OptPlan opt;
        private MapPanel map;
        private boolean robot=false;
        int x[]=new int[10000];
        int kör[];
        int hej[];
        //private MapPanel[] rörelse=new MapPanel[100];
        
        public GuiUpdate(DataStore ds, ControlUI cui, OptPlan opt){
            this.cui = cui;
            this.ds = ds;
            x=opt.noder_boka;
            kör=opt.resurser_boka;
            //hej=opt.robot;
            sleepTime = generator.nextInt(20000);
        }
        
        @Override 
        public void run(){
            try{
                cui.appendStatus("GuiUpdate startar och kommer att köra i " + sleepTime + " millisekunder. ");
                //int i=1;
                cui.appendStatus("Hyllplatser: " + ds.besoknoder);   //cui.appendStatus för att skriva ut i rutan som kommer upp
                cui.appendStatus("Resurser: " + Arrays.toString(kör)); //gör om arrayen med resurser(länkar och noder) till textsträng o skriv ut
                System.out.println(Arrays.toString(x)); //gör om arrayen med bokade noder till textsträng o skriver ut
  
                while(ds.updateUIflag==false){
                    Thread.sleep(sleepTime / 20); 
                }
                //System.out.println("Test " + Arrays.toString(x));
                               /* for(int j = 0; j <= x.length; j++){
                    
                    if(hej[j] == 1){
                        ds.robotX = j*10;
                    }
                    else
                        System.out.println("Gick inte in i if");
                }*/
                
                int j = 1;
                //Försöker få roboten att flytta sig efter order
                //for(int j = 0; j < 100; j++){
               // System.out.println("loopen går " + j + " varv");
               // int j = 1;
               // while(x[j+1] - x[j] > 1){
                
             
              
            //startnodens koordinater finns sparade i startnodX och startnodY i DataStore
               System.out.println("startnodX "+ds.startnodX);
                System.out.println("startnodY "+ds.startnodY);
                ds.robotX=ds.startnodX; //Nod 1s x-koordinat 
                ds.robotY=ds.startnodY;
                
                
                
                
                
                while(j <= x[3]){
                         Thread.sleep(sleepTime/20);
                         //cui.appendStatus("Jag är tråd GuiUpdate! För " +j+ ":te gången. ");
                         //System.out.println(x[j]);
                           //ds.robotX = 
                            //ds.robotY = 1;
                            //j++;
                            //System.out.println("Går igenom while-loopen " + j);
                      
                    //ds.robotY = j*14.33;
                        
                   robotrörelse(); 
                    
                   
                     //System.out.println("while går " + j + " varv");
                    j++;
                }
                
                int k = 23;
                while(x[3] <= k && k <= x[6]){      //Gör vad?
                    Thread.sleep(sleepTime/200);
                   // ds.robotX=ds.startnodX;
                    // ds.robotY=ds.startnodY;
                    
       
                   // System.out.println("Går igenom while-loopen " + k);
                 // ds.robotX = j*10;
                    
                    
                    
                    robotrörelse(); 
                    
                    //System.out.println("while går " + j + " varv");
                    k++;    
                }
                
            //} 
            
        }catch(InterruptedException exception){
        }
        cui.appendStatus("GuiUpdate är nu klar! ");
        }
        //Uppdaterar kartan med robotens position
    public void robotrörelse(){
        robot = true;
        cui.repaint();
    }      
}

