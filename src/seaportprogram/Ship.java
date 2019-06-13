import java.awt.Dimension;
import java.awt.GridLayout;
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
	boolean docked;
	
	ArrayList<Job> jobs = new ArrayList<>();
	JPanel jobPanel = new JPanel();
	
	public Ship(Scanner lineSc, HashMap<Integer, Thing> elements) {
		super(lineSc, elements);
		weight = lineSc.nextDouble();
		length = lineSc.nextDouble();
		width = lineSc.nextDouble();
		draft = lineSc.nextDouble();
		jobPanel.setSize(new Dimension(25,50));
		jobPanel.setBorder(BorderFactory.createTitledBorder(name));
		jobPanel.setLayout(new GridLayout(0, 1, 5, 5));
	}
	
	public Seaport getParentPort() {
		
		if (parent.getClass() == Seaport.class) {
			return (Seaport) parent;
		}
		return (Seaport) parent.parent;
	}
	
	public String getParentDock() {
		if(parent.getClass() == Dock.class) {
			return parent.name; 
		}
		return null;
	}
	
	public void hasPendingJobs() {
		
	}
	
	public boolean isDocked() {
		return true;
	}
	
	
	
}
