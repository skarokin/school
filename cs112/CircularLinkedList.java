public class CircularLinkedList<T> {

    private class Node<T> {
        T data;
        Node<T> next;
        
    }

    private Node<T> last;       // pointer to the last node in the CLL; last.next is the first node in the CLL
    int size;

    /**
     * Constructor
     */
    public CircularLinkedList() {
        last = null;
        size = 0;
    }

    /**
     * check if last == null because that is how CLL works; empty if last does not circle back
     * @return if last == null
     */
    public boolean isEmpty() {
        return last == null;
    }

    /**
     * RUNNING TIME: O(1)
     * Adds data to the front of the CLL
     * @param dataToInsert the data that is to be inserted to the front (beginning) of CLL
     */
    public void addToFront(T dataToInsert) {

        Node<T> newNode = new Node<>();
        newNode.data = dataToInsert;
        
        // if empty, must make the CLL point to itself
        // else, make sure newNode.next points to last.next, and last.next points to newNode, making the LL circular
        if (isEmpty()) {
            newNode.next = newNode;         // newNode points to itself
            last = newNode;                 // newNode is the last node
        } else {
            newNode.next = last.next;       // newNode points to the old first
            last.next = newNode;            // newNode is the new first
        }

        size++;

    }

    /**
     * RUNNING TIME: O(n)
     * Prints the CLL
     * Little tricky; start at front and print until youre at front again
     */
    public void print() {

        if (isEmpty()) {
            System.out.println("Empty!");
            return;
        }

        Node<T> pointerFirst = last.next;       // pointerFirst points to the first Node 
        
        // DO the first iteration WHILE we are not back at the front 
        do {
            System.out.print(pointerFirst.data + " -> ");
            pointerFirst = pointerFirst.next;
        } while (pointerFirst != last.next);

    }

    /**
     * RUNNING TIME: O(1)
     * Make the last Node point to the second Node
     * 1 -> 2 -> 3 -> 4 
     * ^--------------^
     * removeFront()
     * 1 -> 2 -> 3 -> 4
     *      ^---------^
     */
    public void removeFront() {

        if (isEmpty()) {
            return;
        }

        if (last == last.next) {
            last = null;
        }

        last.next = last.next.next;
        size--;

    }

    public void deleteLast(T data) {

        // EDGE: there is only one node
        if (last == last.next) {
            if (last.data == data) {
                last = null;
            } else {
                return;
            }
        } 

        Node<T> ptr = last.next;
        Node<T> slow = null;
        Node<T> fast = null;

        // with this slow-fast implementation, it skips the first node. So, check it outside the loop
        if (ptr.data == data) {
            slow = ptr; 
            fast = ptr.next;
        }

        while (ptr.next != last.next) {
            if (ptr.next.data == data) {        // update pointer everytime our target is found 
                slow = ptr;
                fast = ptr.next;
            }
            ptr = ptr.next;
        }

        // EDGE: last node
        if (fast == last) {
            slow.next = fast.next;
            last = slow;
        }

        // EDGE: first node
        if (slow == last.next) {
            last.next = slow.next;
        }

        // EDGE: target not found
        if (slow == null) return;
        
        slow.next = fast.next;

    }
    /**
     * CLIENT 
     */
    public static void main(String[] args) {

        CircularLinkedList <Integer> cll = new CircularLinkedList<>();

        cll.addToFront(3);
        cll.addToFront(5);
        cll.addToFront(7);
        cll.addToFront(7);
        cll.addToFront(7);
        cll.addToFront(7);
        cll.addToFront(3);
        cll.addToFront(2);

        cll.print();

        System.out.println();

        cll.deleteLast(3);

        cll.print();

    }

}
