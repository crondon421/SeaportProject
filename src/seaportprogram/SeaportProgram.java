
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
public class SeaportProgram extends JFrame{

	File fileContents;
	World world = new World();
	
	
	
	
	//Constructor
	public SeaportProgram() throws FileNotFoundException {
		super ("Seaport Program");
		setFrame(1800,700);
		selectFile();
		readFile(fileContents);
	
		
	}
	
	private void readFile(File file) {
		
		HashMap<Integer, Thing> elements = new HashMap<>();
		
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String curLine = sc.nextLine().trim();
				if(curLine.length() == 0) continue;
				 if(curLine != null && !curLine.startsWith("//")){
					 Scanner lineSc = new Scanner(curLine);
					 String token = lineSc.next();
					 switch(token) {
					 case "port":
						 addPort(lineSc, elements);
						 //System.out.println(world.seaports.size());
						 //System.out.println(elements.size());
						 break;
					 case "dock":
						 addDock(lineSc, elements);
						 break;
					 case "pship":
						 addPship(lineSc, elements);
						 break;
					 case "cship":
						 addCship(lineSc, elements);
						 break;
					 case "person":
						 addPerson(lineSc, elements);
						 break;
					 case "job":
						 addJob(lineSc, elements);
					 }
					
						 
					 
				 }
			}
			for(Seaport port : world.seaports) {
				System.out.println("Stats for port " + port.name + ": ");
				System.out.println("Number of docks: "+ port.docks.size());
				for(Dock dock : port.docks) {
					System.out.println("Ship in dock " + dock.name + " : " + dock.ship.name);
				}
				//System.out.println("ships portbound: " + port.ships.size());
				//System.out.println("queue size :" + port.que.size());
				System.out.println("number of employees :" + port.persons.size());
			}
			
			System.out.println("number of elements in total: " + elements.size());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void addJob(Scanner lineSc, HashMap<Integer, Thing> elements) {
		Job job = new Job(lineSc, elements);
		
	}

	private void addPerson(Scanner lineSc, HashMap<Integer, Thing> elements) {
		Person person = new Person(lineSc, elements);
		((Seaport)person.parent).persons.add(person);
		elements.put(person.id, person);
		System.out.println(person.name + " added to seaport " + person.parent.name);
	}

	

	private void addCship(Scanner lineSc, HashMap<Integer, Thing> elements) {
		CargoShip cship = new CargoShip(lineSc, elements);
	}

	private void addPship(Scanner lineSc, HashMap<Integer, Thing> elements) {
		PassengerShip pship = new PassengerShip(lineSc, elements);
	}

	private void addDock(Scanner lineSc, HashMap<Integer, Thing> elements) {
		Dock dock = new Dock(lineSc, elements);
		((Seaport) elements.get(dock.parent.id)).docks.add(dock);
		elements.put(dock.id, dock);
		//System.out.println("dock added: " + dock.name);
		//System.out.println("Number of docks in " + dock.parent.name + " :" + ((Seaport) elements.get(dock.parent.id)).docks.size());
		
	}

	private void addPort(Scanner lineSc, HashMap<Integer, Thing> elements) {
		Seaport port = new Seaport(lineSc, elements);
		elements.put(port.id, port);
		world.seaports.add(port);
		//System.out.println("port added: " + port.name);
		//System.out.println("Number of ports: " + world.seaports.size());
	}

	//readFile
	public void selectFile() {
		
		JFileChooser fileChooser = new JFileChooser(".");
		String[] exts = {"txt", "text"};
		fileChooser.setFileFilter(new FileNameExtensionFilter("TEXT FILES", exts));
        fileChooser.setDialogTitle("Choose a SeaPort Data File");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int chooserStatus = fileChooser.showOpenDialog(null);
        
        if(chooserStatus == JFileChooser.APPROVE_OPTION) {
        	this.fileContents = fileChooser.getSelectedFile();
        }else if(chooserStatus == JFileChooser.CANCEL_OPTION) {
        	System.exit(0);
        }
	}
	
	
	
	//sets the frame for the GUI
	public void setFrame(int width, int height) {
		setVisible(true);
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.lightGray);
	}
	
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SeaportProgram sp = new SeaportProgram();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
