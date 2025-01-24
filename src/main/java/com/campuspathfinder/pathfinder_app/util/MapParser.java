package com.campuspathfinder.pathfinder_app.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.campuspathfinder.pathfinder_app.models.Building;

public class MapParser {
	
	public static void readEdges(String filename, Map<Building, Set<Integer>> pathways, Map<Integer, Building> buildings) 
			throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line = null;

			while ((line = reader.readLine()) != null) {
				String[] objectIds = line.split(",");
				
				if (objectIds.length < 2) {
					throw new IOException("File " + filename + " not a CSV (ID1,ID2) file.");
				}
				
				int id1 = Integer.parseInt(objectIds[0].trim());
				int id2 = Integer.parseInt(objectIds[1].trim());
				
				Building b1 = buildings.get(id1);
				Building b2 = buildings.get(id2);
				Set<Integer> i1 = pathways.get(b1);
				Set<Integer> i2 = pathways.get(b2);
				
				if(i1 == null) {
					i1 = new HashSet<Integer>();
					pathways.put(b1, i1);
				}
				i1.add(id2);
				
				if(i2 == null) {
					i2 = new HashSet<Integer>();
					pathways.put(b2, i2);
				}
				
				i2.add(id1); 
			}
		}
	}
	
	
	/**
	 * @param: filename     The path to a "CSV" file that contains the
	 *                      "professor","course" pairs
	 * @param: profsTeaching The Map that stores parsed <course,
	 *                      Set-of-professors-that-taught> pairs; usually an empty Map.
	 * @param: profs        The Set that stores parsed professors; usually an empty
	 *                      Set.
	 * @requires: filename != null && profsTeaching != null && profs != null
	 * @modifies: profsTeaching, profs
	 * @effects: adds parsed <course, Set-of-professors-that-taught> pairs to Map
	 *           profsTeaching; adds parsed professors to Set profs.
	 * @throws: IOException if file cannot be read or file not a CSV file following
	 *                      the proper format.
	 * @returns: None
	 */
	public static void readBuildings(String filename, Map<Integer, Building> buildings, Map<String, Building> buildingsMap)
			throws IOException {

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line = null;

			while ((line = reader.readLine()) != null) {
				String[] bldgData = line.split(",");
				
				if (bldgData.length < 4) {
					throw new IOException("File " + filename + " not a CSV (BUILDING NAME,BUILDING ID,X-COORDINATE,Y-COORDINATE) file.");
				}
				
				String buildingName = bldgData[0].trim();
	            int buildingId = Integer.parseInt(bldgData[1].trim());
	            int xCoord = Integer.parseInt(bldgData[2].trim());
	            int yCoord = Integer.parseInt(bldgData[3].trim());
	            
//	            StringBuilder sb = new StringBuilder();
//	            sb.append(buildingName).append(",").append(buildingId).append(",").append(xCoord).append(",").append(yCoord);
//	            System.out.println(sb);
	            
	            Building bldg = new Building(buildingName, buildingId, xCoord, yCoord);
	            buildings.put(buildingId, bldg);
	            buildingsMap.put(buildingName, bldg);
			}
		}
	}

	
	
	public static void main(String[] arg) {

		//String file1 = arg[0];
		//String file2 = arg[1];
		String file1 = "src/main/resources/data/RPI_map_data_Nodes.csv";
		String file2 = "src/main/resources/data/RPI_map_data_Edges.csv";

		try {
			Map<Building, Set<Integer>> pathways = new HashMap<Building, Set<Integer>>();
			Map<Integer, Building> buildings = new HashMap<Integer, Building>();
			Map<String, Building> buildingsMap = new HashMap<String, Building>();
			readBuildings(file1,buildings, buildingsMap);
			readEdges(file2, pathways, buildings);
			
			
			for (Map.Entry<Building, Set<Integer>> entry : pathways.entrySet()) {
		            Integer key = entry.getKey().getBldgId();
		            Set<Integer> value = entry.getValue();
		            System.out.println("Key: " + key + " | Values: " + value);
		    }
			
			System.out.println(
					"Read " + buildings.size() + " buildings with " + pathways.keySet().size() + " paths.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

