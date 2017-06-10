package RestServer;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import OpenDataLoad.AllergyAnalysis;
import OpenDataLoad.CsvParser;
import OpenDataLoad.Dataset;

public class Test {

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
        
		server.createContext("/markers", new MarkerHandler(store));
        server.createContext("/trees", new TreeHandler(getAnalysDate()));
        
        server.start();
    }
    
    
    public static double[][] getAnalysDate() {
    	String fullFile = "res/baeume.csv";
		AllergyAnalysis aa = new AllergyAnalysis(fullFile);
		
		// testdata
		double La0 = 54.0592;
		double Lo0 = 12.0017;
		double La1 = 54.1861;
		double Lo1 = 12.2222;
		int nLa = 20;
		int nLo = 20;
		
		int[] treeGenusIndices = new int[]{0,1,2,10,20,30,40,50,60,70};
//		int[] treeGenusIndices = new int[80];
//		for (int i = 0; i < treeGenusIndices.length; i++) {
//			treeGenusIndices[i] = i;
//		}
		
		
		// do analysis
		double data[][] = aa.analyze(La0, Lo0, La1, Lo1, nLa, nLo, treeGenusIndices);
		return data;
	}
}