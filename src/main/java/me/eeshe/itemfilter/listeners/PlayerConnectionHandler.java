package me.eeshe.itemfilter.listeners;

import me.eeshe.itemfilter.files.FilterPlayerManager;
import me.eeshe.itemfilter.models.FilterPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionHandler implements Listener {
    private final FilterPlayerManager filterPlayerManager;

    public PlayerConnectionHandler(FilterPlayerManager filterPlayerManager) {
        this.filterPlayerManager = filterPlayerManager;
    }

    /**
     * Listens when a player joins the server and loads and registers its corresponding instance of FilterPlayer.
     *
     * @param event PlayerJoinEvent.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        filterPlayerManager.loadFilterPlayer(event.getPlayer()).registerFilterPlayer();
    }

    /**
     * Listens when a player quits the server and saves and unregisters its corresponding instance of FilterPlayer.
     *
     * @param event PlayerQuitEvent.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        filterPlayerManager.saveFilterPlayer(FilterPlayer.fromPlayer(event.getPlayer()));
    }
}
