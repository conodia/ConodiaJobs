package fr.pandaguerrier.newjob.jobs;

import fr.pandaguerrier.newjob.contracts.JobsAbstract;
import org.bukkit.Material;

public class Constructor extends JobsAbstract {
    @Override
    public String getJobName() {
        return "constructor";
    }
    @Override
    public String getFormattedJobName() {
        return "§6➜ §eConstructeur";
    }

    @Override
    public Material getMaterial() {
        return Material.BRICK;
    }
}
