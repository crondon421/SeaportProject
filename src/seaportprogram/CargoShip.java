import java.util.HashMap;
import java.util.Scanner;

public class CargoShip extends Ship {
	
	Double cargoWeight;
	Double cargoVolume;
	Double cargoValue;
	
	public CargoShip(Scanner lineSc, HashMap<Integer, Thing> elements) {
		super(lineSc, elements);
		cargoWeight = lineSc.nextDouble();
		cargoVolume = lineSc.nextDouble();
		cargoValue = lineSc.nextDouble();
		elements.put(id, this);
		
	}
}
