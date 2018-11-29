package it.polito.tesi.model;

public class Hotspot {
	
	private int id;
	private Borough borough;
	private String type;
	private Provider provider;
	private String location;
	private String street;
	private double latitude;
	private double longitude;
	private String city;
	private String remark;
	private String SSID;
	
	public Hotspot(int id, Borough borough, String type, Provider provider, String location, String street,
			double latitude, double longitude, String city, String remark, String sSID) {
		this.id = id;
		this.borough = borough;
		this.type = type;
		this.provider = provider;
		this.location = location;
		this.street = street;
		this.latitude = latitude;
		this.longitude = longitude;
		this.city = city;
		this.remark = remark;
		SSID = sSID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Borough getBorough() {
		return borough;
	}

	public void setBorough(Borough borough) {
		this.borough = borough;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSSID() {
		return SSID;
	}

	public void setSSID(String sSID) {
		SSID = sSID;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Hotspot other = (Hotspot) obj;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		return true;
	}

	public String print() {
		if(remark!=null)
			return "location: " +location + " - " + street + " -SSID: " +SSID +" (" + type + ") - Info: " + remark;
		else
			return "location: " +location + " - " + street + " -SSID: " +SSID +" (" + type + ")";
	}
	
	@Override
	public String toString() {
		return "Hotspot [ID=" + id +", SSID=" + SSID +", borough=" + borough + ", type=" + type + ", provider=" + provider + ", location=" + location
				+ ", street=" + street + ", city=" + city + ", remark=" + remark + "]";
	}
	

}
