package com.degraffa.mcdnd.command;

import com.degraffa.mcdnd.roll.RollArgumentParser;
import com.degraffa.mcdnd.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class CommandRoll implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        RollArgumentParser argumentParser = new RollArgumentParser();
        String rollString = argumentParser.parseRollArguments(commandSender.getName(), strings);

        Bukkit.broadcastMessage(rollString);

        return true;
    }
}
