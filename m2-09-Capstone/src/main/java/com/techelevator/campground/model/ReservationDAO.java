package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
	
	// Returns confirmation id to user 
	public int addReservation(int siteId, String name, LocalDate fromDate, LocalDate toDate, LocalDate createDate);
	
	//Shows Active Reservations
	public Reservation getActiveReservation(int reservationId);
	
	//Cancels Reservation and deletes form db
	public void cancelReservation(int reservationId);

	

	
	
}
