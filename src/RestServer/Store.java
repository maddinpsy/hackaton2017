package RestServer;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Store {
	
	private Map<Integer,Marker> markers; 
	
	public Store() {
		this.markers=new HashMap<>();
	}
	
	public JSONObject getTrees() throws JSONException {
		try {
			return new JSONObject(new FileReader("res/baeume.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new JSONObject();
		}
	}

	public JSONArray getMarkers() {
		return new JSONArray(markers.values());
	}

	public String addMarker(JSONObject newObj) {
		try {
			Marker marker = new Marker(newObj);
			markers.put(marker.getId(),marker);
			return "Ok";
		} catch (JSONException e) {
			return "Error";
		}
	}

	public String removeMarker(JSONObject newObj) {
		try {
			markers.remove((int)newObj.get("id"));
			return "Ok";
		} catch (JSONException e) {
			return "Error";
		}
	}
}
