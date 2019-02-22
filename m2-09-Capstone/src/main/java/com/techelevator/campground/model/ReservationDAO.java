package com.techelevator.campground.model;

import java.util.List;

public interface ReservationDAO {

	// Displays available campgrounds
	public List<Reservation> getAvailableCampgroundReservation();
	
	// Add to future site class 
//	// Displays available results of requested date range
//	public List<Reservation> searchForUserRequestedInfo();
	
	// Returns confirmation id to user 
	public Long returningConfirmationId();
	
	public Reservation getReservationId(Long reservationId);
	
	public void cancelReservation(Long reservationId);
	
}
