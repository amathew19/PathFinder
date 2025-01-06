package com.campuspathfinder.pathfinder_app.models;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Comparator;

/** <b>GraphADT</b> represents a <b>mutable</b> abstract data type for a 
    directed labeled multigraph. It includes methods for managing 
    nodes and edges, as well as finding paths between nodes. 
    The graph can contain reflexive edges, multiple edges between 
    nodes, and labeled edges.
    <p>
    Nodes in the graph are represented by unique identifiers 
    of type String. Edges in the graph are directed and labeled, 
    connecting two nodes. The label of an edge is a number/Integer 
    associated with the connection.
    <p>
    Examples of graphs include airline flight networks, 
    campus maps, web page link structures, and social networks like Facebook.
    
    Additionally, as part of Homework 6, the GraphADT class has two type parameters, 
    where NT represents the data type of Node, and ET represents the 
    data type of the Edge Label.
*/

public class Graph<NT extends Comparable<NT>, ET extends Comparable<ET>> {
    @SuppressWarnings("unused")
    public final HashMap<Node<NT>,HashMap<Node<NT>, ET>> Edges;
    
    /* Abstraction Function:
       A GraphADT g is a directed labeled multigraph where g.Edges 
       represent a list of all directed and reflexive edges between nodes
       
       In other words,
         * the number of edges in a graph is non-negative.
    */
    
    /**
     * @requires none
     * @modifies none
     * @effects Constructs a new GraphADT object.
     */
    public Graph() {
        Edges = new HashMap<Node<NT>,HashMap<Node<NT>, ET>>();
    }
    
    
    /**
     * Checks that the representation invariant holds (if any).
     * @throws RuntimeException
     **/
//  private void checkRep() throws RuntimeException {
//         for (Map.Entry<Node<NT>,HashMap<Node<NT>, ET>> Nodes : Edges.entrySet()){
//        	 HashMap<Node<NT>, ET> edges = Nodes.getValue();
//        	 Node<NT> sourceNode = Nodes.getKey();
//    			for(Map.Entry<Node<NT>, ET> edge : edges.entrySet()) {
//    				Node<NT> targetNode = edge.getKey();
//    				ET edgeWeight = edge.getValue();
//    				if (sourceNode == null || targetNode == null || edgeWeight == null)
//    					throw new RuntimeException("Graph is not Valid!");
//    				else if (sourceNode != null || targetNode != null || edgeWeight != null)
//    					return;
//			}
//         }
//    }
    
    /**
     * Add a node to the graph.
     * @modifies none
     * @effects g + node
     * @param node The identifier of the node to be added.
     * @returns none
     */
    public void addNode(NT node) {
        Node<NT> newNode = new Node<>(node);
        Edges.putIfAbsent(newNode, new HashMap<>());
    }

    /**
     * Remove a node from the graph.
     *
     * @param node The identifier of the node to be removed.
     * @returns none
     */
    public void removeNode(NT node) {
        Node<NT> nodeToRemove = new Node<>(node);
        Edges.remove(nodeToRemove);
    }

    /**
     * Add a directed edge with label between two nodes.
     *
     * @param sourceNode The identifier of the source node.
     * @param targetNode The identifier of the target node.
     * @param label      The label associated with the edge.
     * @returns none
     */
    public void addEdge(NT sourceNodeID, NT targetNodeID, ET label) {
        Node<NT> sourceNode = new Node<>(sourceNodeID);
        Node<NT> targetNode = new Node<>(targetNodeID);
 
        HashMap<Node<NT>, ET> edgesMap = Edges.getOrDefault(sourceNode, new HashMap<>());
        edgesMap.put(targetNode, label);
        Edges.put(sourceNode, edgesMap);
    }

