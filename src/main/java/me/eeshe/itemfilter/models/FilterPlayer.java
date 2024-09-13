package me.eeshe.itemfilter.models;

import me.eeshe.itemfilter.ItemFilter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FilterPlayer {
    private final Player player;
    private final List<Material> filteredItems;

    public FilterPlayer(Player player) {
        this.player = player;
        this.filteredItems = new ArrayList<>();
    }

    public FilterPlayer(Player player, List<Material> filteredItems) {
        this.player = player;
        this.filteredItems = filteredItems;
    }

    /**
     * Searches the FilterPlayer that corresponds to the passed Player.
     *
     * @param player Player that will be searched.
     * @return FilterPlayer corresponding to the passed Player. Null if it couldn't be found.
     */
    public static FilterPlayer fromPlayer(Player player) {
        return ItemFilter.getInstance().getFilterPlayers().get(player);
    }

    public void registerFilterPlayer() {
        ItemFilter.getInstance().getFilterPlayers().put(player, this);
    }

    public void unregisterFilterPlayer() {
        ItemFilter.getInstance().getFilterPlayers().remove(player);
        saveFilterPlayerData();
    }

    private void saveFilterPlayerData() {
        ItemFilter.getInstance().getFilterPlayerManager().saveFilterPlayer(this);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Material> getFilteredItems() {
        return filteredItems;
    }

    /**
     * Searches the passed Material in the list of filtered items to check if it has been filtered yet.
     *
     * @param item Material of the item that will be checked.
     * @return True if the item is already filtered.
     */
    public boolean hasFilteredItem(Material item) {
        return filteredItems.contains(item);
    }

    /**
     * Checks the permissions of the player to see if it can add new filters based on the current amount of filters
     * it has.
     *
     * @return True if the player can add new filters.
     */
    public boolean canAddNewFilters() {
        if (player.hasPermission("filter.max.*")) return true;
        int maxAmount = 400;
        int currentFilters = filteredItems.size();
        for (int i = currentFilters; i < maxAmount; i++) {
            if (!player.hasPermission("filter.max." + i)) continue;

            return i > currentFilters;
        }
        return false;
    }

    /**
     * Adds the passed item to the list of filtered items and saves the player's data.
     *
     * @param item Material of the item that will be added to the item filter.
     */
    public void addFilteredItem(Material item) {
        filteredItems.add(item);
        saveFilterPlayerData();
    }

    /**
     * Removes the passed item to the list of filtered items and saves the player's data.
     *
     * @param item Material of the item that will be removed from the item filter.
     */
    public void removeFilteredItem(Material item) {
        filteredItems.remove(item);
        saveFilterPlayerData();
    }

    /**
     * Removes all the filtered items and saves the player's data.
     */
    public void removeAllFiteredItems() {
        filteredItems.clear();
        saveFilterPlayerData();
    }
}
