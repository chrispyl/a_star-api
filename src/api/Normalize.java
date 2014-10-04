package api;

/**
 * Contains normalization methods.
 *
 */
public class Normalize {
	
	/**
	 * @param number The number we want to normalize
	 * @param maxNumber The maximum value the number can take
	 * @return The number inserted in the range of 0-1
	 */
	public static float zeroToOneScale(float number, float maxNumber)
	{
		return number/maxNumber;
	}
}
