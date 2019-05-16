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
public class Person extends Thing {
    String skill;
    boolean isWorking = false;
    
    //constructors
    public Person(){
        }
    public Person(Scanner sc){
        super(sc);
        if (sc.hasNext()){
            this.skill = sc.next();
        }
    }
    

    
    //getter method
    public boolean isSkilled(String reqSkill){
        if(skill.equalsIgnoreCase(reqSkill)){
            return true;
        }
        return false;
    }
    
    //toString method()
    public String toString(){
        String st = "Person: " + super.toString() + " " + skill;
        return st;
    }
    
    //toggleBusy I will probably need for Project 4 when threads compete for
    //resources
    public synchronized void hireWorker(){
        if (!isWorking){
            isWorking = true;
            return;
        }
    }
    public synchronized void fireWorker(){
        isWorking = false;
    }
    public boolean isBusy(){
        return isWorking;
    }
}
