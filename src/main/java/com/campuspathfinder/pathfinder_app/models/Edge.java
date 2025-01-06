package com.campuspathfinder.pathfinder_app.models;

import java.util.Objects;

/** 
 * <b>Edge</b> represents a directed labeled edge in a graph, connecting two nodes.
 * Each edge has a source node, a target node, and a label.
 * (It is a part of the GraphADT)
 * @param <T>, NT is the type of data stored in node, and T is the type of data for the edge label
 */

public class Edge<NT extends Comparable<NT>, ET extends Comparable<ET>> implements Comparable<Edge<NT, ET>>{

	private final Node<NT> sourceNode;
	private final Node<NT> targetNode; 
	private final ET edgeLabel;
	
	
	public Edge() {
		sourceNode = new Node<NT>();
		targetNode = new Node<NT>();
		edgeLabel = null;
	}
	
	public Edge(Node<NT> sNode, Node<NT> tNode, ET label) {
        this.sourceNode = sNode;
        this.targetNode = tNode;
        this.edgeLabel = label;
    }
	
	public Node<NT> getSourceNode() {
        return sourceNode;
    }

    public Node<NT> getTargetNode() {
        return targetNode;
    }

    public ET getLabel() {
        return edgeLabel;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Edge<?, ?> other = (Edge<?, ?>) obj;
        return Objects.equals(sourceNode, other.sourceNode) && 
        		Objects.equals(targetNode, other.targetNode) &&
        		Objects.equals(edgeLabel, other.edgeLabel);
    }
    
    public String toString() {
        return "<" + sourceNode.getNodeData() + "," + targetNode.getNodeData() + ">";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((edgeLabel == null) ? 0 : edgeLabel.hashCode());
        result = prime * result + ((sourceNode == null) ? 0 : sourceNode.hashCode());
        result = prime * result + ((targetNode == null) ? 0 : targetNode.hashCode());
        return result;
    }
    
    @Override
    public int compareTo(Edge<NT,ET> other) {
        int compareProfessors = this.getTargetNode().getNodeData().compareTo(other.getTargetNode().getNodeData());
        if (compareProfessors != 0) {
            return compareProfessors;
        } else {
            return this.getLabel().compareTo(other.getLabel());
        }
    }

}
