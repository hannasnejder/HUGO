package hugo;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static javafx.beans.binding.Bindings.length;
import java.util.*;
import java.lang.*;

public class OptPlan {

    public List<Vertex> nodes;
    private List<Edge> edges;
    private DataStore ds;
    private ControlUI cui;
    public Boka b;
    int[] länkar_boka = new int[1000];
    int[] noder_boka = new int[1000];
    int dummafel;
    int order_kvar;
    int c;
    int z;
    String boka;

    public OptPlan(DataStore ds, OptPlan opt) {
        this.ds = ds;

    }

    public void createPlan() {

        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();

        int dist = 0;
        int c = 0;
        int z = 0;

        // Sätter upp nätverket
        for (int i = 0; i < ds.nodes; i++) {
            Vertex location = new Vertex("" + (i + 1), "" + (i + 1));
            nodes.add(location);
        }

        //Sätter färgen på alla bågar till blå
        //Längre ner får den utvalda vägen röd färg
        for (int j = 0; j < ds.arcs; j++) {
            ds.arcColor[j] = 0;
        }
        //Sätter färgen på alla noder till blå
        //Längre ner får den utvalda vägen röd färg
        for (int j = 0; j < ds.nodes; j++) {
            ds.nodeColor[j] = 0;
        }

        // Den sista parametern i "Edge" sätter längden på bågarna.
        for (int i = 0; i < ds.arcs; i++) {

            //Gör en fil med matris med första kolumnen startnod, andra kolumnen slutnod, 
            //sista kolumnen längden på tillhörande båge 
            //if start noden kopplas till rätt slutnod, ta längden och sätt in i Edge
            //Else, fortsätt loopa

            for (int m = 0; m < (ds.arcs*2); m++) {

                if ((ds.startpunkt[m] == ds.arcStart[i]) && ds.slutpunkt[m] == ds.arcEnd[i]) {
                    //System.out.println("inne i if-satsen"); 
                    dist = ds.kopiaAvstand[m];
                    break;
                }
            }

            Edge lane = new Edge("" + (i + 1), nodes.get(ds.arcStart[i] - 1), nodes.get(ds.arcEnd[i] - 1), dist);
            edges.add(lane);

            Edge lane2 = new Edge("" + (i + 1), nodes.get(ds.arcEnd[i] - 1), nodes.get(ds.arcStart[i] - 1), dist);
            edges.add(lane2);

        }

        //Här börjar den tänkta koden för att jämföra distanser
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dij = new DijkstraAlgorithm(graph);

        int nuvarande_langd;
        int kortast_avstand;
        int narmaste_nod = ds.startnod;
        int[] test_vag = new int[1000];
        int[] kvarvarande_hyllor = new int[100];
        int[] snabbaste_rutten = new int[100];
        snabbaste_rutten[0] = ds.startnod
                ;

        //Se över detta!! Verkar inte funka som jag vill....
        for (int n = 0, k= 0; n < ds.antalnoderfil; n++) {
            //System.out.println("kopiaVilkanoder är "+ds.kopiaVilkanoder[n]);
            if(ds.kopiaVilkanoder[n]!=0){
            kvarvarande_hyllor[k] = ds.kopiaVilkanoder[n];
            System.out.println("kvarvarande_hyllor är först "+kvarvarande_hyllor[k]+" och k är "+k);
            k++;
            }else{

                //System.out.println("kvarvarande_hyllor är sen "+ kvarvarande_hyllor[n]+ " och n är "+n);
            }
        }

        dummafel = 0;
        for (int q = 0; q < ds.antalnoderfil; q++) {
            if (kvarvarande_hyllor[q] != 0) {
                dummafel = dummafel + 1;
            }
        }
        //System.out.println("dummafel är "+dummafel);

        for (int k = 0; k < dummafel; k++) { 
            test_vag[0] = narmaste_nod;
            kortast_avstand = 100000;

            //används denna loop endast för att rita ut kartan? Kan jag ändra på det ?
            for (int n = 0; n < kvarvarande_hyllor.length; n++) {
                if (kvarvarande_hyllor[n] == narmaste_nod) {
                    kvarvarande_hyllor[n] = 0;
        System.out.println("dummafel är " + dummafel);

        if (dummafel != 0) {


                for (int p = 0; p < dummafel; p++) {
               // System.out.println("p är först"+p);
                    // System.out.println("kvarvarande_hyllor är detta i början av nästa for "+kvarvarande_hyllor[p]);
                    test_vag[1] = kvarvarande_hyllor[p];

                    if ((test_vag[0] != test_vag[1]) && (test_vag[1] != 0)) {
                        dij.execute(nodes.get(test_vag[0] - 1));
                        //System.out.println("Där vi startar " + test_vag[0]);

                        LinkedList<Vertex> path = dij.getPath(nodes.get(test_vag[1] - 1));
                    //System.out.println("Hit vi vill gå " + test_vag[1]);

                    //Loopar först igenom vägen(path) som fåtts från dijkstras för att se vilka noder som passeras

                    //loopar sedan igenom arrayerna med alla avstånd
                    nuvarande_langd = 0;
                    for (int b = 1; b < path.size(); b++) {
                        for (int m = 0; m < (ds.arcs*2); m++) {

                            //Kollar igenom avståndet mellan noderna som passeras för att komma till hyllan
                            if ((ds.startpunkt[m] == Integer.parseInt(path.get(b - 1).getId())) && (ds.slutpunkt[m] == Integer.parseInt(path.get(b).getId()))) {
                                nuvarande_langd = (nuvarande_langd + ds.avstand[m]);
                                }
                            }
                        }
                    //System.out.println("Nuvarande längd är " + nuvarande_langd);

                    //Om avståndet från startplatsen till denna hylla är kortare 
                        //än avståndet från startplats till hyllplatsen som kollades innan
                        //så sparas det nuvarande avståndet som det kortaste avstånde och noden som gav avståndet
                        // System.out.println("korstast avstånd är "+kortast_avstand);
                        // System.out.println("Nuvarande längd är "+nuvarande_langd);
                        //System.out.println("k är sist"+k);
                        //System.out.println("p är "+p);
                        if (kortast_avstand > nuvarande_langd) {
                            kortast_avstand = nuvarande_langd;
                            narmaste_nod = kvarvarande_hyllor[p];
                            snabbaste_rutten[k + 1] = narmaste_nod;
                      //  System.out.println("p är sist"+p);
                            //System.out.println("narmaste nod är i slutet av optplan "+narmaste_nod);
                            //System.out.println("i slutet är kopiaVilkanoder "+kvarvarande_hyllor[p]);

                        }
                    }
                }

           
        } else if (dummafel == 0) {
            snabbaste_rutten[0] = ds.startnod;
            //snabbaste_rutten[1]=ds.slutnod;
            System.out.println("fixat sista biten");
        }
        order_kvar = 0;
        for (int p = 0; p < snabbaste_rutten.length; p++) {
            // System.out.println("snabbaste rutten är "+snabbaste_rutten[p]);
            if (snabbaste_rutten[p] != 0) {
                order_kvar = order_kvar + 1;
                System.out.println("order kvar = " + order_kvar);
            }
        }

        //Rita ut vägen för den snabbaste rutten
        snabbaste_rutten[order_kvar] = ds.slutnod;


        for (int j = 0; j < order_kvar + 1; j++) {
            System.out.println("Rutten är " + snabbaste_rutten[j]);
        }

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

                for (int m = 0; m < ds.arcs*2; m++) {
                    
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
            for (int i = 1; i < path.size(); i++) {
                //System.out.println("Noder som ska passeras: " + path.get(i));
                ds.nodeColor[Integer.parseInt(path.get(i).getId()) - 1] = 1;
                
                 for (int f = 0; f < ds.antalnoderfil; f++) {      //Kopia för att sätta färgen grön
                            
                            ds.nodeColor[ds.vilkanoder[f]-1] = 2;
                        }
                ds.nodeColor[ds.slutnod-1]=3;

                //Sparar de noder vi vill boka i en array
                noder_boka[z] = Integer.parseInt(path.get(i).getId());
                z = z + 1;
            }

            // Undirected arcs in the shortest path
            for (int i = 0; i < path.size() - 1; i++) {

                for (int j = 0; j < ds.arcs; j++) {
                    if (ds.arcStart[j] == Integer.parseInt(path.get(i).getId())
                            && ds.arcEnd[j] == Integer.parseInt(path.get(i + 1).getId())
                            || ds.arcEnd[j] == Integer.parseInt(path.get(i).getId())
                            && ds.arcStart[j] == Integer.parseInt(path.get(i + 1).getId())) {

                        //System.out.println("Arc: " + j);
                        ds.arcColor[j] = 1;
                       
                       //Sparar de länkar vi vill boka i en array
                        länkar_boka[c] = j+ds.nodes+1;
                                           
                        c = c + 1;                      

                        //boka = Arrays.toString(länkar_boka);
                        //Sparar de länkar vi vill boka i en array
                        länkar_boka[c] = j + (ds.nodes + 1);
                        //System.out.println("Boka av c " +  länkar_boka[c]);

                        c = c + 1;

                    }
                }
            }
        }
    }
        }

        int j = 0;
        int k = 1;
        //Skapa en ny for-loop för att kombinera länkar och noder till resurser_boka
        for (int i = 0; i < 100; i++) {
            ds.resurser_boka[j] = länkar_boka[i];

            ds.resurser_boka[k] = noder_boka[i];

            k = k + 2;
            j = j + 2;

        }
        System.out.println("Vi vill boka: " + Arrays.toString(ds.resurser_boka));

    }
}
        
