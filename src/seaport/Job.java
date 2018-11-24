// File: Job.java
// Date: October 29, 2018
// Author: Jacob D. Shimer
// Purpose: This is the file that handles the Job object

package seaport;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
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
    private boolean isFinished;
    private boolean isCancelled;
    private boolean isSuspended;
    private boolean isWaitingToFinish;
    
    // GUI Variables
    private JTextArea statusLog;
    private JTextArea workerLog;
    private JButton cancelButton;
    private JButton suspendButton;
    private JProgressBar progressBar;
    private JPanel rowPanel;
    private JLabel rowLabel;
    

    public Job(Scanner sc, HashMap<Integer, Ship> allShips, HashMap<Integer, Dock> allDocks, JPanel worldJobs) {
        super(sc);
        this.duration = sc.nextDouble();
        this.requirements = new ArrayList<>();
        while (sc.hasNext()){
            requirements.add(sc.next());
        }
        
        // Boolean Flags
        isFinished = false;
        isCancelled = false;
        isSuspended = false;
        
        // Initializing GUI Elements
        cancelButton = new JButton("Cancel");
        suspendButton = new JButton("Suspend");
        
        
        thread = new Thread(this);
        workers = new ArrayList<>(requirements.size());
        
        parentShip = allShips.get(this.getParent());
    }
    
    // Miscellaneous Functions
    // Create the Job Panel
    public JPanel createGUI(){
        rowPanel = new JPanel(new GridLayout(1, 4));
        rowLabel = new JLabel("Job: " + this.getName() + " on " + this.getParentShip().getName(), JLabel.CENTER);
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        
        rowPanel.add(rowLabel);
        rowPanel.add(progressBar);
        rowPanel.add(suspendButton);
        rowPanel.add(cancelButton);
        suspendButton.addActionListener (e -> toggleSuspend());
        cancelButton.addActionListener (e -> toggleCancel());
        
        return rowPanel;
    }
    
    // Toggle the Boolean Flags
    private void toggleSuspend() {
        setIsSuspended(!isIsSuspended());
    }
    
    private void toggleCancel() {
        setIsCancelled(true);
        setIsFinished(true);       
    }
    
    public boolean canJobStart(){
        for (Person worker : workers ){
            if (worker == null){
                updateStatus(Status.WAITING);
                return false;
            }
        }
        return true;
    }
    
    private void updateStatus(Status st){
        status = st;
        switch(status){
            case RUNNING:
                suspendButton.setBackground(Color.GREEN);
                suspendButton.setText("RUNNING");
                break;
            case SUSPENDED:
                suspendButton.setBackground(Color.YELLOW);
                suspendButton.setText("SUSPENDED");
                break;
            case WAITING:
                suspendButton.setBackground(Color.ORANGE);
                suspendButton.setText("WAITING");
                break;
            case DONE:
                suspendButton.setBackground(Color.RED);
                suspendButton.setText("DONE");
                break;
        }
    } 
    
    public synchronized void startJob(){
        setIsFinished(false);
        setIsWaitingToFinish(false);
        thread.start();
    }
    
    public void endJob(){
        progressBar.setVisible(false);
        suspendButton.setVisible(false);
        cancelButton.setVisible(false);
        rowPanel.remove(progressBar);
        rowPanel.remove(suspendButton);
        rowPanel.remove(cancelButton);
        setIsFinished(true);
        setIsWaitingToFinish(false);
        
    }
    
    @Override
    public void run(){
        long time = System.currentTimeMillis();
        long startTime = time;
        long stopTime = time + 1000 * (long)duration;
        double timeNeeded = stopTime - time;
        
        while (time < stopTime && !isCancelled){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            
            if (isWaitingToFinish){
                updateStatus(Status.WAITING);
                time += 100;
                progressBar.setValue((int) (((time - startTime) / timeNeeded) * 100));
            } else if (!isSuspended){
                updateStatus(Status.RUNNING);
                time += 100;
                progressBar.setValue((int) (((time - startTime) / timeNeeded) * 100));
            } else {
                updateStatus(Status.DONE);
            }
            
        }
        
        progressBar.setValue(100);
        if (!isWaitingToFinish){
            updateStatus(Status.DONE);
        }
        isFinished = true;
    }
    
    // ------------------------SETTERS-----------------------------------------
    
    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public void setIsCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public void setIsSuspended(boolean isSuspended) {
        this.isSuspended = isSuspended;
    }

    public void setIsWaitingToFinish(boolean isWaitingToFinish) {
        this.isWaitingToFinish = isWaitingToFinish;
    }

    public void setParentShip(Ship parentShip) {
        this.parentShip = parentShip;
    }

    public void setParentPort(SeaPort parentPort) {
        this.parentPort = parentPort;
    }

    public void setWorkers(ArrayList<Person> workers) {
        this.workers = workers;
    }    
    
    // ------------------------GETTERS-----------------------------------------

    public SeaPort getParentPort() {
        return parentPort;
    }
    
    public boolean isIsWaitingToFinish() {
        return isWaitingToFinish;
    }

    public boolean isIsFinished() {
        return isFinished;
    }

    public boolean isIsCancelled() {
        return isCancelled;
    }

    public boolean isIsSuspended() {
        return isSuspended;
    }

    public ArrayList<Person> getWorkers() {
        return workers;
    }
   
    public double getDuration() {
        return duration;
    }

    public ArrayList<String> getRequirements() {
        return requirements;
    }

    public Ship getParentShip() {
        return parentShip;
    }
    
    
    
    @Override
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

}
