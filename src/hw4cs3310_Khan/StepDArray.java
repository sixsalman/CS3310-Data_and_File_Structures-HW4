package hw4cs3310_Khan;

// STEP d
/**
 * Instances of this class store integers in a sorted manner
 */
class StepDArray {

    private int[] array;
    private int fillLength;

    /**
     * Initializes the calling object's field 'array' assigning it the received size
     * @param size receives the size that the array is to be assigned
     */
    void assignSize (int size) {
        array = new int[size];
    }

    /**
     * Adds the received integer, maintaining ascending order, in the array that the calling instance contains
     * @param value receives the integer to add
     */
    void add (int value) {
        array[fillLength] = value;
        fillLength++;
        for (int i = fillLength - 1; i > 0; i--) {
            if (array[i - 1] > array[i]) {
                int tempHolder = array[i];
                array[i] = array[i - 1];
                array[i - 1] = tempHolder;
            } else {
                break;
            }
        }
    }

    /**
     * Returns the median from the array of integers contained in the calling instance
     * @return the median/upper median
     */
    int getMedian () {
        if (fillLength == 0)
            throw new IndexOutOfBoundsException("An error occurred in getMedian method of StepDArray class");
        return array[fillLength / 2];
    }

    /**
     * Deletes the median/upper median (depending on no. of elements contained in the array) from the array contained
     * in the calling instance
     */
    void deleteMedian () {
        if (fillLength > 0) {
            for (int i = fillLength / 2; i < fillLength - 1; i++) {
                array[i] = array[i + 1];
            }
            array[fillLength - 1] = 0;
            fillLength--;
        }
    }

}
