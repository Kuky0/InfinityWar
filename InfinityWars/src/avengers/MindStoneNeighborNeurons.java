package avengers;
import java.util.*;

import javax.swing.plaf.synth.SynthSplitPaneUI;

/**
 * Given a Set of Edges representing Vision's Neural Network, identify all of the 
 * vertices that connect to the Mind Stone. 
 * List the names of these neurons in the output file.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * MindStoneNeighborNeuronsInputFile name is passed through the command line as args[0]
 * Read from the MindStoneNeighborNeuronsInputFile with the format:
 *    1. v (int): number of neurons (vertices in the graph)
 *    2. v lines, each with a String referring to a neuron's name (vertex name)
 *    3. e (int): number of synapses (edges in the graph)
 *    4. e lines, each line refers to an edge, each line has 2 (two) Strings: from to
 * 
 * Step 2:
 * MindStoneNeighborNeuronsOutputFile name is passed through the command line as args[1]
 * Identify the vertices that connect to the Mind Stone vertex. 
 * Output these vertices, one per line, to the output file.
 * 
 * Note 1: The Mind Stone vertex has out degree 0 (zero), meaning that there are 
 * no edges leaving the vertex.
 * 
 * Note 2: If a vertex v connects to the Mind Stone vertex m then the graph has
 * an edge v -> m
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
 *     //Call StdOut.print() for EVERY neuron (vertex) neighboring the Mind Stone neuron (vertex)
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/MindStoneNeighborNeurons mindstoneneighborneurons.in mindstoneneighborneurons.out
 *
 * @author Yashas Ravi
 * 
 */


public class MindStoneNeighborNeurons {
    
    public static void main (String [] args) {
        
    	if ( args.length < 2 ) {
            StdOut.println("Execute: java MindStoneNeighborNeurons <INput file> <OUTput file>");
            return;
        }
    	
    	// WRITE YOUR CODE HERE

        StdIn.setFile(args[0]);

        int arrSZvert = StdIn.readInt();

        String[] arrVert = new String[arrSZvert];

        for (int i = 0; i < arrSZvert; i++) {
            arrVert[i] = StdIn.readString();
        }

        int arrSZedge = StdIn.readInt();

        String[][] arrEdge = new String[arrSZedge][2];

        for (int i = 0; i < arrEdge.length; i++) {
            for (int j = 0; j < arrEdge[i].length; j++) {
                arrEdge[i][j] = StdIn.readString();
            }
        }

        String[] left = new String[arrSZedge];
        for (int i = 0; i < arrEdge.length; i++) {
            left[i] = arrEdge[i][0];
        }

        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        for (String element : arrVert) {
            set1.add(element);
        }

        for (String element : left) {
            set2.add(element);
        }
        
        String vertex = "";
        for (String element : set1) {
            if (!set2.contains(element)) {
                vertex = element;
            }
        }
          
        for (String element : set2) {
            if (!set1.contains(element)) {
                vertex = element;
            }
        }

        StdOut.setFile(args[1]);
        for (int i = 0; i < arrEdge.length; i++) {
            if (vertex.equals(arrEdge[i][1])) {
                StdOut.println(arrEdge[i][0]);
            }
        }














    }
}
