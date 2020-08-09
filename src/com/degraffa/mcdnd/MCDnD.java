package com.degraffa.mcdnd;

import com.degraffa.mcdnd.command.CommandRoll;
import com.degraffa.mcdnd.command.CommandRollDM;
import com.degraffa.mcdnd.command.CommandRollMe;
import com.degraffa.mcdnd.command.CommandRollTo;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class MCDnD extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();

        // add roll commands
        PluginCommand rollCommand = this.getCommand("roll");
        rollCommand.setExecutor(new CommandRoll());

        PluginCommand rollMeCommand = this.getCommand("rollme");
        rollMeCommand.setExecutor(new CommandRollMe());

        PluginCommand rollToCommand = this.getCommand("rollto");
        rollToCommand.setExecutor(new CommandRollTo());

        PluginCommand rollDMCommand = this.getCommand("rolldm");
        rollDMCommand.setExecutor(new CommandRollDM());
    }

    @Override
    public void onDisable() {
        super.onDisable();

    }
}
