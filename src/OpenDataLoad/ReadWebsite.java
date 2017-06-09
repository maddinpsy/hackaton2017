package OpenDataLoad;

import java.net.*;
import java.io.*;
import java.util.regex.Matcher; // regular expression
import java.util.regex.Pattern; // regular expression

public class ReadWebsite {
	
    public static void main(String[] args) throws Exception {

    	// url to source
    	int limit = 5;
    	String urlString = "https://www.opendata-hro.de/api/action/datastore_search?resource_id=985ae459-ef3a-409d-a5bc-204d6600a253&limit=";
    	
    	// read excerpt of data from source: dataString
        URL url = new URL(urlString + limit);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));
        String dataString = in.readLine();
        in.close();

        // find maximum number of entries: maxEntries
        String pattern = "(\"total\": )(\\d+)(\\})"; // "total": 82015}
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(dataString);
        m.find();        
        pattern = "(\\d+)"; // "total": 82015}
        r = Pattern.compile(pattern);
        m = r.matcher(m.group(0).toString());
        m.find(); 
        String numberString = m.group(0).toString();
        int maxEntries = Integer.parseInt(numberString);
        
        // read all data from source: dataString
        url = new URL(urlString + maxEntries);
        in = new BufferedReader(
        new InputStreamReader(url.openStream()));
        dataString = in.readLine();
        in.close();
        
        System.out.println(dataString);
        
//        // parse data
//        JsonParser jp = new JsonParser(maxEntries);
//        jp.parseTrees(dataString, maxEntries);
    }
}