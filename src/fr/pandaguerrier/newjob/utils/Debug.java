package fr.pandaguerrier.newjob.utils;

import fr.pandaguerrier.newjob.ConodiaJobs;

public class Debug {
    public static void info(String msg) {
        ConodiaJobs.getInstance().getServer().getLogger().info("[ DEBUG ]" + msg);
    }

    public static void severe(String msg) {
        ConodiaJobs.getInstance().getServer().getLogger().severe(msg);
    }
}
