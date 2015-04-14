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
        //opt = new OptPlan();
        
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
        

    }

    
    public static void main(String[] args) {

        //PARTY PARTY 

        HUGO x = new HUGO();
    }
}
