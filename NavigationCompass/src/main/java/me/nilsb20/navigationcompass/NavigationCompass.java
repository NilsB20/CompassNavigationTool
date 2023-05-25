package me.nilsb20.navigationcompass;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class NavigationCompass extends JavaPlugin {

    private static NavigationCompass instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new NavigationCompassManager(), instance);
        getCommand("navigate").setExecutor(new NavigationCompassManager());
        startTimer();
        Bukkit.getLogger().info("Navigation Compass enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Navigation Compass disabled!");
    }
    
    private void startTimer() {
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(instance, NavigationCompassManager::updateAllCompasses, 0, 1);
    }
}
