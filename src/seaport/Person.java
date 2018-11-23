// File: Person.java
// Date: October 29, 2018
// Author: Jacob D. Shimer
// Purpose: This is the file that handles the Persons object

package seaport;

import java.util.Scanner;

/**
 *
 * @author Shimer
 */
public class Person extends Thing{
    private String skill;

    public Person(Scanner sc) {
        super(sc);
        this.skill = sc.next();
    }

    public String getSkill() {
        return skill;
    }
    
    @Override
    public String toString(){
        String st = super.toString();
        st += "Skill: " + getSkill();
        return st;
    }
}
