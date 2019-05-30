import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Ship extends Thing{
	
	double weight;
	double length;
	double width;
	double draft;
	
	ArrayList<Job> jobs = new ArrayList<>();
	JPanel jobPanel = new JPanel();
	
	public Ship(Scanner lineSc, HashMap<Integer, Thing> elements) {
		super(lineSc, elements);
		weight = lineSc.nextDouble();
		length = lineSc.nextDouble();
		width = lineSc.nextDouble();
		draft = lineSc.nextDouble();
		jobPanel.setSize(new Dimension(50,50));
		jobPanel.setBorder(BorderFactory.createTitledBorder(name));
	}

	/*public void addToLists(HashMap<Integer, Thing> elements) {
		if(parent.getClass() == Seaport.class) {
			((Seaport)parent).ships.add(this);
			((Seaport)parent).que.add(this);
		}
		else if(parent.getClass() == Dock.class) {
			((Dock)parent).ship = this;
			((Seaport) ((Dock) parent).parent).ships.add(this);
		}
		
		elements.put(id, this);
	}*/
}
