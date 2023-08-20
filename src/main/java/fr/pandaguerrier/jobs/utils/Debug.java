package fr.pandaguerrier.jobs.utils;

import fr.pandaguerrier.jobs.ConodiaJobs;

public class Debug {
  public static void info(String msg) {
    ConodiaJobs.getInstance().getServer().getLogger().info("[ DEBUG ]" + msg);
  }

  public static void severe(String msg) {
    ConodiaJobs.getInstance().getServer().getLogger().severe(msg);
  }
}
