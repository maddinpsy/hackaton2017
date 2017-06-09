package OpenDataLoad;

import java.net.*;
import java.io.*;

public class ReadWebsite {
	
    public static void main(String[] args) throws Exception {

    	String urlString = "https://www.opendata-hro.de/api/action/datastore_search?resource_id=985ae459-ef3a-409d-a5bc-204d6600a253";
    	
        URL url = new URL(urlString);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
        
        JsonParser jp = new JsonParser();
        jp.parseThisShit(inputLine);
    }
}