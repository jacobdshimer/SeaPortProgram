// File: PassengerShip.java
// Date: October 29, 2018
// Author: Jacob D. Shimer
// Purpose: This is the file that handles the PassengerShip object

package seaport;

import java.util.Scanner;

/**
 *
 * @author Shimer
 */
public class PassengerShip extends Ship{
    private int numberOfOccupiedRooms;
    private int numberOfPassengers;
    private int numberOfRooms;

    public PassengerShip(Scanner sc, PortTime arrivalTime, PortTime dockTime) {
        super(sc, arrivalTime, dockTime);
        this.numberOfPassengers = sc.nextInt();
        this.numberOfRooms = sc.nextInt();
        this.numberOfOccupiedRooms = sc.nextInt();

    }
    
    // ------------------------ GETTERS ----------------------------------------
    public int getNumberOfOccupiedRooms() {
        return numberOfOccupiedRooms;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }
    
    public String toString(){
        String st = super.toString();
        st += "Number of Rooms: " + getNumberOfRooms() + "\n";
        st += "Number of Passengers: " + getNumberOfPassengers() + "\n";
        st += "Occupied Rooms: " + getNumberOfOccupiedRooms() + "\n";
        return st;
    }
}
