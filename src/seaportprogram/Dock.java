/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seaportprogram;

import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author UncleJester
 */
public class Dock extends Thing{
    Ship ship = new Ship();
    HashMap<Integer, Thing> thingMap;
    public Dock(){
    }
    public Dock(Scanner sc, HashMap<Integer, Thing> thingMap){
        super(sc);
        this.ship = new Ship();
        this.thingMap = thingMap;
        setParentObject(thingMap.get(parent));
    }
    public void setShip(PassengerShip pShip){
        ship = pShip;
    }
    public void setShip(CargoShip cShip){
        ship = cShip;
    }
    
    //newShip() calls for a new ship to be docked into an empty dock
    public void newShip(){
        ship = ((SeaPort) parentObject).quePop();
        if (ship !=null){ //if picked ship isn't null if null que list is empty
            
            if (ship.jobs.size() > 0){ //check to see if ship has pending jobs
                
                //nested ifs to check which kind of ship
                if (ship.getClass() == CargoShip.class){
                    CargoShip newShip = (CargoShip) ship;
                    newShip.parentObject = this;
                    newShip.toggleDocked();
                    setShip(newShip);
                }
                if (ship.getClass() == PassengerShip.class){
                    PassengerShip newShip = (PassengerShip) ship;
                    newShip.parentObject = this;
                    newShip.toggleDocked();
                    setShip(newShip);
                }
            }//end pending job check

            //no work needed, fuel up and go.
            else{
                launchShip();
            } // end else
        }//end !=null condition
    }//end newShip() 
    
    //launchShip() when the ship leaves the dock, call in newShip()
    public void launchShip(){
        ship = null;
        newShip();
        
    }
    
    //toString method for dock
    @Override
    public String toString(){
        String st = "Dock: " + super.toString();
        st += "\tShip: ";
        if (ship == null){
            st += "(No ship docked)";
        }
        if (ship != null){
            st+= ship.toString();
        }
        return st;
    }

    //pass requirements to SeaPort to acquireWorkers
    public ArrayList<Person> acquireWorkers(ArrayList<String> requirements, Job aThis) {
        return ((SeaPort) parentObject).acquireWorkers(requirements, aThis);
    }
    
    //pass workerParty to SeaPort to fireWorkers
    public void fireWorkers(ArrayList<Person> workerParty){
        ((SeaPort) parentObject).fireWorkers(workerParty); 
    }
}
