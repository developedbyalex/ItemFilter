package me.eeshe.itemfilter.files;

import me.eeshe.itemfilter.ItemFilter;
import me.eeshe.itemfilter.util.LogUtil;
import me.eeshe.itemfilter.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {
    private static final ItemFilter PLUGIN = ItemFilter.getInstance();

    /**
     * Searches the configuration 'gui-settings.frame-item' and returns its corresponding material.
     *
     * @return Material that represents the GUI frame.
     */
    public static Material getGuiFrameItem() {
        return fetchMaterialFromGuiConfig("frame-item");
    }

    /**
     * Searches the configuration 'gui-settings.page-item' and returns its corresponding material.
     *
     * @return Material that represents the page buttons of the GUI.
     */
    public static Material getGuiPageItem() {
        return fetchMaterialFromGuiConfig("page-item");
    }

    /**
     * Searches the configuration 'gui-settings.empty-filler' and returns its corresponding material.
     *
     * @return Material that represents the filler of the GUI.
     */
    public static Material getGuiFillerItem() {
        return fetchMaterialFromGuiConfig("empty-filler");
    }

    private static Material fetchMaterialFromGuiConfig(String path) {
        String materialName = getConfig().getString("gui-settings." + path);
        if (materialName == null) {
            LogUtil.sendWarnLog("Item not provided in '" + path + "' on config.yml.");
            return Material.AIR;
        }
        Material material = Material.matchMaterial(materialName);
        if (material == null) {
            LogUtil.sendWarnLog("Unknown material '" + materialName + "' for in '" + path + "' on config.yml");
            return Material.AIR;
        }
        return material;
    }

    /**
     * Searches the configuration 'gui-settings.delete-filters-item-name' and returns its String value.
     *
     * @return String with the name of the item that will act as the delete all filters button in the GUI.
     */
    public static String getDeleteFiltersItemName() {
        String itemName = getConfig().getString("gui-settings.delete-filters-item-name");
        if (itemName == null) return "";

        return StringUtil.formatColor(itemName);
    }

    /**
     * Searches the configuration 'gui-settings.gui-head-id' and returns its String value.
     *
     * @return String with the head ID that will be used on the gui settings.
     */
    public static String getGuiHeadId() {
        String headId = getConfig().getString("gui-settings.gui-head-id");
        if (headId == null) return "";

        return headId;
    }

    /**
     * Seaches the configuration 'gui-settings.item-lore' and returns its String list value.
     *
     * @return List of String with the filtered item's lore.
     */
    public static List<String> getFilteredItemLore() {
        return getConfig().getStringList("gui-settings.item-lore");
    }

    /**
     * Searches the passed path in the 'messages' section of the config.yml and returns its String value translating all
     * color codes.
     *
     * @param path Path where the message will be searched in the config.yml
     * @return A string with the message with all the formatted color codes.
     */
    public static String getMessage(String path) {
        String message = getConfig().getString("messages." + path);
        if (message == null) return "";

        return StringUtil.formatColor(message);
    }

    /**
     * Returns a FileConfiguration instance of the config.yml.
     *
     * @return FileConfiguration of the config.yml file.
     */
    private static FileConfiguration getConfig() {
        return PLUGIN.getConfig();
    }
}
