package me.eeshe.itemfilter.files;

import me.eeshe.itemfilter.ItemFilter;
import me.eeshe.itemfilter.models.FilterPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class FilterPlayerFile {
    private File dataFile;
    private FileConfiguration dataYML;

    public FilterPlayerFile(ItemFilter plugin, FilterPlayer filterPlayer) {
        createDataFile(plugin, filterPlayer.getPlayer().getUniqueId().toString());
    }

    public FilterPlayerFile(ItemFilter plugin, Player player) {
        createDataFile(plugin, player.getUniqueId().toString());
    }

    private void createDataFile(ItemFilter plugin, String playerUuidString) {
        this.dataFile = new File(plugin.getDataFolder() + "/filter_players/" + playerUuidString + ".yml");
        if (!dataFile.exists()) {
            try {
                if (!dataFile.getParentFile().exists()) {
                    dataFile.getParentFile().mkdirs();
                }
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.dataYML = YamlConfiguration.loadConfiguration(dataFile);
    }

    public FileConfiguration getData() {
        return dataYML;
    }

    public void saveData() {
        try {
            dataYML.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadData() {
        dataYML.setDefaults(YamlConfiguration.loadConfiguration(dataFile));
    }
}