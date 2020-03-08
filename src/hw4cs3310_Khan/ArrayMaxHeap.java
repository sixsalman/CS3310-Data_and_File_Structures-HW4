package hw4cs3310_Khan;

import java.io.Serializable;

// STEP f
/**
 * Instances of this class simulate a max-oriented heap (priority queue)
 */
class ArrayMaxHeap implements Serializable {

    /**
     * Instances of this class hold the no. of children that the node has along with an integer value
     */
    private class Node implements Serializable {
        int value;
        int childCount;

        /**
         * Creates a Node object initializing all its fields
         * @param value receives the integer that this object is to contain
         * @param childCount receives the no. of children this node has
         */
        Node(int value, int childCount) {
            this.value = value;
            this.childCount = childCount;
        }
    }

    private Node[] heap;

    void assignSize (int size) {
        this.heap = new Node[size];
    }

    // STEP b
    /**
     * Adds the received integer in the tree structure that instances of this class stimulate and rearranges values
     * contained in the tree to maintain its max-oriented nature
     * @param value receives the integer to store
     */
    void addLMedian (int value) {
        if (heap[0] == null) {
            heap[0] = new Node(value, 0);
        } else {
            int traverseNode = addRecur(0, value);
            boolean stop = false;
            while (!stop) {
                if (traverseNode == 0)
                    break;
                if (heap[traverseNode].value > heap[(traverseNode - 1) / 2].value) {
                    int tempHolder = heap[traverseNode].value;
                    heap[traverseNode].value = heap[(traverseNode - 1) / 2].value;
                    heap[(traverseNode - 1) / 2].value = tempHolder;
                    traverseNode = ((traverseNode - 1) / 2);
                } else {
                    stop = true;
                }
            }
        }
    }

    /**
     * Adds a new node at the right index - maintaining the tree's size-balanced nature
     * @param thisIndex receives index of the root node
     * @param value receives the integer to store
     * @return index to the added node
     */
    private int addRecur (int thisIndex, int value) {
        if (thisIndex >= heap.length)
            throw new ArrayIndexOutOfBoundsException("An error occurred in addRecur method of ArrayMaxHeap class");
        if(heap[thisIndex*2 + 1] == null) {
            heap[thisIndex*2 + 1] = new Node(value, 0);
            heap[thisIndex].childCount++;
            return (thisIndex * 2 + 1);
        } else if (heap[thisIndex*2 + 2] == null) {
            heap[thisIndex*2 + 2] = new Node(value, 0);
            heap[thisIndex].childCount++;
            return (thisIndex * 2 + 2);
        }
        heap[thisIndex].childCount++;
        if(heap[thisIndex*2 + 1].childCount <= heap[thisIndex*2 + 2].childCount) {
            return addRecur((thisIndex*2 + 1), value);
        } else {
            return addRecur((thisIndex*2 + 2), value);
        }
    }

    /**
     * Gets the no. of nodes contained in the calling instance of this class
     * @return no. of nodes contained
     */
    int getCount () {
        if (heap[0] == null)
            return 0;
        return (heap[0].childCount + 1);
    }

    // STEP a
    /**
     * Gets the integer (maximum value) stored in root node of the calling instance of this class
     * @return the integer contained in root node
     */
    int getHighestVal () {
        if (heap[0] == null)
            throw new NullPointerException("An error occurred in getHighestVal method of ArrayMaxHeap class");
        return heap[0].value;
    }

