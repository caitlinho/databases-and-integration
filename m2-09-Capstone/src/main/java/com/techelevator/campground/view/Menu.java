package com.techelevator.campground.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import javax.activation.DataSource;
import javax.swing.plaf.metal.MetalBorders.Flush3DBorder;

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

	// public method for cli to call which park user chooses
	public Object getChoiceFromMainParkMenu() {
		Object choice = null;
		while (choice == null) {
			displayMainParkMenuOptions(parks);
			choice = getChoiceFromUserInputFromMainParkMenu();
		}
		return choice;
	}

	// in takes the user input
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

	// creates the park menu options
	private void displayMainParkMenuOptions(List<Park> parks) {
		out.println();
		printHeading("Select a Park for Further Details");
		int optionNum = 0;
		for (Park park : parks) {
			optionNum++;
			out.println(optionNum + ") " + park.getName());
		}
		out.println("Q) Quit");
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	// displays the chosen park info
	public void getParkInfo() {
		printHeading("Parks Info Screen");
		out.println(admin.displayAllParks().get(chosenPark).getName());
		out.println(admin.displayAllParks().get(chosenPark).getLocation());
		out.println(admin.displayAllParks().get(chosenPark).getEstablishDate());
		out.println(admin.displayAllParks().get(chosenPark).getArea());
		out.println(admin.displayAllParks().get(chosenPark).getVisitors());
		out.println(admin.displayAllParks().get(chosenPark).getDescription());
		out.flush();
	}

	// display the camp ground menu
	public void displayMainCampgroundMenu(Object[] choices) {
		out.println();
		printHeading("Select a Command");
		for (int i = 0; i < choices.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + choices[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	// gets user's choice from camp ground menu
	public Object getChoiceFromCampgroundMenu(Object[] choices) {
		Object choice = null;
		while (choice == null) {
			displayMainCampgroundMenu(choices);
			choice = getChoiceFromUserInputFromCampgroundMenu(choices);
		}
		return choice;
	}

	// actually deals with the user input
	private Object getChoiceFromUserInputFromCampgroundMenu(Object[] choices) {
		Object choice = null;
		String userInput = in.nextLine();
		try {

			//chosenCampground = Integer.parseInt(userInput) - 1;

			int chosenChoice = Integer.valueOf(userInput);
			if (chosenChoice > 0 && chosenChoice <= campgrounds.size()) {
				choice = campgrounds.get(chosenChoice -1);
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

	public void getCampgroundInfo() {
		for (Object campground : campgrounds) {
			out.println(campground.toString());
//			out.println(admin.displayAllCampgrounds().get(chosenCampground).getCampgroundId());
		}
		out.flush();
	}

	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}

}
