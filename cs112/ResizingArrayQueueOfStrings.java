import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

public class ResizingArrayQueueOfStrings {
    
    // initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 8;

    private String[] q;     // queue elements
    private int size;       // number of elements on queue
    private int first;      // index of first element on queue
    private int last;       // index of next avail slot

    /**
     * Initialize an empty queue 
     */
    public ResizingArrayQueueOfStrings() {
        q = new String[INIT_CAPACITY];
        size = 0;
        first = 0;
        last = 0;
    }

    /**
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return the number of items in the queue
     */
    public int size() {
        return size;
    }

    /**
     * Running Time: O(1) best case, O(n) worst case, where you need to resize
     * Adds the item to the queue
     * @param item the item to add
     */
    public void enqueue(String item) {
        if (size == q.length) {     // double size of array if necessary and recopy to front of array
            resize(2*q.length);     // ~2n = O(n)
        }   

        q[last] = item;             // add item
        last++;
        if (last == q.length) {     
            last = 0;               // wrap around
        }
        size++;
    }

    /**
     * Running Time: ~2n = O(n)
     * Resizes the underlying array
     * @param capacity is how big the resized array will be
     */
    private void resize(int capacity) {

        String[] copy = new String[capacity];
        for (int i = 0; i < capacity; i++) {
            copy[i] = q[(first + i) % q.length];    // you use (first + i) % q.length because...
        }
        q = copy;
        first = 0;
        last = size;

    }

    /**
     * Running Time: O(n) because dequeuing resizes the array 
     * Removes and returns the item on this queue that was least recently added
     * @return the item on this queue that was least recently added
     * @throws java.util.NoSuchElementException if queue is empty
     */
    public String dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        
        String item = q[first];
        q[first] = null;            // to avoid loitering
        size--;
        first++;

        if (first == q.length) {
            first = 0;              // wrap-around
        }

        // shrinks size of queue if necessary
        // only shrink if queue is 1/4 full as to maintain O(n) resize time
        if (size > 0 && size == q.length / 4) {
            resize(q.length / 2);
        }
        return item;

    }

    /**
     * Returns the item least recently added to this queue
     * @return the item least recently added to this queue
     * @throws java.util.NoSuchElementException if queue is empty
     */
    public String peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }
        return q[first];
    }

    /**
     * Client code
     */
    public static void main(String[] args) {

        ResizingArrayQueueOfStrings queue = new ResizingArrayQueueOfStrings();


    }

}
