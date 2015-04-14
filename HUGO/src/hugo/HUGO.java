package hugo;

/*
 *Den här klassen motsvarar warehouse klassen från labb 1 och 2 
 * /Testar en sak, en sak till
 */
public class HUGO {

    DataStore ds;
    ControlUI cui;
    OptPlan opt;
    //Boka b;
    
   

    HUGO(){

        ds = new DataStore();
        opt = new OptPlan();
        
        //b = new Boka();
        //slipper byta sökväg - förhoppningsvis 
       // ds.setFileName("/Users/HannaSnejder/Desktop/hugo/HUGO/warehouse.txt");
        //ds.setFileName("../../HUGO/warehouse.txt");


        ds.setFileName("../../HUGO/warehouse.txt");
        ds.readNet();
         
        ds.setFileName1("../../HUGO/orderfil.txt");
        ds.readNet1();

        ds.setFileName2("../../HUGO/avstandsmatris.txt");
        ds.readNet2();
      

        cui = new ControlUI(ds);

        cui.setVisible(true);
        cui.showStatus();
        

        /*RobotRead r1 = new RobotRead (ds, cui);
        Thread t1 = new Thread(r1);
        GuiUpdate g1 = new GuiUpdate (ds,cui);
        Thread t2 = new Thread(g1); 
        
        //Testar att skapa en tråd för att boka och avboka länkar
        Boka b1 = new Boka();
        Thread t3 = new Thread(b1);
        
        
        Avboka b2 = new Avboka();
        Thread t4 = new Thread(b2);

        t1.start();
        t2.start();
        t3.start();
        //t4.start();*/

       // OptPlan op = new OptPlan(ds);
       // op.createPlan();

    }

    
    public static void main(String[] args) {
        // TODO code application logic here
        
        //PARTY PARTY 

        /* This is the "main" method what gets called when the application starts
         * All that is done here is to make an instance of the WarehouseControl class,
         * and thereby, call the WarehouseControl constructor.
        */
        HUGO x = new HUGO();
    }
}
