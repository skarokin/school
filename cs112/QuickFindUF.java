public class QuickFindUF {
    
    // vertices that are connected have the same id
    private int[] id;

    // initialize the data structure
    // running time: how many arry accesses? f(n) = n => O(n)
    public QuickFindUF(int n) {

        // initialize an array of n vertices
        id = new int[n]; 

        // set id of each vertex to itself
        for (int i = 0; i < n; i++){
            id[i] = i; // this is an array write 
        }

    }

    // return the representative of the set of vertices that contain p
    // by returning id of p
    // running time; how many array accesses? f(n) = 1 = O(1) 
    public int find(int p) { 
        
        // 1 read
        return id[p];

    }

    // connet vertices p and q
    // by changing all entries with id[p] to id[q]
    // running time; how many array accesses? f(n) = 2 + n + n - 1 = 2n + 1 = ~2n = O(n)
    public void union(int p, int q) {  

        int pid = id[p];  // this is a read because you read the value of id[p] and store as pid
        int qid = id[q];  // this is a read because you read the value of id[q] and store as qid

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {  // this is read -> n reads are being performed 
                id[i] = qid;     // this is a write -> n-1 writes because you dont update id[q], only id[p]
            }
        }

    }

    // client code
    public static void main (String[] args){



    }

}
