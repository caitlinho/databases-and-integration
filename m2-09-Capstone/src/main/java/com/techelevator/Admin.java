package com.techelevator;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.SiteDAO;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.model.jdbc.JDBCParkDAO;
import com.techelevator.campground.model.jdbc.JDBCReservationDAO;
import com.techelevator.campground.model.jdbc.JDBCSiteDAO;

public class Admin {
	
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private SiteDAO siteDAO;
	private ReservationDAO reservationDAO;
	
	
	private DataSource dataSource;


	
	public Admin() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);
	}
	
	public List<Park> displayAllParks() {
		
		List<Park> parks = parkDAO.getAllParks();
		
		return parks;
	}
	
	public Park getChosenParkInfo(Park parkById) {
		Park park = new Park();
		Park parkInfo = parkDAO.getParkInformation(park.getName());
		return parkInfo;
		
	}
	
	public Park parkById(Long parkId) {
		Park park = new Park();
		Park selectedPark = parkDAO.getParkByParkId(park.getParkId());
		Park parkInfo = parkDAO.getParkInformation(selectedPark.getName());
		return parkInfo; 
	}



}
