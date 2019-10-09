/*
 * Assignment #5, brendel_pr5
 * Sam Brendel, sam2b@uw.edu
 * Logan Stafford
 * TCSS 342 Data Structures
 * June 4, 2017
 */

package roadgraph;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import com.sun.beans.editors.ShortEditor;
import com.sun.istack.internal.FinalArrayList;
import com.sun.javafx.scene.paint.GradientUtils.Point;
import com.sun.org.apache.bcel.internal.generic.LASTORE;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.ws.policy.PolicyIntersector;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import basicgraph.GraphAdjList;
import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team, Monika Sobolewska, and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections
 *
 */
public class MapGraph {
	// Maintain both vertices and edges
	
	// Vertices that map lat/lon  to the list of edges from that location
	private HashMap<GeographicPoint, List<Integer>> pointNodeMap;
	// List of edges / roads
	private List<MapEdge> edges;

	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		pointNodeMap = new HashMap<GeographicPoint, List<Integer>>();
		edges = new ArrayList<MapEdge>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
        //
		return pointNodeMap.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as a set of GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		return pointNodeMap.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		return edges.size();
	}	
	
	/** Add a vertex to the graph (location and an empty list)
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		boolean result = false;
		if(location != null && !pointNodeMap.containsKey(location)) {
			pointNodeMap.put(location, new ArrayList<>());
			result = true;
		}
		return result;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
		String roadType, double length) throws IllegalArgumentException {

		// check nodes are valid
		if (!pointNodeMap.containsKey(from))
			throw new NullPointerException("addEdge: pt1:"+from+"is not in graph");
		if (!pointNodeMap.containsKey(to))
			throw new NullPointerException("addEdge: pt2:"+to+"is not in graph");

		// 
		// create and add a new edge to the list of edges 
		MapEdge newEdge = new MapEdge(roadName, roadType, from, to, length);
		edges.add(newEdge);
		// add the index of the edge to the list of edges associated with the "from" vertex
		int index = edges.indexOf(newEdge);
		pointNodeMap.get(newEdge.getStartPoint()).add(index);
	}
		
	/** 
	 * Get a set of neighbor nodes from a GeographicPoint
	 * @param node  The location to get the neighbors from
	 * @return A set containing the GeographicPoint objects that are the neighbors 
	 * 	of node
	 */
	private Set<GeographicPoint> getNeighbors(GeographicPoint node) {
		// Write the code according to JavaDocs
		HashSet<GeographicPoint> neighborSet = new HashSet<>();
		for(final MapEdge edge : edges) {
			
			// If the edge has the same start point as me, the it means this edge's end point has my neighbor.
			/*if (edge.getStartPoint().equals(new GeographicPoint(node.getX(), node.getY()))) {
				neighborSet.add(edge.getEndPoint());
			}*/
			
			for(int index : pointNodeMap.get(node)) {
				neighborSet.add(edges.get(index).getEndPoint());
			}
		}
		return neighborSet;
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, 
			 					     Consumer<GeographicPoint> nodeSearched)
	{
				
		// Setup - check validity of inputs
		if (start == null || goal == null)
			throw new NullPointerException("Cannot find route from or to null node");
		
		// setup to begin BFS
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		Queue<GeographicPoint> toExplore = new LinkedList<GeographicPoint>();
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		toExplore.add(start);
		GeographicPoint next = null;

		visited.add(start);
		
		while (!toExplore.isEmpty()) {
			next = toExplore.remove();
			
			 // hook for visualization
			nodeSearched.accept(next);
			
			if (next.equals(goal)) {
				break;
			}
			
			//Debugging
			//Set<GeographicPoint> testSet = getNeighbors(next);
			
			for (GeographicPoint neighbor : getNeighbors(next)) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					parentMap.put(neighbor, next);
					toExplore.add(neighbor);
				}
			}
		}
			
		if (!next.equals(goal)) {
			System.out.println("No path found from " +start+ " to " + goal);
			return null;
		}
		
		// Reconstruct the parent path
		List<GeographicPoint> path = reconstructPath(parentMap, start, goal);

		return path;
	
	}
	


	/** Reconstruct a path from start to goal using the parentMap
	 *
	 * @param parentMap the HashNode map of children and their parents
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from
	 *   start to goal (including both start and goal).
	 */
	private List<GeographicPoint>
	reconstructPath(HashMap<GeographicPoint, GeographicPoint> parentMap,
					GeographicPoint start, GeographicPoint goal)
	{
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint current = goal;

		while (!current.equals(start)) {
			path.addFirst(current);
			current = parentMap.get(current);
		}

		path.addFirst(start);
		return path;
	}


	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// Setup - check validity of inputs
		if (start == null || goal == null)
			throw new NullPointerException("Cannot find route from or to null node");
		
		// setup to begin Dijkstra
		/** Nodes that still need to be explored.  All neighbors of a given node are added to this list. */
		PriorityQueue<PointCounter> toExplore = new PriorityQueue<>(new PointCounter(null)); // using a comparator.
		/** A set of nodes that have been visited. */
		HashSet<GeographicPoint> visited = new HashSet<>();
		/** Keeps track of a node and its parent in the shortest path. */
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<>();
		/** Keeps track of all nodes' lengths, and is updated with new lengths if there is a shorter path. */
		List<PointCounter> table = new ArrayList<>(); 
		
		PointCounter current = new PointCounter(start, 0.0);
		//PointCounter lastKnown = null;
		parentMap.put(current.point, null);
		toExplore.add(current);
		table.add(current);
		
		while (!toExplore.isEmpty()) {
			current = toExplore.remove();

			// hook for visualization
			nodeSearched.accept(current.point);

			if(!visited.contains(current.point)) {
				visited.add(current.point);
			
				if (current.point.equals(goal)) {
					break;
					// Stop the loop and then reconstructs the path to return by this method.
				}
				
				// DEBUGGING, to see the list of edges for current. ///////////////////////////
//				List<MapEdge> edgeList = new ArrayList<>();
//				for(final int index : pointNodeMap.get(current.point)) {
//					edgeList.add(edges.get(index));
//				} /////////////////////////////////////////////////////////////////////////////
				
				// For each of current's neighbors, which is really each of current's edges' end point.
				for(final int index : pointNodeMap.get(current.point)) {
					MapEdge edge = edges.get(index);

					if(!visited.contains(edge.getEndPoint())) {

						PointCounter neighbor = null;
							
						// If the table already has an entry for this node, then use it.  I want its existing length, not infinity.
						for(final PointCounter pc : table) {
							if(pc.point.equals(edge.getEndPoint())) {
								neighbor = pc;
							}
						}
						
						if (neighbor == null) {
							neighbor = new PointCounter(edge.getEndPoint()); // length defaults to infinity.
							table.add(neighbor);
						}
						
						/* If the path through current to neighbor is shorter, 
						 * then update the neighbor's length,
						 * and update current as neighbor's parent in parent map,
						 * and enqueue this neighbor. */
						if (current.length + edge.getLength() < neighbor.length) {
							neighbor.length = current.length + edge.getLength();
							parentMap.put(neighbor.point, current.point); // This is the new parent since the path is shorter.

							table.remove(neighbor); // rip
							table.add(neighbor);    // and replace.
						}
						toExplore.add(neighbor);
					}
				}
			}
		}
			
		if (!current.point.equals(goal)) {
			System.out.println("No path found from " + start + " to " + goal);
			return null;
		}
		
		// Reconstruct the parent path
		List<GeographicPoint> path = reconstructPath(parentMap, start, goal);
		return path;
	}
		
	private class PointCounter implements Comparator<PointCounter> {
		public double length;
		public GeographicPoint point;

		public PointCounter(final GeographicPoint thePoint, final double theLength) {
			this.length = theLength;
			this.point = thePoint;
		}

		public PointCounter(final GeographicPoint thePoint) {
			//this(thePoint, null); // represents infinity.
			this(thePoint, Double.POSITIVE_INFINITY); // represents infinity.
		}

		@Override
		public int compare(final PointCounter objectA, PointCounter objectB) {
			return (int)(objectA.length - objectB.length);
		}
		
		@Override
		public String toString() {
			String len = (length == Double.POSITIVE_INFINITY) ? "infinity" : String.valueOf(Math.round(length));
			return "[" + (int)point.x + "," + (int)point.y + " length " + len + "]";
		}
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// Setup - check validity of inputs
		if (start == null || goal == null)
			throw new NullPointerException("Cannot find route from or to null node");
		
		// setup to begin Dijkstra
		/** Nodes that still need to be explored.  All neighbors of a given node are added to this list. */
		PriorityQueue<PointCounter> toExplore = new PriorityQueue<>(new PointCounter(null)); // using a comparator.
		/** A set of nodes that have been visited. */
		HashSet<GeographicPoint> visited = new HashSet<>();
		/** Keeps track of a node and its parent in the shortest path. */
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<>();
		/** Keeps track of all nodes' lengths, and is updated with new lengths if there is a shorter path. */
		List<PointCounter> table = new ArrayList<>();
		
		PointCounter current = new PointCounter(start, 0.0);
		//PointCounter lastKnown = null;

		parentMap.put(current.point, null);
		toExplore.add(current);
		table.add(current);
		
		while (!toExplore.isEmpty()) {
			current = toExplore.remove();

			// hook for visualization
			nodeSearched.accept(current.point);

			if(!visited.contains(current.point)) {
				visited.add(current.point);
			
				if (current.point.equals(goal)) {
					break;
					// Stop the loop and then reconstructs the path to return by this method.
				}
				
				// DEBUGGING, to see the list of edges for current. ///////////////////////////
//				List<MapEdge> edgeList = new ArrayList<>();
//				for(final int index : pointNodeMap.get(current.point)) {
//					edgeList.add(edges.get(index));
//				} /////////////////////////////////////////////////////////////////////////////
				
				double shortest = Double.POSITIVE_INFINITY;
				PointCounter temp = null;
				// For each of current's neighbors, which is really each of current's edges' end point.
				for(final int index : pointNodeMap.get(current.point)) {
					MapEdge edge = edges.get(index);
					PointCounter neighbor = null;
					
					if(!visited.contains(edge.getEndPoint())) {

						// If the table already has an entry for this node, then use it.  I want its existing length, not infinity.
						for(final PointCounter pc : table) {
							if(pc.point.equals(edge.getEndPoint())) {
								neighbor = pc;
							}
						}
						
						if (neighbor == null) {
							neighbor = new PointCounter(edge.getEndPoint()); // length defaults to infinity.
							table.add(neighbor);
						}
						
						
						double straightLine = neighbor.point.distance(goal);
						
						//double lengthToNeighbor = current.length + edge.getLength();
						
						neighbor.length = straightLine + current.length + edge.getLength();
						
						/* If the path through current to neighbor is shorter, 
						 * then update the neighbor's length,
						 * and update current as neighbor's parent in parent map,
						 * and enqueue this neighbor. */ 
						if (neighbor.length < shortest) {
							temp = neighbor;
							temp.length = neighbor.length;
							shortest = neighbor.length;

							parentMap.put(temp.point, current.point); // This is the new parent since the path is shorter.
							table.remove(temp); // rip
							table.add(temp);    // and replace.
						}
						toExplore.add(temp);
					}
				} // Now we have the shortest neighbor.
			}
		}
			
		if (!current.point.equals(goal)) {
			System.out.println("No path found from " + start + " to " + goal);
			return null;
		}
		
		// Reconstruct the parent path
		List<GeographicPoint> path = reconstructPath(parentMap, start, goal);
		return path;
	}
	
}
