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
		this.location = null;
		this.street = null;
		this.city = null;
		this.SSID = null;
		this.remark = null;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((SSID == null) ? 0 : SSID.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + numarea;
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HotspotAndArea other = (HotspotAndArea) obj;
		if (SSID == null) {
			if (other.SSID != null)
				return false;
		} else if (!SSID.equals(other.SSID))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (numarea != other.numarea)
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		return true;
	}

	

}
