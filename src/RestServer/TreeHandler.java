package RestServer;


import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.nio.zipfs.JarFileSystemProvider;

import OpenDataLoad.Dataset;

public class TreeHandler implements HttpHandler {

	private Dataset[] data;

	public TreeHandler(Dataset[] data) {
		this.data=data;
	}

	private JSONArray buildJsonFromTrees(){
		JSONArray result = new JSONArray();
		for(Dataset tree:data){
			try {
				result.put(new JSONArray("["
						+ tree.getLatitude()
						+ ","+tree.getLongitude()
						+ ",0.1]"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// Always send Header for CrossDomain
		arg0.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		arg0.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, DELETE");
		arg0.getResponseHeaders().add("Access-Control-Max-Age", "1000");
		arg0.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
		arg0.getResponseHeaders().add("Content-type", "application/json");
		try {
			if (arg0.getRequestMethod().equals("GET")) {
				String response = buildJsonFromTrees().toString();
				arg0.sendResponseHeaders(200, response.length());
				OutputStream os = arg0.getResponseBody();
				os.write(response.getBytes());
				os.close();
			} else {
				arg0.sendResponseHeaders(404, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
