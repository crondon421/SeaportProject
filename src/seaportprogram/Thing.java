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
public class Thing implements Comparable<Thing>{
    public int index;
    public String name;
    public int parent;
    public Thing parentObject = null;
    
    //constructors
    public Thing (){        
    }
    public Thing(Scanner sc){
        name = sc.next();
        index = sc.nextInt();
        parent = sc.nextInt();
    }
    
    //compareTo, haven't found a use for it yet.
    
    
    //getter methods
    public int getIndex(){
        return index;
    }
    public String getName(){
        return name;
    }
    public int getParent(){
        return parent;
    }
    
    //Setter Methods
    public void setParentObject(Thing thing){
        this.parentObject = thing;
    }
   
    //toString method
    @Override
    public String toString(){
        return (name + " " + index);
    }

    @Override
    public int compareTo(Thing t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




    
}