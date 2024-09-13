package me.eeshe.itemfilter.inventories;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class FilterSettingsHolder implements InventoryHolder {
    private final int page;

    public FilterSettingsHolder(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
