import java.util.HashMap;
import java.util.Scanner;

public class PassengerShip extends Ship{
	
	Integer numberOfPassengers;
	Integer numberOfRooms;
	Integer numberOfOccupiedRooms;
	
	

	public PassengerShip(Scanner lineSc, HashMap<Integer, Thing> elements) {
		super(lineSc, elements);
		numberOfPassengers = lineSc.nextInt();
		numberOfRooms = lineSc.nextInt();
		numberOfOccupiedRooms = lineSc.nextInt();
		
	}
}
