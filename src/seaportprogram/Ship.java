import java.util.HashMap;
import java.util.Scanner;

public class Ship extends Thing{
	
	double weight;
	double length;
	double width;
	double draft;
	
	public Ship(Scanner lineSc, HashMap<Integer, Thing> elements) {
		super(lineSc, elements);
		weight = lineSc.nextDouble();
		length = lineSc.nextDouble();
		width = lineSc.nextDouble();
		draft = lineSc.nextDouble();
		
	}

	public void addToLists(HashMap<Integer, Thing> elements) {
		if(parent.getClass() == Seaport.class) {
			((Seaport)parent).ships.add(this);
			((Seaport)parent).que.add(this);
		}
		else if(parent.getClass() == Dock.class) {
			((Dock)parent).ship = this;
			((Seaport) ((Dock) parent).parent).ships.add(this);
		}
		
		elements.put(id, this);
		
	}
}
