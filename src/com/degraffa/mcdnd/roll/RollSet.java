package com.degraffa.mcdnd.roll;

import java.util.ArrayList;

//TODO: Store a list of lists of rolls instead of just a list of rolls, then refactor stuff to make this make sense lol

// A set of rolls
public class RollSet {
    private int rollTotal;
    private ArrayList<Integer> rolls;

    RollSet(int rollTotal, ArrayList<Integer> rolls) {
        this.rolls = rolls;
        this.rollTotal = rollTotal;
    }

    public int getRollTotal() {
        return rollTotal;
    }

    public ArrayList<Integer> getRolls() {
        return rolls;
    }
}
