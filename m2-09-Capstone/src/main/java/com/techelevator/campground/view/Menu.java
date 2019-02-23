package com.techelevator.campground.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
	private List<Campground> campgrounds;

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
		out.println(admin.displayAllParks().get(chosenPark).getName() + " National Park");
		out.println("Location: " + admin.displayAllParks().get(chosenPark).getLocation());
		out.println("Established: " + formattedDate(admin.displayAllParks().get(chosenPark).getEstablishDate()));
		out.println("Area: " + admin.displayAllParks().get(chosenPark).getArea() + " sq km ");
		out.println("Annual Visitors: " + admin.displayAllParks().get(chosenPark).getVisitors());
		out.println();
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
			campgrounds = admin.displayAllCampgrounds();
			displayMainCampgroundMenu(choices);
			choice = getChoiceFromUserInputFromCampgroundMenu(choices);
		}
		return choice;
	}
	
	public void displayAllCampgrounds(Object choices) {
		out.println(admin.displayAllCampgrounds());
	}
	

	// actually deals with the user input
	private Object getChoiceFromUserInputFromCampgroundMenu(Object[] choices) {
		Object choice = null;
		String userInput = in.nextLine();
		try {

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
		for (Campground campground : campgrounds) {
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
	
	private String formattedDate(LocalDate date) {
		String originalDate = date.toString();
		SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat newFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date formattedDashedDate = null;
		try {
			formattedDashedDate = originalFormat.parse(originalDate);
		} catch (ParseException e) {
		}
		String correctlyFormattedDate = newFormat.format(formattedDashedDate).toString().replace("-", "/");
		return correctlyFormattedDate;
	}
	
	private String descriptionWraps() {
		StringBuilder sb = new StringBuilder(s);

		int i = 0;
		while (i + 20 < sb.length() && (i = sb.lastIndexOf(" ", i + 20)) != -1) {
		    sb.replace(i, i + 1, "\n");
		}

		System.out.println(sb.toString());
		return "";
	}

}
