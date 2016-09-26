import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * Algorithm that efficiently computes the sum of the minimum cost tree that spans all veritces
 * O(m*n) where m = no of edges, n =  no of vertices
 */

/**
 * @author Miruna Pislar
 *
 */
public class PrimeMinimumSpanningTree {
	
	private static final int INFINITY = 1000000;
	private static long totalSum = 0;
	
	private static class Graph
	{
		private HashMap<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
		private ArrayList<Edge> allEdges = new ArrayList<Edge>();
		
		// Method that adds a vertex to the vertices hashMap 
		public void addVertex (Vertex vertex)
		{
			vertices.put(vertex.label, vertex);
		} // addVertex
		
		// Method to put a vertex into a graph in case there are duplicates
		// Also returns the element if it already exists
		public Vertex putVertex(int label)
		{
			Vertex v;
			if ((v = vertices.get(label)) == null)
			{
				v = new Vertex(label);
				addVertex(v);
			}
			return v;
		} // getVertex
		
		// Method that accesses a vertex from a graph
		public Vertex getVertex(int label)
		{
			if (!vertices.containsKey(label))
				throw new IllegalArgumentException("The label you want to get "
			+ "doesn't exist in the graph. Provide a valid label!");
			return vertices.get(label);
		} // getVertex
		
		// Print all the elements of the graph - vertices, edges and distances.
		// Used for debugging
		public void printGraph()
		{
			Collection<Vertex> newCollection = vertices.values();
			Iterator<Vertex> iterator = newCollection.iterator(); 
			while(iterator.hasNext())
				System.out.println(iterator.next().toString());
		} // printGraph
		
		// Print the allEdges array of the graph containing all the edges.
		// Used for debugging
		public void printEdges()
		{
			for(int i = 0; i < allEdges.size(); i++)
				System.out.println(allEdges.get(i).toString());
		} // printEdges
		
	} // Graph
	
	// The representation of a vertex
	private static class Vertex
	{
		private int label;
        private Set<Edge> edges = new HashSet<Edge>();
        
        // Constructor of a Vertex object that has this label
        public Vertex(int label)
        {
        	this.label = label;
        } // Vertex
        
        // Method that adds an edge to the graph
        public void addEdge(Edge edge)
        {
        	edges.add(edge);
        } // addEdge
        
        // Access the label of the vertex
        public int getLabel()
        {
        	return label;
        } // getLabel
        
        // Method that returns a String representation of a vertex
        public String toString()
        {
        	String text = "";
        	if (edges.size() == 0)
        		text = "Vertex " + label + " has no edges. \n";
        	else
        	{
	        	text = "Vertex " + label + " has edges: \n";
	        	
	        	// Convert the hashset into an array of edges
	        	Edge[] array = new Edge[edges.size()];
	        	edges.toArray(array);
	
	        	// Print each edge, one by one.
	        	for(int i = 0; i < edges.size(); i++)
	        		text += array[i].toString();
        	} // else
        	return text;
        } // printVertex
        
	} // Vertex
	
	// Representation of an edge
	private static class Edge
	{
		private ArrayList<Vertex> margins = new ArrayList<Vertex>();
		private int distance;
		
		// Constructor method for an edge - needs to have exactly 2 vertexes and a distance.
		public Edge(Vertex first, Vertex second, int distance)
		{
			if(first == null || second == null)
				throw new IllegalArgumentException("You need to provide both vertices!" );
			
			// Add the first and the second end of an edge to the margins array
			margins.add(first);
			margins.add(second);
			// Set the distance
			this.distance = distance;
		} // Edge
		
		// Method that provides access to the first end of the edge
		public Vertex getFirstEnd()
		{
			return margins.get(0);
		} // getFirstEnd
		
		
		// Method that provides access to the second end of the edge
		public Vertex getSecondEnd()
		{
			return margins.get(1);
		} // getSecondEnd
		
		// Method that returns a String representation of an Edge
		public String toString()
		{
			if(margins.isEmpty())
				return "This edge is empty.";
			else
				return "(" + margins.get(0).getLabel() 
						+ "," +  margins.get(1).getLabel() 
						+ ") with distance = " 
						+ distance + "\n";
		} // printEdge
	} // Edge
	
