package me.eeshe.itemfilter.util;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SFXManager {

    public static void playSuccessSound(CommandSender sender) {
        playPlayerSound(sender, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
    }

    public static void playErrorSound(CommandSender sender) {
        playPlayerSound(sender, Sound.BLOCK_NOTE_BLOCK_BASS, 1, 2);
    }

    public static void playPlayerSound(CommandSender sender, Sound sound, float volume, float pitch) {
        if (!(sender instanceof Player)) return;

        Player player = (Player) sender;
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static void playWorldSound(Location location, Sound sound, float volume, float pitch) {
        location.getWorld().playSound(location, sound, volume, pitch);
    }
}
