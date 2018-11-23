// File: SeaPort.java
// Date: October 29, 2018
// Author: Jacob D. Shimer
// Purpose: This is the file that handles the SeaPort object

package seaport;

import java.util.HashMap;
import java.util.Scanner;


/**
 *
 * @author Shimer
 */
public class SeaPort extends Thing{
    private HashMap<Integer,Dock> docks;
    private HashMap<Integer,Ship> que;
    private HashMap<Integer,Ship> ships;
    private HashMap<Integer,Person> persons;

    public SeaPort(Scanner sc) {
        super(sc);
        this.docks = new HashMap<>();
        this.que = new HashMap<>();
        this.ships = new HashMap<>();
        this.persons = new HashMap<>();
    }
    
    // ------------------------ ADDERS ----------------------------------------
    public void addToDocks(int index, Dock dock){
        getDocks().put(index, dock);
        
    }
    
    public void addToQue(int index, Ship ship){
        getQue().put(index, ship);
    }
    
    public void addToShips(int index, Ship ship){
        getShips().put(index, ship);
    }
    
    public void addToPersons(int index, Person person){
        getPersons().put(index, person);
    }
    
    // ------------------------ GETTERS ----------------------------------------
    public HashMap<Integer, Dock> getDocks() {
        return docks;
    }
    
    public HashMap<Integer, Ship> getQue() {
        return que;
    }
    
    public HashMap<Integer, Ship> getShips() {
        return ships;
    }
    
    public HashMap<Integer, Person> getPersons() {
        return persons;
    }
    
    // ---------------- GETTING OBJECTS BY THEIR INDEX -------------------------
    public Dock getDockByIndex(int x, HashMap<Integer, Dock> hmd){
        return hmd.get(x);
    }
    
    public Ship getShipByIndex(int x, HashMap<Integer, Ship> hms){
        return hms.get(x);
    }
    
    public Person getPersonByIndex(int x, HashMap<Integer, Person> hmper){
        return hmper.get(x);
    }
    
    // ----------------- GETTING OBJECTS BY NAME ------------------------------
    // This method's required parameters are a string and a hashmap.
    // loop through the hashmap and see if the name is equal to the passed name
    // if it is, return that object.  If it isn't, return null.
    public Dock getDockByName(String name, HashMap<Integer, Dock> hmd){
        for (Dock dock:hmd.values()){
            if (dock.getName().equalsIgnoreCase(name)){
                return dock;
            }
        } return null;
    }
    
    public Ship getShipByName(String name, HashMap<Integer, Ship> hms){
        for (Ship ship:hms.values()){
            if (ship.getName().equalsIgnoreCase(name)){
                return ship;
            }
        } return null;
    }
    
    public Person getPersonByName(String name, HashMap<Integer, Person> hmp){
        for (Person person:hmp.values()){
            if (person.getName().equalsIgnoreCase(name)){
                return person;
            }
        } return null;
    }
    
    @Override
    public String toString () {
        String st = "\n\nSeaPort: " + super.toString();
        st += "\n\n --- List of all ships in que:";
        for (Ship ms: getQue().values() ) st += "\n" + ms;
        st += "\n\n --- List of all docked ships:";
        for (Dock md: getDocks().values() ) st += "\n" + md;
        st += "\n\n --- List of all persons:";
        for (Person mp: getPersons().values() ) st += "\n" + mp;
        return st;
    }
}
