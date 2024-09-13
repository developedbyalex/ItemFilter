package me.eeshe.itemfilter.commands;

import me.eeshe.itemfilter.files.ConfigManager;
import me.eeshe.itemfilter.models.FilterPlayer;
import me.eeshe.itemfilter.util.Messager;
import me.eeshe.itemfilter.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAdd extends FilterCommand {

    public CommandAdd() {
        setName("add");
        setInfoMessage(ConfigManager.getMessage("add-command-info"));
        setPermission("filter.add");
        setUsageMessage(ConfigManager.getMessage("add-command-usage"));
        setArgumentLength(1);
        setPlayerCommand(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Material newFilteredItem;
        if (args.length == 1) { // Running /filter add
            newFilteredItem = player.getInventory().getItemInMainHand().getType();
            if (newFilteredItem.isAir()) {
                Messager.sendErrorMessage(player, ConfigManager.getMessage("empty-hand"));
                return;
            }
        } else { // Running /filter add <Item>
            String materialName = args[1];
            newFilteredItem = Material.matchMaterial(materialName);
            if (newFilteredItem == null) {
                String unknownItemMessage = ConfigManager.getMessage("unknown-item");
                unknownItemMessage = unknownItemMessage.replace("%item%", materialName);
                Messager.sendErrorMessage(player, unknownItemMessage);
                return;
            }
        }
        FilterPlayer filterPlayer = FilterPlayer.fromPlayer(player);
        if (filterPlayer.hasFilteredItem(newFilteredItem)) {
            Messager.sendErrorMessage(player, ConfigManager.getMessage("item-already-filtered"));
            return;
        }
        if (!filterPlayer.canAddNewFilters()) {
            Messager.sendErrorMessage(player, ConfigManager.getMessage("filters-exceeded"));
            return;
        }
        filterPlayer.addFilteredItem(newFilteredItem);
        String filterAddMessage = ConfigManager.getMessage("add-command-success");
        filterAddMessage = filterAddMessage.replace("%item%", StringUtil.formatEnum(newFilteredItem));
        Messager.sendSuccessMessage(player, filterAddMessage);
    }
}
