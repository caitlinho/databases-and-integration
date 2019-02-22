package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
	
	// Returns confirmation id to user 
	public Long addReservation(Reservation reservation);
	
	//Shows Active Reservations
	public Reservation getActiveReservation(Long reservationId);
	
	//Cancels Reservation and deletes form db
	public void cancelReservation(Long reservationId);

	
	
}
