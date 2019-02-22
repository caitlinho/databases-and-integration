package com.techelevator.campground.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import javax.activation.DataSource;

import com.techelevator.Admin;
import com.techelevator.CampgroundCLI;
import com.techelevator.campground.model.Park;

public class Menu {
	
	private PrintWriter out;
	private Scanner in;
	private Long chosenPark;
	private CampgroundCLI cli;
	
	

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Park getChoiceFromMainParkMenu() {
		Park choice = null;
		while(choice == null) {
			displayMainParkMenuOptions(admin.displayAllParks());
			choice = getChoiceFromUserInputFromMainParkMenu(admin.displayAllParks());
		}
		return choice;
	}

	
	private Park getChoiceFromUserInputFromMainParkMenu(List<Park> parks) {
		Park choice = null;
		String userInput = in.nextLine();
		try {
			chosenPark = (long) Integer.parseInt(userInput);

			if (userInput == "Q") {
				System.exit(0);
			}
			int chosenChoice = Integer.valueOf(userInput);
			if (chosenChoice > 0 && chosenChoice <= parks.size()) {
				choice = parks.get(chosenChoice - 1);
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
		for(int i = 0; i < parks.size(); i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + parks.get(i).getName());
		}
		out.println("Q) Quit");
		out.print("\nPlease choose an option >>> ");
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
	
	