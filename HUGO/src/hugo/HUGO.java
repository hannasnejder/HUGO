package hugo;

public class HUGO {

    DataStore ds;
    ControlUI cui;
    OptPlan opt;
    //MapPanel map;
    //GuiUpdate gui;
    //OptOnline online;
    Boka boka;

    HUGO() {

        ds = new DataStore();

        ds.setFileName("../../HUGO/warehouse.txt");
        ds.readNet();

        ds.setFileName1("../../HUGO/orderfil.txt");
        ds.readNet1();

        ds.setFileName2("../../HUGO/avstandsmatris.txt");
        ds.readNet2();

        cui = new ControlUI(ds);//, boka);

        cui.setVisible(true);
        cui.showStatus();

        //opt = new OptPlan(ds, opt);
        //boka = new Boka(opt);

    }

    public static void main(String[] args) {

        //PARTY PARTY 
        HUGO x = new HUGO();
    }
}
