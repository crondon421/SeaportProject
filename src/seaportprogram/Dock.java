import java.util.HashMap;
import java.util.Scanner;

public class Dock extends Thing{
	
	Ship ship;
	
	public Dock() {
		
	}

	public Dock(Scanner lineSc, HashMap<Integer, Thing> elements) {
		super(lineSc, elements);
	}
}
