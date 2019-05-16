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

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
public class Ship extends Thing{
    int dockTime = 1200;
    int arrivalTime = 1700;
    double draft = 800, length = 1000, width = 500,
           height = 1500, weight = 500;
    ArrayList<Job> jobs;
    boolean isDocked = false;
    boolean isBusy = false;
    boolean isPaused = false;
    ReentrantLock shipLock = new ReentrantLock(true);
    SeaPort parentPort = new SeaPort();
    
    //constructors
    public Ship(){
    }
    Ship(Scanner sc){
        super(sc);
        if (sc.hasNextDouble()){
            weight = sc.nextDouble();
        }
        if (sc.hasNextDouble()){
            length = sc.nextDouble();
        }
        if (sc.hasNextDouble()){
            width = sc.nextDouble();
        }
        if (sc.hasNextDouble()){
            draft = sc.nextDouble();
        }
        this.jobs = new ArrayList<Job>();
    }
    
    
    //getter methods
    public int getDockTime(){
        return dockTime;
    }
    public int getArrivalTime(){
        return arrivalTime;
    }
    public double getDraft(){
        return draft;
    }
    public double getLength(){
        return length;
    }
    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }
    public double getWeight(){
        return weight;
    }
    public ArrayList<Job> getJobs(){
        return jobs;
    }

    public synchronized void removeJob(Job job){
        if (jobs.size() > 0 && jobs.contains(job)){
            jobs.remove(job);
        }
        if (jobs.isEmpty()){
            
            ((Dock) parentObject).launchShip();
            
        }
    }
    
    //setter methods
    public void setDockTime(int d){
        dockTime = d;
    }
    public void setArrivalTime(int a){
        arrivalTime = a;
    }
    public void setDraft(double d){
        this.draft = d;
    }
    public void setLength(double l){
        this.length = l;
    }
    public void setWidth(double w){
        this.width = w;
    }
    public void setHeight(double h){
        this.height = h;
    }
    public void setWeight(double w){
        this.weight = w;
    }
    public void toggleDocked(){
        isDocked = !isDocked;
    }
    public void leaveDock(){
        isDocked = false;
    }
    
    //toString() method
    @Override
    public String toString(){
        return (super.toString());
    }
    
    //pass from Ship to Dock to SeaPort for acquire
    public ArrayList<Person> acquireWorkers(ArrayList<String> requirements, Job aThis){
        return ((Dock) parentObject).acquireWorkers(requirements, aThis);
        
    }
    
    //pass from Shop to Dock to SeaPort for fire
    public void fireWorkers(ArrayList<Person> workerParty){
        ((Dock) parentObject).fireWorkers(workerParty);
    }
    
}
