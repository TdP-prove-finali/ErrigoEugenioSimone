package it.polito.tesi.model;

import java.util.HashMap;
import java.util.Map;

public class BoroughIdMap {
	
	private Map<String, Borough> map;
	
	public BoroughIdMap() {
		map = new HashMap<String, Borough>();
	}
	
	public Borough get(Borough b) {
		Borough old = map.get(b.getSigla());
		if(old==null) {
			map.put(b.getSigla(), b);
			return b;
		}
		return old;
	}
	
	public Borough get(String sigla) {
		return map.get(sigla);
	}

}
