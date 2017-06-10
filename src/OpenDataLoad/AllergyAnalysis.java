package OpenDataLoad;

import java.util.ArrayList;

public class AllergyAnalysis {
	
	private final double R = 6371.0;				// earth radius km
	private final double MAXDISTANCEMETERS = 200;	// max weighted distance of tree
	private Dataset[] data;						// array of data containers
	private ArrayList<String> speciesStrings;	// array of strings for species e.g., {"Winter-Linde/Tilia cordata", "Kirsch-Pflaume/Prunus cerasifera"}
	private ArrayList<String> genusStrings;		// array of strings for genus e.g., {"Linde/Tilia", "Kirsche, Pflaume/Prunus"}
	
	/**
	 * Constructor.
	 * "/users/stud00/mp618/workspace/GitHackathon/res/baeume.csv"
	 */
	public AllergyAnalysis(String fullFileName) {
		
		// read file to data struct
		CsvParser p = new CsvParser();
		p.parseFile(fullFileName);
		data = p.getData();
		speciesStrings = p.getSpeciesStrings();
		genusStrings = p.getGenusStrings();
	}	
	
	/**
	 * Calculates the distance in meters for two given points in
	 * latitude-longitude
	 * 
	 * @param La0
	 * @param Lo0
	 * @param La1
	 * @param Lo1
	 * 
	 * @return distance in meters
	 */
	private double calcDistanceInMeters(final double La0, final double Lo0,
			final double La1, final double Lo1) {

		double dLat = toRad(La1 - La0);
		double dLon = toRad(Lo1 - Lo0); 
		double a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0) +
				Math.cos(toRad(La0)) * Math.cos(toRad(La1)) * 
				Math.sin(dLon / 2.0) * Math.sin(dLon / 2.0); 
		return (R * 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a)))*1000;
	}
	
	/**
	 * Calculates radians of an angle-degree.
	 * 
	 * @param angle in degree
	 * 
	 * @return angle in radians
	 */
	private double toRad(final double angle) {
		return angle * Math.PI / 180.0;
	}
	
	/**
	 * Analyzes the data for a specific allergic contamination.
	 * 
	 * @param La0 Latitude start
	 * @param Lo0 Longitude start
	 * @param La1 Latitude end
	 * @param Lo1 Longitude end
	 * @param nLa number of points in latitude dimension
	 * @param nLo number of points in longitude dimension
	 * @param treeGenusIndices array of indices of trees that trigger the allergy
	 * 
	 * @return matrix of intensity [nLa][nLo]
	 */
	public double[][] analyze(double La0, double Lo0,
			double La1, double Lo1,
			int nLa, int nLo,
			int[] treeGenusIndices) {
		
//		data
//		speciesStrings
//		genusStrings
		
		// 
		
		// create return data
		double[][] result = new double[][];
		
		// iterate la
		for (int i = 0; i < treeGenusIndices.length; i++) {
			int j = treeGenusIndices[i];
			
		}
			// iterate lo
				// calc intensity for (la, lo) depending on treeGenusIndices
		
		return null;
	}
	
	/**
	 * Calculates intensity for one coordinate-point, one data entry and array treeGenusIndices.
	 * 
	 * @param la0 latitude
	 * @param lo0 longitude
	 * @param oneData one data entry of Dataset
	 * @param treeGenusIndices array of indices of allergy trees
	 *  
	 * @return intensity(), if type of oneData is in treeGenusIndices, else 0.0
	 */
	private double calcIntensity(double La0, double Lo0, Dataset oneData, int[] treeGenusIndices) {
				
		return contains( treeGenusIndices, oneData.getGenusIndex()) ? intensity(La0, Lo0, oneData) : 0.0;
	}
	
	/**
	 * Calculates intensity for one coordinate-point and one data entry.
	 * 
	 * @param la0 latitude
	 * @param lo0 longitude
	 * @param oneData one data entry of Dataset
	 * 
	 * @return intensity
	 */
	private double intensity(final double La0, final double Lo0, final Dataset oneData) {
		
		return intensityOnDistance(calcDistanceInMeters(La0, Lo0, oneData.getLatitude(), oneData.getLongitude()));
	}
	
	/**
	 * Calculates intensity on distance [meters].
	 * 
	 * @param distance in meters
	 * 
	 * @return intensity per tree
	 */
	private double intensityOnDistance(final double distance) {
		return (distance<MAXDISTANCEMETERS) ? (MAXDISTANCEMETERS-distance)/MAXDISTANCEMETERS : 0.0;
	}
	
	// calc mask?
	
	/**
	 * Checks if an integer is in an array of integer.
	 * 
	 * @param array
	 * @param key
	 * 
	 * @return true/false
	 */
	public boolean contains(final int[] array, final int key) {
		
		boolean hit = false;
		
		for (int i = 0; i < array.length; i++) {
			hit = array[i]==key;
			if (hit) {
				break;
			}
		}
		
	    return hit;
	}
	
	public static void main(String[] args) {
		
		String fullFile =  "/users/stud00/mp618/workspace/GitHackathon/res/baeume.csv";
		AllergyAnalysis aa = new AllergyAnalysis(fullFile);
		
		// get analysisData
		double La0 = 54.0592;
		double Lo0 = 12.0017;
		double La1 = 54.1075;
		double Lo1 = 12.2157;
		
		int nLa = 1000;
		int nLo = 1000;
		int[] treeGenusIndices = new int[]{0,1,2};
		
		// calc distance for testing
		double metersDiagonal = aa.calcDistanceInMeters(La0, Lo0, La1, Lo1);
		System.out.println(metersDiagonal);
		
		double[][] aaData = aa.analyze(La0, Lo0, La1, Lo1, nLa, nLo, treeGenusIndices);
		
		int x=1;
		int y=x;
	}
}
