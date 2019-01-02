package it.polito.tesi.model;

public class HotspotAndArea {
	
	private int numarea;
	private String location;
	private String street;
	private String city;
	private String SSID;
	private String remark;
	
	public HotspotAndArea(int numarea, Hotspot h) {
		this.numarea = numarea;
		this.location = h.getLocation();
		this.street = h.getStreet();
		this.city = h.getCity();
		this.SSID = h.getSSID();
		this.remark = h.getRemark();
	}
	
	public HotspotAndArea() {   //separatore
		this.location = "-";
		this.street = "-";
		this.city = "-";
		this.SSID = "-";
		this.remark = "-";
	}

	public int getNumarea() {
		return numarea;
	}

	public String getLocation() {
		return location;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getSSID() {
		return SSID;
	}

	public String getRemark() {
		return remark;
	}


}
