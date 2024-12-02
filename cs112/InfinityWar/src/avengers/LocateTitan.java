package avengers;
import java.util.*;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

    	// read from locatetitan input file
        StdIn.setFile(args[0]);
        
        // make an array representation of generator functionalities, index = generator, value = functionality
        int numberOfGenerators = StdIn.readInt();
        double[] functionalitiesOfGenerators = new double[numberOfGenerators];

        // populate functionalities using StdIn
        for (int i = 0; i < numberOfGenerators; i++) {
            StdIn.readInt();
            double functionality = StdIn.readDouble();
            functionalitiesOfGenerators[i] = functionality;
        }
        
        // populate adjacency matrix using StdIn
        int[][] adjacencyMatrix = new int[numberOfGenerators][numberOfGenerators];
        for (int i = 0; i < numberOfGenerators; i++) {
            for (int j = 0; j < numberOfGenerators; j++) {
                adjacencyMatrix[i][j] = StdIn.readInt();
            }
        }

        // adjust matrix cost
        adjustMatrixCost(adjacencyMatrix, functionalitiesOfGenerators);
        
        // perform dijkstras from earth -> titan
        int[] earthToPlanets = dijkstras(adjacencyMatrix, 0);
        
        int earthToTitan = earthToPlanets[earthToPlanets.length - 1];

        StdOut.setFile(args[1]);
        StdOut.print(earthToTitan);

    }

   /**
    * adjustedCost = generatorCost/(functionality[i] * functionality[j])
    * @param adjacencyMatrix is the adjacency matrix to adjust
    * @param functionalitiesOfGenerators is the generator functionalities
    */
    private static void adjustMatrixCost(int[][] adjacencyMatrix, double[] functionalitiesOfGenerators) {
        
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[0].length; j++) {

                int generatorCost = adjacencyMatrix[i][j];
                double functionalitiesMultiplied = functionalitiesOfGenerators[i] * functionalitiesOfGenerators[j];
                double adjustedCost = generatorCost / functionalitiesMultiplied;
                adjacencyMatrix[i][j] = (int)adjustedCost;

            }
        }
        
    }

    private static int[] dijkstras(int[][] graph, int source) {

        int numberVertices = graph.length;
        int[] distances = new int[numberVertices];
        HashMap<Integer, Boolean> visited = new HashMap<>(numberVertices);
    
        // fill distances with max value
        // fill visited{i} to all false
        for (int i = 0; i < numberVertices; i++) {
            distances[i] = Integer.MAX_VALUE;
            visited.put(i, false);
        }
    
        // distance from source -> source is always 0
        distances[source] = 0;
    
        // loop until all vertices have been visited
        while (visited.containsValue(false)) {
    
            // find unvisited vertex with shortest distance from source
            int minDistance = Integer.MAX_VALUE;
            int minIndex = -1;
    
            for (int j = 0; j < numberVertices; j++) {
                if (visited.get(j) == false && distances[j] <= minDistance) {
                    minDistance = distances[j];
                    minIndex = j;
                }
            }
    
            // set the unvisited vertex with shortest distance from source as visited
            visited.put(minIndex, true);
    
            // update all adjacent node distances
            for (int j = 0; j < numberVertices; j++) {
                if (visited.get(j) == false && graph[minIndex][j] != 0) {
                    int newDistance = distances[minIndex] + graph[minIndex][j];
                    if (newDistance < distances[j]) {
                        distances[j] = newDistance;
                    }
                }
            }
        }
    
        return distances;
    
    }
}
