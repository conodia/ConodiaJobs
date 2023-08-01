package fr.pandaguerrier.newjob.jobs;

import fr.pandaguerrier.newjob.contracts.JobsAbstract;
import org.bukkit.Material;

public class Miner extends JobsAbstract {
    @Override
    public String getJobName() {
        return "miner";
    }
    @Override
    public String getFormattedJobName() {
        return "§8➜ §7Mineur";
    }
    @Override
    public Material getMaterial() {
        return Material.DIAMOND_PICKAXE;
    }
}
