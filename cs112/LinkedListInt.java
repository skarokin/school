import java.util.NoSuchElementException;

/**
 * linked list of integers
 */
public class LinkedListInt {

    // nested private class (only visible inside this file of LinkedListInt.java) 
    private class Node {

        // the instance variables of the Node class 
        private int data;       // the data
        private Node next;      // the link

        Node (int dataToInsert, Node link) {
            data = dataToInsert;
            next = link;
        }

    }

    // the instance variables of the LinkedListInt class
    private Node front;     // a reference to the first node of the LL
    
    // constructor; an empty linked list
    LinkedListInt() {
        front = null;
    }

    /** 
     * inserts a Node to the beginning of the linked list
     * then changes front pointer from old front to new front
     * Running Time: O(1)
     */ 
    public void addToFront(int nodeData) {
        // use the private class Node() we created to construct a new node
        Node newNode = new Node(nodeData, front);   // our nodeData is put in data part, and our front pointer is put as the link
        front = newNode;        // changing front from old first value to new first value
    }

    /**
     * print function
     * use a new Node to point to front then update that new node.
     * because if you were to update "front", then you would lose the Node that front was pointing to
     * Running TIme: f(n) = n + 1 = O(n)
     * f(n) = n + 1 because there will be a case where pointer != null that you have to account for
     */
    public void print() {
        Node pointer = front;       // this pointer is pointing to the first node of the linked list 

        while (pointer != null) {
            System.out.print(pointer.data + " -> ");    
            pointer = pointer.next;     // update pointer to point to next node in the linked list 
        }
        System.out.print("\\");
    }

    /**
     * return true if target is in the LL
     * Best case: O(1), Worst case: O(n). 
     * Running Time: O(n);
     */
    public boolean search(int target) {
        Node pointer = front;
        while (pointer != null) {
            if (pointer.data == target) {
                return true;
            }
            pointer = pointer.next;
        }
        return false;
    }
    /**
     * change value of front to the next item
     * doing so removes any references to the original front, so the garbage collector removes it
     */
    public void deleteFront() { 
        if (front == null) {
            throw new NoSuchElementException("The object LinkedListInt is empty");
        }
        front = front.next;
    }

    /**
     * search for the target number to put newEntry after
     * Ex: 1->2->5->8->9, put in "6" after 5
     * in this case, 
     * 1. search if 5 exists
     * 2. make 6 have the address of 8 (aka make newNode.next = pointer.next)
     */
    public boolean insertAfter(int target, int dataToInsert) {
        
        Node pointer = front;

        while (pointer != null) {
            if (pointer.data == target) {
                // insert dataToInsert after the object that holds target 
                Node newNode = new Node(dataToInsert, pointer.next);
                pointer.next = newNode;
                return true;
            }
            pointer = pointer.next;
        }
        return false;

    }   

    /**
     * use a two pointer method to find target you want to delete
     * with one pointer behind the other at all times
     * point the pointer that's one step behind to pointer.next to remove any reference to 
     * the data you wish to delete    
     */
    public boolean deleteTarget(int dataToDelete) {

        Node pointer = front;
        Node previousPointer = null;   

        while (pointer != null) {
            if (pointer.data == dataToDelete) {

                if (pointer == front) {
                    front = front.next;
                } else {
                    previousPointer.next = pointer.next;
                }
                return true;

            }
            previousPointer = pointer;
            pointer = pointer.next;
        }
        return false;

    }

    // client method
    public static void main(String[] args) {

        LinkedListInt lli = new LinkedListInt();
        
        lli.addToFront(3);
        lli.addToFront(5);
        lli.addToFront(7);
        lli.addToFront(9);
        lli.addToFront(11);

        lli.print();

        System.out.println();

        lli.insertAfter(9, 3);

        lli.print();

    }

}
