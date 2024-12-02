// <T> is a placeholder for the data type of the DLL 
// T is an ADT (Abstract Data Type). Cannot be a primitive data type
// Use wrapper classes like Integer, Character, etc. if you need a DLL of primitive data types 

public class DoublyLinkedList<T> {

    /**
     * Private Node class to define what a Node is for a doubly linked list
     * Only visible inside class DoublyLinkedList
     * Pointers: next and previous
     */
    private class Node<T>{
        T data; 
        Node<T> next;
        Node<T> prev;
    }

    private Node<T> first;     // reference (only a reference, not an actual Node) to first Node in the DLL
    private int size;      // stores the size of the DLL 

    /**
     * CONSTRUCTOR 
     * Set reference to first Node to null
     * Set size to 0
     */
    public DoublyLinkedList() {
        first = null;
        size = 0;
    }

    /**
     * RUNNING TIME: O(1)
     * Find the boolean value of size == 0
     * @return if DLL is empty, which is when size == 0
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * RUNNING TIME: O(1)
     * Adds data to the front (beginnning) of the DLL 
     * by pointing newNode to null and previous first Node (NULL -> 1 -> 2 -> 3 -> NULL)
     * and by pointing the old first.prev to newNode
     * Then, update the old first Node to be newNode because we want newNode to be the beginning!
     * Finally, update size of the DLL
     * @param data that is being added to the front (beginning) of the DLL
     */
    public void addToFront(T dataToInsert) {

        Node<T> newNode = new Node<>();
        newNode.data = dataToInsert;
        newNode.next = first;           
        newNode.prev = null;

        if (!isEmpty()) {               // necessary condition because if DLL is empty, then you would have newNode twice
            first.prev = newNode;       // So, adding to front of an empty DLL would just be making newNode the first Node
        }      
        first = newNode;

        size++;

    }

    /**
     * RUNNING TIME: O(n)
     * Prints the DLL
     * There's a pointer to null at the beginning AND the end
     */
    public void print() {
        
        System.out.print("NULL <- ");

        for (Node<T> ptrFirst = first; ptrFirst != null; ptrFirst = ptrFirst.next) {
            System.out.print(ptrFirst.data + " <-> ");
        }
        System.out.print("NULL");

    }

    /**
     * RUNNING TIME: O(n)
     * Inserts data after a target by 
     * 1. finding targetData using pointers
     * 2. point dataToInsert.next to targetData.next, and dataToInsert.prev to targetData  
     * @param dataToInsert is the data to insert after stringToInsertAfter
     * @param stringToInsertAfter  
     * @return if insertAfter was successful
     */
    public boolean insertAfter(T dataToInsert, T targetData) {
    
        Node<T> pointer = first;
        
        while (pointer != null && pointer.data != targetData) {
            pointer = pointer.next;
        }

        // Possibility 1: targetData not found, return false
        if (pointer == null) return false;

        // Possibility 2: targetData is found, return true and perform insertAfter operation
        Node<T> newNode = new Node<>();
        newNode.data = dataToInsert;
        newNode.next = pointer.next;
        newNode.prev = pointer;

        pointer.next = newNode;

        // Ensure that it's even possible to perform insertAfter (AKA targetData is not the last Node)
        // If possible, then assign the Node after nodeAfter to point to nodeAfter
        if (newNode.next != null) {
            newNode.next.prev = newNode;
        }

        size++;
        return true;

    }

    /**
     * RUNNING TIME: O(n)
     * Returns the index of targetData
     * @param targetData is the data to be searched for
     * @return the index of targetData in the DLL. If not found, return -1
     */
    public int search(T targetData) {

        Node<T> pointer = first;
        int counter = 0;

        while (pointer.next != null && pointer.data != targetData) {
            counter++;
            pointer = pointer.next;
        }

        // EDGE CASE: targetData not found
        if (pointer.data != targetData) {
            System.out.println("targetData not found!");
            return -1;
        }

        System.out.println("targetData found at index " + counter);
        return counter;

    }

    /**
     * RUNNING TIME: O(n)
     * Deletes targetData through the following steps
     * 1. iterate through the DLL until targetData is found 
     * 2. delete all references to the Node containing targetData by:
     *      a. making previous Node point to next Node
     *      b. making next Node point to previous Node 
     * @param targetData is the data to delete 
     * @return true if deleteTarget operation was successful, false otherwise 
     */
    public boolean deleteTarget(T targetData) {

        Node<T> pointerFront = first;
        Node<T> pointerLast = null;

        while (pointerFront != null && pointerFront != targetData) {

            if (pointerFront.data == targetData) {              
                if (pointerFront == first) {                  // EDGE CASE: if DLL is size 1, DLL = NULL
                    first = pointerFront.next;
                } else {
                    pointerLast.next = pointerFront.next;
                    pointerFront.next.prev = pointerLast;
                }
                return true;
            }

            pointerLast = pointerFront;
            pointerFront = pointerFront.next;
        }

       return false;

    }

    /**
     * CLIENT 
     */
    public static void main(String[] args) {

        DoublyLinkedList<String> dll = new DoublyLinkedList<>();
        dll.addToFront("Ref:rain");
        dll.addToFront("AM02:00");
        dll.addToFront("Modern Warfare");
        dll.addToFront("tone");

        dll.print();

        System.out.println();

        dll.insertAfter("Neon Ocean", "AM02:00");
        dll.print();

        System.out.println();

        dll.insertAfter("emulation", "Neon Ocean");
        dll.print();

        System.out.println();

        dll.deleteTarget("AM02:00");
        dll.print();

        System.out.println();

        DoublyLinkedList<String> dll2 = new DoublyLinkedList<>();
        dll2.addToFront("Waaa");
        dll2.print();
        System.out.println();
        dll2.deleteTarget("Waaa");
        dll2.print();

        System.out.println();
        dll.search("asdf");
        dll.search("Neon Ocean");

    }
    
}