package hugo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static javafx.beans.binding.Bindings.length;



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
        int dist=0;
        
        for(int k=0; k< 15; k++) {           
            
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
        
        //System.out.println("Avstånd: " + dist);
        }
        
        
        Graph graph = new Graph(nodes, edges);
        
        //26/3 kl 11:36 Går inte in här
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        // Compute shortest path //här ska vi ändra om vi vill ändra vägarna!!!! :D 
        dijkstra.execute(nodes.get(ds.vilkanoder[k] -1));
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(ds.vilkanoder[k+1] -1));
        
        // Get shortest path
        for (int i = 0; i < path.size(); i++){
        System.out.println(path.get(i));
        ds.nodeColor[Integer.parseInt(path.get(i).getId())-1] = 1; 
        
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
