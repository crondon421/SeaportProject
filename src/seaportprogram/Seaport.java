import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;

public class Seaport extends Thing{

	ArrayList<Dock> docks = new ArrayList<>();
	ArrayList<Ship> ships = new ArrayList<>();
	ArrayList<Ship> que = new ArrayList<>();
	ArrayList<Person> persons = new ArrayList<>();
	ArrayList<Job> pendingJobs = new ArrayList<>();
	
	JTable dockedTable = new JTable();
	JPanel dockedPanel = new JPanel();
	GridLayout dockedLayout = new GridLayout(0,1);
	
	public Seaport(Scanner sc, HashMap<Integer, Thing> elements) {
		super(sc, elements);
		dockedPanel.setSize(new Dimension(25,50));
		dockedPanel.setLayout(dockedLayout);
		dockedPanel.setBorder(BorderFactory.createTitledBorder(name));
	}
	//check availability of workers that meet skill requirements in ship
	//TODO: thread management sync

	public boolean checkAvail(ArrayList<String> requirements) {
		int matchCount = 0;
		for (String requirement : requirements) {
			for(Person person : persons) {
				if (person.skill == requirement) {
					if(!person.isBusy()) {
						person.isBusy = true;
						matchCount++;
					}
				}
			}
		}
		
		if(matchCount == requirements.size()) {
			return true;
		}
		return false;
	}
	
}
