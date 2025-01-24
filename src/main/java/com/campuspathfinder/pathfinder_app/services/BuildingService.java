package com.campuspathfinder.pathfinder_app.services;
import com.campuspathfinder.pathfinder_app.models.Building;
import com.campuspathfinder.pathfinder_app.repository.BuildingRepository;

import java.util.List;

public class BuildingService {
	public List<Building> getAllBuildingsByUniversity(String university){
		// First find the University Name using the University DB
		return BuildingRepository.findById(university);
	}
}
