/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seaportprogram;

import java.util.*;
import javax.swing.*;



public class World extends Thing{
    PortTime time = new PortTime();
    ArrayList<SeaPort> ports;
    
    public World(){
        this.ports = new ArrayList<SeaPort>();
    }
    
    //readFile() method to create data structures
    public void readFile(Scanner sc, JPanel progressPanel, JPanel resourcePanel){
        HashMap<Integer, Dock> dockMap = new HashMap<>();
        HashMap<Integer, Ship> shipMap = new HashMap<>();
        HashMap<Integer, SeaPort> portMap = new HashMap<>();
        HashMap<Integer, Thing> thingMap = new HashMap<>();
        /*Instantiated pesonMap as it may be used in project 3 when jobs
        * are implemented..
        */
        HashMap<Integer, Person> personMap = new HashMap<>();
        
        while (sc.hasNextLine()){
            
            String curLine = sc.nextLine().trim();
            
            if(curLine != null){
                Scanner lineSc = new Scanner(curLine);
                if (lineSc.hasNext()){
                    //System.out.println("Still has more");
                    String token = lineSc.next();
                    //System.out.println(token);
                    if(token.equals("port")){
                        SeaPort seaPort = new SeaPort(lineSc, resourcePanel);
                        ports.add(seaPort);
                        portMap.put(seaPort.getIndex(), seaPort);
                        thingMap.put(seaPort.index, seaPort);
                
                    }
                    if(token.equals("dock")){
                        Dock dock = new Dock(lineSc, thingMap);
                        SeaPort parentPort = portMap.get(dock.getParent());
                        dock.setParentObject(parentPort);
                        parentPort.docks.add(dock);
                        dockMap.put(dock.index, dock);
                        thingMap.put(dock.index, dock);
                        //System.out.println(dock.name + " Done");
                    }
                    if(token.equals("pship")){
                        PassengerShip pShip = new PassengerShip(lineSc, thingMap);
                        Thing parentObject = (thingMap.get(pShip.parent));
                        

                        shipMap.put(pShip.getIndex(), pShip);
                        thingMap.put(pShip.index, pShip);
                    }
                    if(token.equals("cship")){
                        CargoShip cShip = new CargoShip(lineSc, thingMap);
                        shipMap.put(cShip.getIndex(), cShip);
                        thingMap.put(cShip.index, cShip);
                        //System.out.println(cShip.name + " Done");
                    }
                    if(token.equals("person")){
                        Person person = new Person(lineSc);
                        SeaPort parentPort = portMap.get(person.getParent());
                        if (parentPort != null){
                            person.setParentObject(parentPort);
                            parentPort.persons.add(person);
                        }
                        personMap.put(person.getIndex(), person);
                        thingMap.put(person.index, person);
                        parentPort.updateResources();
                        //System.out.println(person.name + " Done");
                    }
                    if(token.equals("job")){
                        Job job = new Job(lineSc, thingMap, progressPanel);                
                        Ship parentShip = (Ship)thingMap.get(job.getParent());
                        if (parentShip != null){
                            parentShip.jobs.add(job);
                            if (Dock.class.isInstance(thingMap.get(parentShip.parent))){
                                SeaPort parentPort = (SeaPort) thingMap.get(thingMap.get(parentShip.parent).parent);
                                parentPort.jobs.add(job);
                            }
                            if (SeaPort.class.isInstance(thingMap.get(parentShip.parent))){
                                SeaPort parentPort = (SeaPort) thingMap.get(parentShip.parent);
                                parentPort.jobs.add(job);
                            }
                        }
                        thingMap.put(job.index, job);
                        
                        //System.out.println(job.name + " Done");
                    }
                    
                }
            }            
        } // end while loop
        checkDocks();
    } //end readFile()
    public void checkDocks(){
        System.out.println("checking Docks");
        for (SeaPort port : ports){
            for (Dock dock : port.docks){
                if (dock.ship.jobs.isEmpty()){
                    dock.launchShip();
                }
            }
        }
        System.out.println("docks done being checked");
    }
    //performSearch() method to search based on a keyword
    public String performSearch(int searchTarget, String s){
        String resultStr = "";
        
        //if searchTarget is index
        if (searchTarget == 0){
            try{
                resultStr = "Search Results for Index: " + s + "\n\n";
                int indexTarget = Integer.parseInt(s);
                for (SeaPort port : ports){
                    if (indexTarget == port.getIndex()){
                        resultStr += port.toString();
                        return resultStr;
                    }
                    for (Dock dock : port.docks){
                        if (indexTarget == dock.getIndex()){
                            resultStr += dock.toString();
                            return resultStr;
                        }
                    }
                    for (Ship ship : port.ships){
                        if (indexTarget == ship.getIndex()){
                            resultStr += ship.toString();
                            return resultStr;
                        }
                    }
                    for (Person person : port.persons){
                        if (indexTarget == person.getIndex()){
                            resultStr += person.toString();
                            return resultStr;
                        }
                    }
                }
            return resultStr + "No results found.";
            }
            catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(null, "To search for an index, "
                        + "please enter an integer.");
            }
        }
        
