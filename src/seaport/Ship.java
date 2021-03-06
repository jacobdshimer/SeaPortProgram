// File: Ship.java
// Date: October 29, 2018
// Author: Jacob D. Shimer
// Purpose: This is the file that handles the Ship object

package seaport;

import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Shimer
 */
public class Ship extends Thing{
    private PortTime arrivalTime, dockTime;
    private double draft, length, weight, width;
    private HashMap<Integer, Job> jobs;

    public Ship(Scanner sc, PortTime arrivalTime, PortTime dockTime) {
        super(sc);
        this.weight = sc.nextDouble();
        this.length = sc.nextDouble();
        this.width = sc.nextDouble();
        this.draft = sc.nextDouble();
        this.arrivalTime = arrivalTime;
        this.dockTime = dockTime;
        this.jobs = new HashMap<>();
    }
    
    // ------------------------ GETTERS ----------------------------------------
    public PortTime getArrivalTime() {
        return arrivalTime;
    }

    public PortTime getDockTime() {
        return dockTime;
    }

    public double getDraft() {
        return draft;
    }

    public double getLength() {
        return length;
    }

    public double getWeight() {
        return weight;
    }

    public double getWidth() {
        return width;
    }

    public HashMap<Integer, Job> getJobs() {
        return jobs;
    }

    public void addJobs(int index, Job job) {
        this.jobs.put(index, job);
    }
    
    public String toString(){
        String st = super.toString();
        st += "Arrival Time: " + getArrivalTime() + "\n";
        st += "Dock Time: " + getDockTime() + "\n";
        st += "Draft: " + getDraft() + "\n";
        st += "Length: " + getLength() + "\n";
        st += "Width: " + getWidth() + "\n";
        st += "Weight: " + getWeight() + "\n";
        st += "Ongoing Jobs:\n";
        for (Job job:getJobs().values()){
            st += job.toString() + "\n";
        }
        return st;
    }
    
}
