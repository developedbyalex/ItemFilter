package me.eeshe.itemfilter.commands;

import me.eeshe.itemfilter.files.ConfigManager;
import me.eeshe.itemfilter.inventories.FilterSettingsGUI;
import me.eeshe.itemfilter.models.FilterPlayer;
import me.eeshe.itemfilter.util.SFXManager;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRemove extends FilterCommand {

    public CommandRemove() {
        setName("remove");
        setInfoMessage(ConfigManager.getMessage("remove-command-info"));
        setPermission("filter.remove");
        setUsageMessage(ConfigManager.getMessage("remove-command-usage"));
        setArgumentLength(1);
        setPlayerCommand(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        FilterPlayer filterPlayer = FilterPlayer.fromPlayer(player);
        player.openInventory(new FilterSettingsGUI().getGUI(filterPlayer, 1));
        SFXManager.playPlayerSound(player, Sound.UI_BUTTON_CLICK, 0.6F, 1.8F);
    }
}
