package hugo;

public class threads {

    //Hämtar från klasser
    DataStore ds;
    ControlUI cui;
    OptPlan opt;
    MapPanel map;   
    drive dr;
    Boka boka;
    RobotRead rr;
    OptOnline online;
    Avboka avboka;
    translate tr;
    //GuiUpdate gui;

    //Trådar
    RobotRead r1;
    Thread t1;   
    
    Boka b1; 
    Thread t3; 

    //Avboka b2;
    //Thread t4;    
    
    //AvbokaRobot b3;
    //Thread t5;
    
    threads(DataStore ds, ControlUI cui){
        this.ds = ds;
        this.cui = cui;
        
        //Robotread(r1  och boka(b1 och t3) är trådar
        opt = new OptPlan(this.ds, this.opt);
        boka= new Boka(this.opt, this.ds, this.online, this.dr, this.avboka, this.tr); 
        online = new OptOnline(this.opt, this.ds);
        avboka = new Avboka(this.opt, this.ds);
        tr = new translate(this.rr, this.avboka, this.ds);
        dr = new drive(this.ds, this.cui);
        r1 = new RobotRead (this.ds, this.cui, this.tr, this);
        t1 = new Thread(r1);
        b1 = new Boka(this.opt, this.ds, this.online, this.dr, this.avboka, this.tr);
        t3 = new Thread(b1);
        //b2 = new Avboka(this.opt, this.ds);
        //t4 = new Thread(b2);      
        //b3 = new AvbokaRobot(rr, this.ds); 
        //t5 = new Thread(b3);
    }

    //Gör det möjligt till att starta trådarna
    public void startThreads() {
        t1.start();
        t3.start();
        //t4.start();
        //t5.start();

    }

    //Gör det möjligt till att stoppa trådarna
    //GÅ IN OCH LÄS FÖR ATT SE OM DU KAN LÖSA DETTA MED TYP SLEEEP!
    public void stopThreads() {
        //t1.stop();
        //t2.stop(); 
        //t3.wait();
    }

    //Gör det möjligt till nollställa 
    public void resetThreads() {

    }
}

