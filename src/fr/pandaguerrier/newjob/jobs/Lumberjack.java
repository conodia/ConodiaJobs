package fr.pandaguerrier.newjob.jobs;

import fr.pandaguerrier.newjob.contracts.JobsAbstract;
import org.bukkit.Material;

public class Lumberjack extends JobsAbstract {
    @Override
    public String getJobName() {
        return "lumberjack";
    }
    @Override
    public String getFormattedJobName() {
        return "§9➜ §bBucheron";
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_AXE;
    }
}
