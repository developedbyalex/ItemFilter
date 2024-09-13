package me.eeshe.itemfilter.commands;

import me.eeshe.itemfilter.ItemFilter;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommandCompleter implements TabCompleter {
    private final ItemFilter plugin;
    private final ArrayList<String> materialNames;

    public CommandCompleter(ItemFilter plugin) {
        this.plugin = plugin;
        this.materialNames = getMaterialNames();
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> arguments = new ArrayList<>();
        Collection<FilterCommand> subcommands = plugin.getSubcommands().values();
        for (FilterCommand subcommand : subcommands) {
            if (!sender.hasPermission(subcommand.getPermission())) continue;

            arguments.add(subcommand.getName());
        }
        FilterCommand subcommand = plugin.getSubcommands().get(args[0]);
        if (args.length > 1 && (subcommand == null || !sender.hasPermission(subcommand.getPermission()))) {
            arguments.clear();
            return arguments;
        }
        if (args.length < 2) {
            return getCompletion(arguments, args, 0);
        } else if (args.length < 3) {
            if (subcommand.getName().equals("add")) {
                return getCompletion(materialNames, args, 1);
            }
        }
        arguments.clear();
        return arguments;
    }

    private ArrayList<String> getCompletion(ArrayList<String> arguments, String[] args, int index) {
        ArrayList<String> results = new ArrayList<>();
        for (String argument : arguments) {
            if (!argument.toLowerCase().startsWith(args[index].toLowerCase())) continue;

            results.add(argument);
        }
        return results;
    }

    private ArrayList<String> getMaterialNames() {
        ArrayList<String> materialNames = new ArrayList<>();
        for (Material material : Material.values()) {
            materialNames.add(material.name());
        }
        return materialNames;
    }
}
