package it.polito.tesi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;

public class TSPApproximation extends TSPAlgorithm {
	
	List<Hotspot> path;

	public TSPApproximation(Graph<Hotspot, DefaultWeightedEdge> graph, Set<Hotspot> compconn) {
		super(graph, compconn);
	}

	@Override
	public void solve() {
		
		if(!graph.getType().isUndirected()) {
			throw new IllegalArgumentException("Graph must be undirected");
        }
        if (graph.vertexSet().isEmpty()) {
            throw new IllegalArgumentException("Graph contains no vertices");
		}
 
		GraphPath<Hotspot, DefaultWeightedEdge> graphpath = this.getPath();
		path = new ArrayList<>(graphpath.getVertexList());
		
	}
	
	private GraphPath<Hotspot, DefaultWeightedEdge> getPath(){
		
		/*
         * Special case singleton vertex
         */
        if (graph.vertexSet().size() == 1) {
            Hotspot start = graph.vertexSet().iterator().next();
            return new GraphWalk<>(
                graph, start, start, Collections.singletonList(start), Collections.emptyList(), 0d);
        }
        
        /*
         * Create MinimunSpanningTree
         */
        Graph<Hotspot, DefaultWeightedEdge> mst = new SimpleGraph<>(DefaultWeightedEdge.class);
        for (Hotspot v : graph.vertexSet()) {
            mst.addVertex(v);
        }
        for (DefaultWeightedEdge e : new KruskalMinimumSpanningTree<>(graph).getSpanningTree().getEdges()) {
            mst.addEdge(graph.getEdgeSource(e), graph.getEdgeTarget(e));
        }
        
        /*
         * Perform a depth-first-search traversal
         */
        int n = graph.vertexSet().size();
        Set<Hotspot> found = new HashSet<>(n);
        List<Hotspot> tour = new ArrayList<>(n + 1);
        Hotspot start = graph.vertexSet().iterator().next();
        DepthFirstIterator<Hotspot, DefaultWeightedEdge> dfsIt = new DepthFirstIterator<>(mst, start);
        while (dfsIt.hasNext()) {
            Hotspot v = dfsIt.next();
            if (found.add(v)) {
                tour.add(v);
            }
        }
        // repeat the start vertex
        tour.add(start);
        
        /*
         * Explicitly build the path.
         */
        List<DefaultWeightedEdge> tourEdges = new ArrayList<>(n);
        double tourWeight = 0d;
        Iterator<Hotspot> tourIt = tour.iterator();
        Hotspot u = tourIt.next();
        while (tourIt.hasNext()) {
            Hotspot v = tourIt.next();
            DefaultWeightedEdge e = graph.getEdge(u, v);
            if(graph.containsEdge(u, v)) {
	            tourEdges.add(e);
	            tourWeight += graph.getEdgeWeight(e);
	            u = v;
            }
        }
        return new GraphWalk<>(graph, start, start, tourEdges, tourWeight);   //ho rimosso tour=vertexList
        
    }

	@Override
	public List<Hotspot> getSolution() {
		return path;
	}


}
