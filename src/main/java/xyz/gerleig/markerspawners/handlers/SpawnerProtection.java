package xyz.gerleig.markerspawners.handlers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;

public class SpawnerProtection implements Listener {
    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        if (isNotMarkerSpawner(e.getBlock())) return;
        if (e.getPlayer().hasPermission("markerspawners.break")) return;
        e.setCancelled(true);
        e.getPlayer().sendMessage("This spawner has a powerful aura, it doesn't seem breakable.");
    }
    @EventHandler
    public void onBlockExplodeEvent(BlockExplodeEvent e) {
        if (isNotMarkerSpawner(e.getBlock())) return;
        e.setCancelled(true);
    }

    public boolean isNotMarkerSpawner(Block block) {
        if (block.getType() != Material.SPAWNER) return true;
        else return ((CreatureSpawner) block.getState()).getSpawnedType() != EntityType.MARKER;
    }
}
