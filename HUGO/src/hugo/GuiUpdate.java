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
 
        int x [] = new int[10000];
        double startX, startY, nextX, nextY;
        double kopiaX1, kopiaX2, Y1, Y2;
        
    public GuiUpdate(DataStore ds, ControlUI cui, OptPlan opt, drive dr){
        this.cui = cui;
        this.ds = ds;
        this.dr=dr;
        this.opt=opt;
        //sleepTime = generator.nextInt(20000);
    }
        
        @Override 
        public void run(){
            try{
               // cui.appendStatus("GuiUpdate startar och kommer att köra i " + sleepTime + " millisekunder. ");
                cui.appendStatus("Hyllplatser: " + ds.besoknoder);
                cui.appendStatus("Resurser: " + Arrays.toString(ds.resurser_boka));

                //System.out.println(Arrays.toString(x));
              
                while(ds.updateUIflag==false){
                    TimeUnit.SECONDS.sleep(1);
                   // Thread.sleep(sleepTime / 20); 
                }
        
            //drive: kopiaX1 och kopiaY1 är startnodens koordinater
            //drive: X2 och Y2 är första bokade nodens koordinater
            System.out.println("Inne i GuiUpdate");
            
            //while(en flagga i drive har satts till true)
            
            startX = dr.kopiaX1;   //startnoden som uppdateras i drive
            startY = dr.kopiaY1;   //startnoden som uppdateras i drive
            nextX = dr.X2;
            nextY = dr.Y2;

            //åk till vänster på kartan
             while (startX >= nextX) {
                //Thread.sleep(sleepTime / 20);
                TimeUnit.SECONDS.sleep(1);
                ds.robotX = startX;
                startX = startX - 10;
                robotrorelse();
            }

           //åk till höger på kartan
            while (startX <= nextX) {
               // Thread.sleep(sleepTime / 20);
                TimeUnit.SECONDS.sleep(1);
                ds.robotX = startX;
                startX = startX + 10;
                robotrorelse();
            }

            //åk neråt på kartan
             while (startY >= nextY) {
                //Thread.sleep(sleepTime / 20);
                TimeUnit.SECONDS.sleep(1);
                ds.robotY = nextY;
                startY = startY - 10;
                robotrorelse();
            }

            //åk uppåt på kartan          
         while (startY <= nextY) {
                //Thread.sleep(sleepTime / 20);
                TimeUnit.SECONDS.sleep(1);
                ds.robotY = startY;
                startY = startY + 10;
                robotrorelse();
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

