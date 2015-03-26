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
        int[] Order= new int[]{27, 28, 31};       
        
        for(int k=0; k< 15; k++) {           
            
            // Set up network
        for (int i = 0; i < ds.nodes; i++) {
        Vertex location = new Vertex("" + (i + 1), "Nod #" + (i + 1));
        nodes.add(location);
        }
        
        for (int i = 0; i < ds.arcs; i++) {
        Edge lane = new Edge("" + (i + 1), nodes.get(ds.arcStart[i] -1), nodes.get(ds.arcEnd[i] - 1), 1);
        edges.add(lane);
        
        Edge lane2 = new Edge("" + (i + 1), nodes.get(ds.arcEnd[i] -1), nodes.get(ds.arcStart[i] - 1), 1);
        edges.add(lane2); }
        
        Graph graph = new Graph(nodes, edges);
        
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        // Compute shortest path //här ska vi ändra om vi vill ändra vägarna!!!! :D 
        dijkstra.execute(nodes.get(Order[k] - 1));
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(Order[k+1] - 1));
        
            // Get shortest path
            for (Vertex path1 : path) {
                System.out.println(path1);
                ds.nodeColor[Integer.parseInt(path1.getId()) - 1] = 1;
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
