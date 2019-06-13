import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Dock extends Thing{
	
	Ship ship;
	JPanel dockContainer = new JPanel();
	
	public Dock() {
		
	}

	public Dock(Scanner lineSc, HashMap<Integer, Thing> elements) {
		super(lineSc, elements);
		dockContainer.setBorder(BorderFactory.createTitledBorder(name));
		dockContainer.setLayout(new GridLayout(0, 1, 5, 5));
	}
	
}
