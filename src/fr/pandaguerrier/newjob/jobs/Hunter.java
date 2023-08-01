package fr.pandaguerrier.newjob.jobs;

import fr.pandaguerrier.newjob.ConodiaJobs;
import fr.pandaguerrier.newjob.contracts.JobsAbstract;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Hunter extends JobsAbstract {
    @Override
    public String getJobName() {
        return "hunter";
    }
    @Override
    public String getFormattedJobName() {
        return "§4➜ §cChasseur";
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }

    public void addXpByMob(Player player, Entity entity) {
        this.addXp(player, this.getMobXp(entity));
    }

    public double getMobXp(Entity entity) {
        FileConfiguration config = ConodiaJobs.getInstance().getConfig();
        return config.getDouble("jobs." + getJobName() + ".mobs." + entity.getType().toString(), 0);
    }
}
