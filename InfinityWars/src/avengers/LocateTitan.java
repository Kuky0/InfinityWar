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

    	// WRITE YOUR CODE HERE
        // ============================================================ //
        // STEP 1

        // reads the input file
        StdIn.setFile(args[0]);

        // reads the first number as the array size
        int arrSZ = StdIn.readInt();

        // initialize the 2D array with size
        int[][] arrAdj = new int[arrSZ][arrSZ];

        // initialize the 2D array for generator and functionality
        double[][] arrFunct = new double[arrSZ][2];

        // to fill the array with the functionality
        // the index will represent the vectices
        for (int i = 0; i < arrFunct.length; i++) {
            for (int j = 0; j < arrFunct[i].length; j++) {
                arrFunct[i][j] = StdIn.readDouble();
            }
        }
        
        // filling the 2D array with the adjacency matrix
        for (int i = 0; i < arrSZ; i++) {
            for (int j = 0; j < arrSZ; j++) {
                arrAdj[i][j] = StdIn.readInt();
            }
        }

        // ============================================================ //

        // STEP 2

        // this should loop thru arrAdj to find the Generator
        for (int i = 0; i < arrAdj.length; i++) {
            for (int j = 0; j < arrAdj[i].length; j++) {
                int arrVal = arrAdj[i][j];
                double i_Funct = arrFunct[(int)i][1];
                double j_Funct = arrFunct[(int)j][1];
                arrAdj[i][j] = (int)(arrVal/(i_Funct*j_Funct));
            }
        }

        // ============================================================ //

        // STEP 3
        int path_array[] = new int[arrSZ];
        Boolean sptSet[] = new Boolean[arrSZ];

        for (int i = 0; i < arrSZ; i++) { 
            path_array[i] = Integer.MAX_VALUE; 
            sptSet[i] = false; 
        }

        path_array[arrAdj[0][0]] = 0;

        for (int count = 0; count < arrSZ - 1; count++) { 
            // call minDistance method to find the vertex with min distance
            int u = minDistance(path_array, sptSet, arrSZ); 
              // the current vertex u is processed
            sptSet[u] = true; 
              // process adjacent nodes of the current vertex
            for (int v = 0; v < arrSZ; v++) 
   
                // if vertex v not in sptset then update it  
                if (!sptSet[v] && arrAdj[u][v] != 0 && path_array[u] != 
                            Integer.MAX_VALUE && path_array[u] 
                            + arrAdj[u][v] < path_array[v]) 
                            path_array[v] = path_array[u] + arrAdj[u][v]; 
        }
        StdOut.setFile(args[1]);
        StdOut.print(path_array[arrSZ-1]);
    }
    public static int minDistance(int path_array[], Boolean sptSet[], int arraySize)   { 
        // Initialize min value 
        int min = Integer.MAX_VALUE, min_index = -1; 
        for (int v = 0; v < arraySize; v++) 
            if (sptSet[v] == false && path_array[v] <= min) { 
                min = path_array[v]; 
                min_index = v; 
            } 
   
        return min_index; 
    }
}