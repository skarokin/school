public class LinearProbingHashTable<Key, Value> {

    private int size;                    // hash table size     
    private int numKVPairs;              // number of key-value pairs in the hash table
    private Value[] vals;
    private Key[] keys;

    /**
     * CONSTRUCTOR
     * @param capacity is the initial capacity
     */
    LinearProbingHashTable(int capacity) {
        size = capacity;
        
        // below is an ugly cast to handle generic types
        vals = (Value[]) new Object[size];
        keys = (Key[]) new Object[size];
    }

    /**
     * RUNNING TIME: O(1)
     * This is the hash function
     * @param key is the key to make a hash for
     * @return the integer representation of the key 
     */
    public int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % size;
    }    

    /**
     * RUNNING TIME: O(1)
     *      IN VERY RARE OCCURRENCE, O(n)
     * This is the getter function
     * @param key is the key to find the value of
     * @return the value of the key 
     */
    public Value get(Key key) {

        // we have to set i = hash(key) and go keys[i] != null because... ?
        // we have to use i = (i + 1) % size just in case we have to wrap around, e.g. size = 6, i = 6, so wrap to 0
        for (int i = hash(key); keys[i] != null; i = (i + 1) % size) {
            if (key.equals(keys[i])) return vals[i];        // found the key we are looking for
        }
        return null;

    }

    /**
     * RUNNING TIME: O(1) 
     *      IN VERY RARE OCCURRENCE, O(n)
     * This is the insert function
     * @param key is the key to insert into the hash table
     * @param val is the value to insert into the hash table
     */
    public void put(Key key, Value val) {

        if (numKVPairs >= size / 2) resize(2*size);

        // we have to set i = hash(key) and go to keys[i] != null because... ?
        // we have to use i = (i + 1) % size just in case we have to wrap around, e.g. size = 6, i = 6, so wrap to 0
        int i = hash(key);
        for (i = hash(key); keys[i] != null; i = (i + 1) % size) {
            if (keys[i].equals(key)) break;
        }
        keys[i] = key;
        vals[i] = val;

    }

    /**
     * RUNNING TIME: O(n)
     * Resize if load factor surpasses threshold 
     * @param capacity is the amount to resize our array by 
     */
    private void resize(int capacity) {
        LinearProbingHashTable<Key, Value> table = new LinearProbingHashTable<>(capacity);

        for (int i = 0; i < size; i++) {
            if (keys[i] != null) table.put(keys[i], vals[i]);
        }
        keys = table.keys;
        vals = table.vals;
        size = table.size;
    }

    /**
     * RUNNING TIME: O(1)
     *      IN VERY RARE OCCURRENCE, O(n)
     * Implement delete by yourself
     * @param key is the key to delete
     */
    public void delete(Key key) {

    }

    public static void main(String[] args) {

        LinearProbingHashTable<Point, Point> graph = new LinearProbingHashTable<>(10);

        Point a = new Point(1, 10);
        Point b = new Point(5, 18);
        Point c = new Point(1, 5);
        Point d = new Point(5, 15);
        Point e = new Point(3, 5);
        Point f = new Point(8, 5);

        // insert into the hash table
        graph.put(a, b);
        graph.put(c, d);
        graph.put(e, f);

        System.out.println(graph.get(e));
        System.out.println(graph.get(c));
        
    }

}
