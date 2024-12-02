/**
 * Node to be used for the BST.java class
 * K extends Comparable: means that the class that Key is MUST have the compareTo() method built in, or else Node wont work
 * This class keeps <Key, Value> pairs together in the BST
 */
public class Node <K extends Comparable<K>, V> {

    /**
     * INSTANCE VARIABLES
     */
    public K key;                   // key used for search
    public V value;                 // value associated with key 
    public Node<K, V> left;         // reference to left subtree
    public Node<K, V> right;        // reference to right subtree

    /**
     * Constructor for the Node of the BST
     * @param key is the key to be used, this value is used in searching and inserting
     * @param value is the value associated with the key in the <Key, Value> pair
     */
    Node(K key, V value) {

        this.key = key;
        this.value = value;
        left = null;
        right = null;

    }

}
