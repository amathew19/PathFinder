package com.campuspathfinder.pathfinder_app.models;
import com.campuspathfinder.pathfinder_app.models.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="buildings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Building implements Comparable<Building> {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private String type;
	
	@Column(nullable=false)
	private List<Node> accessPoints;
	
	public Building() {
		this.name = "";
		this.id = 0;
		this.accessPoints = new ArrayList<>();
	}
	
	public Building(String name, int id, double latitude, double longitude, List<Node> accessPoints) {
		this.name = name;
		this.id = id;
		this.accessPoints = accessPoints;
	}
	
	public String getBldgName() {
		return this.name;
	}
	
	public int getBldgId() {
		return this.id;
	}
	
	public List<Node> getAccessPoints() {
		return this.accessPoints;
	}
	
	public int hashCode() {
		int hash = 5; 
        hash = 7 * hash + name.hashCode();
        hash = 21 * hash + Objects.hash(id);
        return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Building) {
	        Building other = (Building) obj;
	        return Objects.equals(id, other.id) && name.equals(other.name);
        } else return false;
    }
	
	public String toString() {
		return this.name + this.id;
	}
	
	@Override
	public int compareTo(Building o) {
		return this.name.compareTo(o.getBldgName());
	}

}

