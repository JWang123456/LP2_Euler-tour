
// change following line to your group number
package cs6301.g18;

import java.util.List;

import cs6301.g00.Graph;
import cs6301.g00.Graph.Edge;
import cs6301.g00.Graph.Vertex;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Euler {
    int VERBOSE;
    List<Graph.Edge> tour;
    List<List<Graph.Edge>> res;
    List<Graph.Edge> l1;
    HashMap<Vertex, List<Graph.Edge>> map = new HashMap<>(); 
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
	setVerbose(g.v.length);
	if(VERBOSE > 9) { printTours(); }
	stitchTours();
	return tour;
  }
    
    void DFSUtil(Graph.Vertex start) {
    	if(start.seen) {
    		return;
    	}
    	start.seen = true;
    	for(Edge e: start.adj) {
    		Vertex v = e.otherEnd(start);
    		DFSUtil(v);
    	}
    }
    
    boolean isStronglyConnected() {
    	for(Vertex u: g.v) {
    		u.seen = false;
    	}
    	int i = 0;
    	for(i = 0; i < g.v.length; i++) {
    		if(g.v[i].adj.size() != 0) {
    			break;
    		}
    	}
    	if(i == g.v.length) {
    		return true;
    	}
    	DFSUtil(g.v[i]);
    	for(i = 0; i < g.v.length; i++) {
    		if(!g.v[i].seen && g.v[i].adj.size() > 0) {
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
    	if(isStronglyConnected() == false) {
    		System.out.println("Reason: Graph is not strongly connected");
    		return false;
    	}
    	for(int i = 0; i < g.v.length; i++) {
    		if(g.v[i].adj.size() != g.v[i].revAdj.size()) {
    			System.out.println("Reason: inDegree = " + g.v[i].revAdj.size() + ", outDegree = " + g.v[i].adj.size() + " at Vertex " + i);
    			return false;
    		}
    	}
    	return true;
    }

    // Find tours starting at vertices with unexplored edges
    void findTours() {
    	res = new LinkedList<>();
    	l1 = new LinkedList<>();
    	findTours(u, l1);
    }
    
    void findTours(Vertex start, List<Graph.Edge> l1) {
    	start.count++;
    	if(start.count > 1) {
    		res.add(new LinkedList<>(l1));
    		l1.clear();
    		return;
    	}
    	for(Edge e: start.adj) {
    		if(!e.seen) {
    			e.seen = true;
    			l1.add(e);
    			Vertex end = e.otherEnd(start);
    			findTours(end, l1);
    		}
    	}
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
    	for(List<Graph.Edge> l: res) {
    		System.out.print(l.get(0).from + ": ");
    		for(Graph.Edge e: l) {
    			System.out.print(e.toString());
    		}
    		System.out.print("\n");
    	}
    }

    // Stitch tours into a single tour using the algorithm discussed in class
    void stitchTours() {
    	for(List<Graph.Edge> l: res) {
    		map.put(l.get(0).from, l);
    	}
    	explore(res.get(0).get(0).from);
    }
    
    void explore(Vertex u) {
    	u.explored = true;
    	Vertex tmp = u;
    	for(Edge e: map.get(u)) {
    		tour.add(e);
    		tmp = e.otherEnd(tmp);
    		if(!tmp.explored) {
    			explore(tmp);
    		}
    	}	
    }

    void setVerbose(int v) {
	VERBOSE = v;
    }
}
