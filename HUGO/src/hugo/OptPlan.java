/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hugo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
//import static javafx.beans.binding.Bindings.length;



public class OptPlan {
    
    private List<Vertex> nodes;
    private List<Edge> edges;
    private DataStore ds;  
    public Boka b;
    //int boka = 0;
    int [] resurser_boka = new int[10000];
    //Vector<int> mVector;
    int c = 0;
    String boka;

    public OptPlan(){
    //resurser_boka = new int[100];
    }
    
    public OptPlan(DataStore ds, OptPlan opt) {
    this.ds = ds;
    ///mVector = new Vector<int>();
    }
    
    public OptPlan(Boka b) {
    this.b = b;
  
    }
    
    
    public  void createPlan(){
                
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        //int [] resurser_boka = new int[100];
        //int[] Order= new int[]{30, 34};        
        int dist=0;
        //int resurser_boka
        //int c = 0;
        //Skapar en string för att kunna skriva ut innehållet i vilka noder
        String vilkanoder_skrivaut;
        
<<<<<<< HEAD
        //kom ihåg att ändra 3an sen
        for(int k=0; k< (ds.antalnoderfil +3); k++) {           
     
        // Set up network
=======
        vilkanoder_skrivaut = Arrays.toString(ds.vilkanoder);
        
        //System.out.println(vilkanoder_skrivaut);
        
        //this.resurser_boka = resurser_boka;
        
        for(int k=0; k< (ds.antalnoderfil) ; k++) {           
            
            //int h = 0;
            // Set up network
>>>>>>> a5d775e2364e4ba373fc0bbf67564eaddfe332bf
        for (int i = 0; i < ds.nodes; i++) {
        Vertex location = new Vertex("" + (i + 1), "Nod #" + (i + 1));
        nodes.add(location);
        
        //b.resurser_boka[h] = i;
        //h++;
        
        }
   
        for (int i = 0; i < ds.arcs; i++) {
        
            //Gör en fil med matris med första kolumnen startnod, andra kolumnen slutnod, sista kolumnen längden på tillhörande båge 
            //For-loop rader
            //for-loop kolumner
            //if start noden kopplas till rätt slutnod, ta längden och sätt in i Edge
            //Else, fortsätt loopa
            
           for (int m=0; m<98; m++){
                if ((ds.startpunkt[m] == ds.arcStart[i]) && ds.slutpunkt[m]==ds.arcEnd[i]){
                    //System.out.println("inne i if-satsen"); 
                    dist = ds.avstand[m];
                     break;
                }
            }    
             
        Edge lane = new Edge("" + (i + 1), nodes.get(ds.arcStart[i] -1), nodes.get(ds.arcEnd[i] - 1), dist);
        edges.add(lane);
        
        Edge lane2 = new Edge("" + (i + 1), nodes.get(ds.arcEnd[i] -1), nodes.get(ds.arcStart[i] - 1), dist);
        edges.add(lane2);
        }
        
        Graph graph = new Graph(nodes, edges);
        
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        // Compute shortest path //här ska vi ändra om vi vill ändra vägarna!!!! :D 
        dijkstra.execute(nodes.get(ds.vilkanoder[k] -1));
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(ds.vilkanoder[k+1] -1));
        
        // Get shortest path
        for (int i = 0; i < path.size(); i++){
        System.out.println(path.get(i));
        ds.nodeColor[Integer.parseInt(path.get(i).getId())-1] = 1; 
        System.out.println(path);
        
        }
        

        //Räknare för att veta vilken resurs som ska bokas
        //int c = 0;
        // Undirected arcs in the shortest path
        for (int i = 0; i < path.size()-1; i++){
            for (int j = 0; j < ds.arcs; j++){
                 if(ds.arcStart[j] == Integer.parseInt(path.get(i).getId()) &&
                    ds.arcEnd[j] == Integer.parseInt(path.get(i+1).getId()) ||
                    ds.arcEnd[j] == Integer.parseInt(path.get(i).getId()) && 
                    ds.arcStart[j] == Integer.parseInt(path.get(i+1).getId())){
                        
                        System.out.println("Arc: "+j);
                        ds.arcColor[j]=1; 
<<<<<<< HEAD
                    }
                }
            }
        }
    }
=======
                        
                        //Mickes boolean ide?????????????????
                        //boka = b.resurser_boka[j];                        
                        
                        resurser_boka[c] = j;
                        //System.out.println("Boka av c " +  resurser_boka[c]);

                        c = c+1;

                        //boka = Arrays.toString(resurser_boka);
                        
                        //System.out.println("Boka  " +  );
                }
               //System.out.println("TJOHO ");

            }
        }
        //System.out.println("Kollar om arrayen funkar " + c);
        //System.out.println("Test: " + boka);
        }
        for(int i = 0; i < 100; i++){
            //System.out.println("Boka av c " +  resurser_boka[c]);

        }
        //boka = Arrays.toString(resurser_boka);
        //System.out.println("Test: " + boka);

    }      
>>>>>>> a5d775e2364e4ba373fc0bbf67564eaddfe332bf
}
