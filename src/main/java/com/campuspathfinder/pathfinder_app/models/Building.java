package com.campuspathfinder.pathfinder_app.models;

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
	
	@Column(name = "x_coord")
	private int x_coord;
	
	@Column(name = "y_coord")
	private int y_coord;
	
	public Building() {
		this.name = "";
		this.id = 0;
		this.x_coord = 0;
		this.y_coord = 0;
	}
	
	public Building(String name, int id, int x_coord, int y_coord) {
		this.name = name;
		this.id = id;
		this.x_coord = x_coord;
		this.y_coord = y_coord;
	}
	
	public String getBldgName() {
		return this.name;
	}
	
	public int getBldgId() {
		return this.id;
	}
	
	public int getBldgXCoord() {
		return this.x_coord;
	}
	
	public int getBldgYCoord() {
		return this.y_coord;
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

