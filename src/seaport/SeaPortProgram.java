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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;


public class SeaPortProgram extends JFrame{
    // Creation of the text area for the GUI
    JTextArea textArea;
    JTextArea jobStatus;
    JTextArea jobPool;
    JPanel jobsPanePanel;
    JScrollPane jobsPane;
    
    // Got rid of the different arrays and now everything is stored within this one object world
    World world = new World();
    HashMap<Integer, Ship> allShips = new HashMap<>();
    HashMap<Integer, Dock> allDocks = new HashMap<>();
    HashMap<SeaPort, HashMap<String,ArrayList<Person>>> workerPool;
    
    // This is so that if someone tries to run any part of the program without first reading the file
    // it will give an error saying that it must be read first
    int readcount = 0;
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
        frame.setSize(width/2, height/2);
        
        // This just sets the frame to the middle of the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
                
        // Panels 
        JPanel main = new JPanel(new BorderLayout());
        JPanel optionsTab = new JPanel(new GridLayout(1, 10, 5, 5));
        JPanel worldInfoPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel treePanel = new JPanel(new BorderLayout());
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
        tree.setModel(null);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        JScrollPane treePane = new JScrollPane(tree);
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
        read.addActionListener (e -> readFile(sc));
        search.addActionListener (e -> search((String)(comboBox.getSelectedItem()), (textField.getText())));
        clear.addActionListener (e -> clear());
        sort.addActionListener(e -> sort((String)(comboBox2.getSelectedItem())));
        tree.getSelectionModel().addTreeSelectionListener(e -> displaySelected(e.getPath().toString()));
        
        
    }
    
    // Read the file
    public void readFile(Scanner sc){
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
                            } else if (pship.getParent() == p.getIndex() && !p.getQue().contains(pship)) {
                                p.addToQue(pship.getIndex(), pship);
                                p.addToShips(pship.getIndex(), pship);
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
                            } else if (cship.getParent() == p.getIndex() && !p.getQue().contains(cship)) {
                                p.addToQue(cship.getIndex(), cship);
                                p.addToShips(cship.getIndex(), cship);
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
                        }
                    }
                    break;
                    // Same as the ships loop, this just adds jobs to a dock if the jobs parent matches the docks index
                case "job":
                    Job job = new Job(line, allShips, allDocks, jobsPanePanel);
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
            DefaultMutableTreeNode portNode = new DefaultMutableTreeNode(port.getName());
            DefaultMutableTreeNode dockNode = new DefaultMutableTreeNode("Docks");
            DefaultMutableTreeNode queNode = new DefaultMutableTreeNode("Que");
            DefaultMutableTreeNode personNode = new DefaultMutableTreeNode("People");
            for (Dock dock:port.getDocks()){
                dockNode.add(new DefaultMutableTreeNode(dock.getName()));
            }
            
            for (Ship ship:port.getQue()){
                queNode.add(new DefaultMutableTreeNode(ship.getName()));
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
            
            workerPool.put(port, skillsMap);
            portNode.add(dockNode);
            portNode.add(queNode);
            portNode.add(personNode);
            ports.add(portNode);
            
        }
        
        
        ready = true;
        updateWorkerPoolGUI();
        
        
        
    }
    
    // This is my search functionality.  Just like the readFile() method, I went away from having a whole bunch of methods and instead 
    // rely on methods within the classes.
    public void search(String type, String target){
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
    
    public void displaySelected(String selected) {
        /*
        Still working on this implementation, trying to make it so that when the user selects on a node and hits search, it search.
        Currently this will just search as soon as the user selects a node, which ends with a lot of extra information.
        //textArea.append(selected + "\n");
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        //search("Name", selectedNode.getUserObject().toString());
        
        SeaPort portWithName = world.getPortByName(selected, world.getPorts());
        if (portWithName != null) {
            textArea.append(portWithName.toString());
        }

        //textArea.append(selectedNode.getUserObject().toString());*/
    }
    
    public void update(){
        
        System.out.println(ready);
        if (ready){
            
            for (SeaPort port : world.getPorts()){
                for (Dock dock : port.getDocks()){
                    if (dock.getShip().getJobs().isEmpty()){
                        //jobStatus.append(String.format("[SHIP UNDOCKING] %s from %s in %s\n", dock.getShip().getName(), dock.getName(), port.getName()));
                        dock.setShip(null);
                        
                        // While the port has a que
                        while (!port.getQue().isEmpty()){
                            Ship newShip = port.getQue().remove(0);
                            if (!newShip.getJobs().isEmpty()){
                                dock.setShip(newShip);
                                
                                //jobStatus.append("[SHIP DOCKING] " + dock.getShip().getName() + " from " + dock.getName() + " in " + port.getName() + "\n");
                                break;
                            }
                        }
                    }
                }
                
                for (Ship ship : port.getShips()){
                    
                    if (!ship.getJobs().isEmpty()){
                        
                        for (Job job : ship.getJobs().values()){
                            
                            jobsPane.add(job.createGUI());
                            jobsPane.revalidate();
                            jobsPane.repaint();
                            job.startJob();
                            
                            
                        }
                    }
                }
            }

        }
    }
    
    public void updateWorkerPoolGUI(){
        //System.out.println(workerPool);
        String jobPoolText = "";
        for (SeaPort port : workerPool.keySet()){
            jobPoolText += port.getName() + "\n";
            for (String skill : workerPool.get(port).keySet()){
                jobPoolText += "    " + skill.toUpperCase() + "\n";
                for (Person worker : workerPool.get(port).get(skill)){
                    jobPoolText += "        " + worker.getName() + "\n";
                }
            }

        }
        jobPoolText += "\n";
        jobPool.setText(jobPoolText);
    }
    
    public void returnWorkerPool(ArrayList<Person> workers, SeaPort port){
        for (Person worker : workers){
            workerPool.get(port).get(worker.getSkill()).add(worker);
            jobStatus.append("[WORKER RELEASED] " + worker.getName() + " - " + worker.getSkill() + " back in worker pool for port: " + port.getName() + "\n");
        }
        
        updateWorkerPoolGUI();
    }
    
    public void takeFromWorkerPool(Job job, Ship ship){
        boolean allocateResources = true;
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
            ArrayList<Person> workers = workerPool.get(jobPort).get(requirement);
        }
    }
     
    public static void main(String[] args) {
        SeaPortProgram sp = new SeaPortProgram();
        while (sp.running){
            sp.update();
        }
    }

    
}
