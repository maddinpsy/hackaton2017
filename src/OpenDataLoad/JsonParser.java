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
	
	JSONParser parser;
	
	public JsonParser() {
		
		parser = new JSONParser();
	}
	
	/**
	 * 
	 * @param str
	 */
	public void parseThisShit(String str) {
		
		try {

			Object obj = parser.parse(str);

			JSONObject jsonObject = (JSONObject) obj;
			System.out.println(jsonObject);

			String name = (String) jsonObject.get("latitude");
			System.out.println(name);

//			long age = (Long) jsonObject.get("age");
//			System.out.println(age);

			// loop array
			JSONArray msg = (JSONArray) jsonObject.get("latitude");
			Iterator<String> iterator = msg.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		JSONParser parser = new JSONParser();

		try {

			Object obj = parser.parse(new FileReader("f:\\test.json"));

			JSONObject jsonObject = (JSONObject) obj;
			System.out.println(jsonObject);

			String name = (String) jsonObject.get("latitude");
			System.out.println(name);

//			long age = (Long) jsonObject.get("age");
//			System.out.println(age);

			// loop array
			JSONArray msg = (JSONArray) jsonObject.get("latitude");
			Iterator<String> iterator = msg.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
