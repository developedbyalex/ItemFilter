package me.eeshe.itemfilter.util;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;

public class VFXManager {

    public static void spawnParticles(Location location, Particle particle, int amount, double generalOffSet) {
        location.getWorld().spawnParticle(particle, location, amount, generalOffSet, generalOffSet, generalOffSet);
    }

    public static void spawnParticles(Location location, Particle particle, int amount, double generalOffSet,
                                      double speed) {
        location.getWorld().spawnParticle(particle, location, amount, generalOffSet, generalOffSet, generalOffSet, speed);
    }

    public static void spawnParticles(Location location, Particle particle, int amount, double xOffSet, double yOffSet,
                                      double zOffSet, double speed) {
        location.getWorld().spawnParticle(particle, location, amount, xOffSet, yOffSet, zOffSet, speed);
    }

    public static void spawnBlockParticles(Location location, int amount, double generalOffset, double speed,
                                           Block block) {
        location.getWorld().spawnParticle(Particle.BLOCK_CRACK, location, amount, generalOffset, generalOffset,
                generalOffset, speed, block.getBlockData());
    }

    public static void spawnColorParticles(Location location, int amount, double generalOffSet, double speed,
                                           Color color, float size) {
        Particle.DustOptions colorSettings = new Particle.DustOptions(color, size);
        location.getWorld().spawnParticle(Particle.REDSTONE, location, amount, generalOffSet, generalOffSet,
                generalOffSet, speed, colorSettings);
    }
}
