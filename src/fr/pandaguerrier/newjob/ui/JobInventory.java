package fr.pandaguerrier.newjob.ui;

import fr.pandaguerrier.newjob.ConodiaJobs;
import fr.pandaguerrier.newjob.contracts.JobsAbstract;
import fr.pandaguerrier.newjob.entities.PlayerCache;
import fr.pandaguerrier.newjob.entities.PlayersEntitites;
import fr.pandaguerrier.newjob.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class JobInventory {
    private final HashMap<UUID, PlayersEntitites> playersJobs = ConodiaJobs.getInstance().getPlayersJobs();
    Player player;
    JobsAbstract job;
    public JobInventory(Player player, JobsAbstract job) {
        this.player = player;
        this.job = job;
        handle();
    }
    public void handle() {
        Inventory inventory = Bukkit.createInventory(player, 45, this.job.getFormattedJobName());
        PlayersEntitites playersEntitites = playersJobs.get(player.getUniqueId());
        if (playersEntitites == null) playersEntitites = Utils.createPlayer(player);
        PlayerCache cache = playersEntitites.getPlayerStats().get(this.job.getJobName());

        Utils.setBorders(inventory);
        inventory.setItem(4, Utils.createGuiItem(job.getMaterial(), job.getFormattedJobName(), 0, ""));

        inventory.setItem(22, Utils.createGuiItem(Material.PAPER, "§bInformations", 0, "§9Level: §b" + cache.getLevel(), "§9Progression: §b" + cache.getXp() + "§8/§b" + (cache.getLevel() + 1)  * job.getMAX_XP()));

        this.player.openInventory(inventory);
    }
}
