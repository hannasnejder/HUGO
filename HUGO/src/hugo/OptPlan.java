package hugo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static javafx.beans.binding.Bindings.length;
import java.util.*;
import java.lang.*;

public class OptPlan {

    private List<Vertex> nodes;
    private List<Edge> edges;
    private DataStore ds;
    public Boka b;
    int[] länkar_boka = new int[10000];
    int[] noder_boka = new int[10000]; 
    int[] resurser_boka = new int[10000];
    int c = 0;
    int z = 0; 
    String boka;
    
    public OptPlan(){
        
    }

    public OptPlan(DataStore ds, OptPlan opt) {
        this.ds = ds;
    }

   public OptPlan(Boka b) {
        this.b = b;
    }

    public void createPlan() {

        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        
        int dist = 0;
               
        // Set up network
        for (int i = 0; i < ds.nodes; i++) {
            Vertex location = new Vertex("" + (i + 1), "" + (i + 1));
            nodes.add(location);
        }

        // Den sista parametern i "Edge" sätter längden på bågarna.
        for (int i = 0; i < ds.arcs; i++) {

            //Gör en fil med matris med första kolumnen startnod, andra kolumnen slutnod, sista kolumnen längden på tillhörande båge 
            //For-loop rader
            //for-loop kolumner
            //if start noden kopplas till rätt slutnod, ta längden och sätt in i Edge
            //Else, fortsätt loopa
            for (int m = 0; m < 98; m++) {
                if ((ds.startpunkt[m] == ds.arcStart[i]) && ds.slutpunkt[m] == ds.arcEnd[i]) {
                    //System.out.println("inne i if-satsen"); 
                    dist = ds.avstand[m];
                    break;
                }
            }

            Edge lane = new Edge("" + (i + 1), nodes.get(ds.arcStart[i] - 1), nodes.get(ds.arcEnd[i] - 1), dist);
            edges.add(lane);

            Edge lane2 = new Edge("" + (i + 1), nodes.get(ds.arcEnd[i] - 1), nodes.get(ds.arcStart[i] - 1), dist);
            edges.add(lane2);

            //System.out.println("Avstånd: " + dist);
        }

        //Här börjar den tänkta koden för att jämföra distanser
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dij = new DijkstraAlgorithm(graph);

        int nuvarande_langd = 0;
        int kortast_avstand;
        int narmaste_nod = ds.startnod;
        int[] test_vag = new int[1000];
        int[] kvarvarande_hyllor = new int[100];
        int[] snabbaste_rutten = new int[100];
        snabbaste_rutten[0] = ds.startnod;

        for (int n = 0; n < ds.antalnoderfil; n++) {
            kvarvarande_hyllor[n] = ds.vilkanoder[n];
        }

        for (int k = 0; k < ds.antalnoderfil; k++) {
            test_vag[0] = narmaste_nod;
            kortast_avstand = 100000;

            for (int n = 0; n < kvarvarande_hyllor.length; n++) {
                if (kvarvarande_hyllor[n] == narmaste_nod) {
                    kvarvarande_hyllor[n] = 0;
                }
            }

            for (int p = 0; p < ds.antalnoderfil +1; p++) {

                System.out.println("p är " + p);
                test_vag[1] = kvarvarande_hyllor[p];
                //System.out.println("narmaste_nod är " + narmaste_nod);
                //System.out.println("test_vag[1] är "+ test_vag[1]);
                if (test_vag[0] != test_vag[1] && (test_vag[1] != 0)) {
                    dij.execute(nodes.get(test_vag[0] - 1));
                    System.out.println("Där vi startar " + test_vag[0]);
                    LinkedList<Vertex> path = dij.getPath(nodes.get(test_vag[1] - 1));
                    System.out.println("Hit vi vill gå " + test_vag[1]);

                    //Loopar först igenom vägen(path) som fåtts från dijkstras för att se vilka noder som passeras
                    //loopar sedan igenom arrayerna med alla avstånd
                    nuvarande_langd = 0;
                    for (int b = 1; b < path.size(); b++) {
                        for (int m = 0; m < 98; m++) {
                           
                            //Kollar igenom avståndet mellan noderna som passeras för att komma till hyllan
                            if ((ds.startpunkt[m] == Integer.parseInt(path.get(b - 1).getId())) && (ds.slutpunkt[m] == Integer.parseInt(path.get(b).getId()))) {
                                nuvarande_langd = (nuvarande_langd + ds.avstand[m]);
                            }
                        }
                    }
                    System.out.println("Nuvarande längd är " + nuvarande_langd);

                    //Om avståndet från startplatsen till denna hylla är kortare 
                    //än avståndet från startplats till hyllplatsen som kollades innan
                    //så sparas det nuvarande avståndet som det kortaste avstånde och noden som gav avståndet
                    if (kortast_avstand > nuvarande_langd) {
                        kortast_avstand = nuvarande_langd;
                        narmaste_nod = ds.vilkanoder[p];
                        snabbaste_rutten[k + 1] = narmaste_nod;
                        //System.out.println("rutten är"+snabbaste_rutten[p+1]);
                        System.out.println("Det kortaste avståndet är " + kortast_avstand);
                        System.out.println("Den närmsta noden är " + narmaste_nod);
                    }
                }
            }

        }
        //Rita ut vägen för den snabbaste rutten
        snabbaste_rutten[ds.antalnoderfil + 1] = ds.startnod;

        for (int j = 0; j < ds.antalnoderfil + 2; j++) {
            System.out.println("Rutten är " + snabbaste_rutten[j]);
        }
        
        for(int k = 0; k < ds.antalnoderfil; k++){

            // Set up network
            for (int i = 0; i < ds.nodes; i++) {
                Vertex location = new Vertex("" + (i + 1), "Nod # " + (i + 1));
                nodes.add(location);
            }
            
            // Den sista parametern i "Edge" sätter längden på bågarna.
        for (int i = 0; i < ds.arcs; i++) {

            //Gör en fil med matris med första kolumnen startnod, andra kolumnen slutnod, sista kolumnen längden på tillhörande båge 
            //For-loop rader
            //for-loop kolumner
            //if start noden kopplas till rätt slutnod, ta längden och sätt in i Edge
            //Else, fortsätt loopa
            for (int m = 0; m < 98; m++) {
                if ((ds.startpunkt[m] == ds.arcStart[i]) && ds.slutpunkt[m] == ds.arcEnd[i]) {
                    //System.out.println("inne i if-satsen"); 
                    dist = ds.avstand[m];
                    break;
                }
            }

            Edge lane = new Edge("" + (i + 1), nodes.get(ds.arcStart[i] - 1), nodes.get(ds.arcEnd[i] - 1), dist);
            edges.add(lane);

            Edge lane2 = new Edge("" + (i + 1), nodes.get(ds.arcEnd[i] - 1), nodes.get(ds.arcStart[i] - 1), dist);
            edges.add(lane2);
        }
           
            Graph gra = new Graph(nodes, edges);

            DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(gra);

            // Compute shortest path //här ska vi ändra om vi vill ändra vägarna!!!! :D 
            dijkstra.execute(nodes.get(snabbaste_rutten[k] - 1));
            LinkedList<Vertex> path = dijkstra.getPath(nodes.get(snabbaste_rutten[k + 1] - 1));

            // Get shortest path
            for (int i = 0; i < path.size(); i++) {
                System.out.println("Noder som ska passeras: " + path.get(i));
                ds.nodeColor[Integer.parseInt(path.get(i).getId()) - 1] = 1;
                
                //Sparar de noder vi vill boka i en array
                noder_boka[z] = Integer.parseInt(path.get(i).getId());
                z = z+1; 

            }

            // Undirected arcs in the shortest path
            for (int i = 0; i < path.size()- 1; i++) {
                for (int j = 0; j < ds.arcs; j++) {
                    if (ds.arcStart[j] == Integer.parseInt(path.get(i).getId())
                            && ds.arcEnd[j] == Integer.parseInt(path.get(i + 1).getId())
                            || ds.arcEnd[j] == Integer.parseInt(path.get(i).getId())
                            && ds.arcStart[j] == Integer.parseInt(path.get(i + 1).getId())) {

                        //System.out.println("Arc: " + j);
                        ds.arcColor[j] = 1;

                       //Sparar de länkar vi vill boka i en array
                        länkar_boka[c] = j+38;

                        //System.out.println("Boka av c " +  länkar_boka[c]);

                        c = c + 1;

                        //boka = Arrays.toString(länkar_boka);
                       
                    }

                }
            }
        }
        
        int j = 1;
        int k = 0;
        //Skapa en ny for-loop för att kombinera länkar och noder till resurser_boka
        for (int i = 0; i < 100; i++) {
           resurser_boka[k] = noder_boka[i];
           
           resurser_boka[j] = länkar_boka[i];
           
           k = k+2;
           j = j+2;
           
        }  
       System.out.println("Resurser: " + Arrays.toString(resurser_boka));
  }

        //System.out.println("Resurser: " + Arrays.toString(resurser_boka));
   
}