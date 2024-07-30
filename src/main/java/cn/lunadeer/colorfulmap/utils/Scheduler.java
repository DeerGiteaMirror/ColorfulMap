package cn.lunadeer.colorfulmap.utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

import static cn.lunadeer.colorfulmap.utils.Common.isPaper;

public class Scheduler {
    public static Scheduler instance;
    private final JavaPlugin plugin;
    private boolean isPaper = false;

    public Scheduler(JavaPlugin plugin) {
        instance = this;
        this.plugin = plugin;
        this.isPaper = isPaper();
    }

    public static void cancelAll() {
        if (instance.isPaper) {
            instance.plugin.getServer().getGlobalRegionScheduler().cancelTasks(instance.plugin);
            instance.plugin.getServer().getGlobalRegionScheduler().cancelTasks(instance.plugin);
        } else {
            instance.plugin.getServer().getScheduler().cancelTasks(instance.plugin);
        }
    }

    /**
     * Run a task asynchronously
     *
     * @param task The task to run
     * @return The scheduled task
     */
    public static void runTaskAsync(Runnable task) {
        if (instance.isPaper) {
            instance.plugin.getServer().getAsyncScheduler().runNow(instance.plugin, (plugin) -> task.run());
        } else {
            instance.plugin.getServer().getScheduler().runTask(instance.plugin, task);
        }
    }
}
