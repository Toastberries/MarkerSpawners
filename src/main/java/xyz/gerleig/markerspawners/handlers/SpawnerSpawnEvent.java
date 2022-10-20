package xyz.gerleig.markerspawners.handlers;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;

public class SpawnerSpawnEvent implements Listener {
    @EventHandler
    public void onSpawnerSpawnEvent(org.bukkit.event.entity.SpawnerSpawnEvent e) {
        Entity entity = e.getEntity();

        if (!entity.getScoreboardTags().contains("MarkerSpawner")) return;
        World world = entity.getWorld();
        Set<String> tags = entity.getScoreboardTags();
        e.setCancelled(true);

        String Id = "EXAMPLE_MOB";
        EntityType Type = EntityType.ZOMBIE;
        int SpawnLimit = 6;
        double CheckDistance = 9;
        double YOffset = 1;

        for (String tag : tags) {
            String[] split = tag.split("\\.");
            if (split.length == 0) break;
            switch (split[0]) {
                case "Id" -> Id = split[1];
                case "Type" -> Type = EntityType.valueOf(split[1]);
                case "SpawnLimit" -> SpawnLimit = Integer.parseInt(split[1]);
                case "CheckDistance" -> CheckDistance = Double.parseDouble(split[1]);
                case "YOffset" -> YOffset = Double.parseDouble(split[1]);
            }
        }

        EntityType finalType = Type;
        if (world.getNearbyEntities(e.getSpawner().getLocation(), CheckDistance, CheckDistance, CheckDistance, (nearbyEntity) -> nearbyEntity.getType() == finalType).size() + 1 > SpawnLimit) return;
        //Entity spawn = world.spawnEntity(entity.getLocation().add(0, YOffset, 0), PlaceholderType, CreatureSpawnEvent.SpawnReason.CUSTOM);
        //spawn.addScoreboardTag("MarkerSpawner");
        //spawn.addScoreboardTag(Id);
        MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob(Id).orElse(null);
        Location spawnLocation = entity.getLocation().add(0, YOffset, 0);
        if(mob != null){
            // spawns mob
            world.spawnParticle(Particle.EXPLOSION_NORMAL, spawnLocation.add(0,1,0), 12, 0.3,0.3,0.3, 0.01);
            mob.spawn(BukkitAdapter.adapt(spawnLocation),1);
        }


    }
}
