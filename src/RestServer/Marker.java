package RestServer;

import org.json.JSONException;
import org.json.JSONObject;

public class Marker {
	private int id;
	private double lat;
	private double lon;
	private String description;
	
	public Marker() {
		// TODO Auto-generated constructor stub
	}
	
	public Marker(JSONObject json) throws JSONException {
		this.id=(int) json.get("id");
		this.lat=(double) json.get("lat");
		this.lon=(double) json.get("lon");
		this.description=json.get("description").toString();
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
