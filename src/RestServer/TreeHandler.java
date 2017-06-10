package RestServer;

import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.nio.zipfs.JarFileSystemProvider;

import OpenDataLoad.AllergyAnalysis;
import OpenDataLoad.Dataset;

public class TreeHandler implements HttpHandler {

	private double[][] data;

	public TreeHandler(double[][] data) {
		this.data = data;
	}

	private JSONArray buildJsonFromTrees(
			double La0, //BottomLeft
			double Lo0, //BottomLeft
			double La1, //TopRight
			double Lo1, //TopRight
			int nLa, //Anzahl Elemente in lat richtung
			int nLo //Anzahl Elemente in Lon richtung
			)
	{
		double dLa = (La1 - La0) / (nLa - 1);
		double dLo = (Lo1 - Lo0) / (nLo - 1);
		double La0Temp;
		double Lo0Temp;
		double tempDla = AllergyAnalysis.calcDistanceInMeters(La0, Lo0, La0+dLa, Lo0);
		double tempDlo = AllergyAnalysis.calcDistanceInMeters(La0, Lo0, La0, Lo0+dLo);
		System.out.println("dLa[m]: " + tempDla);
		System.out.println("dLo[m]: " + tempDlo);

		JSONArray result = new JSONArray();
		for (int i = 0; i < data.length; i++) {
			La0Temp = La0+i*dLa; // calc new La_pos
			for (int j = 0; j < data[i].length; j++) {
				Lo0Temp = Lo0+j*dLo;// calc new Lo_pos
				try {
					result.put(new JSONArray("[" 
				         + La0Temp 
				         + ","
				         + Lo0Temp 
				         + "," 
				         + data[i][j] + "]"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		
		// testdata
		double La0 = 54.0592;
		double Lo0 = 12.0017;
		double La1 = 54.1861;
		double Lo1 = 12.2222;
		int nLa = 500;
		int nLo = 500;
		
		// Always send Header for CrossDomain
		arg0.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		arg0.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, DELETE");
		arg0.getResponseHeaders().add("Access-Control-Max-Age", "1000");
		arg0.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
		arg0.getResponseHeaders().add("Content-type", "application/json");
		try {
			if (arg0.getRequestMethod().equals("GET")) {
				String response = buildJsonFromTrees(La0,Lo0,La1,Lo1, nLa,nLo).toString();
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
