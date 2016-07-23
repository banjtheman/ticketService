package homework.ticketService;
/**
 * 
 * @author banjt
 *
 * SeatHold represents a seat in the venue, it is part of the model
 * VenueAreas has one to many SeatHold objects
 */
public class SeatHold {

	private int seatRow;
	private int seatId;
	private String levelName;
	private int levelId;
	private boolean isTaken;
	private boolean isHeld;
	private String email;

	public SeatHold(int seatRow, int seatId, String levelName, int levelId, boolean isTaken, boolean isHeld,
			String email) {
		super();
		this.seatRow = seatRow;
		this.seatId = seatId;
		this.levelName = levelName;
		this.levelId = levelId;
		this.isTaken = isTaken;
		this.isHeld = isHeld;
		this.email = email;
	}

	public int getSeatRow() {
		return seatRow;
	}

	public void setSeatRow(int seatRow) {
		this.seatRow = seatRow;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatid(int seatId) {
		this.seatId = seatId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public boolean isTaken() {
		return isTaken;
	}

	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}

	public boolean isHeld() {
		return isHeld;
	}

	public void setHeld(boolean isHeld) {
		this.isHeld = isHeld;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
