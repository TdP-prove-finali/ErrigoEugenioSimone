package it.polito.tesi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class TSP extends TSPAlgorithms{

	private List<Hotspot> best;
	private double bestWeight;
	private List<Hotspot> compconn;
	private long start_time;
	private long end_time;
	private boolean isLate;
	
	public TSP(Graph<Hotspot, DefaultWeightedEdge> graph, Set<Hotspot> compconn) {
		super(graph, compconn);
		this.compconn = new ArrayList<>(compconn);
	}
	
	/**
	 * Calcola la sequenza ottimale di hotspot da visitare che minimizza la distanza da percorrere, utilizzando la ricorsione
	 */
	@Override
	public void solve() {
		
		best = new ArrayList<Hotspot>();
		bestWeight = Double.MAX_VALUE;
		
		if(compconn.size()==1)
			best.add(compconn.get(0));
		else {
			isLate = false;
			start_time = System.currentTimeMillis();
			end_time = start_time+2000;
	
			List<Hotspot> parziale = new ArrayList<Hotspot>();
			
			int random = (int) (Math.random()*compconn.size());
			parziale.add(compconn.get(random));
			
			isLate = this.explore(parziale, 1);
			
			if(!best.isEmpty())
				best.add(best.get(0));
		}
	}
	
	/**
	 * Funzione ricorsiva: prova tutti i possibili insiemi di hotspots e sceglie il migliore
	 * 
	 * @param parziale
	 * @param step
	 */
	private boolean explore(List<Hotspot> parziale, int step) {
		
		if(System.currentTimeMillis()>end_time)
			return true;
		
		if(step>=compconn.size()) {
			if(weightOf(parziale) < bestWeight) {
				best = new ArrayList<>(parziale);
				bestWeight = weightOf(best);
				
				System.out.println(bestWeight + best.toString());
				//return;                                           
			}
		}
		
		// trova vertici adiacenti all'ultimo
		Hotspot ultimo = parziale.get(parziale.size() - 1);
		List<Hotspot> adiacenti = Graphs.neighborListOf(this.graph, ultimo);
		System.out.println("Adiacenti: "+adiacenti);
			
		for(Hotspot h : adiacenti) {
			if(compconn.contains(h) && !parziale.contains(h)) {
				parziale.add(h);
				this.explore(parziale, step+1);
				parziale.remove(h);
			}
		}
		
		return false;
		
	}

	/**
	 * Utility method per calcolare il peso della collezione
	 * @param parziale
	 * @return peso
	 */
	private double weightOf(List<Hotspot> parziale) {
		double peso = 0.0;
		
		//Controllo che la soluzione candidata non sia vuota o nulla
		if(parziale.isEmpty() || parziale == null)
			return Double.MAX_VALUE;
		
		//Controllo che la soluzione candidata abbia tutti gli hotspots della componente connessa
		for(Hotspot h : compconn) {
			if(!parziale.contains(h))
				return Double.MAX_VALUE;
		}
		
		//Controllo che la soluzione candidata abbia un arco tra l'ultimo e il primo hotspot
		if(!graph.containsEdge(parziale.get(parziale.size()-1), parziale.get(0))) {
			return Double.MAX_VALUE;
		}
		
		//Calcolo il peso della soluzione parziale 
		for(int i=0; i< parziale.size()-1; i++) {
			DefaultWeightedEdge e = graph.getEdge(parziale.get(i), parziale.get(i+1));
			System.out.println(graph.getEdgeWeight(e));
			peso += graph.getEdgeWeight(e);
		}
		//Aggiungo il peso dell'arco tra l'ultimo e il primo hotspot
		DefaultWeightedEdge e = graph.getEdge(parziale.get(parziale.size()-1), parziale.get(0));
		System.out.println(graph.getEdgeWeight(e));
		peso += graph.getEdgeWeight(e);
		
		
		System.out.println("Peso: "+peso);
		return peso;
	}

	
	/**
	 * Ottiene la soluzione del TSP
	 */
	@Override
	public List<Hotspot> getSolution() {
		System.out.println("+++SOLUZIONE+++");
		for(Hotspot h: best) {
			System.out.println("+ "+ h.toString());
		}
		System.out.println("\nBest weight: "+bestWeight+"\n");
		
		return best;
	}
	
	public boolean isLate() {
		return isLate;
	}

	@Override
	public double getBestWeight() {
		return bestWeight;
	}
	
	
	
	

}
