package com.degraffa.mcdnd.roll;

import java.util.ArrayList;

// Represents a complete roll command
public class Roll {
    private ArrayList<RollComponent> rollComponents;
    private ArrayList<RollOperation> rollOperations;

    public Roll() {
        rollComponents = new ArrayList<>();
    }
    public Roll(ArrayList<RollComponent> rollComponents, ArrayList<RollOperation> rollOperations) {
        addRollComponents(rollComponents, rollOperations);
    }

    public ArrayList<RollComponent> getRollComponents() { return rollComponents; }
    public ArrayList<RollOperation> getRollOperations() { return rollOperations; }

    public void addRollComponents(ArrayList<RollComponent> rollComponents, ArrayList<RollOperation> rollOperations) {
        this.rollComponents = rollComponents;
        this.rollOperations = rollOperations;
    }
    // must add an operation with the component
    public void addRollComponent(RollComponent rollComponent, RollOperation op) {
        rollComponents.add(rollComponent);
        rollOperations.add(op);
    }
    public void addRollComponent(int numDice, int numSides, ArrayList<RollCondition> conditions, RollOperation op) {
        RollComponent rollComponent = new RollComponent(numDice, numSides);

        for (int i = 0; i < conditions.size(); i++) {
            rollComponent.getConditions().add(conditions.get(i));
        }

        addRollComponent(rollComponent, op);
    }
    public void addRollComponent(int numDice, int numSides, RollOperation op) {
        addRollComponent(numDice, numSides, new ArrayList<RollCondition>(), op);
    }


    public ArrayList<RollSet> rollDice() {
        ArrayList<RollSet> rollSets = new ArrayList<>();

        for (int i = 0; i < rollComponents.size(); i++) {
            int rollTotal = 0;
            ArrayList<Integer> rolls = new ArrayList<>();

            RollComponent rollComponent = rollComponents.get(i);
            RollSet rollSet = rollComponent.rollAll();

            rollTotal += rollSet.getRollTotal();
            rolls.addAll(rollSet.getRolls());

            rollSets.add(new RollSet(rollTotal, rolls));
        }

        return rollSets;
    }
}


