
// Generic Linked Queue
public class LinkedQueue<T> {
    
    private Node<T> first; // reference to the first node in the linked list
    private Node<T> last;  // reference to the last node in the linked list
    private int  size;

    // private inner class only visible within LinkedStackOfStrings
    private class Node<T> {
        private T item;    // data
        private Node<T>   next;    // link to next Node in the linked list
    }
    
    public int getSize() {
        return size;
    }
    
    public boolean isEmpty() {
        return first == null;
    }
    
    // Running time? O(1)
    public void enqueue(T item) {
        
        Node<T> oldLast = last; // reference to current last node
        
        last = new Node<T>(); // instantiate a new Node object
        last.item = item;  // data
        last.next = null;  // next will point nowhere because it is the last node

        if ( isEmpty() ) {
            // first node to be added to the list
            first = last; 
        } else {
            oldLast.next = last;
        }         
        size += 1;  
    }
    
    // Running time? O(1)
    public T dequeue() {

        T item = first.item; // save the item to return
        first  = first.next; // update first
        
        if (isEmpty()) last = null;    // last node to be removed from the list
        size -= 1;
        return item;
    }

    public static void main (String[] args) {
        
        LinkedQueue<String> q = new LinkedQueue<String>();
        q.enqueue("pear");
        q.enqueue("apple");
        q.enqueue("banana");
        q.enqueue("kiwi");

        while ( !q.isEmpty() ) {
            System.out.println(q.dequeue());
        }

        LinkedQueue<Integer> qI = new LinkedQueue<Integer>();
        qI.enqueue(1);
        qI.enqueue(2);
        qI.enqueue(3);

        while ( !qI.isEmpty() ) {
            System.out.println(qI.dequeue());
        }
    }
}
