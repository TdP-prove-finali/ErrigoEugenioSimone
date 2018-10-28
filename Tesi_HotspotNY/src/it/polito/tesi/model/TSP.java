package it.polito.tesi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class TSP {
	
	private Graph<Hotspot, DefaultWeightedEdge> graph;
	private List<Hotspot> compconn;
	private List<Hotspot> best;
	private double bestWeight;
	
	public TSP(Set<Hotspot> compconn, Graph<Hotspot, DefaultWeightedEdge> graph) {
		this.graph = graph;
		this.compconn = new ArrayList<Hotspot>(compconn);
	}
	
	/**
	 * Calcola la sequenza ottimale di hotspot da riparare che minimizza la distanza da percorrere, utilizzando la ricorsione
	 */
	public void solve() {
		
		best = new ArrayList<Hotspot>();
		bestWeight = Double.MAX_VALUE;
		
		List<Hotspot> parziale = new ArrayList<Hotspot>();
		parziale.add(compconn.get(0));
		this.explore(parziale, 1);
	}
	
	/**
	 * Funzione ricorsiva: prova tutte le possibili liste di hotspots e sceglie la migliore
	 * @param parziale
	 * @param step
	 */
	private void explore(List<Hotspot> parziale, int step) {
		
		if(step>=10) {                                                               //?
			if(weightOf(parziale) < bestWeight && weightOf(parziale)!=0) {
				best = new ArrayList<>(parziale);
				bestWeight = weightOf(best);
				
				System.out.println(bestWeight + best.toString());
				return;
			}
		}
		
		// trova vertici adiacenti all'ultimo
		Hotspot ultimo = parziale.get(parziale.size() - 1);
		List<Hotspot> adiacenti = Graphs.neighborListOf(this.graph, ultimo);
		System.out.println("Adiacenti: "+adiacenti);
			
		for(Hotspot h : adiacenti) {
			if(!parziale.contains(h) && compconn.contains(h)) {
				parziale.add(h);
				this.explore(parziale, step+1);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}

	/**
	 * Utility method per calcolare il peso della collezione
	 * @param parziale
	 * @return peso
	 */
	private double weightOf(List<Hotspot> parziale) {
		double peso = 0.0;
		
		for(int i=0; i< parziale.size()-1; i++) {
			DefaultWeightedEdge e = graph.getEdge(parziale.get(i), parziale.get(i+1));
			System.out.println(graph.getEdgeWeight(e));
			peso += graph.getEdgeWeight(e);
		}
		
		System.out.println("Peso: "+peso);
		return peso;
	}


	public void printSolution() {
		System.out.println("+++SOLUZIONE+++");
		for(Hotspot h: best) {
			System.out.println("+ "+ h.toString());
		}
	}
	
	
	

}
