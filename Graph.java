package Assignment4;

import BasicIO.*;
/*
 * @author Ahmed Mohamed (aa14zu)
 * Dec, 6, 2018. COSC 2P03 A4.
 * 
 */

public class Graph {
    
    private final ASCIIDataFile input;    //data file input
    private int numEdges;           //max number of edges
    private int[][] adjacencyMatrix;//adjacency matrix (n x n)
    private Node[] vertices;        //the vertices in node form
    private int DFSCount;           //keeps track of # values found with DFS
    private int largestSubset;
    private char[] eulersCircuit;
    
    public Graph (){
        input = new ASCIIDataFile();            //gets an input
        DFSCount = 0;                           //initialize
        setMatrix();                            //read input then set matrix
        createGraph(adjacencyMatrix, numEdges); //Create graph from matrix
        largestSubset = largestEulerSubset(adjacencyMatrix,numEdges);
        System.out.println();
        if ( largestSubset < 0 ){
           System.out.println("No euler circuit found!");
        }
        else {
           System.out.println("Euler circuit of size "+largestSubset+" found.");
           findEulerCircuit(largestSubset, vertices[0]);//find/print circuit
        }
    } //Constructor
    
    
    private void setMatrix(){
        if ( input.isEOF() == false ){          //ensure file is not empty
            
            numEdges = input.readInt();
            adjacencyMatrix = new int[numEdges][numEdges];
            
            for ( int i = 0 ; i < numEdges ; i++ ){
                for ( int j = 0 ; j < numEdges ; j++ ){
                    //fill up adjacency matrix
                    if ( input.isEOF() == false ){ //ensure file not empty
                        adjacencyMatrix[i][j] = input.readInt();
                    }
                    else {
                        System.out.print("Error: Missing inputs!");
                    }
                }
            }
        }
        else {
            System.out.print("Error: Empty file!"); //defensive programming
        }    
    } //setMatrix. Reads the input and sets the adjacency matrix with input.
    
    
    private void createGraph( int[][] adjacenyMatrix, int numEdges ){
        
        vertices = new Node[numEdges];      //holds vertices
        for ( int k = 0 ; k < numEdges ; k++ ){
            vertices[k] = new Node(k);      //Node created w/ letter (A,B etc)
        }
        for ( int i = 0 ; i < numEdges ; i++ ){
            for ( int j = 0 ; j < numEdges ; j++ ){
                if ( adjacencyMatrix[i][j] == 1 ){
                    vertices[i].addEdge(vertices[j]); //add edge to vertex
                }
            }
        }
        
        for ( int l = 0 ; l < numEdges ; l++ ){
            vertices[l].display();
        }
        
    } //createGraph. Creates nodes with values and edges based on matrix.
    
    
    private int largestEulerSubset( int[][] matrix, int numVertex ){
        boolean hasEulerCircuit = false;    //an euler circuit exists
        boolean allVerticesReached = false; //all vertices reached after DFS
        boolean allVerticesEven = false;    //all vertices have an even degree
        createGraph(matrix, numVertex);
        /*Test if graph of adjacency matrix has an Euler circuit*/
        /*Check if all vertices reached after DFS*/
        allVerticesReached = false;
        //System.out.println("DFS: ");
        DFS(vertices[0]);   //depth first search of vertices
        if ( DFSCount == numVertex ){   //if all vertices found in DFS
            allVerticesReached = true;  //then all reached is true
            setDegrees();               //set the degrees of all the nodes
            for ( int z = 0 ; z < numVertex ; z++ ){ //for each vertex
                /*Check if degree of each vertex is even*/
   //System.out.println(vertices[z].getLetter()+" Degree: "+vertices[z].degree);
                if (vertices[z].degree % 2 != 0){ //if not even
                    //System.out.println("Uneven");
                    allVerticesEven = false;
                    break;
                }
                else {  //if even
                    //System.out.println("Even");
                    allVerticesEven = true;
                }
            }
        }
        else { //If not all vertices found in DFS, it can't be an euler circuit
            hasEulerCircuit = false;
        }
        
        if ( allVerticesReached == true && allVerticesEven == true ){
            hasEulerCircuit = true;
        }
        else {
            hasEulerCircuit = false;
        }
        
        if ( hasEulerCircuit == true ){
        /*If so, return number of vertices*/
            return numVertex;
        }
        else if ( hasEulerCircuit == false ){
        /*If not...*/
            /*For each vertex*/
            for ( int k = 1 ; k <= numVertex ; k++ ){
                /*Remove vertex from adjacency matrix*/
                int[][] newMatrix = new int[matrix.length-1][matrix.length-1];
                for ( int i = k ; i <= matrix.length - 1 ; i++ ){    // matrix-1
                    for ( int j = k ; j <= matrix.length - 1 ; j++ ){// matrix-1
                        newMatrix[i-1][j-1] = matrix[i][j];
                    }
                }
                printMatrices(matrix,newMatrix);   
                largestEulerSubset (newMatrix, numVertex - 1);
                //find largest Euler subset of (A’, numV-1);
            }
        }
        return -1;  //-1 means no euler circuit found.
    } //largestEulerSubset
    
    
    private void printMatrices( int[][] matrix, int[][] newMatrix){
        //Print old matrix
        System.out.println("Old Matrix:");
        for (int v = 0 ; v < matrix.length ; v++ ){
            for ( int z = 0 ; z < matrix.length ; z++ ){
                System.out.print(matrix[v][z]);
            }
            System.out.println();
        }
        System.out.println();
        
        System.out.println("New Matrix:");
        //Print new matrix
        for (int x = 0 ; x < newMatrix.length ; x++ ){
            for ( int y = 0 ; y < newMatrix.length ; y++ ){
                System.out.print(newMatrix[x][y]);
            }
            System.out.println();
        }
        System.out.println();
    } //printMatrices (Print out 2 matrices to compare)
    
    
    private void setDegrees(){
        Node v;
        for ( int i = 0 ; i < vertices.length ; i++ ){ //for each vertex
            v = vertices[i];    //curr vertex
            int temp = 0;
                while ( v.edges[temp] != null ){ //check non-null edge
                    v.edges[temp].degree = v.edges[temp].degree + 1; /*pointed*/
                    temp++;                          /*to, so increment degree*/
		}
        }
    }   //setDegrees. //Sets the degree for each of the vertices
    
    
    private void DFS ( Node v ){
        v.wasTraversed = true;          //node now been visited
        DFSCount++;                     //increment # found
        boolean adjacentVertex = true;  //make true
        while ( adjacentVertex ){       //while true
            adjacentVertex = false;     //must be made true again to continue
            /*Check if there is an unvisited adjacent vertex*/
            for ( int i = 0 ; i < v.edges.length ; i++ ){
                if (v.edges[i] != null){
                    if (v.edges[i].wasTraversed == false){ /*if adjacent    */
                        adjacentVertex = true;             /*vertex found   */
                        DFS(v.edges[i]); //recursion
                    }
                }
            }//for
        }//while
        
    } //Depth First Search
    