    /**
     * Remove a directed edge between two nodes with a specific label.
     *
     * @param sourceNode The identifier of the source node.
     * @param targetNode The identifier of the target node.
     * @param label      The label associated with the edge to be removed.
     * @returns none
     */
    public void removeEdge(NT sourceNodeID, NT targetNodeID, ET label) {
        Node<NT> sourceNode = new Node<>(sourceNodeID);
        Node<NT> targetNode = new Node<>(targetNodeID);
        HashMap<Node<NT>, ET> edgesMap = Edges.getOrDefault(sourceNode, new HashMap<>());
        if(edgesMap.get(targetNode).equals(label)) {
            edgesMap.remove(targetNode);
            return;
        }
    }

    /**
     * Check if a node exists in the graph.
     *
     * @param node The identifier of the node to check.
     * @return True if the node exists in the graph, false otherwise.
     */
    public boolean containsNode(NT node) {
        Node<NT> nodeToCheck = new Node<>(node);
        return Edges.containsKey(nodeToCheck);
    }

    /**
     * Check if an edge exists between two nodes with a specific label.
     *
     * @param sourceNode The identifier of the source node.
     * @param targetNode The identifier of the target node.
     * @param label      The label associated with the edge to check.
     * @return True if the edge exists between the nodes with the given label, false otherwise.
     */
    public boolean containsEdge(NT sourceNodeID, NT targetNodeID, ET label) {
        Node<NT> sourceNode = new Node<>(sourceNodeID);
        Node<NT> targetNode = new Node<>(targetNodeID);
        HashMap<Node<NT>, ET> edgesMap = Edges.getOrDefault(sourceNode, new HashMap<>());
        ET e = edgesMap.getOrDefault(targetNode, null);
        if (e == null) {
            return false;
        }
        else if(e.equals(label)) {
            return true;
        }
        return false;
    }

    private List<Node<NT>> reconstructPath(Node<NT> startNode, Node<NT> targetNode, Map<Node<NT>, Node<NT>> parentMap) {
        List<Node<NT>> path = new ArrayList<>();
        Node<NT> current = targetNode;
        
        while (current != null && !current.equals(startNode)) {
            path.add(0, current); 
            current = parentMap.get(current);
        }
        
        
        if (current != null) {
            path.add(0, startNode);
        }
        
        return path;
    }
    
