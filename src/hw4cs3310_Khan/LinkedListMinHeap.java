package hw4cs3310_Khan;

import java.io.Serializable;

// STEP e
/**
 * Instances of this class simulate a min-oriented heap (priority queue)
 */
class LinkedListMinHeap implements Serializable {

    /**
     * Instances of this class simulate a node in a tree structure
     */
    private class Node implements Serializable {
        int value;
        int childCount;
        Node parent;
        Node left;
        Node right;

        /**
         * Creates a Node object initializing all its fields
         * @param value receives the integer that this object is to contain
         * @param childCount receives the no. of children this node has
         * @param parent receives the node that is to be assigned as the parent of this node
         * @param left receives the node that is to be assigned as the left child of this node
         * @param right receives the node that is to be assigned as the right child of this node
         */
        Node(int value, int childCount, Node parent, Node left, Node right) {
            this.value = value;
            this.childCount = childCount;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }

    private Node root;

    // STEP b
    /**
     * Adds the received integer in the tree structure that instances of this class stimulate and rearranges values
     * contained in the tree to maintain its min-oriented nature
     * @param value receives the integer to store
     */
    void addGEMedian (int value) {
        if (root == null) {
            this.root = new Node(value, 0, null, null, null);
        } else {
            Node traverseNode = addRecur(root, value);
            boolean stop = false;
            while (!stop && traverseNode.parent != null) {
                if (traverseNode.value < traverseNode.parent.value) {
                    int tempHolder = traverseNode.value;
                    traverseNode.value = traverseNode.parent.value;
                    traverseNode.parent.value = tempHolder;
                    traverseNode = traverseNode.parent;
                } else {
                    stop = true;
                }
            }
        }
    }

    /**
     * Adds a new node at the right place - maintaining the tree's size-balanced nature
     * @param thisNode receives a reference to the root node
     * @param value receives the integer to store
     * @return reference to the added node
     */
    private Node addRecur (Node thisNode, int value) {
        if(thisNode.left == null) {
            thisNode.left = new Node(value, 0, thisNode, null, null);
            thisNode.childCount++;
            return thisNode.left;
        } else if (thisNode.right == null) {
            thisNode.right = new Node(value, 0, thisNode, null, null);
            thisNode.childCount++;
            return thisNode.right;
        }
        thisNode.childCount++;
        if(thisNode.left.childCount <= thisNode.right.childCount) {
            return addRecur(thisNode.left, value);
        } else {
            return addRecur(thisNode.right, value);
        }
    }

    /**
     * Gets the no. of nodes contained in the calling instance of this class
     * @return no. of nodes contained
     */
    int getCount () {
        if (root == null)
            return 0;
        return (root.childCount + 1);
    }

    // STEP a
    /**
     * Gets the integer (minimum value) stored in root node of the calling instance of this class
     * @return the integer contained in root node
     */
    int getRootVal () {
        if (root == null)
            throw new NullPointerException("An error occurred in getRootVal method of LinkedListMinHeap class");
        return root.value;
    }

    // STEP c
    /**
     * Deletes the integer (minimum value) stored in root node of the calling instance of this class and rearranges
     * the remaining integers maintaining min-oriented nature of the tree
     */
    void deleteRootVal () {
        if (root != null) {
            if (root.childCount == 0) {
                root = null;
                return;
            }
            Node deepestNode = getDeepestNode(root);
            root.value = deepestNode.value;
            if (deepestNode.parent.left != null && deepestNode.parent.right != null &&
                    deepestNode.parent.left.value == deepestNode.parent.right.value)
                deepestNode = deepestNode.parent.left;
            if (deepestNode.parent.left != null && deepestNode.parent.left.value == deepestNode.value) {
                deepestNode.parent.left = null;
                deepestNode.parent = null;
            } else {
                deepestNode.parent.right = null;
                deepestNode.parent = null;
            }

            boolean stop = false;
            Node traverseNode = root;
            while (!stop) {
                if (traverseNode.childCount == 0) {
                    stop = true;
                } else if (traverseNode.childCount == 1) {
                    if (traverseNode.left != null && traverseNode.left.value < traverseNode.value) {
                        int tempHolder = traverseNode.value;
                        traverseNode.value = traverseNode.left.value;
                        traverseNode.left.value = tempHolder;
                    } else if (traverseNode.right != null && traverseNode.right.value < traverseNode.value) {
                        int tempHolder = traverseNode.value;
                        traverseNode.value = traverseNode.right.value;
                        traverseNode.right.value = tempHolder;
                    }
                    stop = true;
                } else if (traverseNode.childCount >= 2) {
                    if (traverseNode.left.value < traverseNode.right.value) {
                        if (traverseNode.value > traverseNode.left.value) {
                            int tempHolder = traverseNode.value;
                            traverseNode.value = traverseNode.left.value;
                            traverseNode.left.value = tempHolder;
                            traverseNode = traverseNode.left;
                        } else {
                            stop = true;
                        }
                    } else {
                        if (traverseNode.value > traverseNode.right.value) {
                            int tempHolder = traverseNode.value;
                            traverseNode.value = traverseNode.right.value;
                            traverseNode.right.value = tempHolder;
                            traverseNode = traverseNode.right;
                        } else {
                            stop = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Finds and returns the deepest node (which has greatest height) contained with the calling instance of this class
     * @param thisNode receives a reference to the root node
     * @return a node at highest level in tree
     */
    private Node getDeepestNode (Node thisNode) {
        if (thisNode.left == null && thisNode.right == null) {
            return thisNode;
        } else if (thisNode.right == null) {
            thisNode.childCount--;
            return thisNode.left;
        } else if (thisNode.left == null) {
            thisNode.childCount--;
            return thisNode.right;
        } else {
            thisNode.childCount--;
            if (thisNode.left.childCount > thisNode.right.childCount) {
                return getDeepestNode(thisNode.left);
            } else {
                return getDeepestNode(thisNode.right);
            }
        }
    }

    /**
     * Returns an in-orderly traversed representation of the integers contained in the calling instance of this class
     * @return a String containing integers in an in-orderly traversed manner
     */
    String getInOrder () {
        StringBuilder toRet = new StringBuilder("");
        buildInorder(root, toRet);
        return ("Inorder traversal of Q+ is: " + toRet.toString());
    }

    /**
     * Traverses (in-order) through the integers contained in the calling instance of this class and builds a string
     * using them
     * @param thisNode receives the root node
     * @param toRet reference to the StringBuilder to which traversed integers are to be added
     */
    private void buildInorder (Node thisNode, StringBuilder toRet) {
        if (thisNode == null)
            return;
        buildInorder(thisNode.left, toRet);
        if (!toRet.toString().equals(""))
            toRet.append(", ");
        toRet.append(thisNode.value);
        buildInorder(thisNode.right, toRet);
    }

}
