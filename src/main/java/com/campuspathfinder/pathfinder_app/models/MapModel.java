package com.campuspathfinder.pathfinder_app.models;

import java.util.*;

import com.campuspathfinder.pathfinder_app.util.MapParser;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.*;

public class MapModel {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String universtiy;
	private Graph<Building, Double> model = new Graph<>();
	private Map<Building, Set<Integer>> pathways = new HashMap<Building, Set<Integer>>();
	private Map<Integer, Building> buildingsById = new HashMap<Integer, Building>();
	private Map<String, Building> buildingsByName = new HashMap<String, Building>();
	
	public MapModel() {
		universtiy = "";
		this.model = new Graph<>();
		this.pathways = new HashMap<>();
		this.buildingsById = new HashMap<>();
		this.buildingsByName = new HashMap<>();
	}
	
	public String findDirection(Building b1, Building b2) {		
		double dx = b2.getBldgXCoord() - b1.getBldgXCoord();
		double dy = b2.getBldgYCoord() - b1.getBldgYCoord();
		
		double angleInRadians = Math.atan2(dy, dx);
        double angleInDegrees = Math.toDegrees(angleInRadians);
        angleInDegrees = angleInDegrees - 270;
        if(angleInDegrees < 0){
        	angleInDegrees += 360;
        }
       
        
        if (angleInDegrees >= 22.5 && angleInDegrees < 67.5) {
            return "NorthEast";
        } else if (angleInDegrees >= 67.5 && angleInDegrees < 112.5) {
            return "East";
        } else if (angleInDegrees >= 112.5 && angleInDegrees < 157.5) {
            return "SouthEast";
        } else if (angleInDegrees >= 157.5 && angleInDegrees < 202.5) {
            return "South";
        } else if (angleInDegrees >= 202.5 && angleInDegrees < 247.5) {
            return "SouthWest";
        } else if (angleInDegrees >= 247.5 && angleInDegrees < 292.5) {
            return "West";
        } else if (angleInDegrees >= 292.5 && angleInDegrees < 337.5) {
            return "NorthWest";
        }
        else {
        	return "North";
        }

	}
	
	public MapModel(String bldgFile, String edgesFile) {
		this.model = new Graph<>();
		this.universtiy = "";
		this.pathways = new HashMap<>();
		this.buildingsById = new HashMap<>();
		this.buildingsByName = new HashMap<>();
		 try {
			 	MapParser.readBuildings(bldgFile, buildingsById, buildingsByName);
				MapParser.readEdges(edgesFile, pathways, buildingsById);
		        
		        for (Building bldg : buildingsById.values()) {
		            model.addNode(bldg);  
		        }
		        
		        
		        for (Building b1 : pathways.keySet()) {
		            Set<Integer> paths = pathways.get(b1);
		            
		            for(Integer id: paths) {
		            	Building b2 = buildingsById.get(id);
		            	int dx = Math.abs(b1.getBldgXCoord() - b2.getBldgXCoord());
		            	int dy = Math.abs(b1.getBldgYCoord() - b2.getBldgYCoord());
		            	double weight = Math.sqrt(dx * dx + dy * dy);
		            	model.addEdge(b1, b2, weight);
		            }
		        }
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	}
	
//	public void createMap(String bldgFile, String edgesFile){
//		model = new GraphADT<>();
//		 try {
//			 	MapParser.readBuildings(bldgFile, buildingsById, buildingsByName);
//				MapParser.readEdges(edgesFile, pathways, buildingsById);
//		        
//		        for (Building bldg : buildingsById.values()) {
//		            model.addNode(bldg);  
//		        }
//		        
//		        
//		        for (Building b1 : pathways.keySet()) {
//		            Set<Integer> paths = pathways.get(b1);
//		            
//		            for(Integer id: paths) {
//		            	Building b2 = buildingsById.get(id);
//		            	int dx = Math.abs(b1.getBldgXCoord() - b2.getBldgXCoord());
//		            	int dy = Math.abs(b1.getBldgYCoord() - b2.getBldgYCoord());
//		            	double weight = Math.sqrt(dx * dx + dy * dy);
//		            	model.addEdge(b1, b2, weight);
//		            }
//		        }
//			}
//			catch (IOException e) {
//				e.printStackTrace();
//			}
//	}
	
	public String getUniversityName() {
		return universtiy;
	}
	
	public Building getBuildingByID(int id) {
		if(!buildingsById.get(id).getBldgName().equals(""))
			return buildingsById.get(id);
		else 
			return null;
	}
	
	public Building getBuildingByName(String name) {
		return buildingsByName.get(name);
	}
	
	public TreeSet<Building> allBuildings(){
		TreeSet<Building> allBuildings = new TreeSet<>(new Comparator<Building>() {
			public int compare(Building b1, Building b2) {
				return b1.getBldgName().compareTo(b2.getBldgName());
			}
		});
		
		for(Node<Building> b : model.getAllNodes()) {
			if(b.getNodeData().getBldgName().equals("")) continue;
			allBuildings.add(b.getNodeData());
		}
		return allBuildings;
	}
	
	//FOR DEBUGGING PURPOSES
//	public void printMap() {
//		for(Node<Building> b: model.getAllNodes()) {
//			System.out.println("Building: " + b.getNodeData().getBldgId());
//			List<Node<Building>> children = model.listChildren(b.nodeData);
//			for(Node<Building> n: children) {
//				if (n.getNodeData().getBldgName().equals("")) {
//				    System.out.println("\t Intersection: " + n.getNodeData().getBldgId() + " | Edge Weight: " + model.getEdgeLabel(b, n));
//				} else {
//				    System.out.println("\t Children Buildings: " + n.getNodeData().getBldgId() + " | Edge Weight: " + model.getEdgeLabel(b, n));
//				}
//			}
//		}
//	}
	
	public double getCost(Node<Building> b1, Node<Building> b2) {
		return model.getEdgeLabel(b1, b2);
	}
	
	public LinkedHashSet<Edge<Building, Double>> findRoute(Building b1, Building b2) {
		LinkedHashSet<Edge<Building, Double>> path = Graph.dijkstra(new Node<Building>(b1), new Node<Building>(b2), model);
		return path;
		
	}
	
//	public static void main(String[] args) throws IOException{
//		Model newMap = new Model("data/RPI_map_data_Nodes.csv","data/RPI_map_data_Edges.csv");
//		Map<Integer, Building> buildings = new HashMap<>();
//		Map<String, Building> buildingMap = new HashMap<>();
//		
//		MapParser.readBuildings("data/RPI_map_data_Nodes.csv", buildings, buildingMap);
//		
//		//newMap.createMap("data/RPI_map_data_Nodes.csv","data/RPI_map_data_Edges.csv");
//		//newMap.printMap();
//		Building b1 = buildingMap.get("EMPAC");
//		Building b2 = buildingMap.get("Academy Hall");
//		System.out.println(newMap.findRoute(b1, b2));
//	}
	
	
}

