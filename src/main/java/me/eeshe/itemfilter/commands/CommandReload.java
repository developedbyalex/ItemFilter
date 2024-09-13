package me.eeshe.itemfilter.commands;

import me.eeshe.itemfilter.ItemFilter;
import me.eeshe.itemfilter.files.ConfigManager;
import me.eeshe.itemfilter.util.Messager;
import org.bukkit.command.CommandSender;

public class CommandReload extends FilterCommand {
    private final ItemFilter plugin;

    public CommandReload(ItemFilter plugin) {
        this.plugin = plugin;

        setName("reload");
        setInfoMessage(ConfigManager.getMessage("reload-command-info"));
        setPermission("filter.reload");
        setArgumentLength(1);
        setUsageMessage(ConfigManager.getMessage("reload-command-usage"));
        setUniversalCommand(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        plugin.reloadConfig();
        Messager.sendSuccessMessage(sender, ConfigManager.getMessage("reload-command-success"));
    }
}
