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
import java.util.*;
import java.lang.*;
//import static javafx.beans.binding.Bindings.length;



public class OptPlan {
    
    private List<Vertex> nodes;
    private List<Edge> edges;
    private DataStore ds;  
    public Boka b;
    int [] länkar_boka = new int[10000];
    int [] noder_boka = new int[10000];
    int [] resurser_boka = new int[1000];
    int c = 0;
    int z = 0;
    String boka;

    public OptPlan(){
    //länkar_boka = new int[100];
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
       
        int dist=0;
        //int länkar_boka
        //int c = 0;
        //Skapar en string för att kunna skriva ut innehållet i vilka noder
        String vilkanoder_skrivaut;
        
        vilkanoder_skrivaut = Arrays.toString(ds.vilkanoder);
        
        //System.out.println(vilkanoder_skrivaut);
        
        for(int k=0; k < (ds.antalnoderfil + 1)  ; k++) {           

         // Set up network
        for (int i = 0; i < ds.nodes; i++) {
        Vertex location = new Vertex("" + (i + 1), "Nod #" + (i + 1));
        nodes.add(location);
 
       
        }
        
        // Den sista parametern i "Edge" sätter längden på bågarna.
       
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
        
        //Sparar de noder vi vill boka i en array
        noder_boka[z] = Integer.parseInt(path.get(i).getId());
        z = z+1;
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
                                  
                        //Sparar de länkar vi vill boka i en array
                        länkar_boka[c] = j + 39;
                        //System.out.println("Boka av c " +  länkar_boka[c]);

                        c = c+1;

                        //boka = Arrays.toString(länkar_boka);
                }
            }
         }

       }
       
        int j = 1;
        int k = 0;
       //Skapa en ny for-loop för att kombinera länkar och noder till resurser_boka
       for(int i = 0; i < 100; i++){
           resurser_boka[k] = noder_boka[i];
           
           resurser_boka[j] = länkar_boka[i];
           k = k+2;
           j = j+2;
           
        }  
       System.out.println("Resurser: " + Arrays.toString(resurser_boka));
  }
}