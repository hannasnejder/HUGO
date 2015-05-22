package hugo;

public class HUGO {

    DataStore ds;
    ControlUI cui;


    HUGO() {

        ds = new DataStore();

        ds.setFileName("../../HUGO/warehouse.txt");
        ds.readNet();

        ds.setFileName1("../../HUGO/orderfil.txt");
        ds.readNet1();

        cui = new ControlUI(ds);
        cui.setVisible(true);
        cui.showStatus();

    }

    public static void main(String[] args) {

        //PARTY PARTY 
        HUGO x = new HUGO();
    }
}
