package it.polito.tesi.model;

public class Borough{
	
	private String sigla;
	private String name;
	private int code;
	
	public Borough(String sigla, String name, int code) {
		this.sigla = sigla;
		this.name = name;
		this.code = code;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public String toString() {
		return String.format("%s - %s", sigla, name);
	}
	
	
	

}
