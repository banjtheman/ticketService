package homework.ticketService;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 
 * @author banjt
 *
 *	The VenueArea is a represtation of an area in the Venue, this a part of the Model as we create a Venue from these areas
 *
 *
 */

public class VenueArea {

	private int levelId;
	private String levelName;
	private double price;
	private int rows;
	private int seatsInRow;
	private int totalSeats;
	private int freeSeats;

	// Representation of an area using row as the key, and an ArrayList of
	// SeatHold objects
	private LinkedHashMap<Integer, ArrayList<SeatHold>> seats;

	public VenueArea(int levelId, String levelName, double price, int rows, int seatsInRow) {
		super();
		this.levelId = levelId;
		this.levelName = levelName;
		this.price = price;
		this.rows = rows;
		this.seatsInRow = seatsInRow;

		this.totalSeats = rows * seatsInRow;
		this.freeSeats = this.totalSeats;

		createSeats();
	}

	private void createSeats() {
		seats = new LinkedHashMap<Integer, ArrayList<SeatHold>>();
		for (int r = 0; r < rows; r++) {

			// list of seats in row
			ArrayList<SeatHold> rowSeats = new ArrayList<SeatHold>();
			for (int s = 0; s < seatsInRow; s++) {
				SeatHold temp = new SeatHold(r, s, this.levelName, this.levelId, false, false, null);
				rowSeats.add(temp);
			}
			// Add the list of seats mapped to the row
			seats.put(r, rowSeats);
		}

	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getSeatsInRow() {
		return seatsInRow;
	}

	public void setSeatsInRow(int seatsInRow) {
		this.seatsInRow = seatsInRow;
	}

	public int getFreeSeats() {
		return this.freeSeats;
	}

	public void freeSeat(int rowId, int seatId) {

		this.freeSeats += 1;
		seats.get(rowId).get(seatId).setHeld(false);
		seats.get(rowId).get(seatId).setTaken(false);

	}

	public boolean isSeatFree(int rowId, int seatId) {
		return seats.get(rowId).get(seatId).isTaken();
	}

	public SeatHold getBestSeat() {

		// Iterate through each row, if the seat is not taken or not held return
		// it
		for (int key : seats.keySet()) {
			ArrayList<SeatHold> currentSeats = seats.get(key);

			for (SeatHold seat : currentSeats) {
				// if seat is not taken or not held then return it
				if (!seat.isHeld() && !seat.isTaken()) {
					// remove a free seat as it is being held now
					System.out.println("Found empty seat " + seat.getSeatId() + " on row " + seat.getSeatRow()
							+ " on level " + seat.getLevelId());

					// Update VenueArea
					seat.setHeld(true);
					this.freeSeats -= 1;
					seats.get(key).get(seat.getSeatId()).setHeld(true);
					return seat;
				}
			}
		}

		// no seats
		System.out.println("No seats left");

		return null;
	}

	public String confirmSeat(int rowId, int seatId, String customerEmail) {

		// check if held first
		if (seats.get(rowId).get(seatId).isHeld()) {
			System.out.println("Seat " + levelId + rowId + seatId + " confirmed");
			// Set the taken flag to true and set email to confirm purchase
			seats.get(rowId).get(seatId).setTaken(true);
			seats.get(rowId).get(seatId).setEmail(customerEmail);
			String confirmString = customerEmail+"-"+levelId + rowId + seatId;
			return confirmString;
		} else {
			System.out.println("Seat " + levelId + rowId + seatId + " no longer held, cannot reserve seat");
			return null;
		}

	}

}
