package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {
	
	//List of Top 5 available sites to book 
	public List<Site> getTop5AvailableSitesByDate(LocalDate fromDate, LocalDate toDate);
	
	

}
