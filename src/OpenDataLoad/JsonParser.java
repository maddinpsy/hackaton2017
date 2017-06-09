package OpenDataLoad;


import java.io.FileReader;

//<dependency>
//<groupId>com.googlecode.json-simple</groupId>
//<artifactId>json-simple</artifactId>
//<version>1.1.1</version>
//</dependency>
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParser {
	
	public JsonParser() {
	}
	
	/**
	 * 
	 * @param str
	 */
	public void parseThisShit(String str)  throws Exception  {
			JSONObject jsonObject = new JSONObject(str);
			System.out.println(jsonObject);

			String name = (String) jsonObject.get("latitude");
			System.out.println(name);

//			long age = (Long) jsonObject.get("age");
//			System.out.println(age);

			// loop array
			JSONArray msg = (JSONArray) jsonObject.get("latitude");
			for(int i=0;i<msg.length();i++){
				System.out.println(msg.get(i));
			}
	}

	public static void main(String[] args) throws Exception {

			JSONObject jsonObject = new JSONObject(new FileReader("f:\\test.json"));
			System.out.println(jsonObject);

			String name = (String) jsonObject.get("latitude");
			System.out.println(name);

//			long age = (Long) jsonObject.get("age");
//			System.out.println(age);

			// loop array
			JSONArray msg = (JSONArray) jsonObject.get("latitude");
			for(int i=0;i<msg.length();i++){
				System.out.println(msg.get(i));
			}

	}

}
