import java.util.NoSuchElementException;

public class ResizingStackofStrings {
    
    private String[] stack;   // stack
    private int size = 0;     // number of tiems in the stack; also the index of top of the stack

    /**
     * Constructor:
     * Make a new stack of strings with length 1
     */
    public ResizingStackOfStrings() {
        stack = new String[1];
        // all positions will be null
    }

    /**
     * Check if stack is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Running Time: O(1)
     * Ensure that there is a conditional to check if stack overflow is occuring (if stack is full when push() is called)
     */
    public void push(String item) {
        if (size == stack.length) {
            resize(stack.length * 2);   // resize(stack.length + 1) = O(n^2); resize(stack.length * 2) = O(n)
        }
        stack[size] = item;
        size++;
    }

    /**
     * Running Time: O(n)
     * Resize by 2x because resizing by + 1 is O(n^2) time
     * @param capacity the size of new, resized array
     */
    private void resize(int capacity) {

        String[] copy = new String[capacity];
        
        for (int i = 0; i < size; i++) {
            copy[i] = stack[size];
        }

        stack = copy;

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
        size--;
        String item = stack[size];
        stack[size] = null;
        return item;
    }

    /**
     * Client code
     */
    public static void main(String[] args) {



    }


}
