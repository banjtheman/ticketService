package homework.ticketService;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author banjt
 *
 * This class tests the Venue class functions that deal with interacting with seats, to ensure the desired outcome is reached for each path.
 */
public class VenueTest extends Venue {
	Venue concertHall;
	// Venue levels
	Optional<Integer> none = Optional.empty();
	Optional<Integer> one = Optional.of(1);
	Optional<Integer> two = Optional.of(2);
	Optional<Integer> three = Optional.of(3);
	String email = "banjtheman@gmail.com";

	
	@Before
	public void setUp() throws Exception {
		
		concertHall = new Venue();
		// create the Venue Areas
		//Venues from sample
		VenueArea simple = new VenueArea(1, "simple", 1, 1, 1);
		VenueArea main = new VenueArea(2, "Main", 75, 5, 5);
		VenueArea back = new VenueArea(3, "Back", 75, 5, 5);

		// add rows to Venue
		concertHall.addRow(simple);
		concertHall.addRow(main);
		concertHall.addRow(back);

		
	}

	@Test
	public void testTotalFreeSeats() {
		int totalSeats = concertHall.totalFreeSeats(none);
		//1+25+25 
		assertEquals(totalSeats,51);
		totalSeats = concertHall.totalFreeSeats(one);
		//1 in simple
		assertEquals(totalSeats,1);
		totalSeats = concertHall.totalFreeSeats(two);
		//25 in main
		assertEquals(totalSeats,25);
		totalSeats = concertHall.totalFreeSeats(three);
		//25 in back
		assertEquals(totalSeats,25);
		
	}

	@Test
	public void testGetBestSeatsAll() {
		ArrayList<SeatHold> heldSeats = concertHall.getBestSeats(230, none, none, email);
		//Should only get 26 seats
		assertEquals(heldSeats.size(),51);
	}
	
	@Test
	public void testGetBestSeatsMin() {
		ArrayList<SeatHold> heldSeats = concertHall.getBestSeats(25, two, none, email);
		//Should only get seats on row 2
		for (SeatHold seat: heldSeats){
			assertEquals(seat.getLevelId(),2);
		}
		
	}
	
	@Test
	public void testGetBestSeatsMinMax() {
		ArrayList<SeatHold> heldSeats = concertHall.getBestSeats(50, two, three, email);
		//Should only get seats between row 2 and 3
		for (SeatHold seat: heldSeats){
			assertNotEquals(seat.getLevelId(),1);
		}
		
	}
	
	@Test
	public void testGetBestSeatsMax() {
		ArrayList<SeatHold> heldSeats = concertHall.getBestSeats(50, none, one, email);
		//Should only get seats in level 1
		for (SeatHold seat: heldSeats){
			assertEquals(seat.getLevelId(),1);
		}
		
	}

	@Test
	public void testSeatTaken() {
		//Get seat, confirm, test if seat is taken
		concertHall.getBestSeats(1, none, one, email);
		concertHall.confirmSeat(1, 0, 0, email);
		boolean seatTaken =concertHall.seatTaken(1, 0, 0);
		assertTrue(seatTaken);
	}
	
	@Test
	public void testSeatNotTaken() {
		//Get seat, DON'T confirm, test if seat is taken
		concertHall.getBestSeats(1, none, one, email);
		boolean seatTaken =concertHall.seatTaken(1, 0, 0);
		assertFalse(seatTaken);
	}

	@Test
	public void testFreeSeat() {
		//Get seat, confirm, test if seat is taken
		concertHall.getBestSeats(1, none, one, email);
		concertHall.confirmSeat(1, 0, 0, email);
		boolean seatTaken =concertHall.seatTaken(1, 0, 0);
		assertTrue(seatTaken);
		//Free seat, test if its free
		concertHall.freeSeat(1, 0, 0);
		seatTaken =concertHall.seatTaken(1, 0, 0);
		assertFalse(seatTaken); //means seat is not taken	
	}

	@Test
	public void testConfirmSeat() {
		//Get seat, confirm seat, test to see if correct string provided
		concertHall.getBestSeats(1, one, one, email);
		String confirmString = concertHall.confirmSeat(1, 0, 0, email);
		String correctString= email+"-100";
		assertEquals(confirmString,correctString);

	}
	
	@Test
	public void testConfirmSeatNotTaken() {
		//Get seat, without confirming, test to see if confirm string is null
		String confirmString = concertHall.confirmSeat(1, 0, 0, email);
		assertNull(confirmString);

	}

}
