package RestServer;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
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

	/**
	 * Für das gegeben Object hinzu und erzeugt eine ID, fass diese 0 war. 
	 * @param newObj
	 * @return die Id des neuen Objects oder -1 bei einem Fehler.
	 */
	public Integer addMarker(JSONObject newObj) {
		try {
			Marker marker = new Marker(newObj);
			if(marker.getId()==0){
				//Increaing id, TODO: Overflow at maxint!
				if(markers.keySet().isEmpty()){
					marker.setId(1);
				}else{
					marker.setId(Collections.max(markers.keySet())+1);
				}
			}
			markers.put(marker.getId(),marker);
			return marker.getId();
		} catch (JSONException e) {
			return -1;
		}
	}

	public String removeMarker(JSONObject newObj) {
		try {
			markers.remove((int)newObj.get("id"));
			return "ok";
		} catch (JSONException e) {
			return "error" + e.getMessage();
		}
	}
}
