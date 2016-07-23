package homework.ticketService;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

/**
 * TicketService main driver which allows you to interact with the system,
 * It is the "view" in the MVC as this is where you see results and actions take place.
 * The data for the venue is derived from the sample in the documentation 
 * 
 * 	Level_Id Level_Name Price  Rows Seats_in_Row
	1 		Orchestra 	$100.00 25 50
	2 		Main 		$75.00  20 100
	3 		Balcony 1 	$50.00  15 100
	4 		Balcony 2 	$40.00  15 100
	
	The systems allows you to 
	1.Find the number of seats available within the venue, optionally by seating level
	2.Find and hold the best available seats on behalf of a customer, potentially limited to specific levels
	3.Reserve and commit a specific group of held seats for a customer
 */

public class App {

	//print the standard commands
	public static void printCommands() {

		System.out.println("Enter 1 for total number of seats");
		System.out.println("Enter 2 to find and hold the best seats");
		System.out.println("Enter 3 to reserve and commit to seats");
		System.out.println("Enter -1 to exit");
	}

	public static void main(String[] args) {
		System.out.println("TicketService Staring");

		Venue concertHall = new Venue();
		// create the Venue Areas
		//Venues from sample
		VenueArea orchestra = new VenueArea(1, "Orchestra", 100, 25, 50);
		VenueArea main = new VenueArea(2, "Main", 75, 20, 100);
		VenueArea balcony1 = new VenueArea(3, "Balcony 1", 50, 15, 100);
		VenueArea balcony2 = new VenueArea(4, "Balcony 2", 40, 15, 100);

		// add rows to Venue
		concertHall.addRow(orchestra);
		concertHall.addRow(main);
		concertHall.addRow(balcony1);
		concertHall.addRow(balcony2);

		// Create TicketService
		Concierge concierge = new Concierge(concertHall);

		// Venue levels
		Optional<Integer> none = Optional.empty();
		Optional<Integer> one = Optional.of(1);
		Optional<Integer> two = Optional.of(2);
		Optional<Integer> three = Optional.of(3);
		Optional<Integer> four = Optional.of(4);

		int num;
		ArrayList<SeatHold> mySeats = null;
		String myEmail = "";
		Scanner scanner = new Scanner(System.in);
		printCommands();

		while (true) {
			try {
				num = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input");
				printCommands();
				scanner.next();
				continue;
			}

			System.out.println("Command " + num + " was entered");

			//Find the number of seats available within the venue, optionally by seating level
			if (num == 1) {
				int option = 0;
				System.out.println("Enter following options ro find total number of free seats");
				System.out.println("Enter 0 for total number of seats");
				System.out.println("Enter 1 to view all seats in the Orchestra Area ");
				System.out.println("Enter 2 to view all seats in the Main Area ");
				System.out.println("Enter 3 to view all seats in the Balcony 1 Area ");
				System.out.println("Enter 4 to view all seats in the Balcony 2 Area ");

				try {
					option = scanner.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
					scanner.next();
					printCommands();
					continue;
				}
				int totalSeats;

				switch (option) {
				case 0:
					totalSeats = concierge.numSeatsAvailable(none);
					System.out.println("Total venue seats: " + totalSeats);
					break;
				case 1:
					totalSeats = concierge.numSeatsAvailable(one);
					System.out.println("Orchestra Area seats: " + totalSeats);
					break;
				case 2:
					totalSeats = concierge.numSeatsAvailable(two);
					System.out.println("Main Area seats: " + totalSeats);
					break;
				case 3:
					totalSeats = concierge.numSeatsAvailable(three);
					System.out.println("Balcony 1 Area seats: " + totalSeats);
					break;
				case 4:
					totalSeats = concierge.numSeatsAvailable(four);
					System.out.println("Balcony 2 Area seats: " + totalSeats);
					break;
				default:
					totalSeats = -1;
					System.out.println("Invalid chocie");
					break;
				}
			}

			//Find and hold the best available seats on behalf of a customer, potentially limited to specific levels
			if (num == 2) {

				String values;
				System.out.println("Enter comma seperated options to find best seat");
				System.out.println("Number of seats, min Level, Max Level, Email");
				System.out.println("Note: enter 0 if you dont want a min or max level");
				System.out.println("Example:1,1,4,banjtheman@gmail.com");
				// Hold seats
				try {
					values = scanner.next();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
					scanner.next();
					printCommands();
					continue;
				}
				System.out.println(values);
				String[] options = values.split(",");

				if (options.length != 4) {
					System.out.println("Invalid input");
					printCommands();
					continue;
				}
				int numSeats;
				int minValue;
				int maxValue;

				try {
					numSeats = Integer.parseInt(options[0]);
					minValue = Integer.parseInt(options[1]);
					maxValue = Integer.parseInt(options[2]);

				} catch (Exception e) {
					System.out.println("First three arguments must be numbers");
					printCommands();
					continue;
				}

				// Check inputs
				if (minValue > maxValue && maxValue != 0) {
					System.out.println("Invalid min/max");
					printCommands();
					continue;
				}

				// Check inputs
				if (minValue < 0 || maxValue < 0) {
					System.out.println("Min and max must between 0 and 4");
					printCommands();
					continue;
				}

				// Check inputs
				if (numSeats < 1) {
					System.out.println("Must enter at least 1 seat");
					printCommands();
					continue;
				}

				Optional<Integer> min;
				Optional<Integer> max;
				if (options[1].equals("0")) {
					min = Optional.empty();
				} else {
					min = Optional.of(Integer.parseInt(options[1]));
				}

				if (options[2].equals("0")) {
					max = Optional.empty();
				} else {
					max = Optional.of(Integer.parseInt(options[2]));
				}

				mySeats = concierge.findAndHoldSeats(numSeats, min, max, options[3]);
				myEmail = options[3];
			}

			//Reserve and commit a specific group of held seats for a customer	
			if (num == 3) {

				if (mySeats == null || mySeats.size() ==0) {
					System.out.println("Error no seats selected");
					printCommands();
					continue;
				}

				for (SeatHold seat : mySeats) {
					int levelId = seat.getLevelId();
					int rowId = seat.getSeatRow();
					int seatId = seat.getSeatId();
					String status =concierge.reserveSeats(levelId, rowId, seatId, myEmail);
					
					if (status!=null){
						System.out.println("Confirmation ID: "+status);
					}
					

				}
				//Clear the local cache of held seats and email
				mySeats = null;
				myEmail = null;

			}

			if (num == -1) {
				System.out.println("TicketService shutting down");
				scanner.close();
				System.exit(1);
			}

			printCommands();

		}

	}
}
