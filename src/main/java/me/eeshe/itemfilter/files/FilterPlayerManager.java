package me.eeshe.itemfilter.files;

import me.eeshe.itemfilter.ItemFilter;
import me.eeshe.itemfilter.models.FilterPlayer;
import me.eeshe.itemfilter.util.LogUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FilterPlayerManager {
    private final ItemFilter plugin;

    public FilterPlayerManager(ItemFilter plugin) {
        this.plugin = plugin;
    }

    /**
     * Saves all the data of the passed FilterPlayer to its corresponding .yml file.
     *
     * @param filterPlayer FilterPlayer that will be saved.
     */
    public void saveFilterPlayer(FilterPlayer filterPlayer) {
        FilterPlayerFile filterPlayerFile = new FilterPlayerFile(plugin, filterPlayer);
        FileConfiguration playerData = filterPlayerFile.getData();
        saveFilteredItems(filterPlayer, playerData);
        filterPlayerFile.saveData();
    }

    /**
     * Saves the filtered items from the passed player into the passed FileConfiguration.
     *
     * @param filterPlayer Filtered Player which filtered items will be saved.
     * @param playerData   FileConfiguration where the filtered items will be saved to.
     */
    private void saveFilteredItems(FilterPlayer filterPlayer, FileConfiguration playerData) {
        playerData.set("filtered-items", null);
        List<String> filteredItemsStringList = new ArrayList<>();
        for (Material item : filterPlayer.getFilteredItems()) {
            filteredItemsStringList.add(item.name());
        }
        playerData.set("filtered-items", filteredItemsStringList);
    }

    /**
     * Saves the data of all the online players into their respective .yml file.
     */
    public void saveOnlineFilterPlayers() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            FilterPlayer.fromPlayer(online).unregisterFilterPlayer();
        }
    }

    /**
     * Loads the filter player from the .yml file that corresponds to the passed Player. If there isn't a .yml file
     * for that player then return a new FilterPlayer.
     *
     * @param player Player that will attempt to be loaded.
     * @return FilterPlayer corresponding to the passed player.
     */
    public FilterPlayer loadFilterPlayer(Player player) {
        FilterPlayerFile filterPlayerFile = new FilterPlayerFile(plugin, player);
        FileConfiguration playerData = filterPlayerFile.getData();
        if (playerData.getKeys(true).size() == 0) {
            // File is empty
            return new FilterPlayer(player);
        }
        List<Material> filteredItems = loadFilteredItems(playerData);
        return new FilterPlayer(player, filteredItems);
    }

    /**
     * Loads the saved filtered items from the passed FileConfiguration.
     *
     * @param playerData FileConfiguration that contains the player's data.
     * @return List of Materials that represents the filtered items.
     */
    private List<Material> loadFilteredItems(FileConfiguration playerData) {
        List<Material> filteredItems = new ArrayList<>();
        List<String> filteredItemsNames = playerData.getStringList("filtered-items");
        if (filteredItemsNames.isEmpty()) return filteredItems;
        for (String itemName : filteredItemsNames) {
            Material material = Material.matchMaterial(itemName);
            if (material == null) {
                LogUtil.sendWarnLog("Unknown material '" + itemName + "' in file '" + playerData.getName() + "'.");
                continue;
            }
            filteredItems.add(material);
        }
        return filteredItems;
    }

    /**
     * Loads the FilterPlayer data for all the players that are currently online on the server.
     */
    public void loadOnlineFilterPlayers() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            loadFilterPlayer(online).registerFilterPlayer();
        }
    }
}
