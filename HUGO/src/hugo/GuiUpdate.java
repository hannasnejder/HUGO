package hugo;
import java.util.Arrays;
import java.util.Random;

public class GuiUpdate implements Runnable{
        private int sleepTime;
        private static Random generator = new Random();
        private ControlUI cui;
        private DataStore ds;
        public OptPlan opt;
 
        int x [] = new int[10000];
        int kör [];
        
    public GuiUpdate(DataStore ds, ControlUI cui, OptPlan opt){
        this.cui = cui;
        this.ds = ds;
        x=opt.noder_boka;
        kör=opt.resurser_boka;
        sleepTime = generator.nextInt(20000);
    }
        
        @Override 
        public void run(){
            try{
                cui.appendStatus("GuiUpdate startar och kommer att köra i " + sleepTime + " millisekunder. ");
                cui.appendStatus("Hyllplatser: " + ds.besoknoder);
                //cui.appendStatus("Resurser: " + Arrays.toString(kör));
                //System.out.println(Arrays.toString(x));
              
                while(ds.updateUIflag==false){
                    Thread.sleep(sleepTime / 20); 
                }
                
                int j = 1;
                
                //Ska kolla om looparna går att kombinera med Johannas kod
                //Försöker få roboten att flytta sig efter order
                while(j <= x[3]){
                         Thread.sleep(sleepTime/20);
                            
                    ds.robotY = j*14.33;
                    robotrörelse(); 
                    
                    //System.out.println("while går " + j + " varv");
                    j++;
                }
                
                int k = 23;
                
                //Behöver fixa så att picken rör sig långsammare, som i loopen ovan
                while(x[3] <= k && k <= x[10]){
                    Thread.sleep(sleepTime/20);      
                    // System.out.println("Går igenom while-loopen " + k);
        
                    ds.robotX = k*14;
                    robotrörelse(); 
                    //System.out.println("k " + k );
                    k++;    
                }
      
            //startnodens koordinater finns sparade i startnodX och startnodY i DataStore
               //System.out.println("startnodX "+ds.startnodX);
                //System.out.println("startnodY "+ds.startnodY);
                ds.robotX=ds.startnodX; //Nod 1s x-koordinat 
                ds.robotY=ds.startnodY;
                
        }catch(InterruptedException exception){
            
        }
        cui.appendStatus("GuiUpdate är nu klar! ");
        }

     //Uppdaterar kartan med robotens position
    public void robotrörelse(){
        cui.repaint();
    }      
}

