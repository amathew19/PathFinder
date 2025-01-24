package com.campuspathfinder.pathfinder_app.models;

import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * <b>Node</b> represents a node in a graph.
 * Each node has a unique identifier represented by a String.
 * Nodes are used as endpoints for edges in the graph.
 * (It is a part of the GraphADT)
 */

@Entity
@Table(name="nodes")
public class Node {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String nodeId;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private double latitude;
	
	@Column(nullable=false)
	private double longitude;
	
	@Column(nullable=false)
	private boolean accessible;
	
	@Column(nullable=false)
	private Map<Node, Double> neighbors;
	
	@Column(nullable=false)
	private Building parentBuilding;
	
	public Node() {
		id = (long) 0;
		name = "";
		
	}
	
	public Node(Node n) {
		this.nodeData = n.nodeData;
	}
	
	public Node( nodeData) {
		this.nodeData = nodeData;
	}
	
	public String toString() {
		return name + latitude;
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
