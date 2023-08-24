package fr.pandaguerrier.jobs;

import fr.pandaguerrier.jobs.cache.PlayerCache;
import fr.pandaguerrier.jobs.commands.JobsCommand;
import fr.pandaguerrier.jobs.listeners.InventoryListener;
import fr.pandaguerrier.jobs.listeners.JobsListener;
import fr.pandaguerrier.jobs.listeners.PlayerListener;
import fr.pandaguerrier.jobs.managers.JobsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ConodiaJobs extends JavaPlugin {
  private static ConodiaJobs instance;
  private PlayerCache playerCache;

  public void onEnable() {
    instance = this;
    playerCache = new PlayerCache();
    playerCache.sync();

    // other
    loadCommands();
    loadEvents();
    System.out.println("\n \n-------------------------\n \nLe new ConodiaJobs est connecté !\n \n-------------------------\n \n");
  }

  public void onDisable() {
    System.out.println("\n \n-------------------------\n \nLe new ConodiaJobs est déconnecté !\n \n-------------------------\n \n");
    for (JobsManager jobsManager : playerCache.getCache().values()) {
      jobsManager.update();
    }
  }

  private void loadCommands() {
    this.getCommand("jobs").setExecutor(new JobsCommand());
  }

  private void loadEvents() {
    this.getServer().getPluginManager().registerEvents(new JobsListener(), this);
    this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
  }

  /**
   * Getters
   */

  public static ConodiaJobs getInstance() {
    return instance;
  }

  public PlayerCache getPlayerCache() {
    return playerCache;
  }
}