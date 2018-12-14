/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seaport;

import java.util.Comparator;

/**
 *
 * @author jacob
 */
public class WeightComparator implements Comparator<Ship>{

    @Override
    public int compare(Ship ship1, Ship ship2) {
        Integer draft1 = (int) Math.round(ship1.getWeight());
        Integer draft2 = (int) Math.round(ship2.getWeight());
        return draft1.compareTo(draft2);
    }
    
}