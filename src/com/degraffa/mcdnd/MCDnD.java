package com.degraffa.mcdnd;

import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class MCDnD extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();

        PluginCommand rollCommand = this.getCommand("roll");
        rollCommand.setExecutor(new CommandRoll());
        TabCompleter rollTabCompleter = rollCommand.getTabCompleter();

    }

    @Override
    public void onDisable() {
        super.onDisable();

    }
}
