/**
 * BST implementation, both recursive and iterative methods will be used
 * 
 * K is a generic data type for the key
 *      "K extends Comparable<K>" means that K is a class that MUST have a compareTo method implemented
 *          This means that the class K must implement Comparable
 *      Comparable is a class that is an interface that has one method called compareTo. We are extending this interface.
 *      This means that the Key object MUST have the compareTo method implemented in
 *      its own object so that the Key can be compared to other objects
 * V is a generic data type for the value 
 */
public class BST<K extends Comparable<K>, V> {

    /**
     * INSTANCE VARIABLES
     */
    private Node root;              // the root of the BST
    private int size;               // the number of items in the BST

    /**
     * DEFAULT CONSTRUCTOR
     */
    BST() {
        root = null;
        size = 0;
    }

    /**
     * RUNNING TIME:
     *      O(logn) if balanced, as this is just binary searching
     *      O(n) if unbalanced, as this is essentially searching through a linked list 
     * Searches iteratively for the targetKey and returns the value associated with that key 
     * if compareTo returns > 0, the first item is larger
     * if compareTo returns < 0, the first item is smaller 
     * if compareTo returns = 0, the two items are the same 
     *      The reason the compareTo method is used is because we extended the Comparable on the Key, meaning that Java
     *      expects that the object of the Key has the compareTo method built in to it! If you don't use the 
     *      compareTo method, you will get an error
     * @param targetKey is the key we are looking for
     * @return the value of the <targetKey, Value> pair. Return null if the key is not found 
     */
    public V get(K targetKey) {

        Node<K, V> pointer = root;

        while (pointer != null) {
            if (targetKey.compareTo(pointer.key) > 0) {
                pointer = pointer.right;
            }
            if (targetKey.compareTo(pointer.key) < 0) {
                pointer = pointer.left;
            }
            if (targetKey.compareTo(pointer.key) == 0) {
                return pointer.value;
            }
        }

        // EDGE CASE: targetKey not found
        return null;

    }

    /**
     * RUNNING TIME: O(n) OR O(logn)
     *      O(logn) if balanced, as this is just binary searching
     *      O(n) if unbalanced, as this is essentially inserting in a linked list 
     * Inserts according to BST rules
     *      Keep going through the BST until pointer is null
     *      2 pointers: one pointer on track, one pointer just behind 
     *      pointerPrev is used to make sure that newNode is placed in the last spot in the BST
     *          Maintain the value of the last comparison that was done before pointer == null, which is used
     *          to determine whether insertion point should be to the left or right, according to BST rules
     *      If inputKey is already in the BST, DO NOT insert! Just update the value. NO DUPLICATES ALLOWED IN BST 
     * @param inputKey is the key to be inputted 
     * @param inputValue is the value to be inputted
     */
    public void put(K inputKey, V inputValue) {

        // EDGE CASE: tree is empty 
        if (root == null) {
           Node<K, V> newNode = new Node<>(inputKey, inputValue);
           root = newNode;
           return;
        }

        Node<K, V> pointer = root;
        Node<K, V> pointerPrev = null;

        int cmp = 0;

        while (pointer != null) {
            cmp = inputKey.compareTo(pointer.key);

            // EDGE CASE: inputKey is already present in the BST, so simply update the value of the key and return
            if (cmp == 0) {
                pointer.value = inputValue;     
                return;     
            }

            pointerPrev = pointer;

            if (cmp > 0) {
                pointer = pointer.right;
            } else {
                pointer = pointer.left;
            }
          
        }

        Node<K, V> newNode = new Node<>(inputKey, inputValue);

        // cmp holds a value > 0 if pointer went right, OR < 0 if pointer went left 
        if (cmp < 0) {
            pointerPrev.left = newNode;     // if most recent comparison showed that pointer < 0, insertion point is left
        } else {
            pointerPrev.right = newNode;    // if most recent comparison showed that pointer > 0, insertion point is right
        }

        size++;

    }

    /**
     * THIS IS A HELPER METHOD FOR private Node<K, V> deleteMinimum(Node <K, V> head)
     * 
     * Finds the minimum value from a starting point, called head
     * Recursive algorithm to find the minimum
     *      BASE CASE: If there isn't a left node, we have reached the leftmost bottommost node.
     *                 Return this leftmost bottommost node!
     *      RECURSIVE STEP: Keep iterating through the tree!
     * @param head is the starting Node
     * @return the Node that contains the minimum key 
     */
    private Node<K, V> minimum(Node<K, V> head) {

        if (head.left == null) return head;

        return minimum(head.left);
    }    

