package com.techelevator.campground.model;

import java.util.List;

public interface CampgroundDAO {
	
	//Display list of all camp grounds in the specific park.
	public List<Campground> getAllCampgrounds();
}
