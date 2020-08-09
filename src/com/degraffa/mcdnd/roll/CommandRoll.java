package com.degraffa.mcdnd.roll;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class CommandRoll implements CommandExecutor {
    // maximum amount of dice that can be rolled in a single command
    public final int MAX_DICE = 1000;
    // maximum sided die that can be rolled
    public final int MAX_DIE_SIDES = 10000;
    // max size of a list in a dice roll
    public final int MAX_REROLL_VALUES = 4;
    // maximum amount of arguments until the command is rejected
    public final int MAX_ARGUMENT_LENGTH = 20;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // first, try to discern what kind of command this is based on the parameters
        // if no additional arguments, then there's nothing to roll
        if (strings.length == 0) {
            commandSender.sendMessage("Must specify dice notation after roll. For example, /roll 1d6");
            return false;
        }

        // if too many arguments, make the user be more concise
        if (strings.length > MAX_ARGUMENT_LENGTH) {
            commandSender.sendMessage("Too many arguments, you may use up to a maximum of " + MAX_ARGUMENT_LENGTH + " arguments");
        }

        // acceptable patterns for the first argument
        // 1 - dX where X is an integer between 1 and [MAX_DIE_SIDES]
        //  - Rolls a single dice with X sides
        // 2 - AdX - where A is an integer between 1 and [MAX_DICE] and X is an integer between 1 nad [MAX_DIE_SIDES]
        //  - rolls A dice with X sides each
        //  - Additions:
        //      ADDING ROLLS:
        //      - +/- AdX: You can add/subtract another roll to this one
        //          - This can have its own additions
        //      - +/- X: You can add an integer to this
        //
        //      DROPPING ROLLS: Drops can be chained together
        //      - h: Drops the highest roll
        //          - hX: Drops the highest X rolls, where X is an integer between 1 and Integer.MAX_VALUE
        //      - l: Drops the lowest roll
        //          - lX: Drops the lowest X rolls, where X is an integer between 1 and Integer.MAX_VALUE
        //
        //      CONDITIONS:
        //      - D<X: drop any roll less than X, where X is an integer
        //      - D>X: drop any roll that is greater than X, where X is an integer
        //      - D<X>Y: drop any roll that is less than X or greater than Y, where X and Y are integers
        //      - D=X: drop any roll that is equal to X
        //      - Example: 4d20D<5=9=11>15: Roll 4d20, drop dice below 5, dice above15, and dice that roll 9 or 11
        //
        //      CLAMPING ROLLS:
        //      - C<X: Treat any roll less than X as X, where X is a positive integer
        //      - C>X: Treat any roll greater than X as X, where X is a positive integer
        //      - C<X>Y: Treat any roll less than X as X, and any value greater than Y as Y, where X and Y are positive integers]
        //
        //      REROLL:
        //      - R{X, ...., Y}A: Rerolls any rolls equal the result in the list, up to A times. List has max size MAX_REROLL_VALUES
        //
        //      COUNT:
        //      - #: Count how many dice roll the maximum value
        //      - #[CONDITION]: Count how many die roll that meet the condition
        //
        //      SPECIAL:
        //      - u: All rolls will be unique. If more die are rolled than there are faces, this command will fail.
        //          - Cannot have any other conditions

        // step 1: Separate into distinct chunks
        ArrayList<String> arguments = processArguments(strings);

        // Step 2: Determine what kind of argument each argument is
        ArrayList<RollArgumentType> argumentTypes = getArgumentTypes(arguments);

        // TODO: Step 3: Verify that the arguments are syntactically correct
        boolean goodSyntax = verifyArgumentSyntax(arguments, argumentTypes);
        // end early if the syntax is bad
        if (!goodSyntax) {
            commandSender.sendMessage("Incorrect roll syntax");
            return false;
        }

        // Step 4: Get the roll command from the arguments
        Roll roll = getRollFromArguments(arguments, argumentTypes);

        // Step 5: Roll the dice and remember the results
        ArrayList<RollSet> rollSets = roll.roll();

        // Step 6: Create the string to print to the command sender
        String rollString = getRollString(rollSets);

        // Step 7: Send the roll string to the command sender
        commandSender.sendMessage(rollString);

        return true;
    }

    private ArrayList<String> processArguments(String[] strings) {
        ArrayList<String> args = new ArrayList<>();

        // for each argument, split it up into its distinct parts to simplify later processing
        for (int i = 0; i < strings.length; i++) {
            String arg = strings[i];
            ArrayList<String> splitArgs = splitArgument(arg);
            args.addAll(splitArgs);
        }

        return args;
    }

    // Splits a single argument into its individual pieces
    private ArrayList<String> splitArgument(String arg) {
        ArrayList<String> splitArgs = new ArrayList<>();

        for (int i = 0; i < arg.length(); i++) {
            char c = arg.charAt(i);

            if (c == '+' || c == '-') {
                splitArgs.addAll(splitPlusMinus(arg, i));
                break;
            }
        }
        // if there was nothing to split, just add the whole string
        if (splitArgs.size() == 0) {
            splitArgs.add(arg);
        }

        return splitArgs;
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
        boolean caseFour = arg.length() == 1;

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
        // if there are more components, recurse and only add the part before the next '+' or '-'
        if (moreComponents) {
            splitArgs.add(right.substring(0, nextOpIdx));
            splitArgs.addAll(splitPlusMinus(right, nextOpIdx));
        }
        // otherwise you can add the whole thing
        else {
            splitArgs.add(right);
        }

        return splitArgs;
    }

    // Returns the type of each of the arguments
    private ArrayList<RollArgumentType> getArgumentTypes(ArrayList<String> args) {
//        ArrayList<RollComponent> rollComponents = new ArrayList<>();
        ArrayList<RollArgumentType> argumentTypes = new ArrayList<>();

        for (int i = 0; i < args.size(); i++) {
            String arg = args.get(i);
            RollArgumentType argType = getRollArgType(arg);
            argumentTypes.add(argType);
        }

        return argumentTypes;
    }

    private RollArgumentType getRollArgType(String arg) {
        char charOne = arg.charAt(0);

        // 4 Cases for each string:
        // 1: A new dice roll component
        for (char c : arg.toCharArray()) {
            if (c == 'd') return RollArgumentType.DiceComponent;
        }

        // 2: A new constant roll component
        boolean isConstant = true;
        for (char c : arg.toCharArray()) {
            if (!Character.isDigit(c)) isConstant = false;
        }
        if (isConstant) return RollArgumentType.ConstantComponent;

        // 3: A roll operation
        if (charOne == '+' || charOne == '-') return RollArgumentType.Operation;

        // 4: A condition on the previous dice roll component
        // If nothing else returned, assume it is a condition
        return RollArgumentType.Condition;
    }

    // Returns true if each of the arguments is syntactically correct, false otherwise
    private boolean verifyArgumentSyntax(ArrayList<String> args, ArrayList<RollArgumentType> argumentTypes) {
        return true;
    }

    // Creates a Roll object from given arguments
    private Roll getRollFromArguments(ArrayList<String> args, ArrayList<RollArgumentType> argumentTypes) {
        ArrayList<RollComponent> rollComponents = new ArrayList<>();

        RollOperation nextRollOp = RollOperation.Add;
        for (int i = 0; i < args.size(); i++) {
            String arg = args.get(i);
            RollArgumentType argType = getRollArgType(arg);

            switch (argType) {
                case DiceComponent:
                    RollComponentDice rollComponent = rollComponentDiceFromString(arg);

                    rollComponent.setRollOperation(nextRollOp);
                    nextRollOp = RollOperation.Add;

                    rollComponents.add(rollComponent);
                    break;
                case ConstantComponent:
                    RollComponentConstant rollComponentConstant = rollComponentConstantFromString(arg);

                    rollComponentConstant.setRollOperation(nextRollOp);
                    nextRollOp = RollOperation.Add;

                    rollComponents.add(rollComponentConstant);
                    break;
                case Condition:
                    RollCondition rollCondition = rollConditionFromString(arg);

                    // do nothing if there aren't any components before this
                    if (rollComponents.size() == 0) break;

                    // the last roll component should be a dice roll. If it's a constant, do nothing.
                    RollComponent lastComponent = rollComponents.get(rollComponents.size()-1);
                    if (!(lastComponent instanceof RollComponentDice)) break;

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
            }
        }

        return new Roll(rollComponents);
    }
    
    // Creates a dice roll component from an argument representing one (ex. 1d20)
    private RollComponentDice rollComponentDiceFromString(String arg) {

        int dCharIdx = arg.indexOf('d');
        int numDice;
        if (dCharIdx == 0) {
            numDice = 1;
        } else {
            String numDiceString = arg.substring(0, dCharIdx);
            numDice = Integer.parseInt(numDiceString);
        }

        String numSidesString = arg.substring(dCharIdx+1);

        int numSides = Integer.parseInt(numSidesString);
        
        return new RollComponentDice(numDice, numSides);
    }

    // Creates a constant roll component from string representing one (ex. 50)
    private RollComponentConstant rollComponentConstantFromString(String arg) {
        int constant = Integer.parseInt(arg);

        return new RollComponentConstant(constant);
    }
    
    // Creates a condition from an argument representing one (ex. D3)
    private RollCondition rollConditionFromString(String arg) {
        // TODO: Complete this
        return new RollCondition(RollConditionType.DropHighest, 1);
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

    private String getRollString(ArrayList<RollSet> rollSets) {
        int rollTotal = 0;

        for (int i = 0; i < rollSets.size(); i++) {
            rollTotal += rollSets.get(i).getRollValue();
        }

        String rollString = "Roll: ";
        StringBuilder sb = new StringBuilder(rollString);

        int constantSum = 0;

        for (int i = 0; i < rollSets.size(); i++) {
            RollSet rollSet = rollSets.get(i);
            if (rollSet.isConstant()) {
                constantSum += rollSet.getRollValue();
                continue;
            }

            sb.append("[");

            for (int j = 0; j < rollSet.getRolls().size(); j++) {
                int roll = rollSet.getRolls().get(j);
                sb.append(roll);

                // if this is not the last roll, add a comma
                if (j != rollSet.getRolls().size() - 1) {
                    sb.append(", ");
                }
            }

            sb.append("]");

            // if there are more non-constnat rollsets after this, add a comma
            if (i != rollSets.size() - 1 && !rollSets.get(i+1).isConstant()) {
                sb.append(", ");
            }
        }

        if (constantSum != 0) {
            if (constantSum < 0) sb.append(" - ");
            else sb.append(" + ");

            sb.append(constantSum);
        }

        sb.append("\nResult: ").append(rollTotal);

        return sb.toString();
    }

    public static void main(String[] args) {
        CommandRoll cr = new CommandRoll();

        String[] testStrings = {"1d100-1d20+2"};

        // step 1: Separate into distinct chunks
        ArrayList<String> arguments = cr.processArguments(testStrings);

        // Step 2: Determine what kind of argument each argument is
        ArrayList<RollArgumentType> argumentTypes = cr.getArgumentTypes(arguments);

        // Step 4: Get the roll command from the arguments
        Roll roll = cr.getRollFromArguments(arguments, argumentTypes);

        // Step 5: Roll the dice and remember the results
        ArrayList<RollSet> rollSets = roll.roll();

        // Step 6: Create the string to print to the command sender
        String rollString = cr.getRollString(rollSets);

        System.out.println(rollString);
    }
}
