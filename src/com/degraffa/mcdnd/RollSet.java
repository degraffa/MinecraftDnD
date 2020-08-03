package com.degraffa.mcdnd;

import java.util.ArrayList;

public class RollSet {
    int rollTotal;
    ArrayList<Integer> rolls;

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
