// File: World.java
// Date: October 29, 2018
// Author: Jacob D. Shimer
// Purpose: This holds all of the SeaPort objects within the file.

package seaport;

import java.util.HashMap;

public class World extends Thing{
    private HashMap<Integer, SeaPort> ports;
    private PortTime time = new PortTime();

    public World() {
        super();
        this.ports = new HashMap<>();
        setTime();
    }

    private void setTime() {
        this.time.setTime();
    }

    public PortTime getTime() {
        return time;
    }
    
    public void addPort(int index, SeaPort port) {
        ports.put(index, port);
    }
    
    public HashMap<Integer, SeaPort> getPorts() {
        return ports;
    }
    
    // Searching by index method.
    public SeaPort getPortByIndex(int x, HashMap<Integer, SeaPort> hmp){
        return hmp.get(x);
    }
    
    // This method's required parameters are a string and a hashmap.
    // loop through the hashmap and see if the name is equal to the passed name
    // if it is, return that object.  If it isn't, return null.
    public SeaPort getPortByName(String name, HashMap<Integer, SeaPort> hmp){
        for (SeaPort port: hmp.values()){
            if (port.getName().equalsIgnoreCase(name)){
                return port;
            }
        }
        return null;
    }
}
