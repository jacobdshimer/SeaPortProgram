// File: SeaPort.java
// Date: October 29, 2018
// Author: Jacob D. Shimer
// Purpose: This is the file that handles the SeaPort object

package seaport;

import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author Shimer
 */
public class SeaPort extends Thing{
    private ArrayList<Dock> docks;
    private ArrayList<Ship> que;
    private ArrayList<Ship> ships;
    private ArrayList<Person> persons;


    public SeaPort(Scanner sc) {
        super(sc);
        this.docks = new ArrayList<>();
        this.que = new ArrayList<>();
        this.ships = new ArrayList<>();
        this.persons = new ArrayList<>();
    }
    
    // ------------------------ ADDERS ----------------------------------------
    public void addToDocks(int index, Dock dock){
        getDocks().add(dock);
        
    }
    
    public void addToQue(int index, Ship ship){
        getQue().add(ship);
    }
    
    public void addToShips(int index, Ship ship){
        getShips().add(ship);
    }
    
    public void addToPersons(int index, Person person){
        getPersons().add(person);
    }
    
    // ------------------------ GETTERS ----------------------------------------
    public ArrayList<Dock> getDocks() {
        return docks;
    }
    
    public ArrayList<Ship> getQue() {
        return que;
    }
    
    public ArrayList<Ship> getShips() {
        return ships;
    }
    
    public ArrayList<Person> getPersons() {
        return persons;
    }
    
    // ---------------- GETTING OBJECTS BY THEIR INDEX -------------------------
    
    public Dock getDockByIndex(int x, ArrayList<Dock> docks){
        for (Dock dock : docks){
            if (dock.getIndex() == x){
                return dock;
            }
        }
        
        return null;
    }
    
    public Ship getShipByIndex(int x, ArrayList<Ship> ships){
        for (Ship ship : ships){
            if (ship.getIndex() == x){
                return ship;
            }
        }
        
        return null;
    }
    
    public Person getPersonByIndex(int x, ArrayList<Person> persons){
        for (Person person : persons){
            if (person.getIndex() == x){
                return person;
            }
        }
        
        return null;
    }
    
    // ----------------- GETTING OBJECTS BY NAME ------------------------------
    // This method's required parameters are a string and a hashmap.
    // loop through the hashmap and see if the name is equal to the passed name
    // if it is, return that object.  If it isn't, return null.
    
    
    public Dock getDockByName(String name, ArrayList<Dock> docks){
        for (Dock dock : docks){
            if (dock.getName().equalsIgnoreCase(name)){
                return dock;
            }
        } return null;
    }
    
    public Ship getShipByName(String name, ArrayList<Ship> ships){
        for (Ship ship : ships){
            if (ship.getName().equalsIgnoreCase(name)){
                return ship;
            }
        } return null;
    }
    
    public Person getPersonByName(String name, ArrayList<Person> persons){
        for (Person person : persons){
            if (person.getName().equalsIgnoreCase(name)){
                return person;
            }
        } return null;
    }
    
    @Override
    public String toString () {
        String st = "\n\nSeaPort: " + super.toString();
        st += "\n\n --- List of all ships in que:";
        for (Ship ms: getQue() ) st += "\n" + ms;
        st += "\n\n --- List of all docked ships:";
        for (Dock md: getDocks() ) st += "\n" + md;
        st += "\n\n --- List of all persons:";
        for (Person mp: getPersons() ) st += "\n" + mp;
        return st;
    }
}
