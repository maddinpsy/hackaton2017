package RestServer;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import OpenDataLoad.AllergyAnalysis;
import OpenDataLoad.CsvParser;
import OpenDataLoad.Dataset;

public class Server {

    public static void main(String[] args) throws Exception {
    	
    	//Read Data
    	System.out.println("reading Data");
    	CsvParser p = new CsvParser();
		p.parseFile("res/baeume.csv");
    	Dataset[] data = p.getData();
    	
    	
    	
    	
    	//Configure Server
    	System.out.println("starting Server");
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        Store store = new Store();
        
        server.createContext("/", new FileHandler());
		server.createContext("/markers", new MarkerHandler(store));
        //server.createContext("/trees", new TreeHandler(getAnalysData()));
        server.createContext("/treekinds", new TreeKindHandler(p.getGenusStrings()));
        
        server.start();
    }
    
    
    public static double[][] getAnalysData() {
    	String fullFile = "res/baeume.csv";
		AllergyAnalysis aa = new AllergyAnalysis(fullFile);
		
		// testdata
		double La0 = 54.0592;
		double Lo0 = 12.0017;
		double La1 = 54.1861;
		double Lo1 = 12.2222;
		int nLa = 500;
		int nLo = 500;
		
		int[] treeGenusIndices = new int[]{6, // Eibe
				8, // Linde
				10, // Erle
				32, // Haselnuss
				45, // Buche
				55}; // Kiefer
		
//		int[] treeGenusIndices = new int[80];
//		for (int i = 0; i < treeGenusIndices.length; i++) {
//			treeGenusIndices[i] = i;
//		}
		
		
		// do analysis
		double data[][] = aa.analyze(La0, Lo0, La1, Lo1, nLa, nLo, treeGenusIndices);
		return data;
	}
}