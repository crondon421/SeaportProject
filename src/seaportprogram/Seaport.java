import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;

public class Seaport extends Thing{

	ArrayList<Dock> docks = new ArrayList<>();
	ArrayList<Ship> ships = new ArrayList<>();
	ArrayList<Ship> que = new ArrayList<>();
	ArrayList<Person> persons = new ArrayList<>();
	
	JTable dockedTable = new JTable();
	JPanel dockedPanel = new JPanel();
	GridLayout dockedLayout = new GridLayout(0,1);
	
	public Seaport(Scanner sc, HashMap<Integer, Thing> elements) {
		super(sc, elements);
		dockedPanel.setSize(new Dimension(25,50));
		dockedPanel.setLayout(dockedLayout);
		dockedPanel.setBorder(BorderFactory.createTitledBorder(name));
	}

	public boolean checkAvail(ArrayList<String> requirements) {
		return false;
	}
	
}
