package com.techelevator.campground.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import javax.activation.DataSource;

import com.techelevator.Admin;
import com.techelevator.CampgroundCLI;
import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Park;

public class Menu {
	
	private PrintWriter out;
	private Scanner in;
	private int chosenPark;
	private int chosenCampground;
	private CampgroundCLI cli;
	private Admin admin = new Admin();
	private List<Park> parks = admin.displayAllParks();
	private List<Campground> campgrounds = admin.displayAllCampgrounds();

	
	

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromMainParkMenu() {
		Object choice = null;
		while(choice == null) {
			displayMainParkMenuOptions(parks);
			choice = getChoiceFromUserInputFromMainParkMenu();
		}
		return choice;
	}
	
	public void displayingAllParks() {
		out.println(parks);
	}
	
	public void getParkInfo() {
		out.println(admin.displayAllParks().get(chosenPark).getName());
		out.println(admin.displayAllParks().get(chosenPark).getLocation());
		out.println(admin.displayAllParks().get(chosenPark).getEstablishDate());
		out.println(admin.displayAllParks().get(chosenPark).getArea());
		out.println(admin.displayAllParks().get(chosenPark).getVisitors());
		out.println(admin.displayAllParks().get(chosenPark).getDescription());
		out.flush();
	}
	
	private Object getChoiceFromUserInputFromMainParkMenu() {
		Object choice = null;
		String userInput = in.nextLine();
		try {
		
			chosenPark = Integer.parseInt(userInput) - 1;

			if (userInput == "Q") {
				System.exit(0);
			}
			int chosenChoice = Integer.valueOf(userInput);
			if (chosenChoice > 0 && chosenChoice <= parks.size()) {
				choice = chosenChoice;
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}	

	private void displayMainParkMenuOptions(List<Park> parks) {
		out.println();
		printHeading("Select a Park for Further Details");
		int optionNum = 0;
		for(Park park : parks) {
			optionNum++;
			out.println(optionNum + ") " + park.getName());
		}
		out.println("Q) Quit");
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}
	
	public Object getChoiceFromCampgroundMenu(Object[] choices) {
		Object choice = null;
		while(choice == null) {
			displayCampgroundMenuOptions(campgrounds);
			choice = getChoiceFromUserInputFromMainParkMenu();
		}
		return choice;
	}

	private void displayCampgroundMenuOptions(List<Campground> campgrounds) {
		out.println();
		printHeading("Park Campgrounds");
		int optionNum = 0;
		for(Campground campground : campgrounds) {
			optionNum++;
			out.println(optionNum + ") " + campground.getName());
		}
		out.println("3) Return to Previous Screen");
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	private Object getChoiceFromUserInputFromCampgroundkMenu(Object[] choices) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
		
			chosenCampground = Integer.parseInt(userInput) - 1;

			if (userInput == "Q") {
				System.exit(0);
			}
			int chosenChoice = Integer.valueOf(userInput);
			if (chosenChoice > 0 && chosenChoice <= parks.size()) {
				choice = chosenChoice;
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}	

	public void displayingAllCampgrounds() {
		out.println(campgrounds);
	}

	public void getCampgroundInfo() {
		out.println(admin.displayAllCampgrounds().get(chosenCampground).getCampgroundId());
		out.println(admin.displayAllCampgrounds().get(chosenCampground).getName());
		out.println(admin.displayAllCampgrounds().get(chosenCampground).getOpenFromMonth());
		out.println(admin.displayAllCampgrounds().get(chosenCampground).getOpenToMonth());
		out.println(admin.displayAllCampgrounds().get(chosenCampground).getDailyFee());
		out.flush();
	}

	
	
	
	
	
	
	
	
	
	private void printHeading(String headingText) {
		System.out.println("\n"+headingText);
		for(int i = 0; i < headingText.length(); i++) {
		System.out.print("-");
		}
		System.out.println();
	}
	

	
}	
	
	