    // STEP c
    /**
     * Deletes the integer (maximum value) stored in root node of the calling instance of this class and rearranges
     * the remaining integers maintaining max-oriented nature of the tree
     */
    void deleteHighestVal () {
        if (heap[0] != null) {
            if (heap[0].childCount == 0) {
                heap[0] = null;
                return;
            }
            int deepestIndex = getDeepestIndex(0);
            heap[0].value = heap[deepestIndex].value;
            if (heap[((deepestIndex - 1) / 2) * 2 + 1] != null && heap[((deepestIndex - 1) / 2) * 2 + 2] != null &&
                    heap[((deepestIndex - 1) / 2) * 2 + 1].value == heap[((deepestIndex - 1) / 2) * 2 + 2].value)
                deepestIndex = ((deepestIndex - 1) / 2) * 2 + 1;
            if (heap[((deepestIndex - 1) / 2) * 2 + 1] != null &&
                    heap[((deepestIndex - 1) / 2) * 2 + 1].value == heap[deepestIndex].value) {
                heap[((deepestIndex - 1) / 2) * 2 + 1] = null;
            } else {
                heap[((deepestIndex - 1) / 2) * 2 + 2] = null;
            }

            boolean stop = false;
            int traverseIndex = 0;
            while (!stop) {
                if (heap[traverseIndex].childCount == 0) {
                    stop = true;
                } else if (heap[traverseIndex].childCount == 1) {
                    if (heap[traverseIndex * 2 + 1] != null &&
                            heap[traverseIndex * 2 + 1].value > heap[traverseIndex].value) {
                        int tempHolder = heap[traverseIndex].value;
                        heap[traverseIndex].value = heap[traverseIndex * 2 + 1].value;
                        heap[traverseIndex * 2 + 1].value = tempHolder;
                    } else if (heap[traverseIndex * 2 + 2] != null &&
                            heap[traverseIndex * 2 + 2].value > heap[traverseIndex].value) {
                        int tempHolder = heap[traverseIndex].value;
                        heap[traverseIndex].value = heap[traverseIndex * 2 + 2].value;
                        heap[traverseIndex * 2 + 2].value = tempHolder;
                    }
                    stop = true;
                } else if (heap[traverseIndex].childCount >= 2) {
                    if (heap[traverseIndex * 2 + 1].value > heap[traverseIndex * 2 + 2].value) {
                        if (heap[traverseIndex].value < heap[traverseIndex * 2 + 1].value) {
                            int tempHolder = heap[traverseIndex].value;
                            heap[traverseIndex].value = heap[traverseIndex * 2 + 1].value;
                            heap[traverseIndex * 2 + 1].value = tempHolder;
                            traverseIndex = (traverseIndex * 2 + 1);
                        } else {
                            stop = true;
                        }
                    } else {
                        if (heap[traverseIndex].value < heap[traverseIndex * 2 + 2].value) {
                            int tempHolder = heap[traverseIndex].value;
                            heap[traverseIndex].value = heap[traverseIndex * 2 + 2].value;
                            heap[traverseIndex * 2 + 2].value = tempHolder;
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
     * Finds and returns the deepest index (which has greatest height) contained with the calling instance of this
     * class
     * @param thisIndex receives index of the root node
     * @return an index at highest level in tree
     */
    private int getDeepestIndex (int thisIndex) {
        if (thisIndex >= heap.length)
            throw new ArrayIndexOutOfBoundsException("An error occurred in getDeepestIndex method of ArrayMaxHeap " +
                    "class");
        if (heap[thisIndex * 2 + 1] == null && heap[thisIndex * 2 + 2] == null) {
            return thisIndex;
        } else if (heap[thisIndex * 2 + 2] == null) {
            heap[thisIndex].childCount--;
            return (thisIndex * 2 + 1);
        } else if (heap[thisIndex * 2 + 1] == null) {
            heap[thisIndex].childCount--;
            return (thisIndex * 2 + 2);
        } else {
            heap[thisIndex].childCount--;
            if (heap[thisIndex * 2 + 1].childCount > heap[thisIndex * 2 + 2].childCount) {
                return getDeepestIndex(thisIndex * 2 + 1);
            } else {
                return getDeepestIndex(thisIndex * 2 + 2);
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
     * @param thisIndex receives index of the root node
     * @param toRet reference to a StringBuilder to which traversed integers are to be added
     */
    private void buildInorder (int thisIndex, StringBuilder toRet) {
        if (heap[thisIndex] == null)
            return;
        buildInorder(thisIndex * 2 + 1, toRet);
        if (!toRet.toString().equals(""))
            toRet.append(", ");
        toRet.append(heap[thisIndex].value);
        buildInorder(thisIndex * 2 + 2, toRet);
    }

}
