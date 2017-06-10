package OpenDataLoad;

//container for one dataset: data_index, position, species_index, genus_index
public class Dataset {

	// source: "https://www.opendata-hro.de/dataset/baeume/resource/985ae459-ef3a-409d-a5bc-204d6600a253"

	private int index;			// dataset indexing
	private double latitude;
	private double longitude;		
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
	public Dataset(int index, double latitude, double longitude, int speciesIndex, int genusIndex) {

		this.index = index;
		this.latitude = latitude;
		this.longitude = longitude;			
		this.speciesIndex = speciesIndex;
		this.genusIndex = genusIndex;
	}

	public int getIndex() {
		return index;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public int getSpeciesIndex() {
		return speciesIndex;
	}

	public int getGenusIndex() {
		return genusIndex;
	}
}