package me.eeshe.itemfilter;

import me.eeshe.itemfilter.commands.*;
import me.eeshe.itemfilter.files.FilterPlayerManager;
import me.eeshe.itemfilter.listeners.FilterSettingsGUIHandler;
import me.eeshe.itemfilter.listeners.PickupHandler;
import me.eeshe.itemfilter.listeners.PlayerConnectionHandler;
import me.eeshe.itemfilter.models.FilterPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class ItemFilter extends JavaPlugin {
    //           CommandName, SubcommandInstance
    private final Map<String, FilterCommand> subcommands = new LinkedHashMap<>();
    private final Map<Player, FilterPlayer> filterPlayers = new HashMap<>();
    private FilterPlayerManager filterPlayerManager;

    public static ItemFilter getInstance() {
        return ItemFilter.getPlugin(ItemFilter.class);
    }

    @Override
    public void onEnable() {
        configureFiles();
        registerManagers();
        registerCommands();
        registerListeners();
        filterPlayerManager.loadOnlineFilterPlayers();
    }

    private void registerManagers() {
        this.filterPlayerManager = new FilterPlayerManager(this);
    }

    private void configureFiles() {
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        filterPlayerManager.saveOnlineFilterPlayers();
    }

    private void registerCommands() {
        subcommands.put("add", new CommandAdd());
        subcommands.put("remove", new CommandRemove());
        subcommands.put("help", new CommandHelp());
        subcommands.put("reload", new CommandReload(this));
        getCommand("filter").setExecutor(new CommandRunner(this));
        getCommand("filter").setTabCompleter(new CommandCompleter(this));
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerConnectionHandler(filterPlayerManager), this);
        pluginManager.registerEvents(new PickupHandler(), this);
        pluginManager.registerEvents(new FilterSettingsGUIHandler(this), this);
    }

    public Map<String, FilterCommand> getSubcommands() {
        return subcommands;
    }

    public Map<Player, FilterPlayer> getFilterPlayers() {
        return filterPlayers;
    }

    public FilterPlayerManager getFilterPlayerManager() {
        return filterPlayerManager;
    }
}
