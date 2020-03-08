package hw4cs3310_Khan;

import java.io.Serializable;

/**
 * Instances of this aggregate class contain objects which are variations of heaps and store integers.
 */
class Heaps implements Serializable {

    private LinkedListMinHeap qPlus;
    private ArrayMaxHeap qMinus;

    /**
     * Creates and returns an object of the LinkedListMinHeap class
     * @return an object of the LinkedListMinHeap class
     */
    LinkedListMinHeap createQPlus () {
        qPlus = new LinkedListMinHeap();
        return qPlus;
    }

    /**
     * Creates and returns an object of the ArrayMaxHeap class
     * @return an object of the ArrayMaxHeap class
     */
    ArrayMaxHeap createQMinus () {
        qMinus = new ArrayMaxHeap();
        return qMinus;
    }

    // STEP b
    /**
     * Adds an integer in one of the heaps (depending on its value) contained within the calling instance of this class
     * @param value the integer to add
     * @return String "Q+" if the integer was added in LinkedListMinHeap; "Q-" otherwise
     */
    String add (int value) {
        String toRet;
        if (getTotCount() == 0) {
            qPlus.addGEMedian(value);
            return "Q+";
        }
        if (value < getMedian()) {
            qMinus.addLMedian(value);
            toRet = "Q-";
            if (getTotCount() % 2 == 0) {
                if (qMinus.getCount() != qPlus.getCount()) {
                    qPlus.addGEMedian(qMinus.getHighestVal());
                    qMinus.deleteHighestVal();
                }
            } else {
                if (qMinus.getCount() != (qPlus.getCount() - 1)) {
                    qPlus.addGEMedian(qMinus.getHighestVal());
                    qMinus.deleteHighestVal();
                }
            }
        } else {
            qPlus.addGEMedian(value);
            toRet = "Q+";
            if (getTotCount() % 2 == 0) {
                if (qMinus.getCount() != qPlus.getCount()) {
                    qMinus.addLMedian(qPlus.getRootVal());
                    qPlus.deleteRootVal();
                }
            } else {
                if (qMinus.getCount() != (qPlus.getCount() - 1)) {
                    qMinus.addLMedian(qPlus.getRootVal());
                    qPlus.deleteRootVal();
                }
            }
        }
        return toRet;
    }

    // STEP a
    /**
     * Gets the median integer among the integers contained in objects within the calling instance of this class
     * @return the median integer
     */
    int getMedian () {
        int count = getTotCount();
        if (count == 0)
            throw new IndexOutOfBoundsException("An error occurred in getMedian method of Heaps class");
        if (count % 2 == 0) {
            return ((qMinus.getHighestVal() + qPlus.getRootVal()) / 2);
        } else {
            return qPlus.getRootVal();
        }
    }

    // STEP c
    /**
     * Deletes the median integer among the integers contained in objects within the calling instance of this class
     */
    void deleteMedian () {
        if (getTotCount() == 0)
            return;
        qPlus.deleteRootVal();
        if (qMinus.getCount() > qPlus.getCount()) {
            qPlus.addGEMedian(qMinus.getHighestVal());
            qMinus.deleteHighestVal();
        }
    }

    /**
     * Gets cumulative count of the integers contained in objects within the calling instance of this class
     * @return the total count
     */
    int getTotCount () {
        return qPlus.getCount() + qMinus.getCount();
    }

    /**
     * Returns inorder traversals of the integers contained in objects within the calling instance of this class
     * @return a String containing the traversals
     */
    String getInOrder () {
        return (qMinus.getInOrder() + "\n" + qPlus.getInOrder());
    }

    /**
     * Gets the LinkedListMinHeap object contained within the calling instance of this class
     * @return the contained LinkedListMinHeap object
     */
    LinkedListMinHeap getQPlus() {
        return qPlus;
    }
}
