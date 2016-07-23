package homework.ticketService;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VenueAreaTest  {

	VenueArea simple;
    VenueArea main;
    VenueArea balcony1;
    VenueArea balcony2;
    String email = "banjtheman@gmail.com";
	
    
	@Before
	public void setUp() throws Exception {
		
        simple = new VenueArea(1, "simple", 1, 1, 1);
        main = new VenueArea(2, "Main", 75, 5, 5);
        balcony1 = new VenueArea(3, "Balcony 1", 50, 5, 5);
        balcony2 = new VenueArea(4, "Balcony 2", 40, 5, 5);
		
	}
    
	@Test
	public void testFreeSeat() {
		//Takes seat, then frees the seat, to test if it worked
		SeatHold testSeat =main.getBestSeat();
		boolean isHeld =testSeat.isHeld();
		assertTrue(isHeld); //check if seat is held
		main.freeSeat(0, 0);
		boolean isFree = main.isSeatFree(0, 0);
		assertFalse(isFree); //check if seat is free
	}

	@Test
	public void testIsSeatFree() {
		
		//returns false if seat is taken, meaning its free
		boolean isFree = main.isSeatFree(0, 0);
		assertFalse(isFree);
	}
	
	@Test
	public void testIsSeatFreeWhenNotFree() {
		
		//returns true if seat is taken, meaning its NOT free
		SeatHold testSeat =main.getBestSeat();		
		String confirmString = main.confirmSeat(0, 0, email);
		boolean isFree = main.isSeatFree(0, 0);
		assertTrue(isFree);
	}

	@Test
	public void testGetBestSeat() {	
		//returns true if seat is now taken
		SeatHold testSeat =main.getBestSeat();
		boolean isHeld =testSeat.isHeld();
		assertTrue(isHeld);
	}

	@Test
	public void testGetBestSeatwithNoSeats() {	
		//returns NULL if no seats left
		SeatHold testSeat =simple.getBestSeat();
		SeatHold nullSeat =simple.getBestSeat();
		assertNull(nullSeat);
	}
	
	@Test
	public void testConfirmSeat() {		
		//returns confirmation string if seat is now reserved
		SeatHold testSeat =main.getBestSeat();
		String confirmString = main.confirmSeat(0, 0, email);
		String correctString= email+"-200";
		assertEquals(confirmString,correctString);
	}
	
	
	@Test
	public void testConfirmSeatWithoutTaken() {		
		//Returns NULL if seat not taken first
		String confirmString = main.confirmSeat(0, 0, email);
		assertNull(confirmString);
	}

}
