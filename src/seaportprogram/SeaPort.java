/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seaportprogram;

/**
 *
 * @author UncleJester
 */
import java.awt.GridLayout;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
public class SeaPort extends Thing{
    ArrayList<Job> jobs;
    ArrayList<Ship> que;
    ArrayList<Ship> ships;
    ArrayList<Person> persons;
    ArrayList<Dock> docks;
    ArrayList<Job> pendingJobs;
    final ReentrantLock lock = new ReentrantLock(true);
    
    //port panel on left side of southPanel
    JPanel portPanel = new JPanel();
    JLabel portLabel = new JLabel("SeaPort " + name + " Resources Available: ", JLabel.CENTER);
    
    //jobPanel on center of southPanel
    JPanel jobPanel = new JPanel();
    JLabel requestLabel = new JLabel("Job requests: ");
    JLabel requestCountLabel = new JLabel("Job requests loading: ");
    //availPanel on right side of southPanel
    JPanel availPanel = new JPanel();
    JLabel workersLabel = new JLabel("Workers available: ", JLabel.LEFT);
    JLabel countLabel = new JLabel();
    JProgressBar availBar = new JProgressBar(0, 1);
    int fullAvailability = 0;
    
    //constructors
    public SeaPort(){
    }
    public SeaPort(Scanner sc, JPanel resourcePanel){
        super(sc);
        this.jobs = new ArrayList<Job>();
        this.que = new ArrayList<Ship>();
        this.ships = new ArrayList<Ship>();
        this.persons = new ArrayList<Person>();
        this.docks = new ArrayList<Dock>();
        this.pendingJobs = new ArrayList<Job>();
        portPanel.setLayout(new GridLayout(0, 3, 5, 5));
        jobPanel.setLayout(new GridLayout(0, 2, 5, 5));
        availPanel.setLayout(new GridLayout(0, 3, 5, 5));
        availPanel.setBorder(new EmptyBorder(0, 0, 0, 100));
        portPanel.add(portLabel);
        portPanel.add(jobPanel);
        portPanel.add(availPanel);
        resourcePanel.add(portPanel);
        jobPanel.add(requestLabel);
        jobPanel.add(requestCountLabel);
        availPanel.add(workersLabel);
        availPanel.add(countLabel);
        availPanel.add(availBar);
        
    }
    
    //add dock to this port
    public void addDock(Dock dock){
        docks.add(dock);
    }
    
    //remove a ship from que and return to dock so it can setShip()
    public Ship quePop(){
        
        if(que.size() > 0){
            Ship ship = que.get(0);
            que.remove(ship);
            return ship;
        }
        return null;
    }
    
    //toString Method
    @Override
    public String toString(){
        String st = "\n\tSeaPort: " + super.toString();
        for (Dock dock : docks) {
            st += "\n\t\t\n\t\t" + dock.toString();
        }
        st += "\n\n\t--- List of all ships in que: ";
        for (Ship ship : que){
            st += "\n\n\t>" + ship.toString();
        }
        st += "\n\n\t--- List of all ships: ";
        for (Ship ship :ships){
            st += "\n\n\t>" + ship.toString();
        }
        st += "\n\n\t--- List of all persons: ";
        for (Person person : persons){
            st += "\n\t>" + person.toString();
        }
    return st;      
    }
    
    //acquires workers when they are all available
    synchronized ArrayList<Person> acquireWorkers(ArrayList<String> requirements, Job thisJob) {
        
        
        
        ArrayList<Person> workerParty = new ArrayList<>();
        
        //for loop to pick workers out of SeaPort.persons ArrayList
        for (String requirement : requirements){
            for (Person person : persons){        
                if (person.isSkilled(requirement)&& !person.isWorking){
                    person.hireWorker();
                    workerParty.add(person);
                    System.out.println(workerParty);
                }
                if (requirements.size() == workerParty.size()){                   
                    updateResources();
                    return workerParty;
                }
            }//end SeaPort.persons for
        }//end thisJob.requirements for
        
        //add job to pending jobs if not enough workers
        if (!pendingJobs.contains(thisJob)){
            pendingJobs.add(thisJob);
        }
        
        //workers acquired, but not enough, so fire previously acquired workers
        if (workerParty.size() > 0){
            fireWorkers(workerParty);
            return workerParty;

        }
        return null;
    }
    
    //fireWorkers with an ArrayList parameter, coming from Jobs workers
    public synchronized void fireWorkers(ArrayList<Person> workerParty){
        
        //some Jobs do not require workers, therefore can skip firing
        if (workerParty != null){
            
            for (Person worker : workerParty){
                for (Person person : persons){
                    if (worker != null && worker.index == person.index){
                    person.fireWorker();
                }
                }
            
            }
            
        }
        
    }
    
    //check availability for ALL workers required, 
    //before actually attempting to get them
    public synchronized boolean checkAvail(ArrayList<String> requirements) {
        ArrayList<Person> availCheck = new ArrayList<Person>();
            for (String requirement : requirements) {
                for (Person person : persons) {
                    if (person.isSkilled(requirement) && !availCheck.contains(person))
                        availCheck.add(person);
                    if (availCheck.size() == requirements.size()) return true;
                }
            }
        return false;
    }
    
    //updates the resources on the southPanel after workers are hired and fired
    public void updateResources(){
        
        fullAvailability = 0;
        int availablePersons = 0;
        
        for (Person person : persons){
            fullAvailability++;
            if (!person.isWorking){
                availablePersons++;
            }
        }
        String personString = Integer.toString(availablePersons) +" / "
                + Integer.toString(fullAvailability);
        availBar.setMaximum(fullAvailability);
        availBar.setValue(availablePersons);
        countLabel.setText(personString);
        pendingJobs.trimToSize();
        String newSize = Integer.toString(pendingJobs.size());
        requestCountLabel.setText(newSize);
    }
    
    
    
        
}
