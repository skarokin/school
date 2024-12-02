import java.util.NoSuchElementException;

public class LinkedStackOfStrings {

    private Node first = null;      // front of list

    // nested private class seen ONLY inside this file
    private class Node {
        private String item;
        private Node next;
    }

    /**
     * @return whether stack is empty or not, AKA if there is no "first" node
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Running Time: O(1)
     * Same as addToFront()
     * Overflow cannot happen with this implementation.
     * Make a new Node object for the old first node then make first a new Node
     * then fill in the data and next pointer for this new first 
     * @param item which is pushed to the front of the LL
     */
    public void push(String item) {

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;

    }

    /**
     * Running Time: O(1)
     * Underflow happens with this implementation; if front points to null, and you try to add an item to null
     * you get NullPointerException
     * @return the popped String 
     */
    public String pop() {

        // what happens when stack is empty? Stack underflow!
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow! Stack is empty");
        }

        String item = first.item;
        first = first.next;         // first now points to the second node 
        return item;

    }
    
    /**
     * Client code
     */
    public static void main(String[] args) {

        LinkedStackOfStrings stack = new LinkedStackOfStrings();
        stack.push("jacket");
        stack.push("pants");
        stack.push("shirt");
        stack.push("socks");

        String item = null;
        int n = 4;
        
        // try: code that MIGHT throw an exception
        // catch: if exception is thrown, catch it 
        try {
            while (n > 0) {
                item = stack.pop();
                System.out.println("Popped item is: " + item);
                n--;
            }
        } catch (NoSuchElementException exception) {
            System.out.println(exception.getMessage());
        }    

    }

}
