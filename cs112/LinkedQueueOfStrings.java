public class LinkedQueueOfStrings {
    
    private Node first;     // reference to the first Node in the LL
    private Node last;      // reference ot the last Node in the LL

    /**
     * Private inner class to define Node
     * only visible within class LinkedQueueOfStrings
     */
    private class Node {
        private String item;        // data
        private Node next;          // link to the next Node in the LL
    }

    /**
     * Running Time: O(1)
     * @return if the linked list is null 
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Running Time: O(1)
     * enqueue at the end of the list to maintain FIFO order  
     * @param item denotes the data to add to the queue
     */
    public void enqueue(String item) {

        Node oldLast = last;    // reference to current last node

        last = newNode<T>();   // instantiate a new Node object
        last.item = item;   // data
        last.next = null;   // next will point nowhere, as it is the last node 

        if (isEmpty()) {
            first = last;           // first node to be added to the list 
        } else {
            oldLast.next = last;    // point the next pointer of oldLast to Node last
        }

    }

    /**
     * Running Time: O(1)
     * dequeue from the beginning of the list 
     * @return the String that was the first to be enqueued
     */
    public String dequeue() {
        String item = first.item;   // save item to return
        first = first.next;         // update first

        if (isEmpty()) last = null; // last node to be removed from the list 
        return item;
    }

    /**
     * Client Code
     */
    public static void main(String[] args) {

        

    }

}
