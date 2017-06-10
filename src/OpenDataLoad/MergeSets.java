package OpenDataLoad;

/**
 * Class to merge 2 int arrays to one set (array that is a set).
 */
public final class MergeSets {
	
	/**
	 * Merges two sets of array of integer into one set of array of integer.
	 * 
	 * @param arr1 set of int[]
	 * @param arr2 set of int[]
	 * 
	 * @return set of int[]
	 */
	public static int[] merge2sets(int[] arr1, int[] arr2) {
		
		int[] result = null;
		int[] resultTemp = null;
		
		if (arr1!=null) {
		
			resultTemp = new int[arr1.length+arr2.length];
			int i = 0;
			for (; i < arr1.length; i++) {
				resultTemp[i] = arr1[i];
			}
			int endIndex = i-1;
			
			for (int k=0; k < arr2.length; k++) {
				if (!contains(arr1, arr2[k])) {
					endIndex++;
					resultTemp[endIndex] = arr2[k];
				}
			}
			result = new int[endIndex+1];
			for (int j = 0; j < result.length; j++) {
				result[j] = resultTemp[j];
			}		
		}
		
		return result;
	}
	
	/**
	 * Checks if an integer is in an array of integer.
	 * 
	 * @param array
	 * @param key
	 * 
	 * @return true/false
	 */
	public static boolean contains(int[] array, int key) {
		
		boolean hit = false;
		
		for (int i = 0; i < array.length; i++) {
			hit = array[i]==key;
			if (hit) {
				break;
			}
		}
		
	    return hit;
	}
	
//	public static void main(String[] args) {
//		
//		int[] arr1 = new int[]{1,2,3,4};
//		int[] arr2 = new int[]{5,6};
//		int[] result =  merge2sets(arr1, arr2);
//		int x=result[1];
//		int y=x;
//		
//	}
}
