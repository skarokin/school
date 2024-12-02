public class WeightedQuickUnionUF {
    
    // array stores the information that 2 vertices are connected
    // vertices that are connected have the same root
    private int[] parent;
    private int[] size;

    // initailizes the data structure
    // how many array accesses? O(n)
    public WeightedQuickUnionUF(int n) {

        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {

            parent[i] = i;
            size[i] = i;

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

    // connect vertices of p and q
    // by linking the root of smaller tree to root of larger tree
    // running time; how many array accesses? 
    public void union(int p, int q) {

        int rootP = find(p);
        int rootQ = find(q);

        // return if p and q have the same roots
        if (rootP == rootQ) return;

        // assume size of rootP is smaller than size of rootQ
        int rootSmaller = rootP, rootLarger = rootQ;

        // verify whether size of rootP is smaller than size of rootQ or not
        if (size[rootP] >= size[rootQ]) {

            rootSmaller = rootQ;
            rootLarger = rootP;

        }

        // link root of smaller to root of larger
        parent[rootSmaller] = rootLarger;

        // update the size
        size[rootLarger] += size[rootSmaller]; 
        
    }

    // client code
    public static void main (String[] args) {

        WeightedQuickUnionUF wqf = new WeightedQuickUnionUF(10);
        wqf.union(0, 2);
        wqf.union(3, 9);
        wqf.union(9, 10);

        boolean checkConnection = wqf.find(3) == wqf.find(9);
        System.out.println("Are 3 and 9 connected? " + checkConnection);

    }

}
