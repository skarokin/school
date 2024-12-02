public class QuickUnionUF {
    
    // array stores the information that two vertices are connected
    // vertices that are connected hav the same root
    private int[] parent;

    // initializes data structure
    // running time; how many array accesses?
    public QuickUnionUF(int n) {

        parent = new int[n];
        for (int i = 0; i < n; i++) { 
            parent[i] = n;
        }

    }

    // returns the representative of the set of vertices that contains p
    // by returning the root
    // the root is the vertex that is its own parent
    // running time = f(n) = 2n = O(n)
    public int find(int p) {
        
        while (p != parent[p]) {   // 1 read
            p = parent[p];         // 1 read
        }
        return p;

    }

    // connect vertices p and q
    // by changing the root of p to the root of q
    // rnning time; how many array accesses? f(n) = n + n + 1 = O(n)
    public void union(int p, int q) {

        int rootP = find(p);  // get root of P
        int rootQ = find(q);  // get root of Q

        parent[rootP] = rootQ;  // 1 write 

    }

}
