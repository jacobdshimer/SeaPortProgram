// File: SeaPortProgam.java
// Date: October 29, 2018
// Author: Jacob D. Shimer
// Purpose: This file handles creation of the GUI along with the methods 
// behind all of the buttons.

package seaport;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


public class SeaPortProgram extends JFrame{
    // Creation of the text area for the GUI
    JTextArea textArea;
    JTextArea jobStatus;
    JTextArea jobPool;
    JPanel jobsPanePanel;
    JPanel treePanel;
    JScrollPane jobsPane;
    JScrollPane treePane;
    
    // Got rid of the different arrays and now everything is stored within this one object world
    World world = new World();
    HashMap<Integer, Ship> allShips = new HashMap<>();
    HashMap<SeaPort, HashMap<String,ArrayList<Person>>> workerPool;
    JTable table;
    // This is so that if someone tries to run any part of the program without first reading the file
    // it will give an error saying that it must be read first
    boolean ready;
    boolean running;
    
    // JTree Root Node Creation
    DefaultMutableTreeNode ports = new DefaultMutableTreeNode("Ports");

    // JTreeCreation
    JTree tree = new JTree(ports);
    
    
    
    
    public SeaPortProgram() {
        ready = false;
        running = true;
        JFrame frame = new JFrame();
        Scanner sf = null;
        // Set the location of the JFileChooser to the source directory
        JFileChooser jfc = new JFileChooser(".");
        jfc.showOpenDialog(null);
        File file = jfc.getSelectedFile();
        
        
        // Try catch block for assigning the chosen file to the scanner
        try {
            sf = new Scanner(file);
            
            // Catch to handle FileNotFound exception
        } catch (FileNotFoundException e) {
            System.out.println (e + "\nFile bummer: " + file);
            // Catch to handle the user not selecting a file
        } catch (NullPointerException e) {
            System.exit(0);
        }
        
        if (sf == null) {
            return;
        }
        
        // The scnaner getting passed to the lambda expression must be final, this is to get around that.
        final Scanner sc = sf;
        
        
        //Creating the GUI
        frame.pack();
        frame.setTitle("Sea Port Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Setting the frame size to one third the width and height of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width/2 + 400, height/2 + 300);
        
        // This just sets the frame to the middle of the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
                
        // Panels 
        JPanel main = new JPanel(new BorderLayout());
        JPanel optionsTab = new JPanel(new GridLayout(1, 10, 5, 5));
        JPanel worldInfoPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        treePanel = new JPanel(new BorderLayout());
        JPanel treeButtonsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JPanel mainJobsPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        jobsPanePanel = new JPanel(new GridLayout(0, 1));
        JPanel jobsLogPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        
        // Options Tab - In order they appear
        JButton read = new JButton("Read");
        JLabel searchTarget = new JLabel("Search Target: ", JLabel.RIGHT);
        JTextField textField = new JTextField(10);
        // ComboBox - these are the allowable search areas
        JComboBox <String> comboBox = new JComboBox <> ();
        comboBox.addItem("Index");
        comboBox.addItem("Name");
        comboBox.addItem("Skill");
        // End of ComboBox
        JButton search = new JButton("Search");
        JButton clear = new JButton("Clear");
        JLabel sortDesc = new JLabel("Sort By: ", JLabel.RIGHT);
        JButton sort = new JButton("Sort");
        // ComboBox2 - this is to select what to sort by
        JComboBox<String> comboBox2 = new JComboBox<>();
        comboBox2.addItem("Draft");
        comboBox2.addItem("Length");
        comboBox2.addItem("Width");
        comboBox2.addItem("Weight");
        comboBox2.addItem("Name");
        // End of ComboBox2
        
        // Add it all to the Options Tab
        optionsTab.add(read);
        optionsTab.add(searchTarget);
        optionsTab.add(textField);
        optionsTab.add(comboBox);
        optionsTab.add(search);
        optionsTab.add(clear);
        optionsTab.add(sortDesc);
        optionsTab.add(sort);
        optionsTab.add(comboBox2);
        
        
        // Panel for JTree
        
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        //tree.setRootVisible(false);
        treePane = new JScrollPane(tree);
        treePane.setPreferredSize(new Dimension(200, 0));
        treePanel.add(treePane, BorderLayout.CENTER);
        
        // Buttons for JTree
        JButton expand = new JButton("Expand All");
        JButton collapse = new JButton("Collapse All");
        JButton details = new JButton("Details");
        treeButtonsPanel.add(expand);
        treeButtonsPanel.add(collapse);
        treeButtonsPanel.add(details);
        treePanel.add(treeButtonsPanel, BorderLayout.SOUTH);
        
        // Search and Sorting Panel
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        JScrollPane textAreaPane = new JScrollPane(textArea);
        
        
        // Add the Tree information and the TextArea Information to the World Info Area
        worldInfoPanel.add(treePanel);
        worldInfoPanel.add(textAreaPane);
        
        // Jobs Area
        // TextAreas for jobs being declared
        jobStatus = new JTextArea();
        jobStatus.setEditable(false);
        jobStatus.setLineWrap(true);
        jobPool = new JTextArea();
        jobPool.setEditable(false);
        jobPool.setLineWrap(true);
        
        // Create the Panes
        JScrollPane jobStatusPane = new JScrollPane(jobStatus);
        JScrollPane jobPoolPane = new JScrollPane(jobPool);
        jobsPane = new JScrollPane(jobsPanePanel);
        
        // Add the logging panes to the logging panel
        jobsLogPanel.add(jobStatusPane);
        jobsLogPanel.add(jobPoolPane);
        
        // Add the Jobs Panel to the main jobs panel that will be added to the main panel
        mainJobsPanel.add(jobsPane);
        mainJobsPanel.add(jobsLogPanel);
        

        // Add the panels to the main panel
        main.add(optionsTab, BorderLayout.NORTH);
        main.add(worldInfoPanel, BorderLayout.WEST);
        main.add(mainJobsPanel, BorderLayout.CENTER);
        
        // Prettify the borders
        treePanel.setBorder(BorderFactory.createTitledBorder("World Tree"));
        textAreaPane.setBorder(BorderFactory.createTitledBorder("Search and Sort Results"));
        jobsLogPanel.setBorder(BorderFactory.createTitledBorder("Logging Area"));
        jobStatusPane.setBorder(BorderFactory.createTitledBorder("Jobs Status"));
        jobPoolPane.setBorder(BorderFactory.createTitledBorder("Workers Pool"));
        jobsPane.setBorder(BorderFactory.createTitledBorder("Jobs"));        
        
        // Validate all of the subcomponents
        frame.setContentPane(main);
        frame.validate();
        
        // Add the action listeners
        read.addActionListener (e -> {
            try {
                readFile(sc);
            } catch (InterruptedException ex) {
                Logger.getLogger(SeaPortProgram.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        search.addActionListener (e -> search((String)(comboBox.getSelectedItem()), (textField.getText())));
        clear.addActionListener (e -> clear());
        sort.addActionListener(e -> sort((String)(comboBox2.getSelectedItem())));
        expand.addActionListener(e -> expand());
        collapse.addActionListener(e -> collapse());
        details.addActionListener(e -> getDetails(tree.getSelectionPath()));
        
        
        
    }
    
    // Read the file
    public void readFile(Scanner sc) throws InterruptedException{
        workerPool = new HashMap<>();
        String inline;
        
        while (sc.hasNextLine()){
            inline = String.valueOf(sc.nextLine()).trim();
            if (inline.length() == 0) continue;
            if (inline.startsWith("//")) continue;
            Scanner line = new Scanner(inline);
            line.next();
            String type = inline.split(" ")[0];
            // Figuring out what the line is, i.e. if its a dock, passengership, etc.
            // Instead of creating a whole bunch of methods for creating the internal structure,
            // I decided to handle it this way with
            switch(type) {
                case "port": 
                    // Create the port object and then add it to world's port hashmap with the index value as the index for the map
                    SeaPort port = new SeaPort(line);
                    world.addPort(port.getIndex(), port);
                    break;
                case "dock": 
                    // Create the dock object, loop through the different ports within world and check if the docks parent is equal to the 
                    // port's index.  If it is, add it to world -> port hashmap at specified index -> dock hashmap
                    Dock dock = new Dock(line);
                    for (SeaPort p:world.getPorts()){
                        if (dock.getParent() == p.getIndex()) {
                            p.addToDocks(dock.getIndex(), dock);
                            
                        }
                    }
                    break;
                case "pship": 
                    // Create the passenger ship object, loop through the different ports within world, then loop through the dock's of that port,
                    // and check if the ship's parent is equal to the dock's index.  If it is, then it sets the dock's ship and add's it to the port's
                    // ships hashmap.  If it doesn't match but it matches the port's index, then it adds it to the port's que and then adds it to the port's
                    // ships hashmap.
                    PassengerShip pship = new PassengerShip(line, world.getTime(), world.getTime());
                    allShips.put(pship.getIndex(), pship);
                    for (SeaPort p: world.getPorts()) {
                        for (Dock d: p.getDocks()){
                            if (pship.getParent() == d.getIndex()){
                                d.setShip(pship);
                                p.addToShips(pship.getIndex(), pship);
                                jobStatus.append("[SHIP ARRIVING] " + pship.getName() + " has arrived at Port: " + p.getName() + " and is docking at " + d.getName() + "\n");
                            } else if (pship.getParent() == p.getIndex() && !p.getQue().contains(pship)) {
                                p.addToQue(pship.getIndex(), pship);
                                p.addToShips(pship.getIndex(), pship);
                                jobStatus.append("[SHIP ARRIVING] " + pship.getName() + " has arrived at Port: " + p.getName() + " and is waiting to dock.\n");
                            }
                        }
                    }
                    break;
                case "cship": 
                    CargoShip cship = new CargoShip(line, world.getTime(), world.getTime());
                    allShips.put(cship.getIndex(), cship);
                    for (SeaPort p: world.getPorts()) {
                        for (Dock d: p.getDocks()){
                            if (cship.getParent() == d.getIndex()){
                                d.setShip(cship);
                                p.addToShips(cship.getIndex(), cship);
                                jobStatus.append("[SHIP ARRIVING] " + cship.getName() + " has arrived at Port: " + p.getName() + " and is docking at " + d.getName() + ".\n");
                            } else if (cship.getParent() == p.getIndex() && !p.getQue().contains(cship)) {
                                p.addToQue(cship.getIndex(), cship);
                                p.addToShips(cship.getIndex(), cship);
                                jobStatus.append("[SHIP ARRIVING] " + cship.getName() + " has arrived at Port: " + p.getName() + " and is waiting to dock.\n");
                            }
                        }
                    }
                    break;
                case "person": 
                    // Same as dock's but with people instead.
                    Person person = new Person(line);
                    for (SeaPort p: world.getPorts()){
                        if (person.getParent() == p.getIndex()){
                            p.addToPersons(person.getIndex(), person);
                            jobStatus.append("[WORKER] " + person.getName() + ":" + person.getSkill().toUpperCase() + " has arrived to work at Port: " + p.getName() + ".\n");
                        }
                    }
                    break;
                    // Same as the ships loop, this just adds jobs to a dock if the jobs parent matches the docks index
                case "job":
                    Job job = new Job(line, allShips, jobsPanePanel);
                    for (SeaPort p: world.getPorts()){
                        for (Ship s: p.getShips()){
                            if (job.getParent() == s.getIndex()){
                                s.addJobs(job.getIndex(), job);
                            }
                        }
                        
                    }
                    break;
            }
        }
        
        
        for (SeaPort port:world.getPorts()){
            // Map of Skills to People with that Skill
            HashMap<String, ArrayList<Person>> skillsMap = new HashMap<>();
            // This is wehre the different nodes are created
            DefaultMutableTreeNode portNode = new DefaultMutableTreeNode(port.getName());
            DefaultMutableTreeNode dockNode = new DefaultMutableTreeNode("Docks");
            DefaultMutableTreeNode queNode = new DefaultMutableTreeNode("Que");
            DefaultMutableTreeNode personNode = new DefaultMutableTreeNode("People");
            DefaultMutableTreeNode shipNode = new DefaultMutableTreeNode("Ships");
            
            for (Dock dock:port.getDocks()){
                dockNode.add(new DefaultMutableTreeNode(dock.getName()));
            }
            
            for (Ship ship:port.getQue()){
                queNode.add(new DefaultMutableTreeNode(ship.getName()));
            }
            
            for (Ship ship:port.getShips()){
                shipNode.add(new DefaultMutableTreeNode(ship.getName()));
            }
            
            for (Person person:port.getPersons()){
                personNode.add(new DefaultMutableTreeNode(person.getName()));
                if (skillsMap.get(person.getSkill()) == null){
                    skillsMap.put(person.getSkill(), new ArrayList<>());
                    skillsMap.get(person.getSkill()).add(person);
                } else {
                    skillsMap.get(person.getSkill()).add(person);
                }
                
            }
            
            // Creates the worker pool and adds the nodes to the tree
            workerPool.put(port, skillsMap);
            portNode.add(dockNode);
            portNode.add(queNode);
            portNode.add(shipNode);
            portNode.add(personNode);
            ports.add(portNode);
            
        }
        
        // This is the way I found to get rid of the root node and update 
        tree.expandRow(0);
        tree.setRootVisible(false);
        ready = true;
        updateWorkerPoolGUI();
    }
    
    // This is my search functionality.  Just like the readFile() method, I went away from having a whole bunch of methods and instead 
    // rely on methods within the classes.
    public void search(String type, String target){
        textArea.setText(null);
        if (!ready){
            textArea.append("FILE NOT READ YET!\n");
        } else {
            
            target = target.replace(" ", "");
            // Lets the user know what it is searching for
            textArea.append("Searching for " + target + " by " + type + "\n");
            switch (type) {
                case "Index":
                    int intTarget;
                    if (!target.isEmpty()){
                        try{
                            intTarget = Integer.parseInt(target);
                        } catch (NumberFormatException e){
                            textArea.append(target + " is not a number, please insert a number.\n");
                            break;
                        }
                    } else {
                        textArea.append("You must search for something.\n");
                        break;
                    }


                    // If the target is not a port, then it loops through the ports hashmap and looks in the different hashmaps.
                    for (SeaPort port: world.getPorts()){
                        if (port.getIndex() == intTarget){
                            textArea.append(port.toString());
                        } else {
                            Dock dock = port.getDockByIndex(intTarget, port.getDocks());
                            Ship ship = port.getShipByIndex(intTarget, port.getShips());
                            Person person = port.getPersonByIndex(intTarget, port.getPersons());
                            
                            if (dock != null ){
                                textArea.append(dock.toString());
                            }
                            if (ship != null){
                                textArea.append(ship.toString());
                            }
                            if (person != null){
                                textArea.append(person.toString());
                            }
                            
                        }
                        
                    } break;

                case "Name":

                    // This was a bit more difficult.  I first query the ports hashmap to see if it is located in there.
                    // If it isn't, this will be null.  Jump over to world to understand this method.
                    SeaPort portWithName = world.getPortByName(target, world.getPorts());

                    // If its not null, output to the screen the port's toString() method.
                    if (portWithName != null){
                        textArea.append(portWithName.toString());
                    } else {
                        for (SeaPort port : world.getPorts()){
                            // These all do the same as above
                            Dock dockWithName = port.getDockByName(target, port.getDocks());
                            Ship shipWithName = port.getShipByName(target, port.getShips());
                            Person personWithName = port.getPersonByName(target, port.getPersons());
                            if (dockWithName != null) {
                                textArea.append(dockWithName.toString());
                            } else if (shipWithName != null) {
                                textArea.append(shipWithName.toString());
                            } else if (personWithName != null) {
                                textArea.append(personWithName.toString());
                            }
                        }
                    }
                    break;

                case "Skill":
                    // This is simple, just loop through the ports and then loop through the people at the port looking
                    // for people with the specified skill.
                    HashMap<String, ArrayList<String>> targetedPeople = new HashMap<>();
                    ArrayList<String> people = new ArrayList<>();
                    for (SeaPort port : world.getPorts()) {
                        for (Person person : port.getPersons()) {
                            if (person.getSkill().equalsIgnoreCase(target)) {
                                people.add(person.toString());
                                break;
                            }
                        }
                        targetedPeople.put(port.getName(), people);
                    }

                    if (targetedPeople.isEmpty()) {
                        textArea.append("No one found with " + target + " skill\n");
                    } else {
                        for (String Key : targetedPeople.keySet()) {
                            textArea.append("\nPeople with skill " + target + " at " + Key + "\n");
                            for (String person:targetedPeople.get(Key)){
                                textArea.append(person + "\n");
                            }

                        }
                    }

                    break;
                   
                default:
                    textArea.append("Searching by " + target + " is currently unavailable at this time. \n");
                    break;


            }
        }
        
    }
   
    // I got tired of seeing all of the data all the time, so I added this button to clear the text area
    public void clear(){
        textArea.setText(null);
    }
    
    // This method is entirely new to this version of the program.
    public void sort(String type){
        textArea.setText(null);
        if (!ready){
            textArea.append("FILE NOT READ YET!\n");
        } else {
            // Loop through each port
            for (SeaPort port: world.getPorts()){
                textArea.append("Sorting " + port.getName() + " by " + type + "\n");
                textArea.append("----------------------------\n");
                // Probably could've made this a switch statement, but I went with if statements
                // If sorting by Draft, Length, Width, or Weight, then it only sorts by ships in que
                // Sorting by Draft, Length, Width, or Weight is all the same, so I'm only going to comment Draft
                // If sorting by name, then it sorts everything
                if (type.equals("Draft")) {
                    textArea.append("Name - Draft\n");
                    // Create an arraylist of ships in que.  Probably could've found a way to do this once, but didn't
                    ArrayList<Ship> shipsInQue = new ArrayList<>(port.getQue());
                    // Sort it by the right comparator
                    Collections.sort(shipsInQue, new DraftComparator());
                    // Loop through the ships in the arraylist
                    for (Ship ship:shipsInQue){
                        // Output to the screen
                        textArea.append(ship.getName() + " - " + ship.getDraft() + "\n");
                    } 
                } else if (type.equals("Length")) {
                    textArea.append("Name - Length\n");
                    ArrayList<Ship> shipsInQue = new ArrayList<>(port.getQue());
                    Collections.sort(shipsInQue, new LengthComparator());
                    for (Ship ship:shipsInQue){
                        textArea.append(ship.getName() + " - " + ship.getLength() + "\n");
                    }
                } else if (type.equals("Width")) {
                    textArea.append("Name - Width\n");
                    ArrayList<Ship> shipsInQue = new ArrayList<>(port.getQue());
                    Collections.sort(shipsInQue, new WidthComparator());
                    for (Ship ship:shipsInQue){
                        textArea.append(ship.getName() + " - " + ship.getWidth() + "\n");
                    }
                } else if (type.equals("Weight")){
                    textArea.append("Name - Weight\n");
                    ArrayList<Ship> shipsInQue = new ArrayList<>(port.getQue());
                    Collections.sort(shipsInQue, new WeightComparator());
                    for (Ship ship:shipsInQue){
                        textArea.append(ship.getName() + " - " + ship.getWeight() + "\n");
                    } 
                } else if (type.equals("Name")){
                    // If its Name, then we can use the default comparator created in Thing
                    // Create and arraylist of what is about to be sorted of just the values
                    // Then sort them, loop throuh them, and then output to the screen
                    textArea.append("Docks by Name\n");
                    textArea.append("-------------\n");
                    ArrayList<Dock> docks = new ArrayList<>(port.getDocks());
                    Collections.sort(docks);
                    for (Dock dock:docks){
                        textArea.append(dock.getName() + "\n");
                    }
                    textArea.append("\n");
                    textArea.append("Ships by Name\n");
                    textArea.append("-------------\n");
                    ArrayList<Ship> ships = new ArrayList<>(port.getShips());
                    Collections.sort(ships);
                    for (Ship ship:ships){
                        textArea.append(ship.getName() + "\n");
                    }
                    textArea.append("\n");
                    textArea.append("People by Name\n");
                    textArea.append("--------------\n");
                    ArrayList<Person> persons = new ArrayList<>(port.getPersons());
                    Collections.sort(persons);
                    for (Person person:persons){
                        textArea.append(person.getName() + "\n");
                    }

                }

                textArea.append("\n");
            }
        }
    }
    
    
    // The following is the code responsible for the World Tree Buttons methods
    // This gets the details of the currently selected leaf of the tree
    // It tries to get split the selectionPath.toString up by first replacing the 
    // "[" and "]" before splitting the string off of the ",".  Then it uses the 
    // search method that I already had created by setting the first parameter to 
    // "Name" and the second parameter by the last index in the String array
    private void getDetails(TreePath selectionPath) {
        try {
            String[] target = selectionPath.toString().replace("[", "").replace("]", "").split(",");
            search("Name", target[target.length - 1]);
        } catch (NullPointerException ex) {
            // Simple catch saying that if there is no selection, through a JOptionPane error.  I need to do this with more of my error 
            // checking as this is my preferred way of displaying errors.
            JOptionPane.showMessageDialog(null, "You must select an object to get details of it.", "No Selection", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
    }

    // Simple code to collapse the entire tree
    private void collapse() {
        int row = tree.getRowCount();
        while (row >= 0){
            tree.collapseRow(row);
            row--;
        }
    }
    
    // Simple code to expand the entire tree
    private void expand() {
        int row = 0;
        while (row < tree.getRowCount()){
            tree.expandRow(row);
            row++;
        }
    }
    
    //The following methods deal with threading.
    public void update() throws InterruptedException{
        if (ready){
            for (SeaPort port : world.getPorts()){
                for (Dock dock : port.getDocks()){
                    boolean readyToLeave = false;

                    // If the dock doesn't have a ship
                    if (dock.getShip() == null){
                        continue;
                    }
                    
                    // If the ship at the dock doesn't have any jobs to complete
                    if (dock.getShip().getJobs().isEmpty()){
                        readyToLeave = true;
                    }
                    
                    // Loop through the jobs for each ship, if the job is finished, 
                    // reset its arraylist "releasing" the workers from that ship
                    for (Job job : dock.getShip().getJobs().values()){
                        if (job.isIsFinished() || job.isIsCancelled()){
                            returnWorkerPool(job.getWorkers(), port);
                            job.setWorkers(new ArrayList<>());
                        }
                    }
                    
                    // Probably a bad way to do this, but this is how I found to make sure
                    // that all of the Jobs have been completed.  First I turn the hashmap 
                    // of jobs into a Collection, then turn this into an ArrayList.
                    // I then loop through that ArrayList and get the status of the job
                    // putting it into an Boolean array.  Its important that this is a 
                    // Boolean array and not a boolean array, because a primitive boolean 
                    // array will error out
                    ArrayList<Job> listOfJobs = new ArrayList<>(dock.getShip().getJobs().values());
                    Boolean[] allJobStatus = new Boolean[listOfJobs.size()];
                    
                    for (int i = 0; i < listOfJobs.size(); i++){
                        allJobStatus[i] = listOfJobs.get(i).isIsFinished();
                    }
                    
                    // This is a continuation of the above Boolean array. If the 
                    // array contains any false, it sets readyToLeave to false,
                    // else if there are no false's, then it sets it to true and the
                    // ship can leave
                    if (Arrays.asList(allJobStatus).contains(false)) {
                        readyToLeave = false;
                    } else {
                        readyToLeave = true;
                    }
                    
                    
                    // If the ship is ready to leave
                    if (readyToLeave){
                        
                        // First, undock the ship, then loop through the jobs and return the workers back to the
                        // pool by calling the returnWorkerPool method, passing the job's workers and the current
                        // port to the method.  Then end the job and revalidate the pane that handles displaying job
                        // information
                        jobStatus.append("[SHIP UNDOCKING] " + dock.getShip().getName() + " from " + dock.getName() + " in port: " + port.getName() + "\n");
                        for (Job job : dock.getShip().getJobs().values()){
                            returnWorkerPool(job.getWorkers(),port);
                            job.endJob();
                            jobsPanePanel.validate();
                        }
                        
                        // Set the dock's ship to null
                        dock.setShip(null);
                        
                        
                        // Check if the que is empty and if it is, then return out of the method.  Else
                        // dock the first available ship in the que to the dock
                        if (port.getQue().isEmpty()){
                            return;
                        } else {
                            dock.setShip(port.getQue().remove(0));
                        }
                        
                        jobStatus.append("[SHIP DOCKING] " + dock.getShip().getName()+ " to " + dock.getName() + " in port: " + port.getName() + "\n");
                        // Set the ship's parent to its new docks' index
                        dock.getShip().setParent(dock.getIndex());                       
                    }
                    
                    // Loop through the jobs for the docked ship's and attept to start them.
                    // Only attempt to start jobs that aren't finished or cancelled
                    for (Job job : dock.getShip().getJobs().values()){
                        if (!(job.isIsCancelled() || job.isIsFinished()) && !job.getThread().isAlive()) {
                            takeFromWorkerPool(job, dock.getShip());
                        }
                        
                    }
                    
                    updateWorkerPoolGUI();
                }
            }
        }
    }
    
    /* This is in preparation of Project 4.  The goal for this is to remove from and add to a 
    worker pool.  This worker pool is setup per port, per skill. It looks like this:
    {port={skill=[person]}}*/
    
    public void updateWorkerPoolGUI(){
        String jobPoolText = "";
        for (SeaPort port : workerPool.keySet()){
            jobPoolText += "[WORKER POOL FOR " + port.getName().toUpperCase() + "]\n";
            for (String skill : workerPool.get(port).keySet()){
                jobPoolText += "    " + skill.toUpperCase() + "\n";
                for (Person worker : workerPool.get(port).get(skill)){
                    jobPoolText += "        " + worker.getName() + "\n";
                }
            }
            jobPoolText += "\n";
        }
        
        jobPool.setText(jobPoolText);
    }
    
    
    
    public void returnWorkerPool(ArrayList<Person> workers, SeaPort port){
        for (Person worker : workers){
            workerPool.get(port).get(worker.getSkill()).add(worker);
            jobStatus.append("[WORKER RELEASED] " + worker.getName() + " - " + worker.getSkill().toUpperCase() + " back in worker pool for port: " + port.getName() + "\n");
        }
        
        updateWorkerPoolGUI();
    }
    
    public void takeFromWorkerPool(Job job, Ship ship){
        // Whether a ship can leave or not.  It starts out as true and if a 
        // ship can't, then it gets changed
        boolean canLeave = true;
        ArrayList<Person> jobWorkers = new ArrayList<>();
        
        // Loop through the requirements and find the worker with the correct skill
        for (String requirement : job.getRequirements()){
            
            // Get the Port that this job belongs too, probably a better way to do this, need to look into it,
            // mayber a way of a ship knowing what port it is at?
            SeaPort jobPort = null;
            for (SeaPort port : world.getPorts()){
                for (Dock dock : port.getDocks()){
                    if (ship.getParent() == dock.getIndex()){
                        jobPort = port;
                    }
                }
            }
            
            job.setParentPort(jobPort);
            
            try{
                // Ships were taking workers even though they didn't have all of 
                // the required workers at the end.  This is part of the solution I found.
                // Instead of removing the worker from the worker pool, only get a copy of that worker.
                // If the ship can in fact leave, we will assign the workers later and remove them
                // from the pool.
                jobWorkers.add(workerPool.get(jobPort).get(requirement).get(0));
            } catch (NullPointerException ex){
                // This gets thrown if there is no skill that matches what is required in the job
                // If this happens, maybe have the thread close and the ship undock
                canLeave = false;
                job.noResourcesAvailable();
                jobStatus.append("[JOB CANCELLED] " + job.getName() + " has been cancelled because the skill " + requirement.toUpperCase() + " is not available at " + jobPort.getName() + "\n");
            } catch (IndexOutOfBoundsException ex){
                 // This gets thrown if there is no one to work the job but the skill exists in the SeaPort
                 canLeave = false;
                 return;
            }
        }
        
        // If the size of the workers is the same as the size of the requirements, remove them from the
        // workerPool.  This is done by passing the value of the worker within the for loop instead of the
        // index.
        
        if (jobWorkers.size() == job.getRequirements().size()){
            job.setWorkers(jobWorkers);
            for (Person worker : jobWorkers){
                workerPool.get(job.getParentPort()).get(worker.getSkill()).remove(worker);
                jobStatus.append("[WORKER ASSIGNED] " + worker.getName() + " - " + worker.getSkill() + " has been assigned to " + ship.getName() + "\n");
            }
        }
        
        // If the workers can leave and the thread is not alive, create the GUI and start the job's thread
        // else, return out of the method
        if (canLeave){
            jobsPanePanel.add(job.createGUI());
            jobsPanePanel.validate();
            job.startJob();
        }
        
    }
         
    public static void main(String[] args) throws InterruptedException {
        SeaPortProgram sp = new SeaPortProgram();
        
        // While the SeaPortProgram is running
        while (sp.running){
            
            // Get the current time in milliseconds.  This is to limit the number of updates from once 
            // every few milliseconds to once a second
            long millis = System.currentTimeMillis();
            sp.update();
            Thread.sleep(1000 - millis % 1000);
        }
        
    }

    

    
}
