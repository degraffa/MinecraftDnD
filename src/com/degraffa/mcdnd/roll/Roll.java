package com.degraffa.mcdnd.roll;

import java.util.ArrayList;

// Represents a complete roll command
public class Roll {
    private ArrayList<RollComponent> rollComponents;

    public Roll() {
        rollComponents = new ArrayList<>();
    }
    public Roll(ArrayList<RollComponent> rollComponents) {
        addRollComponents(rollComponents);
    }

    public ArrayList<RollComponent> getRollComponents() { return rollComponents; }

    public void addRollComponents(ArrayList<RollComponent> rollComponents) {
        this.rollComponents = rollComponents;
    }
    // must add an operation with the component
    public void addRollComponent(RollComponent rollComponent) {
        rollComponents.add(rollComponent);
    }
    public void addRollComponent(int numDice, int numSides, ArrayList<RollCondition> conditions, RollOperation op) {
        RollComponentDice rollComponentDice = new RollComponentDice(numDice, numSides);

        for (int i = 0; i < conditions.size(); i++) {
            rollComponentDice.getConditions().add(conditions.get(i));
        }

        addRollComponent(rollComponentDice);
    }
    public void addRollComponent(int numDice, int numSides, RollOperation op) {
        addRollComponent(numDice, numSides, new ArrayList<RollCondition>(), op);
    }

    // rolls the dice and adds each of them
    public ArrayList<RollSet> roll() {
        ArrayList<RollSet> rollSets = new ArrayList<>();

        for (int i = 0; i < rollComponents.size(); i++) {
            RollComponent rollComponent = rollComponents.get(i);

            rollSets.add(rollComponent.roll());
        }

        return rollSets;
    }
}


