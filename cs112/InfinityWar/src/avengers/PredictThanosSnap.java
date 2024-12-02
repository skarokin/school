package avengers;

/**
 * Given an adjacency matrix, use a random() function to remove half of the nodes. 
 * Then, write into the output file a boolean (true or false) indicating if 
 * the graph is still connected.
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * PredictThanosSnapInputFile name is passed through the command line as args[0]
 * Read from PredictThanosSnapInputFile with the format:
 *    1. seed (long): a seed for the random number generator
 *    2. p (int): number of people (vertices in the graph)
 *    2. p lines, each with p edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note: the last p lines of the PredictThanosSnapInputFile is an ajacency matrix for
 * an undirected graph. 
 * 
 * The matrix below has two edges 0-1, 0-2 (each edge appear twice in the matrix, 0-1, 1-0, 0-2, 2-0).
 * 
 * 0 1 1 0
 * 1 0 0 0
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * Delete random vertices from the graph. You can use the following pseudocode.
 * StdRandom.setSeed(seed);
 * for (all vertices, go from vertex 0 to the final vertex) { 
 *     if (StdRandom.uniform() <= 0.5) { 
 *          delete vertex;
 *     }
 * }
 * Answer the following question: is the graph (after deleting random vertices) connected?
 * Output true (connected graph), false (unconnected graph) to the output file.
 * 
 * Note 1: a connected graph is a graph where there is a path between EVERY vertex on the graph.
 * 
 * Note 2: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, isConnected is true if the graph is connected,
 *   false otherwise):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(isConnected);
 * 
 * @author Yashas Ravi
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/PredictThanosSnap predictthanossnap.in predictthanossnap.out
*/

public class PredictThanosSnap {
	 
    public static void main (String[] args) {
 
        if ( args.length < 2 ) {
            StdOut.println("Execute: java PredictThanosSnap <INput file> <OUTput file>");
            return;
        }

        // intiialzie file to read from 
        StdIn.setFile(args[0]);

        // set seed
    	long seed = StdIn.readLong();

        // initialize number of people
        int numberPeople = StdIn.readInt();
        
        int[][] adjacencyMatrix = new int[numberPeople][numberPeople];

        // fill the adjacnency matrix
        for (int i = 0; i < numberPeople; i++) {
            for (int j = 0; j < numberPeople; j++) {
                adjacencyMatrix[i][j] = StdIn.readInt();
            }
        }

        StdRandom.setSeed(seed);
        
        // delete random person from adjacency matrix
        int numberVertices = numberPeople;
        for (int i = 0; i < numberVertices; i++) {
            for (int j = 0; j < numberVertices; j++) {
                if (StdRandom.uniform() <= 0.5) {
                    adjacencyMatrix[i][j] = 0;
                }
            }
        }

        // print the adjacnency matrix
        System.out.println("{");
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.out.print("{");
            for (int j = 0; j < adjacencyMatrix[0].length; j++) {
                if (j == adjacencyMatrix[0].length - 1) System.out.print(adjacencyMatrix[i][j]);
                else System.out.print(adjacencyMatrix[i][j] + ", "); 
            }
            System.out.print("},");
            System.out.println();
        }
        System.out.println("}");

        StdOut.setFile(args[1]);
        StdOut.print(isConnected(adjacencyMatrix));

    }
/*
    public static int[][] deleteVertex(int[][] adjacencyMatrix, int numberVertices) {
        int vertexToDelete = (int)(StdRandom.uniform() * numberVertices);

        int[][] newAdjacencyMatrix = new int[numberVertices-1][numberVertices-1];

        // copy data from old matrix to new matrix, skipping the row and column of the vertexToDelete
        int newRow = 0;

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (i == vertexToDelete) continue;
            int newCol = 0;

            for (int j = 0; j < adjacencyMatrix[0].length; j++) {
                if (j == vertexToDelete) continue;
                newAdjacencyMatrix[newRow][newCol] = adjacencyMatrix[i][j];
                newCol++;
            }
            newRow++;
        }

        return newAdjacencyMatrix;

    }
*/
    public static boolean isConnected(int[][] adjacencyMatrix) {
        int n = adjacencyMatrix.length;
        boolean[] visited = new boolean[n];

        // DFS from the first vertex with an edge
        // undirected, so if first vertex is not connected then no need to restart 
        int startVertex = 0;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[0].length; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    startVertex = i;
                    break;
                }
            }
        }

        DFS(startVertex, visited, adjacencyMatrix);   
        
        for (int i = 0; i < n; i++) {
            if (!visited[i]) return false;
        }

        return true;
    }

    public static void DFS(int v, boolean[] visited, int[][] adjacencyMatrix) {
        // Mark the current node as visited
        visited[v] = true;
        // Recursively traverse all vertices adjacent to this vertex
        for (int i = 0; i < adjacencyMatrix[v].length; i++) {
            if (adjacencyMatrix[v][i] == 1 && !visited[i]) {
                DFS(i, visited, adjacencyMatrix);
            }
        }
    }
}
