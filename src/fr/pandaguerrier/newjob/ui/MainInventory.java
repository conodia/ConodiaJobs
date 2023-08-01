package fr.pandaguerrier.newjob.ui;

import fr.pandaguerrier.newjob.ConodiaJobs;
import fr.pandaguerrier.newjob.contracts.JobsAbstract;
import fr.pandaguerrier.newjob.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainInventory {
    Player player;
    public MainInventory(Player player) {
        this.player = player;
        handle();
    }
    public void handle() {
        Inventory inventory = Bukkit.createInventory(player, 45, "§9➜ §bJobs");
        Utils.setBorders(inventory);

        int start = 20;
        for(JobsAbstract job : ConodiaJobs.getInstance().getJobs().getJobs()) {
            inventory.setItem(start, Utils.createGuiItem(job.getMaterial(), job.getFormattedJobName(), 0, "§8§m---------------------", "§7Cliquez pour accèder", "§8§m---------------------"));
            start++;
        }

        this.player.openInventory(inventory);
    }
}