    private void resetDFS(){
        DFSCount = 0;       //reset count
        for ( int i = 0 ; i < vertices.length ; i++ ){
            for ( int j = 0 ; j < vertices[i].edges.length ; j++ ){
                if (vertices[i].edges[j] != null){
                    vertices[i].edges[j].wasTraversed = false;   
                } //set all 'wasTraversed' to false
            }
        }
    } //resetDFS
    
    
    private void findEulerCircuit(int size, Node v){
        eulersCircuit = new char[v.edges.length];
        for ( int j = 0 ; j < size ; j++ ){
            for ( int i = 0 ; i < v.edges.length ; i++ ){
                if ( v.edges[i] != null ){  //If not null, consider
                    Node temp;
                    temp = v.edges[i];  //so not lost.
                    v.edges[i] = null;  //delete edge
                    resetDFS();
                    DFS(v); //Depth first search
        //            System.out.print(DFSCount+" ");
                    if (DFSCount == vertices.length){//all found(no disconnect) 
                        v = temp;   //move to next vertex
          //              System.out.println("going");
                        eulersCircuit[j] = v.getLetter();
                    }
                    else{   //if removing that edge disconnected the graph
                        v.edges[i] = temp;  //revert back
                    }
                }
            }
        }
        /*Print out Eulers Circuit*/
        System.out.println();
        System.out.println("Euler's Circuit: ");
        for ( int k = 0 ; k < v.edges.length ; k++ ){
            if (eulersCircuit[k] != '\0' ){
                System.out.print(eulersCircuit[k]+" ");
            }  
        }
    }
    
    public static void main (String args[]){
        Graph graph = new Graph();
    } //main method
    
    
} //Graph