import java.util.HashMap;
import java.util.Scanner;

public class Person extends Thing {
	
	String skill;
	boolean isBusy;
	
	public Person(Scanner lineSc, HashMap<Integer, Thing> elements) {
		super(lineSc, elements);
		skill = lineSc.next();
	}
	
	public boolean isBusy() {
		return isBusy;
	}
	
}
