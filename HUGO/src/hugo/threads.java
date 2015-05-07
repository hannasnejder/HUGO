package hugo;

public class threads {

    //Hämtar från klasser
    DataStore ds;
    ControlUI cui;
    OptPlan opt;
    MapPanel map;   
    Boka boka;
    RobotRead rr;
    OptOnline online;
    Avboka avboka;

    //Trådar
    RobotRead r1;
    Thread t1; 
    
    GuiUpdate g1;
    Thread t2;   
    
    Boka b1; 
    Thread t3; 

    Avboka b2;
    Thread t4;    
    
    AvbokaRobot b3;
    Thread t5;
    
    threads(DataStore ds, ControlUI cui){
        this.ds = ds;
        this.cui = cui;
        
        opt = new OptPlan(this.ds, this.opt);
        boka= new Boka(this.opt, this.ds, this.online); 
        online = new OptOnline(this.opt, this.ds);
 
        r1 = new RobotRead (this.ds, this.cui);
        t1 = new Thread(r1);
        g1 = new GuiUpdate (this.ds,this.cui, this.opt);
        t2 = new Thread(g1); 
        b1 = new Boka(this.opt, this.ds, this.online);
        t3 = new Thread(b1);;
        b2 = new Avboka(this.opt, this.boka, this.ds);
        t4 = new Thread(b2);      
        b3 = new AvbokaRobot(rr, this.ds); 
        t5 = new Thread(b3);
    }

    //Gör det möjligt till att starta trådarna
    public void startThreads() {
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

    }

    //Gör det möjligt till att stoppa trådarna
    //GÅ IN OCH LÄS FÖR ATT SE OM DU KAN LÖSA DETTA MED TYP SLEEEP!
    public void stopThreads() {
        //t1.stop();
        //t2.stop(); 
    }

    //Gör det möjligt till nollställa 
    public void resetThreads() {

    }
}

