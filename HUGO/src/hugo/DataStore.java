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
    int slutnod;
    int antalnoderfil;
    int forstanoden;
    int andranoden;
    int tredjenoden;
    int[] startpunkt;
    int[] slutpunkt;
    int[] avstand;
    int[] kopiaAvstand;
    int[] vilkanoder;
    int[] kopiaVilkanoder;
    boolean networkRead1;

    boolean robotflaga;
    boolean bokaflag;

    double startnodX=0;        // För att spara startplatsens x-koordinat
    double startnodY=0;        //För att spara startnodens y-koordinat

    int raknare = 0;
    int [] okej;
    int [] ejokej;
    int [] vill_avboka;
    int [] resurser_boka;


    public DataStore() {
        // Initialize the datastore with fixed size arrays for storing the network data
        nodes = 0;
        arcs = 0;
        nodeX = new double[1000];
        nodeY = new double[1000];
        arcStart = new int[1000];
        arcEnd = new int[1000];
        arcColor = new int[1000];
        nodeColor = new int[1000];
        startpunkt = new int[1000];
        slutpunkt = new int[1000];
        avstand = new int[1000];
        kopiaAvstand = new int[1000];
        vilkanoder = new int[1000];
        okej = new int[2];
        ejokej = new int[2];
        vill_avboka = new int[2];
        resurser_boka = new int[1000];
        vilkanoder = new int[100];
        kopiaVilkanoder = new int[100];

        networkRead = false;
        updateUIflag = false;
        bokaflag = false;

    }

    public void setFileName(String newFileName) {
        this.fileName = newFileName;
    }

    public void setFileName1(String newFileName1) {
        this.fileName1 = newFileName1;
    }
    
    public String getFileName() {
        return fileName;
    }

    public String getFileName1() {
        return fileName1;
    }

    public void readNet() {
        String line;

        if (fileName == null) {
            System.err.println("No file name set. Data read aborted. ");
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

            // Read nodes as number, x, y
            for (int i = 0; i < nodes; i++) {
                line = scanner.nextLine();
                //split space separated data on line
                sline = line.split(" ");
                nodeX[i] = Double.parseDouble(sline[1].trim());
                nodeY[i] = Double.parseDouble(sline[2].trim());
            }

            // Read arc list as start node number, end node number
            for (int i = 0; i < arcs; i++) {
                line = scanner.nextLine();
                //split space separated data on line
                sline = line.split(" ");
                arcStart[i] = Integer.parseInt(sline[1].trim());
                arcEnd[i] = Integer.parseInt(sline[2].trim());
            }
            //Skapar vår "nya avståndsmatris"
            startpunkt = arcStart;
            slutpunkt = arcEnd;
            
            for(int j = arcs; j<(arcs*2); j++){
                startpunkt[j] = arcEnd[j-arcs];
                slutpunkt[j] = arcStart[j-arcs];
            }
            
            //System.out.print("avstånden är ");
            for (int j = 0; j<(arcs*2); j++){
                //Gör om till int!!!
                avstand[j]=(int) Math.abs((nodeX[startpunkt[j]-1] - nodeX[slutpunkt[j]-1])
                        -(nodeY[startpunkt[j]-1]-nodeY[slutpunkt[j]-1]));
               // System.out.print(avstand[j]+" ");
            }
            
            kopiaAvstand = avstand;
            networkRead = true;  // Indicate that all network data is in place in the DataStore

        } catch (Exception e) {

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
            slutnod = startnod;
            line1 = scanner1.nextLine();
            antalnoderfil = Integer.parseInt(line1.trim());
            
            //För att spara koordinaterna till startplatsen
            startnodX=nodeX[startnod-1]; //sparar x-koordinaten till startnoden
            startnodY=nodeY[startnod-1]; //sparar y-koordinaten till startnoden


            for (int i = 0; i < (antalnoderfil); i++) {
            line1 = (scanner1.nextLine());
            vilkanoder[i] = Integer.parseInt(line1.trim());

            besoknoder = besoknoder + " " + vilkanoder[i];
            

                //System.out.println("Besöksnoder: " + besoknoder);
            }
            kopiaVilkanoder = vilkanoder;
        
            // Indicate that all network data is in place in the DataStore
            networkRead1 = true; 

            System.out.println("Vi ska besöka noderna: " + besoknoder);

        } catch (Exception e) {

            e.printStackTrace();
        }
        robotX = startnodX;
        robotY = startnodY;
    }
}
