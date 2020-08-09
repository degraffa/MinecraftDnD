package com.degraffa.mcdnd.command;

import com.degraffa.mcdnd.roll.RollArgumentParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class CommandRollMe implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        RollArgumentParser argumentParser = new RollArgumentParser();
        String rollString = argumentParser.parseRollArguments(commandSender.getName(), strings);

        commandSender.sendMessage(rollString);

        return true;
    }
}
