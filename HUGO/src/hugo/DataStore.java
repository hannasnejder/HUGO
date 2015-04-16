package hugo;

import java.io.File;
import java.util.Scanner;
import java.util.Arrays;

public class DataStore {

    String fileName = null;
    String fileName1 = null;
    String fileName2 = null;
    String besoknoder = null;
    int nodes;
    int arcs;
    double[] nodeX;
    double[] nodeY;
    int[] arcStart;
    int[] arcEnd;
    boolean networkRead;
    boolean updateUIflag;
    double robotX;
    double robotY;
    int[] arcColor;
    int[] nodeColor;
    int startnod;
    int antalnoderfil;
    int forstanoden;
    int andranoden;
    int tredjenoden;
    int[] startpunkt;
    int[] slutpunkt;
    int[] avstand;
    int[] vilkanoder;
    boolean networkRead1;

    public DataStore() {
        // Initialize the datastore with fixed size arrays for storing the network data
        nodes = 0;
        arcs = 0;
        //arcs = 38;
        nodeX = new double[1000];
        nodeY = new double[1000];
        arcStart = new int[1000];
        arcEnd = new int[1000];
        arcColor = new int[1000];
        nodeColor = new int[1000];
        startpunkt = new int[1000];
        slutpunkt = new int[1000];
        avstand = new int[1000];
        vilkanoder = new int[1000];

        networkRead = false;
        updateUIflag = false;

    }

    public void setFileName(String newFileName) {
        this.fileName = newFileName;
    }

    public void setFileName1(String newFileName1) {
        this.fileName1 = newFileName1;
    }

    public void setFileName2(String newFileName2) {
        this.fileName2 = newFileName2;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileName1() {
        return fileName1;
    }

    public String getFileName2() {
        return fileName2;
    }

    public void readNet() {
        String line;

        if (fileName == null) {
            System.err.println("No file name set. Data read aborted.");
            return;
        }
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file, "iso-8859-1");
            String[] sline;

            // Read number of nodes
            line = (scanner.nextLine());
            nodes = Integer.parseInt(line.trim());
            line = scanner.nextLine();
            arcs = Integer.parseInt(line.trim());

            // Debug printout: network size data
            // System.out.println("Nodes: "+nodes);
            // System.out.println("Arcs: "+arcs);
            // Read nodes as number, x, y
            for (int i = 0; i < nodes; i++) {
                line = scanner.nextLine();
                //split space separated data on line
                sline = line.split(" ");
                nodeX[i] = Double.parseDouble(sline[1].trim());
                nodeY[i] = Double.parseDouble(sline[2].trim());
            }

            // Debug printout: print data for node 1
            // System.out.println("Node 1: "+nodeX[0]+" "+nodeY[0]);
            // Read arc list as start node number, end node number
            for (int i = 0; i < arcs; i++) {
                line = scanner.nextLine();
                //split space separated data on line
                sline = line.split(" ");
                arcStart[i] = Integer.parseInt(sline[1].trim());
                arcEnd[i] = Integer.parseInt(sline[2].trim());
            }

            networkRead = true;  // Indicate that all network data is in place in the DataStore

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        robotX = nodeX[0];
        robotY = nodeY[0];
    }

    public void readNet1() {

        String line1;
        besoknoder = " ";

        if (fileName1 == null) {
            System.err.println("No file name set. Data read aborted.");
            return;
        }
        try {
            File file1 = new File(fileName1);
            Scanner scanner1 = new Scanner(file1, "iso-8859-1");

            // Read number of nodes
            line1 = (scanner1.nextLine());
            startnod = Integer.parseInt(line1.trim());
            line1 = scanner1.nextLine();
            antalnoderfil = Integer.parseInt(line1.trim());

 
            for (int i = 1; i < (antalnoderfil +1); i++) {
                line1 = (scanner1.nextLine());

                vilkanoder[i] = Integer.parseInt(line1.trim());

               // Arrays.sort(vilkanoder);
                besoknoder = besoknoder + " " + vilkanoder[i];

                // System.out.println("Besöksnoder: " + besoknoder);
            }

            //System.out.println(Arrays.toString(vilkanoder));

            //Gör så att den åker tillbaka

           // vilkanoder[(antalnoderfil+1)]=startnod;

           // vilkanoder[(antalnoderfil + 1)] = startnod;

            networkRead1 = true;  // Indicate that all network data is in place in the DataStore
            System.out.println("Vi ska besöka noderna: " + besoknoder);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        robotX = nodeX[8];
        robotY = nodeY[8];
    }

    public void readNet2() {
        String line2;

        if (fileName2 == null) {
            System.err.println("No file name set. Data read aborted.");
            return;
        }
        try {
            File file2 = new File(fileName2);
            Scanner scanner2 = new Scanner(file2, "iso-8859-1");
            String[] sline2;

            // Read number of nodes
            //Läsa av filen rad för rad
            //Läsa in varje tal i raden, ett i taget
            // första talet = start, andra talet = slut, 3e talet = längd
       for (int k = 0; k<98; k++){

                line2 = (scanner2.nextLine());
                sline2 = line2.split(" ");
                startpunkt[k] = Integer.parseInt(sline2[0].trim());
                slutpunkt[k] = Integer.parseInt(sline2[1].trim());
                avstand[k] = Integer.parseInt(sline2[2].trim());
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
