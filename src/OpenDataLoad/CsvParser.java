package OpenDataLoad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvParser {

	private Dataset[] data;						// array of data containers
	private ArrayList<String> speciesStrings;	// array of strings for species e.g., {"Winter-Linde/Tilia cordata", "Kirsch-Pflaume/Prunus cerasifera"}
	private ArrayList<String> genusStrings;		// array of strings for genus e.g., {"Linde/Tilia", "Kirsche, Pflaume/Prunus"}
	
	/**
	 * Constructor.
	 * 
	 * @param maxEntries max number of data entries
	 */
	public CsvParser() {
		speciesStrings = new ArrayList<>();
		genusStrings = new ArrayList<>();
	}
	
	/**
	 * Getter.
	 * @return data array
	 */
	public Dataset[] getData() {
		return data;
	}	

	/**
	 * Getter.
	 * @return speciesStrings
	 */
	public ArrayList<String> getSpeciesStrings() {
		return speciesStrings;
	}
	
	/**
	 * Getter.
	 * @return genusStrings
	 */
	public ArrayList<String> getGenusStrings() {
		return genusStrings;
	}
	
	/**
	 * Adds entry to list if entry is not in list.
	 * Returns index where entry can be found in list.
	 * 
	 * @param list an ArrayList<String>
	 * @param newEntry a String to be unique in list
	 * @return index where newEntry can be found in list
	 */
	private int setEntryInList(ArrayList<String> list, String newEntry) {

		int i = 0;
		boolean hit = false;
		
		if (list.isEmpty()) { // new entry from start
			
			list.add(newEntry);
			
		} else { // check if entry is in array, if not: add
			
			for (int j = 0; j < list.size(); j++) {
				
				hit = newEntry.equals(list.get(j));
				if (hit) {
					i = j;
					break;
				}
			}
			if (!hit) {
				i = list.size();
				list.add(newEntry);
			}
		}
		return i;
	}
	
	/**
	 * Adds new string to speciesStrings if it is new. Returns index where
	 * the new string can be found in speciesStrings.
	 * 
	 * @param newEnty a new string
	 * @return index where newEntry can be found in speciesStrings
	 */
	public int setSpeciesStrings(String newEntry) {
		
		return setEntryInList(getSpeciesStrings(), newEntry);
	}
	
	/**
	 * Adds new string to genusStrings if it is new. Returns index where
	 * the new string can be found in genusStrings.
	 * 
	 * @param newEnty a new string
	 * @return index where newEntry can be found in genusStrings
	 */
	public int setGenusStrings(String newEntry) {
		
		return setEntryInList(getGenusStrings(), newEntry);
	}
	
	/**
	 * Parses a csv-file into the data format.
	 * 
	 * @param fullFileName full file name to a csv-file
	 */
	public void parseFile(String fullFileName) {
		
        String line = "";
        String cvsSplitBy = ",";        
        
        double latitude;
        double longitude;        
        int speciesIndex;
        int genusIndex;
        String specStringTemp;
        String genusStringTemp;
        
        // get max number of lines
        int maxIndex = 0; // index counter  
        try (BufferedReader br = new BufferedReader(new FileReader(fullFileName))) {

        	// drop first line
        	line = br.readLine();

        	      	
        	while ((line = br.readLine()) != null) {

        		// index++
        		maxIndex++;
        	}
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        // create data structure
        data = new Dataset[maxIndex];

        // parse all lines to data structure
        try (BufferedReader br = new BufferedReader(new FileReader(fullFileName))) {

        	// drop first line
        	line = br.readLine();
        	
        	int index = 0; // index counter        	
            while ((line = br.readLine()) != null) {

            // split line
            String[] dataStrings = line.split(cvsSplitBy);

            // cast data:
            // latitude:0
            // longitude:1
            // gattung_botanisch:11
            // gattung_deutsch:12
            // art_botanisch:13
            // art_deutsch:14
            latitude = Double.parseDouble(dataStrings[0]);
            longitude = Double.parseDouble(dataStrings[1]);
            specStringTemp = dataStrings[14] + "/" + dataStrings[13];
            genusStringTemp = dataStrings[12] + "/" + dataStrings[11];

            // add species and genus to set
            speciesIndex = setSpeciesStrings(specStringTemp);
            genusIndex = setGenusStrings(genusStringTemp);        		                

            // save data in new entry
            data[index] = new Dataset(index, latitude, longitude, speciesIndex, genusIndex);

            // index++
            index++;         

            }
        } catch (IOException e) {
        	e.printStackTrace();
        }		
	}
	
//	public static void main(String[] args) {
//		
//		CsvParser p = new CsvParser();
//		p.parseFile("/users/stud00/mp618/workspace/GitHackathon/res/baeume.csv");
//	}
}
