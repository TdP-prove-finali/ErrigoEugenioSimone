package it.polito.tesi.model;

import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public abstract class TSPAlgorithms {
	
	public Graph<Hotspot, DefaultWeightedEdge> graph;
	public Set<Hotspot> compconn;
	
	public TSPAlgorithms(Graph<Hotspot, DefaultWeightedEdge> graph, Set<Hotspot> compconn) {
		this.graph = graph;
		this.compconn = compconn;
	}

	public abstract void solve();
	public abstract List<Hotspot> getSolution();
	public abstract double getBestWeight();

}