	// Prim's Algorithm
	private static void prim(Graph g, Vertex sourceVertex)
	{
		// Create a HashSet that keeps track of the vertices visited so far.
		HashSet<Vertex> X = new HashSet<Vertex>(1);
		X.add(sourceVertex);
		
		// As long as there are unvisited vertices
		while(X.size() != g.vertices.size())
		{
			int minimum = INFINITY;
			Vertex wFinal = null;
			
			// Loop through the edges of the graph
			for(int i = 0; i < g.allEdges.size(); i++)
			{
				// Get the edge and retain its two ends in some Vertex variables.
				Edge edge = g.allEdges.get(i);
				Vertex v1 = edge.getFirstEnd();
				Vertex v2 = edge.getSecondEnd();
				
				// If one end in in X and the other is not
				if(X.contains(v1) && !(X.contains(v2)))
				{					
					// Keep only the minimum distance from X to V \ X
					if(edge.distance < minimum)
					{
						minimum = edge.distance;
						wFinal = v2;						
					} // if
				} // if
			} // for
			
			// Add the current found vertex to the set of visited vertices.
			X.add(wFinal);
			
			// Increase the sum found so far by the minimum distance found
			totalSum += minimum;

		} // while

	} // prim

	/**
	 * @param args
	 */
	// Main method, constructing the graph, calling the method that computes the
	// sum of minimum spanning tree 
	public static void main(String[] arg)throws IOException
	{
		
		File file = new File(System.getProperty("user.dir") + "/primeAlgorithmTextFile.txt");
		BufferedReader input = new BufferedReader(new FileReader(file));
		
		Graph graph = new Graph();
		int noOfEdges, noOfNodes;
		
		try 
		{
			// Read a line of text from the file
		    String line = input.readLine();
		    String[] splitVector = line.split(" ");
		    noOfNodes = Integer.parseInt(splitVector[0]);
		    noOfEdges = Integer.parseInt(splitVector[1]);
		    
		    line = input.readLine();
		    
		    while (line != null) 
		    {
		    	// Split the line of text into a vector of Strings
		    	String[] vector = line.split(" ");
		    	
		    	// Extract the first vertex on this line and add it to the graph
		    	int labelVertex1 = Integer.parseInt(vector[0]);
		    	
		    	// Create a vertex holding the new vertex and add it to the graph
		    	Vertex v1 = graph.putVertex(labelVertex1);
		    	
		    	// Extract the second vertex on this line and add it to the graph
		    	int labelVertex2 = Integer.parseInt(vector[1]);
		    	
		    	// Extract the cost of the edge formed by the previous 2 vertices
		    	int distance = Integer.parseInt(vector[2]);

		    	// Create a vertex holding the new vertex and add it to the graph
			    Vertex v2 = graph.putVertex(labelVertex2);
			    
			    // Create an edge having the properties read.
			    Edge edge1 = new Edge(v1, v2, distance);
			    Edge edge2 = new Edge(v2, v1, distance);
			    
			    // Add edge to vertex 1 and vertex 2
			    v1.addEdge(edge1);
			    v2.addEdge(edge2);
			    
			    // Add edge to edges repository
			    graph.allEdges.add(edge1);
			    graph.allEdges.add(edge2);
		    	
		    	// Read a new line of text
		    	line = input.readLine();		    	
		    } // while			
		} // try
		finally
		{
			input.close();
		} // finally
		
		//System.out.println("The representation of the graph read is:\n");
		//graph.printGraph();
		
		//System.out.println("The edges of the graph are:\n");
		//graph.printEdges();
		
		// Initialize the source vertex
		Vertex sourceVertex = graph.getVertex(1);
		
		// Call Prim's algorithm to compute the MST
		prim(graph, sourceVertex);
		
		// Print the final sum on the standars output
		System.out.println("\nSum = " + totalSum);
		
	} // main	

} // PrimeMinimumSpanningTree
