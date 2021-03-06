package com.degraffa.mcdnd.roll;

import com.degraffa.mcdnd.util.StringUtil;

import java.util.ArrayList;

public class RollArgumentParser {
    // how many times should this command be executed?
    private int commandMultiplier;

    public String parseRollArguments(String name, String[] strings) {
        // if too many arguments, make the user be more concise
        if (strings.length == 0) {
            return "Syntax Error: No arguments after command";
        }

        this.commandMultiplier = 1;

        // step 1: Separate into distinct chunks
        ArrayList<String> arguments = splitArguments(strings);

        // Step 2: Determine what kind of argument each argument is
        ArrayList<RollArgumentType> argumentTypes = getArgumentTypes(arguments);

        StringBuilder sb = new StringBuilder();
        // Step 4: Get the roll command from the arguments
        for (int i = 0; i < this.commandMultiplier; i++) {
            Roll roll = getRollFromArguments(arguments, argumentTypes);

            // Step 5: Roll the dice and remember the results
            ArrayList<RollSet> rollSets = roll.roll();

            // Step 6: Create the string to print to the command sender
            boolean isFirst = i == 0;
            String rollString = getRollString(name, strings, rollSets, isFirst);

            // Step 7: Send the roll string to the command sender
            sb.append(rollString);

            // Step 8: Add a new line in between commands if there is more than one
            if (this.commandMultiplier > 1 && i != this.commandMultiplier-1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    // Split each argument into its distinct parts
    private ArrayList<String> splitArguments(String[] strings) {
        ArrayList<String> args = new ArrayList<>();

        for (String arg : strings) {
            ArrayList<String> splitArgs = splitArgument(arg);
            args.addAll(splitArgs);
        }

        return args;
    }

    // Splits a single argument into its individual pieces
    private ArrayList<String> splitArgument(String arg) {
        ArrayList<String> splitOpArgs = new ArrayList<>();

        // just add it immediately if there's nothing to split
        if (arg.length() == 1) {
            splitOpArgs.add(arg);
            return splitOpArgs;
        }

        // start by splitting on +/-
        for (int i = 0; i < arg.length(); i++) {
            char c = arg.charAt(i);

            if (c == '+' || c == '-') {
                // don't split if this is part of +dX / -dX
                if (i == 0) {
                    char c2 = arg.charAt(i+1);
                    if (c2 != 'd') {
                        splitOpArgs.addAll(splitPlusMinus(arg, i));
                    }
                } else {
                    splitOpArgs.addAll(splitPlusMinus(arg, i));
                }
                break;
            }
        }

        // if there was nothing to split, just add the whole string
        if (splitOpArgs.size() == 0) {
            splitOpArgs.add(arg);
        }

        // Now that we have split on +/-, split on condition (in splitArgs)
        ArrayList<String> splitCondArgs = new ArrayList<>();
        for (String s : splitOpArgs) {
            // don't worry about it if its just a single character
            if (s.length() == 1) {
                splitCondArgs.add(s);
                continue;
            }

            splitCondArgs.addAll(splitConditions(s));
        }

        return splitCondArgs;
    }

    // assumes '+' or '-' will be in the string at index [opIdx] in [arg]
    private ArrayList<String> splitPlusMinus(String arg, int opIdx) {
        ArrayList<String> splitArgs = new ArrayList<>();

        // 4 cases:
        // 1: AdX+B
        boolean caseOne = opIdx < arg.length() && opIdx > 0;
        // 2: AdX +B
        boolean caseTwo = arg.length() > 1 && opIdx == 0;
        // 3: AdX+ B
        boolean caseThree = arg.length() > 1 && opIdx == arg.length() - 1;
        // 4: AdX + B

        // add left half of equation
        if (caseOne || caseThree) {
            String left = arg.substring(0, opIdx);
            splitArgs.add(left);
        }
        // add operator
        String op = arg.substring(opIdx, opIdx+1);
        splitArgs.add(op);

        // get the right half of the equation
        String right = "";
        if (caseOne || caseTwo) {
            right = arg.substring(opIdx+1);
        }

        // check to see if the right side has any + or -, if so, recurse down the string until there are none left
        boolean moreComponents = false;
        int nextOpIdx = -1;
        for (int i = 0; i < right.length(); i++) {
            char c = right.charAt(i);
            if (c == '+' || c == '-') {
                moreComponents = true;
                nextOpIdx = i;
                break;
            }
        }
        // if there are more components, recurse
        if (moreComponents) {
            splitArgs.addAll(splitPlusMinus(right, nextOpIdx));
        }
        // otherwise we can just add the right side since its the last one
        else {
            splitArgs.add(right);
        }

        return splitArgs;
    }

    private ArrayList<String> splitConditions(String arg) {
        ArrayList<String> splitArgs = new ArrayList<>();

        RollCondition condition = getRollConditionFromString(arg);

        switch(condition.type) {
            case DropHighest:
            case DropLowest:
            case Reroll:
                splitArgs.addAll(splitOneCharCondition(arg));
                break;
            // if no conditions, don't split anything
            case None:
            default:
                splitArgs.add(arg);
                break;
        }

        return splitArgs;
    }

    // Splits arguments which contain a condition with one character
    private ArrayList<String> splitOneCharCondition(String arg) {
        int dropCharIdx = -1;

        // determine where to split
        for (int i = 0; i < arg.length(); i++) {
            char c = arg.charAt(i);

            if (c == 'h' || c == 'l' || c == 'H' || c == 'L' || c == 'R' || c == 'r') {
                dropCharIdx = i;
                break;
            }
        }

        ArrayList<String> splitArgs = new ArrayList<>();

        // left
        if (dropCharIdx != 0) {
            String left = arg.substring(0, dropCharIdx);
            splitArgs.add(left);
        }

        // middle
        int rightStartIdx = StringUtil.getNextLetterIdx(arg, dropCharIdx+1);
        String conditionString = arg.substring(dropCharIdx, rightStartIdx);
        splitArgs.add(conditionString);

        // right
        if (rightStartIdx <= arg.length() -1) {
            String right = arg.substring(rightStartIdx);

            // recurse down for more conditions
            ArrayList<String> rightSplit = splitConditions(right);
            splitArgs.addAll(rightSplit);
        }

        return splitArgs;
    }

    // Returns the type of each of the arguments
    private ArrayList<RollArgumentType> getArgumentTypes(ArrayList<String> args) {
//        ArrayList<RollComponent> rollComponents = new ArrayList<>();
        ArrayList<RollArgumentType> argumentTypes = new ArrayList<>();

        boolean isFirst = true;
        for (String arg : args) {
            RollArgumentType argType = getArgType(arg, isFirst);
            argumentTypes.add(argType);
            isFirst = false;
        }

        return argumentTypes;
    }

    private RollArgumentType getArgType(String arg, boolean isFirst) {
        if (arg.length() == 0) return RollArgumentType.None;

        char charOne = arg.charAt(0);

        // 4 Cases for each string:
        // 1: A new dice roll component
        for (char c : arg.toCharArray()) {
            if (c == 'd') return RollArgumentType.DiceComponent;
        }

        // 2: A new constant roll component (or a multiplier)
        if (StringUtil.isNumeric(arg)) {
            // if this is the first one, then this is a multiplier
            if (isFirst) {
                this.commandMultiplier = Integer.parseInt(arg);
                return RollArgumentType.Multiplier;
            } else {
                return RollArgumentType.ConstantComponent;
            }
        }

        // 3: A roll operation
        if (charOne == '+' || charOne == '-') return RollArgumentType.Operation;

        // 4: A condition on the previous dice roll component
        // If nothing else returned, assume it's a condition!
        return RollArgumentType.Condition;
    }

    // Creates a Roll object from given arguments
    private Roll getRollFromArguments(ArrayList<String> args, ArrayList<RollArgumentType> argTypes) {
        ArrayList<RollComponent> rollComponents = new ArrayList<>();

        // keep track of dice components in case we go past the max
        int numDiceComponents = 0;

        RollOperation nextRollOp = RollOperation.Add;
        for (int i = 0; i < args.size(); i++) {
            String arg = args.get(i);
            RollArgumentType argType = argTypes.get(i);

            switch (argType) {
                case DiceComponent:
                    RollComponentDice rollComponent = rollComponentDiceFromString(arg);

                    // don't process this if it exceeds any of the maximums
                    boolean isValid = isValidDiceComponent(rollComponent, numDiceComponents);
                    if (!isValid) break;

                    rollComponent.setRollOperation(nextRollOp);
                    nextRollOp = RollOperation.Add;

                    rollComponents.add(rollComponent);

                    numDiceComponents += 1;
                    break;
                case ConstantComponent:
                    RollComponentConstant rollComponentConstant = rollComponentConstantFromString(arg);

                    rollComponentConstant.setRollOperation(nextRollOp);
                    nextRollOp = RollOperation.Add;

                    rollComponents.add(rollComponentConstant);
                    break;
                case Condition:
                    // do nothing if there aren't any components before this
                    if (rollComponents.size() == 0) break;

                    // the last roll component should be a dice roll. If it's a constant, do nothing.
                    RollComponent lastComponent = rollComponents.get(rollComponents.size()-1);
                    if (!(lastComponent instanceof RollComponentDice)) break;

                    RollCondition rollCondition = rollConditionFromString(arg);
                    // cast the component to a dice roll component since we know it has to be one
                    RollComponentDice rollComponentDice = (RollComponentDice)lastComponent;
                    rollComponentDice.addCondition(rollCondition);

                    // Replace the last component with the same one but with the added condition
                    rollComponents.set(rollComponents.size()-1, rollComponentDice);
                    break;
                case Operation:
                    RollOperation rollOp = rollOperationFromString(arg);

                    if (rollOp != nextRollOp) nextRollOp = RollOperation.Subtract;
                    else nextRollOp = RollOperation.Add;

                    break;
                default:
                    // no need to do anything
                    break;
            }
        }

        return new Roll(rollComponents);
    }

    private boolean isValidDiceComponent(RollComponentDice diceComponent, int numDiceComponents) {
        boolean validNumSides = diceComponent.getNumSides() <= RollConstants.MAX_DIE_SIDES;
        boolean validNumDice = diceComponent.getNumDice() <= RollConstants.MAX_DICE;
        boolean validNumComponents = numDiceComponents <= RollConstants.MAX_DICE_COMPONENTS;

        validNumDice = validNumDice && diceComponent.getNumDice() > 0;
        validNumSides = validNumSides && diceComponent.getNumSides() > 0;

        return validNumSides && validNumDice && validNumComponents;
    }

    // Creates a dice roll component from an argument representing one (ex. 1d20)
    private RollComponentDice rollComponentDiceFromString(String arg) {
        ArrayList<RollCondition> rollConditions = new ArrayList<>();

        int dCharIdx = arg.indexOf('d');

        int numDice;

        if (dCharIdx == 1 && arg.charAt(0) == '+') {
            numDice = 2;
            rollConditions.add(new RollCondition(RollConditionType.DropLowest, 1));
        }
        else if (dCharIdx == 1 && arg.charAt(0) == '-') {
            numDice = 2;
            rollConditions.add(new RollCondition(RollConditionType.DropHighest, 1));
        }
        else if (dCharIdx == 0) {
            numDice = 1;
        }
        else {
            String numDiceString = arg.substring(0, dCharIdx);
            numDice = Integer.parseInt(numDiceString);
        }

        String numSidesString = arg.substring(dCharIdx+1);

        int numSides = Integer.parseInt(numSidesString);

        RollComponentDice rollComponentDice = new RollComponentDice(numDice, numSides);
        // apply any macro conditions to the component dice
        for (RollCondition condition : rollConditions) {
            rollComponentDice.addCondition(condition);
        }
        return rollComponentDice;
    }

    // Creates a constant roll component from string representing one (ex. 50)
    private RollComponentConstant rollComponentConstantFromString(String arg) {
        int constant = Integer.parseInt(arg);

        return new RollComponentConstant(constant);
    }

    // Creates a condition from an argument representing one (ex. D3)
    private RollCondition rollConditionFromString(String arg) {
        char charOne = arg.charAt(0);

        boolean dropHigh = charOne == 'h' || charOne == 'H';
        boolean dropLow = charOne == 'l' || charOne == 'L';
        boolean reroll = charOne == 'R' || charOne == 'r';

        // if the argument has more than just one character, that means it must have a value afterwards
        // Get the value of the value baby
        int value = 1;
        if (arg.length() > 1) {
            int nextLetterIdx = StringUtil.getNextLetterIdx(arg, 1);
            value = Integer.parseInt(arg.substring(1, nextLetterIdx));
        }

        if (dropHigh || dropLow) {
            RollConditionType conditionType = (dropHigh) ? RollConditionType.DropHighest : RollConditionType.DropLowest;

            return new RollCondition(conditionType, value);
        } else if (reroll) {
            return new RollCondition(RollConditionType.Reroll, value);
        }

        return new RollCondition(RollConditionType.None, 1);
    }

    // Creates a roll operation from an argument representing one
    private RollOperation rollOperationFromString(String arg) {
        RollOperation op;

        switch(arg.charAt(0)) {
            case '-':
                op = RollOperation.Subtract;
                break;
            case '+':
            default:
                op = RollOperation.Add;
                break;
        }

        return op;
    }

    // returns the first roll condition found in a given string
    private RollCondition getRollConditionFromString(String arg) {
        RollCondition noneCondition = new RollCondition(RollConditionType.None, 0);

        for (int i = 0; i < arg.length(); i++) {
            char c = arg.charAt(i);

            // Conditions with one character and possibly some numbers, if no numbers, default of 1
            if (c == 'h' || c == 'H' || c == 'l' || c == 'L' || c == 'R' || c == 'r') {
                int conditionValue = 1;

                // if more characters, check for drop amount
                if (i != arg.length() - 1) {
                    int nextIdx = i+1;
                    int nextLetterIdx = StringUtil.getNextLetterIdx(arg, nextIdx);

                    // only parse a number if there is one to parse
                    if (nextLetterIdx != nextIdx) {
                        String dropAmountString = arg.substring(nextIdx, nextLetterIdx);
                        conditionValue = Integer.parseInt(dropAmountString);
                    }
                }

                RollConditionType conditionType = RollConditionType.None;
                switch(c) {
                    case 'H':
                    case 'h':
                        conditionType = RollConditionType.DropHighest;
                        break;
                    case 'L':
                    case 'l':
                        conditionType = RollConditionType.DropLowest;
                        break;
                    case 'R':
                    case 'r':
                        conditionType = RollConditionType.Reroll;
                        break;
                }

                return new RollCondition(conditionType, conditionValue);
            }
            // we've reached another component, end now
            else if (c == '+' || c == '-') {
                return noneCondition;
            }
        }

        return noneCondition;
    }

    private String getRollString(String name, String[] strings, ArrayList<RollSet> rollSets, boolean isFirst) {
        StringBuilder sb = new StringBuilder();

        // Step 1: Print Command if this is the first one
        if (isFirst) {
            sb.append(name);
            sb.append(" rolled ");

            for (String arg : strings) {
                sb.append(arg);
                sb.append(" ");
            }

            sb.append("\n");
        }

        // Step 2: Print results
        sb.append("Result: ");
        int rollTotal = 0;
        int constantSum = 0;

        for (RollSet rs : rollSets) {
            rollTotal += rs.getRollValue();
        }

        for (int i = 0; i < rollSets.size(); i++) {
            RollSet rollSet = rollSets.get(i);
            if (rollSet.isConstant()) {
                constantSum += rollSet.getRollValue();
                continue;
            }

            sb.append("[");

            for (int j = 0; j < rollSet.getOriginalRolls().size(); j++) {
                int roll = rollSet.getOriginalRolls().get(j);
                sb.append(roll);

                // if this is not the last roll, add a comma
                if (j != rollSet.getOriginalRolls().size() - 1) {
                    sb.append(", ");
                }
            }

            sb.append("]");

            // if there are more non-constant rollsets after this, add a comma
            if (i != rollSets.size() - 1 && !rollSets.get(i+1).isConstant()) {
                sb.append(", ");
            }
        }

        if (constantSum != 0) {
            if (constantSum < 0) sb.append(" - ");
            else sb.append(" + ");

            sb.append(constantSum);
        }

        // put the total on a new line if the string before was too long
        if (sb.length() > RollConstants.MAX_CHARS_FOR_ONE_LINE) {
            sb.append("\n");
        } else {
            // otherwise, just separate it by a space
            sb.append(" ");
        }
        sb.append("Total: ").append(rollTotal);

        return sb.toString();
    }

    public static void main(String[] args) {
        RollArgumentParser rap = new RollArgumentParser();

        String[] testStrings = {"6", "1d20H"};

        rap.commandMultiplier = 1;

        // step 1: Separate into distinct chunks
        ArrayList<String> arguments = rap.splitArguments(testStrings);

        // Step 2: Determine what kind of argument each argument is
        ArrayList<RollArgumentType> argumentTypes = rap.getArgumentTypes(arguments);

        // Step 4: Get the roll command from the arguments
        for (int i = 0; i < rap.commandMultiplier; i++) {
            Roll roll = rap.getRollFromArguments(arguments, argumentTypes);

            // Step 5: Roll the dice and remember the results
            ArrayList<RollSet> rollSets = roll.roll();

            // Step 6: Create the string to print to the command sender
            boolean isFirst = i == 0;
            String rollString = rap.getRollString("degraffa", testStrings, rollSets, isFirst);

            // Step 7: Send the roll string to the command sender
            System.out.println(rollString);
        }
    }
}
