package it.polito.tesi.model;

public class Provider{
	
	private String name;
	
	public Provider(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
}
