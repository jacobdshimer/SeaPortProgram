// File: Thing.java
// Date: October 29, 2018
// Author: Jacob D. Shimer
// Purpose: This is the parent class to all other classes and handles the assignment of the 
// name, index, and parent fields.

package seaport;

import java.util.Scanner;

public class Thing implements Comparable <Thing> {
    private int index;
    private int parent;
    private String name;
    
    Thing(Scanner sc) {
        this.name = sc.next();
        this.index = sc.nextInt();
        this.parent = sc.nextInt();
        
    }
    
    // Default constructor to work for the World Class
    Thing() {
        
    }
    
    // Custom Comparable compareTo.  This is essentially the default comparator
    @Override
    public int compareTo(Thing thing) {
        return this.name.compareToIgnoreCase(thing.getName());
    }
    
    // ------------------------ GETTERS ----------------------------------------
    public String getName(){
        return name;
    }
    
    public int getIndex(){
        return index;
    }
    
    public int getParent(){
        return parent;
    }
    
    @Override
    public String toString () {
        String st = "";
        st += "\n";
        st += "Name - " + getName() + "\n";
        st += "Index - " + getIndex() + "\n";
        st += "Parent - " + getParent() + "\n";
        return st;
    }
}
