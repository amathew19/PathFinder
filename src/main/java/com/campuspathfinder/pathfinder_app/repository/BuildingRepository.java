package com.campuspathfinder.pathfinder_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campuspathfinder.pathfinder_app.models.Building;

public interface BuildingRepository extends JpaRepository<Building, Integer> {

}
