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
public class CargoShip extends Ship{
    double cargoValue;
    double cargoVolume;
    double cargoWeight;
    //Constuctors, one with & w/o parameters
    public CargoShip(){
    }
    public CargoShip(Scanner sc, HashMap<Integer, Thing> thingMap){
        super(sc);
        if (sc.hasNextDouble()){
            cargoWeight = sc.nextDouble();
        }
        if (sc.hasNextDouble()){
            cargoVolume = sc.nextDouble();
        }
        if (sc.hasNextDouble()){
            cargoValue = sc.nextDouble();
        }
        setParentObject(thingMap.get(parent));
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
        
    }
    
    //Getter methods
    public double getCargoValue(){
        return cargoValue;
    }
    public double getCargoVolume(){
        return cargoVolume;
    }
    public double getCargoWeight(){
        return cargoWeight;
    }
    
    
    //toString method
    public String toString(){
        String st = "CargoShip: " + super.toString();
        st += "\n\t\t>Jobs on Ship: \n";
        for (Job job : jobs){
            st += "\t\t\t" + job + "\n";
        }
        return st;
    }
}