        //if searchTarget is Name
        if (searchTarget == 1){
            resultStr = "Search Results for Name: " + s + "\n\n";
            String name = s;
            for (SeaPort port : ports){
                if (name.equalsIgnoreCase(port.getName())){
                    resultStr += port.toString();
                    return resultStr;
                }
                for (Dock dock : port.docks){
                    if (name.equalsIgnoreCase(dock.getName())){
                        resultStr += dock.toString();
                        return resultStr;
                    }
                }
                for (Ship ship : port.ships){
                    if (name.equalsIgnoreCase(ship.getName())){
                        resultStr += ship.toString();
                        return resultStr;
                    }
                }
                for (Person person : port.persons){
                    if (name.equalsIgnoreCase(person.getName())){
                        resultStr += person.toString();
                        return resultStr;
                    }
                }
            }
            return resultStr + "No results found.";
        }
        
        //if searchTarget is a skill
        if (searchTarget == 2){
            resultStr = "Search Results for skill: " + s + "\n\n";
            String skill = s;
            for (SeaPort port : ports){
                for (Person person : port.persons){
                    if (skill.equalsIgnoreCase(person.skill)){
                        resultStr += person.toString();
                        return resultStr;
                    }
                }
            }
        }
        return resultStr + "No results found.";
    }
    
    //performSort with parameter coming from JComboBox in SeaPortProgram.java
    public String performSort(int sortTarget){
        String sortString = "";
        //by port name
        if (sortTarget == 0){
            Collections.sort(ports, new PortNameComparator());
            sortString += ports;
        }
        //by dock name
        if (sortTarget == 1){
            for (SeaPort port : ports){
                sortString += "Port " + port.getName() + "\nDocks in order by name: \n";
                Collections.sort(port.docks, new DockNameComparator());
                for (Dock dock : port.docks){
                    sortString += dock + "\n";
                }    
            }
        }
        //all ships by name
        if (sortTarget == 2){
            for (SeaPort port : ports){
                sortString += "Port " + port.getName() + "\nShips in order by name: \n";
                Collections.sort(port.ships, new ShipNameComparator());
                for (Ship ship : port.ships){
                    sortString += ship + "\n";
                }
            }
        }
        //ships in que by name
        if (sortTarget == 3){
            for (SeaPort port : ports){
                sortString += "Port " + port.getName() + "\nShips in que by name: \n";
                Collections.sort(port.que, new ShipNameComparator());
                for (Ship ship : port.que){
                    sortString += ship + "\n";
                }
            }
        }
        //ships in que by width
        if (sortTarget == 4){
            for (SeaPort port : ports){
                sortString += "Port " + port.getName() + "\nShips in que by width: \n";
                Collections.sort(port.que, new ShipWidthComparator());
                for (Ship ship : port.que){
                    sortString += ship + "\t\tWidth: " + ship.getWidth() +  "\n";
                }
            }
        }
        //ships in que by length
        if (sortTarget == 5){
            for (SeaPort port : ports){
                sortString += "Port " + port.getName() + "\nShips in que by length: \n";
                Collections.sort(port.que, new ShipLengthComparator());
                for (Ship ship: port.que){
                    sortString += ship + "\t\tLength: " +ship.getLength() + "\n";
                }
            }
        }
        //ships in que by draft
        if (sortTarget ==6){
            for (SeaPort port : ports){
                sortString += "Port " + port.getName() + "\nShips in que by draft: \n";
                Collections.sort(port.que, new ShipDraftComparator());
                for (Ship ship : port.que){
                    sortString += ship + "\t\tDraft: " + ship.getDraft() + "\n";
                }
            }
        }
        //ships in que by weight
        if (sortTarget == 7){
            for (SeaPort port : ports){
                sortString += "Port " + port.getName() + "\nShips in que by weight: \n";
                Collections.sort(port.que, new ShipWeightComparator());
                for (Ship ship : port.que){
                    sortString += ship + "\t\tWeight: " + ship.getWeight() + "\n";
                }
            }
        }
        
        return sortString;
    }
    
    public void resourceUpdate(){
        
    }

    //world toString() method to show whole world structure
    public String toString(){
        String st = "The World:";
        for (int i = 0; i < ports.size(); i++){
            st += ports.get(i).toString();        
        }
        return st;
    }
    
    //Comparator Classes for sorting
    class ShipLengthComparator implements Comparator<Ship>{
        
        @Override
        public int compare(Ship s1, Ship s2) {
            double shipLength1 = s1.getLength();
            double shipLength2 = s2.getLength();
            if (shipLength1 < shipLength2){
                return -1;
            }
            if (shipLength1 > shipLength2){
                return 1;
            }
            return 0;
        }
    }
    class ShipWidthComparator implements Comparator<Ship>{
        @Override
        public int compare(Ship s1, Ship s2){
            double shipWidth1 = s1.getWidth();
            double shipWidth2 = s2.getWidth();
            if (shipWidth1 < shipWidth2){
                return -1;
            }
            if (shipWidth1 > shipWidth2){
                return 1;
            }
            return 0;
        }
    }
    class ShipDraftComparator implements Comparator<Ship>{
        @Override
        public int compare(Ship s1, Ship s2){
            double shipDraft1 = s1.getDraft();
            double shipDraft2 = s2.getDraft();
            if (shipDraft1 < shipDraft2){
                return -1;
            }
            if (shipDraft1 > shipDraft2){
                return 1;
            }
            return 0;
        }
    }
    class ShipWeightComparator implements Comparator<Ship>{
        @Override
        public int compare(Ship s1, Ship s2){
            double shipWeight1 = s1.getWeight();
            double shipWeight2 = s2.getWeight();
            if (shipWeight1 < shipWeight2){
                return -1;
            }
            if (shipWeight1 > shipWeight2){
                return 1;
            }
            return 0;
        }
    }
    class PortNameComparator implements Comparator<SeaPort>{
        @Override
        public int compare(SeaPort p1, SeaPort p2){
            String portName1 = p1.getName();
            String portName2 = p2.getName();
            return portName1.compareTo(portName2);
        }
    }
    class DockNameComparator implements Comparator<Dock>{
        @Override
        public int compare(Dock d1, Dock d2){
            String dockName1 = d1.getName();
            String dockName2 = d2.getName();
            return dockName1.compareTo(dockName2);
        }
    }
    class ShipNameComparator implements Comparator<Ship>{
        @Override
        public int compare(Ship s1, Ship s2){
            String shipName1 = s1.getName();
            String shipName2 = s2.getName();
            return shipName1.compareTo(shipName2);
        }
    }
    
}