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
 * This class tests the Concierge interface methods to ensure that each path has the desired outcome
 *
 */
public class ConciergeTest {

	Venue concertHall;
	Concierge concierge;
	// Venue levels
	Optional<Integer> none = Optional.empty();
	Optional<Integer> one = Optional.of(1);
	Optional<Integer> two = Optional.of(2);
	Optional<Integer> three = Optional.of(3);
	String email = "banjtheman@gmail.com";
	
	@Before
	public void setUp() throws Exception {
		
        VenueArea simple = new VenueArea(1, "simple", 1,1, 1);
        VenueArea main = new VenueArea(2, "Main", 75, 5, 5);
        VenueArea back = new VenueArea(3, "back", 50, 5, 5);
        concertHall = new Venue();
        //add rows to Venue
        concertHall.addRow(simple);
        concertHall.addRow(main);
        concertHall.addRow(back);
        
        //Create TicketService
        concierge = new Concierge(concertHall);	
	}

	@Test
	public void testNumSeatsAvailable() {
		int totalSeats = concierge.numSeatsAvailable(none);
		//1+25+25 
		assertEquals(totalSeats,51);
		totalSeats = concierge.numSeatsAvailable(one);
		//1 in simple
		assertEquals(totalSeats,1);
		totalSeats = concierge.numSeatsAvailable(two);
		//25 in main
		assertEquals(totalSeats,25);
		totalSeats = concierge.numSeatsAvailable(three);
		//25 in back
		assertEquals(totalSeats,25);		
	}


	@Test
	public void testFindAndHoldSeatsAll() {
		
		ArrayList<SeatHold> heldSeats = concierge.findAndHoldSeats(230, none, none, email);
		//Should only get 26 seats
		assertEquals(heldSeats.size(),51);
	}
	
	@Test
	public void testFindAndHoldSeatsMin() {
		ArrayList<SeatHold> heldSeats = concierge.findAndHoldSeats(25, two, none, email);
		//Should only get seats on row 2
		for (SeatHold seat: heldSeats){
			assertEquals(seat.getLevelId(),2);
		}		
	}
	
	@Test
	public void testFindAndHoldSeatsMinMax() {
		ArrayList<SeatHold> heldSeats = concierge.findAndHoldSeats(50, two, three, email);
		//Should only get seats between row 2 and 3
		for (SeatHold seat: heldSeats){
			assertNotEquals(seat.getLevelId(),1);
		}	
	}
	
	@Test
	public void testFindAndHoldSeatsMax() {
		ArrayList<SeatHold> heldSeats = concierge.findAndHoldSeats(50, none, one, email);
		//Should only get seats in level 1
		for (SeatHold seat: heldSeats){
			assertEquals(seat.getLevelId(),1);
		}		
	}


	
	@Test
	public void testReserveSeats() {
		//Get seat, confirm seat, test to see if correct string provided
		concierge.findAndHoldSeats(1, none, none, email);
		String confirmString = concierge.reserveSeats(1, 0, 0, email);
		String correctString= email+"-100";
		assertEquals(confirmString,correctString);
	}
	
	@Test
	public void testReserveSeatsNotTaken() {
		//Get seat, without confirming, test to see if confirm string is null
		String confirmString = concierge.reserveSeats(1, 0, 0, email);
		assertNull(confirmString);
	}
}
