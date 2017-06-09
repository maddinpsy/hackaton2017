package OpenDataLoad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvParser {
	
	// container for one dataset: position, species_index, genus_index
	class Dataset {
		
		// source: "https://www.opendata-hro.de/dataset/baeume/resource/985ae459-ef3a-409d-a5bc-204d6600a253"
		
		private int index;			// dataset indexing
		private double longitude;
		private double latitude;
		private int speciesIndex;	// index of species in speciesStrings
		private int genusIndex;		// index of genus in genusStrings
		
		/**
		 * Constructor.
		 * 
		 * @param index index of this dataset
		 * @param longitude	longitude coordinate
		 * @param latitude latitude coordinate
		 * @param speciesIndex index of species in speciesStrings
		 * @param genusIndex index of genus in genusStrings
		 */
		public Dataset(int index, double longitude, double latitude, int speciesIndex, int genusIndex) {
			
			this.index = index;
			this.longitude = longitude;
			this.latitude = latitude;
			this.speciesIndex = speciesIndex;
			this.genusIndex = genusIndex;
		}

		public int getIndex() {
			return index;
		}
		
		public double getLongitude() {
			return longitude;
		}

		public double getLatitude() {
			return latitude;
		}
		
		public int getSpeciesIndex() {
			return speciesIndex;
		}
		
		public int getGenusIndex() {
			return genusIndex;
		}
	}

	private Dataset[] data;			// array of data containers
	private String[] speciesStrings;// array of strings for species e.g., {"Winter-Linde/Tilia cordata", "Kirsch-Pflaume/Prunus cerasifera"}
	private String[] genusStrings;	// array of strings for genus e.g., {"Linde/Tilia", "Kirsche, Pflaume/Prunus"}
	
	/**
	 * Constructor.
	 * 
	 * @param maxEntries max number of data entries
	 */
	public CsvParser(int maxEntries) {
		data = new Dataset[maxEntries];
		speciesStrings = null;
		genusStrings = null;
	}
	
	public Dataset[] getData() {
		return data;
	}
	
	public String[] getSpeciesStrings() {
		return speciesStrings;
	}
	
	public String[] getGenusStrings() {
		return genusStrings;
	}
	
	/**
	 * Parses a csv-file into the data format.
	 * 
	 * @param fullFileName full file name to a csv-file
	 */
//	public void parseFile(String fullFileName) {
	public void parseFile() {
		
		String fullFileName = "/users/stud00/mp618/workspace/GitHackathon/res/baeume.csv";		
		
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fullFileName))) {

        	// drop first line
        	line = br.readLine();
        	
        	int i = 0; // index counter        	
            while ((line = br.readLine()) != null && i<5) {
            	
            	i++;

                // use comma as separator
                String[] dataString = line.split(cvsSplitBy);

                // latitude=0, longitude=1, =13
                for (int j = 0; j < dataString.length; j++) {
                	System.out.println(dataString[j]);
				}
                System.out.println("---");               

            }

        } catch (IOException e) {
            e.printStackTrace();
        }		
	}
	
	public static void main(String[] args) {
		
		CsvParser p = new CsvParser(100);
		p.parseFile();
	}
}
