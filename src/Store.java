

import org.json.JSONException;
import org.json.JSONObject;

public class Store {

	public JSONObject getTrees() throws JSONException {
		return new JSONObject("trees:[{1:'ulme'},{2:'pappel'}]");
	}

	public JSONObject getMarkers() {
		return new JSONObject();
	}

	public String addMarker(JSONObject newObj) {
		return "";
	}

	public String removeMarker(JSONObject newObj) {
		return "";
	}
}
