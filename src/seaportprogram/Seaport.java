import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Seaport extends Thing{

	ArrayList<Dock> docks = new ArrayList<>();
	ArrayList<Ship> ships = new ArrayList<>();
	ArrayList<Ship> que = new ArrayList<>();
	ArrayList<Person> persons = new ArrayList<>();
	JPanel dockedPanel = new JPanel();
	GridLayout dockedLayout = new GridLayout(0,1);
	
	public Seaport(Scanner sc, HashMap<Integer, Thing> elements) {
		super(sc, elements);
		dockedPanel.setSize(new Dimension(50,50));
		dockedPanel.setLayout(dockedLayout);
		dockedPanel.setBorder(BorderFactory.createTitledBorder(name));
	}
}
