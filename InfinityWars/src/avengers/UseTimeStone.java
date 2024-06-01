package avengers;
import java.util.*;

/**
 * Given a starting event and an Adjacency Matrix representing a graph of all possible 
 * events once Thanos arrives on Titan, determine the total possible number of timelines 
 * that could occur AND the number of timelines with a total Expected Utility (EU) at 
 * least the threshold value.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * UseTimeStoneInputFile name is passed through the command line as args[0]
 * Read from UseTimeStoneInputFile with the format:
 *    1. t (int): expected utility (EU) threshold
 *    2. v (int): number of events (vertices in the graph)
 *    3. v lines, each with 2 values: (int) event number and (int) EU value
 *    4. v lines, each with v (int) edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note 1: the last v lines of the UseTimeStoneInputFile is an ajacency matrix for a directed
 * graph. 
 * The rows represent the "from" vertex and the columns represent the "to" vertex.
 * 
 * The matrix below has only two edges: (1) from vertex 1 to vertex 3 and, (2) from vertex 2 to vertex 0
 * 0 0 0 0
 * 0 0 0 1
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * UseTimeStoneOutputFile name is passed through the command line as args[1]
 * Assume the starting event is vertex 0 (zero)
 * Compute all the possible timelines, output this number to the output file.
 * Compute all the posssible timelines with Expected Utility higher than the EU threshold,
 * output this number to the output file.
 * 
 * Note 2: output these number the in above order, one per line.
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for total number of timelines
 *     //Call StdOut.print() for number of timelines with EU >= threshold EU 
 * 
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/UseTimeStone usetimestone.in usetimestone.out
 * 
 * @author Yashas Ravi
 * 
 */

public class UseTimeStone {

    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java UseTimeStone <INput file> <OUTput file>");
            return;
        }

    	// WRITE YOUR CODE HERE
        StdIn.setFile(args[0]);
        int thresholdEU = StdIn.readInt();
        int numEvents = StdIn.readInt();

        int[][] eventEU = new int[numEvents][2];

        for (int i = 0; i < eventEU.length; i++) {
            for (int j = 0; j < eventEU[i].length; j++) {
                eventEU[i][j] = StdIn.readInt();
            }
        }

        int[][] arrAdj = new int[numEvents][numEvents];

        for (int i = 0; i < arrAdj.length; i++) {
            for (int j = 0; j < arrAdj[i].length; j++) {
                arrAdj[i][j] = StdIn.readInt();
            }
        }

        int[][] arrAdjEU = new int[numEvents][numEvents];
        for (int i = 0; i < arrAdjEU.length; i++) {
            for (int j = 0; j < arrAdjEU[i].length; j++) {
                arrAdjEU[i][j] = arrAdj[i][j];
            }
        }

        for (int i = 0; i < arrAdjEU.length; i++) {
            for (int j = 0; j < arrAdjEU[i].length; j++) {
                if (i == eventEU[i][0]) {
                    arrAdjEU[i][j] = eventEU[j][1];
                }
            }
        }

        // number of vertices in the graph
        int n = arrAdj.length;

        // create a matrix to hold the number of paths between each pair of vertices
        int[][] pathCounts = new int[n][n];

        // initialize the path counts for each pair of vertices to be the same as the adjacency matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                pathCounts[i][j] = arrAdj[i][j];
            }
        }

        // count the number of paths between each pair of vertices using the Floyd-Warshall algorithm
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    pathCounts[i][j] = pathCounts[i][j] + pathCounts[i][k] * pathCounts[k][j];                    
                }
            }
        }

        // print the number of paths between each pair of vertices
        int totalPaths = 1;
        for (int i = 0; i < n; i++) {
            totalPaths += pathCounts[0][i];
        }

        StdOut.setFile(args[1]);
        StdOut.println(totalPaths);
        int totalPathEU = search(arrAdj, new int[]{0}, 0, thresholdEU);
        StdOut.println(totalPathEU);
    }

    // Recursive search function
    public static int search(int[][] adjacencyMatrix, int[] path, int currentVertex, int threshold) {
        int numPaths = 0; // Counter for the number of paths that meet the criteria

        // Check if the path length is greater than or equal to the threshold
        if (path.length >= threshold) {
            // Increment the counter
            numPaths++;
        }
        
        // Loop through the vertices adjacent to the current vertex
        for (int i = 0; i < adjacencyMatrix[currentVertex].length; i++) {
            // Check if there is an edge between the current vertex and the i-th vertex
            if (adjacencyMatrix[currentVertex][i] > 0) {
                // Add the i-th vertex to the path
                int[] newPath = Arrays.copyOf(path, path.length + 1);
                newPath[path.length] = i;
                
                // Recursively search from the i-th vertex
                numPaths += search(adjacencyMatrix, newPath, i, threshold);
            }
        }
        
        return numPaths;
    }

}