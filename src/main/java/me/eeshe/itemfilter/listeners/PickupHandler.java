package me.eeshe.itemfilter.listeners;

import me.eeshe.itemfilter.models.FilterPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PickupHandler implements Listener {

    /**
     * Listens when an entity pick ups an item. If the entity is a player and has the item filtered then cancel the event.
     *
     * @param event EntityPickupItemEvent.
     */
    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        FilterPlayer filterPlayer = FilterPlayer.fromPlayer(player);
        Material pickedItem = event.getItem().getItemStack().getType();
        if (!filterPlayer.hasFilteredItem(pickedItem)) return;

        event.setCancelled(true);
    }
}
