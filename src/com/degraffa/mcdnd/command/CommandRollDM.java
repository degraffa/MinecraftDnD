package com.degraffa.mcdnd.command;

import com.degraffa.mcdnd.roll.RollArgumentParser;
import com.degraffa.mcdnd.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

// command roll that sends roll only to the sender and the DM
public class CommandRollDM implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        RollArgumentParser argumentParser = new RollArgumentParser();
        String rollString = argumentParser.parseRollArguments(commandSender.getName(), strings);

        commandSender.sendMessage(rollString);

        boolean foundDM = false;
        for(Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("DM")) {
                foundDM = true;
                player.sendMessage(rollString);
            }
        }
        if (!foundDM) {
            commandSender.sendMessage("Error: Nobody in the server is a DM");
        }

        return true;
    }
}
