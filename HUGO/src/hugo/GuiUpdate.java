package hugo;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GuiUpdate implements Runnable{
        //private int sleepTime;
        //private static Random generator = new Random();
        private ControlUI cui;
        private DataStore ds;
        public OptPlan opt;
        public drive dr;
 
        double kopiaX1, kopiaY1, X2, Y2;
        
    public GuiUpdate(DataStore ds, ControlUI cui, OptPlan opt, drive dr){
        this.cui = cui;
        this.ds = ds;
        this.dr=dr;
        this.opt=opt;    
    }         
    
        //I drive anropas run(). Vill att allt nedanför run() ska köras då
        @Override 
        public void run(){
            try{
               // cui.appendStatus("GuiUpdate startar och kommer att köra i " + sleepTime + " millisekunder. ");
                cui.appendStatus("Hyllplatser: " + ds.besoknoder);
                cui.appendStatus("Resurser: " + Arrays.toString(ds.resurser_boka));
          
                while(ds.updateUIflag==false){
                    TimeUnit.SECONDS.sleep(3);      //FÖRDRÖJNING? 1? 3?
                }
        
            //drive: kopiaX1 och kopiaY1 är startnodens koordinater
            //drive: X2 och Y2 är första bokade nodens koordinater
            System.out.println("Inne i GuiUpdate");
            
            //while(en flagga i drive har satts till true)

             //åk till vänster på kartan
             while (kopiaX1 > X2) {
                TimeUnit.SECONDS.sleep(3);      //Hur länge ska tråden sova?
                ds.robotX = kopiaX1;
                kopiaX1 = kopiaX1 - 10;         //Öka 10 för att pricken ska åka snabbare. Gäller även för resterande while-looparna
                robotrorelse();                
                break;
            }

           //åk till höger på kartan
            while (kopiaX1 < X2) {
                TimeUnit.SECONDS.sleep(3);
                ds.robotX = kopiaX1;
                kopiaX1 = kopiaX1 + 10;
                robotrorelse();
                break;
            }

            //åk neråt på kartan
             while (kopiaY1 > Y2) {
                TimeUnit.SECONDS.sleep(3);
                System.out.println("KOM IN HÄR");
                ds.robotY = kopiaY1;
                kopiaY1 = kopiaY1 - 10;
                robotrorelse();
                break;
            }

            //åk uppåt på kartan          
         while (kopiaY1 < Y2) {
                TimeUnit.SECONDS.sleep(3);
                ds.robotY = kopiaY1;
                kopiaY1 = kopiaY1 + 10;
                robotrorelse();
               break;
            }

       
            
           // System.out.println("Flaggan i GuiUpdate är sann");

          
          // } //stäng loopen
        } catch (InterruptedException exception) {
        }
        cui.appendStatus("GuiUpdate är nu klar! ");
    }

    //Uppdaterar kartan med robotens position

   public void robotrorelse() {
        cui.repaint();
    }
}