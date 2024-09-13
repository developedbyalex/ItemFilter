package me.eeshe.itemfilter.util;

import me.eeshe.itemfilter.ItemFilter;
import org.bukkit.Bukkit;

import java.util.logging.Logger;

public class LogUtil {
    protected static final String PLUGIN_NAME = ItemFilter.getInstance().getDescription().getName();
    protected static final Logger LOGGER = Bukkit.getLogger();

    public static void sendInfoLog(String text) {
        LOGGER.info("[" + PLUGIN_NAME + "] " + text);
    }

    public static void sendWarnLog(String text) {
        LOGGER.warning("[" + PLUGIN_NAME + "] " + text);
    }
}
