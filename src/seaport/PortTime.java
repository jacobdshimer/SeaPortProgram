// File: PortTime.java
// Date: October 29, 2018
// Author: Jacob D. Shimer
// Purpose: This is the file that handles the PortTime object

package seaport;

import java.time.Instant;

/**
 *
 * @author Shimer
 */
public class PortTime {
    private int time;
    
    PortTime(){
        
    }
    
    public void setTime() {
        this.time = ((int) (Instant.now().toEpochMilli()/1000));
    }

    public int getTime() {
        return time;
    }
    
    public String toString() {
        String st = String.valueOf(getTime());
        return st;
    }
}
