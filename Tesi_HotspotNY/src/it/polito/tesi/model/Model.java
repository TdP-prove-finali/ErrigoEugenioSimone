package it.polito.tesi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.tour.TwoApproxMetricTSP;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tesi.db.HotspotDAO;

public class Model {
	
	private HotspotDAO dao;
	private Graph<Hotspot, DefaultWeightedEdge> graph;
	private BoroughIdMap bmap;
	
	private int maxDist;
	
	public Model() {
		dao = new HotspotDAO();
		bmap = new BoroughIdMap();
		
	}
	
	public List<Provider> getAllProviders(){
		List<Provider> providers = dao.getAllProviders();
		
		if(providers.isEmpty())
			throw new RuntimeException("Provider list is empty. Check DAO class.");
	
		return providers;
		
	}

	public List<Borough> getAllBoroughs(){
		List<Borough> boros = dao.getAllBoroughs(bmap);
		
		if(boros.isEmpty())
			throw new RuntimeException("Borough list is empty. Check DAO class.");
		
		return boros;
		
	}
	
	/**
	 * Popola il grafo (vertici hotspot; archi tra distanze che rappresentano il peso) attraverso un metodo DAO
	 * @param provider
	 * @param borough
	 * @param maxDist
	 * @param failure
	 * @param allIsSelected
	 */
	public void createGraph(Provider provider, Borough borough, int maxDist, double failure, boolean allIsSelected) {
		
		graph = new SimpleWeightedGraph<Hotspot, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		//this.maxDist = maxDist;
	
		List<Hotspot> allVertex = new ArrayList<>();
		
		if(!allIsSelected)
			allVertex = dao.getVertex(provider, borough);
		else
			allVertex = dao.getAllVertex(provider, bmap);
		
		List<Hotspot> vertex = this.badVertex(allVertex, failure);
		Graphs.addAllVertices(graph, vertex);
		
		for(Hotspot h1 : vertex) {
			for(Hotspot h2 : vertex) {
				if(!h1.equals(h2)) {
					double distance = this.calculateDistance(h1, h2);
					//System.out.println(distance+"\n");
					if(distance <= maxDist) {
						Graphs.addEdge(graph, h1, h2, distance);
						//System.out.println("Aggiunto: "+distance+"\n");
					}
				}
			}
		}
		
		System.out.println(String.format("+++Grafo creato+++ Vertici: %d, Archi:%d" , graph.vertexSet().size(), graph.edgeSet().size()));
		
	}
	
	/**
	 * Utility method per calcolare la distanza 
	 * @param h1
	 * @param h2
	 * @return distance
	 */
	private double calculateDistance(Hotspot h1, Hotspot h2) {
		
		double distance = LatLngTool.distance(new LatLng(h1.getLatitude(), h1.getLongitude()),
												new LatLng(h2.getLatitude(), h2.getLongitude()),
													LengthUnit.METER);
		return distance;
	}

	/**
	 * Utility method per ottenere una lista di dimensione random di hotspot (vertici) guasti
	 * @param allVertex
	 * @param failure
	 * @return
	 */
	private List<Hotspot> badVertex(List<Hotspot> allVertex, double failure) {

		int newListSize = (int) (failure*allVertex.size());
		
		List<Hotspot> badVertex = new ArrayList<>(allVertex);
		for(int i=0; i<newListSize; i++) {
			badVertex.remove(badVertex.get((int) (Math.random()*badVertex.size())));
		}
		
		//Collections.shuffle(allVertex);
		
		return badVertex;
	}

	/**
	 * Componenti connesse del grafo
	 */
	public List<Set<Hotspot>> getCC() {
		
		ConnectivityInspector<Hotspot, DefaultWeightedEdge> c = new ConnectivityInspector<>(graph);
		System.out.println("Tot componenti connesse: "+c.connectedSets().size());     
		
		return c.connectedSets();
	}
	
	
	/**
	 * Eseguo la schedulazione su singola componente connessa attraverso l'algoritmo TSP
	 * @param compconn 
	 */
	public List<Hotspot> schedule(Set<Hotspot> compconn) {
		
			System.out.println("---Nuovo TSP---\n");
			TSP tsp = new TSP(compconn, graph);
			System.out.println(compconn.toString());
			tsp.solve();
		return tsp.printSolution();
			
//			TSP tsp = new TSP(listcompconn.get(0), graph);              //TEST sulla prima componente
//			System.out.println(listcompconn.get(0).toString());
//			tsp.solve();
//			tsp.printSolution();
		
	}
	
	
	//Calcolo il TSP con una classe Java
	public List<Hotspot> TSPwithJClass(Set<Hotspot> compconn) {
		
		Graph<Hotspot, DefaultWeightedEdge> subgraph = new AsSubgraph<Hotspot, DefaultWeightedEdge>(graph, compconn);
		
		System.out.println(String.format("--Sottografo-- Vertici: %d, Archi:%d" , subgraph.vertexSet().size(), subgraph.edgeSet().size()));
		
		TwoApproxMetricTSP<Hotspot, DefaultWeightedEdge> tsp = new TwoApproxMetricTSP<>();          //il grafo deve essere completo!!
		GraphPath<Hotspot, DefaultWeightedEdge> gp = tsp.getTour(subgraph);            //su sottografo
		
		List<Hotspot> path = gp.getVertexList();
		
		for(Hotspot h : path)
			System.out.println(h.toString());
		
		return path;
	}
	

	
}
