package RestServer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MarkerHandler  implements HttpHandler{
	
	private Store store;

	public MarkerHandler(Store store) {
		this.store=store;
	}
	
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		try{
			if(arg0.getRequestMethod().equals("GET")){
				String response=store.getMarkers().toString();
				arg0.sendResponseHeaders(200, response.length());
	            OutputStream os = arg0.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			}else if(arg0.getRequestMethod().equals("POST")){
				InputStream is = arg0.getRequestBody();
				java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
				JSONObject newObj=new JSONObject(s.next());
				String response=store.addMarker(newObj);
				arg0.sendResponseHeaders(200, response.length());
	            OutputStream os = arg0.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			}else if(arg0.getRequestMethod().equals("DELETE")){
				InputStream is = arg0.getRequestBody();
				java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
				JSONObject newObj=new JSONObject(s.next());
				String response=store.removeMarker(newObj);
				arg0.sendResponseHeaders(200, response.length());
	            OutputStream os = arg0.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			}else{
				arg0.sendResponseHeaders(404, 0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
