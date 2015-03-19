package hugo;

/**
 *Den här klassen motsvarar warehouse klassen från labb 1 och 2 
 * 
 */
public class HUGO {

    DataStore ds;
    ControlUI cui;

    HUGO(){

        ds = new DataStore();

        ds.setFileName("/home/itn/Dokument/warehouse.txt");
        ds.readNet();
        
        ds.setFileName1("/home/itn/Dokument/orderfil.txt");
        ds.readNet1();
        
        ds.setFileName1("/home/itn/Dokument/orderfil.txt");
        ds.readNet1();

        cui = new ControlUI(ds);
        cui.setVisible(true);
        cui.showStatus();
         
        RobotRead r1 = new RobotRead (ds, cui);
        Thread t1 = new Thread(r1);
        GuiUpdate g1 = new GuiUpdate (ds,cui);
        Thread t2 = new Thread(g1); 
        
        t1.start();
        t2.start();
        
        OptPlan op = new OptPlan(ds);
        op.createPlan();
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //PARTY PARTY 

        /* This is the "main" method what gets called when the application starts
         * All that is done here is to make an instance of the WarehouseControl class,
         * and thereby, call the WarehouseControl constructor.
        */
        HUGO x = new HUGO();
    }

 //Kommentar kommentar kommnetar kommentar   

}
