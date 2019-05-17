import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Job extends Thing{
	
	Double duration;
	ArrayList<String> requirements = new ArrayList<>();
	ArrayList<Person> workers = new ArrayList<>();
	
	public Job(Scanner lineSc, HashMap<Integer, Thing> elements) {
		super(lineSc, elements);
		duration = lineSc.nextDouble();
		//populate requirements
		while (lineSc.hasNext()) {
			requirements.add(lineSc.next());
		}
		
		
	}
}
