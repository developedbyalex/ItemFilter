package me.eeshe.itemfilter.commands;

import me.eeshe.itemfilter.files.ConfigManager;
import me.eeshe.itemfilter.util.Messager;
import org.bukkit.command.CommandSender;

public class CommandHelp extends FilterCommand {

    public CommandHelp() {
        setName("help");
        setInfoMessage(ConfigManager.getMessage("help-command-info"));
        setPermission("filter.help");
        setArgumentLength(1);
        setUsageMessage(ConfigManager.getMessage("help-command-usage"));
        setUniversalCommand(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Messager.sendHelpMessage(sender);
    }

}
