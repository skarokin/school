/**
 * This PQ class is an array implementation of a priority queue
 * This can either be a max priority queue or a min priority queue, dependent on the user's needs
 */
public class PQ <Key extends Comparable<Key>> {

    public static final int MAX_PQ = 1;     // Max_PQ = LARGEST value is at the root
    public static final int MIN_PQ = 2;     // Min_PQ = SMALLEST value is at the root

    private Key[] pq;                       // this is the array implementation of a priority queue
    private int numberOfItems;              // number of items in the priority queue
    private int mode;                       // determines whether we are implementing a Max_PQ or a Min_PQ
    
    /**
     * CONSTRUCTOR
     * @param capacity is the user-inputted capacity of the priority queue
     * @param mode is 1 for Max_PQ, 2 for Min_PQ
     */
    public PQ (int capacity, int mode) {
        pq = (Key[]) new Comparable[capacity + 1];
        this.mode = mode;
        numberOfItems = 0;
    }

    /**
     * Returns whether the priority queue is empty
     * @return true if the priority queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return numberOfItems == 0;
    }

    /**
     * RUNNING TIME: O(logn)
     * Inserts the key at the first open position (which is the end of the array)
     *      swim() is a helper method that allows the inserted key to "swim up" the priority queue
     *      to maintain proper Max_PQ or Min_PQ order
     * @param key is the key to insert
     */
    public void insert(Key key) {
        numberOfItems +=1 ;
        pq[numberOfItems] = key;
        swim(numberOfItems);
    }

    /**
     * RUNNING TIME: O(logn)
     * Deletes the highest priority item (will ALWAYS be the item at the root)
     *      exchange() is a helper method that exchanges the first (to be deleted) key and the last (new root) key
     *      to maintain proper Max_PQ or Min_PQ order
     *      sink() is a helper method that allows the new root key to "sink down" the priority queue 
     *      to maintain proper Max_PQ or Min_PQ order
     *          Exchange the root and the last item in the priority queue because this method removes the root. 
     *          SOMETHING must become the root, so the last item is used to prevent needing to shift the array 
     * @return the deleted key
     */
    public Key delete() {

        Key itemToDelete = pq[1];
        exchange(1, numberOfItems);     
        numberOfItems -= 1;
        sink(1);
        pq[numberOfItems + 1] = null;
        return itemToDelete;

    }

    /**
     * --> HELPER METHOD FOR INSERTION <--
     * RUNNING TIME: O(logn), because index of k is being divided by 2 (go up the tree from child to parent)
     * Makes the parameter key "swim up" the priority queue as needed to maintain Max_PQ or Min_PQ order
     *      exchange() is a helper method that ensures that the priority queue remains either a Max_PQ or a Min_PQ
     * @param k is the index of the item to "swim up"
     */
    private void swim(int k) {

        if (mode == MAX_PQ) {
            // Max_PQ: parent is greater than or equal to children
            while (k > 1 && pq[k/2].compareTo(pq[k]) < 0) {
                exchange(k, k/2);       // while parent (index k/2) < child (index k), exchange. If k == 1, then it is the largest
                k = k/2;
            }
        } else {
            // Min_PQ: parent is less than or equal to children
            while (k > 1 && pq[k/2].compareTo(pq[k]) > 0) {
                exchange(k, k/2);       // while parent (index k/2) > child (index k), exchange. If k == 1, then it is smallest
                k = k/2;
            }
        }

    }

    /**
     * --> HELPER METHOD FOR DELETION <--
     * RUNNING TIME: O(logn), because the index of k is being multipled by 2 (go down the tree from parent to child)
     * Makes the parameter key "sink down" the priority queue as needed to maintain Max_PQ or Min_PQ order
     *      exchange() is a helper method that ensures that the priority queue remains either a Max_PQ or a Min_PQ
     * @param k is the index of the item to "sink down"
     */
    private void sink(int k) {

        if (mode == MAX_PQ) {
            // Max_PQ: parent is greater than or equal to children
            while (2*k <= numberOfItems) {      // while parent (index k) has an existing left child, do the below operations 
                int child = 2*k;                                                            // assume left child is larger
                if (child < numberOfItems && pq[child].compareTo(pq[child + 1]) < 0) {      // if right child exists, and
                    child += 1;                                                             // is larger, then child += 1
                }
                // compare the largest child against the parent
                if (pq[k].compareTo(pq[child]) >= 0) {          // break if parent (index k) is >= child (index 2k or 2k + 1)
                    break;                                      // because this is now in correct Max_PQ order
                }
                // child is greater than the parent. So, exchange the two and continue down iterating the priority queue
                exchange(k, child);
                k = child;
            }
        } else {
            // Min_PQ: parent is less than or equal to children
            while (2*k <= numberOfItems) {      // while parent (index k) has an existing left child, do the below operations
                int child = 2*k;                                                            // assume left child is smaller
                if (child < numberOfItems && pq[child].compareTo(pq[child + 1]) > 0) {      // if right child exists, and
                    child += 1;                                                             // is smaller, then child += 1
                }
                // compare the smallest child against the parent
                if (pq[k].compareTo(pq[child]) <= 0) {          // break if parent (index k) is <= child (index 2k or 2k + 1)
                    break;                                      // because this is now in correct Min_PQ order
                }
                // child is less than the parent. So, exchange the two and continue iterating down the priority queue
                exchange(k, child);
                k = child;
            }
        }

    }

    /**
     * --> HELPER METHOD FOR INSERTION, DELETION, SINK, AND SWIM <--
     * Exchanges ith position in the array with the jth position in the array 
     * @param i is the first index to exchange
     * @param j is the second index to exhange
     */
    private void exchange(int i, int j) {

        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;

    }

    /**
     * CLIENT METHOD
     */
    public static void main(String[] args){

        

    }

}
