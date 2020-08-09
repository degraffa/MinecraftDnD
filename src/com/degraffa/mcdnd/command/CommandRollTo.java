package com.degraffa.mcdnd.command;

import com.degraffa.mcdnd.roll.RollArgumentParser;
import com.degraffa.mcdnd.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

// command sends roll to sender and specified player
public class CommandRollTo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length < 2) commandSender.sendMessage("Incorrect Syntax");

        String playerName = strings[0];
        ArrayList<String> args = new ArrayList<>();
        for (int i = 1; i < strings.length; i++) {
            args.add(strings[i]);
        }
        String[] commandStrings = (String[])args.toArray();

        RollArgumentParser argumentParser = new RollArgumentParser();
        String rollString = argumentParser.parseRollArguments(commandSender.getName(), commandStrings);

        commandSender.sendMessage(rollString);

        // only send this message if it's to another player
        boolean isCommandSender = commandSender.getName().equals(playerName);
        if (isCommandSender) return true;

        boolean foundPlayer = false;
        for (Player player : Bukkit.getOnlinePlayers()) {

            if (player.getName() == playerName) {
                player.sendMessage(rollString);

                foundPlayer = true;

                break;
            }
        }

        if (!foundPlayer) {
            commandSender.sendMessage("Error: Could not find player with name " + playerName);
        }

        return true;
    }
}
