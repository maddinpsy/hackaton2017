package OpenDataLoad;


//<dependency>
//<groupId>com.googlecode.json-simple</groupId>
//<artifactId>json-simple</artifactId>
//<version>1.1.1</version>
//</dependency>
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class JsonParser {
	
	// container for position: (longitude, latitude)
	class Position  {
		
		private double longitude;
		private double latitude;
		
		public Position(double longitude, double latitude) {
			this.longitude = longitude;
			this.latitude = latitude;
		}
		
		public double getLongitude() {
			return longitude;
		}
		
		public double getLatitude() {
			return latitude;
		}
	}
	
	// json parser
	private JSONParser parser;

	// max number of entries
	private int	maxEntries;
	
	// position array
	private Position[] position;
	
	/**
	 * Constructor.
	 * 
	 * @param maxEntries number of entries in data
	 */
	public JsonParser(int maxEntries) {
		
		this.maxEntries = maxEntries;
		parser = new JSONParser();
		position = new Position[maxEntries];
	}
	
	/**
	 * Parses data for tree input.
	 * 
	 * @param str source data with tree positions and names
	 */
	public void parseTrees(String str, int maxEntries) {
		
		try {

			JSONObject jsonObject = (JSONObject) parser.parse(str);

			// parse
			JSONObject result = (JSONObject) jsonObject.get("result");
			JSONArray records = (JSONArray) result.get("records");			
			Iterator<JSONObject> iterator = records.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				i++;
				JSONObject name1 = iterator.next();
				System.out.println("" + i + ": " + name1.get("longitude") + ", " + name1.get("latitude"));
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
