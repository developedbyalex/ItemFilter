package me.eeshe.itemfilter.commands;

import me.eeshe.itemfilter.ItemFilter;
import me.eeshe.itemfilter.files.ConfigManager;
import me.eeshe.itemfilter.util.Messager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRunner implements CommandExecutor {
    private final ItemFilter plugin;

    public CommandRunner(ItemFilter plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("filter")) return false;
        if (args.length < 1) {
            Messager.sendHelpMessage(sender);
            return true;
        }
        String inputCommand = args[0].toLowerCase();
        if (!plugin.getSubcommands().containsKey(inputCommand)) {
            Messager.sendErrorMessage(sender, ConfigManager.getMessage("unknown-command"));
            return true;
        }
        FilterCommand subcommand = plugin.getSubcommands().get(inputCommand);
        if (subcommand.isPlayerCommand() && !(sender instanceof Player)) {
            Messager.sendErrorMessage(sender, ConfigManager.getMessage("not-console-command"));
            return true;
        }
        if (subcommand.isConsoleCommand() && sender instanceof Player) {
            Messager.sendErrorMessage(sender, "&cNot available for players.");
            return true;
        }
        if (args.length < subcommand.getArgumentLength()) {
            Messager.sendErrorMessage(sender, "&cUsage: &l" + subcommand.getUsageMessage());
            return true;
        }
        if (!sender.hasPermission(subcommand.getPermission())) {
            Messager.sendNoPermissionMessage(sender);
            return true;
        }
        subcommand.execute(sender, args);
        return true;
    }
}
