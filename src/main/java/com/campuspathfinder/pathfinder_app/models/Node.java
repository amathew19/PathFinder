package com.campuspathfinder.pathfinder_app.models;

/**
 * <b>Node</b> represents a node in a graph.
 * Each node has a unique identifier represented by a String.
 * Nodes are used as endpoints for edges in the graph.
 * (It is a part of the GraphADT)
 */

public class Node<T> {
	public final T nodeData;
	
	public Node() {
		nodeData = null;
	}
	
	public Node(Node<T> n) {
		this.nodeData = n.nodeData;
	}
	
	public Node(T nodeData) {
		this.nodeData = nodeData;
	}
	
	public String toString() {
		return nodeData.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Node<?> other = (Node<?>) obj;
        return nodeData.equals(other.nodeData);
    }
	
	@Override
	public int hashCode() {
        int hash = 17; 
        hash = 31 * hash + nodeData.hashCode();
        return hash;
    }
	
	public T getNodeData() {
        return nodeData;
    }
}
