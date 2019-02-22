package com.techelevator;

import java.util.List;

import javax.sql.DataSource;



import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.view.Menu;

public class CampgroundCLI {
	
	private static final String MAIN_PARK_MENU_OPTION_DISPLAY_ACADIA= "Acadia";
	private static final String MAIN_PARK_MENU_OPTION_DISPLAY_ARCHES = "Arches";
	private static final String MAIN_PARK_MENU_OPTION_DISPLAY_CUYAHOGA_NATIONAL_VALLEY_PARK = "Cuyahoga National Valley Park"; 
	private static final String[] MAIN_PARK_MENU_OPTIONS = {MAIN_PARK_MENU_OPTION_DISPLAY_ACADIA,
													   MAIN_PARK_MENU_OPTION_DISPLAY_ARCHES,
													   MAIN_PARK_MENU_OPTION_DISPLAY_CUYAHOGA_NATIONAL_VALLEY_PARK};
	
	private static final String SELECT_A_COMMAND_OPTION_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String SELECT_A_COMMAND_OPTION_VIEW_SEARCH_FOR_RESERVATION = "Search for Reservation";
	private static final String SELECT_A_COMMAND_OPTION_VIEW_RETURN_TO_PREVIOUS_SCREEN= "Return to Previous Screen";
	private static final String[] SELECT_A_COMMAND_OPTIONS = {SELECT_A_COMMAND_OPTION_VIEW_CAMPGROUNDS,
															  SELECT_A_COMMAND_OPTION_VIEW_SEARCH_FOR_RESERVATION,
															  SELECT_A_COMMAND_OPTION_VIEW_RETURN_TO_PREVIOUS_SCREEN};
	private Menu menu;
	private Admin admin;
	

	public static void main(String[] args) {
		
		Menu menu = new Menu(System.in, System.out);
		
		CampgroundCLI application = new CampgroundCLI(menu);
		application.run();
	}

	public CampgroundCLI(Menu menu) {
		admin = new Admin();
		this.menu = menu;
	}
	
	public void run() {
		
		List<Park> parks = admin.displayAllParks();
		
		Park userParkChoicePark = menu.getChoiceFromMainParkMenu();
		menu.
	
		
	}
}
