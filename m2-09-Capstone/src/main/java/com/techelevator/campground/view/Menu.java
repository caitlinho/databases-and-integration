package com.techelevator.campground.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import javax.activation.DataSource;
import javax.swing.plaf.metal.MetalBorders.Flush3DBorder;

import com.techelevator.Admin;
import com.techelevator.CampgroundCLI;
import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.Site;

public class Menu {

	private PrintWriter out;
	private Scanner in;
	private int chosenPark;
	private int chosenCampground;
	private CampgroundCLI cli;
	private Admin admin = new Admin();
	private List<Park> parks = admin.displayAllParks();
	private List<Campground> campgrounds;
	private List<Site> siteChosen;
	private List<Site> getTop5AvailableSitesByDate;
	private static LocalDate arrivalDate = null;
	private static LocalDate departureDate = null;
	private static int bookingCampground;
	private static int databaseCampground = 0;
	private static int chosenSiteNumber;
	private String bookedName;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	// public method for cli to call which park user chooses
	public Integer getChoiceFromMainParkMenu() {
		Integer choice = null;
		while (choice == null) {
			displayMainParkMenuOptions(parks);
			choice = (Integer)(getChoiceFromUserInputFromMainParkMenu();
		}
		return choice;
	}

	// in takes the user input
	private Object getChoiceFromUserInputFromMainParkMenu() {
		Object choice = null;
		String userInput = in.nextLine();
		try {

			chosenPark = Integer.parseInt(userInput) - 1;
			chosenCampground = chosenPark + 1;

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
		out.println(admin.displayAllParks().get(chosenPark).getName() + " National Park ");
		out.printf("%-17s %-20s \n", "Location: ", admin.displayAllParks().get(chosenPark).getLocation());
		out.printf("%-17s %-2s \n", "Established: ",
				formattedDate(admin.displayAllParks().get(chosenPark).getEstablishDate()));
		out.printf("%-17s %-2d %-5s \n", "Area: ", admin.displayAllParks().get(chosenPark).getArea(), " sq km ");
		out.printf("%-17s %-2d \n", "Annual Visitors: ", admin.displayAllParks().get(chosenPark).getVisitors());
		out.println();
		out.println(descriptionWraps(admin.displayAllParks().get(chosenPark).getDescription()));
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
		getCampgroundInfo(choices);
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
			if (chosenChoice >= 0 && chosenChoice <= campgrounds.size()) {
				choice = campgrounds.get(chosenChoice - 1);
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

	public void getCampgroundInfo(Object[] choices) {
		List<Campground> campgroundsByParkId = admin.displayCampgroundByParkId(chosenCampground);
		printHeading("Park Campgrounds Screen");
		for (Campground campground : campgroundsByParkId) {
			System.out.println(
					campground.getCampgroundId() + "    " + campground.getName() + "  " + campground.getOpenFromMonth()
							+ "   " + campground.getOpenToMonth() + "  " + campground.getDailyFee());
		}
		out.flush();

	}

	public Object getChoiceFromReservationMenu(Object[] choices) {
		Object choice = null;
		while (choice == null) {
			campgrounds = admin.displayAllCampgrounds();
			displayMainCampgroundMenu(choices);
			choice = getChoiceFromUserInputFromReservationMenu(choices);
		}
		takeInCampgroundReservation(choices);
		resultsMatchingCriteria();
		bookReservationSite();
		bookReservationUnderName();
		createReservation();
		return choice;
	}

	private Object getChoiceFromUserInputFromReservationMenu(Object[] choices) {
		Object choice = null;
		String userInput = in.nextLine();
		try {

			int chosenChoice = Integer.valueOf(userInput);
			if (chosenChoice >= 0 && chosenChoice < 3) {
				choice = (chosenChoice - 1);
				databaseCampground = campgrounds.get(chosenChoice).getCampgroundId();
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

	public void takeInCampgroundReservation(Object[] choices) {
		Object choice = null;
		while (choice == null) {
			chooseCampgroundOrQuit();
			reservationArrivalDate();
			reservationDepartureDate();
			break;
		}

	}

	// Will need to add try catch for incorrect dates entered

	private int chooseCampgroundOrQuit() {
		boolean waitingForInput = true;
		int userCampground = 0;

		while (waitingForInput) {
			System.out.println("Which campground (enter 0 to cancel)?");
			String userInput = in.nextLine();

			try {
				userCampground = Integer.parseInt(userInput);
				bookingCampground = userCampground;
				if (bookingCampground == 0) {
					System.exit(0);
				} else {
					waitingForInput = false;
				}
			} catch (Exception numberFormatException) {
				System.out.println("Invalid input, please try again");
			}
		}

		return bookingCampground;

	}

	private LocalDate reservationArrivalDate() {
		boolean waitingForInput = true;
		LocalDate userArrivalDate = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		while (waitingForInput) {
			System.out.println("What is the arrival date?");
			String userInput = in.nextLine();

			try {
				userArrivalDate = LocalDate.parse(userInput, formatter);
				arrivalDate = userArrivalDate;
				waitingForInput = false;
			} catch (Exception dateTimeParseException) {
				System.out.println("Arrival date was entered incorrectedly, please try again.");
			}
		}
		return userArrivalDate;

	}

	private LocalDate reservationDepartureDate() {
		boolean waitingForInput = true;
		LocalDate userDepartureDate = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		while (waitingForInput) {
			System.out.println("What is the departure date?");
			String userInput = in.nextLine();

			try {
				userDepartureDate = LocalDate.parse(userInput, formatter);

				if (userDepartureDate.isBefore(arrivalDate)) {
					System.out.println("Departure date must be after arrival date.");
				} else {
					departureDate = userDepartureDate;
					waitingForInput = false;
				}

			} catch (Exception dateTimeParseException) {
				System.out.println("Departure date was entered incorrectedly, please try again.");
			}
		}
		return userDepartureDate;

	}

	private void resultsMatchingCriteria() {
		siteChosen = admin.getTop5AvailableSitesByDate(bookingCampground, arrivalDate, departureDate);
		printHeading("Results Matching Your Search Criteria");
		for (Site site : siteChosen) {
			String utilities = site.isUtilities() ? "Yes" : "N/A";
			String accessible = site.isAccessible() ? "Yes" : "No";

			out.print(site.getSiteNumber() + "  " + site.getMaxOccupancy() + "   " + accessible + "   "
					+ site.getMaxRVLength() + "  " + utilities + "   "
					+ (admin.displayAllCampgrounds().get(databaseCampground).getDailyFee()));
			out.println();
		}
		out.println();
		out.flush();
	}

	private int bookReservationSite() {
		boolean waitingForInput = true;

		while (waitingForInput) {
			System.out.println("Which site should be reserved? ");
			int userReserveSite = in.nextInt();

			try {
				chosenSiteNumber = userReserveSite;
				waitingForInput = false;
			} catch (Exception numberFormatException) {
				System.out.println("Invalid input, please try again");
			}
		}
		return chosenSiteNumber;

	}

	private String bookReservationUnderName() {
		boolean waitingForInput = true;
		
		String userReservationName = null;
		while (waitingForInput) {
			System.out.println("What name should the reservation be under? ");
			userReservationName = in.next();
			bookedName = userReservationName;
			waitingForInput = false;
		}
		
		return bookedName;

	}
	
	private void createReservation() {
		out.println("Your reservation confirmation id is: " + admin.addReservation(chosenSiteNumber, bookedName, arrivalDate, departureDate, LocalDate.now()));
		out.flush();
		System.exit(0);

	}
	
	// ChronoUnit.DAYS.between(arrivalDate, departureDate);

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

	private String descriptionWraps(String description) {

		StringBuilder sb = new StringBuilder(description);
		int i = 0;
		while (i + 90 < sb.length() && (i = sb.lastIndexOf(" ", i + 90)) != -1) {
			sb.replace(i, i + 1, "\n");
		}

		return sb.toString();
	}

}
