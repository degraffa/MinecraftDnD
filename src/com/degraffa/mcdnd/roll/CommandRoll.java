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

        Roll roll = processArguments(strings);

        ArrayList<RollSet> rollSets = roll.rollDice();

        getRollString(rollSets);

        return true;
    }

    private Roll processArguments(String[] strings) {
        // step 1: Seperate into distinct chunks
        ArrayList<String> args = new ArrayList<>();
        // for each argument, split it up into its distinct parts to simplify later processing
        for (int i = 0; i < strings.length; i++) {
            String arg = strings[i];
            ArrayList<String> splitArgs = splitArgument(arg);
            args.addAll(splitArgs);
        }

        // Step 2: Find each of the components of the dice roll, and determine how to combine them with operations
        ArrayList<RollComponent> rollComponents = new ArrayList<>();
        // track whether we should add or subtract each roll component
        ArrayList<RollOperation> rollOperations = new ArrayList<>();
        // The first operation is always to add
        rollOperations.add(RollOperation.Add);
        
        
        for (int i = 0; i < strings.length; i++) {
            String s = strings[i];
            RollArgumentType argType = getRollArgType(s);

            switch (argType) {
                case RollComponent:
                    RollComponent rollComponent = rollComponentFromString(s);
                    rollComponents.add(rollComponent);
                    break;
                case Condition:
                    RollCondition rollCondition = rollConditionFromString(s);
                    
                    // do nothing if there aren't any components before this
                    if (rollComponents.size() == 0) break;

                    // add this condition to the previously added roll component
                    rollComponents.get(rollComponents.size()-1).addCondition(rollCondition);
                    break;
                case Operation:
                    RollOperation rollOp = rollOperationFromString(s);
                    
                    // If we already added an operation for this component, combine the two
                    if (rollOperations.size() > rollComponents.size()) {
                        RollOperation lastRollOp = rollOperations.get(rollOperations.size()-1);
                        
                    }
                    // Otherwise, add this operation
                    rollOperations.add(rollOp);
                    break;
            }
        }
        
        // Create a dice roll from this list of components and operations

        return new Roll();
    }

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
            String left = arg.substring(0, opIdx-1);
            splitArgs.add(left);
        }
        // add operator
        String op = arg.substring(opIdx, opIdx);
        splitArgs.add(op);
        // add right half of the equation
        if (caseOne || caseTwo) {
            String right = arg.substring(opIdx+1, arg.length()-1);
            splitArgs.add(right);
        }

        return splitArgs;
    }

    private RollArgumentType getRollArgType(String arg) {
        // 3 Cases for each string:
        // 1: A new roll component
        // 2: A condition on the previous roll component
        // 3: A roll operation

        return RollArgumentType.RollComponent;
    }
    
    // Creates a dice roll component from an argument representing one (ex. 1d20)
    private RollComponent rollComponentFromString(String arg) {
        int dCharIdx = arg.indexOf('d');
        String numDiceString = arg.substring(0, dCharIdx-1);
        String numSidesString = arg.substring(dCharIdx+1, arg.length()-1);
        
        int numDice = Integer.parseInt(numDiceString);
        int numSides = Integer.parseInt(numSidesString);
        
        return new RollComponent(numDice, numSides);
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
            rollTotal += rollSets.get(i).getRollTotal();
        }

        String rollString = "Roll: ";
        StringBuilder sb = new StringBuilder(rollString);

        for (int i = 0; i < rollSets.size(); i++) {
            RollSet rollSet = rollSets.get(i);
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

            // if there are more rollsets after this, add a comma
            if (i != rollSets.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append(" Result: ").append(rollTotal);

        return sb.toString();
    }

    private String getSyntaxError(CommandSender sender, ArrayList<String> args, int badArgIdx, int badCharIdx) {
        return "Improper roll syntax!"; // TODO: Show where they fucked up
    }

    public static void main(String[] args) {
        System.out.println("Test");
        CommandRoll cr = new CommandRoll();

//      TESTING processArguments
        String[] strings = {"1d20"};
        Roll roll = cr.processArguments(strings);

        // TESTING CommandRoll
//        ArrayList<RollSet> testRollSets = new ArrayList<>();
//        ArrayList<Integer> testRolls1 = new ArrayList<>();
//        testRolls1.add(10);
//        testRolls1.add(15);
//        RollSet testRollSet1 = new RollSet(25, testRolls1);
//        testRollSets.add(testRollSet1);
//

//        System.out.println(cr.getRollString(testRollSets));
    }
}
