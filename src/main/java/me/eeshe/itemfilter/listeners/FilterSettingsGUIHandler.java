package me.eeshe.itemfilter.listeners;

import me.eeshe.itemfilter.ItemFilter;
import me.eeshe.itemfilter.files.ConfigManager;
import me.eeshe.itemfilter.inventories.FilterSettingsGUI;
import me.eeshe.itemfilter.inventories.FilterSettingsHolder;
import me.eeshe.itemfilter.models.FilterPlayer;
import me.eeshe.itemfilter.util.GUIUtil;
import me.eeshe.itemfilter.util.Messager;
import me.eeshe.itemfilter.util.SFXManager;
import me.eeshe.itemfilter.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class FilterSettingsGUIHandler implements Listener {
    private final ItemFilter plugin;
    // Stores the players that have been asked to confirm a filter deletion.
    private final Set<Player> deleteConfirmations = new HashSet<>();

    public FilterSettingsGUIHandler(ItemFilter plugin) {
        this.plugin = plugin;
    }

    /**
     * Listens when a player clicks a GUI. If it is the Filter Settings GUI call the handleFilterSetttingsGUI method.
     *
     * @param event InventoryClickEvent.
     */
    @EventHandler
    public void onFilterSettingsGUIClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (!(event.getInventory().getHolder() instanceof FilterSettingsHolder)) return;

        handleFilterSettingsGUI(event);
    }

    /**
     * Handles the filter settings GUI according to the player's click.
     *
     * @param event InventoryClickEvent.
     */
    private void handleFilterSettingsGUI(InventoryClickEvent event) {
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        FilterPlayer filterPlayer = FilterPlayer.fromPlayer(player);
        Inventory inventory = event.getInventory();
        int clickedSlot = event.getSlot();
        // Player clicked in one of the page slots.
        if (clickedSlot == 53 || clickedSlot == inventory.getSize() - 9) {
            openNewPageInventory(inventory, clickedSlot, filterPlayer);
            return;
        }
        // Player clicked in the remove all filters button.
        if (clickedSlot == 4) {
            removeAllFilters(filterPlayer);
            return;
        }
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || GUIUtil.isFiller(clickedItem)) return;

        Material filteredItem = clickedItem.getType();
        removeFilteredItem(filterPlayer, filteredItem);
    }

    /**
     * Opens the Filter Settings GUI in a different page according to the clicked slot.
     *
     * @param inventory    Current instance of the Filter Settings GUI.
     * @param clickedSlot  Slot the player clicked.
     * @param filterPlayer FilterPlayer that clicked the inventory.
     */
    private void openNewPageInventory(Inventory inventory, int clickedSlot, FilterPlayer filterPlayer) {
        int page = ((FilterSettingsHolder) inventory.getHolder()).getPage();
        // If the player clicks previous page slot and it is on page 1 then don't do anything
        if (clickedSlot != 53 && page == 1) return;
        if (clickedSlot == 53 && inventory.getItem(53).getType() != ConfigManager.getGuiPageItem()) return;
        if (clickedSlot == 53) {
            page++;
        } else {
            page--;
        }
        Player player = filterPlayer.getPlayer();
        player.openInventory(new FilterSettingsGUI().getGUI(filterPlayer, page));
        SFXManager.playPlayerSound(player, Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 0.6F, 1.4F);
    }

    /**
     * Deletes all the filtered items from the passed FilterPlayer. If it is the first time the player clicks then
     * ask to confirm the operation.
     *
     * @param filterPlayer FilterPlayer that is removing all its filters.
     */
    private void removeAllFilters(FilterPlayer filterPlayer) {
        Player player = filterPlayer.getPlayer();
        if (!deleteConfirmations.contains(player)) {
            askForConfirmation(player);
            return;
        }
        deleteConfirmations.remove(player);
        filterPlayer.removeAllFiteredItems();
        Messager.sendSuccessMessage(player, ConfigManager.getMessage("remove-all-filters"));
        player.openInventory(new FilterSettingsGUI().getGUI(filterPlayer, 1));
    }

    /**
     * Removes the passed filtered item from the passed FilterPlayer's item filter. If the player hasn't been asked
     * for confirmation ask for it before removing it.
     *
     * @param filterPlayer FilterPlayer that is removing an item from its filter.
     * @param filteredItem Material of the filtered item that will be removed from the filter.
     */
    private void removeFilteredItem(FilterPlayer filterPlayer, Material filteredItem) {
        Player player = filterPlayer.getPlayer();
        if (!deleteConfirmations.contains(player)) {
            askForConfirmation(player);
            return;
        }
        deleteConfirmations.remove(player);
        filterPlayer.removeFilteredItem(filteredItem);
        String filterRemoveMessage = ConfigManager.getMessage("remove-single-filter");
        filterRemoveMessage = filterRemoveMessage.replace("%item%", StringUtil.formatEnum(filteredItem));
        Messager.sendSuccessMessage(player, filterRemoveMessage);
        player.openInventory(new FilterSettingsGUI().getGUI(filterPlayer, 1));
    }

    /**
     * Adds the passed player to the deleteConfirmations Set and asks it to confirm the delete operation.
     *
     * @param player Player that is being asked for confirmation.
     */
    private void askForConfirmation(Player player) {
        deleteConfirmations.add(player);
        Bukkit.getScheduler().runTaskLater(plugin, () -> deleteConfirmations.remove(player), 100L);
        Messager.sendMessage(player, ConfigManager.getMessage("remove-confirmation"));
        SFXManager.playPlayerSound(player, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 0.7F, 1.4F);
    }
}