    private List<Node<NT>> bfs(Node<NT> currentNode, Node<NT> targetNode) {
        
        Queue<Node<NT>> queue = new LinkedList<Node<NT>>();
        Set<Node<NT>> visited = new HashSet<Node<NT>>();
        List<Node<NT>> path = new ArrayList<Node<NT>>();
        Map<Node<NT>, Node<NT>> parentMap = new HashMap<Node<NT>, Node<NT>>();

        queue.add(currentNode);
        visited.add(currentNode);
        
        while (!queue.isEmpty()) {
            Node<NT> current = queue.poll();
            path.add(current);

            if (current.equals(targetNode)) {
                return reconstructPath(currentNode, targetNode, parentMap);
            }
            
            HashMap<Node<NT>, ET> edgesMap = Edges.getOrDefault(current, new HashMap<>());
            for(Map.Entry<Node<NT>, ET> entry : edgesMap.entrySet()) {
                Node<NT> neighbor = entry.getKey();
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }
        return null;
    }
    
    /**
     * Find a path between two nodes in the graph.
     *
     * @param sourceNode The identifier of the source node.
     * @param targetNode The identifier of the target node.
     * @return A list of nodes representing a path from the source node to the target node,
     *         or an empty list if no path exists.
     */
    public List<Node<NT>> findPath(NT sourceNodeID, NT targetNodeID) {
        //throw new RuntimeException("GraphADT.findPath() is not yet implemented");
        Node<NT> sourceNode = new Node<>(sourceNodeID);
        Node<NT> targetNode = new Node<>(targetNodeID);
        List<Node<NT>> path = bfs(sourceNode, targetNode);
        return path;
    }

    /**
     * Lists the children that is outgoing from a code
     *
     * @param the identifier of the source/first node
     * @return A list of nodes representing the children of node
     */
    public List<Node<NT>> listChildren(NT nodeID){
        //throw new RuntimeException("Graph.listChildren(0 is not yet implemented");
        Node<NT> node = new Node<>(nodeID);
        List<Node<NT>> children = new LinkedList<>();
        HashMap<Node<NT>, ET> edgesMap = Edges.getOrDefault(node, new HashMap<>());
        for(Map.Entry<Node<NT>, ET> entry : edgesMap.entrySet()) {
            children.add(entry.getKey());
        }
        return children;
    }
    
    /**
     * Get all nodes in the graph.
     *
     * @return A list containing all the nodes in the graph.
     */
    public List<Node<NT>> getAllNodes() {
        return new LinkedList<>(Edges.keySet());
    }
    
    /**
     * Get the Edges connected to a Node
     *
     * @param Node the source node.
     * @return the linked list of edges,
     *         or null if no such list exists.
     */
    public LinkedList<Edge<NT, ET>> getEdges(Node<NT> node) {
        LinkedList<Edge<NT, ET>> nodeEdges = new LinkedList<>();
        HashMap<Node<NT>, ET> edgesMap = Edges.getOrDefault(node, new HashMap<>());
        for(Map.Entry<Node<NT>, ET> entry : edgesMap.entrySet()) {
            nodeEdges.add(new Edge<NT, ET>(node, entry.getKey(), entry.getValue()));
        }
        return nodeEdges; 
    }
    
    /**
     * Get the label of an edge between two nodes.
     *
     * @param sourceNode The identifier of the source node.
     * @param targetNode The identifier of the target node.
     * @return The label associated with the edge between the given nodes,
     *         or null if no such edge exists.
     */
    public ET getEdgeLabel(Node<NT> sourceNode, Node<NT> targetNode) {
        HashMap<Node<NT>, ET> edgesMap = Edges.getOrDefault(sourceNode, new HashMap<>());
        ET min = edgesMap.getOrDefault(targetNode, null);
        return min;
    }
    
    private static <N extends Comparable<N>> double calculatePathCost(LinkedHashSet<Edge<N, Double>> path) {
	    double pathCost = 0.0;
	    for (Edge<N, Double> edge : path) {
	        pathCost += edge.getLabel();
	    }
	    return pathCost;
	}
    
    public static <N extends Comparable<N>> LinkedHashSet<Edge<N, Double>> dijkstra(Node<N> startNode, Node<N> destNode, Graph<N,Double> legoPaths) {
    	HashSet<Node<N>> finished = new LinkedHashSet<>();
    	LinkedHashSet<Edge<N, Double>> startPath = new LinkedHashSet<>();
    	PriorityQueue<LinkedHashSet<Edge<N, Double>>> active = new PriorityQueue<LinkedHashSet<Edge<N, Double>>>(new Comparator<LinkedHashSet<Edge<N, Double>>>() {
    		public int compare(LinkedHashSet<Edge<N, Double>> n1, LinkedHashSet<Edge<N, Double>> n2) {
                double pathCostN1 = calculatePathCost(n1);
                double pathCostN2 = calculatePathCost(n2);
                return Double.compare(pathCostN1, pathCostN2);
            }});
    	
        startPath.add(new Edge<>(startNode, startNode, 0.0));
        active.add(startPath);
        
        while (!active.isEmpty()) {
        	LinkedHashSet<Edge<N, Double>> minPath = active.poll();
        	List<Edge<N, Double>> listPath = new ArrayList<>(minPath);
            Node<N> minDest = listPath.get(listPath.size() - 1).getTargetNode();
            
            if (minDest.equals(destNode)) return minPath;
            
            if (finished.contains(minDest)) continue;
            
            List<Node<N>> children = legoPaths.listChildren(minDest.getNodeData());
            for(Node<N> child: children) {
                if (!finished.contains(child)) {
                    LinkedHashSet<Edge<N, Double>> newPath = new LinkedHashSet<>(minPath);
                    newPath.add(new Edge<>(minDest, child, legoPaths.getEdgeLabel(minDest, child)));
                    active.add(newPath);
                }
            }
            finished.add(minDest);
        }
        
        return new LinkedHashSet<>(); // No path found
    }
    
}