package homework.ticketService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * 
 * @author banjt
 * 
 * The Venue is a container for VenueAreas that allows for bulk operations to be run on the entire venue or certain areas based
 * on levelId
 *
 */

public class Venue {

	// Map of data using levelId as the key and VenueRow data as value
	private LinkedHashMap<Integer, VenueArea> venueData;

	public Venue() {
		this.init();
	}

	// Initialize Venue
	public void init() {
		venueData = new LinkedHashMap<Integer, VenueArea>();
	}

	public void addRow(VenueArea row) {
		// add VenueRow to Venue HashMap
		venueData.put(row.getLevelId(), row);
	}

	public VenueArea getRowbyLevel(int level) {
		return venueData.get(level);
	}

	public int totalFreeSeats(Optional<Integer> venueLevel) {

		int totalFreeSeats = 0;
		// only return total seats for selected row
		if (venueLevel.isPresent()) {
			VenueArea currentRow = venueData.get(venueLevel.get());
			totalFreeSeats = currentRow.getFreeSeats();
			return totalFreeSeats;
		}
		// return seats for all rows

		for (int key : venueData.keySet()) {
			VenueArea currentRow = venueData.get(key);
			totalFreeSeats += currentRow.getFreeSeats();
		}

		return totalFreeSeats;
	}

	private ArrayList<SeatHold> seatFinder(ArrayList<Integer> levelArray, int numSeats, String customerEmail) {

		// System.out.println(levelArray.toString());
		ArrayList<SeatHold> heldSeats = new ArrayList<SeatHold>();
		int seatsFound = 0;
		for (int key : levelArray) {
			VenueArea currentRow = venueData.get(key);

			while (seatsFound != numSeats) {
				SeatHold nextSeat = currentRow.getBestSeat();

				// no more seats in this row break out
				if (nextSeat == null) {
					break;
				}

				// add to the held seats
				heldSeats.add(nextSeat);
				seatsFound += 1;

			}

			// exit condition we found all required seats
			if (seatsFound == numSeats) {

				return heldSeats;
			}

		}
		return heldSeats;

	}

	// return first level
	private int first() {
		for (int key : venueData.keySet()) {
			return key;
		}

		return 0;
	}

	// hold the best available seats on behalf of a customer
	// Note: we assume best available just mean closest seat not taken
	public ArrayList<SeatHold> getBestSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {

		ArrayList<Integer> levelArray = new ArrayList<Integer>();
		ArrayList<SeatHold> heldSeats = new ArrayList<SeatHold>();
		int min = this.first();
		int max = venueData.size();

		// find best seats between minLevel and maxLevel
		if (minLevel.isPresent() && maxLevel.isPresent()) {
			min = minLevel.get().intValue();
			max = maxLevel.get().intValue();
			// System.out.println("Min: "+min+" Max: "+max);

			// create subset array between minLevl and maxLevel

			for (int m = min; m <= max; m++) {
				levelArray.add(m);
			}
			// System.out.println(levelArray.toString());

			heldSeats = seatFinder(levelArray, numSeats, customerEmail);
		}

		// find best seats at least the minLevel
		else if (minLevel.isPresent()) {
			min = minLevel.get().intValue();
			for (int m = min; m <= max; m++) {
				levelArray.add(m);
			}

			heldSeats = seatFinder(levelArray, numSeats, customerEmail);
		}

		// find best seats no higher than maxLevel
		else if (maxLevel.isPresent()) {
			max = maxLevel.get().intValue();
			for (int m = min; m <= max; m++) {
				levelArray.add(m);
			}

			heldSeats = seatFinder(levelArray, numSeats, customerEmail);

		}
		// find best seats regardless of levels
		else {
			for (int m = min; m <= max; m++) {
				levelArray.add(m);
			}

			heldSeats = seatFinder(levelArray, numSeats, customerEmail);
		}

		return heldSeats;
	}

	public boolean seatTaken(int levelId, int rowId, int seatId) {

		return this.venueData.get(levelId).isSeatFree(rowId, seatId);

	}

	public void freeSeat(int levelId, int rowId, int seatId) {

		venueData.get(levelId).freeSeat(rowId, seatId);

	}

	public String confirmSeat(int levelId, int rowId, int seatId, String customerEmail) {
		return venueData.get(levelId).confirmSeat(rowId, seatId, customerEmail);
		// TODO Auto-generated method stub

	}

}
