package homework.ticketService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author banjt
 * 
 * Concierge class implements the TicketService to allow for controlling interactions between the venue and the user
 * It can be though of as the controller in the MVC framework as it sends data back to the view and grabs data from the model
 * 
 * 
 *
 */


public class Concierge implements TicketService {

	private Venue venue;
	private final int MAX_SECONDS = 10 * 1000;
	private ArrayList<SeatHold> heldSeats;

	public Concierge(Venue venue) {
		super();
		this.venue = venue;
	}

	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		// Returns total seats for venue
		return venue.totalFreeSeats(venueLevel);
	}

	public ArrayList<SeatHold> findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {

		if (numSeats < 1) {
			System.out.println("Must enter at least 1 seat");
			return null;
		}

		final ArrayList<SeatHold> tempSeats = venue.getBestSeats(numSeats, minLevel, maxLevel, customerEmail);
		heldSeats = tempSeats;

		// set timer to hold seats
		if (tempSeats != null && tempSeats.size()!=0) {

			Timer timer = new Timer();

			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					System.out.println("10 seconds have passed check to see if seat is resvered");

					for (SeatHold seat : tempSeats) {
						int levelId = seat.getLevelId();
						int rowId = seat.getSeatRow();
						int seatId = seat.getSeatId();

						if (venue.seatTaken(levelId, rowId, seatId)) {
							// Seat is taken
							System.out.println("Seat " + levelId + rowId + seatId + " has been taken");
						} else {
							// seat wasn't taken in time limit set back to free
							System.out.println("Seat " + levelId + rowId + seatId
									+ " has not been taken in time limit setting to free");
							venue.freeSeat(levelId, rowId, seatId);
						}

					}
					// clear temp held seats
					heldSeats = null;

				}
			}, MAX_SECONDS);

		}

		return tempSeats;
	}

	public String reserveSeats(int levelId, int rowId, int seatId, String customerEmail) {
		// TODO Auto-generated method stub

		return venue.confirmSeat(levelId, rowId, seatId, customerEmail);
	}

}
