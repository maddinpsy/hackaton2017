package OpenDataLoad;

import java.util.ArrayList;

public class AllergyAnalysis {

	private static final double WEIGHTCUTOFF = 300.0; 
	private static final double WEIGHTFACTOR = 1.0;
	private static final double D_DISTANCE_LA = 111194.92664455873; // approx distance in meter per degree
	private static final double D_DISTANCE_LO = 65358.19505379952; // approx distance in meter per degree
	private static final double MAXDISTANCEMETERS = 300.0;	// max weighted distance of tree [meters]
	private static final double HALFDISTANCEMETERS = MAXDISTANCEMETERS/4; // half weight distance [meters]
	// expconst = ln(A_0/A(x))/x
	private static final double EXPCONST = Math.log(2)/HALFDISTANCEMETERS;

	private Dataset[] data;						// array of data containers
	private ArrayList<String> speciesStrings;	// array of strings for species e.g., {"Winter-Linde/Tilia cordata", "Kirsch-Pflaume/Prunus cerasifera"}
	private ArrayList<String> genusStrings;		// array of strings for genus e.g., {"Linde/Tilia", "Kirsche, Pflaume/Prunus"}

	/**
	 * Constructor.
	 * 
	 * @param fullFileName full file name to source csv
	 */
	public AllergyAnalysis(String fullFileName) {

		// read file to data structure
		CsvParser p = new CsvParser();
		p.parseFile(fullFileName);
		data = p.getData();
		speciesStrings = p.getSpeciesStrings();
		genusStrings = p.getGenusStrings();
	}

	/**
	 * Getter.
	 * 
	 * @return data
	 */
	public Dataset[] getData() {
		return data;
	}

	/**
	 * Getter.
	 * 
	 * @return speciesStrings
	 */
	public ArrayList<String> getSpeciesStrings() {
		return speciesStrings;
	}

	/**
	 * Getter.
	 * 
	 * @return genusStrings
	 */
	public ArrayList<String> getGenusStrings() {
		return genusStrings;
	}

	/**
	 * Calculates the distance in meters for two given points of
	 * latitude-longitude
	 * 
	 * @param La0
	 * @param Lo0
	 * @param La1
	 * @param Lo1
	 * 
	 * @return distance in meters
	 */
	public static double calcDistanceInMeters(double La0, double Lo0, double La1, double Lo1) {

		//		double dLat = toRad(La1 - La0);
		//		double dLon = toRad(Lo1 - Lo0); 
		//		double a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0) +
		//				Math.cos(toRad(La0)) * Math.cos(toRad(La1)) * 
		//				Math.sin(dLon / 2.0) * Math.sin(dLon / 2.0); 
		//		return (6371.0 * 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a)))*1000;

		double distLaMeters = D_DISTANCE_LA*Math.abs(La1-La0);
		double distLoMeters = D_DISTANCE_LO*Math.abs(Lo1-Lo0);

		return Math.sqrt( distLaMeters*distLaMeters + distLoMeters*distLoMeters );
	}

