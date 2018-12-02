// File: World.java
// Date: October 29, 2018
// Author: Jacob D. Shimer
// Purpose: This holds all of the SeaPort objects within the file.

package seaport;

import java.util.ArrayList;

public class World extends Thing{
    private ArrayList<SeaPort> ports;
    private PortTime time = new PortTime();

    public World() {
        super();
        this.ports = new ArrayList<>();
        setTime();
    }

    private void setTime() {
        this.time.setTime();
    }

    public PortTime getTime() {
        return time;
    }
    
    public void addPort(int index, SeaPort port) {
        ports.add(port);
    }
    
    public ArrayList<SeaPort> getPorts() {
        return ports;
    }
    
    // Searching by index method.
    public SeaPort getPortByIndex(int x, ArrayList<SeaPort> ports){
        for (SeaPort port : ports){
            if (port.getIndex() == x){
                return port;
            }
        }
        return null;
    }
    
    // This method's required parameters are a string and a hashmap.
    // loop through the hashmap and see if the name is equal to the passed name
    // if it is, return that object.  If it isn't, return null.
    public SeaPort getPortByName(String name, ArrayList<SeaPort> ports){
        for (SeaPort port : ports){
            if (port.getName().equalsIgnoreCase(name)){
                return port;
            }
        }
        return null;
    }
}