    /**
     * THIS IS A HELPER METHOD FOR public void deleteMinimum()
     * 
     * RUNNING TIME:
     *      O(logn) in balanced
     *      O(n) in unbalanced
     * Recursive algorithm to delete the minimum
     *      BASE CASE: If there isn't a left node, we have reached the leftmost bottommost node. The node to the right
     *                 of this is the minimum node. Return this!
     *      RECURSIVE STEP: Keep iterating through the tree!
     * @param head is the starting pint
     * @return the value of the minimum key
     */
    private Node<K, V> deleteMinimum(Node<K, V> head) {

        if (head.left == null) {
            return head.right;          // return head.right because 
        }
        head.left = deleteMinimum(head.left);
        return head;

    }

    /**
     * Deletes the smallest value in the BST
     * RUNNING TIME:
     *      O(logn) in balanced
     *      O(n) in unbalanced
     * Relies on a helper method called deleteMinimum, which relies on a helper method called minimum
     */
    public void deleteMinimum() {
        root = deleteMinimum(root);
    }

    /**
     * RUNNING TIME: O(n) because we enqueue the same amount as number of keys
     * 1. Start at the root
     * 2. Go left until you can't go left anymore... when there is no more left, print that left item
     * 3. Then, go right. Keep going left until you can't go left anymore, print that left item
     * 
     * BASE CASE: if x is null, return to the caller
     * RECURSIVE STEP: 
     *      call inorderTraversal again, to the left until you cannot go left anymore
     *      once you cannot go left anymore, enqueue the root of the subtree
     *      call inorderTraversal to the right, THEN go left until you cannot go left anymore
     * Go left to root to right
     * @param x is the root node
     * @param q is the 
     */
    public void inorderTraversal(Node<K, V> x, LinkedQueue<K> q) {

        if (x == null) return;
        
        inorderTraversal(x.left, q);
        q.enqueue(x.key);       // counted towards run time
        inorderTraversal(x.right, q);
        return;
        
    }

    /**
     * RUNNING TIME: O(n) because we enqueue the same amount as number of keys
     *      Go root to left to right
     * @param x
     * @param q
     */
    public void preorderTraversal(Node<K, V> x, LinkedQueue<K> q) {

        if (x == null) return;
        q.enqueue(x.key);       // counted towards run time
        preorderTraversal(x.left, q);
        preorderTraversal(x.right, q);
        return;

    }

    /**
     * RUNNING TIME: O(n) because we enqueue the same amount as number of keys
     *      Go left to right to root
     * @param x
     * @param q
     */
    public void postorderTraversal(Node<K, V> x, LinkedQueue<K> q) {

        if (x == null) return;
        postorderTraversal(x.left, q);
        postorderTraversal(x.right, q);
        q.enqueue(x.key);       // counted towards running time

    }

    /**
     * RUNNING TIME: 
     * @param x root 
     * @param target target to delete
     * @return
     */
    public Node<K, V> delete(Node<K, V> x, K target) {

        if (x == null) return null;

        int cmp = target.compareTo(x.key);

        if (cmp < 0) {          // target is to left
            x.left = delete(x.left, target);    
        } else if (cmp > 0) {   // target is to right
            x.right = delete(x.right, target);
        } else {                // target found
            Node<K, V> t = x;
            // case 2: (takes care of case 1: deletes a leaf)
            // case 2 is that you delete a node with one child... replace it with its only child 
            if (t.right == null) return t.left;     // no right child
            if (t.left == null) return t.right;    // no left child

            // case 3: two children... replace with either predecessor OR successor (replace with successor for this one)
            x = minimum(t.right);       // find the successor of t: that is the minimum key on t's subtree
                                        // delete minimum of this right subtree and replace our t into that 
            // the successor x is going to be put into t's place
            // hook up to the right of x the entire t's right subtree (have to delete x)
            x.right = deleteMinimum(t.right);      // delete successor of t, which is x
            x.left = t.left;                        // hook up to the left of x t's left subtree

        }
        return x;       // return x so that it can be hooked up to its parent

    }

    /**
     * CLIENT
     */
    public static void main(String[] args) {

        BST<NetID, Student> studentRecords = new BST<>();
        studentRecords.put(new NetID("10001430"), new Student("Sean", 2026));
        studentRecords.put(new NetID("10001774"), new Student("David", 2025));
        studentRecords.put(new NetID("10001356"), new Student("Honoka", 2023));

        studentRecords.get(new NetID("10001430"));

        System.out.println(studentRecords.minimum(studentRecords.root).key);

        LinkedQueue<NetID> queue = new LinkedQueue<>();
        studentRecords.inorderTraversal(studentRecords.root, queue);

        while (!queue.isEmpty()) {
            NetID key = queue.dequeue();
            System.out.print("key: " + key.t    oString());
            System.out.println(", value: " + studentRecords.get(key));
        }
        
    }

}
