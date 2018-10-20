package it.polito.tesi.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
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
		List<Borough> boros = dao.getAllBoroughs();
		
		if(boros.isEmpty())
			throw new RuntimeException("Borough list is empty. Check DAO class.");
		
		return boros;
		
	}
	
	public void createGraph(Provider provider, Borough borough, int maxDist, double failure) {
		
		graph = new SimpleWeightedGraph<Hotspot, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List<Hotspot> allVertex = new ArrayList<>();
		if(borough!=null)
			allVertex = dao.getVertex(provider, borough);
		else
			allVertex = dao.getAllVertex(provider, bmap);
		
		int size = (int) (failure*allVertex.size());
		List<Hotspot> vertex = new ArrayList<>(allVertex);
		for(int i=0; i<size; i++) {
			vertex.remove(vertex.get((int) (Math.random()*vertex.size())));
		}
		
		//Collections.shuffle(allVertex);
		
		Graphs.addAllVertices(graph, vertex);
		
		for(Hotspot h1 : vertex) {
			for(Hotspot h2 : vertex) {
				if(!h1.equals(h2)) {
					double distance = LatLngTool.distance(new LatLng(h1.getLatitude(), h1.getLongitude()),
														new LatLng(h2.getLatitude(), h2.getLongitude()),
														LengthUnit.METER);
					System.out.println(distance+"\n");
					if(distance<=maxDist) {
						graph.addEdge(h1, h2);
						System.out.println("Aggiunto: "+distance+"\n");
					}
				}
			}
		}
		
		System.out.println(String.format("+++Grafo creato+++ Vertici: %d, Archi:%d" , graph.vertexSet().size(), graph.edgeSet().size()));
		
	}

}
