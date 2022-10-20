package xyz.gerleig.markerspawners;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;

import java.util.Set;


public final class MarkerSpawners extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onSpawnerSpawnEvent(SpawnerSpawnEvent e) {
        Entity entity = e.getEntity();

        if (!entity.getScoreboardTags().contains("MarkerSpawner")) return;
        World world = entity.getWorld();
        Set<String> tags = entity.getScoreboardTags();
        e.setCancelled(true);

        String Id = "EXAMPLE_MOB";
        EntityType Type = EntityType.ZOMBIE;
        int SpawnLimit = 6;
        double CheckDistance = 9;
        EntityType PlaceholderType = EntityType.ARMOR_STAND;
        double YOffset = 0;

        for (String tag : tags) {
            String[] split = tag.split("-");
            switch (split[0]) {
                case "Id" -> Id = split[1];
                case "Type" -> Type = EntityType.valueOf(split[1]);
                case "SpawnLimit" -> SpawnLimit = Integer.parseInt(split[1]);
                case "CheckDistance" -> CheckDistance = Double.parseDouble(split[1]);
                case "Placeholder" -> PlaceholderType = EntityType.valueOf(split[1]);
                case "YOffset" -> YOffset = Double.parseDouble(split[1]);
            }
        }

        EntityType finalType = Type;
        if (world.getNearbyEntities(entity.getLocation(), CheckDistance, CheckDistance, CheckDistance, (nearbyEntity) -> nearbyEntity.getType() == finalType).size() + 1 > SpawnLimit) return;
        Entity spawn = world.spawnEntity(entity.getLocation().add(0, YOffset, 0), PlaceholderType, CreatureSpawnEvent.SpawnReason.CUSTOM);
        spawn.addScoreboardTag("MarkerSpawner");
        spawn.addScoreboardTag(Id);

    }

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
