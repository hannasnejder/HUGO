package hugo;

/*
 *Den här klassen motsvarar warehouse klassen från labb 1 och 2 
 * /Testar en sak, en sak till
 */
public class HUGO {

    DataStore ds;
    ControlUI cui;   

   HUGO(){

       ds = new DataStore();

        ds.setFileName("../../HUGO/warehouse.txt");
        ds.readNet();
         
        ds.setFileName1("../../HUGO/orderfil.txt");
        ds.readNet1();

        ds.setFileName2("../../HUGO/avstandsmatris.txt");
        ds.readNet2();
      

        cui = new ControlUI(ds);

        cui.setVisible(true);
        cui.showStatus();
        
        
        OptPlan op = new OptPlan(ds);
        op.createPlan();
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
