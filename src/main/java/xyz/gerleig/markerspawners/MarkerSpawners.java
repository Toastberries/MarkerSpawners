package xyz.gerleig.markerspawners;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MarkerSpawners extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("asd");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onSpawnerSpawnEvent(SpawnerSpawnEvent e) {
        Entity entity = e.getEntity();
        World world = entity.getWorld();
        //getLogger().info(entity.getScoreboardTags().toString());

        if (entity.getScoreboardTags().contains("MarkerSpawner")) {
            e.setCancelled(true);
            getLogger().info(world.getNearbyEntities(entity.getLocation(), 9, 9, 9, (nearbyEntity) -> nearbyEntity.getType() == EntityType.ARMOR_STAND).size() + 1 + " yo!");

            Entity placeholder = world.spawnEntity(entity.getLocation().add(0, 1, 0), EntityType.ARMOR_STAND);
            entity.getScoreboardTags().forEach(placeholder::addScoreboardTag);
        }

    }
}
