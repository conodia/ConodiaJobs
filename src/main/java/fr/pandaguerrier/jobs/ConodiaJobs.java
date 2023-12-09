package fr.pandaguerrier.jobs;

import fr.pandaguerrier.conodiagameapi.utils.Utils;
import fr.pandaguerrier.jobs.cache.FLevelCache;
import fr.pandaguerrier.jobs.cache.PlayerCache;
import fr.pandaguerrier.jobs.commands.JobsCommand;
import fr.pandaguerrier.jobs.commands.LevelsCommand;
import fr.pandaguerrier.jobs.commands.ReloadCommand;
import fr.pandaguerrier.jobs.listeners.FactionListener;
import fr.pandaguerrier.jobs.listeners.InventoryListener;
import fr.pandaguerrier.jobs.listeners.JobsListener;
import fr.pandaguerrier.jobs.listeners.PlayerListener;
import fr.pandaguerrier.jobs.managers.JobsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConodiaJobs extends JavaPlugin {
  private static ConodiaJobs instance;
  private PlayerCache playerCache;

  private FLevelCache fLevelCache;
  private FileConfiguration rewardFile;

  public void onEnable() {
    instance = this;
    playerCache = new PlayerCache();
    playerCache.sync();

    fLevelCache = new FLevelCache();
    fLevelCache.sync();

    // config
    this.saveDefaultConfig();
    rewardFile = Utils.createCustomConfig("rewards.yml", this);

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
    this.getCommand("conojreload").setExecutor(new ReloadCommand());
    this.getCommand("levels").setExecutor(new LevelsCommand());

  }

  private void loadEvents() {
    this.getServer().getPluginManager().registerEvents(new JobsListener(), this);
    this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    this.getServer().getPluginManager().registerEvents(new FactionListener(), this);
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
  public FLevelCache getfLevelCache() {
    return fLevelCache;
  }
  public FileConfiguration getRewardFile() {
    return rewardFile;
  }

  public void setRewardFile(FileConfiguration rewardFile) {
    this.rewardFile = rewardFile;
  }
}