package hw4cs3310_Khan;

/**
 * Contains multiple sorting algorithms.
 * Some of the contents of this class were copied from HW1
 */
class Sort {

    private static int[] shuffled;

    // STEP i
    /**
     * Inserts each obtained median, through the received object, at the correct position in an array and returns it
     * @param s receives the object containing heaps - contents of which are to be sorted
     * @return an array of sorted numbers
     */
    static int[] sortUsingMedians(Heaps s) {
        if (s.getTotCount() == 0) {
            shuffled = new int[0];
            return new int[0];
        }
        int[] toRet = new int[s.getTotCount()];
        shuffled = new int[s.getTotCount()];
        toRet[toRet.length / 2] = s.getQPlus().getRootVal();
        shuffled[0] = s.getQPlus().getRootVal();
        s.deleteMedian();
        if (toRet.length % 2 == 0) {
            for (int i = 1, j = 1; i < toRet.length / 2; i++, j += 2) {
                toRet[toRet.length / 2 - i] = s.getQPlus().getRootVal();
                shuffled[j] = s.getQPlus().getRootVal();
                s.deleteMedian();
                toRet[toRet.length / 2 + i] = s.getQPlus().getRootVal();
                shuffled[j + 1] = s.getQPlus().getRootVal();
                s.deleteMedian();
            }
            toRet[0] = s.getMedian();
            shuffled[shuffled.length - 1] = s.getQPlus().getRootVal();
            s.deleteMedian();
        } else {
            for (int i = 1, j = 1; i < toRet.length / 2 + 1; i++, j += 2) {
                toRet[toRet.length / 2 + i] = s.getQPlus().getRootVal();
                shuffled[j] = s.getQPlus().getRootVal();
                s.deleteMedian();
                toRet[toRet.length / 2 - i] = s.getQPlus().getRootVal();
                shuffled[j + 1] = s.getQPlus().getRootVal();
                s.deleteMedian();
            }
        }
        return toRet;
    }

    /**
     * Gets 'shuffled' array field contained in this class
     * @return an unsorted version of the contents contained in the object received by sortUsingMedians method earlier
     */
    static int[] getShuffled() {
        return shuffled;
    }

    // STEP j
    /**
     * Divides received array completely (recursively) and gets its parts merged back in a sorted manner
     * @param toSort receives an array
     * @return sorted version of the received array
     */
    static int[] mergeSort(int[] toSort) {
        if (toSort.length == 0)
            return new int[0];
        int[] partOne;
        int[] partTwo;
        int mid;
        if(toSort.length == 1)
            return toSort;
        mid = toSort.length / 2;
        partOne = new int[mid];
        for (int i = 0; i < mid; i++) {
            partOne[i] = toSort[i];
        }
        partTwo = new int[toSort.length - mid];
        int j = 0;
        for (int i = mid ; i < toSort.length; i++) {
            partTwo[j] = toSort[i];
            j++;
        }
        partOne = mergeSort(partOne);
        partTwo = mergeSort(partTwo);
        return merge(partOne, partTwo);
    }

    /**
     * Merges two sorted arrays into one
     * @param partOne receives a sorted array
     * @param partTwo receives another sorted array
     * @return a sorted array containing all elements from both parts received
     */
    private static int[] merge(int[] partOne, int[] partTwo) {
        int[] toReturn = new int[partOne.length + partTwo.length];
        int indexToRet = 0;
        int indexPOne = 0;
        int indexPTwo = 0;
        while(partOne.length != indexPOne || partTwo.length != indexPTwo) {
            if(partOne.length == indexPOne) {
                toReturn[indexToRet] = partTwo[indexPTwo];
                indexToRet++;
                indexPTwo++;
            } else if (partTwo.length == indexPTwo){
                toReturn[indexToRet] = partOne[indexPOne];
                indexToRet++;
                indexPOne++;
            } else if(partOne[indexPOne] > partTwo[indexPTwo]) {
                toReturn[indexToRet] = partTwo[indexPTwo];
                indexToRet++;
                indexPTwo++;
            } else {
                toReturn[indexToRet] = partOne[indexPOne];
                indexToRet++;
                indexPOne++;
            }
        }
        return toReturn;
    }

}
