/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seaportprogram;

import java.util.*;

/**
 *
 * @author UncleJester
 */
public class PassengerShip extends Ship {
    int numOccRooms;
    int numPassengers;
    int numRooms;
    //constructors
    public PassengerShip(Scanner sc, HashMap<Integer, Thing> thingMap){
        super(sc);
        if (sc.hasNextInt()){
            numPassengers = sc.nextInt();
        }
        if (sc.hasNextInt()){
            numRooms = sc.nextInt();
        }
        if (sc.hasNextInt()){
            numOccRooms = sc.nextInt();
        }
        parentObject = thingMap.get(parent);
        
        if (parentObject.getClass() == SeaPort.class){
            parentPort = (SeaPort)parentObject;
            ((SeaPort)parentObject).ships.add(this);
            ((SeaPort)parentObject).que.add(this);
        }
        else if (parentObject.getClass() == Dock.class){
           toggleDocked();
            setParentObject(parentObject);
            ((Dock)parentObject).setShip(this);
            ((SeaPort)thingMap.get(((Dock)parentObject).getParent())).ships.add(this);
            parentPort = (SeaPort)thingMap.get(parentObject.parent);
        }
        System.out.println("pship " + name + " " + System.identityHashCode(this));
    }
    public PassengerShip(int numOccRooms, int numPassengers, int numRooms){
        this.numOccRooms = numOccRooms;
        this.numPassengers = numPassengers;
        this.numRooms = numRooms;
    }
    
    //toString() method
    public String toString(){
        String st = "PassengerShip: " + super.toString();
        st += "\n\t\t>Jobs on Ship: \n";
        for (Job job : jobs){
            st += "\t\t\t" + job + "\n";
        }
        return st;
    }
    
}
