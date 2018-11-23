// File: Job.java
// Date: October 29, 2018
// Author: Jacob D. Shimer
// Purpose: This is the file that handles the Job object

package seaport;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

/**
 *
 * @author Shimer
 */
public class Job extends Thing implements Runnable{
    private double duration;
    private ArrayList<String> requirements;
    
    private enum Status { RUNNING, SUSPENDED, WAITING, DONE };
    private Ship parentShip;
    private SeaPort parentPort;
    private Status status;
    private Thread thread;
    private ArrayList<Person> workers;
    
    
    private JTextArea statusLog;
    private JTextArea workerLog;
    private JButton cancel;
    private JButton suspend;
    private JProgressBar progressBar;
    private JPanel rowPanel;
    private JLabel rowLabel;
    

    public Job(Scanner sc) {
        super(sc);
        this.duration = sc.nextDouble();
        this.requirements = new ArrayList<>();
        setRequirements(sc);
        //parentShip = this.getParent();
        
    }
    
    // This is how I figured out to get all the requirements from the scanner
    // Essentially I just waited until the scanner was empty except for jobs and then
    // went through the rest of the line in the scanner
    public void setRequirements(Scanner sc) {
        while (sc.hasNext()) {
            getRequirements().add(sc.next());
        }
    }

    public double getDuration() {
        return duration;
    }

    public ArrayList<String> getRequirements() {
        return requirements;
    }
    
    public String toString(){
        String st = super.toString();
        st += "Job Duration: " + getDuration() + "\n";
        st += "Required Skills:\n";
        for (String req:getRequirements()){
            if (!req.isEmpty()){
                st += "    " + req.toUpperCase() + "\n";
            }
        }
        return st;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
