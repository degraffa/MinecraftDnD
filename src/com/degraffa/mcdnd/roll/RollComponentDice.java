package com.degraffa.mcdnd.roll;

import java.util.ArrayList;
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

            rollTotal += rollValue;
            rolls.add(rollValue);
        }

        // for each condition, apply it to the rolls
        for (RollCondition condition : conditions) {
            applyConditions(condition, rolls);
        }

        // if we're subtracting this, negate the roll total
        if (this.operation == RollOperation.Subtract) rollTotal *= -1;

        return new RollSet(rollTotal, rolls);
    }

    // applies the given condition to the roll
    public void applyConditions(RollCondition condition, ArrayList<Integer> rolls) {
        switch (condition.type) {
            case DropHighest:
                dropHighest(rolls);
                break;
            case DropLowest:
                dropLowest(rolls);
                break;
            case DropLessThan:
                dropLessThan(rolls, condition.conditionValue);
                break;
            case DropEqualTo:
                dropEqualTo(rolls, condition.conditionValue);
                break;
            case DropGreaterThan:
                dropGreaterThan(rolls, condition.conditionValue);
                break;
            case ClampHigh:
                clampHigh(rolls, condition.conditionValue);
                break;
            case ClampLow:
                clampLow(rolls, condition.conditionValue);
                break;
            case Reroll:
                reroll(rolls, condition.conditionValue);
                break;
            default:
                break;
        }
    }

    private void dropHighest(ArrayList<Integer> rolls) {
        int highestRoll = -1;
        int highestIdx = 0;

        for (int i = 0; i < rolls.size(); i++) {
            int roll = rolls.get(i);
            if (roll > highestRoll) {
                highestRoll = roll;
                highestIdx = i;
            }
        }

        rolls.remove(highestIdx);
    }

    private void dropLowest(ArrayList<Integer> rolls) {
        int lowestRoll = Integer.MAX_VALUE;
        int lowestIdx = 0;

        for (int i = 0; i < rolls.size(); i++) {
            int roll = rolls.get(i);
            if (roll < lowestRoll) {
                lowestRoll = roll;
                lowestIdx = i;
            }
        }

        rolls.remove(lowestIdx);
    }

    private void dropLessThan(ArrayList<Integer> rolls, int value) {
        for (int i = 0; i < rolls.size(); i++) {
            int roll = rolls.get(i);
            if (roll < value) {
                rolls.remove(i);
            }
        }
    }

    private void dropEqualTo(ArrayList<Integer> rolls, int value) {
        for (int i = 0; i < rolls.size(); i++) {
            int roll = rolls.get(i);
            if (roll == value) {
                rolls.remove(i);
            }
        }
    }

    private void dropGreaterThan(ArrayList<Integer> rolls, int value) {
        for (int i = 0; i < rolls.size(); i++) {
            int roll = rolls.get(i);
            if (roll > value) {
                rolls.remove(i);
            }
        }
    }

    private void clampHigh(ArrayList<Integer> rolls, int value) {
        for (int i = 0; i < rolls.size(); i++) {
            int roll = rolls.get(i);
            if (roll > value) {
                rolls.set(i, value);
            }
        }
    }

    private void clampLow(ArrayList<Integer> rolls, int value) {
        for (int i = 0; i < rolls.size(); i++) {
            int roll = rolls.get(i);
            if (roll < value) {
                rolls.set(i, value);
            }
        }
    }

    private void reroll(ArrayList<Integer> rolls, int value) {
        for (int i = 0; i < rolls.size(); i++) {
            int roll = rolls.get(i);
            if (roll == value) {
                int newRoll = rollDie();
                rolls.set(i, newRoll);
            }
        }
    }
}
