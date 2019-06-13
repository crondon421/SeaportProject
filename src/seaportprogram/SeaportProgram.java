
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.rowset.internal.Row;

import javafx.scene.Parent;
import sun.java2d.pipe.ShapeSpanIterator;


public class SeaportProgram extends JFrame implements ItemListener{

	
	File fileContents; 
	World world = new World();	
	JPanel mainContainer = new JPanel();
	JPanel portPanel = new JPanel();
	DefaultTableModel model = new DefaultTableModel();
	ProgressBarRenderer pbr = new ProgressBarRenderer(0, 100);
	JTable shipsTable = new JTable(model);
	JScrollPane docksPanel = new JScrollPane(shipsTable);
	final String[] COLUMN_NAMES = {"Ship name",  "Port", "Dock",  "Job", "Progress"};
	Object[] columnTypes = {"String", "String", "String", "String",  new JProgressBar(0, 100)};
	
	//Constructor
	public SeaportProgram() throws FileNotFoundException {
		
		super ("Seaport Program");
		model.setColumnIdentifiers(COLUMN_NAMES);
		shipsTable.getColumn("Progress").setCellRenderer(pbr);
		setFrame(1800,700);
		selectFile();
		readFile(fileContents);
		


		
		
		mainContainer.setBorder(BorderFactory.createTitledBorder("Progress View"));
		mainContainer.setLayout(new GridLayout(0, 2, 5, 5));
		
		portPanel.setBorder(BorderFactory.createTitledBorder("Seaports"));
		mainContainer.add(portPanel); 
		
		docksPanel.setBorder(BorderFactory.createTitledBorder("Ports"));
		docksPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		docksPanel.revalidate();
		mainContainer.add(docksPanel);
		mainContainer.revalidate();
		add(mainContainer);
		revalidate();
		repaint();
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
				//shipsArea.add(port.dockedPanel);
				
				for(Dock dock : port.docks) {
					System.out.println("Ship in dock " + dock.name + " : " + dock.ship.name);
					if (!dock.ship.jobs.isEmpty()) {
						for (Job job : dock.ship.jobs) {
						}
					}
					
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
		Object[] newRow = {job.parent.name, ((Ship)job.parent).getParentPort().name, ((Ship)job.parent).getParentDock(), job.name, job.pbar};
		model.addRow(newRow);
	}

	private void addPerson(Scanner lineSc, HashMap<Integer, Thing> elements) {
		Person person = new Person(lineSc, elements);
		((Seaport)person.parent).persons.add(person);
		elements.put(person.id, person);
		System.out.println(person.name + " added to seaport " + person.parent.name);
	}

	

	private void addCship(Scanner lineSc, HashMap<Integer, Thing> elements) {
		CargoShip cship = new CargoShip(lineSc, elements);
		addToLists(cship, elements);
		elements.put(cship.id, cship);
		
	}

	private void addPship(Scanner lineSc, HashMap<Integer, Thing> elements) {
		PassengerShip pship = new PassengerShip(lineSc, elements);
		addToLists(pship, elements);
		elements.put(pship.id, pship);
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
		JCheckBox newButton = new JCheckBox(port.name);
		newButton.setSelected(true);
		newButton.addItemListener(this);
		portPanel.add(newButton);
	
		//System.out.println("port added: " + port.name);
		//System.out.println("Number of ports: " + world.seaports.size());
	}
	
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItem();
		String text = ((JCheckBox)source).getText();
		Boolean isSelected = ((JCheckBox)source).isSelected();
		
		for (Seaport port : world.seaports) {
			if (port.name == text) {
				for(Dock dock : port.docks) {
					if (isSelected == false) {
						shipsTable.remove(dock.ship.jobPanel);
					}
					else if(isSelected == true) {
						shipsTable.add(dock.ship.jobPanel);
					}
				}
				shipsTable.revalidate();
			}
		}
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
	
	public void addToLists(Ship ship, HashMap<Integer, Thing> elements) {
		if(ship.parent.getClass() == Seaport.class) {
			((Seaport) elements.get(ship.parent.id)).ships.add(ship);
			((Seaport) elements.get(ship.parent.id)).que.add(ship);
			
		}
		else if(ship.parent.getClass() == Dock.class) {
			ship.docked = true;
			((Seaport) elements.get(ship.parent.parent.id)).ships.add(ship);
			((Dock) elements.get(ship.parent.id)).ship = ship;
			((Dock)ship.parent).dockContainer.add(ship.jobPanel);
			((Dock)ship.parent).dockContainer.revalidate();
			
		}
		
		elements.put(ship.id, ship);
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
