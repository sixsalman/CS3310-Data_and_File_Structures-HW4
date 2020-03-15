package hw4cs3310_Khan;

import java.io.Serializable;

// STEP f
/**
 * Instances of this class simulate a max-oriented heap (priority queue)
 */
class ArrayMaxHeap implements Serializable {

    private int[] heap;
    private int fillLength;

    void assignSize (int size) {
        this.heap = new int[size];
    }

    // STEP b
    /**
     * Adds the received integer in the tree structure that instances of this class stimulate and rearranges values
     * contained in the tree to maintain its max-oriented nature
     * @param value receives the integer to store
     */
    void addLMedian (int value) {
        heap[fillLength] = value;
        int traverseInt = fillLength;
        fillLength++;
        boolean stop = false;
        while (!stop) {
            if (traverseInt == 0)
                break;
            if (heap[traverseInt] > heap[(traverseInt - 1) / 2]) {
                int tempHolder = heap[traverseInt];
                heap[traverseInt] = heap[(traverseInt - 1) / 2];
                heap[(traverseInt - 1) / 2] = tempHolder;
                traverseInt = ((traverseInt - 1) / 2);
            } else {
                stop = true;
            }
        }
    }

    /**
     * Gets the no. of integers contained in the calling instance of this class
     * @return no. of integers contained
     */
    int getCount () {
        return fillLength;
    }

    // STEP a
    /**
     * Gets the integer (maximum value) stored as root of the calling instance of this class
     * @return the integer contained as root
     */
    int getHighestVal () {
        if (fillLength == 0)
            throw new NullPointerException("An error occurred in getHighestVal method of ArrayMaxHeap class");
        return heap[0];
    }

    // STEP c
    /**
     * Deletes the integer (maximum value) stored as root of the calling instance of this class and rearranges
     * the remaining integers maintaining max-oriented nature of the tree
     */
    void deleteHighestVal () {
        if (fillLength != 0) {
            if (fillLength == 1) {
                fillLength--;
                return;
            }
            int deepestIndex = --fillLength;
            heap[0] = heap[deepestIndex];

            boolean stop = false;
            int traverseIndex = 0;
            while (!stop) {
                if (traverseIndex * 2 + 1 >= fillLength) {
                    stop = true;
                } else if (traverseIndex * 2 + 2 >= fillLength) {
                    if (heap[traverseIndex * 2 + 1] > heap[traverseIndex]) {
                        int tempHolder = heap[traverseIndex];
                        heap[traverseIndex] = heap[traverseIndex * 2 + 1];
                        heap[traverseIndex * 2 + 1] = tempHolder;
                    }
                    stop = true;
                } else {
                    if (heap[traverseIndex * 2 + 1] > heap[traverseIndex * 2 + 2]) {
                        if (heap[traverseIndex] < heap[traverseIndex * 2 + 1]) {
                            int tempHolder = heap[traverseIndex];
                            heap[traverseIndex] = heap[traverseIndex * 2 + 1];
                            heap[traverseIndex * 2 + 1] = tempHolder;
                            traverseIndex = (traverseIndex * 2 + 1);
                        } else {
                            stop = true;
                        }
                    } else {
                        if (heap[traverseIndex] < heap[traverseIndex * 2 + 2]) {
                            int tempHolder = heap[traverseIndex];
                            heap[traverseIndex] = heap[traverseIndex * 2 + 2];
                            heap[traverseIndex * 2 + 2] = tempHolder;
                            traverseIndex = (traverseIndex * 2 + 2);
                        } else {
                            stop = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns an in-orderly traversed representation of the integers contained in the calling instance of this class
     * @return a String containing integers in an in-orderly traversed manner
     */
    String getInOrder () {
        StringBuilder toRet = new StringBuilder("");
        buildInorder(0, toRet);
        return ("Inorder traversal of Q- is: " + toRet.toString());
    }

    /**
     * Traverses (in-order) through the integers contained in the calling instance of this class and builds a string
     * using them
     * @param thisIndex receives index of the root integer
     * @param toRet reference to the StringBuilder to which traversed integers are to be added
     */
    private void buildInorder (int thisIndex, StringBuilder toRet) {
        if (thisIndex >= fillLength)
            return;
        buildInorder(thisIndex * 2 + 1, toRet);
        if (!toRet.toString().equals(""))
            toRet.append(", ");
        toRet.append(heap[thisIndex]);
        buildInorder(thisIndex * 2 + 2, toRet);
    }

}
