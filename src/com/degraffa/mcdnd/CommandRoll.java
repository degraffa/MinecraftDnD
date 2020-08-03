package com.degraffa.mcdnd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

        ArrayList<DiceSet> diceSets = processArguments(strings);
        ArrayList<RollSet> rollSets = rollDice(diceSets);
        printRolls(rollSets);

        return true;
    }

    private ArrayList<DiceSet> processArguments(String[] strings) {
        ArrayList<DiceSet> diceSets = new ArrayList<>();

        return diceSets;
    }

    private ArrayList<RollSet> rollDice(ArrayList<DiceSet> diceSets) {
        int rollTotal = 0;
        ArrayList<RollSet> rollSets = new ArrayList<>();

        return rollSets;
    }

    private void printRolls(ArrayList<RollSet> rollSets) {

    }
}
