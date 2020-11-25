package Assignment4;

/*
 * @author Ahmed Mohamed (aa14zu)
 * Dec 6, 2018 COSC 2P03 A4
 * Node wrapper class for graph vertices
 */

public class Node {
    
    private char item;              //A,B,C,D,E,F,G
    private final int maxEdges = 10;//hardcoded assumption.
    public Node[] edges;            //Pointer to another vertex
    public int degree;              //The degree of the node
    private int edgeCount;          //Counter for next avail spot to add edge
    public boolean wasTraversed;    /*To avoid traversing twice when checking
                                    /*for an Euler circuit*/
   
    public Node ( int i ){
        item = intToChar(i); //0 = 'A', 1 = 'B', etc.
        edges = new Node[maxEdges];
        edgeCount = 0;
    }  //constructor
    
    
    public void addEdge ( Node n ){
        if (edgeCount < maxEdges ){
                edges[edgeCount] = n;   //add edge
                edgeCount++;            //increment next avail spot
        }
        else {
            System.out.print("No room to add edge!"); //so user knows
        }
    } //addEdge
    
    
    public void display(){
        System.out.println();
        System.out.println(item+": ");
        System.out.print("Edges: ");
        for ( int i = 0 ; i < maxEdges ; i++ ){
            if ( edges[i] != null ){
                System.out.print(edges[i].getLetter()+", ");
            }
        }
        System.out.println();
    }
    
    
    public char getLetter(){
        return item;
    }
    
    
    private char intToChar( int i ){
        char value;
        
        if ( i == 0 ){
            value = 'A';
        }
        else if ( i == 1 ){
            value = 'B';
        }
        else if ( i == 2 ){
            value = 'C';
        }
        else if ( i == 3 ){
            value = 'D';
        }
        else if ( i == 4 ){
            value = 'E';
        }
        else if ( i == 5 ){
            value = 'F';
        }
        else if ( i == 6 ){
            value = 'G';
        }
        else if ( i == 7 ){
            value = 'H';
        }
        else if ( i == 8 ){
            value = 'I';
        }
        else if ( i == 9 ){
            value = 'J';
        }
        else {
            value = 'K';
        }
        
        return value;
    }
    
    public int getEdgeCount(){
        return edgeCount;
    }
    
    
} //Node
