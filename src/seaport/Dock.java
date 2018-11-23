// File: Dock.java
// Date: October 29, 2018
// Author: Jacob D. Shimer
// Purpose: This is the file that handles the Dock object

package seaport;

import java.util.Scanner;

/**
 *
 * @author Shimer
 */
public class Dock extends Thing{
    private Ship ship;

    public Dock(Scanner sc) {
        super(sc);
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }
    
    public String toString(){
        String st = super.toString();
        st += "Docked Ship: \n";
        st += getShip().toString();
        return st;
    }
    
}
