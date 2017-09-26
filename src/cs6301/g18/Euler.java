
// change following line to your group number
package cs6301.g18;

import java.util.List;

import cs6301.g00.Graph;
import cs6301.g00.Graph.Edge;
import cs6301.g00.Graph.Vertex;

import java.util.Iterator;
import java.util.LinkedList;

public class Euler {
    int VERBOSE;
    List<Graph.Edge> tour;
    Graph.Vertex u;
    Graph g;
    // Constructor
    Euler(Graph g, Graph.Vertex start) {
	VERBOSE = 1;
	tour = new LinkedList<>();
	this.u = start;
	this.g = g;
    }

    // To do: function to find an Euler tour
    public List<Graph.Edge> findEulerTour() {
	findTours();
	if(VERBOSE > 9) { printTours(); }
	stitchTours();
	return tour;
    }
    
    void DFSUtil(int v, boolean visited[]) {
    	visited[v] = false;
    	Iterator<Edge> i = g.v[v].adj.iterator();
    	while(i.hasNext()) {
    		Edge e = i.next();
    		Vertex ov = e.otherEnd(g.v[v]);
    		int k = 0;
    		for(k = 0; k < g.v.length; k++) {
    			if(g.v[k].equals(ov)) {
    				break;
    			}
    		}
    		if(!visited[k]) {
    			DFSUtil(k, visited);
    		}
    	}
    }
    
    boolean isConnected() {
    	int V = g.v.length;
    	boolean visited[] = new boolean[V];
    	int i;
    	for(i = 0; i < V; i++) {
    		visited[i] = false;
    	}
    	for(i = 0; i < V; i++) {
    		if(g.v[i].adj.size() != 0) {
    			break;
    		}
    	}
    	if(i == V) {
    		return true;
    	}
    	DFSUtil(i, visited);
    	for(i = 0; i < V; i++) {
    		if(visited[i] == false && g.v[i].adj.size() > 0) {
    			return false;
    		}
    	}
    	return true;
    }

    /* To do: test if the graph is Eulerian.
     * If the graph is not Eulerian, it prints the message:
     * "Graph is not Eulerian" and one reason why, such as
     * "inDegree = 5, outDegree = 3 at Vertex 37" or
     * "Graph is not strongly connected"
     */
    boolean isEulerian() {
	System.out.println("Graph is not Eulerian");
	System.out.println("Reason: Graph is not strongly connected");
	return false;
    }

    // Find tours starting at vertices with unexplored edges
    void findTours() {
    }

    /* Print tours found by findTours() using following format:
     * Start vertex of tour: list of edges with no separators
     * Example: lp2-in1.txt, with start vertex 3, following tours may be found.
     * 3: (3,1)(1,2)(2,3)(3,4)(4,5)(5,6)(6,3)
     * 4: (4,7)(7,8)(8,4)
     * 5: (5,7)(7,9)(9,5)
     *
     * Just use System.out.print(u) and System.out.print(e)
     */
    void printTours() {
    }

    // Stitch tours into a single tour using the algorithm discussed in class
    void stitchTours() {
    }

    void setVerbose(int v) {
	VERBOSE = v;
    }
}
