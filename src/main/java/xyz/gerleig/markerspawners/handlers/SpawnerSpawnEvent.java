package xyz.gerleig.markerspawners.handlers;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.WorldScaling;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SpawnerSpawnEvent implements Listener {
    @EventHandler
    public void onSpawnerSpawnEvent(org.bukkit.event.entity.SpawnerSpawnEvent e) {
        Entity entity = e.getEntity();

        if (!entity.getScoreboardTags().contains("MarkerSpawner")) return;
        e.setCancelled(true);

        String id = "none";
        int spawnLimit = 6;
        double checkDistance = 9;
        double yOffset = 1;
        int level = -1;

        for (String tag : entity.getScoreboardTags()) {
            String[] split = tag.split("_");
            switch (split[0]) {
                case "id" -> id = split[1];
                case "spawnLimit" -> spawnLimit = Integer.parseInt(split[1]);
                case "checkDistance" -> checkDistance = Double.parseDouble(split[1]);
                case "yOffset" -> yOffset = Double.parseDouble(split[1]);
                case "level" -> level = Integer.parseInt(split[1]);
            }
        }

        MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob(id).orElse(null);
        if (mob == null) return;
        World world = entity.getWorld();
        EntityType type = EntityType.valueOf(mob.getEntityType().toUpperCase());
        Location SpawnerLocation = e.getSpawner().getLocation();

        if (world.getNearbyEntities(SpawnerLocation, checkDistance, checkDistance, checkDistance, (nearbyEntity) -> nearbyEntity.getType() == type).size() < spawnLimit) {
            if (level == -1) level = (int) WorldScaling.getLevelBonus(BukkitAdapter.adapt(SpawnerLocation));
            Location SpawnLocation = entity.getLocation().add(0, yOffset, 0);

            world.spawnParticle(Particle.EXPLOSION_NORMAL, SpawnLocation.add(0, 1, 0), 12, 0.3, 0.3, 0.3, 0.01);
            mob.spawn(BukkitAdapter.adapt(SpawnLocation), level);
        }
    }
}
