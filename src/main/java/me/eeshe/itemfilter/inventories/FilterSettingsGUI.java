package me.eeshe.itemfilter.inventories;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.eeshe.itemfilter.ItemFilter;
import me.eeshe.itemfilter.files.ConfigManager;
import me.eeshe.itemfilter.models.FilterPlayer;
import me.eeshe.itemfilter.util.GUIUtil;
import me.eeshe.itemfilter.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.ListIterator;

public class FilterSettingsGUI {

    /**
     * Returns the Filter Settings GUI with the passed FilterPlayer's filter data.
     *
     * @param filterPlayer FilterPlayer that is opening the GUI.
     * @return Inventory with the Filter settings GUI.
     */
    public Inventory getGUI(FilterPlayer filterPlayer, int page) {
        List<Material> filteredItems = filterPlayer.getFilteredItems();
        int inventorySize = GUIUtil.getFramedInventorySize(filteredItems.size(), page);
        Inventory inventory = Bukkit.createInventory(new FilterSettingsHolder(page), inventorySize, "Filter Settings");
        GUIUtil.setFrame(inventory, ConfigManager.getGuiFrameItem());
        addRemoveAllFiltersItem(inventory);
        addFilteredItems(inventory, filteredItems, page);
        GUIUtil.fillEmpty(inventory, ConfigManager.getGuiFillerItem());
        return inventory;
    }

    /**
     * Adds the item that allows players to remove all their filters.
     *
     * @param inventory Inventory where the item will be added to.
     */
    private void addRemoveAllFiltersItem(Inventory inventory) {
        ItemStack item;
        if (ItemFilter.getInstance().getServer().getPluginManager().isPluginEnabled("HeadDatabase")) {
            HeadDatabaseAPI headDatabaseAPI = new HeadDatabaseAPI();
            item = headDatabaseAPI.getItemHead(ConfigManager.getGuiHeadId());
        } else {
            item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ConfigManager.getDeleteFiltersItemName());
        item.setItemMeta(meta);
        inventory.setItem(4, item);
    }

    /**
     * Adds all the filtered items to the passed Filter Settings GUI.
     *
     * @param inventory     Inventory where the filtered items will be added to.
     * @param filteredItems Filtered items that will be added to the inventory.
     */
    public void addFilteredItems(Inventory inventory, List<Material> filteredItems, int page) {
        // Formatting Filter Info Item lore
        List<String> lore = ConfigManager.getFilteredItemLore();
        ListIterator<String> loreIterator = lore.listIterator();
        while (loreIterator.hasNext()) {
            loreIterator.set(StringUtil.formatColor(loreIterator.next()));
        }
        boolean hasNextPage = false;
        int initialIndex = GUIUtil.getInitialIndex(page);
        for (int i = initialIndex; i < filteredItems.size(); i++) {
            if (inventory.firstEmpty() == -1) {
                hasNextPage = true;
                break;
            }
            Material filteredItem = filteredItems.get(i);
            // Creating filter info item
            ItemStack item = new ItemStack(filteredItem);
            ItemMeta meta = item.getItemMeta();
            meta.setLore(lore);
            item.setItemMeta(meta);

            inventory.addItem(item);
        }
        if (hasNextPage || page > 1) {
            GUIUtil.setPageItems(inventory, page, hasNextPage);
        }
    }
}
