package com.techelevator.campground.model;

import java.util.List;

public interface ParkDAO {
	
	//Displays All Parks
	public List<Park> getAllParks();
	
	//Displays the selected park info
	public Park getParkInformation(String name);
	
	//Displays park by parkId
	public Park getParkByParkId(Long parkId);
	

}