	/**
	 * Calculates radians of a degree angle.
	 * 
	 * @param angle in degree
	 * 
	 * @return angle in radians
	 */
	private static double toRad(double angle) {
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

		// create return data, init with 0.0
		double[][] result = new double[nLa][nLo];

		// calc dLa, dLo
		double dLa = (La1-La0) / (nLa-1); // one less steps then points
		double dLo = (Lo1-Lo0) / (nLo-1); // one less steps then points
		double La0Temp;
		double Lo0Temp;

		// test output: steps in meters
		//				System.out.println("dLa[m]: " + calcDistanceInMeters(La0, Lo0, La0+dLa, Lo0));
		//				System.out.println("dLo[m]: " + calcDistanceInMeters(La0, Lo0, La0, Lo0+dLo));

		// iterate la
		for (int i = 0; i < nLa; i++) {

			// progress feedback
			System.out.println("" + ((double)100.0*i/nLa) + " %");

			La0Temp = La0+i*dLa; // calc new La_pos

			// iterate lo
			for (int j = 0; j < nLo; j++) {

				Lo0Temp = Lo0+j*dLo;// calc new Lo_pos

				// iterate data
				for (int k = 0; k < data.length; k++) {

					// calc intensity for (la, lo) depending on treeGenusIndices
					if (result[i][j] < WEIGHTCUTOFF) {
						double c = calcIntensity(La0Temp, Lo0Temp, data[k], treeGenusIndices);
						result[i][j] += c;
					}
				}
			}
		}
		
		// check min and max in data
		double[] miMa = minMax(result);
		System.out.println("min: " + miMa[0]);
		System.out.println("max: " + miMa[1]);

		return result;
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
	private double intensity(double La0, double Lo0, Dataset oneData) {

		return intensityOnDistance(calcDistanceInMeters(La0, Lo0, oneData.getLatitude(), oneData.getLongitude()));
	}

	/**
	 * Calculates intensity on distance [meters].
	 * 
	 * @param distance in meters
	 * 
	 * @return intensity per tree
	 */
	private double intensityOnDistance(double distance) {

		// 
		// Math.pow(base, exponent) // base=2, exponent=3: 8.0
		// A(x) = A_0 * Math.pow(Math.E, (-EXPCONST*x))

//		return (distance<MAXDISTANCEMETERS) ? WEIGHTFACTOR : 0.0;
//		return (distance<MAXDISTANCEMETERS) ? (MAXDISTANCEMETERS-distance)/MAXDISTANCEMETERS : 0.0;
		return (distance<MAXDISTANCEMETERS) ? WEIGHTFACTOR*Math.pow(Math.E, (-EXPCONST*distance)) : 0.0;
	}

	// calc mask if too slow?

	/**
	 * Checks if an integer is in an array of integer.
	 * 
	 * @param array
	 * @param key
	 * 
	 * @return true/false
	 */
	public boolean contains(int[] array, int key) {

		boolean hit = false;

		for (int i = 0; i < array.length; i++) {
			hit = array[i]==key;
			if (hit) {
				break;
			}
		}

		return hit;
	}

	/**
	 * Calculates maximum over array.
	 * 
	 * @param arr
	 * 
	 * @return maxMax
	 */
	private static double maxMax(double[][] arr) {

		double max = 0.0;

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {				
				max = max<arr[i][j] ? arr[i][j] : max;
			}
		}

		return max;
	}

	/**
	 * Calculates minimum and maximum over array.
	 * 
	 * @param arr
	 * 
	 * @return miMa[0] - minimum, miMa[1] - maximum
	 */
	public static double[] minMax(double[][] arr) {

		double miMa[] = new double[2];
		miMa[0] = maxMax(arr);
		miMa[1] = miMa[0];

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {				
				miMa[0] = miMa[0]>arr[i][j] ? arr[i][j] : miMa[0];
			}
		}

		return miMa;
	}

	public static void main(String[] args) {

		String fullFile = "/users/stud00/mp618/workspace/GitHackathon/res/baeume.csv";
		AllergyAnalysis aa = new AllergyAnalysis(fullFile);

		double La0 = 54.0592;
		double Lo0 = 12.0017;
		double La1 = 54.1861;
		double Lo1 = 12.2222;
		int nLa = 20;
		int nLo = 20;

		// calc distance for testing
		double metersLa = aa.calcDistanceInMeters(54, 12, 55, 12);
		System.out.println("metersLa: " + metersLa);
		double metersLo = aa.calcDistanceInMeters(54, 12, 54, 13);
		System.out.println("metersLo: " + metersLo);

		int[] treeGenusIndices = new int[]{0,1,2,10,20,30,40,50,60,70};
		//		int[] treeGenusIndices = new int[80];
		//		for (int i = 0; i < treeGenusIndices.length; i++) {
		//			treeGenusIndices[i] = i;
		//		}

		// test contains()
		System.out.println("contains:");
		System.out.println(aa.contains(treeGenusIndices, 71));
		System.out.println(aa.contains(treeGenusIndices, 10));

		// do analysis
		double[][] aaData = aa.analyze(La0, Lo0, La1, Lo1, nLa, nLo, treeGenusIndices);

//		// check min and max in data
//		double[] miMa = aa.minMax(aaData);
//		System.out.println("min: " + miMa[0]);
//		System.out.println("max: " + miMa[1]);
	}
}