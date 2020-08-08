package com.degraffa.mcdnd;

import com.degraffa.mcdnd.roll.CommandRoll;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class MCDnD extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();

        // add roll command
        PluginCommand rollCommand = this.getCommand("roll");
        rollCommand.setExecutor(new CommandRoll());
    }

    @Override
    public void onDisable() {
        super.onDisable();

    }
}
