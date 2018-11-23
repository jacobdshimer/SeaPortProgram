// File: CargoShip.java
// Date: October 29, 2018
// Author: Jacob D. Shimer
// Purpose: This is the file that handles the CargoShip object

package seaport;

import java.util.Scanner;

/**
 *
 * @author Shimer
 */
public class CargoShip extends Ship{
    private double cargoValue, cargoVolume, cargoWeight;

    public CargoShip(Scanner sc, PortTime arrivalTime, PortTime dockTime) {
        super(sc, arrivalTime, dockTime);
        this.cargoValue = sc.nextDouble();
        this.cargoVolume = sc.nextDouble();
        this.cargoWeight = sc.nextDouble();
    }

    // ------------------------ GETTERS ----------------------------------------
    public double getCargoValue() {
        return cargoValue;
    }

    public double getCargoVolume() {
        return cargoVolume;
    }

    public double getCargoWeight() {
        return cargoWeight;
    }
    
    
    
    public String toString(){
        String st = super.toString();
        st += "Cargo Value: " + getCargoValue() + "\n";
        st += "Cargo Volume: " + getCargoVolume() + "\n";
        st += "Cargo Weight: " + getCargoWeight();
        return st;
    }
    
}
