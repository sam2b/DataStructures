/*
 * Assignment #5, brendel_pr5
 * Sam Brendel, sam2b@uw.edu
 * Logan Stafford
 * TCSS 342 Data Structures
 * June 4, 2017
 */

package roadgraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import com.sun.javafx.geom.Edge;

import geography.GeographicPoint;

/**
 * @author UCSD MOOC development team, Monika Sobolewska, and Sam Brendel.
 *         A class which represents a graph of geographic locations Nodes in the
 *         graph are intersections.
 */
public class MapGraph {
	// Maintain both vertices and edges

	// Vertices that map lat/lon to the list of edges from that location
	private HashMap<GeographicPoint, List<Integer>> pointEdgeMap;
	// List of edges / roads
	private List<MapEdge> edges;

	/**
	 * Create a new empty MapGraph
	 */
	public MapGraph() {
		pointEdgeMap = new HashMap<GeographicPoint, List<Integer>>();
		edges = new ArrayList<MapEdge>();
	}

	/**
	 * Get the number of vertices (road intersections) in the graph
	 * 
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		///
		return pointEdgeMap.size();
	}

	/**
	 * Return the intersections, which are the vertices in this graph.
	 * 
	 * @return The vertices in this graph as a set of GeographicPoints
	 */
	public Set<GeographicPoint> getVertices() {
		return pointEdgeMap.keySet();
	}

	/**
	 * Get the number of road segments in the graph
	 * 
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges() {
		return edges.size();
	}

	/**
	 * Add a vertex to the graph (location and an empty list) If the location is
	 * already in the graph or null, this method does not change the graph.
	 * 
	 * @param location The location of the intersection
	 * @return true if a node was added, false if it was not (the node was already
	 *         in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location) {
		boolean result = false;
		if (location != null && !pointEdgeMap.containsKey(location)) {
			pointEdgeMap.put(location, new ArrayList<>());
			result = true;
		}
		return result;
	}

	/**
	 * Adds a directed edge to the graph from pt1 to pt2. Precondition: Both
	 * GeographicPoints have already been added to the graph
	 * 
	 * @param from     The originating point of the edge
	 * @param to       The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length   The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been added as
	 *                                  nodes to the graph, if any of the arguments
	 *                                  is null, or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length)
			throws IllegalArgumentException {

		// check nodes are valid
		if (!pointEdgeMap.containsKey(from))
			throw new NullPointerException("addEdge: pt1:" + from + "is not in graph");
		if (!pointEdgeMap.containsKey(to))
			throw new NullPointerException("addEdge: pt2:" + to + "is not in graph");

		//
		// create and add a new edge to the list of edges
		MapEdge newEdge = new MapEdge(roadName, roadType, from, to, length);
		edges.add(newEdge);
		// add the index of the edge to the list of edges associated with the "from"
		// vertex
		int index = edges.indexOf(newEdge);
		pointEdgeMap.get(newEdge.getStartPoint()).add(index);
	}

	/**
	 * Get a set of neighbor nodes from a GeographicPoint
	 * 
	 * @param node The location to get the neighbors from
	 * @return A set containing the GeographicPoint objects that are the neighbors
	 *         of node
	 */
	private Set<GeographicPoint> getNeighbors(GeographicPoint node) {
		// Write the code according to JavaDocs
		HashSet<GeographicPoint> neighborSet = new HashSet<>();
		for (final MapEdge edge : edges) {

			// If the edge has the same origin point as me, the it means this edge's end
			// point has my neighbor.
			/*
			 * if (edge.getStartPoint().equals(new GeographicPoint(node.getX(),
			 * node.getY()))) { neighborSet.add(edge.getEndPoint()); }
			 */

			for (int index : pointEdgeMap.get(node)) {
				neighborSet.add(edges.get(index).getEndPoint());
			}
		}
		return neighborSet;
	}

	/**
	 * Find the path from origin to destination using breadth first search
	 * 
	 * @param origin      The originating location
	 * @param destination The destination location
	 * @return The list of intersections that form the shortest (unweighted) path
	 *         from origin to destination (including both origin and destination).
	 */
	public List<GeographicPoint> bfs(GeographicPoint origin, GeographicPoint destination) {
		// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return bfs(origin, destination, temp);
	}

