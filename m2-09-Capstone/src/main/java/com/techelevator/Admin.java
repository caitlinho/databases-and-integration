package com.techelevator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.Site;
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
		return parkDAO.getAllParks();
	}
	
	public List<Campground> displayAllCampgrounds() {	
		return campgroundDAO.getAllCampgrounds();
	}

	public List<Campground> displayCampgroundByParkId(int parkId) {
		return campgroundDAO.getAllCampgroundsByParkId(parkId);
	}
	
	public List<Site> getTop5AvailableSitesByDate(int campgroundId, LocalDate fromDate, LocalDate toDate) {
		return siteDAO.getTop5AvailableSitesByDate(campgroundId, fromDate, toDate);
	}

}
