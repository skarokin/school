import java.util.NoSuchElementException;

public class FixedCapacityStackOfStrings {
    
    private String[] stack;         // stack
    private int indexOfTop = 0;     // number of tiems in the stack; top of the stack

    /**
     * Constructor:
     * Make a new stack of strings with a fixed capacity
     * @param capacity
     */
    public FixedCapacityStackOfStrings(int capacity) {
        stack = new String(capacity);
        // all positions will be null
    }

    /**
     * Check if stack is empty
     */
    public boolean isEmpty() {
        return indexOfTop == 0;
    }

    /**
     * Running Time: O(1)
     * Ensure that there is a conditional to check if stack overflow is occuring (if stack is full when push() is called)
     */
    public void push(String item) {
        if (indexOfTop == stack.length) {
            throw new NoSuchElementException("Stack overflow!");
        }
        stack[indexOfTop] = item;
        indexOfTop++;
    }

    /**
     * Running Time: O(1)
     * Ensure that there is a conditional to check if stack underflow is occuring (stack is empty when pop() is called)
     * Make it so that there is no reference to an object that is popped, 
     */
    public String pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow!");
        }
        indexOfTop--;
        String item = stack[indexOfTop];
        stack[indexOfTop] = null;
        return item;
    }

    /**
     * Client code
     */
    public static void main(String[] args) {

        FixedCapacityStackOfStrings stack = new FixedCapacityStackOfStrings(4);

        StdOut.print("Enter items to be pushed onto stack");
        while (!StdIn.isEmpty()) {
            stack.push(StdIn.readString());
        }

        System.out.println("All items pushed on stack, pop from stack");

    }

}
