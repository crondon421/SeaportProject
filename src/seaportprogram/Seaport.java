import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Seaport extends Thing{

	ArrayList<Dock> docks = new ArrayList<>();
	ArrayList<Ship> ships = new ArrayList<>();
	ArrayList<Ship> que = new ArrayList<>();
	ArrayList<Person> persons = new ArrayList<>();
	
	public Seaport(Scanner sc, HashMap<Integer, Thing> elements) {
		super(sc, elements);
	}
}
