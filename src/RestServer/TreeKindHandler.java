package RestServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TreeKindHandler implements HttpHandler {

	private HashMap<String, Boolean> selectedTrees;

	public TreeKindHandler(ArrayList<String> treeKinds) {
		this.selectedTrees=new HashMap<>(treeKinds.size());
		for(String treename:treeKinds){
			this.selectedTrees.put(treename,false);
		}
	}
	
	
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		try {
			// Always send Header for CrossDomain
			arg0.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
			arg0.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, DELETE");
			arg0.getResponseHeaders().add("Access-Control-Max-Age", "1000");
			arg0.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
			arg0.getResponseHeaders().add("Content-type", "application/json");

			// Replay with treekinds to a get request
			JSONArray resp = new JSONArray();
			if (arg0.getRequestMethod().equals("GET")) {
				// Get all Data
				for(String key:this.selectedTrees.keySet()){
					resp.put(new JSONObject("{'name':'"+key+"','sel':'"+selectedTrees.get(key)+"'}"));
				}
				String response = resp.toString();
				arg0.sendResponseHeaders(200, response.length());
				OutputStream os = arg0.getResponseBody();
				os.write(response.getBytes());
				os.close();
				// add new marker/or change existing on post
			} else if (arg0.getRequestMethod().equals("POST")) {
				// First get the message body, which is the list with selected trees
				InputStream is = arg0.getRequestBody();
				java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
				if (!s.hasNext())
					return;
				String strObj = s.next();
				System.out.println(strObj);
				JSONArray client_select = new JSONArray(strObj);
	
				// deselect all trees in the servers list
				for(String key:this.selectedTrees.keySet()){
					this.selectedTrees.put(key, false);
				}
				//And select the given ones
				for(int i=0;i<client_select.length();i++){
					JSONObject itm = (JSONObject) client_select.get(i);
					this.selectedTrees.put((String)itm.get("name"),true);
				}
			} else {
				arg0.sendResponseHeaders(404, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
