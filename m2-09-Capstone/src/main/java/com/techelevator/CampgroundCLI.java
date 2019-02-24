package com.techelevator;

import javax.sql.DataSource;



import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.view.Menu;

public class CampgroundCLI {
	
	
	private static final String CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String CAMPGROUND_MENU_OPTION_VIEW_SEARCH_FOR_RESERVATION = "Search for Reservation";
	private static final String CAMPGROUND_MENU_OPTION_VIEW_RETURN_TO_PREVIOUS_SCREEN= "Return to Previous Screen";
	private static final String[] CAMPGROUND_MENU_OPTIONS = {CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS,
																		CAMPGROUND_MENU_OPTION_VIEW_SEARCH_FOR_RESERVATION,
																		CAMPGROUND_MENU_OPTION_VIEW_RETURN_TO_PREVIOUS_SCREEN};
	
	private static final String RESERVATION_MENU_OPTION_SEARCH_FOR_AVAILABLE_RESERVATION = "Search for Available Reservation";
	private static final String RESERVATION_MENU_OPTION_VIEW_RETURN_TO_PREVIOUS_SCREEN= "Return to Previous Screen";
	private static final String[] RESERVATION_MENU_OPTIONS = {RESERVATION_MENU_OPTION_SEARCH_FOR_AVAILABLE_RESERVATION,
															RESERVATION_MENU_OPTION_VIEW_RETURN_TO_PREVIOUS_SCREEN};
	
	private static Menu menu;
	

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		CampgroundCLI application = new CampgroundCLI(menu);
		application.run();
	}

	public CampgroundCLI(Menu menu) {
		this.menu = menu;
	}
	
	public void run() {
		menu.getChoiceFromMainParkMenu();
		menu.getParkInfo();
					
			while(true) {
				String choice = String.valueOf(menu.getChoiceFromCampgroundMenu(CAMPGROUND_MENU_OPTIONS));
//				break;
//				if (choice.equals(CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS)) {
//					menu.get;
//						
//				} else if(choice.equals(CAMPGROUND_MENU_OPTION_VIEW_SEARCH_FOR_RESERVATION)) {
//					
//				} else if(choice.equals(CAMPGROUND_MENU_OPTION_VIEW_RETURN_TO_PREVIOUS_SCREEN)) {
//					
//			}
				while(true) {
					String choice2 = String.valueOf(menu.getChoiceFromReservationMenu(RESERVATION_MENU_OPTIONS));
					//break;
				}	
					
				
	
	}
		


	
	
	
	
	
	}
}

