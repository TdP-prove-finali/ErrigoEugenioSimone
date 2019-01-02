package it.polito.tesi.model;

import java.util.List;

public class Route {
	
	private int area;
	private List<HotspotAndArea> hotspots;
	private double totDistance;
	
	public Route(int area, List<HotspotAndArea> hotspots, double totDistance) {
		this.area = area;
		this.hotspots = hotspots;
		this.totDistance = totDistance;
	}

	public int getArea() {
		return area;
	}

	public List<HotspotAndArea> getHotspots() {
		return hotspots;
	}

	public double getTotDistance() {
		return totDistance;
	}
	
	public double getRouteTime() {            //10min per km di spostamento
		if(this.hotspots.size()==1)
			return 0.0;
		return totDistance*10;
	}
	
	public double getRipTime() {              //15min per riparazione
		return this.hotspots.size()*15;
	}
	
	
	

}
