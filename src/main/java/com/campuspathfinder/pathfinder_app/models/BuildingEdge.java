package com.campuspathfinder.pathfinder_app.models;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "edges")
@Data
public class BuildingEdge implements Comparable<BuildingEdge> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "source_building_id", nullable = false)
    private Building sourceBuilding;

    @ManyToOne
    @JoinColumn(name = "target_building_id", nullable = false)
    private Building targetBuilding;

    @Column(nullable = false)
    private Double weight;  // this replaces the generic edgeLabel for our specific case
    
    // Default constructor required by JPA
    public BuildingEdge() {}
    
    // Constructor that mirrors your Edge class constructor
    public BuildingEdge(Building source, Building target, Double weight) {
        this.sourceBuilding = source;
        this.targetBuilding = target;
        this.weight = weight;
    }
    
    // Convert to your generic Edge<Building, Double>
    public Edge<Building, Double> toEdge() {
        return new Edge<>(
            new Node<>(sourceBuilding), 
            new Node<>(targetBuilding), 
            weight
        );
    }
    
    // Create from your generic Edge<Building, Double>
    public static BuildingEdge fromEdge(Edge<Building, Double> edge) {
        return new BuildingEdge(
            edge.getSourceNode().getNodeData(),
            edge.getTargetNode().getNodeData(),
            edge.getLabel()
        );
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BuildingEdge other = (BuildingEdge) obj;
        return Objects.equals(sourceBuilding, other.sourceBuilding) &&
               Objects.equals(targetBuilding, other.targetBuilding) &&
               Objects.equals(weight, other.weight);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((weight == null) ? 0 : weight.hashCode());
        result = prime * result + ((sourceBuilding == null) ? 0 : sourceBuilding.hashCode());
        result = prime * result + ((targetBuilding == null) ? 0 : targetBuilding.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "<" + sourceBuilding + "," + targetBuilding + ">";
    }

    @Override
    public int compareTo(BuildingEdge other) {
        int compareBuildings = this.targetBuilding.compareTo(other.targetBuilding);
        if (compareBuildings != 0) {
            return compareBuildings;
        } else {
            return this.weight.compareTo(other.weight);
        }
    }
}
