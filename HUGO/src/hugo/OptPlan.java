/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hugo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
//import static javafx.beans.binding.Bindings.length;



public class OptPlan {
    
    private List<Vertex> nodes;
    private List<Edge> edges;
    private DataStore ds;  
    
    public OptPlan(DataStore ds) {
    this.ds = ds;
    }
    
    
    public void createPlan(){
                
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        //int[] Order= new int[]{30, 34};        
        int dist=0;
        System.out.println(ds.vilkanoder);
        
        //kom ihåg att ändra 3an sen
        for(int k=0; k< (ds.antalnoderfil +3); k++) {           
     
        // Set up network
        for (int i = 0; i < ds.nodes; i++) {
        Vertex location = new Vertex("" + (i + 1), "Nod #" + (i + 1));
        nodes.add(location);
        }
        
        /*
        int nuvarande_langd = 10000; 
        
        for (int j = 0; j < ds.antalnoderfil; j++ )
        { 
        array[0] = ds.startnod; 
        array[1] = ds.vilkanoder[j];
        } 
        Djikstrakoden ..... spara längden i en int "tot längd"... 
        
        if tot längd < nuvarande längd {
                nuvarande_langd = totlängd 
                annan_array[0]= startnod 
                annan_array[1] = nuvarande nod
                        }
        
        int nuvarande_langd = 100000; 
        for (int g; bla bla )
        { array[0] = annan_array[1]; 
        array[1] = ds.vilkanoder[g];
        } 
        if ds.vilkanoder[g]!= array[0]
                { kör djukstras.... spara längden i en int "tot längd"... 
                if tot längd < nuvarande längd 
                        nuvarande_langd = totlängd 
                                annan_array[0]= startnod 
                                        annan_array[1] = nuvarande nod 
                                                }
                */
//måste kolla någonstans om arryen är tom 
//dvs om ifsatsen inte uppfylls 
// då skall arrayen förhoppningsvis vara fylld med alla hyllplatser som skall besökas 
//lägg till startnoden igen på sista platsen i arrayen 
// använd den färdiga arrayen "annan array" och optimera över denna
        
                                                
                                                
                                                
                                                
                                                
        // Den sista parametern i "Edge" sätter längden på bågarna./ hyllorna borde ligga i den ordning som är snabbast att besöka } }
     
       
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
        
        // Undirected arcs in the shortest path
        for (int i = 0; i < path.size()-1; i++){
            for (int j = 0; j < ds.arcs; j++){
                 if(ds.arcStart[j] == Integer.parseInt(path.get(i).getId()) &&
                    ds.arcEnd[j] == Integer.parseInt(path.get(i+1).getId()) ||
                    ds.arcEnd[j] == Integer.parseInt(path.get(i).getId()) && 
                    ds.arcStart[j] == Integer.parseInt(path.get(i+1).getId())){
     
                        System.out.println("Arc: "+j);
                        ds.arcColor[j]=1; 
                    }
                }
            }
        }
    }
}
