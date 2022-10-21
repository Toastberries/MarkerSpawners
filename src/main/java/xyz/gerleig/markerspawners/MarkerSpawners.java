package xyz.gerleig.markerspawners;

import xyz.gerleig.markerspawners.handlers.SpawnerSpawnEvent;
import xyz.gerleig.markerspawners.handlers.SpawnerProtection;

import org.bukkit.plugin.java.JavaPlugin;

public final class MarkerSpawners extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new SpawnerSpawnEvent(), this);
        getServer().getPluginManager().registerEvents(new SpawnerProtection(), this);
    }


}
