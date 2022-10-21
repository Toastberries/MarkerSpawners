package xyz.gerleig.markerspawners.handlers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class SpawnerProtection implements Listener {
    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        if (isMarkerSpawner(e.getBlock()) && !e.getPlayer().hasPermission("markerspawners.break")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("This spawner has a powerful aura, it doesn't seem breakable.");
        }
    }
    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent e) {
        e.blockList().removeIf(this::isMarkerSpawner);
    }

    public boolean isMarkerSpawner(Block block) {
        if (block.getType() != Material.SPAWNER) return false;
        else return ((CreatureSpawner) block.getState()).getSpawnedType() == EntityType.MARKER;
    }
}
