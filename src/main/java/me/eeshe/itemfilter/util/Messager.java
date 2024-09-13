package me.eeshe.itemfilter.util;

import me.eeshe.itemfilter.ItemFilter;
import me.eeshe.itemfilter.commands.FilterCommand;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class Messager {

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(StringUtil.formatColor(message));
    }

    public static void sendSuccessMessage(CommandSender sender, String message) {
        sendMessage(sender, message);
        SFXManager.playSuccessSound(sender);
    }

    public static void sendErrorMessage(CommandSender sender, String message) {
        sendMessage(sender, message);
        SFXManager.playErrorSound(sender);
    }

    public static void sendHelpMessage(CommandSender sender) {
        ItemFilter plugin = ItemFilter.getInstance();
        StringBuilder finalMessage = new StringBuilder("&6&lCommands\n");
        Iterator<FilterCommand> subcommandIterator = plugin.getSubcommands().values().iterator();
        while (subcommandIterator.hasNext()) {
            FilterCommand subcommand = subcommandIterator.next();
            if (!sender.hasPermission(subcommand.getPermission())) continue;
            finalMessage.append("&9").append(subcommand.getUsageMessage()).append(" &6- &b").append(subcommand.getInfoMessage());
            if (subcommandIterator.hasNext()) {
                finalMessage.append("\n");
            }
        }
        Messager.sendSuccessMessage(sender, finalMessage.toString());
    }

    public static void sendNoPermissionMessage(CommandSender sender) {
        sendErrorMessage(sender, "&cYou do not have permissions to use this command!");
    }

    public static void sendActionBarMessage(Player player, String message) {
        TextComponent barMessage = new TextComponent(StringUtil.formatColor(message));
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, barMessage);
    }
}
