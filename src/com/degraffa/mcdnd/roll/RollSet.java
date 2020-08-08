package com.degraffa.mcdnd.roll;

import java.util.ArrayList;

//TODO: Store a list of lists of rolls instead of just a list of rolls, then refactor stuff to make this make sense lol

// A set of rolls
public class RollSet {
    // the total value of the roll
    private int rollValue;
    // list of each of the rolls
    private ArrayList<Integer> rolls;

    public RollSet(int rollValue, ArrayList<Integer> rolls) {
        this.rolls = rolls;
        this.rollValue = rollValue;
    }

    public RollSet(int constant) {
        this.rollValue = constant;
        this.rolls = new ArrayList<Integer>();
    }

    public int getRollValue() {
        return rollValue;
    }

    // determines whether this roll set is from a constant or randomly generated
    public boolean isConstant() {
        return this.rolls.size() == 0;
    }

    public ArrayList<Integer> getRolls() {
        return rolls;
    }
}
