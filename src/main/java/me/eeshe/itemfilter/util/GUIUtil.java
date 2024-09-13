package me.eeshe.itemfilter.util;

import me.eeshe.itemfilter.files.ConfigManager;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.ListIterator;

public class GUIUtil {

    public static void setFrame(Inventory inventory, Material frameMaterial) {
        ItemStack filler = getFiller(frameMaterial);
        int inventorySize = inventory.getSize();
        int lastInventoryRowIndex = inventorySize - 9;
        for (int i = 0; i < inventorySize; i++) {
            if (i > 8 && i < lastInventoryRowIndex) continue;

            inventory.setItem(i, filler);
        }
    }

    public static void fillEmpty(Inventory inventory, Material fillMaterial) {
        ItemStack filler = getFiller(fillMaterial);
        while (inventory.firstEmpty() != -1) {
            inventory.setItem(inventory.firstEmpty(), filler);
        }
    }

    public static void setPageItems(Inventory inventory, int page, boolean hasNextPage) {
        if (page > 1) {
            inventory.setItem(inventory.getSize() - 9, getGUIItem(ConfigManager.getGuiPageItem(), "&6Previous Page", null));
        }
        if (hasNextPage) {
            inventory.setItem(53, getGUIItem(ConfigManager.getGuiPageItem(), "&6Next Page", null));
        }
    }

    public static void addBackItem(Inventory inventory) {
        inventory.setItem(0, getGUIItem(Material.RED_STAINED_GLASS_PANE, "&c&lGo Back", null));
    }

    public static ItemStack getGUIItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (name != null) {
            meta.setDisplayName(StringUtil.formatColor(name));
        }
        if (lore != null) {
            ListIterator<String> loreIterator = lore.listIterator();
            while (loreIterator.hasNext()) {
                loreIterator.set(StringUtil.formatColor(loreIterator.next()));
            }
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static boolean isFiller(ItemStack item) {
        if (item == null) return false;
        if (!item.hasItemMeta()) return false;

        return item.getItemMeta().getDisplayName().equals(" ");
    }

    public static int getFramedInventorySize(int itemAmount) {
        int inventorySize = 18 + 9 * (int) Math.ceil(itemAmount / 9.0);
        if (inventorySize > 54) return 54;

        return Math.max(27, inventorySize);
    }

    public static int getFramedInventorySize(int itemAmount, int page) {
        // Reducing the amount of items present in previous pages (If there are)
        itemAmount -= --page * 36;
        return getFramedInventorySize(itemAmount);
    }


    public static int getInitialIndex(int page) {
        return page == 1 ? 0 : (page - 1) * 36;
    }

    private static ItemStack getFiller(Material material) {
        ItemStack filler = new ItemStack(material);
        ItemMeta meta = filler.getItemMeta();
        meta.setDisplayName(" ");
        filler.setItemMeta(meta);
        return filler;
    }
}