	/**
	 * Find the path from origin to destination using breadth first search
	 * 
	 * @param origin       The originating location
	 * @param destination  The destination location
	 * @param nodeSearched A hook for visualization.
	 * @return The list of intersections that form the shortest (unweighted) path
	 *         from origin to destination (including both origin and destination).
	 */
	public List<GeographicPoint> bfs(GeographicPoint origin, GeographicPoint destination,
			Consumer<GeographicPoint> nodeSearched) {
		validate(origin, destination);
		// setup to begin BFS.
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		Queue<GeographicPoint> unvisited = new LinkedList<GeographicPoint>();
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		unvisited.add(origin);
		GeographicPoint current = null;

		visited.add(origin);

		while (!unvisited.isEmpty()) {
			current = unvisited.remove();
			nodeSearched.accept(current); // hook for visualization.

			if (current.equals(destination)) {
				break;
			}

			for (GeographicPoint neighbor : getNeighbors(current)) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					parentMap.put(neighbor, current);
					unvisited.add(neighbor);
				}
			}
		}

		if (!current.equals(destination)) {
			System.out.println("No path found from " + origin + " to " + destination);
			return null;
		}

		// Reconstruct the parent path.
		return reconstructPath(parentMap, origin, destination);
	}

	/**
	 * Reconstruct a path from origin to destination using the parentMap
	 *
	 * @param parentMap   the HashNode map of children and their parents
	 * @param origin      The originating location
	 * @param destination The destination location
	 * @return The list of intersections that form the shortest path from origin to
	 *         destination (including both origin and destination).
	 */
	private List<GeographicPoint> reconstructPath(HashMap<GeographicPoint, GeographicPoint> parentMap,
			GeographicPoint origin, GeographicPoint destination) {
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint current = destination;

		while (!current.equals(origin)) {
			path.addFirst(current);
			current = parentMap.get(current);
		}

		path.addFirst(origin);
		return path;
	}

	/**
	 * Find the path from origin to destination using Dijkstra's algorithm
	 * 
	 * @param origin      The originating location.
	 * @param destination The destination location.
	 * @return The list of intersections that form the shortest path from origin to
	 *         destination (including both origin and destination).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint origin, GeographicPoint destination) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return dijkstra(origin, destination, temp);
	}

	public List<GeographicPoint> dijkstra(GeographicPoint origin, GeographicPoint destination,
			Consumer<GeographicPoint> nodeSearched) {
		validate(origin, destination);

		// setup to begin Dijkstra.
		/** Nodes that still need to be explored. All neighbors of a given node are added to this list. */
		PriorityQueue<NodeData> unvisited = new PriorityQueue<>(new NodeData(null)); // using a comparator.
		/** Keeps track of a node and its parent in the shortest path. */
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<>();
		/** Node object storage and quick retrieval. */
		HashMap<GeographicPoint, NodeData> pointNodeMap = new HashMap<>();
		NodeData current = new NodeData(origin, 0.0);
		NodeData neighbor;
		MapEdge edge;

		// Start with the origin point.
		pointNodeMap.put(origin, current);
		parentMap.put(current.point, null);
		unvisited.add(current);
		while (!unvisited.isEmpty()) {
			current = unvisited.remove();
			if (!current.point.equals(destination)) {
				nodeSearched.accept(current.point); // hook for visualization.
				// Look at each neighbor, which is really each of current's edges' end point.
				for (final int index : pointEdgeMap.get(current.point)) {
					// Iterates over the given list of edges for that point.
					edge = edges.get(index);
					neighbor = getNodeObject(pointNodeMap, edge.getEndPoint(), destination);
					final double length = current.localLength + edge.getLength();
					// Update this neighbor's local value if it is less than the present value.
					if (length < neighbor.localLength) {
						neighbor.localLength = length;
						neighbor.parentPoint = current.point;
						parentMap.put(neighbor.point, current.point);
						unvisited.remove(neighbor);
						unvisited.add(neighbor);
					}
				}
			} else {
				break; // stop searching because you've reached the destination.
			}
		}

		// Reconstruct the parent path.
		return reconstructPath(parentMap, origin, destination);
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	private class NodeData implements Comparator<NodeData> {
		public double localLength;
		public double globalLength;
		public GeographicPoint point;
		public GeographicPoint parentPoint;
		public double heuristic;

		public NodeData(final GeographicPoint theNode, final double theLength) {
			this.point = theNode;
			this.parentPoint = null;
			this.localLength = theLength;
			this.globalLength = Double.POSITIVE_INFINITY;
			this.heuristic = Double.POSITIVE_INFINITY;
		}

		public NodeData(final GeographicPoint thePoint) {
			this(thePoint, Double.POSITIVE_INFINITY);
		}

		// Descending order makes the "unvisited" PriorityQueue a min heap, meaning the
		// remove() method removes the smallest value among the elements.
		@Override
		public int compare(final NodeData objectA, NodeData objectB) {
			return (heuristic == Double.POSITIVE_INFINITY) 
					? (int)(objectA.localLength - objectB.localLength) 
					: (int) (objectA.globalLength - objectB.globalLength);
		}

		@Override
		public String toString() {
			String loc = (localLength == Double.POSITIVE_INFINITY) ? "infinity"
					: String.valueOf(Math.round(localLength));
			return "[" + (double) point.x + "," + (double) point.y + " Local=" + loc + ", Global=" + globalLength
					+ ", Heuristic=" + heuristic + ", Parent=" + parentPoint + "]";
		}
	}

	/**
	 * Find the path from origin to destination using A-Star search
	 * 
	 * @param origin      The originating location
	 * @param destination The destination location
	 * @return The list of intersections that form the shortest path from origin to
	 *         destination (including both origin and destination).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint origin, GeographicPoint destination) {
		// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return aStarSearch(origin, destination, temp);
	}

	/**
	 * Find the path from origin to destination using A-Star search
	 * 
	 * @param origin       The originating location.
	 * @param destination  The destination location.
	 * @param nodeSearched A hook for visualization. See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from origin to
	 *         destination (including both origin and destination).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint origin, GeographicPoint destination,
			Consumer<GeographicPoint> nodeSearched) {
		validate(origin, destination);
		/** Nodes that still need to be explored. All neighbors of a given node are added to this list. */
		PriorityQueue<NodeData> unvisited = new PriorityQueue<>(new NodeData(null)); // using a comparator.
		/** Keeps track of a node and its parent in the shortest path. */
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<>();
		/** Node object storage and quick retrieval. */
		HashMap<GeographicPoint, NodeData> pointNodeMap = new HashMap<>();
		NodeData neighbor;
		MapEdge edge;

		// Start with the origin point.
		NodeData current = new NodeData(origin, 0.0);
		current.heuristic = current.point.distance(destination);
		pointNodeMap.put(origin, current);
		parentMap.put(current.point, null);
		unvisited.add(current);

		while (!unvisited.isEmpty()) {
			current = unvisited.remove();
			nodeSearched.accept(current.point); // hook for visualization.
			if (!current.point.equals(destination)) { // Don't update any neighbors of desination.
				// Look at each neighbor, which is really each of current's edges' end point.
				for (final int index : pointEdgeMap.get(current.point)) { // Iterates over the given list of edges for that point.
					edge = edges.get(index);
					neighbor = getNodeObject(pointNodeMap, edge.getEndPoint(), destination);
					final double length = current.localLength + edge.getLength();
					// Use the heuristic of the least global length from the unvisited priority queue.
					final double temp = (!unvisited.isEmpty()) ? unvisited.peek().heuristic : Double.POSITIVE_INFINITY;
					final boolean shorterLength = neighbor.heuristic < temp;
					final boolean isTowardsDestination = length < neighbor.localLength;
					final boolean result = shorterLength && isTowardsDestination;
					// Update the neighbor's local value only if a shorterLength was found and only if isTowardsDestination.
					//if (neighbor.heuristic < current.heuristic && length < neighbor.localLength) { // Less accurate with more nodes visited not towards the destination.
					if (result) {
						neighbor.localLength = length;
						neighbor.globalLength = length + neighbor.heuristic;
						neighbor.parentPoint = current.point;
						parentMap.put(neighbor.point, current.point);
						unvisited.remove(neighbor);
						unvisited.add(neighbor);
					}
				}
			}
		}

		// Reconstruct the parent path.
		return reconstructPath(parentMap, origin, destination);
	}
	
	//// HELPER METHODS ////
	
	/**
	 * Check validity of inputs.
	 * @param theOrigin the origin point.
	 * @param theDestination the destination point.
	 */
	void validate(final GeographicPoint theOrigin, final GeographicPoint theDestination) {
		// Check validity of inputs.
		if (theOrigin == null || theDestination == null)
			throw new NullPointerException("Cannot find route from or to null node");
	}
	
	/**
	 * Store the NodeData objects visited for quick future access. If the object exists, retrieve it so you can read & write its fields.
	 * @param theMap the HashMap of NodeData objects that have been visited.
	 * @param thePoint the GeographicPoint object of the edge's endpoint presently being evaluated.
	 * @param theDestination the GeographicPoint object of the destination.
	 * @return the NodeData object in the HashMap, or if not exist a new NodeData object.
	 */
	private NodeData getNodeObject(final HashMap<GeographicPoint, NodeData> theMap, final GeographicPoint thePoint, final GeographicPoint theDestination) {
		NodeData neighbor = null;
		if (theMap.containsKey(thePoint)) {
			neighbor = theMap.get(thePoint);
		} else {
			neighbor = new NodeData(thePoint);
			neighbor.heuristic = neighbor.point.distance(theDestination);
			theMap.put(thePoint, neighbor);
		}
		
		return neighbor;
	}

}
