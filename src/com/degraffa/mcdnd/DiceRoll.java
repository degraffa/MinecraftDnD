package com.degraffa.mcdnd;

import java.util.ArrayList;

public class DiceRoll {
    ArrayList<DiceSet> diceSets;

    public DiceRoll() {
        diceSets = new ArrayList<>();
    }

    public ArrayList<DiceSet> getDiceSets() { return diceSets; }
    public void addDiceSet(int numDice, int numSides, ArrayList<RollCondition> conditions) {
        DiceSet diceSet = new DiceSet(numDice, numSides);

        for (int i = 0; i < conditions.size(); i++) {
            diceSet.getConditions().add(conditions.get(i));
        }

        diceSets.add(diceSet);
    }

    public RollSet roll() {
        int rollTotal = 0;
        ArrayList<Integer> rolls = new ArrayList<>();

        for (int i = 0; i < diceSets.size(); i++) {
            DiceSet diceSet = diceSets.get(i);
            RollSet rollSet = diceSet.rollAll();

            rollTotal += rollSet.getRollTotal();
            rolls.addAll(rollSet.getRolls());
        }

        return new RollSet(rollTotal, rolls);
    }
}


