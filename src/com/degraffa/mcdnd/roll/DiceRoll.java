package com.degraffa.mcdnd.roll;

import java.util.ArrayList;

public class DiceRoll {
    ArrayList<DiceSet> diceSets;

    public DiceRoll() {
        diceSets = new ArrayList<>();
    }
    public DiceRoll(ArrayList<DiceSet> diceSets) {
        this.diceSets = diceSets;
    }

    public ArrayList<DiceSet> getDiceSets() { return diceSets; }
    public void addDiceSet(int numDice, int numSides, ArrayList<RollCondition> conditions) {
        DiceSet diceSet = new DiceSet(numDice, numSides);

        for (int i = 0; i < conditions.size(); i++) {
            diceSet.getConditions().add(conditions.get(i));
        }

        diceSets.add(diceSet);
    }
    public void addDiceSet(int numDice, int numSides) {
        addDiceSet(numDice, numSides, new ArrayList<RollCondition>());
    }

    public ArrayList<RollSet> rollDice() {
        ArrayList<RollSet> rollSets = new ArrayList<>();

        for (int i = 0; i < diceSets.size(); i++) {
            int rollTotal = 0;
            ArrayList<Integer> rolls = new ArrayList<>();

            DiceSet diceSet = diceSets.get(i);
            RollSet rollSet = diceSet.rollAll();

            rollTotal += rollSet.getRollTotal();
            rolls.addAll(rollSet.getRolls());

            rollSets.add(new RollSet(rollTotal, rolls));
        }

        return rollSets;
    }
}


