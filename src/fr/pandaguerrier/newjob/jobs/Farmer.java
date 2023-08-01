package fr.pandaguerrier.newjob.jobs;

import fr.pandaguerrier.newjob.ConodiaJobs;
import fr.pandaguerrier.newjob.contracts.JobsAbstract;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Farmer extends JobsAbstract {
    @Override
    public String getJobName() {
        return "farmer";
    }
    @Override
    public String getFormattedJobName() {
        return "§2➜ §aFermier";
    }

    public void addXpByCraft(Player player, ItemStack item) {
        FileConfiguration config = ConodiaJobs.getInstance().getConfig();
        double xp = config.getDouble("jobs." + getJobName() + ".craft." + item.getType().toString(), 0) * item.getAmount();
        if (xp == 0) return;
        addXp(player, xp);
        System.out.println(xp);
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_HOE;
    }
}
