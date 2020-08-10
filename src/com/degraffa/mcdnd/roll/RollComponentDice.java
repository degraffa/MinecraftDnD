package com.degraffa.mcdnd.roll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

// Represents a component of a dice roll
public class RollComponentDice extends RollComponent {
    private int numDice;
    private int numSides;

    // conditions types
    private ArrayList<RollCondition> conditions;

    public RollComponentDice(int numDice, int numSides) {
        setDice(numDice, numSides);
        this.conditions = new ArrayList<>();
        this.operation = RollOperation.Add;
    }

    public int getNumDice() { return numDice; }
    public int getNumSides() { return numSides; }
    public void setDice(int numDice, int numSides) {
        this.numDice = numDice;
        this.numSides = numSides;
    }

    public ArrayList<RollCondition> getConditions() { return conditions; }
    public void setConditions(ArrayList<RollCondition> conditions) { this.conditions = conditions; }
    public void addCondition(RollConditionType conditionType, int conditionValue) {
        conditions.add(new RollCondition(conditionType, conditionValue));
    }
    public void addCondition(RollCondition condition) {
        conditions.add(condition);
    }

    // rolls a single die
    private int rollDie() {
        return ThreadLocalRandom.current().nextInt(1, numSides + 1);
    }

    // rolls all the dice using the given conditions and returns the list of results
    public RollSet roll() {
        int rollTotal = 0;
        ArrayList<Integer> rolls = new ArrayList<>();

        // roll all the dice
        for (int i = 0; i < numDice; i++) {
            int rollValue = rollDie();

            rolls.add(rollValue);
        }

        // Keep track of original rolls
        ArrayList<Integer> originalRolls = new ArrayList<>();
        originalRolls.addAll(rolls);

        // Sort each the roll list in descending order for later use
        Collections.sort(rolls, Collections.reverseOrder());

        // for each condition, apply it to the rolls
        for (RollCondition condition : conditions) {
            applyConditions(condition, rolls, originalRolls);
        }

        // Calculate roll total after conditions are applied
        for (int rollValue : rolls) {
            rollTotal += rollValue;
        }

        // if we're subtracting this, negate the roll total
        if (this.operation == RollOperation.Subtract) rollTotal *= -1;

        return new RollSet(rollTotal, originalRolls, rolls);
    }

    // applies the given condition to the roll
    public void applyConditions(RollCondition condition, ArrayList<Integer> rolls, ArrayList<Integer> originalRolls) {
        switch (condition.type) {
            case DropHighest:
                drop(rolls, condition.conditionValue, true);
                break;
            case DropLowest:
                drop(rolls, condition.conditionValue, false);
                break;
            case Reroll:
                reroll(rolls, originalRolls, condition.conditionValue);
                break;
            default:
                break;
        }
    }

    private void drop(ArrayList<Integer> rolls, int dropAmount, boolean dropHigh) {
        // can't drop more rolls than we have
        if (dropAmount > rolls.size()) dropAmount = rolls.size();

        // if dropHigh, drop the later elements. If not dropHigh, drop the earlier elements.
        int dropIdx = (dropHigh) ? 0 : rolls.size()-1;

        // we want to drop [dropAmount] elements
        for (int i = 0; i < dropAmount; i++) {
            rolls.remove(dropIdx);
        }
    }

    // rerolls the die if it lands on a given value, a maximum of one reroll
    private void reroll(ArrayList<Integer> rolls, ArrayList<Integer> originalRolls, int value) {
        for (int i = 0; i < rolls.size(); i++) {
            int roll = rolls.get(i);
            if (roll == value) {
                int newRoll = rollDie();
                rolls.set(i, newRoll);

                int ogIdx = originalRolls.indexOf(roll);
                originalRolls.set(ogIdx, newRoll);
            }
        }
    }
}
