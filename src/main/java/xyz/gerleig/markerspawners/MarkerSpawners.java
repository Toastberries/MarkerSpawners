package xyz.gerleig.markerspawners;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

            Object[] args = entity.getScoreboardTags().toArray(); // FIX THIS
            // 0: "MarkerSpawner" / 1: ID / 2: Type / 3: Amount / 4: Distance
            String id = (String) args[1];
            EntityType type = EntityType.valueOf((String) args[2]); // FIX THIS ALSO
            int amount = Integer.parseInt((String) args[3]);
            double distance = Double.parseDouble((String) args[4]);

            if (world.getNearbyEntities(entity.getLocation(), distance, distance, distance, (nearbyEntity) -> nearbyEntity.getType() == type).size() + 1 > amount) return;

            world.spawnEntity(entity.getLocation().add(0, 1, 0), EntityType.ARMOR_STAND).addScoreboardTag(id);
        }

    }
}
