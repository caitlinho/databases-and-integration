package com.techelevator.campground.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {
	
	//List of Top 5 available sites to book 
	public List<Site> getTop5AvailableSitesByDate(LocalDate fromDate, LocalDate toDate);
	
//	//Displaying cost
//	public BigDecimal getTotalCostOfReservation(long campgroundId, long lengthOfStay);

}
