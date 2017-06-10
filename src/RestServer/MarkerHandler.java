package RestServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MarkerHandler implements HttpHandler {

	private Store store;

	public MarkerHandler(Store store) {
		this.store = store;
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

			// Replay with all markers to a get request
			if (arg0.getRequestMethod().equals("GET")) {
				// Get all Data
				String response = store.getMarkers().toString();
				arg0.sendResponseHeaders(200, response.length());
				OutputStream os = arg0.getResponseBody();
				os.write(response.getBytes());
				os.close();
				// add new marker/or change existing on post
			} else if (arg0.getRequestMethod().equals("POST")) {
				// Hack damit delete funktioniert...
				if (arg0.getRequestURI().toString().contains("delete")) {
					// Delete
					InputStream is = arg0.getRequestBody();
					java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
					JSONObject newObj = new JSONObject(s.next());
					String response = store.removeMarker(newObj);
					arg0.sendResponseHeaders(200, response.length());
					OutputStream os = arg0.getResponseBody();
					os.write(response.getBytes());
					os.close();
				} else {
					// First get the message body, which is Json serelized marker
					InputStream is = arg0.getRequestBody();
					java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
					if (!s.hasNext())
						return;
					String strObj = s.next();
					System.out.println(strObj);
					JSONObject newObj = new JSONObject(strObj);
		
					// then add this to the sorage
					Integer newID = store.addMarker(newObj);
					String strNewId = newID.toString();
					arg0.sendResponseHeaders(200, strNewId.length());
					OutputStream os = arg0.getResponseBody();
					os.write(strNewId.getBytes());
					os.close();
				}
			} else if (arg0.getRequestMethod().equals("DELETE")) {
				InputStream is = arg0.getRequestBody();
				java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
				JSONObject newObj = new JSONObject(s.next());
				String response = store.removeMarker(newObj);
				arg0.sendResponseHeaders(200, response.length());
				OutputStream os = arg0.getResponseBody();
				os.write(response.getBytes());
				os.close();
			} else if (arg0.getRequestMethod().equals("OPTIONS")) {
			} else {
				arg0.sendResponseHeaders(404, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
